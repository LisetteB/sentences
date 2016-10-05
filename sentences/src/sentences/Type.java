package sentences;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Type {
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
	 * removeOuterBrackets, only if the first and last char are the opening and closing brackets
	 * and if the type inside the bracket has correct brackets so with (np)/(np) you cannot remove the outer brackets 
	 */
	public static String removeOuterBrackets(String t){
		
		if(t.startsWith("(") && t.endsWith(")")){
			String withinBrackets = t.substring(1, t.length()-1);
			if(correctBrackets(withinBrackets)){
				return withinBrackets;
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
	
	/*
	 * a type is correct if the brackets are correctly formed
	 * or if it is a singleton correct type
	 * or if both sides are correct types
	 */
	public static boolean correctType(String type){
		return correctBrackets(type) && correctTypeRecursion(type);
	}
	
	public static boolean correctTypeRecursion(String type){
		//first remove outerbrackets if they exist
		type = removeOuterBrackets(type);
		
		if(TypeExists(type)){
			return true;
		}
		//If there is a / or \\ there also should be a left and right element.
		else if(type.contains("/") || type.contains("\\")){
			if(findElements(type).length ==2){
				System.out.println(findElements(type)[0]+ " "+findElements(type)[1]);
				return (correctTypeRecursion(findElements(type)[0]) && correctTypeRecursion(findElements(type)[1]));
			}	
		}
		return false;
	}
	
	/*
	 * method to check if every opening bracket has a closing bracket
	 */
	public static boolean correctBrackets(String type){
		Stack<Character> s = new Stack<Character>();
		for(int i=0; i< type.length(); i++){
			char c = type.charAt(i);
			if(c == '('){
				s.push(c);
			}else if(c == ')' && s.pop() != '('){
				//If there's no opening bracket on the stack, the closing bracket is incorrect
				return false;
			}
		}
		if(s.isEmpty()){
			return true;
		}else{
			return false;
		}
	}
	
	public static String[] findElements(String type){
		Stack<Character> s = new Stack<Character>();
		for(int i=0; i< type.length(); i++){
			char c = type.charAt(i);
			if(c == '('){
				s.push(c);
			}else if(c == ')'){
				s.pop();
			}else if(s.isEmpty() && (c == '/' || c=='\\')){
				return(new String[] {type.substring(0, i), type.substring(i+1)});
			}	
		}
		return new String[] {};	
	}
	
	/*
	 * method written for correctType to check if the String is an existing type
	 */
	private static boolean TypeExists(String s){
		String[] typesAllowed = {"np","n","s"}; 
		for(int i=0; i<typesAllowed.length; i++){
			if(typesAllowed[i].equals(s)){
				return true;
			}
		}
		return false;
	}
	
	public static void main(String[] args){
		Type t1 = new Type("s\\(s/s)");
//		Type t2 = new Type("(s)");
//		System.out.println(t2.removeOuterBrackets(t2.typeComplete));
//		
		System.out.println(removeOuterBrackets(t1.typeComplete));
		String[] s = findElements(t1.typeComplete);
		for(String s1 : s){
			System.out.println(s1);
		}
		System.out.println(correctBrackets(t1.typeComplete));
		System.out.println(correctType(t1.typeComplete));
		
	}
}

