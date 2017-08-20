import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {

    public static void main(String[] args) {
        
        RandomizedQueue<String> bob = new RandomizedQueue<>();
        
        while (!StdIn.isEmpty()) {
            
            String in = StdIn.readString();
            bob.enqueue(in);
        }
        
        int k = Integer.parseInt(args[0]);
        for (int i = 0; i < k; i++) {
            StdOut.println(bob.dequeue());
        }
    }

}
