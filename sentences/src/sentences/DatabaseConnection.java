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
	private String possibleInsert;
	
	String driverLocation = "com.mysql.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3306/";
	String user = "root";
	String password = "java2016";
	String dbName = "testdb";	
	
	public String getPossibleInsert(){
		return this.possibleInsert;
	}
	
	/*
	 * createConnection does the following
	 * If it is possible: create a Connection with reference con and set the database
	 * If it is not possible return null for connection and set the default database
	 */
	@SuppressWarnings("rawtypes")
	public Connection createConnection(){
		Connection con = null;
		try{
			// Load the JDBC driver
            Class driver_class = Class.forName(driverLocation);
            Driver driver = (Driver) driver_class.newInstance();
            DriverManager.registerDriver(driver);
            con = DriverManager.getConnection(url + dbName, user, password);
            
            setDatabase(con);
            return con;
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
		return con;
	}
	
	public List<Element> getDatabase(){
		try{
			//createConnection also sets the database
			Connection con = createConnection();
			
			con.close();
		}catch(SQLException e){
			e.printStackTrace();
		}
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
	
	/*
	 * insert is an overloaded method to add Element objects to the database, 
	 * or to add just a word with its type. 
	 */
//	public void insert(Element e){
//		try{
//			Connection con = createConnection();
//			
//			String sql = "insert into elements (word_sequence, type)" + " values (?,?)";
//			PreparedStatement statement = con.prepareStatement(sql);
//			
//			
//			statement.setString(1, e.getWordSequence());
//			statement.setString(2, e.getType().typeComplete);				
//			statement.execute();	
//			
//			setDatabase(con);
//			con.close();
//		}catch(SQLException sqle){
//			sqle.printStackTrace();
//		}
//		
//	}
	public void insert(String wordSequence, String type){
		//Use the value of possibleInsert to tell if it is not possible to insert the element
		possibleInsert = "";
		try{
			Connection con = createConnection();
			
			//first check if the element is included
//			String sqlIncluded = "SELECT count(*) FROM elements WHERE word_sequence = ?";
//			PreparedStatement statement = con.prepareStatement(sqlIncluded);
//			statement.setString(1,wordSequence);
//			ResultSet resultSet = statement.executeQuery();
//			boolean isIncluded = resultSet.next();
//			System.out.println(isIncluded);
//			while(resultSet.next()){
//				System.out.println("found another " + wordSequence);
//			}
			
			String sqlIncluded = "select * from elements";
			PreparedStatement statement = con.prepareStatement(sqlIncluded);
			ResultSet result = statement.executeQuery();
			boolean isIncluded = false;
			while(result.next()){
				if(result.getString(1).equals(wordSequence)){
					isIncluded = true;
					break;
				}
			}

			//If the word is not included and if the word has a valid type, you can add it.
			if(isIncluded){
				possibleInsert += "The database already contains the word "+ wordSequence;
			}else if(!new Type(type).correctType()){
				possibleInsert += "This type does not exist "+ type;
			}else{

				String sql = "insert into elements (word_sequence, type)" + " values (?,?)";
				statement = con.prepareStatement(sql);
				
				statement.setString(1, wordSequence);
				statement.setString(2, type);				
				statement.execute();	
				
				setDatabase(con);
				possibleInsert += "well done, you've just added the word "+ wordSequence + " to the database."; 
			}
			
			con.close();
		}catch(SQLException sqle){
			sqle.printStackTrace();
		}		
	}
	
	public static void main(String[] args){
				
		DatabaseConnection dbc = new DatabaseConnection();
		dbc.insert("marie", "np");
		System.out.println(dbc.database);
		System.out.println(dbc.possibleInsert);
	}

}
