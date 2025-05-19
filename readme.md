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
repository and that the .env file contains these parameters:

```
MYSQL_DATABASE='learniverse'
MYSQL_PASSWORD='password'
MYSQL_ROOT_PASSWORD='verysecret'
MYSQL_USER='user1'

JWT_SECRET_KEY=SomeJWTSecretKeyThatIsVerySecureAndLong
USE_HTTPS=true
SSL_KEYSTORE_PATH=certs/local/keystore.p12
SSL_KEYSTORE_PASSWORD=changeit
SSL_KEY_ALIAS=springboot
````
A certificate is required for the application to run with HTTPS. The certificate is included in the repository
and is only intended for development purposes. It is not secure and should not be used in production.

Along with this, a SQL file is included in the repository. This file is used to create the database and tables
necessary
for normal operation of the application. The SQL file is located in root and is named `init.sql`. 

## Running the Application

- Clone the application using ssh: git@github.com:NTNUFrokostklubben/IDATA2306.git  
- Create a .env file in the root of the project and copy the parameters above into it.  


Open the root folder in a terminal and run the application using docker-compose:
```bash
  docker-compose up --build
```
It may be necessary to run this twice to ensure that the database is created.  
once the docker container is running properly, start a new terminal and
copy the `init.sql` file to the relevant container:
```bash
 docker cp init.sql $(docker ps -q --filter "name=learniverse-mysql"):/init.sql
```
once the file has been copied, enter the container and login to mysql:
```bash
  docker exec -it $(docker ps -q --filter "name=learniverse-mysql") bash
  mysql -u root -p
```
the password here is the MYSQL_ROOT_PASSWORD from the .env file.  

Once inside mysql, run:
```sql
    USE learniverse;
  SOURCE init.sql;
```
Assuming all changes went through with no errors, make sure to commit changes before leaving:
```sql
  COMMIT;
```
Now that the database is created, initialized and running, it is possible to see all exposed API endpoints using
swagger: https://localhost:8080/api/swagger-ui/index.html#/

Before authorized endpoints can be tested or used, it is necessary to generate a JWT token, which can be done by
logging into the default users created in the database. The default users are:
```
#admin
    username: Chuck@gmail.com
    password: Nunchucks2024
#user
    username: dave@gmail.com
    password: Dangerous2024
```
using the Authenticate endpoint, a JWT token can be generated. This token is then used to authorize all other endpoints
https://localhost:8080/api/swagger-ui/index.html#/authentication-controller/authenticate.