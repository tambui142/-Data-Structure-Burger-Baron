/**
 * Rand Almaroof - rand3
 * Tam Bui       - tabui
 * Keegan Kell   - keeganjk
 *
 * TCSS 342A - Group Project 02: Compressed Literature
 */

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.BitSet;
import java.util.Scanner;

/**
 * Class Main reads the input txt file and writes the key & compressed files.
 * It is also removes byte order mark at begining of file if present, which
 * messes up file reading.  Tests for...
 * 
 * @author Rand Almaroof - rand3
 * @author Tam Bui - tabui
 * @author Keegan Kell - keeganjk
 * @version 24 February 2017
 */
class Main {
    /** Name of the input file. */
    public static final String INPUT_FILE_NAME = "WarAndPeace.txt";
    
    /** Name of the coding values output file. */
    public static final String CODES_FILE_NAME = "codes.txt";
    
    /** Name of the compressed output file. */
    public static final String OUTPUT_FILE_NAME = "compressed.txt";
    
    /**
     * Reads input file, writes codes file, writes compressed binary file,
     * tests <addTests>.
     *
     * @param args filename to be compressed, pass only 1 file at a time
     * @throws IOException if unable to read/write files
     */
    public static void main(String[] args) throws IOException { //add args****
        InputStream is = null;
        BufferedWriter codesOut = null;
        BufferedOutputStream compressedOut = null;
        Stopwatch sw = new Stopwatch();
        
        try {
            //read un-encoded input file to String field
            File input = new File(INPUT_FILE_NAME);
            is = new FileInputStream(input);
            Scanner fs = new Scanner(is);
            StringBuilder sb = new StringBuilder(120);
            checkByteOrderMark(sb, fs);
            
            while (fs.hasNextLine()) {
				   sb.append(fs.nextLine());
			}
            String fileAsStr = sb.toString();
            
            //build Huffman Tree
            CodingTree ct = new CodingTree(fileAsStr);
            
            //verify compressed version is smaller, if not, return
            //System.out.printf("compressedLength: %d | uncompressedLength: %d\n", 
            //                  ct.bits.length() / 8, fileAsStr.length());
            if (ct.bits.length() / 8 >= fileAsStr.length()) {
                System.out.println("Compressed form will not be smaller. No files written.");
                return;
            }
            
            //write file: Huffman char codes
            FileWriter fw = new FileWriter(CODES_FILE_NAME);
            codesOut = new BufferedWriter(fw);
            codesOut.write(ct.codes.entrySet().toString());
            
            //write file: compressed binary
            File output = new File(OUTPUT_FILE_NAME);
            FileOutputStream fos = new FileOutputStream(output);
            compressedOut = new BufferedOutputStream(fos);
            BitSet bs = new BitSet(ct.bits.length());
            for (int i = 0; i < ct.bits.length(); i++) {
                if (ct.bits.charAt(i) == '1') {
                    bs.set(i); //default bits are 0, so only need to flip 1's
                }
            }
            
            /*
            System.out.printf("Input length: %d | Output length: %d\n", 
                              input.length(), output.length());
            if (input.length() <= output.length()) {
                System.out.println("Compressed form will not be smaller. No files written.");
                return;
            }*/
            
            compressedOut.write(bs.toByteArray());
            
            //display statistics
            long time = sw.elapsedTime();
            System.out.println("\t..:[" + INPUT_FILE_NAME + "]:..");
            System.out.printf("original size:\t\t%d bytes\n", 
                              input.length());
            System.out.printf("compressed size:\t%d bytes\n", 
                              output.length());
            System.out.printf("compression ratio:\t%.2f%%\n",
                              100.0 * input.length() / output.length());
            System.out.printf("elapsed time:\t\t%.4f seconds\n",
                              (double) time / 1000000000);

        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            if (is != null && codesOut != null && compressedOut != null) {
                is.close();
                codesOut.close();
                compressedOut.close();
            }
        }
    }
    
    /**
     * Checks if the first char of the file is a byte order mark.
     *
     * Fixes problem described here:
     * https://stackoverflow.com/questions/1835430/
     *         byte-order-mark-screws-up-file-reading-in-java#1835529
     *
     * @param theStrBldr constructor for the uncompressed input string
     * @param theScanr txt file reading object
     */
    private static void checkByteOrderMark(StringBuilder theStrBldr, 
                                           Scanner theScanr) {
        if (theScanr.hasNext()) {
               String s = theScanr.next();
               if (s.charAt(0) == '\uFEFF') {
                  theStrBldr.append(s.substring(1)); //excludes 1st char
               } else { //no byte order mark, change nothing
                  theStrBldr.append(s);
               }
        }
    }
}