package main;

import java.util.Collections;
import java.util.Comparator;

public class Graph {
    /**
     * Method that executes the selected algorithms on the a starting vertex.
     * @param s vertex from there to start the algorithm execution
     */
	void run(Vertex s) {
		original(s);
		Graph gaux = StrongComponents();
		Graph.stopProfile();
		gaux.display();
		Graph.resumeProfile();

	}
	
    /** This method adds only the edge and not the neighbor. 
     * Used to transpose the graph as part of @feature STRONGLYCONNECTED. 
     * @param the_edge
     */
    public void addOnlyEdge( Edge the_edge ) {
        edges.add( the_edge );
    }
    
    public  Graph StrongComponents() {
        
        FinishTimeWorkSpace FTWS = new FinishTimeWorkSpace();
        
        // 1. Computes the finishing times for each vertex
        GraphSearch( FTWS );
                  
        // 2. Order in decreasing  & call DFS Transposal
        Collections.sort( vertices, 
         new Comparator<Vertex>() {
            public int compare(Vertex v1, Vertex v2) {
                if (v1.finishTime > v2.finishTime) return -1;
                if (v1.finishTime == v2.finishTime) return 0;
                return 1;
            }
        } );
  
        // 3. Compute the transpose of G
        // Done at layer transpose                     
        Graph gaux = computeTranspose((Graph)this );
            
        // 4. Traverse the transpose G
        TransposeWorkSpace WST = new TransposeWorkSpace();
        gaux.GraphSearch(WST);
 
        return gaux;
        
    }
    
    public  Graph computeTranspose( Graph the_graph ) {
                
         // Creating the new Graph
         Graph newGraph = new  Graph();
                 
         // Creates and adds the vertices with the same name
         for (Vertex vertex : vertices)
        	 newGraph.addVertex(new  Vertex().assignName(vertex.name));
        
         Vertex newVertex;
         Vertex newAdjacent;
         Edge newEdge;
         
         // adds the transposed edges
         for (Vertex theVertex : vertices) {
             // theVertex is the original source vertex
             // the newAdjacent is the reference in the newGraph to theVertex  
             newAdjacent = newGraph.findsVertex( theVertex.name );
        	 
             for (Neighbor theNeighbor : theVertex.neighbors) {              
                 // the new Vertex is the vertex that was adjacent to theVertex
                 // but now in the new graph
                 newVertex = newGraph.findsVertex( theNeighbor.end.name );
                           
                 // Creates a new Edge object and adjusts the adornments 
                 newEdge = new Edge(newVertex,newAdjacent );
                 newEdge.adjustAdorns( theNeighbor.edge );
                 
                 newGraph.addEdge( newEdge );
             } // all the neighbors
             
         } // all the vertices
         
         return newGraph;
         
     }
}