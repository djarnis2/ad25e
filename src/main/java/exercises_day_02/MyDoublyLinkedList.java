package exercises_day_02;


// From the book: "Data-Structures-and-Algorithm-Analysis-in-Java" by Mark Allen Weiss
// 1. The MyLinkedList class itself, which contains links to both ends, the size of the list,
// and a host of methods.
// 2. The Node class, which is likely to be a private nested class. A node contains the data
// and links to the previous and next nodes, along with appropriate constructors.
// 3. The LinkedListIterator class, which abstracts the notion of a position and is a private inner class,
// implementing the Iterator interface. It provides implementations of next, hasNext, and remove.

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

public class MyDoublyLinkedList<T> implements Iterable<T> {
    private int theSize;
    private int modCount = 0;
    private Node<T> beginMarker;
    private Node<T> endMarker;

    private static class Node<U> {
        public Node(U d, Node<U> p, Node<U> n) {
            data = d;
            prev = p;
            next = n;
        }

        public U data;
        public Node<U> prev;
        public Node<U> next;
    }

    public MyDoublyLinkedList() {
        doClear();
    }

    public void clear() {
        doClear();
    }

    private void doClear() {
        beginMarker = new Node<>(null, null, null);
        endMarker = new Node<>(null, beginMarker, null);
        beginMarker.next = endMarker; // laves efterfølgende, da den ikke kan laves før endMarker er etableret.

        theSize = 0;
        modCount++;
    }

    public int size() {
        return theSize;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public boolean add(T x) {
        add(size(), x);
        return true;
    }

    public void add(int idx, T x) {
        addBefore(getNode(idx, 0, size()), x);
    }

    public T get(int idx) {
        return getNode(idx).data;
    }

    public T set(int idx, T newVal) {
        Node<T> p = getNode(idx);
        T oldVal = p.data;
        p.data = newVal;
        return oldVal;
    }

    public T remove(int idx) {
        return remove(getNode(idx));
    }

    /**
     * Adds an item to this collection, at specified position p.
     * Item at or that position are slid one position higher.
     *
     * @param p Node to add before
     * @param x Object
     * @throws IndexOutOfBoundsException if idx is not between 0 ad size(),.
     */
    private void addBefore(Node<T> p, T x) {
        Node<T> newNode = new Node<>(x, p.prev, p);
        newNode.prev.next = newNode;
        p.prev = newNode;
        theSize++;
        modCount++;
    }

    /**
     * Removes the object contained in Node p.
     *
     * @param p the Node containg the object.
     * @return the item was removed from the collection.
     */
    private T remove(Node<T> p) {
        p.next.prev = p.prev; // The  node after p (p.next) gets assigned p's prev as prev.
        p.prev.next = p.next; // The node before p (p.prev) get assigned p's next as next.
        theSize--;
        modCount++;

        return p.data;
    }

    /**
     * Gets the node at position idx, which must range from 0 to size() -1.
     *
     * @param idx index to search at.
     * @return internal node corresponding to idx.
     * @throws IndexOutOfBoundsException if idx is not between 0 and size() - 1, inclusive.
     */
    private Node<T> getNode(int idx) {
        return getNode(idx, 0, size() - 1);
    }

    /**
     * Gets the node at position idx, which must range from lower to upper.
     *
     * @param idx   index to search at.
     * @param lower lowest valid index.
     * @param upper highest valid index.
     * @return internal node corresponding to idx.
     * @throws IndexOutOfBoundsException if idx is not between 0 and size() - 1, inclusive.
     */
    private Node<T> getNode(int idx, int lower, int upper) {
        Node<T> p;

        if (idx < lower || idx > upper)
            throw new IndexOutOfBoundsException();

        // In the lower half of the list, start from beginning go until idx is reached.
        if (idx < size() / 2) {
            p = beginMarker.next;
            for (int i = 0; i < idx; i++) {
                p = p.next;
            }
            // In the upper half of the list, start at the end and go backwards and move until idx is reached.
        } else {
            p = endMarker;
            for (int i = size(); i > idx; i--) {
                p = p.prev;
            }
        }
        return p;
    }

    @Override
    public java.util.Iterator<T> iterator() {
        return new LinkedListIterator();
    }

    // ******************************* Iterator **************************************
    private class LinkedListIterator implements java.util.Iterator<T> {
        private Node<T> current = beginMarker.next;
        private int expectedModCount = modCount;
        private boolean okToRemove = false;

        @Override
        public boolean hasNext() {
            return current != endMarker;
        }

        @Override
        public T next() {
            if (modCount != expectedModCount) {
                throw new ConcurrentModificationException();
            }
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            T nextItem = current.data;
            current = current.next;
            okToRemove = true;
            return nextItem;
        }

        @Override
        public void remove() {
            if (modCount != expectedModCount) {
                throw new ConcurrentModificationException();
            }
            if (!okToRemove) {
                throw new IllegalStateException();
            }

            // fjern senest returnerede element
            MyDoublyLinkedList.this.remove(current.prev);
            expectedModCount++;
            okToRemove = false;
        }
    }

    // ********************** Main *******************************
    public static void main(String[] args) {
        MyDoublyLinkedList<String> list = new MyDoublyLinkedList<>();

        // Test: Tilføj elementer
        list.add("A");
        list.add("B");
        list.add("C");

        System.out.println("Efter tilføjelse (A, B, C):");
        for (String s : list) {
            System.out.println(s);
        }

        // Test: Indsæt på bestemt position
        list.add(1, "X");
        System.out.println("\nEfter indsættelse af 'X' på index 1:");
        for (String s : list) {
            System.out.println(s);
        }

        // Test: Få elementer
        System.out.println("\nElement på index 2: " + list.get(2));

        // Test: Sæt nyt element
        list.set(0, "Z");
        System.out.println("\nEfter set(0, 'Z'):");
        for (String s : list) {
            System.out.println(s);
        }

        // Test: Fjern et element
        list.remove(2);
        System.out.println("\nEfter remove(2):");
        for (String s : list) {
            System.out.println(s);
        }

        // Test: Iterator.remove()
        var it = list.iterator();
        while (it.hasNext()) {
            String val = it.next();
            if (val.equals("B")) {
                it.remove();
            }
        }
        System.out.println("\nEfter iterator.remove() (fjern 'B'):");
        for (String s : list) {
            System.out.println(s);
        }

        System.out.println("\nListe størrelse: " + list.size());
    }

}
