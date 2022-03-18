package main;

import java.util.LinkedList;

public class Vertex {
    public  Vertex representative;
    public LinkedList<Vertex> members;
    
	public void display() {
        if ( representative == null )
            System.out.print( "Rep null " );
        else
            System.out.print( " Rep " + representative.name + " " );
        System.out.println();
	}
}