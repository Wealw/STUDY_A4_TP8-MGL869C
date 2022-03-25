package main; 

import java.util.LinkedList; 

import java.io.IOException; 
import java.io.Reader; 
import java.io.*; 
import java.lang.Integer; 

public   class  Graph {
	

	
	// **************************************************
	// Management of graph file for benchmarking
    // ************************************************************************
     
    // Lists of vertices and edges
    public LinkedList<Vertex> vertices;

	
    public LinkedList<Edge> edges;

	
    
     private void  run__wrappee__Prog  (Vertex s) {
    	
    }

	
    /**
     * Method that executes the selected algorithms on the a starting vertex.
     * @param s vertex from there to start the algorithm execution
     */
	void run(Vertex s) {
		run__wrappee__Prog(s);
		 Graph gaux = Prim( s );
		 System.out.println("Displaying Prim result ");
		 Graph.stopProfile();
		 gaux.display();
		 System.out.println("End displaying Prim result ");
		 Graph.resumeProfile();

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
    public void addEdge  ( Edge the_edge ) {
        Vertex start = the_edge.start;
        Vertex end = the_edge.end;
        edges.add( the_edge );
        
        end.addNeighbor( new  Neighbor( start,the_edge ) );
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

	
    public Reader inFile;

	 // File handler for reading
    public static int ch;

	 // Character to read/write

 
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

	
    /** 
     * Adds an edge with weights if Weighted layer is not present
     * @param start vertex of the edge
     * @param end vertex of the edge
     */
    public void addAnEdge( Vertex start,  Vertex end, int weight ) {
        Edge theEdge = new Edge(start, end, weight );
        addEdge(theEdge);
    }

	
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
            	System.out.println("BFS " + v.name);
            	v.bftNodeSearch( w );
            }
        } 
    }

	
	
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
    }

	
    
    public  Graph Prim( Vertex root ) {
        // 1. Queue <- V[G], copy the vertex in the graph in the priority queue

        // Represents the vertices already in the computed MST so far ,  V-Q in algorithm
        LinkedList<Vertex> verticesMST = new LinkedList<Vertex>();
        
        LinkedList<Vertex> Q = new LinkedList<Vertex>();
        
        // Add all the vertices to the list
        for (Vertex x : vertices) 
        	// this means, if this is not the root
        	if (x!=root) Q.add(x);
       
        
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
        	
            System.out.println("In loop Vertices in Q " + Q.size());
            System.out.println("In loop Vertices in verticesMST " + verticesMST.size());
            
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

                isNeighborInQueue = Q.contains(v);
                
                wuv = en.weight;
                                                 
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
        
    }

	
    
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
    		
    		// For all its neighbors get their edges, and check if they connect one 
    		for (Neighbor neighbor: vertex.neighbors) {
    			Edge edge = neighbor.edge;
    		
    			boolean startInMST = verticesMST.contains(edge.start);
    			boolean endInMST = verticesMST.contains(edge.end);
    			
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


}
