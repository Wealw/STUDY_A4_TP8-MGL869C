/**
 * GPL Example
 * Runtime variability and monolithic implementation
 * @author Roberto E. Lopez-Herrejon
 * ETS-LOGTI
 */
package main;

// undirected and Directed have the same code


import java.util.LinkedList;

// *********************************************************************   

/**
 * Class the represents the vertices of the graph
 * @author rlopez
 *
 */
public class Vertex {
    public LinkedList<Neighbor> neighbors;
    public String name;
      
    public Vertex() {
        name      = null;
        neighbors = new LinkedList<Neighbor>();
    }
    
    public  Vertex assignName( String name ) {
        this.name = name;
        return this;
    }
   
    public void addNeighbor( Neighbor n ) {
        neighbors.add( n );
    }

    public boolean equals(Object o) {
    	boolean result = false;
    	    	if (!(o instanceof Vertex)) return result;
    	Vertex v = (Vertex) o;
    	if (v.name==this.name) result = true;
    	return result;
    }  
}
