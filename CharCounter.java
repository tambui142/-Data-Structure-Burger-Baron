/**
 * Rand Almaroof - rand3
 * Tam Bui       - tabui
 * Keegan Kell   - keeganjk
 *
 * TCSS 342A - Group Project 02: Compressed Literature
 */

import java.io.IOException;
import java.io.InputStream;

/**
 * Class CharCounter is a character counting object, uses int's implicitly for the
 * characters to allow for end-of-file mark.
 * 
 * @author Rand Almaroof - rand3
 * @author Tam Bui - tabui
 * @author Keegan Kell - keeganjk
 * @version 24 February 2017
 */
class CharCounter {
    /** Number of potential characters. */
    public static final int DIFF_CHAR_BYTES = 256;

    /** Storage for the character counts. */
    private int[] myCounts;
    
    /**
     * Constructs character counter object with space for end-of-file mark.
     */
    public CharCounter() {
        myCounts = new int[DIFF_CHAR_BYTES + 1];
    }
    
    /**
     * Constructs character counter object and counts passed string {@code s} into it.
     *
     * @param s string to count
     */
    public CharCounter(String s) {
        this();
        
        for (int i = 0; i < s.length(); i++) {
            //System.out.printf("i: %d, s.length(): %d s.charAt(i): %c, myCounts[s.charAt(i)]: %d\n", 
            //                  i, s.length(), s.charAt(i), myCounts[s.charAt(i)]);
            myCounts[s.charAt(i)]++;
        }
    }
    
    /**
     * Returns count of {@code theCh}.
     *
     * @param theCh character to return count of
     * @return count of passed character
     */
    public int getCount(int theCh) {
        return myCounts[theCh & 0xff];
    }
    
    /**
     * Sets count of {@code theCh} to {@code theCount}.  Used for testing.
     *
     * @param theCh character to set
     * @param theCount number to set count to
     */
    public void setCount(int theCh, int theCount) {
        myCounts[theCh & 0xff] = theCount;
    }
}