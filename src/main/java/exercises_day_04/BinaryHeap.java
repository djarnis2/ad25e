package exercises_day_04;

import java.util.HashMap;
import java.util.HashSet;

/**
 * A binary min-heap with O(log N) insert/delete and O(1) findMin.
 *
 * Differences vs. the original snippet:
 *  - Uses a 1-based array (classic heap layout) that grows as needed.
 *  - Tracks each element's index with a HashMap<T,Integer> (instead of a custom SeparateChainingHashTable),
 *    so delete(x) is efficient and the code compiles cleanly.
 *  - Disallows duplicates (like the original intent) to keep the map well-defined.
 */
public class BinaryHeap<T extends Comparable<? super T>> {
    private static final int DEFAULT_CAPACITY = 10;

    private int currentSize;          // number of elements in heap
    private T[] array;                // 1-based backing array
    private final HashMap<T, Integer> loc; // maps element -> index in array

    /** Construct empty heap. */
    public BinaryHeap() {
        this(DEFAULT_CAPACITY);
    }

    /** Construct empty heap with capacity. */
    @SuppressWarnings("unchecked")
    public BinaryHeap(int capacity) {
        currentSize = 0;
        array = (T[]) new Comparable[capacity + 1];
        loc = new HashMap<>();
    }

    /** Construct heap from initial items (no duplicates allowed). */
    @SuppressWarnings("unchecked")
    public BinaryHeap(T[] items) {
        currentSize = items.length;
        array = (T[]) new Comparable[(currentSize + 2) * 11 / 10];
        loc = new HashMap<>(array.length);

        // disallow duplicates to keep loc well-defined
        HashSet<T> seen = new HashSet<>();
        int i = 1;
        for (T item : items) {
            if (item == null) throw new IllegalArgumentException("Null elements not supported");
            if (!seen.add(item)) {
                throw new IllegalArgumentException("Duplicate element in constructor: " + item);
            }
            array[i] = item;
            loc.put(item, i);
            i++;
        }
        buildHeap();
    }

    /** Insert x if not present (duplicates ignored). */
    public void insert(T x) {
        if (x == null) throw new IllegalArgumentException("Null elements not supported");
        if (loc.containsKey(x)) return; // disallow duplicates

        if (currentSize == array.length - 1) enlargeArray(array.length * 2 + 1);

        int hole = ++currentSize;
        // percolate up with x
        while (hole > 1) {
            int parent = hole / 2;
            T p = array[parent];
            if (x.compareTo(p) < 0) {
                array[hole] = p;
                loc.put(p, hole);
                hole = parent;
            } else break;
        }
        array[hole] = x;
        loc.put(x, hole);
    }

    /** Return min or null if empty. */
    public T findMin() {
        if (isEmpty()) return null;
        return array[1];
    }

    /** Delete and return min, or null if empty. */
    public T deleteMin() {
        if (isEmpty()) return null;
        T min = array[1];
        loc.remove(min);

        T last = array[currentSize--];
        if (currentSize == 0) {
            // heap is now empty
            array[1] = null;
            return min;
        }
        array[1] = last;
        loc.put(last, 1);
        percolateDown(1);
        return min;
    }

    /** Delete arbitrary element x if present. Returns true if removed. */
    public boolean delete(T x) {
        Integer idx = loc.get(x);
        if (idx == null) return false;

        int i = idx;
        loc.remove(x);

        if (i == currentSize) {
            array[currentSize] = null;
            currentSize--;
            return true;
        }

        T last = array[currentSize];
        array[currentSize] = null;
        currentSize--;

        array[i] = last;
        loc.put(last, i);

        // restore heap order from position i (either up or down)
        if (i > 1 && array[i].compareTo(array[i / 2]) < 0) {
            percolateUp(i);
        } else {
            percolateDown(i);
        }
        return true;
    }

    public boolean isEmpty() { return currentSize == 0; }

    public void makeEmpty() {
        for (int i = 1; i <= currentSize; i++) array[i] = null;
        currentSize = 0;
        loc.clear();
    }

    /** Return the 1-based index of x in the heap, or 0 if absent. */
    public int getLocation(T x) {
        Integer i = loc.get(x);
        return i == null ? 0 : i;
    }

    /** Return true if x is present. */
    public boolean isPresent(T x) { return loc.containsKey(x); }

    // -------------------- internals --------------------

    private void buildHeap() {
        for (int i = currentSize / 2; i > 0; i--) percolateDown(i);
    }

    private void percolateDown(int hole) {
        T tmp = array[hole];
        while (hole * 2 <= currentSize) {
            int child = hole * 2;
            if (child != currentSize && array[child + 1].compareTo(array[child]) < 0) child++;
            if (array[child].compareTo(tmp) < 0) {
                array[hole] = array[child];
                loc.put(array[hole], hole);
                hole = child;
            } else break;
        }
        array[hole] = tmp;
        loc.put(tmp, hole);
    }

    private void percolateUp(int hole) {
        T x = array[hole];
        while (hole > 1) {
            int parent = hole / 2;
            T p = array[parent];
            if (x.compareTo(p) < 0) {
                array[hole] = p;
                loc.put(p, hole);
                hole = parent;
            } else break;
        }
        array[hole] = x;
        loc.put(x, hole);
    }

    @SuppressWarnings("unchecked")
    private void enlargeArray(int newSize) {
        T[] old = array;
        array = (T[]) new Comparable[newSize];
        System.arraycopy(old, 0, array, 0, old.length);
    }

    // --------------- simple test ---------------
    public static void main(String[] args) {
        BinaryHeap<Integer> h = new BinaryHeap<>();
        h.insert(13);
        h.insert(21);
        h.insert(16);
        h.insert(24);
        h.insert(31);
        h.insert(19);
        h.insert(68);
        h.insert(65);
        h.insert(26);
        h.insert(32);

        System.out.println(h.getLocation(13));
        System.out.println(h.getLocation(24));
        System.out.println(h.getLocation(31));
        System.out.println(h.getLocation(68));
        System.out.println(h.getLocation(26));
        h.insert(85);
        h.insert(85); // check for dubbletes.
        System.out.println("CurrentSize: "+h.currentSize);

        System.out.println("min=" + h.findMin());
        System.out.println("delete(24)=" + h.delete(24));
        System.out.println("min after delete(24)=" + h.findMin());
        while (!h.isEmpty()) System.out.print(h.deleteMin() + " ");
    }
}
