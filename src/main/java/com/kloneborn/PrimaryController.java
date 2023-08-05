package com.kloneborn;

import java.io.*;
import java.net.*;
import java.text.NumberFormat;
import java.text.ParseException;

import java.util.*;
import com.google.gson.*;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.util.StringConverter;

public class PrimaryController implements Initializable {

    public static final String API_KEY = fetchApiKey();
    public static final String CURRENCY_REGEX = "^(?!0*(\\.0{1,2})?$)\\$?\\d+(,\\d{3})*(\\.\\d{0,2})?$";

    @FXML
    private ChoiceBox<Currency> cb_currFrom, cb_currTo;
    @FXML
    private Circle cr_status;
    @FXML
    private Label lb_conn, lb_currAmt;
    @FXML
    private TextField tf_currAmt;
    @FXML
    private Text tx_amtErr;

    private HttpExchangeClient client;

    @FXML
    private void onActionParseAmount(ActionEvent event) {
        parseCurrenct();
    }

    @FXML
    private void onActionConvertInputAmount(ActionEvent event) {
        Double amt = parseCurrenct();
        if (amt == -1.0)
            return;
        Currency from = cb_currFrom.getValue();
        if (from == null) {
            cb_currFrom.setStyle("-fx-border-color:red;");
        } else
            cb_currFrom.setStyle("");
        Currency to = cb_currTo.getValue();
        if (to == null) {
            cb_currTo.setStyle("-fx-border-color:red;");
        } else
            cb_currTo.setStyle("");
        if (amt > -1.0 && from != null && to != null) {
            double rate = latest(from, to);
            lb_currAmt.setText(String.format("$%.2f", amt * rate));
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        client = new HttpExchangeClient(API_KEY, lb_conn, cr_status);

        if (!client.bConnected) {
            Alert exit = new Alert(AlertType.ERROR, "Unable to connect to exchange host", ButtonType.CLOSE);
            exit.showAndWait();
            Platform.exit();
        }

        cr_status.setFill(Color.GREEN);
        tx_amtErr.setManaged(false);
        tx_amtErr.setVisible(false);
        lb_currAmt.setText("");
        configure(cb_currFrom, cb_currTo);
    }

    private double parseCurrenct() {
        String text = tf_currAmt.getText();
        if (!text.matches(CURRENCY_REGEX)) {
            tx_amtErr.setManaged(true);
            tx_amtErr.setVisible(true);
            tx_amtErr.setText("Unable to convert amount to currency");
            return -1.0;
        }
        tx_amtErr.setManaged(false);
        tx_amtErr.setVisible(false);
        return extractDoubleFromCurrency(text);
    }

    public static double extractDoubleFromCurrency(String currencyString) {
        // Remove currency symbols and commas
        String cleanedString = currencyString.replaceAll("[^\\d.]", "");

        try {
            // Parse the cleaned string as a double
            NumberFormat format = NumberFormat.getInstance(Locale.US);
            Number number = format.parse(cleanedString);
            return number.doubleValue();
        } catch (ParseException e) {
            // Handle the parsing exception here
            e.printStackTrace();
            return 0.0; // Default value in case of an error
        }
    }

    private double latest(Currency frm, Currency to) {
        client.params.put("base_currency", frm.code);
        client.params.put("currencies", to.code);
        JsonObject obj = client.execute();
        return obj
                .get("data").getAsJsonObject()
                .get(to.code).getAsJsonObject()
                .get("value").getAsDouble();
    }

    public static void configure(ChoiceBox<Currency>... nodes) {
        for (ChoiceBox<Currency> choicebox : nodes) {
            choicebox.setItems(FXCollections.observableArrayList(Currency.values()));
            choicebox.setConverter(new StringConverter<Currency>() {
                @Override
                public String toString(Currency currency) {
                    if (currency == null) {
                        return null;
                    } else {
                        return String.format("%s - %s", currency.code, currency.fullName);
                    }
                }

                @Override
                public Currency fromString(String code) {
                    return Currency.getByCode(code);
                }
            });
        }
    }

    private static String fetchApiKey() {
        String file = "apikey.json";
        JsonObject obj = JsonParser.parseReader(new InputStreamReader(App.class.getResourceAsStream(file)))
                .getAsJsonObject();
        return obj.get("value").getAsString();
    }

    public class HttpExchangeClient {
        public static final String base_url = "https://api.currencyapi.com/";
        public final String api_key;
        public final Map<String, String> params;
        private String endpoint = "";
        private Label connOutput;
        private Circle statusOutput;
        public final boolean bConnected;

        public HttpExchangeClient(String api_key, Label connOut, Circle statOut) {
            this.api_key = api_key;
            this.connOutput = connOut;
            this.statusOutput = statOut;
            this.params = new HashMap<>();
            this.bConnected = isConnectionAvailable();
            this.endpoint = "v3/latest";
        }

        private void logEvent(String event, boolean bError) {
            connOutput.setText(event);
            statusOutput.setFill((bError ? Color.RED : Color.GREEN));
        }

        public boolean isConnectionAvailable() {
            try {
                URL status_url = URI.create(this.base_url + "v3/status" + "?apikey=" + this.api_key).toURL();
                HttpURLConnection connection = (HttpURLConnection) status_url.openConnection();
                connection.setRequestMethod("HEAD"); // Use HEAD method for a lightweight check
                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK)
                    logEvent("Connection established", false);
                else
                    logEvent("Connection failed", true);
                return (responseCode == HttpURLConnection.HTTP_OK);
            } catch (IOException e) {
                return false; // Connection cannot be established
            }
        }

        public JsonObject execute() {
            try {
                URL url = getURL();
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.connect();
                InputStream is = (InputStream) conn.getContent();
                JsonElement jsElem = JsonParser.parseReader(new InputStreamReader(is));
                JsonObject response = jsElem.getAsJsonObject();

                // Return the response
                return response;
            } catch (IOException io) {
                io.printStackTrace();

                // Log error message
                logEvent("Error occurred: " + io.getMessage(), true);
            }
            return null;
        }

        private URL getURL() throws MalformedURLException {
            String parameters = "";
            for (Map.Entry<String, String> param : this.params.entrySet()) {
                String token = String.format("&%s=%s", param.getKey(), param.getValue());
                parameters += token;
            }
            return URI.create(base_url + endpoint + "?apikey=" + this.api_key + "" + parameters).toURL();
        }
    }
}