[![forthebadge](https://forthebadge.com/images/badges/license-mit.svg)](https://forthebadge.com)
[![forthebadge](https://forthebadge.com/images/badges/made-with-java.png)](https://forthebadge.com)
[![forthebadge](https://forthebadge.com/images/badges/uses-git.png)](https://forthebadge.com)

# Chatop Rental App
Chatop Rental App is a REST backend Application for helping house owner to connect to customers.

## 🔥Features

- Register as new user & secured Login
- Create rentals, update rentals & browse existing rentals.
- Post messages to house owner for a rental.

# Requirements

- [Java jdk](https://nodejs.org/en) >= v. 17.
- [MariaDB Database](https://github.com/angular/angular-cli) v. 11.2.2

## Technologies

- Java jdk v. 17
- Gradle v. 8.5
- Spring boot v. 3.2.1
- Spring Security & OAuth2 Resource Server
- Spring Data JPA
- Spring Validation
- Modelmapper v. 3.1.1
- Springdoc openapi v. 2.3.0
- Project lombok v. 1.18.30
- MariaDB JDBC driver

# Contribute to the project

Chatop Rental App is an open source project. Feel free to fork the source and contribute with your own features.

# Authors

Coder : Patrice

# Licensing

This project was built under the MIT licence.

# 🧬 Installing and running

## Running locally for development

To install locally, you must first clone the repository.
```shell
git clone https://github.com/Picrate/Developpez-le-back-end-en-utilisant-Java-et-Spring.git
```

### Install MariaDB server

If MariaDB server is not installed in your computer follow these installation instructions:

https://mariadb.org/wp-content/uploads/2024/01/MariaDBServerKnowledgeBase.pdf

#### Create Database, MariaDB user & configure Grant Access for user
First we connect to MariaDB;
- From shell
```shell
mariadb -p
Enter password : 
```
- Then create an empty database
```SQL
CREATE DATABASE IF NOT EXISTS 'chatop';
```
- Create a database user (choose you own)
```SQL
CREATE USER 'dbuser'@'*' IDENTIFIED BY 'should_be_changed';
```
- Grant all privileges to dbuser on database chatop
```SQL
GRANT ALL PRIVILEGES ON chatop.* TO 'dbuser'@'%';
```
- Exit MariaDB
```SQL
exit;
```
#### Execute schema.sql
You will find in the root of the repository a directory named `SQL` which contain the database schema creation script: `schema.sql`.
For creating database schema and 'USER' Role which is required, use the following command in a terminal:
```shell
mariadb -u dbuser -p < schema.sql
```
Your database is ready.

### Install dependencies

```bash
gradlew build
```
This will install all necessary modules for running this application.

### Running application

For running the application, you need to provide some variables with values according to you environment.

Ex with environment variables in Linux Bash:
```shell
export DB_HOST=localhost;
export DB_NAME=chatop;
export DB_PASSWORD="";
export DB_PORT=3306;
export DB_USER="";
export JWT_SECRET_KEY="";
```
JWT Secret key is a randomly generated key.
You can use https://randomkeygen.com/ and its `CodeIgniter Encryption Keys` to help you to generate a such a 256-bit key;

Then:

```bash
gradlew bootRun
```
This will start a development server on your local machine.
Application server is now accessible to `http://localhost:3001/`.

You can use a tool like https://www.postman.com/ to query endpoints listed in swagger documentation: http://localhost:3001/swagger-ui.html

## To build production files
Run:
```bash
gradle build
```
The build artifacts will be stored in the `build/libs` directory.

### externalize variables for production use
You can externalize application.properties file outside the jar.
- Copy application.properties in a directory `config` at the root directory containing the jar.
- Replace variables names used for development with the production values.
- Add JWT_SECRET_KEY property.

Launch jar with:
```shell
java -jar chatop-version.jar
```
## Accessing to OpenApi documentation
Simply go to http://localhost:3001/swagger-ui.html

# 🤝 Contributors

This project would not be possible without our amazing contributors and the community.
