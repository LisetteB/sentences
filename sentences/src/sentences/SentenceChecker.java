package sentences;

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
	private Sentence sentence;
	private String result;

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
}
