package main; 

// undirected and Directed have the same code


import java.util.LinkedList; 

public   class  Vertex {
	
    public LinkedList<Neighbor> neighbors;

	
    public String name;

	
	
    public Vertex  () {
        name      = null;
        neighbors = new LinkedList<Neighbor>();
    
        visited = false;
    }

	
    
    public  Vertex assignName( String name ) {
        this.name = name;
        return this;
    }

	
   
    public void addNeighbor( Neighbor n ) {
        neighbors.add( n );
    }

	

    public boolean equals(Object o) {
    	boolean result = false;
    	    	if (!(o instanceof Vertex)) return result;
    	Vertex v = (Vertex) o;
    	if (v.name==this.name) result = true;
    	return result;
    }

	
	public boolean visited;

	
    
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

	
    public int VertexCycle;

	
    public int VertexColor;

	
	public void display  () {
		System.out.print( " Node " + name + " connected to: " );
        for (Neighbor theNeighbor : neighbors) {
            Vertex v = theNeighbor.end;
            System.out.print( v.name + ", " );
        }
        System.out.println();
	}


}
