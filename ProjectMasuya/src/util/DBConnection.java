/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author yiachmad
 */
public class DBConnection {
//    private static final String URL = "jdbc:sqlserver://FA-22060015-1-1\\SQLEXPRESS:1433;databaseName=MasuyaDB";
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=MasuyaDB;encrypt=true;trustServerCertificate=true;";
    private static final String USER = "sa";
    private static final String PASSWORD = "sa";

    public static Connection getConnection() throws SQLException {
        DriverManager.registerDriver(new com.microsoft.sqlserver.jdbc.SQLServerDriver());
        
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
