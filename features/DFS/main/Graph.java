package main;

public class Graph {
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