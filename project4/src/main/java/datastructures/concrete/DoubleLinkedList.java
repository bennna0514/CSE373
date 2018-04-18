package datastructures.concrete;
import datastructures.interfaces.IList;
import misc.exceptions.EmptyContainerException;
import java.util.Iterator;
import java.util.NoSuchElementException;
/**
 * Note: For more info on the expected behavior of your methods, see
 * the source code for IList.
 */
public class DoubleLinkedList<T> implements IList<T> {
    // You may not rename these fields or change their types.
    // We will be inspecting these in our private tests.
    // You also may not add any additional fields.
    private Node<T> front;
    private Node<T> back;
    private int size;
    public DoubleLinkedList() {
        this.front = null;
        this.back = null;
        this.size = 0;
    }
    @Override
    public void add(T item) {
        Node<T> store = new Node<T>(item);
        if (front == null){
            front = store;
            back = store;
        }
        
        else {
        Node<T> current = back;
        current.next = store;
        store.prev = current;
        back = store;
        }
        size++;
        
    }
    
    
    
    /**
     * Removes and returns the item from the *end* of this IList.
     *
     * @throws EmptyContainerException if the container is empty and there is no element to remove.
     */
    @Override
    public T remove() {
        T store = null;
        if (size() == 0) {
             throw new EmptyContainerException();
        
        }
        
        if (size() == 1) {
            store = front.data;
            front = null;
            back = null;
            
        }
        else {
            Node<T> current = back;
            store = current.data;
            back = back.prev;
            
        }
        size--;
    return store;
    }
    
    /**
     * Returns the item located at the given index.
     *
     * @throws IndexOutOfBoundsException if the index < 0 or index >= this.size()
     */
    @Override
    public T get(int index) {
        if (index <0 || index >= this.size) {
            throw new IndexOutOfBoundsException();
        }
        Node<T> current = front;
        
        for (int i = 0; i < index; i++) {
            current = current.next;
            
        }
        return current.data;
        
    }
    /**
     * replace the element located at the given index with the new item.
     *
     * @throws IndexOutOfBoundsException if the index < 0 or index >= this.size()
     */
    @Override
    public void set(int index, T item) {
        if (index < 0 || index >= this.size) {
            throw new IndexOutOfBoundsException();
        }

        Node<T> current = null;
        Node<T> store = new Node<T>(item);
        if (index <= size()/2) {
            current = front;
            if (index ==0) {
                front = new Node<T>(null, item, current.next);
                current = front;
                if ((current.next != null)) {
                    current.next.prev = current;
                }
            }
            else {
                for (int i = 0; i < index; i++) {
                    current = current.next;
                }
                if (current.prev != null) {
                    store.prev = current.prev;
                    current.prev.next = store;
                }
                if (current.next != null) {
                    store.next = current.next;
                    current.next.prev = store;
                }
                
            }
            
        }

        else if (index > size()/2) {
            current = back;
            if (index == size() -1) {
                back = new Node<T>(current.prev, item, null);
                current = back;
                if (current.prev != null) {
                    current.prev.next = current;
                }
            }
            else {
                for (int i = 0; i < size()-1 - index; i++) {
                    current = current.prev;
                }
                if (current.prev != null) {
                    store.prev = current.prev;
                    current.prev.next = store;
                }
                if (current.next != null) {
                    store.next = current.next;
                    current.next.prev = store;
                }
                
            }
            
        }
        
    }
    /**
     * Inserts the given item at the given index. If there already exists an element
     * at that index, shift over that element and any subsequent elements one index
     * higher.
     *
            current.next = new LinkedNode(value, current.next);
     * @throws IndexOutOfBoundsException if the index < 0 or index >= this.size() + 1
     */
    @Override
    public void insert(int index, T item) {
        if (index < 0 || index >= this.size +1) {
            throw new IndexOutOfBoundsException();
        }
        
        Node<T> current = null;
        if (index <= size/2) {
            current = front;
            
            if (index ==0) {
                
                
                front = new Node<T>(null, item, current);
                if (size() ==0) {
                    current = front;
                    back = front;
                }
                else {
                    current.prev = front;
                }
            }
            else {
                for (int i = 0; i < index-1; i++) {
                    current = current.next;
                     
                }
                if ((current.next != null)) {
                    current.next.prev = current;
                }
              
                if (current.prev != null) {
                    current.prev.next = current;
                }
                current.next = new Node<T>(current, item, current.next);
                
            }
        }
     
        
        else if (index > size()/2) {
            Node<T> store = new Node<T>(item);
            current = back;
            if (index == size()) {
                back = new Node<T>(current, item, null);
                current.next = back;
            }
            
            else {
                for (int i =0; i < (size() -1)- index; i++) {
                    current = current.prev;
                }
                if (current.prev != null) {
                    store.prev = current.prev;
                    current.prev.next =  store;
                }
                    store.next = current;
                    current.prev = store;
            }
            
        }
        
        size++;
    }
    
    
    /**
     * Deletes the item at the given index. If there are any elements located at a higher
     * index, shift them all down by one.
     *
     * @throws IndexOutOfBoundsException if the index < 0 or index >= this.size()
     */
    @Override
    public T delete(int index) {
        if (index < 0 || index >=this.size) {
            throw new IndexOutOfBoundsException();
        }
       T store = null;
       Node<T> current = front;
       
       for (int i = 0; i < index; i++) {
           current = current.next;
       }
       store = current.data;
       
       if (size() == 1) {
           current = null;
           front = null;
           back = null;
       }
       
       else {
           
           if (current.prev != null && current.next != null) {
               current.next.prev = current.prev;
               current.prev.next = current.next;
           }
           else if (current.prev != null && current.next == null) {
               current.prev.next = null;
               back = current.prev;
               
           }
           else if (current.prev == null && current.next != null) {
               current.next.prev = null;
               front = current.next;
           }
           
           
       }
       
       size--;
       return store;
    }
    
