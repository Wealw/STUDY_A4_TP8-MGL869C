/**
 * GPL Example
 * Runtime variability and monolithic implementation
 * @author Roberto E. Lopez-Herrejon
 * ETS-LOGTI
 */
package main;

import java.util.LinkedList;
import java.util.PriorityQueue;
// @feature MSTPrim
import java.lang.Integer;
import java.util.Collections;
import java.util.Comparator;
// ---

// @feature BENCH
import java.io.*;

/**
 * Class Graph loads the graph descriptions from files and executes the selected algorithms.
 * @author rlopez
 *
 */
public class Graph {

	
	// **************************************************
	// Management of graph file for benchmarking
	// @feature BENCH
    public Reader inFile; // File handler for reading
    public static int ch; // Character to read/write

 
    /**
     * Opens the benchmark file
     * @param FileName
     */
    public void openBenchmark( String FileName ) {
        try {
            inFile = new FileReader( FileName );
        } catch ( IOException e ) {
            System.out.println( "Your file " + FileName + " cannot be read" );
            e.printStackTrace();
            System.exit(0);
        }
    }

    /**
     * Attempts to close the benchmark file.
     * @throws IOException
     */
    public void stopBenchmark()
    {
    	try { inFile.close();
    	} catch(IOException e) {
    		System.out.println("Error while closing the benchmark file");
    		e.printStackTrace();
    		System.exit(0);
    	}
    }

    public int readNumber() throws IOException {
        int index =0;
        char[] word = new char[80];
        int ch=0;

        ch = inFile.read();
        while( ch==32 )
            ch = inFile.read(); // skips extra whitespaces

        while( ch!=-1 && ch!=32 && ch!=10 ) // while it is not EOF, WS, NL
        {
            word[index++] = ( char )ch;
            ch = inFile.read();
        }
        word[index]=0;

        String theString = new String( word );

        theString = new String( theString.substring( 0,index )).trim();
        return Integer.parseInt( theString,10 );
    }

    
    // Timing variables for profiling
    static long last=0, current=0, accum=0;

    
    /**
     * Gets current reading of timing in milliseconds
     */
    public static void startProfile() {
        accum = 0;
        current = System.currentTimeMillis();
        last = current;
    }

    
    /**
     * Stops the profiling by computing the elapsed time
     */
    public static void stopProfile() {
        current = System.currentTimeMillis();
        accum = accum + ( current - last );
    }

    /**
     * Resumes the profiler by reset the current value to current time.
     */
    public  static void resumeProfile() {
        current = System.currentTimeMillis();
        last = current;
    }

    /**
     * Ends the profiling 
     */
    public static void endProfile() {
        current = System.currentTimeMillis();
        accum = accum + ( current-last );
        System.out.println( "Time elapsed: " + accum + " milliseconds" );
    }


    // **** End of @feature BENCH code
    // ************************************************************************
     
    // Lists of vertices and edges
    public LinkedList<Vertex> vertices;
    public LinkedList<Edge> edges;
    
    /**
     * Constructor of the Graph class that initializes the 
     */
    public Graph() {
        vertices = new LinkedList<Vertex>();
        edges = new LinkedList<Edge>();
    }
    
    /**
     * Method that executes the selected algorithms on the a starting vertex.
     * @param s vertex from there to start the algorithm execution
     */
    public void run( Vertex s ) {
    	
    	
    	// @feature Number
    	if (Main.NUMBER) NumberVertices();
    	 // ---
	 
        // @feature CONNECTED
   	 	if (Main.CONNECTED) ConnectedComponents();
        // ----

        // @feature STRONGLYCONNECTED
   	 	if (Main.STRONGLYCONNECTED) {
   	 		Graph gaux = StrongComponents();
   	 		Graph.stopProfile();
   	 		gaux.display();
   	 		Graph.resumeProfile();
   	 	}
        // ---
   	 
    	// @feature Cycle
    	if (Main.CYCLE) System.out.println( " Cycle? " + CycleCheck() );
    	// ---
    	 
    	// @feature MSTPrim
    	if (Main.MSTPRIM) {
    		 Graph gaux = Prim( s );
    		 System.out.println("Displaying Prim result ");
    		 Graph.stopProfile();
    		 gaux.display();
    		 System.out.println("End displaying Prim result ");
    		 Graph.resumeProfile();
    	 }
    	 // ----
    	 
         // @feature MSTKRUSKAL
    	 if (Main.MSTKRUSKAL) {
    		 Graph gaux = Kruskal();
    		 Graph.stopProfile();
    		 gaux.display();
    		 Graph.resumeProfile();
    	 }
         // ---

         // @feature SHORTESTPATH
    	 if (Main.SHORTESTPATH) {
    		 Graph gaux = ShortestPath( s );
    		 Graph.stopProfile();
    		 gaux.display();
    		 Graph.resumeProfile();
    	 }
         // ----
         
    } // run

