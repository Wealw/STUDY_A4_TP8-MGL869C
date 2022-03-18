package main;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

public class Graph {
    /**
     * Method that executes the selected algorithms on the a starting vertex.
     * @param s vertex from there to start the algorithm execution
     */
	void run(Vertex s) {
		 original(s);
		 Graph gaux = ShortestPath( s );
		 Graph.stopProfile();
		 gaux.display();
		 Graph.resumeProfile();

	 }
	
	public  Graph ShortestPath( Vertex s ) {
        Vertex source;
                
        source = s;
                                        
        // 1. Initializes the single source
        for (Vertex x : vertices) {
            x.predecessor = null;
            x.dweight = Integer.MAX_VALUE;
        }
                                              
        source.dweight = 0;
        source.predecessor = null;
                                
        // 2. S <- empty set
        LinkedList<Vertex> S = new LinkedList<Vertex>();
                
        // 3. Queue <- V[G], copy the vertex in the graph in the priority queue
        LinkedList<Vertex> Queue = new LinkedList<Vertex>();
        
        for (Vertex x : vertices) {
        	// this means, if this is not the source
        	if (x.dweight!=0) Queue.add(x);      	
        }
        
        // Inserts the source at the head of the queue
        Queue.addFirst( source );
                
        // 4. while Q!=0
        int j,k;
        LinkedList<Neighbor> Uneighbors;
        Vertex u,v;
        Edge en;
        int wuv;
                
        while (Queue.size()!=0) {
            // 5. u <- Extract-Min(Q);
            u = (Vertex)Queue.removeFirst();
                                    
            // 6. S <- S U {u} 
            S.add(u);
                                                                        
            // 7. for each vertex v adjacent to u
            Uneighbors = u.neighbors;

            // For all the neighbors
            k=0;
            for (Neighbor vn: Uneighbors) {
                v = vn.end;
                en = vn.edge;
                wuv = en.weight;
  
                // 8. Relax (u,v w)
                if (v.dweight > (u.dweight+wuv)) {
                    v.dweight = u.dweight +  wuv;
                    v.predecessor = u.name;
                    Uneighbors.set(k,vn ); // adjust values in the neighbors
                                                                                                    
                    // update the values of v in the queue
                    int indexNeighbor = Queue.indexOf( v );
                    if ( indexNeighbor>=0 ) {
                        Queue.remove(indexNeighbor);
                                                                        
                        // Get the new position for v
                        int position = Collections.binarySearch( Queue,v, 
                          new Comparator<Vertex>() {
                            public int compare( Vertex v1, Vertex v2 ) {
                                if (v1.dweight < v2.dweight) return -1;
                                if (v1.dweight == v2.dweight) return 0;
                                return 1;
                            }
                        } );
                                                                                                                                
                        // Adds v in its new position in Queue                                                                                                                          
                        if ( position < 0 ) {
                        	// means it is not there
                            Queue.add(-(position+1),v);
                        } else {
                        	// means it is there
                            Queue.add(position,v);
                        }
                                                                                                            
                    } // if it is in the Queue
                                                                
                } // if 8.
  
            	k++;
            } // for all neighbors
                        
        } // of while Queue is not empty
                         
        // Creates the new Graph that contains the SSSP
        Graph newGraph = new  Graph();
                
        // Creates and adds the vertices with the same name
        for (Vertex vertex : vertices)
        	newGraph.addVertex(new Vertex().assignName(vertex.name));
                
        // Creates the edges from the NewGraph
        Vertex thePred;
        Vertex theNewVertex, theNewPred;
        Edge   e;
        Neighbor theNeighbor;
        boolean flag = false;

        // For each vertex in vertices list we find its predecessor
        // make an edge for the new graph from predecessor->vertex
        for (Vertex theVertex : vertices) {
            // theVertex and its Predecessor
            thePred = findsVertex( theVertex.predecessor );
         
            // if theVertex is the source then continue we don't need
            // to create a new edge at all
            if ( thePred==null ) continue;
         
            // Find the references in the new Graph
            theNewVertex = newGraph.findsVertex( theVertex.name );
            theNewPred = newGraph.findsVertex( thePred.name );
 
            // theNeighbor corresponds to the neighbor formed with
            // theVertex -> thePred
            // find the corresponding neighbor of the Vertex, that is,
            // predecessor
            j=0;
            flag=false;
            do {
                theNeighbor = ( Neighbor ) ( thePred.neighbors ).get( j );
                if ( theNeighbor.end.name.equals( theVertex.name ) )
                    flag = true;
                else
                    j++;
            } while( flag==false && j< thePred.neighbors.size() );
        
            // Creates the new edge from predecessor -> vertex in the newGraph
            // and adjusts the adorns based on the old edge
            // Edge theNewEdge = new Edge();
            Edge theNewEdge = new Edge( theNewPred, theNewVertex );
            e = theNeighbor.edge;
            theNewEdge.adjustAdorns( e );
         
            // Adds the new edge to the newGraph
            newGraph.addEdge( theNewEdge );
         	
        } // for all the vertices
                  
        return newGraph;
         
    } // shortest path
}