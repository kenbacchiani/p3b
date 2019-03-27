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
import static org.junit.jupiter.api.Assertions.*; // org.junit.Assert.*;
import org.junit.jupiter.api.Assertions;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import java.util.Random;

/**
 * Tester class for Hashtable which tests the various methods of the Hashtable class.
 * @author kenbacchiani
 *
 */
public class HashTableTest {
  /**
   * Runs before all of the test methods
   * @throws Exception Exception if the code here throws an exception
   */
  @Before
  public void setUp() throws Exception {

  }

  /**
   * Runs after each test method
   * @throws Exception If the code throws an exception.
   */
  @After
  public void tearDown() throws Exception {

  }

  /**
   * Tests that a HashTable returns an integer code indicating which collision resolution strategy
   * is used. REFER TO HashTableADT for valid collision scheme codes.
   */
  @Test
  public void test000_collision_scheme() {
    HashTableADT htIntegerKey = new HashTable<Integer, String>();
    int scheme = htIntegerKey.getCollisionResolution();
    if (scheme < 1 || scheme > 9)
      fail("collision resolution must be indicated with 1-9");
  }

  /**
   * IMPLEMENTED AS EXAMPLE FOR YOU Tests that insert(null,null) throws IllegalNullKeyException
   */
  @Test
  public void test001_IllegalNullKey() {
    try {
      HashTableADT htIntegerKey = new HashTable<Integer, String>();
      htIntegerKey.insert(null, null);
      fail("should not be able to insert null key");
    } catch (IllegalNullKeyException e) {
      /* expected */ } catch (Exception e) {
      fail("insert null key should not throw exception " + e.getClass().getName());
    }
  }

  /**
   * Tests the insert and get functionality of the hashTable.
   */
  @Test
  public void test002_Insert_And_Get() {
    try {
      HashTableADT test = new HashTable<Integer, String>();
      test.insert(1, "2");
      test.insert(2, "3");
      test.insert(3, "4");
      if (test.get(1) != "2") {  //checks if get returns the correct values
        fail("insert or get didn't work");
      }
      if (test.get(2) != "3") {
        fail("insert or get didn't work");
      }
      if (test.get(3) != "4") {
        fail("insert or get didn't work");
      }
    } catch (Exception e) {
      fail("Exception shouldn't be thrown");
    }
  }

  /**
   * This method tests the loadFactor of the hashtable and makes sure its calculated
   * correctly.
   */
  @Test
  public void test003_test_loadFactor() {
    try {
      HashTableADT test = new HashTable<Integer, String>();
      test.insert(1, "2");
      test.insert(2, "3");
      test.insert(3, "4");
      if (test.getLoadFactor() != (double) 3 / 11) {
        fail("loadFactor not correct");
      }
    } catch (Exception e) {
      fail("Exception shouldn't be thrown");
    }
  }

  /**
   * This tests if the correct number of Keys is tracked, through get and remove methods.
   */
  @Test
  public void test004_test_numKeys() {
    try {
      HashTableADT test = new HashTable<Integer, String>();
      if (test.numKeys() != 0) {
        fail("numKeys not correct");
      }
      test.insert(1, "2");
      if (test.numKeys() != 1) {
        fail("numKeys not correct");
      }
      test.insert(2, "3");
      if (test.numKeys() != 2) {
        fail("numKeys not correct");
      }
      test.insert(3, "4");
      if (test.numKeys() != 3) {
        fail("numKeys not correct");
      }
      test.remove(2);
      if(test.numKeys() != 2) {
        fail("numKeys not correct");
      }
    } catch (Exception e) {
      fail("Exception shouldn't be thrown");
    }
  }

  /**
   * This tests if the insert method can correctly detect and throw the correct exception
   * for duplicate keys.
   */
  @Test
  public void test005_test_duplicate_key() {
    try {
      HashTableADT test = new HashTable<Integer, String>();
      test.insert(1, "2");
      test.insert(2, "3");
      test.insert(1, "4");
    } catch (DuplicateKeyException q) {
      //Expected Exception
    } catch (Exception e) {
      fail("Correct Exception wasn't thrown");
    }
  }

  /**
   * This tests if the capacity is correctly tracked and resized at the correct time to 
   * the correct value.
   */
  @Test
  public void test006_test_capcaity() {
    try {
      HashTableADT test = new HashTable<Integer, String>(2, 0.5);
      test.insert(1, "2");
      test.insert(2, "3");
      if (test.getCapacity() != 5) {  //capacity should be 2* original + 1
        fail("resize not correct");
      }
    } catch (Exception e) {
      fail("Correct Exception wasn't thrown");
    }
  }
  
  /**
   * This methods tests if the remove works correctly.
   */
  @Test
  public void test007_test_remove() {
    try {
      HashTableADT test = new HashTable<Integer, String>();
      test.insert(1, "2");
      if(test.remove(2) != false) {
        fail("remove didn't work correctly on value not present.");
      }
      test.insert(2, "3");
      if (test.remove(2) != true) {
        fail("remove didn't work correctly on an added value");
      }
    } catch (Exception e) {
      fail("Exception shouldn't be thrown");
    }
  }
}






