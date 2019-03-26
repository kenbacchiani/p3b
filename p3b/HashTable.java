/**
 * Project: p3a Hash Table
 * Due Date: March 14, 2019
 * Lecture: 002
 * Email: bacchiani@wisc.edu
 * @author kenbacchiani 
 * 
 */
import java.util.LinkedList;

//
// TODO: describe the collision resolution scheme you have chosen
// I have used buckets of linked lists as the collision resolution system.
//
// TODO: explain your hashing algorithm here
// First I got the absolute value of the hash code algorithm for the key. If that index is
// Out of bounds of the array, I divide the key by 2 until the index fits in the array.
//

/**
 * This class represents a hash table
 * @author kenbacchiani
 *
 * @param <K> Keys of the nodes in the hashtable
 * @param <V> Value of the nodes in the hashtable.
 */
public class HashTable<K extends Comparable<K>, V> implements HashTableADT<K, V> {
  private double loadFactor; // keys/capacity
  private double loadFactorThreshold; // at what point the table should resize
  private int capacity; // capacity of the table
  private int numKeys; // number of keys in the array
  private bucketNode[] table; // Array to hold head nodes of linked lists
  /**
   * This class represents a bucketNode to be combined into linked lists
   * @author kenbacchiani
   *
   * @param <K> Keys for the node
   * @param <V> Value for the node
   */
  private class bucketNode<K extends Comparable<K>, V> { 
    K key; // key value of the node
    V value; // value of the node
    bucketNode nextNode; // next node in the chain

    /**
     * Constructor of a bucketNode
     * 
     * @param key key of the bucketnode to be made
     * @param value value of the bucketNode to be made
     */
    public bucketNode(K key, V value) {
      this.key = key;
      this.value = value;
      nextNode = null;
    }

    /**
     * Constructor for a bucketnode with a known next node
     * 
     * @param key key of the bucketnode to be made
     * @param value value of the bucketnode to be made
     * @param nextNode the next bucketnode in the chain
     */
    public bucketNode(K key, V value, bucketNode nextNode) {
      this.key = key;
      this.value = value;
      nextNode = nextNode;
    }

    /**
     * Sets the next bucketnode
     * 
     * @param nextNode the next bucketnode to be set
     */
    public void setNext(bucketNode nextNode) {
      this.nextNode = nextNode;
    }

    /**
     * @return returns the next bucketNode
     */
    public bucketNode getNext() {
      return (nextNode);
    }

    /**
     * @return returns the key of the bucketNode
     */
    public K getKey() {
      return (key);
    }

    /**
     * Sets the key of the bucketnode
     * 
     * @param newKey the new key of the bucketNode
     */
    public void setKey(K newKey) {
      key = newKey;
    }

    /**
     * @return Returns the value of the bucketNode
     */
    public V getVal() {
      return (value);
    }

    /**
     * Sets a new value of the bucketNode
     * 
     * @param newVal new value of the bucketNode
     */
    public void setVal(V newVal) {
      value = newVal;
    }
  }

  /**
   * No argument constructor of the Hash Table. Sets loadFactor threshold to 0.8 and initial
   * capacity to 11.
   */
  public HashTable() {
    loadFactor = 0;
    loadFactorThreshold = 0.8;
    capacity = 11;
    numKeys = 0;
    table = new bucketNode[11];
  }

  /**
   * Constructor for hashtable with set initial capacity and LoadFactorThreshold
   * 
   * @param initialCapacity Initial capacity of the table
   * @param loadFactorThreshold loadfactor threshold of the table.
   */
  public HashTable(int initialCapacity, double loadFactorThreshold) {
    capacity = initialCapacity;
    this.loadFactorThreshold = loadFactorThreshold;
    loadFactor = 0;
    numKeys = 0;
    table = new bucketNode[capacity];
  }

  /**
   * Inserts a new node into the table given a certain key and value combo.
   * @param key key of the node to be added
   * @param value value of the node to be added
   * @throws IllegalNullKeyException if the passed key is null
   * @throws DuplicateKeyException if the key is already present in the table.
   */
  @Override
  public void insert(K key, V value) throws IllegalNullKeyException, DuplicateKeyException {
    if (key == null) {
      throw new IllegalNullKeyException();
    }
    int index = Math.abs(getHashCode(key));  //gets the hash index of the key
    while (index > capacity) {  //makes sure that index is in bounds
      index = index / 2;
    }
    bucketNode runner = table[index];
    if (runner == null) {
      table[index] = new bucketNode(key, value);
      numKeys++;
      loadFactor = getLoadFactor();
      if (loadFactor >= loadFactorThreshold) {  //checks if the threshold is reached
        increaseCapacity();
      }
      return;  //ends method from running further.
    }
    if (runner.getKey() == key) {
      throw new DuplicateKeyException();  
    }
    while (runner.getNext() != null) {  //traverse to the end of the linkedList
      runner = runner.getNext();
      if (runner.getKey() == key) {
        throw new DuplicateKeyException();
      }
    }
    runner.setNext(new bucketNode(key, value));  //Puts the new node at the end of the list.
    numKeys++;
    loadFactor = getLoadFactor();
    if (loadFactor >= loadFactorThreshold) {  //Checks if the threshold was reached.
      increaseCapacity();
    }
  }

