package com.kloneborn;

public enum Currency {
    USD("USD", "United States Dollar"),
    EUR("EUR", "Euro"),
    JPY("JPY", "Japanese Yen"),
    GBP("GBP", "British Pound Sterling"),
    AUD("AUD", "Australian Dollar"),
    CAD("CAD", "Canadian Dollar"),
    CHF("CHF", "Swiss Franc"),
    CNY("CNY", "Chinese Yuan"),
    SEK("SEK", "Swedish Krona"),
    NZD("NZD", "New Zealand Dollar"),
    XCD("XCD", "East Caribbean Dollar"),
    HKD("HKD", "Hong Kong Dollar"),
    INR("INR", "Indian Rupee"),
    SGD("SGD", "Singapore Dollar"),
    KRW("KRW", "South Korean Won"),
    MXN("MXN", "Mexican Peso"),
    BRL("BRL", "Brazilian Real"),
    RUB("RUB", "Russian Ruble"),
    ZAR("ZAR", "South African Rand"),
    TRY("TRY", "Turkish Lira");

    public final String code;
    public final String fullName;

    Currency(String code, String fullName) {
        this.code = code;
        this.fullName = fullName;
    }

    public static Currency getByCode(String code) {
        for (Currency currency : values()) {
            if (currency.code.equals(code)) {
                return currency;
            }
        }
        throw new IllegalArgumentException("No currency found for code: " + code);
    }
}
