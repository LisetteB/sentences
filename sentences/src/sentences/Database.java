package sentences;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named
@SessionScoped
public class Database implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/*
	 * property elements to loop through and see the words in the front end
	 * property isWellFormed, to save the value of the correctness of the sentence
	 */
	private List<Element> elements;
	private List<Element> sentence;
	private String wellFormed;
	
	public Database(){
		sentence = new ArrayList<Element>();
	}
	
	public List<Element> getElements(){
		return elements;
	}	
	
	@PostConstruct
	public void setElements(){
		elements = new ArrayList<Element>();
		elements.add(new Element("chat", new Type("n")));
		elements.add(new Element("le", new Type("np/n")));
		elements.add(new Element("chien", new Type("n")));
		elements.add(new Element("dort", new Type("np\\s")));
		elements.add(new Element("jean", new Type("np")));		
	}

	public List<Element> getSentence() {
		return sentence;
	}

	public void setSentence(List<Element> sentence) {
		this.sentence = sentence;
	}
	public String addToSentence(Element e){
		this.sentence.add(e);
		return null;
	}
	
	public String computeSentence(ArrayList<Element> elements){
		ArrayList<Element> sentence_arrayList = Element.deduction(elements);
		if(sentence_arrayList.size() == 1 && sentence_arrayList.get(0).getType().typeComplete.equals("s")){
			wellFormed="bien fait, tu as finalement construit une phrase correcte";
		}
		else{ 
			wellFormed="mais non, personne comprendra ton francais";
		}
		return "newGame";
	}

	public String getWellFormed() {
		return wellFormed;
	}
	
	public String startaNewGame(){
		return "index";
	}


	
}
