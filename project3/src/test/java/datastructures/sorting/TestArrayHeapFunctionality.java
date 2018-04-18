package datastructures.sorting;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import misc.BaseTest;
import misc.exceptions.EmptyContainerException;
import datastructures.concrete.ArrayHeap;
import datastructures.interfaces.IPriorityQueue;
import org.junit.Test;

import java.util.PriorityQueue;

/**
 * See spec for details on what kinds of tests this class should include.
 */
public class TestArrayHeapFunctionality extends BaseTest {
    protected <T extends Comparable<T>> IPriorityQueue<T> makeInstance() {
        return new ArrayHeap<>();
    }

    @Test(timeout=SECOND)
    public void testBasicSize() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        heap.insert(3);
        assertEquals(1, heap.size());
        assertTrue(!heap.isEmpty());
    }
    
    

    @Test(timeout = SECOND)
    public void testNullInsert() {

        IPriorityQueue<Integer> heap = this.makeInstance();
        
        try {
            heap.insert(null);
            fail("Expected an IllegalArgumentException");
        }
        catch (IllegalArgumentException ex) {
            //do nothing
        }
    }
    

    @Test(timeout = SECOND)
    public void testRemoveEmpty() {

        IPriorityQueue<Integer> heap = this.makeInstance();
        try {
            heap.removeMin();
            fail("Expected EmptyContainerException");
        }
        catch (EmptyContainerException ex) {
            //do nothing
        }
    }

    @Test(timeout = SECOND)
    public void testPeakEmpty() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        try {
            heap.peekMin();
            fail("Expected EmptyContainerException");
        }
        catch (EmptyContainerException ex) {
            //do nothing
        }
    }

    @Test(timeout = SECOND)
    public void testBasicRemoveMin() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        heap.insert(1);
        heap.insert(2);
        heap.insert(3);
        heap.insert(4);
        heap.insert(5);
        heap.insert(6);
        
        assertEquals(1, heap.removeMin());
        assertTrue(heap.peekMin() == 2);
        
        assertEquals(2, heap.removeMin());
        assertTrue(heap.peekMin() == 3);
        
        assertEquals(3, heap.removeMin());
        assertTrue(heap.peekMin() == 4);
        
        assertEquals(4, heap.removeMin());
        assertTrue(heap.peekMin() == 5);
        
        assertEquals(5, heap.removeMin());
        assertTrue(heap.peekMin() == 6);
        
        assertEquals(6, heap.removeMin());
        assertTrue(heap.isEmpty());
        
    }
    
    @Test(timeout = SECOND)
    public void testVSJavaHeap(){
        PriorityQueue<Integer> javaHeap = new PriorityQueue<Integer>();
        IPriorityQueue<Integer> heap = this.makeInstance();
        
        for (int i = 0; i < 2000; i++) {
        javaHeap.add(i);
        heap.insert(i);
        }
        
        for (int i = 0; i < 2000; i++) {
            assertEquals(heap.removeMin(), javaHeap.remove());
            assertEquals(heap.size(), javaHeap.size());
        }
        
        
    }
    
    
    @Test(timeout = SECOND)
    public void testString() {
        IPriorityQueue<String> heap = this.makeInstance();
        heap.insert("abc");
        heap.insert("bcd");
        heap.insert("cde");
        heap.insert("def");
        heap.insert("efg");
        
        assertEquals("abc", heap.removeMin());
        assertEquals("bcd", heap.removeMin());
        assertEquals("cde", heap.removeMin());
        assertEquals("def", heap.removeMin());
        assertEquals("efg", heap.removeMin());
    }
    
    @Test(timeout = SECOND)
    public void testCharacter(){
        IPriorityQueue<Character> heap = this.makeInstance();
        heap.insert('a');
        heap.insert('b');
        heap.insert('c');
        heap.insert('d');
        heap.insert('e');
        
        assertEquals('a', heap.removeMin());
        assertEquals('b', heap.removeMin());
        assertEquals('c', heap.removeMin());
        assertEquals('d', heap.removeMin());
        assertEquals('e', heap.removeMin());
        
    }
    
    @Test(timeout = SECOND)
    public void testMixedRemoveInsert(){
        PriorityQueue<Integer> javaHeap = new PriorityQueue<Integer>();
        IPriorityQueue<Integer> heap = this.makeInstance();
        heap.insert(12);
        heap.insert(4);
        heap.insert(20);
        heap.insert(2);
        heap.insert(17);
        heap.insert(16);
        assertEquals(2, heap.removeMin());
        assertEquals(4, heap.removeMin());
        assertEquals(12, heap.removeMin());
        heap.insert(15);
        heap.insert(18);
        heap.insert(19);
        heap.insert(3);
        assertEquals(3, heap.removeMin());
        assertEquals(15, heap.removeMin());
        assertEquals(16, heap.removeMin());
        assertEquals(17, heap.removeMin());
        assertEquals(18, heap.removeMin());
        heap.insert(1);
        assertEquals(1, heap.removeMin());
        assertEquals(19, heap.removeMin());
        assertEquals(20, heap.removeMin());
        heap.insert(1);
        heap.insert(4);
        assertEquals(1, heap.removeMin());
        heap.insert(1);
        heap.insert(3);
        heap.insert(7);
        assertEquals(1, heap.removeMin());
        assertEquals(3, heap.removeMin());
        assertEquals(4, heap.removeMin());
        assertEquals(7, heap.removeMin());
        assertTrue(heap.isEmpty());
        
        javaHeap.add(12);
        javaHeap.add(4);
        javaHeap.add(20);
        javaHeap.add(2);
        javaHeap.add(17);
        javaHeap.add(16);
        assertEquals(2, javaHeap.remove());
        assertEquals(4, javaHeap.remove());
        assertEquals(12, javaHeap.remove());
        javaHeap.add(15);
        javaHeap.add(18);
        javaHeap.add(19);
        javaHeap.add(3);
        assertEquals(3, javaHeap.remove());
        assertEquals(15, javaHeap.remove());
        assertEquals(16, javaHeap.remove());
        assertEquals(17, javaHeap.remove());
        assertEquals(18, javaHeap.remove());
        javaHeap.add(1);
        assertEquals(1, javaHeap.remove());
        assertEquals(19, javaHeap.remove());
        assertEquals(20, javaHeap.remove());
        javaHeap.add(1);
        javaHeap.add(4);
        assertEquals(1, javaHeap.remove());
        javaHeap.add(1);
        javaHeap.add(3);
        javaHeap.add(7);
        assertEquals(1, javaHeap.remove());
        assertEquals(3, javaHeap.remove());
        assertEquals(4, javaHeap.remove());
        assertEquals(7, javaHeap.remove());
        assertTrue(javaHeap.isEmpty());

    }
    
    @Test(timeout = SECOND)
    public void testRepeat(){
        
        IPriorityQueue<Integer> heap = this.makeInstance();

        heap.insert(1);
        heap.insert(1);
        heap.insert(1);
        heap.insert(3);
        heap.insert(5);
        heap.insert(5);
        assertEquals(1, heap.removeMin());
        assertEquals(1, heap.removeMin());
        assertEquals(1, heap.removeMin());
        assertEquals(3, heap.removeMin());
        assertEquals(5, heap.removeMin());
        assertEquals(5, heap.removeMin());
        
    }
    
    @Test(timeout = SECOND)
    public void testEmptyString(){
        IPriorityQueue<String> heap = this.makeInstance();
        heap.insert("");
        heap.insert("a");
        heap.insert("b");
        assertEquals("", heap.removeMin());
        assertEquals("a", heap.removeMin());
        assertEquals("b", heap.removeMin());
        
    }
    @Test(timeout=SECOND)
    public void testSingleElement() {
            IPriorityQueue<Integer> heap = this.makeInstance();
            assertEquals(0, heap.size());
            heap.insert(5);
            assertEquals(1, heap.size());
            assertEquals(5, heap.removeMin());
            assertEquals(0, heap.size());
    }
    
    @Test(timeout = SECOND)
    public void testLargeSize() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        for (int i = 1; i <=100; i++) {
            heap.insert(i);
            assertEquals(i, heap.size());
        }
        
        for (int i = 99; i >=0; i--) {
            assertEquals(i+1, heap.size());
            heap.removeMin();
            assertEquals(i, heap.size());
        }
    }
    
    @Test(timeout = SECOND)
    public void testNegative(){
        IPriorityQueue<Integer> heap = this.makeInstance();
        for (int i = -2; i <3; i++) {
            heap.insert(i);
            assertEquals(i+3, heap.size());
            
        }
        for (int i = -2; i<3; i++) {
            assertEquals(i, heap.removeMin());
            assertEquals(-i+2, heap.size());
        }
        
        
    }
    
    
    //test repeat #s
    
    //inserting several values, and removing all of them to find out if they it's right
    //compare to java's minheap this way.
    
    //Test Resizing
    
    //Test for characters
    
    //Test for Strings
    
    //Test for empty string?
    
    //already sorted leads to percolating down on every value (worst case)
    
    //mixed deletes and inserts
    
    /*Not doing Floyd's Buildheap Algorithm
     *  I recommend writing a private "invariantChecker" helper method. 
     *  This helper method will check every single invariant you need maintain, 
     *  and throw an exception if it detects a violation. Now, call this helper
     *  method at the very start and very end of every single public method 
     *  and run your tests. If you accidentally violate some invariant, 
     *  they should crash and make that test fail.
     * 
     * Can use java priorityqueue for testing java.utils
     * 
     * Timeout at 1 seconds
     * 
     * parallel cases are the same?
     * 
     * Stress tests for ArrayHeap in testSortingStress
     * 
     * 
     * Test basic percolate down effect for removing
     * 
     * Test emptycontainer exception if item is null
     * 
     * Test Insert and Remove a few
     * 
     * Test Insert, Remove, and Peek
     * 
     * Test size after removals and inserts
     * 
     * 
     * */
}
