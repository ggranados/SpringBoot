# overactive_assessment

Back End Java Developer

Homework Problem

All candidates who are a fit for this role will be required to do a homework problem before any
interviews. This will be the basis for the first client interview. Your solution must be coded in
Java and use Spring Boot.


A retailer offers a rewards program to its customers, awarding points based on each recorded
purchase.


A customer receives 2 points for every dollar spent over $100 in each transaction, plus 1 point
for every dollar spent over $50 in each transaction
(e.g. a $120 purchase = 2x$20 + 1x$50 = 90 points).


Given a record of every transaction during a three month period, calculate the reward points
earned for each customer per month and total.


● Code your solution in Java and Spring

● Make up a data set to best demonstrate your solution

● Include a readme

● Check solution into GitHub
~~~~

# Read me
Build with Java 11, SpringBoot 2.5.4, H2 in memory DB, JPA, Lombok and JUnit

Base set of transaction have been added, which can be requested by the endpoint:

GET http://localhost:8080/api/v1/reward-points/transactions/all

Returning a list of transaction response, identified by a unique id and containing information of client, amount , date of transacrion and an applicable flag (which indicates a valid status of the transaction established by inner transaction domain conditions)
  {
    "id": 2003,
    "clientId": "CLI002",
    "amount": 150.00,
    "date": "2020-07-01T04:00:00.000+00:00",
    "applicable": true
  },


Simulating 3 operations for 3 months by 2 clients : CLI001, CLI002 and CLI003

Three endpoints where added:

GET http://localhost:8080/api/v1/reward-points/clients/all
Which returns the total points calculation for applicable transactions by client

GET http://localhost:8080/api/v1/reward-points/{{clientId}}/total
Returns total calculated point of valid transactions by the client specifeid with ID parameter

GET http://localhost:8080/api/v1/reward-points/{{clientId}}/monthly
Returns total calculated point of valid transactions by month, by the client specifeid with ID parameter

Review API documented in swagger.yml with https://editor.swagger.io/ or IntelliJ
