# Learniverse application backend.

**Contributors**:
- Signe B. Ekern <br>
- Matthew R. Hunt
- August D. Oksavik

This repository contains the backend code for our Learniverse application.

The application is built using Spring boot for JAVA, and it is designed to be a RESTful API. We use NGINX to serve
images to the frontend, and we have a mySQL database to store user and course data. JPA is used to connect the
application to the database. The application is designed to be deployed with Docker, and we have a Dockerfile and 
docker-compose file to facilitate this.

## Getting Started
To get started with the application, you will need to have docker installed on your machine, such as docker desktop.
For correct connections of the application to the database, ensure that a .env file is present in your downloaded
repository and that the .env file contains these parameters, adapted to fit your local environment:

```
MYSQL_DATABASE='learniverse'
MYSQL_PASSWORD='password'
MYSQL_ROOT_PASSWORD='verysecret'
MYSQL_USER='user1'
```
These parameters do not need to be adapted and must be copied directly into your .env file:
```
JWT_SECRET_KEY=SomeJWTSecretKeyThatIsVerySecureAndLong
USE_HTTPS=true
SSL_KEYSTORE_PATH=certs/local/keystore.p12
SSL_KEYSTORE_PASSWORD=changeit
SSL_KEY_ALIAS=springboot
````
A certificate is required for the application to run with HTTPS. The certificate is included in the repository
and is only intended for development purposes. It is not secure and should not be used in production.

Along with this, a SQL file is included in the repository. This file is used to create the database and tables necessary
for normal operation of the application. The SQL file is located in root and is named `init.sql`. 