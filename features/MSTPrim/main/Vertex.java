package main;

public class Vertex {
    public String pred; // the predecessor vertex if any
    public int key; // weight so far from s to it
	
	public void display() {
    	System.out.print( " Pred " + pred + " Key " + key + " " );
        System.out.println();
	}
}