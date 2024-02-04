# GoHighLevel

## Pre-requisites
- Java 21
- Maven 3.8.1
- Google Chrome (tested on version v121)
- Google Chrome for Testing (tested on version v121)
- Google ChromeDriver (tested on version v121)
- Set JAVA_HOME to set to the JDK 21

## Getting the configs
Download the configs from the email sent separately

## How to run the tests
- Clone the repository
- Open the terminal and navigate to the project folder

## For Assignment 1
- java -jar GoHighLevel-assignment1-jar-with-dependencies.jar {path-to-credentials-file}

e.g.  java -jar GoHighLevel-assignment1-jar-with-dependencies.jar ~/Documents/credentials.properties

## For Assignment 2
- java -jar GoHighLevel-assignment2-jar-with-dependencies.jar {path-to-transaction-logs-file} {month-and-year-to-find-charge-for-separated-by-space}

e.g.  java -jar GoHighLevel-assignment2-jar-with-dependencies.jar ~/Documents/transaction-logs.csv "Jan 2023"
