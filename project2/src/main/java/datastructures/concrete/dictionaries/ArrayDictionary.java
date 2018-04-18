package datastructures.concrete.dictionaries;

import datastructures.concrete.KVPair;
//import datastructures.concrete.dictionaries.ArrayDictionary.Pair;
import datastructures.interfaces.IDictionary;
import misc.exceptions.NoSuchKeyException;
//import misc.exceptions.NotYetImplementedException;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
*  Replace this file with the one you wrote from project 1
*  Add the missing "iterator()" method
*/
public class ArrayDictionary<K, V> implements IDictionary<K, V> {
   // You may not change or rename this field: we will be inspecting
   // it using our private tests.
   private Pair<K, V>[] pairs;
   private int size;
   private int sizeOfArrays;
   // You're encouraged to add extra fields (and helper methods) though!
   
   public ArrayDictionary() {
      this.size = 0;
      this.sizeOfArrays = 10;
      this.pairs = makeArrayOfPairs(10);
   }
   
   /**
   * This method will return a new, empty array of the given size
   * that can contain Pair<K, V> objects.
   *
   * Note that each element in the array will initially be null.
   */
   @SuppressWarnings("unchecked")
   private Pair<K, V>[] makeArrayOfPairs(int arraySize) {
      // It turns out that creating arrays of generic objects in Java
      // is complicated due to something known as 'type erasure'.
      //
      // We've given you this helper method to help simplify this part of
      // your assignment. Use this helper method as appropriate when
      // implementing the rest of this class.
      //
      // You are not required to understand how this method works, what
      // type erasure is, or how arrays and generics interact. Do not
      // modify this method in any way.
      return (Pair<K, V>[]) (new Pair[arraySize]);
   }
   
   @Override
   public V get(K key) {
      int index = indexOf(key);
      if (index == -1) {
         throw new NoSuchKeyException();
      }
      return pairs[index].value;
   }
   
   @Override
   public void put(K key, V value) {
      if (size < sizeOfArrays) {
         int index = this.indexOf(key);
         if (index != -1) {
            pairs[index] = new Pair<>(key, value);
         } else {
            pairs[size] = new Pair<>(key, value);
            size++;
         }
      } else {
         sizeOfArrays *= 2;
         pairs = resizePairs(this.pairs, this.size, sizeOfArrays);
         this.put(key, value);
      }
   }
   
   private Pair<K, V>[] resizePairs(Pair<K, V>[] p, int originalSize, int reSize) {
      Pair<K, V>[] resizedPair = makeArrayOfPairs(reSize);
      for (int i = 0; i < originalSize; i++) {
         resizedPair[i] = p[i];
      }
      return resizedPair;
   }
   
   @Override
   public V remove(K key) {
      int index = indexOf(key);
      if (index == -1) {
         throw new NoSuchKeyException();
      }
      V result = pairs[index].value;
      for (int i = index; i < size - 1; i++) {
         pairs[i] = pairs[i + 1];
      }
      size--;
      return result;
   }
   
   @Override
   public boolean containsKey(K key) {
      boolean contains = false;
      if (indexOf(key) != -1) {
         contains = true;
      } else {
         contains = false;
      }
      return contains;
   }
   
   @Override
   public int size() {
      return size;
   }
   
   /*
    * 
            if (item == null) {
                if (item == current.data) {
                    return i;
                }
            }
            else if (item != null) {
                if (item.equals(current.data)) {
                    return i;
                }
            }
            */
   private int indexOf(K key){
      int index = 0;
      for (int i = 0; i < this.size; i++) {
          
         if (key == null && pairs[i].key == null) {
            return index;
         }
         
         else if (key != null && pairs[i].key != null) {
            /*
                 if (key == "" && pairs[i].key == "") {
                     return index;
                 }
                 */
            
            if (pairs[i].key.equals(key)) { //breaks here with key = "". effectively an empty string.
               return index;
            }
         }
         index++;
      }
      return -1;
   }
   
   
   public Iterator<KVPair<K, V>> iterator() {
      return new ArrayDictionaryIterator<>(this.pairs);
   }
   
   private class ArrayDictionaryIterator<K, V> implements Iterator<KVPair<K, V>> {
      private Pair<K, V>[] current;
      private int index = 0;
      
      public ArrayDictionaryIterator(Pair<K, V>[] pairDict) {
         this.current = pairDict;
      }
      
      
      public boolean hasNext() {
         return index < size;
      }
      
      public KVPair<K, V> next() {
         if (!hasNext()) {
            throw new NoSuchElementException();
         }
         Pair<K, V> store = this.current[index];
         //current[] = this.current[index];
         KVPair<K, V> currentPair = new KVPair<>(store.key, store.value);
         index++;
         return currentPair;
      }
   }
   private static class Pair<K, V> {
      public K key;
      public V value;
      // You may add constructors and methods to this class as necessary.
      public Pair(K key, V value) {
         this.key = key;
         this.value = value;
      }
      @Override
      public String toString() {
         return this.key + "=" + this.value;
      }
   }
}
