package exercises_day_02;

// My own attempt to implement a linked list

public class MyLinkedList {
    private Object element;
    private MyLinkedList tail;

    public MyLinkedList(Object element) {
        this.element = element;
    }

    public MyLinkedList(Object element, MyLinkedList tail) {
        this.element = element;
        this.tail = tail;
    }

    public int length() {
        int count = 0;
        MyLinkedList current = this;
        while (current != null) {
            count++;
            current = current.tail;
        }
        return count;
    }

    public void add(Object element) {
        MyLinkedList current = this;
        while (current.tail != null) {
            current = current.tail;
        } current.tail = new MyLinkedList(element);
    }

    public void insert(Object element, int idx) {
        if (idx < 0) return;
        // Special case: because there is no header node, inserting at index 0 requires shifting
        // the current head node down and replacing its element, instead of simply linking before it.
        if (idx == 0) {
            this.tail = new MyLinkedList(this.element, this.tail);
            this.element = element;
            return;
        }
        int count = 0;
        MyLinkedList prev = this;
        while (prev != null && count < idx - 1) {
            count++;
            prev = prev.tail;
        }
        prev.tail = new MyLinkedList(element, prev.tail);
    }
    public Object delete(int idx) {
        if (idx < 0) {
            return null;
        }
        // Special case: without a header node, deleting index 0 requires copying data from the
        // second node into the first, since we cannot change the external reference to the list.
        if (idx == 0) {
            Object removed = this.element; // this.element is not specifically removed, that is left for the garbage collector to do.
            // If the list consists of several elements
            if (this.tail != null) {
                this.element = this.tail.element; // Effectively makes a new list starting from the element found through the tail of the first element.
                this.tail = this.tail.tail; // legal because tail != null, this.tail.tail is allowed to be null.
                // Else the list consists of one element only
            } else {
                this.element = null; // Clears the element in the head node; list will appear empty if emptiness is defined by element == null.
            }
            return removed;
        }
        MyLinkedList prev = this;
        int i = 0;
        while (prev.tail != null && i < idx - 1) {
            i++;
            prev = prev.tail;
        } if (prev == null || prev.tail == null) {
            return null;
        }
        Object removed = prev.tail.element;
        prev.tail = prev.tail.tail;
        return removed;
    }


    @Override
    public String toString() {
        return element + (tail != null ? " -> " + tail : "");
    }


    public static void main(String[] args) {
        System.out.println("Testing MyLinkedList:");
        System.out.println("Making a list with 3 elements, num1, num2, num3");
        MyLinkedList list1 = new MyLinkedList("num1");
        list1.add("num2");
        list1.add("num3");
        System.out.println("inserting elements at idx 0 and 2");
        list1.insert("obj_inserted_at_0", 0);
        list1.insert("obj_inserted_at_2", 2);
        System.out.println(list1);
        System.out.print("list length: ");
        System.out.println(list1.length());
        System.out.println("Making another list with elements 'w' and 'f' and testing toString method");
        MyLinkedList myList = new MyLinkedList("w", new MyLinkedList("f"));
        System.out.println(myList.toString());
        System.out.println("Adding an int to list with strings...");
        myList.add(5);
        System.out.println("Testing toString and length() on list 2");
        System.out.println(myList.toString());
        System.out.println(myList.length());
        System.out.println("Testing delete on idx 0");
        System.out.println("Deleted: " + myList.delete(0));
        System.out.println(myList);
    }
}
