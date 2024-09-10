# Java Connection Pool Project

This project demonstrates a simple implementation of a database connection pool in Java using Maven.

## Project Structure

- `src/main/java/org/example/ConnectionPool.java`: Contains the `ConnectionPool` class which manages a pool of database connections.
- `pom.xml`: Maven configuration file for managing project dependencies.

## Prerequisites

- Java 8 or higher
- Maven
- MySQL database

## Setup

1. **Clone the repository**:
    ```sh
    git clone https://github.com/shashankqv/java-work.git
    cd java-work
    ```

2. **Configure the database**:
    - Update the database URL, username, and password in `ConnectionPool.java` to match your MySQL setup.

3. **Build the project**:
    ```sh
    mvn clean install
    ```

## Usage

1. **Run the application**:
    ```sh
    mvn exec:java -Dexec.mainClass="org.example.ConnectionPool"
    ```

2. **Expected Output**:
    ```
    Obtained connection from pool 1.
    Obtained connection from pool 2.
    Released connection back to pool 1.
    Released connection back to pool 2.
    ```

## Dependencies

- **MySQL JDBC Driver**: The project uses the MySQL JDBC driver to connect to the MySQL database. The dependency is defined in the `pom.xml` file.