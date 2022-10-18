# Getting Started

### Run the application

To run the application using docker use the following command from the directory root

```
docker-compose up
```
The above command will build the api image, run the unit tests, spin up postgres db and start the API. The api will also create the db tables using flyway on first startup.

Note: To run locally without docker first build the application using JDK 11 and change the application.properties file to set the db url.

### API endpoints

#### 1. Add stock data

To add stock data use the following request

```
POST: http://localhost:8080/stocks

{
    "ticker": "ABC",
    "quarter": "1",
    "date": "1/7/2011",
    "open": "$15.82",
    "close": "$16.42",
    "high": "$16.72",
    "low": "$15.78",
    "volume": "239655616",
    "percentChangePrice": "3.79267",
    "previousWeekVolume": 239655616,
    "percentChangeVolumeOverLastWeek": 1.380223028,
    "nextWeekOpen": "16.71",
    "nextWeekClose": "15.97",
    "percentChangeNextWeekPrice": "-4.42849",
    "daysToNextDividend": "26",
    "percentReturnNextDividend": "0.182704"
}

```

This will return 200 OK response along with the inserted data.

#### 2. Bulk insert stock data

To bulk insert you can use the sample file in the folder /src/test/resources/dow_jones_index.data
It is downloaded from the URL http://archive.ics.uci.edu/ml/datasets/Dow+Jones+Index#

```
POST : http://localhost:8080/stocks/upload

form-data

key: file
value : <file to upload>
```
It will return 200 OK response if successful.

#### 3. Query stock data

```
GET: http://localhost:8080/stocks/AA

Sample response:

[
    {
        "id": 1,
        "ticker": "AA",
        "quarter": 1,
        "date": "2011-01-07",
        "open": 15.82,
        "close": 16.42,
        "high": 16.72,
        "low": 15.78,
        "volume": 239655616,
        "percentChangePrice": 3.79267,
        "previousWeekVolume": null,
        "percentChangeVolumeOverLastWeek": null,
        "nextWeekOpen": 16.71,
        "nextWeekClose": 15.97,
        "percentChangeNextWeekPrice": -4.42849,
        "daysToNextDividend": 26,
        "percentReturnNextDividend": 0.182704
    } 
  ]
```