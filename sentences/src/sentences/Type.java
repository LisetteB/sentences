package sentences;

import java.util.Stack;

public class Type implements IType{
	boolean singleton;
	boolean needsLeftArgument;
	boolean needsRightArgument;
	String typeComplete;
	String typeLeft;
	String typeRight;
	
	public Type(){
		this("s");
	}
	
	public Type(String type){
		this.typeComplete = type;
		findProperties();
	}
	public void setTypeComplete(String type){
		typeComplete = type; 
	}
	public String getTypeComplete(){
		return typeComplete;
	}
	
	
	
	/*
	 * method that removes outer brackets if they are present and linked
	 * if the first element is an opening bracket and
	 * if the first closing bracket you encounter is at the end,
	 * you know they are the only ones and can be removed
	 * 
	 */
	public String removeOuterBrackets(String t){
		int numberOfChars = t.length();
		if(t.startsWith("(")){
			for(int letter=1; letter<numberOfChars; letter++){
				if(t.charAt(letter) == ')' && letter == numberOfChars-1){
					return(t.substring(1, numberOfChars-1));
				}
			}
		}
		return t;
	}
	
	/*
	 * method to fill all the member variables of a type
	 */
	public void findProperties(){		
		Stack<Character> s = new Stack<Character>();
		if(typeComplete.contains("/") || typeComplete.contains("\\")){
			for(int letter=0; letter<typeComplete.length(); letter++){
				if(typeComplete.charAt(letter) == '('){
					s.push('(');
				}else if(typeComplete.charAt(letter) == '/' && s.empty()){
					typeLeft = removeOuterBrackets(typeComplete.substring(0, letter));
					typeRight = removeOuterBrackets(typeComplete.substring(letter+1));
					needsLeftArgument = false;
					needsRightArgument = true;
				}else if(typeComplete.charAt(letter) == '\\' && s.empty()){
					typeLeft = removeOuterBrackets(typeComplete.substring(0, letter));
					typeRight = removeOuterBrackets(typeComplete.substring(letter+1));
					needsLeftArgument = true;
					needsRightArgument = false;
				}else if(typeComplete.charAt(letter) == ')'){
					s.pop();
				}					
			}
		}else{
			singleton = true;
			needsLeftArgument = false;
			needsRightArgument = false;
			typeLeft = null;
			typeRight = null;
		}	
		
	}
	
	public String toString(){
		String s = "singleton: " + singleton + "\n";
		s+= "needsLeftArgument: " + needsLeftArgument + "\n";
		s+= "needsRightArgument: " + needsRightArgument + "\n";
		s+= "type: " + typeComplete + "\n";
		s+= "typeLeft: " + typeLeft + "\n";
		s+= "typeRight: " + typeRight;
		return s;
	}

	
	public Type mergeRight(){		
		return new Type(typeLeft);
	}
	
	public Type mergeLeft(){
		return new Type(typeRight);
	}
	
	@Override
	public String example(){
		Type t1 = new Type("(np/n)\\s");
		System.out.println("example wordt daadwerkelijk aangeroepen");
		//return "jaja";
		return t1.toString();
	}
	
	public static void main(String[] args){
		Type t1 = new Type("(np/n)\\s");
		t1.example();
		System.out.println(t1.example());
	}
	
}
