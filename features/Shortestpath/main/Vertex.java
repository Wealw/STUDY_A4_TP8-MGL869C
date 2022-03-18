package main;

public class Vertex {
    public String predecessor; // the name of the predecessor if any
    public int dweight; // weight so far from s to it
	
	public void display() {
    	System.out.print( "Pred " + predecessor + " DWeight " + dweight + " " );
        System.out.println();
	}
}