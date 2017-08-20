import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Algorithms
 * Programming Assignment 2: Deques and Randomized Queues
 * @author charl
 */

public class Deque<Item> implements Iterable<Item> {
    
    private Node head, tail;
    private int amount;
    
    /**
     * Private class to keep up w/ constant time
     */
    private class Node {
        
        private Item item;
        private Node next, prev;
        
        Node (Item item) {
            this.item = item;
            this.next = null;
            this.prev = null;
        }
    }
    
    /**
     * Constructs an empty Deque
     */
    public Deque() {
        this.head = null;
        this.tail = null;
        this.amount = 0;
    }
    
    /**
     * is the deque empty?
     * @return
     */
    public boolean isEmpty() {
        return amount == 0;
    }
    
    /**
     * return the number of items on the deque
     * @return
     */
    public int size() {
        return this.amount;
    }
    
    /**
     * add the item to the front
     * @param item
     */
    public void addFirst(Item item) {
        
        nullCheck(item);
      
        if (isEmpty()) {
            head = new Node(item);
            tail = head;
        } else {
            Node n = new Node(item);
            n.next = head;
            head = n;
            head.prev = n;
        }
        
        amount++;
    }
    
    /**
     * add the item to the end
     * @param item
     */
    public void addLast(Item item) {
        
        nullCheck(item);
        
        if(isEmpty()) {
            tail = new Node(item);
            head = tail;
        } else {
            Node n = new Node(item);
            tail.next = n;
            n.prev = tail;
            tail = n;
        }
        amount++;
        
    }
    
    /**
     * remove and return the item from the front
     * @return
     */
    public Item removeFirst() {
        
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        
        Node n = this.head;
        
        if(amount == 1) {
            head = null;
            tail = null;
            
        } else {
            head = head.next;
            head.prev = null;
        }
        amount--;
        return n.item;
    }
    
    /**
     * remove and return the item from the end
     * @return
     */
    public Item removeLast() {
        
        if(isEmpty()) {
            throw new NoSuchElementException();
        }
        
        Node n = this.tail;
        
        if (amount == 1) {
            head = null;
            tail = null;
            
        } else {
            tail = tail.prev;
            tail.prev = null;
            
        }
        
        amount--;
        return n.item;
    }
    
    /**
     * return an iterator over items in order from front to end
     */
    public Iterator<Item> iterator() {
        return new DeqIterator();
    }
    
    private class DeqIterator implements Iterator<Item> {
        
        private Node current = head;
        
        public void remove() {
            throw new UnsupportedOperationException();
        }
        
        public boolean hasNext() {
            return current != null;
        }
        
        public Item next() {
            Item item = current.item;
            
            if (!hasNext()) {
                throw new NoSuchElementException(); 
            }
            
            current = current.next;
            return item;
        }
        
    }
    
    /**
     * helper method to avoid inserting null values
     * @param item
     */
    private void nullCheck(Item item){
        if (item == null){
            throw new IllegalArgumentException();
        }
    }
}
