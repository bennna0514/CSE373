package datastructures.concrete.dictionaries;
import datastructures.concrete.KVPair;
import datastructures.interfaces.IDictionary;
import misc.exceptions.NoSuchKeyException;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * See the spec and IDictionary for more details on what each method should do
 */
public class ChainedHashDictionary<K, V> implements IDictionary<K, V> {
    // You may not change or rename this field: we will be inspecting
    // it using our private tests.
    private IDictionary<K, V>[] chains;

    // You're encouraged to add extra fields (and helper methods) though!
    //Internal Size
    private int capacity;
    
    //Number of Keys
    private int numKey;
    
    //Test to check
    
    
    /*
     * Invariants?:
     *
     * External key  is hash. internal is actual key?
     * hash % by array.length to determine bucket
     *
     * ArrayDictionary as internal chain/buckets
     *
     * Implement resizing whenever n/c >1?
     *
     * check for null
     * */
    
    public ChainedHashDictionary() {
        chains = makeArrayOfChains(10);
        capacity = chains.length;
        numKey = 0;
        
        //throw new NotYetImplementedException();
    }

    /**
     * This method will return a new, empty array of the given size
     * that can contain IDictionary<K, V> objects.
     *
     * Note that each element in the array will initially be null.
     */
    @SuppressWarnings("unchecked")
    private IDictionary<K, V>[] makeArrayOfChains(int size) {
        // Note: You do not need to modify this method.
        // See ArrayDictionary's makeArrayOfPairs(...) method for
        // more background on why we need this method.
        return (IDictionary<K, V>[]) new IDictionary[size];
    }

    //hash function has to depend on size of dictionary?
    public int createHash(K key) {
        int hashs = 0;
        if (key != null) {
            //hashs = Math.abs(key.hashCode() % chains.length);
            hashs = Math.abs(key.hashCode() % chains.length);
            //hashs = key.hashCode() % chains.length;
        }
        return hashs;
    }
    
    /**
     * Returns the value corresponding to the given key.
     *
     * @throws NoSuchKeyException if the dictionary does not contain the given key.
     */
    
    
    @Override
    public V get(K key) {
        int hashs = createHash(key);
        
        if (chains[hashs]!= null && chains[hashs].containsKey(key)) {
            return chains[hashs].get(key);
        }
        else {
            throw new NoSuchKeyException();
        }
        /*
        int index = indexOf(key);
        if (index == -1) {
           throw new NoSuchKeyException();
        }
        return pairs[index].value;
        */
        
        
        //throw new NotYetImplementedException();
    }
    //since my chains.length hasn't been changed, I'm modding by the wrong length everytime.
    private void resize() {
        KVPair<K, V> store = null;
        
        IDictionary<K, V>[] temp = this.chains; 

        this.capacity = this.capacity * 2;
        chains= makeArrayOfChains(chains.length * 2);
        for (int i = 0; i < temp.length; i++) {
            if (temp[i] != null){
                
                
            
            
                Iterator<KVPair<K, V>> iter = temp[i].iterator();
                while (iter.hasNext()) {
                    store = iter.next();
                    int hashs = createHash(store.getKey());
                    if (chains[hashs] == null) {
                        chains[hashs] = new ArrayDictionary<K, V>();
                    }
                    chains[hashs].put(store.getKey(), store.getValue());
                    /*Need to sort by if mod is correct?
                 * Only have to do so if you are putting it the same array that you resized.
                 * */
                }
            
            }
            
        }
        
        
    }
    
    public Boolean checkLoadFactor() {
        int factor = numKey/capacity;
        if (factor >= 1) {
        return false;
        }
        return true;
        
    }
    /**
     * Adds the key-value pair to the dictionary. If the key already exists in the dictionary,
     * replace its value with the given one.
     */
    @Override
    public void put(K key, V value) {
        

        int hashs = createHash(key);
        if (chains[hashs] == null) {
            chains[hashs] = new ArrayDictionary<K, V>();
            //capacity+= chains[hashs].size();
        }
        if (!chains[hashs].containsKey(key)) {
            //if it already exists, replace it.
            //chains[hashs].remove(key);
            
            numKey++;
        }
        chains[hashs].put(key, value);
        if (!checkLoadFactor()) {
            resize();
        }
        
        //throw new NotYetImplementedException();
    }