    /** 
     * Adds an edge without weights if Weighted layer is not present
     * @param start vertex of the edge
     * @param end vertex of the edge
     */
    public void addAnEdge( Vertex start,  Vertex end) {
        Edge theEdge = new  Edge(start,end);
        addEdge( theEdge );
    }
   
    // @feature Weighted
    /** 
     * Adds an edge with weights if Weighted layer is not present
     * @param start vertex of the edge
     * @param end vertex of the edge
     */
    public void addAnEdge( Vertex start,  Vertex end, int weight ) {
        Edge theEdge = new Edge(start, end, weight );
        addEdge(theEdge);
    }
    // ----
    
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
        
        // @feature Undirected
        if (Main.UNDIRECTED) end.addNeighbor( new  Neighbor( start,the_edge ) );
        // ---
    } //of addEdge
    
    // @feature STRONGLYCONNECTED
    /** This method adds only the edge and not the neighbor. 
     * Used to transpose the graph as part of @feature STRONGLYCONNECTED. 
     * @param the_edge
     */
    public void addOnlyEdge( Edge the_edge ) {
        edges.add( the_edge );
    } // of addOnlyEdge
    // ---
    
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
    } // of findsVertex

     
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
    } // of display
    
     
    // @feature MSTPRIM
    /**
     * Finds an Edge given both of its vertices
     * @param theSource
     * @param theTarget
     * @return
     */
    public  Edge findsEdge( Vertex theSource, Vertex theTarget ) {     
        for(Edge theEdge : edges)
        	if ( (theEdge.start.name.equals( theSource.name ) && 
                    theEdge.end.name.equals( theTarget.name ) ) ||
                   ( theEdge.start.name.equals( theTarget.name ) && 
                    theEdge.end.name.equals( theSource.name ) ) )
        	return theEdge;
       
        return null;
    } // of findsEdge
    // ---
 
    
    // Note: same code as DFS, only one line difference
    //@feature BFS
    // @feature when there is a search, either BFS or DFS. There is only one line of difference below.
    /**
     * Performs a search on the graph, either in BFS or DFS mode.
     * @param w
     */
    public void GraphSearch( WorkSpace w ) {
        int           s, c;
        Vertex  v;
  
        // Step 1: initialize visited member of all nodes

        s = vertices.size();
        if ( s == 0 )
            return;
         
        // Showing the initialization process
        for ( c = 0; c < s; c++ ) {
            v = ( Vertex ) vertices.get( c );
            v.init_vertex( w );
        }

        // Step 2: traverse neighbors of each node
        for ( c = 0; c < s; c++ ) {
            v = ( Vertex ) vertices.get( c );
            if ( !v.visited ) {
                w.nextRegionAction( v );
                
                // Note: Because we manually check that they are mutually exclusive, actually at most one of the
                // two searches will be called
                
                // @feature BFS
                if (Main.BFS) {
                	System.out.println("BFS " + v.name);
                	v.bftNodeSearch( w );
                }
                // ---
                
                // @feature DFS 
                if (Main.DFS) {
                	System.out.println("DFS " + v.name);
                	v.dftNodeSearch( w );
                }
                // --
            }
        } //end for
    }   // GraphSearch
    // ---
    
    
    //@feature NUMBER
    public void NumberVertices() {
        GraphSearch( new NumberWorkSpace() );
    }
    // -----
    
    
    // @feature CYCLE
    public boolean CycleCheck() {
        CycleWorkSpace c = new CycleWorkSpace();
        GraphSearch( c );
        return c.anyCycles;
    }
    // ---
    
    
    // @feature MSTPRIM
    public  Graph Prim( Vertex root ) {
 
    	// Vertex root = r;
     // Sets up the root
        // root = r;
       
        // 1. Queue <- V[G], copy the vertex in the graph in the priority queue
        // LinkedList<Vertex> Queue = new LinkedList<Vertex>(); // @Removed because it is not a priority queue

        // Represents the vertices already in the computed MST so far ,  V-Q in algorithm
        LinkedList<Vertex> verticesMST = new LinkedList<Vertex>();
        
        LinkedList<Vertex> Q = new LinkedList<Vertex>();
        
        // Add all the vertices to the list
        for (Vertex x : vertices) 
        	// this means, if this is not the root
        	// if ( x.key != 0 ) 
        	// 	Q.add( x );
        	if (x!=root) Q.add(x);
       
        
        // @DEBUG
        System.out.println("Vertices in Q " + Q.size());
                
       // 2. and 3. Initializes the vertices
        for (Vertex x : vertices) {
            x.pred = null;
            x.key = Integer.MAX_VALUE;
        }
                 
        // 4. and 5.        
        root.key = 0;
        root.pred = null;
       
        // Adds the root to the vertices in the MST
        verticesMST.add(root);
        
        // Inserts the root at the head of the queue
        // Queue.addFirst( root ); // @DEBUG, the root has been added in the first step of the algorithm
                
        // 6. while Q!=0
        LinkedList<Neighbor> Uneighbors;
        Vertex u,v;
        Edge en;
                
        int k=0; // iteration number of loops, special case for the first elemetn
        int wuv;
        boolean isNeighborInQueue = false;
                
        // Queue is a list ordered by key values.
        // At the beginning all key values are INFINITUM except
        // for the root whose value is 0.
        
        // 6. while Q!=0
        while ( Q.size()!=0 ) {
        	
        	// @DEBUG
            System.out.println("In loop Vertices in Q " + Q.size());
            System.out.println("In loop Vertices in verticesMST " + verticesMST.size());
            
            // 7. u <- Extract-Min(Q);                                                  
            // Since this is an ordered queue the first element is the min
            // u = Queue.removeFirst();  // @DEBUG, deprecated for the case of a linked list
            // u = Queue.poll();  // for the priority queue, it extracts and removes the head from the queue
            
        	// Extracts the vertex that as an edge with minimum weight that connects with the vertices of verticesMST (MST build so far)
        	// Removes u from the list Q and adds it to vertices MST
            // 
            if (k==0) { // special case of the first iteration
            	u = root;
            } else
            	u = extractMin(Q, verticesMST);
        	
        	// @DEBUG Extracted minimum vertex
        	System.out.println(u.name);
        	
        	// System.exit(0);
        	
            // 8. for each vertex v adjacent to u
            Uneighbors = u.neighbors;

            for(Neighbor vn : Uneighbors) {
                v = vn.end;
                en = vn.edge;

                // @DEBUG deprecated for linkedlist
                // Check to see if the neighbor is in the queue
                // isNeighborInQueue = false;                                                                          

                // int indexNeighbor = Queue.indexOf( v );
                // if ( indexNeighbor>=0 )
                //    isNeighborInQueue=true;
                // -----
                
                // if the Neighbor is in the list Q
                isNeighborInQueue = Q.contains(v);
                
                wuv = en.weight;
                                                 
                // @DEBUG
                System.out.println("\t Neighbor=" + v.name + " isNeighborInQueue=" + isNeighborInQueue + " wuv=" + wuv + " v.key=" + v.key);
                System.out.print("Q status =");
                for (Vertex inqv : Q) {
                	System.out.print(inqv.name +",");
                }
                System.out.println();
                
                // 9.do if v E Q and w(u,v) < key [v]
                if ( isNeighborInQueue && ( wuv < v.key ) ) {
                    v.pred = u.name; // 10
                    v.key = wuv;     // 11 

                    // // @DEBUG
                    // System.out.println("Index value " + k);
                    // Uneighbors.set( k,vn ); // adjust values in the neighbors
                                                                                                    
                    // update the values of v in the queue                    
                    // Remove v from the Queue so that we can reinsert it
                    // in a new place according to its new value to keep
                    // the Linked List ordered
                    // Queue.remove( indexNeighbor );
                                                                                                    
                    // Get the new position for v
                    // int position = Collections.binarySearch( Queue,v, 
                    //       new Comparator<Vertex>() {
                    //    		public int compare( Vertex v1, Vertex v2 ) {                       			
                    //    			if ( v1.key < v2.key ) return -1;
                    //    			if ( v1.key == v2.key ) return 0;
                    //    			return 1;
                    //    		}
                    //	} 
                    // );
                                                                                                            
                    // Adds v in its new position in Queue                                                                                  
                    // if (position<0)  { 
                    //	// means it is not there
                    //    Queue.add(-(position+1),v);
                    // } else { 
                    //	// means it is there
                    //    Queue.add( position,v );
                   // }
                                                                                                  
                } // if 9-11.
            	
             
             
            } // 8. for all neighbors
            
            // increments the k counter at the end of each iteration   
            k++;	
                
        } // 6. while there are elements in Q

        
        // @DEBUG
        System.out.println("MSTPrim computed ");
        
        System.out.println("Vertices in the Q " + Q.size());
        for (Vertex vv : Q) { 
        	System.out.print(vv.name +", ");
        }
        
        System.out.println("\nVertices in MST " + verticesMST.size());
        for (Vertex vv : verticesMST) { 
        	System.out.print(vv.name +", "); 
        }      
        
        System.out.println("\nVertices, preds, keys (weights) ");
        for (Vertex vv: vertices) {
        	System.out.println("name= " + vv.name + ", pred=" + vv.pred + ", key=" + vv.key);
        }
        
        // @DEBUG
        // System.exit(0);
        
        // Creates the new Graph that contains the MST
        Graph newGraph = new Graph();
                
        // Creates and adds the vertices with the same name
        for (Vertex vertex : vertices) 
        	newGraph.addVertex( new Vertex().assignName( vertex.name ));      
        
        // Creates the edges from the NewGraph
        Vertex thePred;
        Vertex theNewVertex, theNewPred;
        Edge   e;
       
        // For each vertex in vertices list we find its predecessor
        // make an edge for the new graph from predecessor->vertex
        for (Vertex theVertex : vertices) {
            // theVertex and its Predecessor
            thePred = findsVertex( theVertex.pred );
         
            // if theVertex is the source then continue we dont need
            // to create a new edge at all
            if ( thePred==null ) continue;
         
            // Find the references in the new Graph
            theNewVertex = newGraph.findsVertex( theVertex.name );
            theNewPred = newGraph.findsVertex( thePred.name );
         
            // Creates the new edge from predecessor -> vertex in the newGraph
            // and adjusts the adorns based on the old edge
            Edge theNewEdge = new Edge( theNewPred, theNewVertex );
            e = findsEdge( thePred,theVertex );
            theNewEdge.adjustAdorns( e );
         
            // Adds the new edge to the newGraph
            newGraph.addEdge( theNewEdge );        	
        } // for each vertex
        
        return newGraph;
        
    } // MSTPrim
    
    /**
     * Auxiliary method that: 
     * 1) finds an edge with minimum weight that connects a vertex in q and a vertex in verticesMST, 
     * 2) removes from q the vertex minVertex found in 1) that is not in verticesMST
     * 3) add vertex minVertex to verticesMST, meaning, the vertex is now in the MST
     * @param q Represents Q in Prim algorithm, the queue of nodes yet to be included in the MST
     * @param verticesMST Represents V-Q, the list of nodes already that form the MST being constructed
     * @return
     */
    private Vertex extractMin(LinkedList<Vertex> q, LinkedList<Vertex> verticesMST) {
    	
    	// 1. Finds the edge with minimum weight that connects a node in q and a node in verticesMST, 
    	// which means that only one vertex is already in MST
    	Vertex minVertex = new Vertex();
    	int minEdgeValue = Integer.MAX_VALUE; 
    	
    	// For all the vertices
    	for (Vertex vertex : q) {
    		
    		// @DEBUG
    		// System.out.println("Vertex " + vertex.name);
    		
    		// For all its neighbors get their edges, and check if they connect one 
    		for (Neighbor neighbor: vertex.neighbors) {
    			Edge edge = neighbor.edge;
    		
    			boolean startInMST = verticesMST.contains(edge.start);
    			boolean endInMST = verticesMST.contains(edge.end);
    		
    			//@DEBUG
    			// System.out.println("\t edges (" + edge.start.name + "," + edge.end.name + "," + edge.weight + ")");
    			
    			// Do nothing if both in MST or both not in MST, because they do not connect across the cut
    			if ((startInMST && endInMST) || (!startInMST && !endInMST)) continue;
    			
    			// When start vertex is already in the MST and end vertex is not
    			if (startInMST && !endInMST) {
    				// Check if this is the minimum edge
    				if (edge.weight < minEdgeValue) {
    					minEdgeValue = edge.weight;
    					minVertex = edge.end;
    				}
    			} // startInMST && !endInMST
    			
    			// When start vertex is not already in the MST and end vertex is 
    			if (!startInMST && endInMST) {
    				// Check if this is the minimum edge
    				if (edge.weight < minEdgeValue) {
    					minEdgeValue = edge.weight;
    					minVertex = edge.start;
    				}
    			} // startInMST && !endInMST			
    			
    			
    		} // for all the neighbors
    		
    	} // for all the vertices
    	
    	// 2. Removes the minimum vertex from the list q
    	q.remove(minVertex);
    	
    	// 3. Add the minimum vertex to the verticesMST
    	verticesMST.add(minVertex);
    	
    	// @DEBUG
    	System.out.println("Extracted min " + minVertex.name + ", weight " + minEdgeValue);
    	
		// Returns the minimum vertex
		return minVertex;
	}

	// ----
    
    
    // @feature MSTKruskal
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
                } // else                                            
            } // of if
        } // for all the edges
        
                
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
        } // for all the edges
        
        return newGraph;
        
    } // MSTKruskal    
    // ----
    
    
    // @feature CONNECTED
    public void ConnectedComponents() {
        GraphSearch( new ConnectedWorkSpace() );
    }
    // ----
    
    // @feature STRONGLYCONNECTED
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
        
    } // of Strong Components    
    // ---
    
    // @feature STRONGLYCONNECTED, computation of transpose
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
         
     } // of ComputeTranspose
    // ---
    
    
    // @feature SHORTESTPATH
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
    // ---
    
} // of Graph
