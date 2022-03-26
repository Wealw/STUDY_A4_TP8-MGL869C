package main; 

import java.util.LinkedList; 

import java.io.IOException; 
import java.io.Reader; 
import java.io.*; /**
 * TODO description
 */
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
		System.out.println( " Cycle? " + CycleCheck() );

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

	
	public boolean directed  ;

	
	
	public void setDirected  (){
		directed = true;
	}

	
	
    public boolean CycleCheck() {
        CycleWorkSpace c = new CycleWorkSpace();
        c.setDirected(directed);
        GraphSearch( c );
        return c.anyCycles;
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
            	System.out.println("DFS " + v.name);
            	v.dftNodeSearch( w );
            }
        } 
    }


}
