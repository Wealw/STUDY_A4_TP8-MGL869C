package main;

public class Vertex {
	public boolean visited;
	
    public Vertex() {
        visited = false;
    }
    
    public void init_vertex( WorkSpace w ) {
        visited = false;
        w.init_vertex(this);
    }
    
    public void dftNodeSearch( WorkSpace w ) {
        Vertex v;

        // Step 1: Do preVisitAction. 
        // If we've already visited this node return
        w.preVisitAction(this);
        if (visited) return;

        // Step 2: else remember that we've visited and 
        //         visit all neighbors
        visited = true;
         
        for (Neighbor n : neighbors) {
            v = n.end;
            w.checkNeighborAction(this,v);
            v.dftNodeSearch(w);
        }
             
        // Step 3: do postVisitAction now
        w.postVisitAction(this);
    } 
}