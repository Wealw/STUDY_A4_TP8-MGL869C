package main; 

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

	 // weight so far from s to it
	
	public void display  () {
    	System.out.print( " Pred " + pred + " Key " + key + " " );
        System.out.println();
	}

	
	
    public boolean visited;

	
    static LinkedList<Vertex> Queue =  new LinkedList<Vertex>();

	
    
    public void init_vertex( WorkSpace w ) {
        visited = false;
        w.init_vertex(this);
    }

	
    
    public void bftNodeSearch( WorkSpace w ) {
        Vertex  v;
        Vertex  header;
      
        // Step 1: if preVisitAction is true or if we've already
        //         visited this node
        w.preVisitAction(this);               
        if (visited) return;

        // Step 2: Mark as visited, put the unvisited neighbors in the queue 
        //     and make the recursive call on the first element of the queue
        //     if there is such if not you are done
        visited = true;
         
        // Step 3: do postVisitAction now, you are no longer going through the
        // node again, mark it as black
        w.postVisitAction(this);

        // enqueues the vertices not visited
        for (Neighbor n : neighbors) {
            v = n.end;

            // if the neighbor has not been visited then enqueue 
            if (!v.visited) Queue.add(v);                        
        }

        // while there is something in the queue
        while(Queue.size()!=0) {
        	header = Queue.get(0);
            Queue.remove(0);
            header.bftNodeSearch( w );
        } // while there is a vertex pending to visit
                  
    }

	
    public String pred;

	 // the predecessor vertex if any
    public int key;


}
