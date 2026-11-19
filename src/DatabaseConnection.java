import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {

    // Store the database in the user's Windows AppData folder
    private static final String LOCAL_APP_DATA =
        System.getenv("LOCALAPPDATA");

    private static final Path DATABASE_PATH =
        Path.of(
            LOCAL_APP_DATA != null
                ? LOCAL_APP_DATA
                : System.getProperty("user.home"),
            "MoneyTracker",
            "moneytracker.db"
        ).toAbsolutePath();
    
    private static final String URL = 
        "jdbc:sqlite:" + DATABASE_PATH;

    

private static void initializeDatabase(Connection connection)
        throws SQLException {

    // Load schema.sql from inside the JAR
    try (InputStream input =
            DatabaseConnection.class.getResourceAsStream("/schema.sql")) {

        if (input == null) {
            throw new SQLException(
                "schema.sql not found inside application JAR"
            );
        }

        String schema = new String(
            input.readAllBytes(),
            StandardCharsets.UTF_8
        );

        try (Statement statement = connection.createStatement()) {

            for (String command : schema.split(";")) {

                String sql = command.trim();

                if (!sql.isEmpty()) {
                    statement.execute(sql);
                }
            }
        }

        System.out.println("Database schema initialized!");

    } catch (IOException e) {
        throw new SQLException(
            "Failed to read database schema", e
        );
    }
}



 
    public static Connection getConnection() throws SQLException {

        try {
            Files.createDirectories(DATABASE_PATH.getParent());

        } catch (IOException e) {
            throw new SQLException(
                "Failed to create database directory", e
            );
        }

        Connection connection = DriverManager.getConnection(URL);

        try {
            initializeDatabase(connection);

            System.out.println(
                "SQLite database path: " + DATABASE_PATH
            );

            return connection;

        } catch (SQLException e) {
            try {
                connection.close();
            } catch (SQLException closeError) {
                e.addSuppressed(closeError);
            }
            throw e;
        }
    }

    public static void main(String[] args) {

        System.out.println("Connecting to SQLite Database...");

        try (Connection connection = getConnection()) {

            if (connection.isValid(5)) {

                System.out.println("-----------------------------");
                System.out.println("DATABASE CONNECTED!");
                System.out.println("-----------------------------");

                String sql = "SELECT COUNT(*) FROM transactions";

                try (PreparedStatement statement = connection.prepareStatement(sql);
                    ResultSet result = statement.executeQuery()) {
                        if (result.next()) {
                            System.out.println("found: " + result.getInt(1));
                        }
                    }
            }

        } catch (SQLException e) {

            System.out.println("DATABASE CONNECTION FAILED!");
            System.out.println("Reason: " + e.getMessage());
        }
    }
}



 
