/**
 * Rand Almaroof - rand3
 * Tam Bui       - tabui
 * Keegan Kell   - keeganjk
 *
 * TCSS 342A - Group Project 02: Compressed Literature
 */

import java.util.Map;
import java.util.PriorityQueue;
import java.util.TreeMap;

/**
 * Class CodingTree takes an uncompressed string, creates binary {@code codes} for
 * characters, and then builds the {@code bits} which is the compressed string.
 * 
 * @author Rand Almaroof - rand3
 * @author Tam Bui - tabui
 * @author Keegan Kell - keeganjk
 * @version 24 February 2017
 */
class CodingTree {    
    /** Char value used in non-char HuffNodes containing only weight information. */
    public static final int NOT_CHAR_CODE = -2;
    
    /** Number of potential characters. */
    public static final int DIFF_CHAR_BYTES = 256;
    
    /** End of File location. It is at end of potential characters. */
    public static final int EOF = DIFF_CHAR_BYTES;

    /** Maps characters to their Huffman-encoded binaryCode. */
    public Map<Character, String> codes;
    
    /** The message encoded using the Huffman codes. */
    public String bits;
    
    /** Counter for storing character frequency. */
    private CharCounter myCounts;
    
    /** Huffman Tree nodes, non-leaves' {@code #getValue()} is NOT_CHAR_CODE. */
    private HuffNode[] myNodes;
    
    /** Root of the Huffman Tree. */
    private HuffNode myRoot;        //must be initialized last (tree built bottom up)

    /**
     * Constructs CodingTree with {@code theMessage}, implements Huffman algorithm.
     *
     * @param theMessage uncompressed string
     */
    public CodingTree(String theMessage) {
        //initialize fields
        codes = new TreeMap<Character, String>();
        myNodes = new HuffNode[DIFF_CHAR_BYTES + 1];
        myCounts = new CharCounter(theMessage);
        PriorityQueue<HuffNode> pq = new PriorityQueue<HuffNode>();
        
        //find chars that have > 0 counts, make HuffNodes & add to priority queue
        for(int i = 0; i < DIFF_CHAR_BYTES; i++) {
            if (myCounts.getCount(i) > 0) {
                myNodes[i] = new HuffNode(i, myCounts.getCount(i), null, null, null);
                pq.add(myNodes[i]);
            }
        }
        
        //add end-of-file symbol
        myNodes[EOF] = new HuffNode(EOF, 1, null, null, null);
        pq.add(myNodes[EOF]);
        
        //merge trees until only one remains
        while (pq.size() > 1) {
            HuffNode n1 = pq.remove();
            HuffNode n2 = pq.remove();
            HuffNode result = new HuffNode(NOT_CHAR_CODE, 
                                          n1.getWeight() + n2.getWeight(), n1, n2, null);
            n1.myParent = n2.myParent = result;
            pq.add(result);
        }
        
        //set the only remaining tree in the priority queue to root
        myRoot = pq.element();
        buildEncodingCodes();
        buildEncodedBits(theMessage);
    }
    
    /**
     * Returns binary coded string for {@code theCh}.
     *
     * @param theCh the character to get the code of
     * @return binary coded string
     */
    public String getCode(int theCh) {
        HuffNode current = myNodes[theCh];
        if (current == null) {
            return null;
        }
        
        StringBuilder sb = new StringBuilder(100);
        HuffNode parent = current.myParent;
        
        while(parent != null) {
            if (parent.myLeft == current) {
                sb.append('0');
            } else {
                sb.append('1');
            }
            current = current.myParent;
            parent = current.myParent;
        }
        
        return sb.reverse().toString(); //appended from bottom-up, must reverse;
    }
    
    /**
     * Builds map containing key-value pairs char-binaryCode.
     */
    public void buildEncodingCodes() {
        for(int i = 0; i < DIFF_CHAR_BYTES; i++) {
            if (myCounts.getCount(i) > 0) {
                codes.put((char) i, getCode(i));
            }
        }
    }
    
    /**
     * Builds string {#code bits} consisting of the compressed {@code theMessage}. 
     *
     * @param theMessage desired string to compress
     */
    public void buildEncodedBits(String theMessage) {
        StringBuilder sb = new StringBuilder(theMessage.length());
        for (int i = 0; i < theMessage.length(); i++) {
            sb.append(codes.get(theMessage.charAt(i)));
        }
        bits = sb.toString();
    }
}