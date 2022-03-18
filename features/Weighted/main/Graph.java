package main;

import java.io.IOException;

public class Graph {
    /** 
     * Adds an edge with weights if Weighted layer is not present
     * @param start vertex of the edge
     * @param end vertex of the edge
     */
    public void addAnEdge( Vertex start,  Vertex end, int weight ) {
        Edge theEdge = new Edge(start, end, weight );
        addEdge(theEdge);
    }
}