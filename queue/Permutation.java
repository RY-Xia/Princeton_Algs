/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;

import java.util.Iterator;

public class Permutation {
    int u = 0;
    private Permutation(int u) {
        this.u = u;
    }
    public static void main(String[] args) {

        RandomizedQueue<String> rque = new RandomizedQueue<>();
        //int num = Integer.parseInt(args[0]);
        //In in = new In(args[1]);
        String str;
        while (!StdIn.isEmpty()) {
            str = StdIn.readString();
            rque.enqueue(str);
        }
        int num = rque.size();
        Iterator<String> iterator = rque.iterator();
        for (int i = 0; i < num; i++)
            System.out.println(iterator.next());
    }
}
