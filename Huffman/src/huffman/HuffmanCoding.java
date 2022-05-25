package huffman;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;

/**
 * This class contains methods which, when used together, perform the
 * entire Huffman Coding encoding and decoding process
 * 
 * @author Ishaan Ivaturi
 * @author Prince Rawal
 */
public class HuffmanCoding {
    private String fileName;
    private ArrayList<CharFreq> sortedCharFreqList;
    private TreeNode huffmanRoot;
    private String[] encodings;

    /**
     * Constructor used by the driver, sets filename
     * DO NOT EDIT
     * @param f The file we want to encode
     */
    public HuffmanCoding(String f) { 
        fileName = f; 
    }

    /**
     * Reads from filename character by character, and sets sortedCharFreqList
     * to a new ArrayList of CharFreq objects with frequency > 0, sorted by frequency
     */
    public void makeSortedList() {
        StdIn.setFile(fileName);
        int[] chars = new int[128];
        char a;
        int charcount = 0;
        String b = StdIn.readAll();
        int chari=0;
        while(chari < b.length()) {
            a = b.charAt(chari);
            //System.out.print(a); //testing
            chars[(int)a]+=1;
            charcount++;
            chari++;
        }
        //System.out.println(charcount); //testing
        double prob = 0;
        int index = 0;
        char lastChar = 0;
        sortedCharFreqList = new ArrayList<CharFreq>();
        for(int i=0; i<128; i++) {
            //System.out.println(chars[i]); //testing
            if(chars[(char)i] != 0){
                lastChar = (char)i;
                prob = (double)chars[i]/(double)charcount;
                sortedCharFreqList.add(index, new CharFreq((char)i, prob));
                index++;
            }
        }
        if(sortedCharFreqList.size() == 1) {
            if(index != 127) {
                lastChar++;
                sortedCharFreqList.add(index, new CharFreq(lastChar, 0));
            }
            else {
                sortedCharFreqList.add(index, new CharFreq((char)0, 0));
            }
        }
        Collections.sort(sortedCharFreqList);
    }

    /**
     * Uses sortedCharFreqList to build a huffman coding tree, and stores its root
     * in huffmanRoot
     */
    public void makeTree() {
        Queue<TreeNode> source = new Queue<TreeNode>();
        Queue<TreeNode> target = new Queue<TreeNode>();
        int counter = 0;
        while(counter<sortedCharFreqList.size()) {
            source.enqueue(new TreeNode(sortedCharFreqList.get(counter), null, null));
            counter++;
        }
        TreeNode first = source.dequeue();
        TreeNode second = source.dequeue();
        double combinedProbs = first.getData().getProbOcc() + second.getData().getProbOcc();
        huffmanRoot = new TreeNode(new CharFreq(null, combinedProbs), first, second);
        target.enqueue(huffmanRoot);
        while (huffmanRoot.getData().getProbOcc() != 1) {
            //System.out.println(source.size() + " " + target.size() + " " + huffmanRoot.getData().getProbOcc()); //testing
            if(source.isEmpty()) {
                first = target.dequeue();
                second = target.dequeue();
                combinedProbs = first.getData().getProbOcc() + second.getData().getProbOcc();
                huffmanRoot = new TreeNode(new CharFreq(null, combinedProbs), first, second);
                target.enqueue(huffmanRoot);
            } 
            else{
            if(source.peek().getData().getProbOcc()<=target.peek().getData().getProbOcc()) {
                first = source.dequeue();
                //System.out.println("source removed"); //testing
            }
            else { 
                first = target.dequeue();
                //System.out.println("target removed"); //testing
            }
            if(!source.isEmpty()) {
                if(target.isEmpty()) {
                    second = source.dequeue();
                }
                else if(source.peek().getData().getProbOcc()<=target.peek().getData().getProbOcc()) {
                    second = source.dequeue();
                }
                else second = target.dequeue();
            }
            else second = target.dequeue();
            combinedProbs = first.getData().getProbOcc() + second.getData().getProbOcc();
            huffmanRoot = new TreeNode(new CharFreq(null, combinedProbs), first, second);
            target.enqueue(huffmanRoot);
            }
        }
        //System.out.println(source.size() + " " + target.size() + " " + huffmanRoot.getData().getProbOcc()); //testing
    }

    /**
     * Uses huffmanRoot to create a string array of size 128, where each
     * index in the array contains that ASCII character's bitstring encoding. Characters not
     * present in the huffman coding tree should have their spots in the array left null.
     * Set encodings to this array.
     */
    public void makeEncodings() {
        encodings = new String[128];
        Traversal(huffmanRoot.getLeft(), "0");
        Traversal(huffmanRoot.getRight(), "1");
    }

    private void Traversal(TreeNode root, String code) {
        if (root.getData().getCharacter() != null) {
            encodings[(int)root.getData().getCharacter()] = code;
            return;
        }
        if(root.getLeft() != null) {
            Traversal(root.getLeft(), code + "0");
        }
        if(root.getRight() != null) {
            Traversal(root.getRight(), code + "1");
        }
    }