  /**
   * Removes a certain node from the table, if present
   * 
   * @param key the key of the node to be removed
   * @return returns true if the node was removed, false if not
   * @throws IllegalNullKeyException if the inserted key is null
   */
  @Override
  public boolean remove(K key) throws IllegalNullKeyException {
    if (key == null) {
      throw new IllegalNullKeyException();
    }
    int index = Math.abs(getHashCode(key)); // gets the hash index of the key
    while (index > capacity) { // makes sure the index isn't out of bounds
      index = index / 2;
    }
    bucketNode runner = table[index];
    if (runner == null) { // if nothing is at the index, no node to be removed
      return (false);
    }
    if (runner.getKey().equals(key)) {
      runner = runner.getNext(); // removes reference of node
      numKeys--;
      return (true);
    }
    while (runner.getNext() != null) { // traverses the linked list
      if (runner.getNext().getKey().equals(key)) {
        runner.setNext(runner.getNext().getNext()); // removes reference of node
        numKeys--;
        return (true);
      }
      runner = runner.getNext();
    }
    return (false); // if the node wasn't present
  }

  /**
   * Get method to return the value of a node with a given key.
   * 
   * @param key the key of the node for which the value is to be returned.
   * @return returns the value of the node of key.
   * @throws IllegalNullKeyException if the key inserted is null
   * @throws KeyNotFoundException if the key isn't present in the table
   */
  @Override
  public V get(K key) throws IllegalNullKeyException, KeyNotFoundException {
    if (key == null) {
      throw new IllegalNullKeyException();
    }
    int index = Math.abs(getHashCode(key)); // Gets the hash index
    while (index > capacity) { // if the index is larger than capacity
      index = index / 2;
    }
    bucketNode runner = table[index];
    if (runner == null) {
      throw new KeyNotFoundException();
    }
    if (runner.getKey().equals(key)) {
      return (V) (runner.getVal());
    }
    while (runner.getNext() != null) { // traverses through the linked list to find key
      if (runner.getNext().getKey().equals(key)) {
        return (V) (runner.getVal());
      }
      runner = runner.getNext();
    }
    if (runner.getKey().equals(key)) { // checks the last node in the list
      return (V) (runner.getVal());
    }
    throw new KeyNotFoundException(); // if the key wasn't there
  }

  /**
   * @return returns the number of keys in the table.
   */
  @Override
  public int numKeys() {
    return (numKeys);
  }

  /**
   * returns the loadFactor threshold of the table
   * 
   * @return returns the loadfactor threshold of the table
   */
  @Override
  public double getLoadFactorThreshold() {
    return (loadFactorThreshold);
  }

  /**
   * Returns the loadFactor of the table
   * 
   * @return returns the loadFactor of the table.
   */
  @Override
  public double getLoadFactor() {
    if (capacity == 0) { // if the capacity is 0, the load Factor is 0
      return (0);
    }
    return ((double) numKeys / capacity);
  }

  /**
   * Returns the capacity of the table.
   * 
   * @return returns the capacity of the array.
   */
  @Override
  public int getCapacity() {
    return (capacity);
  }

  /**
   * @return returns the collision resolution of the hashTable.
   */
  @Override
  public int getCollisionResolution() {
    return (8);
  }

  /**
   * Returns the hash code for a given key, which is defined as hashcode & capacity.
   * 
   * @param key key to get the hashcode for
   * @return returns the hashcode index for the key
   */
  public int getHashCode(K key) {
    return (key.hashCode() % capacity);
  }

  /**
   * Increases the capacity of the table if the load factor is reached. The new table size is 2*
   * capacity + 1.
   */
  public void increaseCapacity() {
    bucketNode[] increased = new bucketNode[capacity * 2 + 1]; // The new larger table
    for (int i = 0; i < capacity; i++) { // Goes through all of the indexes of orig table
      if (table[i] != null) { // If there is a node in the certain index
        increased[i] = table[i]; // copies the node over
        bucketNode runner = table[i];
        bucketNode nRunner = increased[i];
        while (runner.getNext() != null) {
          nRunner.setNext(runner.getNext()); // adds all of the successive nodes of the index
          runner = runner.getNext();
          nRunner = nRunner.getNext();
        }
      }
    }
    capacity = 2 * capacity + 1;
    loadFactor = getLoadFactor();
    table = increased;
  }
}
