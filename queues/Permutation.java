/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);

        RandomizedQueue<String> rq = new RandomizedQueue<String>();

        int i = 0;
        while (!StdIn.isEmpty()) {
            if (i >= k) break;
            else rq.enqueue(StdIn.readString());
        }

        for (int j = 0; j < k; j++) {
            StdOut.println(rq.dequeue());
        }
    }
}
