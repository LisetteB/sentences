package sentences;

import java.util.ArrayList;
import java.util.List;

public class SentenceChecker {
	
	private List<Element> database;
	private List<Element> sentence;
	
	public SentenceChecker(){
		setDatabase();
		sentence = new ArrayList<>();
		sentence.add(database.get(1));
		sentence.add(database.get(0));
		sentence.add(database.get(3));
	}
	
	public void setDatabase(){
		database = new ArrayList<Element>();
		database.add(new Element("chat", new Type("n")));
		database.add(new Element("le", new Type("np/n")));
		database.add(new Element("chien", new Type("n")));
		database.add(new Element("dort", new Type("np\\s")));
		database.add(new Element("jean", new Type("np")));		
	}
	
	public List<Element> deduction(){
		if(sentence.size() > 1){
			Element leftElement;
			Element rightElement;
			
			for(int i=0; i<sentence.size()-1; i++){
				leftElement = sentence.get(i);
				rightElement = sentence.get(i+1);

				if(leftElement.getType().needsRightArgument && (leftElement.getType().typeRight.equals(rightElement.getType().typeComplete))){
					Element combination = mergeRight(leftElement, rightElement);
					sentence.set(i,combination);
					sentence.remove(i+1);
					deduction();
				}
				if(rightElement.getType().needsLeftArgument && (rightElement.getType().typeLeft.equals(leftElement.getType().typeComplete))){
					Element combination = mergeLeft(leftElement, rightElement);
					sentence.set(i,combination);
					sentence.remove(i+1);
					deduction();
				}
			}
			return sentence;	
		}else {
			return sentence;	
		}	
	}
	
	/*
	 * mergeRight and mergeLeft return a new Element that's a combination of two other elements
	 * "np/n -> rechts een n geeft np
	 * "np\s" -> links een np geeft s
	 */
	public Element mergeRight(Element leftElement, Element rightElement){
		Type type = new Type(leftElement.getType().typeLeft);
		type.findProperties();
		String word_sequence = leftElement.getWordSequence() + " " + rightElement.getWordSequence();
		Element combination = new Element(word_sequence, type);
		return combination;
	}
	
	public Element mergeLeft(Element leftElement, Element rightElement){
		Type type = new Type(rightElement.getType().typeRight);
		type.findProperties();
		String word_sequence = leftElement.getWordSequence() + " " + rightElement.getWordSequence();
		Element combination = new Element(word_sequence, type);
		return combination;
	}
	
	public static void main(String[] args){
		SentenceChecker sc = new SentenceChecker();
		List<Element> deductionResult = sc.deduction();
		System.out.println(deductionResult);	

		
	}
}
