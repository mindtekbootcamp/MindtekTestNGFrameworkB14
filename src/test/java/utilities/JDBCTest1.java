package utilities;

import java.sql.*;

public class JDBCTest1 {
    public static void main(String[] args) throws SQLException {
//        1 Connection
//        2 statement
//        3 resultSet
        Connection connection = DriverManager.getConnection(
                // URL, username, password
                "jdbc:postgresql://localhost:5432/HR Department",
                "postgres",
                "admin"
        );

        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("select * from countries");

        System.out.println(rs.toString());

        rs.next(); // point to the first row


        ResultSetMetaData rsMeta = rs.getMetaData();
        System.out.println(rsMeta.getColumnCount());
        System.out.println(rsMeta.getColumnName(1));
        System.out.println(rsMeta.getColumnName(2));
        System.out.println(rs.getString(rsMeta.getColumnName(2)));
        System.out.println("------------");

        for(int i = 1; i <= rsMeta.getColumnCount(); i++){
            System.out.println(rsMeta.getColumnName(i));
        }

        System.out.println("-------------------");

        while(rs.next()){
            System.out.println("********");
            for(int columnNumber = 1; columnNumber <= rsMeta.getColumnCount(); columnNumber++){
                String columName = rsMeta.getColumnName(columnNumber);
                System.out.println(columName + ": " + rs.getString(columName));
            }
            System.out.println("#########");

        }


    }
}
