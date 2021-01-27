The CCN Roadshow(Dev Track) Module 4 Labs 2019
===
These labs provide a simple shopping cart application built using microservices architecture.
The included Java projects and/or installation files are here:

* Catalog Service - A Spring boot application running on JBoss Web Server (Tomcat) and PostgreSQL, serves products and prices for retail products
* Cart Service - Quarkus application running on OpenJDK and native which manages shopping cart for each customer, together with inifnispan/JDG
* Inventory Service - Quarkus application running on OpenJDK and PostgreSQL, serves inventory and availability data for retail products
* Order service  - Quarkus application service running on OpenJDK or native for writing and displaying reviews for products
* Payment Service  - A Quarkus based application
* Coolstore UI - An Angular application running on Node.js used as a frontend for accessing the shopping cart application.
