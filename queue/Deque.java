/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Node first;
    private Node last;
    private int size;


    // construct an empty deque
    public Deque() {}

    // is the deque empty?
    public boolean isEmpty() {
        return first == null;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) throw new java.lang.IllegalArgumentException();
        Node newFirst = new Node(null, first, item);
        if (isEmpty()) {
            first = newFirst;
            last = newFirst;
        }else{
            first.pre = newFirst;
            first = first.pre;
        }
        size++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) throw new java.lang.IllegalArgumentException();
        Node newLast = new Node(last, null, item);
        if (isEmpty()) {
            first = newLast;
            last = newLast;
        }else{
            last.next = newLast;
            last = last.next;
        }
        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) throw new java.util.NoSuchElementException();
        Node f = first;
        if (first == last) {
            first = null;
            last = null;
            return f.item;
        }

        first = first.next;
        first.pre = null;
        return f.item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) throw new java.util.NoSuchElementException();
        Node l = last;
        if (first == last) {
            first = null;
            last = null;
            return l.item;
        }
        last = last.pre;
        last.next = null;
        return l.item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new newIterator();
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<>();
        deque.addLast(1);
        deque.addFirst(3);
        deque.addLast(1);
        deque.addFirst(3);
        deque.addLast(1);
        deque.addFirst(3);
        deque.addLast(1);
        deque.addFirst(3);
        deque.addLast(1);
        deque.addFirst(3);
        Iterator<Integer> iterator = deque.iterator();
        System.out.println(iterator.next());
        System.out.println(iterator.next());
        System.out.println(iterator.next());
        System.out.println(iterator.next());
        System.out.println(iterator.next());
        System.out.println(iterator.next());
        System.out.println(iterator.next());
        System.out.println(iterator.next());
        System.out.println(iterator.next());
        System.out.println(iterator.next());

    }

    private class Node {
        Node pre;
        Node next;
        Item item;

        Node(Node f, Node l, Item i) {
            pre = f;
            next = l;
            item = i;
        }
    }
    private class newIterator implements Iterator<Item> {
        private Node curr;
        newIterator() {
            curr = first;
        }
        @Override
        public boolean hasNext() {
            return curr != null;
        }

        @Override
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = curr.item;
            curr = curr.next;
            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }


}
