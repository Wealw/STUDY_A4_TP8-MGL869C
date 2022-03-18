package main;

public class Vertex {
	public void display() {
		System.out.print( " Node " + name + " connected to: " );
        for (Neighbor theNeighbor : neighbors) {
        	System.out.print( theNeighbor.end.name + ", " );
        }
        System.out.println();
	}
}