package datastructures.concrete;

import datastructures.concrete.dictionaries.ChainedHashDictionary;
import datastructures.interfaces.IDictionary;
import datastructures.interfaces.ISet;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * See ISet for more details on what each method is supposed to do.
 */
public class ChainedHashSet<T> implements ISet<T> {
    // This should be the only field you need
    private IDictionary<T, Boolean> map;

    public ChainedHashSet() {
        // No need to change this method
        this.map = new ChainedHashDictionary<>();
    }

    @Override
    public void add(T item) {

        if (!map.containsKey(item)) {
            map.put(item, true);
        }
        
        /**
         * Adds the given item to the set.
         *
         * If the item already exists in the set, this method does nothing.
         */
        
        //throw new NotYetImplementedException();
    }

    @Override
    public void remove(T item) {
        /**
         * Removes the given item from the set.
         *
         * @throws NoSuchElementException  if the set does not contain the given item
         */
        if (map.containsKey(item)) {
            map.remove(item);
        }
        else {
            throw new NoSuchElementException();
        }
        //throw new NotYetImplementedException();
    }

    @Override
    public boolean contains(T item) {
        return map.containsKey(item);
        /**
         * Returns 'true' if the set contains this item and false otherwise.
         */
        //throw new NotYetImplementedException();
    }

    @Override
    public int size() {
        return map.size();
        //throw new NotYetImplementedException();
        /**
         * Returns the number of items contained within this set.
         */
    }

    @Override
    public Iterator<T> iterator() {
        return new SetIterator<>(this.map.iterator());
    }

    private static class SetIterator<T> implements Iterator<T> {
        // This should be the only field you need
        private Iterator<KVPair<T, Boolean>> iter;

        public SetIterator(Iterator<KVPair<T, Boolean>> iter) {
            // No need to change this method.
            this.iter = iter;
        }

        @Override
        public boolean hasNext() {
            return iter.hasNext();
            //throw new NotYetImplementedException();
        }

        @Override
        public T next() {
            return iter.next().getKey();
            //throw new NotYetImplementedException();
        }
    }
}