    /**
     * Returns the index corresponding to the first occurrence of the given item
     * in the list.
     *
     * If the item does not exist in the list, return -1.
     */
    @Override
    public int indexOf(T item) {
        Node<T> current = front;
  
        
        for (int i = 0; i< this.size; i++) {
            
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
            current = current.next;
        }
        return -1;
    }
    
    /**
     * Returns the number of elements in the container.
     */
    @Override
    public int size() {
        return this.size;
    }
    
    
    @Override
    public boolean contains(T other) {
        boolean contained = false;
        Node<T> current = front;
        for (int i = 0; i < this.size(); i++) {
            
            if (current.data == other || current.data.equals(other)) {
                contained = true;
            }
            current = current.next;
        }
        
        
        return contained;
    }
    @Override
    public Iterator<T> iterator() {
        // Note: we have provided a part of the implementation of
        // an iterator for you. You should complete the methods stubs
        // in the DoubleLinkedListIterator inner class at the bottom
        // of this file. You do not need to change this method.
        return new DoubleLinkedListIterator<>(this.front);
    }
    private static class Node<E> {
        // You may not change the fields in this node or add any new fields.
        public final E data;
        public Node<E> prev;
        public Node<E> next;
        public Node(Node<E> prev, E data, Node<E> next) {
            this.data = data;
            this.prev = prev;
            this.next = next;
        }
        public Node(E data) {
            this(null, data, null);
        }
        // Feel free to add additional constructors or methods to this class.
    }
    private static class DoubleLinkedListIterator<T> implements Iterator<T> {
        // You should not need to change this field, or add any new fields.
        private Node<T> current;
        public DoubleLinkedListIterator(Node<T> current) {
            // You do not need to make any changes to this constructor.
            this.current = current;
        }
        /**
         * Returns 'true' if the iterator still has elements to look at;
         * returns 'false' otherwise.
         */
        public boolean hasNext() {

            return current != null;
        }
        /**
         * Returns the next item in the iteration and internally updates the
         * iterator to advance one element forward.
         *
         * @throws NoSuchElementException if we have reached the end of the iteration and
         *         there are no more elements to look at.
         */
        public T next() {
            
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            T store = current.data;
            this.current = current.next;
            return store;
            
        }
    }
}
