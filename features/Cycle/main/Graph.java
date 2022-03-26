package main;

public class Graph {
	public boolean directed;
	
	public void setDirected(){}
    /**
     * Method that executes the selected algorithms on the a starting vertex.
     * @param s vertex from there to start the algorithm execution
     */
	void run(Vertex s) {
		original(s);
		System.out.println( " Cycle? " + CycleCheck() );

	}
	
    public boolean CycleCheck() {
        CycleWorkSpace c = new CycleWorkSpace();
        c.setDirected(directed);
        GraphSearch( c );
        return c.anyCycles;
    }
}