    /**
     * Remove the key-value pair corresponding to the given key from the dictionary.
     *
     * @throws NoSuchKeyException if the dictionary does not contain the given key.
     */
    @Override
    public V remove(K key) {
        int hashs = createHash(key);
        
        if (chains[hashs]!= null && chains[hashs].containsKey(key)) {
            numKey--;
            return chains[hashs].remove(key);
            
        }
        else {
            throw new NoSuchKeyException();
        }
        //throw new NotYetImplementedException();
    }

    
    /**
     * Returns 'true' if the dictionary contains the given key and 'false' otherwise.
     */
    @Override
    public boolean containsKey(K key) {
        
        int hashs = createHash(key);
        if (chains[hashs]!= null) {
        return chains[hashs].containsKey(key);
        }
        return false;
    }

    @Override
    public int size() {
        return this.numKey;
        //throw new NotYetImplementedException();
    }

    @Override
    public Iterator<KVPair<K, V>> iterator() {
        // Note: you do not need to change this method
        return new ChainedIterator<>(this.chains);
    }

    /**
     * Hints:
     *
     * 1. You should add extra fields to keep track of your iteration
     *    state. You can add as many fields as you want. If it helps,
     *    our reference implementation uses three (including the one we
     *    gave you).
     *
     * 2. Think about what exactly your *invariants* are. Once you've
     *    decided, write them down in a comment somewhere to help you
     *    remember.
     *
     * 3. Before you try and write code, try designing an algorithm
     *    using pencil and paper and run through a few examples by hand.
     *
     *    We STRONGLY recommend you spend some time doing this before
     *    coding. Getting the invariants correct can be tricky, and
     *    running through your proposed algorithm using pencil and
     *    paper is a good way of helping you iron them out.
     *
     * 4. Think about what exactly your *invariants* are. As a
     *    reminder, an *invariant* is something that must *always* be
     *    true once the constructor is done setting up the class AND
     *    must *always* be true both before and after you call any
     *    method in your class.
     *
     *    Once you've decided, write them down in a comment somewhere to
     *    help you remember.
     *
     *    You may also find it useful to write a helper method that checks
     *    your invariants and throws an exception if they're violated.
     *    You can then call this helper method at the start and end of each
     *    method if you're running into issues while debugging.
     *
     *    (Be sure to delete this method once your iterator is fully working.)
     *
     * Implementation restrictions:
     *
     * 1. You **MAY NOT** create any new data structures. Iterators
     *    are meant to be lightweight and so should not be copying
     *    the data contained in your dictionary to some other data
     *    structure.
     *
     * 2. You **MAY** call the `.iterator()` method on each IDictionary
     *    instance inside your 'chains' array, however.
     */
    private static class ChainedIterator<K, V> implements Iterator<KVPair<K, V>> {
        private IDictionary<K, V>[] chains;
        //private IDictionary<K,V> current;
        private int index;
        private int arrayindex;
        private Iterator<KVPair<K, V>> iter;
        
                
        //looping until we arrive at a bucket that has a KV pair
        //loop across chain until we find a bucket (ArrayDictionary) that has iterator.
        // or stop at last bucket.
        
        public ChainedIterator(IDictionary<K, V>[] chains) {
            this.chains = chains;
            this.index = 0;
            iter = null;
        }
        
        /*
         * create a helper that brings you to the first/next available
         * part of the chain. Otherwise, you will run into null situations.
         *
         * */
        
        /*
         * is always assigning chain[index].iterator() to iter (the private field)
         * whenever chain[index] != null, but it should only do that when iter == null
         * is also true (i.e. both those conditions need to be true to assign that to iter).
         * Then when the check is made for iter.hasNext() and that returns false,
         * iter should then be set to null and the loop should continue.
         * As the code was, iter was always starting at the beginning of the
         * iterator() for a particular chain[index] rather than continuing with
         * the same one until its hasNext() was exhausted.
         * */
        /*
         * index is null
         * index is not null and iter hasNext
         * index is not null and iter !hasNext
         *
         * */
        
        
        @Override
        public boolean hasNext() {
            
            
            
            while (index < chains.length) {
                
                //chain == null
                if (chains[index] == null) {
                    index++;
                }
                
                //chain != null and iter == null
                else if (iter == null){
                    
                    iter = chains[index].iterator();
                    
                    if (iter.hasNext()) {
                        return true;
                    }
                    else if (!iter.hasNext()) {
                        iter = null;
                        index++;
                    }
                }
                
                //chain != null and iter != null
                else if (iter != null) {
                    if (iter.hasNext()) {
                        return true;
                    }
                    else if (!iter.hasNext()) {
                        iter = null;
                        index++;
                    }
                }
            }
            
            
            
            return false;
            
            
        }
            
        

        @Override
        public KVPair<K, V> next() {
            if (hasNext()) {
                return iter.next();
            }
            else {
                throw new NoSuchElementException();
            }
                
            
        }
    }
}
