package main;

public class Vertex {
	public void display() {
		System.out.print( " Node " + name + " connected to: " );
        for (Neighbor theNeighbor : neighbors) {
            Vertex v = theNeighbor.end;
            System.out.print( v.name + ", " );
        }
        System.out.println();
	}
}