package datastructures.concrete;

import datastructures.concrete.dictionaries.ChainedHashDictionary;
import datastructures.interfaces.IDictionary;
import datastructures.interfaces.IDisjointSet;

/**
 * See IDisjointSet for more details.
 */
public class ArrayDisjointSet<T> implements IDisjointSet<T> {
    // Note: do NOT rename or delete this field. We will be inspecting it
    // directly within our private tests.
    private int[] pointers;
    private IDictionary<T, Integer> nodes;
    private IDictionary<Integer, T> vertices;
    private int size;
    // However, feel free to add more methods and private helper methods.
    // You will probably need to add one or two more fields in order to
    // successfully implement this class.


    
    
    public ArrayDisjointSet() {
        this.pointers = new int[10];
        this.size = 0;
        this.nodes = new ChainedHashDictionary<T, Integer>();
        this.vertices = new ChainedHashDictionary<Integer, T>();
    }
    
    
    /**
     * Creates a new set containing just the given item.
     * The item is internally assigned an integer id (a 'representative').
     *
     * @throws IllegalArgumentException  if the item is already a part of this disjoint set somewhere
     */
    @Override
    public void makeSet(T item) {
        
        if (size +1 >= pointers.length) {
            resize();
        }
        
        
        if (nodes.containsKey(item)) {
            throw new IllegalArgumentException();
        }
        nodes.put(item, this.size);
        vertices.put(this.size, item);
        this.pointers[this.size]= -1;
        this.size++;
        
    }
    private void resize() {
        int[] temp = new int[pointers.length * 2];
        for (int i =0; i< pointers.length; i++) {
            temp[i] = pointers[i];
        }
        this.pointers = temp;
    }


    /**
     * Returns the integer id (the 'representative') associated with the given item.
     *
     * @throws IllegalArgumentException  if the item is not contained inside this disjoint set
     */
    @Override
    //findset is returning the rank rather than the parent.
    public int findSet(T item) {
        if (!this.nodes.containsKey(item)) {
            throw new IllegalArgumentException();
        }
        
        
        //        finding the representative int id recursively
        int itemID = this.nodes.get(item);
        //        Check if parent ID is negative(so we're at the root and that's the rank
        if (this.pointers[itemID] >= 0){
          int parentID = this.pointers[itemID];
          //          if parentID is positive, recurse on parentID item
          T parent = vertices.get(parentID);

           //          also need to change tree so current node points at representative node
           //return findSet(parent);
          
           int rep = findSet(parent);
           this.pointers[itemID] = rep;
           return rep;
           
                  
        }
        
        else {
          //return the integer
          return itemID;
        }
    }
    /**
     * Finds the two sets associated with the given items, and combines the two sets together.
     *
     * @throws IllegalArgumentException  if either item1 or item2 is not contained inside this disjoint set
     * @throws IllegalArgumentException  if item1 and item2 are already a part of the same set
     */
    
    /*
      1. Keep track of the rank of all trees.
      2. When unioning, make the tree with the larger rank the root!
      3. If it’s a tie, pick one randomly and increase the rank by one.
      
      */
    @Override
    public void union(T item1, T item2) {
        if (!this.nodes.containsKey(item1) || !this.nodes.containsKey(item2)) {
            throw new IllegalArgumentException();
        }
        /*
        if(this.findSet(item1) == this.findSet(item2)) {
            throw new IllegalArgumentException();
        }
        */
        
        //        store findsets of each.
        int repOne = findSet(item1);
        int repTwo = findSet(item2);
        
        if (repOne == repTwo) {
            throw new IllegalArgumentException();
        }
        //        find the ranks of each.
        
        int rankOne = Math.abs(this.pointers[repOne]);
        int rankTwo = Math.abs(this.pointers[repTwo]);
        
        //        compare ranks
         
        //        if ranks are same, arbitrarily make 1 higher and combine (lets make rankOne bigger)
        if (rankOne == rankTwo){
        rankOne++;
        }
        //potentially combine below rather than in this if?
         
         
        if (rankOne > rankTwo){
         //nest rankTwo under rankOne
         this.pointers[repTwo] = repOne;
         this.pointers[repOne] = -rankOne;
        }
        else {// rankOne < rankTwo. No == b/c that case was settled first.
         this.pointers[repOne] = repTwo;
         this.pointers[repTwo] = -rankTwo;
        }
        
        
        
    }
}
