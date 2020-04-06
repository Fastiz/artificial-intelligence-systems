package back.algorithms.util;

import back.interfaces.Game;

import java.util.*;

public class SearchCollection<T> {
    private Queue<T> queue = null;
    private Stack<T> stack = null;

    public SearchCollection(boolean FIFO) {
        if (FIFO)
            queue = new LinkedList<>();
        else
            stack = new Stack<>();
    }

    public SearchCollection(Comparator<T> comparator) {
        queue = new PriorityQueue<>(comparator);
    }

    public T pop() {
        if (queue == null)
            return stack.pop();
        return queue.poll();
    }

    public void add(T elem) {
        if (queue == null)
            stack.push(elem);
        else
            queue.add(elem);
    }

    public boolean isEmpty() {
        if (queue == null)
            return stack.isEmpty();
        return queue.isEmpty();
    }

    public int size() {
        if (queue == null)
            return stack.size();
        return queue.size();
    }
}
