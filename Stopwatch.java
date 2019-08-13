/**
 * Rand Almaroof - rand3
 * Tam Bui       - tabui
 * Keegan Kell   - keeganjk
 *
 * TCSS 342A - Group Project 02: Compressed Literature
 */

/**
 * Class Stopwatch defines a resettable timing device object, uses nanoseconds.
 * 
 * @author Rand Almaroof - rand3
 * @author Tam Bui - tabui
 * @author Keegan Kell - keeganjk
 * @version 24 February 2017
 */
public class Stopwatch {
    /** The starting time. */
    private long myStart;
    
    /**
     * Constructs stopwatch object, logs start time.
     */
    public Stopwatch() {
        myStart = System.nanoTime();
    }
    
    /**
     * Resets stopwatch, updating {@code myStart} to current time.
     */
    public void resetStart() {
        myStart = System.nanoTime();
    }
    
    /**
     * Returns elapsed time.
     *
     * @return elasped time
     */
    public long elapsedTime() {
        return System.nanoTime() - myStart;
    }
}