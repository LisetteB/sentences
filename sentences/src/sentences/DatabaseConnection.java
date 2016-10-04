package sentences;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Driver;

import java.sql.PreparedStatement;

public class DatabaseConnection{
	private List<Element> database;
	private Connection con;
	
	@SuppressWarnings("rawtypes")
	public DatabaseConnection() {
		String driverLocation = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/";
		String user = "root";
		String password = "java2016";
		String dbName = "testdb";
		
		
		try{
			// Load the JDBC driver
            Class driver_class = Class.forName(driverLocation);
            Driver driver = (Driver) driver_class.newInstance();
            DriverManager.registerDriver(driver);
            con = DriverManager.getConnection(url + dbName, user, password);
            
            setDatabase(con);
            System.out.println("From databaseConnection: database is now: " + database	);
		}catch(ClassNotFoundException e){
			e.printStackTrace();
			System.out.println("Class was not found, from databaseConnection constructor, and the defaultdatabase was set." );
			setDefaultDatabase();			
		}catch(SQLException e){
			e.printStackTrace();
			setDefaultDatabase();
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("in databaseConnection another exception was thrown and the defaultdatabase was set");
			setDefaultDatabase();
		}
//		
//		//Accessing driver from the JAR file
//		Class.forName("com.mysql.jdbc.Driver").newInstance();
//		
//		//creating a variable for the connection called con
//		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/testdb","root","java2016");
//		// jdbc:mysql://localhost:3306/testdb
//		// root is the user
//		// java2016 is the password
//		
//		if(con==null)   {
//            System.out.println("connection failed");
//        }
//		
					
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
	
	public void setDefaultDatabase(){
		database = new ArrayList<Element>();
		database.add(new Element("chat", new Type("n")));
		database.add(new Element("le", new Type("np/n")));
		database.add(new Element("chien", new Type("n")));
		database.add(new Element("dort", new Type("np\\s")));
		database.add(new Element("jean", new Type("np")));
		database.add(new Element("mange", new Type("(np\\s)/np")));	
	}
	
	public static void insert(Connection con, Element e) throws SQLException{
		String sql = "insert into elements (word_sequence, type)" + " values (?,?)";
		PreparedStatement statement = con.prepareStatement(sql);
		
		statement.setString(1, e.getWordSequence());
		statement.setString(2, e.getType().typeComplete);	
		
		statement.execute();
	}

}