    /**
     * Using encodings and filename, this method makes use of the writeBitString method
     * to write the final encoding of 1's and 0's to the encoded file.
     * 
     * @param encodedFile The file name into which the text file is to be encoded
     */
    public void encode(String encodedFile) {
        String code = null;
        StdIn.setFile(fileName);
        String s = StdIn.readAll();
        int index = 0;
        while (index < s.length()) {
            char a = s.charAt(index);
            if(encodings[(int)a] != null) {
                if(code == null) {
                    code = encodings[(int)a];
                }
                else code = code + encodings[(int)a];
            }
            //System.out.println(code); //testing
            index++;
        }
        writeBitString(encodedFile, code);
    }
    
    /**
     * Writes a given string of 1's and 0's to the given file byte by byte
     * and NOT as characters of 1 and 0 which take up 8 bits each
     * DO NOT EDIT
     * 
     * @param filename The file to write to (doesn't need to exist yet)
     * @param bitString The string of 1's and 0's to write to the file in bits
     */
    public static void writeBitString(String filename, String bitString) {
        byte[] bytes = new byte[bitString.length() / 8 + 1];
        int bytesIndex = 0, byteIndex = 0, currentByte = 0;

        // Pad the string with initial zeroes and then a one in order to bring
        // its length to a multiple of 8. When reading, the 1 signifies the
        // end of padding.
        int padding = 8 - (bitString.length() % 8);
        String pad = "";
        for (int i = 0; i < padding-1; i++) pad = pad + "0";
        pad = pad + "1";
        bitString = pad + bitString;

        // For every bit, add it to the right spot in the corresponding byte,
        // and store bytes in the array when finished
        for (char c : bitString.toCharArray()) {
            if (c != '1' && c != '0') {
                System.out.println("Invalid characters in bitstring");
                return;
            }

            if (c == '1') currentByte += 1 << (7-byteIndex);
            byteIndex++;
            
            if (byteIndex == 8) {
                bytes[bytesIndex] = (byte) currentByte;
                bytesIndex++;
                currentByte = 0;
                byteIndex = 0;
            }
        }
        
        // Write the array of bytes to the provided file
        try {
            FileOutputStream out = new FileOutputStream(filename);
            out.write(bytes);
            out.close();
        }
        catch(Exception e) {
            System.err.println("Error when writing to file!");
        }
    }

    /**
     * Using a given encoded file name, this method makes use of the readBitString method 
     * to convert the file into a bit string, then decodes the bit string using the 
     * tree, and writes it to a decoded file. 
     * 
     * @param encodedFile The file which has already been encoded by encode()
     * @param decodedFile The name of the new file we want to decode into
     */
    public void decode(String encodedFile, String decodedFile) {
        StdOut.setFile(decodedFile);
        String code = readBitString(encodedFile);
        //System.out.println(code); //testing
        TreeNode temp = huffmanRoot;
        int i = 0;
        while(i < code.length()) {
            //System.out.println(temp.getData().getCharacter() + " " + temp.getData().getProbOcc()); //testing
            char a = code.charAt(i);
            if(a == '0') {
                temp = temp.getLeft();
            }
            else {temp = temp.getRight();}
            if(temp.getData().getCharacter() != null) {
                //System.out.print(temp.getData().getCharacter()); //testing
                StdOut.print(temp.getData().getCharacter());
                temp = huffmanRoot;
            }
            i++;
        }
    }

    /**
     * Reads a given file byte by byte, and returns a string of 1's and 0's
     * representing the bits in the file
     * DO NOT EDIT
     * 
     * @param filename The encoded file to read from
     * @return String of 1's and 0's representing the bits in the file
     */
    public static String readBitString(String filename) {
        String bitString = "";
        
        try {
            FileInputStream in = new FileInputStream(filename);
            File file = new File(filename);

            byte bytes[] = new byte[(int) file.length()];
            in.read(bytes);
            in.close();
            
            // For each byte read, convert it to a binary string of length 8 and add it
            // to the bit string
            for (byte b : bytes) {
                bitString = bitString + 
                String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0');
            }

            // Detect the first 1 signifying the end of padding, then remove the first few
            // characters, including the 1
            for (int i = 0; i < 8; i++) {
                if (bitString.charAt(i) == '1') return bitString.substring(i+1);
            }
            
            return bitString.substring(8);
        }
        catch(Exception e) {
            System.out.println("Error while reading file!");
            return "";
        }
    }

    /*
     * Getters used by the driver. 
     * DO NOT EDIT or REMOVE
     */

    public String getFileName() { 
        return fileName; 
    }

    public ArrayList<CharFreq> getSortedCharFreqList() { 
        return sortedCharFreqList; 
    }

    public TreeNode getHuffmanRoot() { 
        return huffmanRoot; 
    }

    public String[] getEncodings() { 
        return encodings; 
    }
}
