/**
 * GPL Example
 * Runtime variability and monolithic implementation
 * @author Roberto E. Lopez-Herrejon
 * ETS-LOGTI
 */
package main;

// @feature CYCLE
public class CycleWorkSpace extends WorkSpace {

	   public boolean anyCycles;
	    public int     counter;
	    private boolean _DIRECTED;
	      
	    public static final int WHITE = 0;
	    public static final int GRAY  = 1;
	    public static final int BLACK = 2;
	            
	    public CycleWorkSpace() {
	        anyCycles = false;
	        counter   = 0;
	    }
	    
	    public void setDirected(boolean isDirected) {
	        _DIRECTED = isDirected;
	    }

	    public void init_vertex( Vertex v ) {
	        v.VertexCycle = Integer.MAX_VALUE;
	        v.VertexColor = WHITE; // initialize to white color
	    }

	    public void preVisitAction( Vertex v ) {
	        // This assigns the values on the way in
	        if ( v.visited!=true ) { 
	        	// if it has not been visited then set the
	            // VertexCycle accordingly
	            v.VertexCycle = counter++;
	            v.VertexColor = GRAY; // we make the vertex gray
	        }
	    }

	    public void postVisitAction( Vertex v ) {
	        v.VertexColor = BLACK; // we are done visiting so make it black
	        counter--;
	    } // of postVisitAction

	    public void checkNeighborAction(Vertex vsource, Vertex vtarget ) {
	    	if(_DIRECTED) {
		    	aux_directed_checkNeighborAction(vsource.VertexColor,vtarget.VertexColor,vsource.VertexCycle,vtarget.VertexCycle);
	    	} else {
		    	aux_undirected_checkNeighborAction(vsource.VertexColor,vtarget.VertexColor,vsource.VertexCycle,vtarget.VertexCycle);
	    	}
	    } // of checkNeighborAction
	    
	    public void aux_directed_checkNeighborAction(int sourceColor, int targetColor, int sourceCycle, int targetCycle) {
	    	if (sourceColor==GRAY && targetColor==GRAY) anyCycles = true;
	    }
	    
	    public void aux_undirected_checkNeighborAction(int sourceColor, int targetColor, int sourceCycle, int targetCycle) {
	        if (sourceColor==GRAY && targetColor==GRAY && sourceCycle!=(targetCycle+1)) 
	            anyCycles = true;
	    }

} // of CycleWorkSpace
// --
