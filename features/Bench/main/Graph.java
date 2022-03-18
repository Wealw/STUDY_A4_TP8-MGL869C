package main;

import java.io.IOException;
import java.io.Reader;
import java.io.*;

public class Graph {
    public Reader inFile; // File handler for reading
    public static int ch; // Character to read/write

 
    /**
     * Opens the benchmark file
     * @param FileName
     */
    public void openBenchmark( String FileName ) {
        try {
            inFile = new FileReader( FileName );
        } catch ( IOException e ) {
            System.out.println( "Your file " + FileName + " cannot be read" );
            e.printStackTrace();
            System.exit(0);
        }
    }

    /**
     * Attempts to close the benchmark file.
     * @throws IOException
     */
    public void stopBenchmark()
    {
    	try { inFile.close();
    	} catch(IOException e) {
    		System.out.println("Error while closing the benchmark file");
    		e.printStackTrace();
    		System.exit(0);
    	}
    }

    public int readNumber() throws IOException {
        int index =0;
        char[] word = new char[80];
        int ch=0;

        ch = inFile.read();
        while( ch==32 )
            ch = inFile.read(); // skips extra whitespaces

        while( ch!=-1 && ch!=32 && ch!=10 ) // while it is not EOF, WS, NL
        {
            word[index++] = ( char )ch;
            ch = inFile.read();
        }
        word[index]=0;

        String theString = new String( word );

        theString = new String( theString.substring( 0,index )).trim();
        return Integer.parseInt( theString,10 );
    }

    
    // Timing variables for profiling
    static long last=0, current=0, accum=0;

    
    /**
     * Gets current reading of timing in milliseconds
     */
    public static void startProfile() {
        accum = 0;
        current = System.currentTimeMillis();
        last = current;
    }

    
    /**
     * Stops the profiling by computing the elapsed time
     */
    public static void stopProfile() {
        current = System.currentTimeMillis();
        accum = accum + ( current - last );
    }

    /**
     * Resumes the profiler by reset the current value to current time.
     */
    public  static void resumeProfile() {
        current = System.currentTimeMillis();
        last = current;
    }

    /**
     * Ends the profiling 
     */
    public static void endProfile() {
        current = System.currentTimeMillis();
        accum = accum + ( current-last );
        System.out.println( "Time elapsed: " + accum + " milliseconds" );
    }
}