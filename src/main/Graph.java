package main; 
import java.util.LinkedList; 

import java.io.IOException; 
import java.io.Reader; 
import java.io.*; 

import java.util.Collections; 
import java.util.Comparator; 

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
	 private void  run__wrappee__Number  (Vertex s) {
		run__wrappee__Prog(s);
		NumberVertices();

	}

	
    /**
     * Method that executes the selected algorithms on the a starting vertex.
     * @param s vertex from there to start the algorithm execution
     */
	void run(Vertex s) {
		 run__wrappee__Number(s);
		 Graph gaux = ShortestPath( s );
		 Graph.stopProfile();
		 gaux.display();
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

	
	
    public void NumberVertices() {
        GraphSearch( new NumberWorkSpace() );
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
         
    }


}
