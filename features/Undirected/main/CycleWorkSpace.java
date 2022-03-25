/**
 * GPL Example
 * Runtime variability and monolithic implementation
 * @author Roberto E. Lopez-Herrejon
 * ETS-LOGTI
 */
package main;

// @feature CYCLE
public class CycleWorkSpace extends WorkSpace {
	   public boolean anyCycles;
	    public static final int GRAY  = 1;
    public void aux_checkNeighborAction(int sourceColor, int targetColor, int sourceCycle, int targetCycle) {
        if (sourceColor==GRAY && targetColor==GRAY && sourceCycle!=(targetCycle+1)) 
            anyCycles = true;
    }
} // of CycleWorkSpace
// --
