package main;

public class Vertex {
    public int VertexCycle;
    public int VertexColor; // white ->0, gray ->1, black->2
	
	public void display() {
    	System.out.print( " VertexCycle# " + VertexCycle + " " );
        System.out.println();
	}
}