package Util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class DbAccess {
	
	//static String dbUrl= "jdbc:<mysql>://localhost:3306/JTA_CKB90";
	//Connection URL Syntax: "jdbc:mysql://ipaddress:portnumber/db_name"
	static String dbUrl= "jdbc:mysql://192.168.0.28:3306/jawedschema";
	static String userName = "jawed";
	static String Password= "mysql123$";
	 
	/**
	 * @param query
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public static String  getDBData(String query) throws SQLException, ClassNotFoundException 
	{
	//Load mysql jdbc driver
	Class.forName("com.mysql.jdbc.Driver");	
	//Create Connection to DB	
	Connection con = DriverManager.getConnection(dbUrl, userName, Password);
	//Create Statement Object	
	Statement stmt = con.createStatement();
	// Execute the SQL Query. Store results in ResultSet
	ResultSet rs=stmt.executeQuery(query);
	String myName=null;
	while (rs.next()){
		myName = rs.getString(1);								        
        	}	
	
	con.close();
	return myName;
	}


}
