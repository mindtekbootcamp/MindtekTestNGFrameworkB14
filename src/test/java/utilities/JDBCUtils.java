package utilities;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JDBCUtils {
    private static Connection connection;
    private static Statement statement;
    private static ResultSet resultSet;

    public static void connectToDB(String URL, String username, String password) throws SQLException {
        connection = DriverManager.getConnection(URL, username, password);
        statement = connection.createStatement();
    }

    public static List<Map<String, Object>> executeQuery(String query) throws SQLException {
        resultSet = statement.executeQuery(query);
        List<Map<String, Object>> data = new ArrayList<>();
        ResultSetMetaData rsMeta = resultSet.getMetaData();
        while(resultSet.next()){
            Map<String,Object> map = new HashMap<>();
            for(int i = 1; i <= rsMeta.getColumnCount(); i++){
                map.put(rsMeta.getColumnName(i), resultSet.getString(rsMeta.getColumnName(i)));
            }
            data.add(map);
        }
        return data;
    }

    public static void executeQueryInsert(String query) throws SQLException {
        statement.execute(query);
    }

    public static void executeQueryDelete(String query) throws SQLException {
        statement.execute(query);
    }

    public static void executeQueryUpdate(String query) throws SQLException {
        statement.execute(query);
    }

    public static void closeDBConnection() throws SQLException {
        if(connection != null) connection.close();
        if(statement != null) statement.close();
        if(resultSet != null) resultSet.close();
    }
}
