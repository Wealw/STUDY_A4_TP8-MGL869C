/**
 * GPL Example
 * Runtime variability and monolithic implementation
 * @author Roberto E. Lopez-Herrejon
 * ETS-LOGTI
 */
package main;

import java.util.LinkedList;


/**
 * Class Graph loads the graph descriptions from files and executes the selected algorithms.
 * @author rlopez
 *
 */
public class Graph {

	
	// **************************************************
	// Management of graph file for benchmarking
    // ************************************************************************
     
    // Lists of vertices and edges
    public LinkedList<Vertex> vertices;
    public LinkedList<Edge> edges;
    
    public void run(Vertex s) {
    	
    }
    
    /**
     * Constructor of the Graph class that initializes the 
     */
    public Graph() {
        vertices = new LinkedList<Vertex>();
        edges = new LinkedList<Edge>();
    }

    /** 
     * Adds an edge without weights if Weighted layer is not present
     * @param start vertex of the edge
     * @param end vertex of the edge
     */
    public void addAnEdge( Vertex start,  Vertex end) {
        Edge theEdge = new  Edge(start,end);
        addEdge( theEdge );
    }
   
    /**
     * Adds a vertex to the list of vertices
     * @param v Vertex to add
     */
    public void addVertex( Vertex v ) {
        vertices.add( v );
    }
   
    /**
     * Adds an edge based from
     * @param the_edge
     */
    public void addEdge( Edge the_edge ) {
        Vertex start = the_edge.start;
        Vertex end = the_edge.end;
        edges.add( the_edge );
        
        start.addNeighbor( new  Neighbor( end,the_edge ) );
    }
    
    /**
     * Finds a vertex given its name in the vertices list
     * @param theName
     * @return
     */
    public  Vertex findsVertex( String theName ) {
    
        // if we are dealing with the root
        if ( theName==null ) return null;
        
        for (Vertex theVertex : vertices)  
        	if ( theName.equals( theVertex.name ) ) return theVertex;
        return null;
    }

    /**
     * Displays the list of vertices and edges
     */
    public void display() {
        int i;
                                   
        System.out.println( "******************************************" );
        System.out.println( "Vertices " );
        for ( i=0; i<vertices.size(); i++ )
            ( ( Vertex ) vertices.get( i ) ).display();
         
        System.out.println( "******************************************" );
        System.out.println( "Edges " );
        for ( i=0; i<edges.size(); i++ )
            ( ( Edge ) edges.get( i ) ).display();
                
        System.out.println( "******************************************" );
    }

}
