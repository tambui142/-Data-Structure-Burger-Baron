/**
 * Rand Almaroof - rand3
 * Tam Bui       - tabui
 * Keegan Kell   - keeganjk
 *
 * TCSS 342A - Group Project 02: Compressed Literature
 */

/**
 * Class HuffNode defines the underlying nodes in the Huffman algorithm, it consists of
 * links parent, left, and right as well as value (char as int) and weight fields.
 * Non-leaf nodes should be set to a non-char (negative) value, typically -2.
 * 
 * @author Rand Almaroof - rand3
 * @author Tam Bui - tabui
 * @author Keegan Kell - keeganjk
 * @version 24 February 2017
 */
class HuffNode implements Comparable<HuffNode> {
    /** Character value as int (used to allow end-of-file mark). */
    private int myValue;
    
    /** Weight of the node, for leaves this is the character count, for non-leaves it is
     * the combined 'weight' of the sub-tree at that node.
     */
    private int myWeight;
    
    /** Link to the left child node. */
    protected HuffNode myLeft;
    
    /** Link to the right child node. */
    protected HuffNode myRight;
    
    /** Link to the parent node. */
    protected HuffNode myParent;
    
    /**
     * Constructs HuffNode initialized to passed value, weight and link parameters.
     *
     * @param theValue char as int if leaf, use -2 for non-leaves
     * @param theWeight leaves: char count; non-leaves: combined 'weight' of sub-tree
     * @param theLeft link to left child node
     * @param theRight link to right child node
     * @param theParent link to parent node
     */
    HuffNode(int theValue, int theWeight, HuffNode theLeft, HuffNode theRight, 
             HuffNode theParent) {
        myValue = theValue;
        myWeight = theWeight;
        myLeft = theLeft;
        myRight = theRight;
        myParent = theParent;
    }
    
    /**
     * Compares {@code this} node with {@code theRhs} node based on weight, returns 
     * positive if {@code this} object is weighted more, 0 if equals, and negative 
     * if {@code theRhs} is weighted more.
     *
     * @param theRhs other object to compare this object to
     * @return positive if this weighted more, 0 if equal, negative if other weighted more
     */
    @Override
    public int compareTo(HuffNode theRhs) {
        return myWeight - theRhs.myWeight;
    }
    
    /**
     * Returns value (char as int) of node.
     *
     * @return value (char as int) of node
     */
    public int getValue() {
        return myValue;
    }
    
    /**
     * Returns weight of node.
     *
     * @return weight of node
     */
    public int getWeight() {
        return myWeight;
    }
}