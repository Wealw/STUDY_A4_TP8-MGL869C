package main;

public class Graph {
	
	public boolean directed;
	
	public void setDirected(){
		directed = false;
	}
	
    /**
     * Adds an edge based from
     * @param the_edge
     */
    public void addEdge( Edge the_edge ) {
        Vertex start = the_edge.start;
        Vertex end = the_edge.end;
        edges.add( the_edge );
        
        end.addNeighbor( new  Neighbor( start,the_edge ) );
    }
}