package exercises_day_02;

public class MyLinkedList {
    private final Object element;
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

    @Override
    public String toString() {
        return element + (tail != null ? " -> " + tail : "");
    }


    public static void main(String[] args) {
        MyLinkedList myList = new MyLinkedList("w", new MyLinkedList("f"));
        System.out.println(myList.toString());
        System.out.println(myList.length());
        System.out.println("Add element to list");
        myList.add(5);
        System.out.println(myList.toString());
        System.out.println(myList.length());
    }
}
