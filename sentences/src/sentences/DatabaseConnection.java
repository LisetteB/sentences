package sentences;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;

public class DatabaseConnection{
	private List<Element> database;
	
	public DatabaseConnection() throws Exception{
		//Accessing driver from the JAR file
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		
		//creating a variable for the connection called con
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/testdb","root","java2016");
		// jdbc:mysql://localhost:3306/testdb
		// root is the user
		// root is the password
		
		if(con==null)   {
            System.out.println("connection failed");
        }
		
		setDatabase(con);			
	}
	
	public List<Element> getDatabase(){
		return database;
	}
	public void setDatabase(Connection con) throws SQLException{
		String sql = "select * from elements";
		PreparedStatement statement = con.prepareStatement(sql);
		//Creating a variable to execute query
		ResultSet result = statement.executeQuery();
		
		database = new ArrayList<Element>();
		while(result.next()){
			database.add(new Element(result.getString(1), new Type(result.getString(2))));
		}		
	}
	
	public static void insert(Connection con, Element e) throws SQLException{
		String sql = "insert into elements (word_sequence, type)" + " values (?,?)";
		PreparedStatement statement = con.prepareStatement(sql);
		
		statement.setString(1, e.getWordSequence());
		statement.setString(2, e.getType().typeComplete);	
		
		statement.execute();
	}

}
