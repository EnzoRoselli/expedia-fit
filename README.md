# Globant Fit Excercise

The excercise was to develop an application that give us available flights for a given search query. With an inventory of the following flights:

```
{
  "flights": [
    {
      "flight": "Air Canada 8099",
      "departure": "7:30AM"
    },
    {
      "flight": "United Airline 6115",
      "departure": "10:30AM"
    },
    {
      "flight": "WestJet 6456",
      "departure": "12:30PM"
    },
    {
      "flight": "Delta 3833",
      "departure": "3:00PM"
    }
  ]
}

```

## Stack: :computer:

- Java 11
- Spring Boot 2.5.0
- Maven 
- JUnit 5
- MySQL8
- Mockito
- MapStruct
- Swagger
- H2 for integration tests

## How to Use :pencil:

- You will need to create a local database in MySQL with the name you wish, then run the queries from **schema.sql**
- In **application.yml** you have the Environment Variables **DB_NAME**, **DB_USERNAME** and **DB_PASSWORD**
- Configure the Environment Variables in your IDE and then run the application.
- Open a browser a type localhost:8080/flights/?hour=hh&minutes=mm&time=a where *hh* is the hour, *mm* minutes, and *a* AM/PM. E.g. /flights/?hour=9&minutes=15&time=PM
- You should see some flights 5 hours before & after the time you wrote.
- At the root of the project a file named fitFlightApplication.log will be created which serves as the log for the application.
