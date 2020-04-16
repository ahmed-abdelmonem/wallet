# WALLET MICROSERVICE 

Wallet microservice is a simple wallet running on the JVM that manages credit/debit transactions on behalf of players.

## Description

A REST API that fulfil the requirements detailed below:

Current balance per player

Debit /withdraw per player: A debit transaction will only succeed if there are sufficient funds on the account (balance - debit amount >= 0). The caller will supply a transaction number that must be unique for all transactions. If the transaction number is not unique, the operation must fail.

Credit /deposit per player. The caller will supply a transaction number that must be unique for all transactions. If the transaction number is not unique, the operation must fail.

Transaction history per player sorted by creation date and support pagination.

## Running

1. Java 8 and Maven 3 should be installed.

2. Change directory to the root folder of the application.

3. Run the below maven command to generate the JAR file.

```bash
mvn clean package
```

4. Run the JAR file

```bash
java -jar target/wallet-0.0.1-SNAPSHOT.jar
```

5. Go to the below URL - the URL will be redirected to Swagger UI.

```bash
http://localhost:8080
```

6. To reset the database with resources/data.sql script - delete the data folder and run the JAR file with the below argument

```bash
java -jar target/wallet-0.0.1-SNAPSHOT.jar --spring.datasource.initialization-mode=embedded
```

## Endpoints

1. Get player balance (/wallet/{playerId}/balance) - HTTP GET

- Sample Request

```
http://localhost:8080/wallet/5/balance
```

- Sample Response

```
{
  "playerId": 5,
  "balance": 625
}
```

2. Add Debit transaction (/wallet/{playerId}/withdraw) - HTTP POST

- Sample Request - body

```
transactionNumber:RE66FGH33
amount:30
```

- Sample Response

```
{
    "number": "RE66FGH33",
    "playerId": 4,
    "type": "DEBIT",
    "amount": 30,
    "previousBalance": 50,
    "newBalance": 20,
    "dateCreated": "2020-04-15T17:31:00.711+02:00"
}
```

3. Add Credit transaction (/wallet/{playerId}/deposit) - HTTP POST

- Sample Request - body

```bash
transactionNumber:RE66FGH68
amount:50
```

- Sample Response

```
{
    "number": "RE66FGH68",
    "playerId": 4,
    "type": "CREDIT",
    "amount": 50,
    "previousBalance": 0,
    "newBalance": 50,
    "dateCreated": "2020-04-15T17:28:50.258+02:00"
}
```

4. Get player transactions (/wallet/{playerId}/transactions) - HTTP GET

- Sample Request

```
http://localhost:8080/wallet/5/transactions?pageNo=0&&pageSize=3
```

- Sample Response

```
[
  {
    "number": "ddf25234556655",
    "playerId": 5,
    "type": "CREDIT",
    "amount": 66,
    "previousBalance": 559,
    "newBalance": 625,
    "dateCreated": "2020-04-15T00:37:39.004+02:00"
  },
  {
    "number": "ddf25546666",
    "playerId": 5,
    "type": "DEBIT",
    "amount": 13,
    "previousBalance": 572,
    "newBalance": 559,
    "dateCreated": "2020-04-15T00:37:39.004+02:00"
  },
  {
    "number": "ddf2536fjfbjf233",
    "playerId": 5,
    "type": "CREDIT",
    "amount": 2,
    "previousBalance": 570,
    "newBalance": 572,
    "dateCreated": "2020-04-15T00:37:39.003+02:00"
  }
]
```

## Technologies

1. Java 8

2. Spring boot with Spring data JPA and Hibernate.

3. Maven 3

4. H2 database

5. Junit 4

6. Swagger UI

7. SonarLint

## Design And Assumptions

1. There is only one wallet for each player.

2. Cash balance stored in Player table.

3. Each transaction has a reference (foreign key) for the player.

4. Transfer Object Pattern (DTO) is used to return data from endpoints.

5. Data Access Object (DAO) pattern is used to isolate the business layer from the persistence layer.

6. Optimistic Locking with JPA and Hibernate is used to solve the Concurrency issue while updating the player balance.

7. 11 players are created with ID (1 to 11) for testing in initiating DB script resources/data.sql. To update the players edit the script and remove data folder from the root directory and run again with argument  --spring.datasource.initialization-mode=embedded 

8. Endpoints are tested using Swagger UI and Postman also with Junit testing with MockMvc.

## Changes needed for the API be production-ready

1. The database used in this application is H2 in file to test it only and to persist data across restarts. For production the database should be changed in application.properties file to a good relational database which support ACID compliance.

2. The endpoints should be secured by authentication and data encrypted by SSL by adding a web server layer (API gateway, NGINX..) or secure the connections to the Tomcat server using SSL and secure the endpoints using Spring Security (Not recommended). secured_endpoints branch contains a simple way of securing the endpoints using SSL and HTTP basic authentication (Not recommended in production environment just for POC).     

## secured_endpoints Branch

As an example to secure the endpoints using simple tools. The endpoints in this Git branch are secured using spring security HTTP basic authentication and all connections to the server are encrypted using SSL (a new self-signed SSL certificate is created and added to the tomcat embedded server).

```
Admin user: (admin / adminPs) for GET, POST

Normal user: (user / userPs) for GET method only.
```

```
Access the URL https://localhost:8443
```