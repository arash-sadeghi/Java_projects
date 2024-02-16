interface Query<T, U> {
    U search(T index);
    Query<T, U> add(T index, U value);
    Query<T, U> delete(T index);
}

abstract class SearchableNode<T, U> {
    private T index; // the index of the node
    private U value; // the value of the node
    public T getIndex() { return index;}
    public U getValue() {return value;}
    public void setIndex(T index) {this.index = index;}
    public void setValue(U value) {this.value = value;}
}

class LinkedListNode<T, U> extends SearchableNode<T, U> {
    private LinkedListNode<T, U> next;
    public LinkedListNode(T index, U value) {
        setIndex(index);
        setValue(value);
        next = null;
    }
    public LinkedListNode<T, U> getNext() {
        return next;
    }
    public void setNext(LinkedListNode<T, U> next) {
        this.next = next;
    }
}


class LinkedList<T, U> implements Query<T, U> {
    private LinkedListNode<T, U> head;

    public LinkedList() {
        head = null;
    }

    @Override
    public U search(T index) {
        System.out.println("Searching for element with index " + index + ":");
        LinkedListNode<T, U> current = head;
        while (current != null) {
            System.out.println("Traversed: " + current.getIndex());
            if (current.getIndex().equals(index)) {
                return current.getValue();
            }
            current = current.getNext();
        }
        return null;
    }

    @Override
    public Query<T, U> add(T index, U value) {
        LinkedListNode<T, U> newNode = new LinkedListNode<>(index, value);
        newNode.setNext(head);
        head = newNode;
        return this;
    }

    @Override
    public Query<T, U> delete(T index) {
        if (head == null) {
            return this;
        }

        if (head.getIndex().equals(index)) {
            head = head.getNext();
            return this;
        }

        LinkedListNode<T, U> current = head;
        while (current.getNext() != null) {
            if (current.getNext().getIndex().equals(index)) {
                current.setNext(current.getNext().getNext());
                break;
            }
            current = current.getNext();
        }
        return this;
    }
}

public class Main {
    public static void main(String[] args) {
        // Create a LinkedList of Integer values
        LinkedList<Integer, String> linkedList = new LinkedList<>();

        // Insert some elements
        linkedList.add(1, "One");
        linkedList.add(2, "Two");
        linkedList.add(3, "Three");
        linkedList.add(4, "Four");
        linkedList.add(5, "Five");

        // Search for an element and print elements during the search
        int searchKey = 3;
        String result = linkedList.search(searchKey);
        if (result != null) {
            System.out.println("Element found: " + result);
        } else {
            System.out.println("Element not found.");
        }

        // Delete an element
        int deleteKey = 2;
        linkedList.delete(deleteKey);
        System.out.println("Deleted element with key " + deleteKey);

        // Search again after deletion
        searchKey = 2;
        result = linkedList.search(searchKey);
        if (result != null) {
            System.out.println("Element found: " + result);
        } else {
            System.out.println("Element not found.");
        }
    }
}
