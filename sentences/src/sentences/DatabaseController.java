package sentences;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class DatabaseController {
	private String word; 
	private String type;
	private List<Element> database;
	private String possibleInsert;
	
	@Inject
	private DatabaseConnection dbc;
	
	
	public String getPossibleInsert() {
		return possibleInsert;
	}
	public String getWord(){
		return this.word;
	}
	public void setWord(String w){
		this.word = w;
	}
	public String getType(){
		return this.type;
	}
	public void setType(String type){
		this.type = type;
	}
	public List<Element> getDatabase() {
		return database;
	}
	
	@PostConstruct
	public void setDatabase(){
		//System.out.println("tried to set the database");
		//DatabaseConnection dbc = new DatabaseConnection();
		database = dbc.getDatabase();
	}
	public void insert(){
		//DatabaseConnection dbc = new DatabaseConnection();
		dbc.insert(word, type);
		possibleInsert = dbc.getPossibleInsert();
		setDatabase();		
	}
	public void delete(Element e){
		dbc.delete(e);
		setDatabase();
	}
	
}
