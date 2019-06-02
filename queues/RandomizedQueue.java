/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] s;
    private int n = 0;
    private int capacity = 0;

    public RandomizedQueue() {
        s = (Item[]) new Object[capacity];
    } // construct an empty randomized queue

    public boolean isEmpty() {
        return (n == 0);
    } // is the randomized queue empty?

    public int size() {
        return n;
    } // return the number of items on the randomized queue

    private Item[] resize() {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < n; i++) {
            copy[i] = s[i];
        }
        return copy;
    }

    public void enqueue(Item item) {
        if (item == null) throw new java.lang.IllegalArgumentException("Can't enqueue null.");
        else {
            if (capacity == 0) {
                capacity = 1;
                s = resize();
            }
            else if (n >= capacity) {
                capacity *= 2;
                s = resize();
            }
            s[n++] = item;
        }
    } // add the item

    public Item dequeue() {
        if (isEmpty()) throw new java.util.NoSuchElementException("No such element.");
        else {
            int d = StdRandom.uniform(n);
            Item res = s[d];
            for (int i = d; i < n; i++) {
                if (i == (n - 1)) s[i] = null;
                else s[i] = s[i + 1];
            }
            n -= 1;
            if ((n > 0) && (n == capacity / 4)) {
                capacity /= 2;
                s = resize();
            }
            return res;
        }
    } // remove and return a random item

    public Item sample() {
        if (isEmpty()) throw new java.util.NoSuchElementException("No such element.");
        else return s[StdRandom.uniform(n)];
    } // return a random item (but do not remove it)

    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    } // return an independent iterator over items in random order

    private class RandomizedQueueIterator implements Iterator<Item> {
        private int cnt = 0;
        private Item[] shuffled = (Item[]) new Object[n];

        public RandomizedQueueIterator() {
            for (int i = 0; i < n; i++) {
                shuffled[i] = s[i];
            }
        }

        @Override
        public Item next() {
            if (cnt < n) return shuffled[cnt++];
            else throw new java.util.NoSuchElementException("No such element.");
        }

        @Override
        public boolean hasNext() {
            return (cnt < n);
        }

        @Override
        public void remove() {
            /* not supported */
        }
    }

    // public static void main(String[] args) {}  // unit testing (optional)
}
