package Recruitflow;

public class CircularSlotQueue {

    String[] slots;
    int front = -1;
    int rear = -1;
    int size;

    public CircularSlotQueue(int n) {
        size = n;
        slots = new String[n];
    }

    public void add(String slot) {

        if ((rear + 1) % size == front) {
            System.out.println("All interview slots filled.");
            return;
        }

        if (front == -1)
            front = 0;

        rear = (rear + 1) % size;
        slots[rear] = slot;
    }

    public String get() {

        if (front == -1)
            return null;

        String val = slots[front];

        if (front == rear)
            front = rear = -1;
        else
            front = (front + 1) % size;

        return val;
    }
}