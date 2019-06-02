/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {
    private int n;
    private Node first;
    private Node last;

    private class Node {
        Item item;
        Node next;
        Node prev;
    }

    public Deque() {
        n = 0;
    } // construct an empty deque

    public boolean isEmpty() {
        return (n == 0);
    } // is the deque empty?

    public int size() {
        return n;
    } // return the number of items on the deque

    public void addFirst(Item item) {
        if (item == null) throw new java.lang.IllegalArgumentException("Can't add null");
        else {
            Node oldfirst = first;
            first = new Node();
            first.item = item;
            first.next = oldfirst;
            if (isEmpty()) last = first;
            else {
                oldfirst.prev = first;
            }
            n += 1;
        }
    } // add the item to the front

    public void addLast(Item item) {
        if (item == null) throw new java.lang.IllegalArgumentException("Can't add null");
        else {
            Node oldlast = last;
            last = new Node();
            last.item = item;
            last.prev = oldlast;
            if (isEmpty()) first = last;
            else {
                oldlast.next = last;
            }
            n += 1;
        }
    } // add the item to the end

    public Item removeFirst() {
        if (isEmpty()) throw new java.util.NoSuchElementException("No such element");
        else {
            Item item = first.item;
            n -= 1;
            if (isEmpty()) {
                first = null;
                last = null;
            }
            else {
                first = first.next;
                first.prev = null;
            }
            return item;
        }
    } // remove and return the item from the front

    public Item removeLast() {
        if (isEmpty()) throw new java.util.NoSuchElementException("No such element");
        else {
            Item item = last.item;
            n -= 1;
            if (isEmpty()) {
                first = null;
                last = null;
            }
            else {
                last = last.prev;
                last.next = null;
            }
            return item;
        }
    } // remove and return the item from the end

    public Iterator<Item> iterator() {
        return new DequeIterator();
    } // return an iterator over items in order from front to end

    private class DequeIterator implements Iterator<Item> {
        private Node current = first;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            if (current == null) throw new java.util.NoSuchElementException("No such element");
            else {
                Item item = current.item;
                current = current.next;
                return item;
            }
        }

        @Override
        public void remove() {
            /* not supported */
        }
    }

    public static void main(String[] args) {
        Deque<String> deque = new Deque<String>();
        deque.addFirst("First");
        deque.addFirst("Second");
    }  // unit testing (optional)
}
