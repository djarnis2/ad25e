package exercises_day_02;

public class Queue {

    Integer[] q;
    Integer len;
    Integer front = 0;
    Integer rear = 0;
    boolean isEmpty = true;

    public Queue(int size) {
        q = new Integer[size];
        len = q.length;
    }

    public void enqueue(Integer i) {
        if (rear.equals(len)) {
            // index of of bounce but rear is always empty, so we correct it here
            if (front.equals(0)) {
                System.out.println("Queue is full");
                return;
            } else {
                isEmpty = false;
                rear = 0;
                q[rear] = i;
                rear += 1;
                return;
            }
        }
        if (rear.equals((front - 1) % len)) {
            // almost full
            isEmpty = false;
            q[rear] = i;
            rear += 1;
            System.out.println("Queue is now full");
        } else {
            // most cases
            isEmpty = false;
            q[rear] = i;
            rear += 1;
        }
    }

    public Integer dequeue() {
        if (front.equals(rear) && isEmpty) {
            isEmpty = true;
            return null;
        }
        if ((front) % len == rear % len) {
            Integer value = q[front];
            front += 1;
            isEmpty = true;
            return value;
        }
        if (front > rear) {
            // front er sidste element, rear er roteret
            Integer value = q[front];
            if (front == len - 1) {
                front = 0;
                return value;
            } else {
                front += 1;
                return value;
            }
        }
        if (front < rear) {
            // flytter pointer en frem, og lader værdien stå
            Integer value = q[front];
            front += 1;
            return value;
        } else {
            return null;

        }
    }

    public void printQ() {
        if (!isEmpty) {
            System.out.println("PRINTQ from index " + front + " to " + ((rear + len)%len));
            if (front > rear) {
                int end = rear + len;
                for (int i = front; i <= end ; i++) {
                    System.out.println(q[i % len]);
                }
            }
            if (front < rear) {
                for (int i = front; i < rear; i++) {
                    System.out.println(q[i]);
                }

            }
        }
        if (front.equals(rear)) {
            System.out.println("Queue is empty");
        }

    }

    public static void main(String[] args) {
        Queue queue = new Queue(5);
        queue.enqueue(12);
        queue.enqueue(14);
        queue.enqueue(16);
        queue.enqueue(18);
        queue.enqueue(20);
        queue.printQ();
        System.out.println("removed: "+queue.dequeue());
        System.out.println("Front: " + queue.front + ", Rear: " + queue.rear);

        queue.enqueue(22);

        System.out.println("removed: "+queue.dequeue());
        System.out.println("Front: " + queue.front + ", Rear: " + queue.rear);
        queue.printQ();

        System.out.println("removed: "+queue.dequeue());
        queue.printQ();
        System.out.println("Front: " + queue.front + ", Rear: " + queue.rear);
        System.out.println("removed: "+queue.dequeue());
        queue.printQ();
        System.out.println("removed: "+queue.dequeue());
        queue.printQ();
        System.out.println("removed: "+queue.dequeue());
        queue.printQ();
        System.out.println("removed: "+queue.dequeue());
        //System.out.println(queue.q.length);
    }
}
