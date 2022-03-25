package main; 

import java.io.IOException; 

public   class  Main {
		
	
	static StringBuffer configuration = new StringBuffer();

	
	
	/** Definition of arguments
	 * [0] Name of Benchmark file
	 * [1] Name of starting vertex, ex.  v0 
	 * [2] ... names of features that are selected for execution
	 */
    public static void main( String[] args ) {
    	
    	// Step 0: Display the configuration
    	displayConfigurationValues();
    	
    	// Step 1: create graph object
        Graph g = new  Graph();
        
        // Step 2: sets up the benchmark file to read
        g.openBenchmark( args[0] );
         
        // Step 3: reads number of vertices, number of edges and weights
        int num_vertices = 0;
        int num_edges = 0;
        try {
            num_vertices = g.readNumber();
            num_edges = g.readNumber();
            // ignores the additional description files
            g.readNumber();   // undirected, directed
            g.readNumber();   // self loops
            g.readNumber();   // unique
        }catch( IOException e ) {
        	System.out.println("Error while reading benchmark file configuration values");
        	System.exit(0);
        }
        
        System.out.println("Benchmark file read");
        
        // Step 4: reserves space for vertices, edges and weights
        Vertex V[] = new  Vertex[num_vertices];
        int startVertices[] = new int[num_edges];
        int endVertices[] = new int[num_edges];
        
        // @feature WEIGHTED selected
        int weights[] = new int[num_edges];
        
        // Step 5: creates the vertices objects 
        int i=0;
        for ( i=0; i<num_vertices; i++ ) {
            V[i] = new Vertex().assignName( "v"+i );
            g.addVertex( V[i] );
        }
                  
        // Step 6: reads the edges
        try {
            for( i=0; i<num_edges; i++ ) {
                startVertices[i] = g.readNumber();
                endVertices[i] = g.readNumber();
            }
        }catch( IOException e ) {
        	System.out.println("Error while reading the edges");
        	System.exit(0);
        }
        
        // Step 7: reads the weights
    	try {
    		for( i=0; i<num_edges; i++ ) weights[i] = g.readNumber();
    	}catch ( IOException e ) {
    	System.out.println("Error while reading the weigths");
    	System.exit(0);
    	}
        
        // Stops the benchmark reading
        g.stopBenchmark();
         
        // Step 8: Adds the edges
        for ( i=0; i<num_edges; i++ ) {
    		g.addAnEdge( V[startVertices[i]], V[endVertices[i]],weights[i] );
        }
     
        // Executes the selected features
        Graph.startProfile();
        g.run( g.findsVertex( args[1] ) );
           
        Graph.stopProfile();
        g.display();
        Graph.resumeProfile();
            
        // End profiling
            
        Graph.endProfile();
    }

	

	/**
	 * Shows the names of the selected features
	 */
	 private static void  displayConfigurationValues__wrappee__Prog  () {
		System.out.println("Input configuration: " + configuration);
	}

	
	 private static void  displayConfigurationValues__wrappee__Bench  () {
		configuration.append("BENCH ");
		displayConfigurationValues__wrappee__Prog();
	}

	
	 private static void  displayConfigurationValues__wrappee__Undirected  () {
		configuration.append("UNDIRECTED ");
		displayConfigurationValues__wrappee__Bench();
	}

	
	 private static void  displayConfigurationValues__wrappee__Weighted  () {
		configuration.append("WEIGHTED ");
		displayConfigurationValues__wrappee__Undirected();
	}

	
	 private static void  displayConfigurationValues__wrappee__BFS  () {
		configuration.append("BFS ");
		displayConfigurationValues__wrappee__Weighted();
	}

	
	private static void displayConfigurationValues() {
		configuration.append("MSTPRIM ");
		displayConfigurationValues__wrappee__BFS();
	}


}
