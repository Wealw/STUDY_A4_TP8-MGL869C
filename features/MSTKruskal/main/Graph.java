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
   		 Graph gaux = Kruskal();
   		 Graph.stopProfile();
   		 gaux.display();
   		 Graph.resumeProfile();

	}
	
    public  Graph Kruskal() {
        
        // 1. A <- Empty set
        LinkedList<Edge> A = new LinkedList<Edge>();
                
        // 2. for each vertex v E V[G]
        // 3.    do Make-Set(v)
        
        for (Vertex v : vertices) {
            v.representative = v; 					// a vertex is in its own set 
            v.members = new LinkedList<Vertex>(); 	// There are no members in the set
        }
        
                
        // 4. sort the edges of E by nondecreasing weight w        
        // Creates the edges objects
                
        // Sort the Edges in non decreasing order
        Collections.sort( edges, 
          new Comparator<Edge>() {
            public int compare( Edge e1, Edge e2 ) { 
                if ( e1.weight < e2.weight ) return -1;
                if ( e1.weight == e2.weight ) return 0;
                return 1;
            }
        } );
        
        // 5. for each edge in the nondecreasing order
        Vertex urep, vrep;
        
        for(Edge e1 : edges) {
            // 6. if Find-Set(u)!=Find-Set(v)
            Vertex u = e1.start;
            Vertex v = e1.end;

            if (!(v.representative.name).equals(u.representative.name)) {
                // 7. A <- A U {(u,v)}
                A.add( e1 );
                                                                
                // 8. Union(u,v)
                urep = u.representative;
                vrep = v.representative;
                                                                 
                if (urep.members.size() > vrep.members.size()) { 
                	// we add elements of v to u
                	for(Vertex vertexaux : vrep.members) {
                		vertexaux.representative = urep;
                        urep.members.add( vertexaux );
                	}
                    v.representative = urep;
                    vrep.representative = urep;
                    urep.members.add( v );
                    if (!v.equals(vrep)) urep.members.add( vrep );
                    ( vrep.members ).clear();
                }
                else { // we add elements of u to v
                    for (Vertex vertexaux : urep.members) {
                    	vertexaux.representative = vrep;
                        vrep.members.add( vertexaux );                    	
                    }                   
                    u.representative = vrep;
                    urep.representative = vrep;
                    vrep.members.add( u );
                    if (!u.equals(urep)) vrep.members.add( urep );
                    urep.members.clear();                                                                       
                }                                    
            }
        }
        
                
        // 9. return A
        // Creates the new Graph that contains the SSSP
        Graph newGraph = new  Graph();
                
        // Creates and adds the vertices with the same name
        for (Vertex vertex : vertices)
        	newGraph.addVertex(new Vertex().assignName(vertex.name));
              
        // Creates the edges from the NewGraph
        Vertex theStart, theEnd;
        Vertex theNewStart, theNewEnd;
       
        // For each edge in A we find its two vertices
        // make an edge for the new graph from with the correspoding
        // new two vertices
        for (Edge theEdge : A) {
            // theEdge with its two vertices
            theStart = theEdge.start;
            theEnd = theEdge.end;
         
            // Find the references in the new Graph
            theNewStart = newGraph.findsVertex(theStart.name);
            theNewEnd = newGraph.findsVertex(theEnd.name);
         
            // Creates the new edge with new start and end vertices in the newGraph
            // and adjusts the adorns based on the old edge
            Edge theNewEdge = new Edge(theNewStart,theNewEnd);
            theNewEdge.adjustAdorns(theEdge);
         
            // Adds the new edge to the newGraph
            newGraph.addEdge( theNewEdge );
        }
        
        return newGraph;
        
    }
}