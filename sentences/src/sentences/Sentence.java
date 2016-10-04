package sentences;

import java.util.ArrayList;
import java.util.List;

public class Sentence {
	private List<Element> sentence;

	public List<Element> getSentence() {
		return sentence;
	}
	
	public void addToSentence(Element e){
		sentence.add(e);
	}
	
	Sentence(){
		sentence = new ArrayList<>();
	}
	
	@Override
	public String toString(){
		String stringRepresentation = "";
		for(int i=0; i<sentence.size(); i++){
			stringRepresentation += sentence.get(i).getWordSequence() + " ";
		}
		return stringRepresentation;
	}
	
	public void clearSentence(){
		sentence = new ArrayList<>();
	}
	
	/*
	 * loop through the sentence, 
	 * if the types of two elements next to each other match
	 * merge them and insert the resulted combination in the sentence,
	 * in place of the two elements
	 * and call the deduction function again
	 * 
	 * if there is only one element left in the word_array
	 * Or if there are no more possible matches
	 * return the sentence
	 */
	public List<Element> deduction(){
		if(getSentence().size() > 1){
			Element leftElement;
			Element rightElement;
			
			for(int i=0; i<getSentence().size()-1; i++){
				leftElement = getSentence().get(i);
				rightElement = getSentence().get(i+1);

				if(leftElement.getType().needsRightArgument && (leftElement.getType().typeRight.equals(rightElement.getType().typeComplete))){
					Element combination = mergeRight(leftElement, rightElement);
					getSentence().set(i,combination);
					getSentence().remove(i+1);
					deduction();
				}
				if(rightElement.getType().needsLeftArgument && (rightElement.getType().typeLeft.equals(leftElement.getType().typeComplete))){
					Element combination = mergeLeft(leftElement, rightElement);
					getSentence().set(i,combination);
					getSentence().remove(i+1);
					deduction();
				}
			}
			return getSentence();	
		}else {
			return getSentence();	
		}	
	}
	
	public boolean isSentence(){
		List<Element> result = deduction();
		if(result.size() == 1 && sentence.get(0).getType().typeComplete.equals("s")){
			return true;
		}
		else return false;
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
}
