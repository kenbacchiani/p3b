/**
 * Filename:   MyProfiler.java
 * Project:    p3b-201901     
 * Authors:    Ken Bacchiani, Lecture 002
 *
 * Semester:   Spring 2019
 * Course:     CS400
 * 
 * Due Date:   March 28, 2019 10pm
 * Version:    1.0
 * 
 * Credits: https://www.oracle.com/technetwork/java/javaseproducts/mission-control/java-mission-control-1998576.html
 * 
 * Bugs:       
 */

// Used as the data structure to test our hash table against
import java.util.TreeMap;

public class MyProfiler<K extends Comparable<K>, V> {
    HashTableADT<K, V> hashtable;
    TreeMap<K, V> treemap;
    
    /**
     * Constructor for a new profiler. Creates a new hashtable and treemap.
     */
    public MyProfiler() {
        hashtable = new HashTable();
        treemap = new TreeMap();
    }
    
    /**
     * Runs insert for both the treemap and the hashtable
     * @param key key to be inserted
     * @param value value to be inserted
     * @throws IllegalNullKeyException If the key to be added is null
     * @throws DuplicateKeyException if the key already exists in the data structure.
     */
    public void insert(K key, V value) throws IllegalNullKeyException, DuplicateKeyException {
        hashtable.insert(key, value);
        treemap.put(key, value);
    }
    
    /**
     * Runs the get method for both the hashtable and the treemap
     * @param key key of the value to be retrieved
     * @throws IllegalNullKeyException if the passed key is null
     * @throws KeyNotFoundException if the key doesn't exist in the data structure
     */
    public void retrieve(K key) throws IllegalNullKeyException, KeyNotFoundException {
      hashtable.get(key);
      treemap.get(key);
    }
    
    /**
     * The main driver to run the insert and retrieve methods
     * @param args the value for the number of keys to be inserted/retrieved
     */
    public static void main(String[] args) {
        try {
            int numElements = Integer.parseInt(args[0]);  //gets the numElements from input
            MyProfiler<Integer, Integer> profile = new MyProfiler<Integer, Integer>();
            for(int i=0; i < numElements; i++) {  //runs the amount of times given by 
              profile.insert(i, i);               //numElements
              profile.retrieve(i);
            }   
            String msg = String.format("Inserted and retreived %d (key,value) pairs", numElements);
            System.out.println(msg);
        }
        catch (Exception e) {
            System.out.println("Usage: java MyProfiler <number_of_elements>");
            System.exit(1);
        }
    }
}
