package sentences;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@SessionScoped
public class SentenceChecker implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Sentence sentence;
	private String result;
	private List<Element> suggestions;
	
	public List<Element> getSuggestions(){
		return suggestions;
	}
	public String getResult(){
		return this.result;
	}

	public Sentence getSentence() {
		return sentence;
	}

	private void setSentence(Sentence sentence) {
		this.sentence = sentence;
	}
	
	public SentenceChecker(){
		//setDatabase();		
		setSentence(new Sentence());
	}
	
	public String addToSentence(Element e){
		sentence.addToSentence(e);
		result = null;
		suggestions = null;
		return null;
	}
	public String clearSentence(){
		sentence.clearSentence();
		result = "";
		suggestions = null;
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
	
	
	/*
	 * method that calls the method sentenceSuggestions, 
	 * to fill a list with possible next Elements
	 */
	public String elementSuggestions(){
		DatabaseConnection dbc = new DatabaseConnection();
		List<Element> database = dbc.getDatabase();
		suggestions = sentence.sentenceSuggestion(database);
		if(suggestions.size() >0){
			return null;
		}else{
			suggestions = null;
			return null;
		}
	}
	
	public static void main(String[] args){
		SentenceChecker sc = new SentenceChecker();
		//sc.addToSentence(new Element("il",new Type("np")));
		//sc.addToSentence(new Element("mange", new Type("(np\\s)/np")));
		sc.elementSuggestions();
		System.out.println(sc.suggestions);
	}
}
