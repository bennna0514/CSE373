package datastructures.concrete;


import datastructures.interfaces.IPriorityQueue;
import misc.exceptions.EmptyContainerException;

/**
 * See IPriorityQueue for details on what each method must do.
 */
public class ArrayHeap<T extends Comparable<T>> implements IPriorityQueue<T> {
    // See spec: you must implement a implement a 4-heap.
    private static final int NUM_CHILDREN = 4;

    // You MUST use this field to store the contents of your heap.
    // You may NOT rename this field: we will be inspecting it within
    // our private tests.
    private T[] heap;
    private int capacity;
    private int arrayLength;

    // Feel free to add more fields and constants.

    public ArrayHeap() {
        capacity = 0;
        arrayLength = 10;
        heap = makeArrayOfT(arrayLength);
        
    }

    /**
     * This method will return a new, empty array of the given size
     * that can contain elements of type T.
     *
     * Note that each element in the array will initially be null.
     */
    @SuppressWarnings("unchecked")
    private T[] makeArrayOfT(int size) {
        // This helper method is basically the same one we gave you
        // in ArrayDictionary and ChainedHashDictionary.
        //
        // As before, you do not need to understand how this method
        // works, and should not modify it in any way.
        return (T[]) (new Comparable[size]);
    }
    @Override
    public T removeMin() {
        if (heap[0] == null) {
            throw new EmptyContainerException();
        }
        
        
        T store = heap[0];
        T item = heap[size() -1];
        heap[0] = item;
        heap[size() -1] = null;
        capacity--;
        //percolate down
        int position = 0;
        boolean percolate = true;
        while (percolate && position < capacity) {
            int lowestChild = findLowest(position);
            
            T temp = null;
            //compare to each child until one is lower?
            //from one to four
            if (lowestChild > heap.length || heap[lowestChild] == null) {
                percolate = false;
            }
            else if (item.compareTo(heap[lowestChild])>0) {
                temp = heap[lowestChild];
                heap[lowestChild] = item;
                heap[position] = temp;
                position = lowestChild;
            }

            else {
                percolate = false;
            }
        }
        
        return store;
        

    }
    
    
private int findLowest(int parent) {
    int lowestPosition = NUM_CHILDREN * parent + 1;
    int firstChild = NUM_CHILDREN * parent + 1;
    for (int i =0; i < NUM_CHILDREN && firstChild + i < size(); i++) {
        if (heap[firstChild+i].compareTo(heap[lowestPosition]) <0) {
            lowestPosition = firstChild + i;
            
        }
        
    }
    return lowestPosition;
    
    
}


    @Override
    public T peekMin() {
        if (heap[0] == null) {
            throw new EmptyContainerException();
        }
        else {
            return heap[0];
        }
        
    }

    
    
    @Override
    public void insert(T item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        capacity++;
        if (size() > arrayLength) {
            resize();
        }
        heap[size()-1] = item;
        
        
        int position = size()-1;
        boolean percolate = true;

        while (percolate) {
            T temp = null;
            int parent = (position - 1)/NUM_CHILDREN;
            if (item.compareTo(heap[parent])<0) {
                //swap
                //store parent, place item at position of parent, place parent at current position
                //change current position to new position
                temp = heap[parent];
                heap[parent] = item;
                heap[position] = temp;
                position = parent;
                
            }
            
            else {
                percolate = false;
                //stay the same
                //end loop
            }
            
            
        }
        
    }


    private void resize() {
         T[] temp =  this.heap;
         this.arrayLength = this.arrayLength * 2;
         this.heap = makeArrayOfT(heap.length * 2);
         for (int i = 0; i < temp.length; i++) {
             heap[i] = temp[i];
         }
    }

    @Override
    public int size() {
        return this.capacity;
    }

}
