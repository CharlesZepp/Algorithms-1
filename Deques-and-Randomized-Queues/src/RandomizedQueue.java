import java.util.Iterator;
import edu.princeton.cs.algs4.StdRandom;
import java.util.NoSuchElementException;

/**
 * Algorithms
 * Programming Assignment 2: Deques and Randomized Queues
 * @author charl
 */
public class RandomizedQueue<Item> implements Iterable<Item> {
    
    private Item[] queue;
    private int elements;
    
    /**
     * construct an empty randomized queue
     */
    public RandomizedQueue() {
        this.queue = (Item[]) new Object[1];//ugly but necessary cast
        this.elements = 0;
    }
    
    /**
     * is the queue empty?
     * @return
     */
    public boolean isEmpty() {
        return this.elements == 0;
    }
    
    /**
     * return the number of items on the queue
     * @return
     */
    public int size() {
        return elements;
    }
    
    /**
     * add the item
     * @param item
     */
    public void enqueue(Item item) {
        
        nullCheck(item);
        
        if (queue.length == elements) {
            resize(2*queue.length);
        }
        
        queue[elements++] = item;
    }
    
    /**
     * remove and return a random item
     * @return
     */
    public Item dequeue() {
        
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        
        Item item = queue[elements-1];
        queue[elements-1] = null; // NO LOITERING!
        elements--;
        
        if (elements > 0 && elements == queue.length/4) {
            resize(queue.length/2);
        }
        
        return item;
    }
    
    /**
     * Helper method used from examples given in checklist
     * @param cap
     */
    private void resize(int cap) {
        assert cap >= elements;
        
        Item[] temp = (Item[]) new Object[cap];
        for (int i = 0; i < elements; i++) {
            temp[i] = queue[i];
        }
        queue = temp;
    }
    
    /**
     * return (but do not remove) a random item
     * @return
     */
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        
        return queue[StdRandom.uniform(elements)];
    }
    
    /**
     * 
     * @param item
     */
    private void nullCheck(Item item){
        if (item == null){
            throw new IllegalArgumentException();
        }
    }
    
    /**
     * return an independent iterator over items in random order
     */
    public Iterator<Item> iterator() {
        return new RandomIterator();
    }
        private class RandomIterator implements Iterator<Item> {
            private Item[] output;
            private int n = 0;

            private RandomIterator() {
                output = (Item[]) new Object[elements];
                for (int i = 0; i < elements; i++) {
                    output[i] = queue[i];
                }
                StdRandom.shuffle(output);
            }

            public boolean hasNext() {
                return n < elements;
            }

            public Item next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return output[n++];
            }

            public void remove() {
                throw new UnsupportedOperationException();
            }
        }
    
    /**
     * CLIENT
     * @param args
     */
    public static void main(String[] args) {
        
        RandomizedQueue<Integer> random = new RandomizedQueue<>();
        random.enqueue(4);
        random.enqueue(3);
        random.enqueue(5);
        random.enqueue(7);
        random.enqueue(89);
       /*
        System.out.println(random.dequeue());
        System.out.println(random.sample());
        System.out.println(random.dequeue());
        */
        for(Integer s: random) {
            System.out.println(s);
        }
    }
}
