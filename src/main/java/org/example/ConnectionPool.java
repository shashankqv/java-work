package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ConnectionPool {
    private final String dbUrl;
    private final String user;
    private final String pass;
    private final int poolSize;

    private final List<Connection> connectionPool;
    private final List<Connection> usedConnections = new ArrayList<>();

    // Constructor to initialise the connection pool with custom configurations
    public ConnectionPool(String dbUrl, String user, String pass, int poolSize) throws SQLException {
        this.dbUrl = dbUrl;
        this.user = user;
        this.pass = pass;
        this.poolSize = poolSize;
        this.connectionPool = new ArrayList<>(poolSize);

        // Initialize the pool.
        for (int i = 0; i < poolSize; i++) {
            connectionPool.add(createConnection());
        }
    }

    // Synchronized method to get a connection from the pool
    public synchronized Connection getConnection() {
        if (connectionPool.isEmpty()) {
            // TODO - handle this better.
            System.out.println("All connections are in use.");
            return null;
        }
        Connection connection = connectionPool.remove(connectionPool.size() - 1);
        usedConnections.add(connection);
        return connection;
    }

    // Synchronized method to release a connection back to the pool
    public synchronized boolean releaseConnection(Connection connection) {
        if (connection != null) {
            usedConnections.remove(connection);
            connectionPool.add(connection);
            return true;
        }
        return false;
    }

    // Synchronized method to get the total size of the pool
    public synchronized int getPoolSize() {
        return connectionPool.size() + usedConnections.size();
    }

    // method to get the size of the used connections
    public synchronized int getUsedConnectionsSize() {
        return usedConnections.size();
    }

    // Private method to create a new connection
    private Connection createConnection() throws SQLException {
        return DriverManager.getConnection(dbUrl, user, pass);
    }

    public static void main(String[] args) {
        try {
            // Create the first connection pool.
            ConnectionPool pool1 = new ConnectionPool(
                    "jdbc:mysql://localhost:3306/database1",
                    "username",
                    "password",
                    5
            );

            // Create the second connection pool.
            ConnectionPool pool2 = new ConnectionPool(
                    "jdbc:mysql://localhost:3306/database2",
                    "username",
                    "password",
                    5
            );
            Connection conn1 = pool1.getConnection();
            System.out.println("Obtained connection from pool 1.");

            Connection conn2 = pool2.getConnection();
            System.out.println("Obtained connection from pool 2.");

            System.out.println("Pool 1 size: " + pool1.getPoolSize());
            System.out.println("Pool 2 size: " + pool2.getPoolSize());

            System.out.println("Used connections from pool 1: " + pool1.getUsedConnectionsSize());
            System.out.println("Used connections from pool 2: " + pool2.getUsedConnectionsSize());

            pool1.releaseConnection(conn1);
            System.out.println("Released connection back to pool 1.");

            pool2.releaseConnection(conn2);
            System.out.println("Released connection back to pool 2.");

            System.out.println("Used connections from pool 1: " + pool1.getUsedConnectionsSize());
            System.out.println("Used connections from pool 2: " + pool2.getUsedConnectionsSize());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

