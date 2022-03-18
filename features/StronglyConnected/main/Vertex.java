package main;

public class Vertex {
    public int finishTime;
    public int strongComponentNumber;
	
	public void display() {
        System.out.print( " FinishTime -> " + finishTime + " SCCNo -> " + strongComponentNumber );
        System.out.println();
	}
}