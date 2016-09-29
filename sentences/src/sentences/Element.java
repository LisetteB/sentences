package sentences;

import java.util.ArrayList;

import javax.inject.Named;

@Named
public class Element {
	private String wordSequence;
	private Type type;
	
	/*
	 * "np"
	 * "np\s" -> links een np geeft s
	 * "np/n -> rechts een n geeft np
	 */
	public Element(String word_sequence, Type type){
		setWordSequence(word_sequence);
		setType(type);
	}
	
	public void setWordSequence(String wordSequence){
		this.wordSequence = wordSequence;
	}
	
	public void setType(Type type){
		this.type = type;
	}
	
	public String getWordSequence(){
		return this.wordSequence;
	}
	public Type getType(){
		return this.type;
	}
	
	/*
	 * "np/n -> rechts een n geeft np
	 */
	public void mergeRight(Element rightElement){
		type.typeComplete = type.typeLeft;
		type.findProperties();
		wordSequence = wordSequence + " " + rightElement.wordSequence;

	}
	
	
	/*
	 * "np\s" -> links een np geeft s
	 */
	public void mergeLeft(Element leftElement){
		type.typeComplete = type.typeRight;
		type.findProperties();
		
		wordSequence = leftElement.wordSequence + " " + wordSequence;

	}

	
	/*
	 * loop through the word_array, 
	 * if two words next to each other match
	 * merge them and insert in the word_arrayList,
	 * delete the old two words
	 * and call the deduction function again
	 * 
	 * if there is only one Words left in the word_array
	 * Or if there are no more possible matches
	 * return the word_array 
	 */
	public static ArrayList<Element> deduction(ArrayList<Element> elements){
		if(elements.size() > 1){
			Element leftElement;
			Element rightElement;
			
			for(int i=0; i<elements.size()-1; i++){
				leftElement = elements.get(i);
				rightElement = elements.get(i+1);

				if(leftElement.type.needsRightArgument && (leftElement.type.typeRight.equals(rightElement.type.typeComplete))){
					leftElement.mergeRight(rightElement);
					elements.remove(i+1);
					deduction(elements);
				}
				if(rightElement.type.needsLeftArgument && (rightElement.type.typeLeft.equals(leftElement.type.typeComplete))){
					rightElement.mergeLeft(leftElement);
					elements.remove(i);
					deduction(elements);
				}
			}
			return elements;	
		}else {
			return elements;	
		}		
	}
	
	
	public String toString(){
		return wordSequence + "\n" ;//+  type;
	}
	
	public static boolean isSentence(ArrayList<Element> elements){
		ArrayList<Element> sentence_arrayList = deduction(elements);
		if(sentence_arrayList.size() == 1 && sentence_arrayList.get(0).type.typeComplete.equals("s")){
			return true;
		}
		else return false;
	}
	
	public static void main(String[] args){
		Type t = new Type("n");
		Element w = new Element("chat", t);
		
		Type t1 = new Type("np\\s");
		Element w1 = new Element("dort", t1);
		
		Type t2 = new Type("np/n");
		Element w2 = new Element("le", t2);
		
		Type t3 = new Type("np");
		Element w3 = new Element("Jean", t3);
		
		ArrayList<Element> sentence1 = new ArrayList<>();
		sentence1.add(w2);
		sentence1.add(w);
		sentence1.add(w1);
		
		ArrayList<Element> sentence2 = new ArrayList<>();
		sentence2.add(w3);
		sentence2.add(w1);
		sentence2.add(w2);
		
		
		//System.out.println(deduction(word_arrayList1));
		
		System.out.println(isSentence(sentence1));
		System.out.println(isSentence(sentence2));
		
	}
	
}
