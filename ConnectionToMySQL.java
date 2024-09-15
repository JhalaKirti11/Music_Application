package com.jukebox.app.MusicApp;
import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionClassForDM {
    	static Connection con; // Global Connection Object
	public static Connection getConnection(){
		try {
			String mysqlJDBCDriver = "com.mysql.cj.jdbc.Driver"; //jdbc driver
			String url = "jdbc:mysql://localhost:3306/MusicApp";
			String user = "root";
			String pass = "1234";
			Class.forName(mysqlJDBCDriver);
			con = DriverManager.getConnection(url, user, pass);
		}
		catch (Exception e) {
			System.out.println("Connection Failed!");
		}
		return con;
	}
}
