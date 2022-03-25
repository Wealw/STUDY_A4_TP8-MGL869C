/**
 * GPL Example
 * Runtime variability and monolithic implementation
 * @author Roberto E. Lopez-Herrejon
 * ETS-LOGTI
 */
package main;

// @feature CYCLE
public class CycleWorkSpace extends WorkSpace{	     
    public void aux_checkNeighborAction(int sourceColor, int targetColor, int sourceCycle, int targetCycle) {
    	original(sourceColor, targetColor, sourceCycle, targetCycle);
    	if (sourceColor==GRAY && targetColor==GRAY) anyCycles = true;
    }
} // of CycleWorkSpace
// --
