# Envibe
[![Build Status](https://travis-ci.org/Nvibe/Envibe.svg?branch=master)](https://travis-ci.org/Nvibe/Envibe)

## How To Contribute
The `master` branch is protected. This means that you cannot directly push to the `master` branch. Instead, use the box above to create a new branch (for example: `fix-registration-form`). Once you have submitted your code to the branch, and your feature/fix is complete, sumbit a PR. GitHub will show you if you need to change anything before you merge.

Code documentation is available at https://nvibe.github.io/Envibe.

## Environment Info
*For minimal issues, try to get your dev environment as close as possible to this list*
- Ubuntu 18.04 Bionic (64-bit)
- OpenJDK Java 1.8.0 build 251
- Maven 3
- Redis 5.0.8

## Environment Variables
*Project may crash without these defined*
- `JDBC_DATABASE_URL` Defines the PostgreSQL username, password, URI, and database to use.
- `REDIS_URL` Defines the Redis username, password, URI, and database to use.

## Default Accounts
By default, the following users are created for testing purposes.

| Username | Email                | Password | Internal Role |
|----------|----------------------|----------|---------------|
| admin    | admin@example.com    | envibe   | ROLE_ADMIN    |
| listener | listener@example.com | envibe   | ROLE_USER     |
| artist   | artist@example.com   | envibe   | ROLE_USER     |

As of right now, the friends functionality is missing from the codebase. Anytime you create a post, only the `listener` account will be able to see it. (Essentially, `listener` is a permanent follower of the other two accounts).

## Basic Commands
- `mvnw spring-boot:run` Runs server locally.
- `mvnw clean package` Builds JAR file, rarely needed.
- `mvnw flyway:migrate` Updates the schema of your local PostgreSQL database (done automatically on bootup).
- `mvnw test` Runs unit test suite (Requires JUnit).
- `mvn javadoc:javadoc` Compiles HTML documentation site under `./target/site/apidocs`.

## How To Use H2
Instead of messing around with PostgreSQL, you can run an in-memory database for testing purposes.
1. Within your IDE, set the environment variable `JDBC_DATABASE_URL` to `jdbc:h2:mem:test_db`.
2. Every time you start the webapp, it will create a temporary database in memory that your application will use. The database will be deleted every time you close the webapp.

## How to set up Redis
This application requires Redis for cross-thread communication. You can install it in one of three ways.
- Install the Windows Subsystem for Linux (WSL) (Windows 10 version 2004 only).
- Install a non-supported build of Redis for Windows.
- In a Linux VM.

If you chose option 1 or 3, you will need to run `sudo apt install redis-server -y` before you continue.
1. Turn protected mode off by running `sudo nano /etc/redis/redis.conf`
2. Start the Redis service with `sudo service redis-server start`
3. In your IDE, set the environment variable `REDIS_URL` to `redis://127.0.0.1:6379`

When you are done, and don't want Redis running in the background, stop it with `sudo service redis-server stop`

## How To Update The Database
This project uses Flyway to manage database schemas. To change how the database is set up, create a migration.
1. Go to `/src/main/resources/db/migration`
2. Create a SQL file with the format `VX__Useful_Name_Here.sql`. Note that the filename MUST start with a capital `V`, followed by a number higher than any of the other files in the `/migration` folder(e.g. V1, V2, V3...). After `VX`, there should be TWO underscores(`__`).
3. Insert your SQL code into the file you just created.
4. Ensure that the environment variable `JDBC_DATABASE_URL` is defined and is a valid JDBC URI to a running instance of PostgreSQL.
5. From the project root, run `./mvnw flyway:migrate`. The database schema will be updated automatically.

## Project Links
- [Build logs](https://travis-ci.org/Nvibe/Envibe)
- [Live website](https://envibe.herokuapp.com/)

## Helpful Links
- [Getting Started With Spring Framework](https://spring.io/guides/gs/serving-web-content/)
- [Getting Started With Flyway Database Migrations](https://flywaydb.org/getstarted/firststeps/maven)
- [Setting up PostgreSQL (Windows 8+)](https://www.guru99.com/download-install-postgresql.html)
- [PostgreSQL Downloads](https://www.postgresql.org/download/windows/)

