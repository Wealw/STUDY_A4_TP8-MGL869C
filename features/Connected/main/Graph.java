package main;

public class Graph {
    /**
     * Method that executes the selected algorithms on the a starting vertex.
     * @param s vertex from there to start the algorithm execution
     */
	void run(Vertex s) {
		original(s);
   	 	ConnectedComponents();
	}
	
    public void ConnectedComponents() {
        GraphSearch( new ConnectedWorkSpace() );
    }
}