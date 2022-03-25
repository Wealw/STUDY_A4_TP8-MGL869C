/**
 * GPL Example
 * Runtime variability and monolithic implementation
 * @author Roberto E. Lopez-Herrejon
 * ETS-LOGTI
 */
package main; 

// Note: Code for UNDIRECTED and DIRECTED is the same.

// *************************************************************************

/**
 * Class the represents the edges of the graph
 * @author rlopez
 *
 */
public  class  Edge  extends Neighbor {
	
    public  Vertex start;

	
        
    // TODO : Check if it is usefull to remove this constructor when WEIGHTED is selected
    public Edge( Vertex the_start, Vertex the_end) {
        start = the_start;
        end = the_end;
    }

	
        
    public void display() {
        System.out.print( " start=" + start.name + " end=" + end.name );
        System.out.println();
      
    }

	 
     
    public String toString() {
        String result = " start=" + start.name + " end=" + end.name;
        return result;
    }


}
