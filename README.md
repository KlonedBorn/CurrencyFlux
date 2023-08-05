<p align="center">
  <a href="" rel="noopener">
 <img width=200px height=200px src="./src/main/resources/com/kloneborn/exchange-rate.png" alt="Project logo"></a>
</p>

<h3 align="center">CurrencyFlux</h3>

<div align="center">

[![Status](https://img.shields.io/badge/status-active-success.svg)]()
[![GitHub Issues](https://img.shields.io/github/issues/kylelobo/The-Documentation-Compendium.svg)](https://github.com/kylelobo/The-Documentation-Compendium/issues)
[![GitHub Pull Requests](https://img.shields.io/github/issues-pr/kylelobo/The-Documentation-Compendium.svg)](https://github.com/kylelobo/The-Documentation-Compendium/pulls)
[![License](https://img.shields.io/badge/license-MIT-blue.svg)](/LICENSE)

</div>

---

<p align="center"> A simple GUI application for converting international, well-known currencies. 
    <br> 
</p>

## 📝 Table of Contents

- [📝 Table of Contents](#-table-of-contents)
- [🧐 About ](#-about-)
- [🏁 Getting Started ](#-getting-started-)
  - [⚠️ Prerequisites](#️-prerequisites)
- [🔧 Deployment ](#-deployment-)
- [⛏️ Built Using](#️-built-using)
- [🎉 Acknowledgements](#-acknowledgements)
- [🎈 Usage ](#-usage-)
- [🚀 Deployment ](#-deployment--1)
- [⛏️ Built Using ](#️-built-using-)
- [✍️ Authors ](#️-authors-)
- [🎉 Acknowledgements ](#-acknowledgements-)

## 🧐 About <a name = "about"></a>

Greetings! I'm Kloneborn, an aspiring Java developer, driven to master the Java ecosystem and craft useful and reliable products for all customers.

Now, imagine this: a spark ignited within me when the need arose to convert XCD to USD. It occurred to me that I should put my skills to the test and try my hand at creating a currency converter. Instead of relying on the myriad of online converters, I opted to challenge myself and build my own!

While I acknowledge that this application might harbor a few bugs, I take immense pride in having created a functional tool that's accessible to almost anyone.So, why not give this straightforward app a shot? Try it out and experience firsthand the result of my dedication and innovation! Your feedback matters greatly, as it fuels my motivation to continuously refine my craft.


## 🏁 Getting Started <a name = "getting_started"></a>

To get this project up and running on your local machine, follow these steps:

1. Clone the Git repository:
    ```sh
    git clone https://github.com/KlonedBorn/CurrencyFlux.git
    ```
   
2. Navigate to the project directory:
    ```sh
    cd CurrencyFlux
    ```

3. Obtain an API key from [currencyapi.com](https://currencyapi.com/):

    - Create an account on [currencyapi.com](https://currencyapi.com/).
    - Obtain an API key from your personal dashboard.

4. Set the API key in the project:

    - Navigate to the `resources/config` directory in the project.
    - Open the `apikey.json` file.
    - Replace `"YOUR_API_KEY"` with the API key you obtained from [currencyapi.com](https://currencyapi.com/).

5. Install Maven dependencies:

    Make sure you have [Maven](https://maven.apache.org/) installed on your system.
    
    ```sh
    mvn install
    ```

6. Run the application:

    ```sh
    mvn javafx:run
    ```

You should see the application starting up and the `App.main` class being executed.

> Note: This assumes you have JDK 11 or later installed as well.

### ⚠️ Prerequisites

Make sure you have the following installed on your system:

- [Git](https://git-scm.com/)
- [Maven](https://maven.apache.org/)
- [JDK 11 or later](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)

## 🔧 Deployment <a name = "deployment"></a>

Add notes about how to deploy this project on a live system.

## ⛏️ Built Using

- [Gson](https://github.com/google/gson) - JSON library for Java
- [JavaFX](https://openjfx.io/) - Java library for building desktop applications

## 🎉 Acknowledgements

- [javafx-maven-plugin](https://github.com/openjfx/javafx-maven-plugin) - JavaFX Maven Plugin for packaging JavaFX applications

---

Project generated from the following `pom.xml`:

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd">
    <!-- ... (the rest of your pom.xml content) ... -->
</project>


## 🔧 Running the tests <a name = "tests"></a>

Explain how to run the automated tests for this system.

### Break down into end to end tests

Explain what these tests test and why

```
Give an example
```

### And coding style tests

The following code tests to see if a connection can be made to the api. Additionally, the method also fails if the API key is invalid.

```java
public boolean isConnectionAvailable() {
    try {
        URL statusUrl = URI.create(this.base_url + "v3/status" + "?apikey=" + this.api_key).toURL();
        HttpURLConnection connection = (HttpURLConnection) statusUrl.openConnection();
        connection.setRequestMethod("HEAD"); // Use HEAD method for a lightweight check
        
        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            logEvent("Connection established", false);
        } else {
            logEvent("Connection failed", true);
        }
        
        return (responseCode == HttpURLConnection.HTTP_OK);
    } catch (IOException e) {
        return false; // Connection cannot be established
    }
}

```

## 🎈 Usage <a name="usage"></a>

The system uses the *HttpUrlConnection* interface to connect to API hook and retrieve data.

Here's an example of how the interface works:
```java
URL statusUrl = URI.create(this.base_url + "v3/status" + "?apikey=" + this.api_key).toURL();
HttpURLConnection connection = (HttpURLConnection) statusUrl.openConnection();
connection.setRequestMethod("HEAD"); // Use HEAD method for a lightweight check
int responseCode = connection.getResponseCode();
if (responseCode == HttpURLConnection.HTTP_OK) {
    logEvent("Connection established", false);
}

```
The code above builds the url then creates a connection to host (currencyapi)

## 🚀 Deployment <a name = "deployment"></a>

<p style="color:red;">WIP</p>
<!-- Add additional notes about how to deploy this on a live system. -->

## ⛏️ Built Using <a name = "built_using"></a>

- [JavaFX](https://openjfx.io/) - UI Framework
- [CurrencyApi](https://currencyapi.com/) - Currency Exchange Database
- [Maven](https://maven.apache.org/) - Package Manager
- [Vscode](https://code.visualstudio.com/) - Code Editor

## ✍️ Authors <a name = "authors"></a>

- [@KloneBorn](https://github.com/KloneBorn) - Idea & Implementation

## 🎉 Acknowledgements <a name = "acknowledgement"></a>

- Hat tip to anyone whose code was used
- Appreciation goes to the creator of chat GPT for providing me a quick of generating code.
- Thanks to *currencyapi* for providing api to get currency exchanges.
