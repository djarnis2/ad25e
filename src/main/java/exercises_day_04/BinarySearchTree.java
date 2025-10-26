package exercises_day_04;

import javax.swing.*;
import java.util.EmptyStackException;

public class BinarySearchTree<T extends Comparable<? super T>>  {

    private static class BinaryNode<T> {
        BinaryNode(T element) {
            this(element, null, null);
        }
        BinaryNode(T element, BinaryNode<T> left, BinaryNode<T> right) {
            this.element = element;
            this.left = left;
            this.right = right;
        }
        T element; // Data
        BinaryNode<T> left;
        BinaryNode<T> right;
    }

    private BinaryNode<T> root;

    public BinarySearchTree() {
        root = null;
    }

    public void makeEmpty() {
        root = null;
    }
    public boolean isEmpty() {
        return root == null;
    }

    public boolean contains(T x) {
        return contains(x, root);
    }
    public T findMin() {
        if(isEmpty()) throw new EmptyStackException();
        return findMin(root).element;
    }
    public T findMax() {
        if(isEmpty()) throw new EmptyStackException();
        return findMax(root).element;
    }
    public void insert(T x) {
        root = insert(x, root);
    }
    public void remove(T x) {
        root = remove(x, root);
    }
    public void printTree() {

    }

    /**
     * Internal method to find an item in a subtree.
     * @param x is item to search for.
     * @param t the node that roots the subtree.
     * @return true if the item is found; false otherwise.
     */
    private boolean contains(T x, BinaryNode<T> t) {
        if (t == null) {
            return false;
        }
        int compareResult = x.compareTo(t.element);

        if (compareResult < 0)
            return contains(x, t.left);
        else if (compareResult > 0)
            return contains(x, t.right);
        else return true; // Match
    }

    /**
     * Internal method to find the smallest item in a subtree.
     * Recursive method.
     * @param t the node that roots the subtree.
     * @return node containing the smallest item.
     */
    private BinaryNode<T> findMin(BinaryNode<T> t) {
        if (t == null)
            return null;
        else if (t.left == null)
            return t;
        return findMin(t.left);
    }

    /**
     * Internal method to find the largest item in a subtree.
     * Non-recursive.
     * @param t the node that roots the subtree.
     * @return node containing the largest item.
     */
    private BinaryNode<T> findMax(BinaryNode<T> t) {
        if (t != null)
            while (t.right != null)
                t = t.right;

        return t;
    }

    /**
     * Internal method to insert an item into a subtree.
     * @param x the item to be inserted.
     * @param t the node that roots the subtree.
     * @return the new root of the subtree
     */
    private BinaryNode<T> insert(T x, BinaryNode<T> t) {
        if (t == null)
            return new BinaryNode<>(x, null, null); // inserting
        int compareResult = x.compareTo(t.element);
        if (compareResult < 0)
            t.left = insert(x, t.left); // searching
        else if (compareResult > 0)
            t.right = insert(x, t.right); // searching
        else
            ; // Do nothing or duplicate
        return t;
    }

    /**
     * Internal method to remove an item from a subtree.
     * @param x the item to remove.
     * @param t the node that roots the subtree.
     * @return the new root of the subtree.
     */
    private BinaryNode<T> remove(T x, BinaryNode<T> t) {
        if (t == null)
            return t;

        int compareResult = x.compareTo(t.element);

        if (compareResult < 0)

            // searching/removing down the left subtree.
            t.left = remove(x, t.left);
        else if(compareResult >  0)

            // searching/removing down the right subtree.
            t.right = remove(x, t.right);

            // Node found, and it has TWO children.

        else if(t.left != null && t.right != null)
        {
            // Strategy: replace the node's value with the smallest value
            // from its right subtree (the in-order successor).
            t.element = findMin(t.right).element;

            // Remove that successor node (it will be a duplicate now)
            // from the right subtree.
            t.right = remove(t.element, t.right);
        }
        // Node has ONE child or NO children.
        // If there is a left child, replace this node with it.
        // Otherwise, replace it with the right child (which may be null).
        // This effectively removes the current node from the tree. Removes link.
        else t = (t.left != null) ? t.left : t.right;
        // Return the new root for this subtree (may have changed or become null).
        return t;
    }

    private void printTree(BinaryNode<T> t) {

    }
}
