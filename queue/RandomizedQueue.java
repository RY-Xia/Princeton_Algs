/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {


    //private int randomNum;
    private Item[] items;

    private int point;

    // construct an empty randomized queue
    public RandomizedQueue() {
        point = 0;

        items = (Item[])new Object[8];
        //randomNum = StdRandom.uniform(point);


    }
    private void resize() {
        int length = items.length;
        Item[] newItems = (Item[])new Object[length * 2];
        for (int i = 0; i < size(); i++) {
            newItems[i] = items[i];
        }
        items = newItems;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return point == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return point;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) throw new java.lang.IllegalArgumentException();
        if (point == items.length) resize();
        items[point++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {throw new NoSuchElementException();}
        int randomNum = StdRandom.uniform(size());
        Item res = items[randomNum];
        for (int i = randomNum; i < size() - 1; i++) {
            items[i] = items[i+1];
        }
        return res;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) {throw new NoSuchElementException();}
        int randomNum = StdRandom.uniform(size());
        return items[randomNum];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new newIterator<>(items);
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> test = new RandomizedQueue<>();
        Iterator<Integer> iterator = test.iterator();
        test.enqueue(3);
        test.enqueue(4);
        test.enqueue(5);
        test.enqueue(6);
        System.out.println(test.dequeue());
        System.out.println(test.sample());
        //System.out.println(iterator.next());
    }

    private class newIterator<Item> implements Iterator<Item> {
        private Item[] items;
        private int current;

        public newIterator(Item[] arr) {
            int N = size();
            items = (Item[]) new Object[N];
            for (int i = 0; i < N; i++) {
                this.items[i] = arr[i];
            }
            StdRandom.shuffle(items);
        }

        @Override
        public boolean hasNext() {
            return current != items.length;
        }

        @Override
        public Item next() {
            if (!hasNext()) throw new java.util.NoSuchElementException();
            return items[current++];
        }

        @Override
        public void remove() {
            throw new java.lang.UnsupportedOperationException();
        }

    }

}
