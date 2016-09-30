package sentences;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named
@SessionScoped
public class SentenceChecker implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Element> database;
	private Sentence sentence;
	private String result;
	
	public String getResult(){
		return this.result;
	}
	
	public List<Element> getDatabase() {
		return database;
	}
	
	public void setDatabase(){
		database = new ArrayList<Element>();
		database.add(new Element("chat", new Type("n")));
		database.add(new Element("le", new Type("np/n")));
		database.add(new Element("chien", new Type("n")));
		database.add(new Element("dort", new Type("np\\s")));
		database.add(new Element("jean", new Type("np")));
		database.add(new Element("mange", new Type("(np\\s)/np")));
	}

	public Sentence getSentence() {
		return sentence;
	}

	private void setSentence(Sentence sentence) {
		this.sentence = sentence;
	}
	
	public SentenceChecker(){
		setDatabase();		
		setSentence(new Sentence());
//		getSentence().add(database.get(1));
//		getSentence().add(database.get(0));
//		getSentence().add(database.get(3));
	}
	
	public String addToSentence(Element e){
		sentence.addToSentence(e);
		System.out.println("the complete sentence is now " + sentence);
		return null;
	}
	public String clearSentence(){
		sentence.clearSentence();
		result = "";
		return null;
	}
	
	public String checkSentence(){
		boolean isSentence = sentence.isSentence();
		if(isSentence){
			result = "Bien fait tu as construit une phrase correcte!";
		}else{
			result = "Mais non... C'est incomprehensable...";
		}
		return null;
	}
	
	public static void main(String[] args){
		SentenceChecker sc = new SentenceChecker();
		Sentence s = new Sentence();
		s.addToSentence(sc.database.get(1));
		s.addToSentence(sc.database.get(2));
		s.addToSentence(sc.database.get(3));
		System.out.println(s.isSentence());			
	}
}
