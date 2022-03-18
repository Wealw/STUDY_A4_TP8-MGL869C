package main;

class Edge {
    public int weight;
	
    public Edge( Vertex the_start,  Vertex the_end, int the_weight ) {
    	this(the_start,the_end );
        weight = the_weight;
    }
	
    public void display() {
        System.out.print( " start=" + start.name + " end=" + end.name );
        System.out.print( " Weight=" + weight );
        System.out.println();
    }
    
    public String toString() {
        String result = " start=" + start.name + " end=" + end.name + " weight=" + weight;
        result = result + " weight=" + weight;
        return result;
    }
	
	public void adjustAdorns(Edge the_edge) {
		weight = the_edge.weight;
	}
}
