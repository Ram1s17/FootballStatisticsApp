package connection;

import java.sql.*;

public class DatabaseConnection {
    private static Connection connection;
    private static Statement statement;

    public static void connectToDB() {
        String connectionUrl =
                "jdbc:sqlserver://127.0.0.1:1433;"
                        + "database=FootballStatisticsDB;"
                        + "user=sa;"
                        + "password=RMubarakov17062002;"
                        + "encrypt=false;"
                        + "trustServerCertificate=false;"
                        + "loginTimeout=10;";
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            connection = DriverManager.getConnection(connectionUrl);
            statement = connection.createStatement();
            System.out.println("Успешное подключение к базе данных!");
        } catch (SQLException throwables) {
            System.out.println("Не удалось подключиться к базе данных!");
            throwables.printStackTrace();
        }
    }

    public static Connection getConnection() {
        return connection;
    }

    public static Statement getStatement() {
        return statement;
    }
}
