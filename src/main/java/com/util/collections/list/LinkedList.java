package com.util.collections.list;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class LinkedList<T> extends AbstractList<T> {

    //==================== Inner class for representing a node of this LinkedList ===========//
    static final class Node<T> {
        private final T data;
        private Node<T> next;

        public Node(T data) {
            this.data = data;
        }
    }
    //=======================================================================================//

    //==================== FIELDS OF LINKED LIST ============================================//
    private Node<T> head;
    private Node<T> tail;
    private int count = 0;
    private final boolean isAllowNull;
    //=======================================================================================//

    //==================== CONSTRUCTOR'S ====================================================//
    public LinkedList() {
        isAllowNull = false;
    }

    public LinkedList(boolean isAllowNull) {
        this.isAllowNull = isAllowNull;
    }
    //=======================================================================================//

    private void linkLast(T data) {
        if (!isAllowNull && Objects.isNull(data)) {
            throw new IllegalArgumentException("LinkedList doesn't allow null value");
        }

        Node<T> newNode = new Node<>(data);
        if (head == null) {
            head = newNode;
        } else {
            tail.next = newNode;
        }
        tail = newNode;
        count++;
    }

    private void linkFirst(T data) {
        if (!isAllowNull && Objects.isNull(data)) {
            throw new IllegalArgumentException("LinkedList doesn't allow null value");
        }

        Node<T> newNode = new Node<>(data);
        newNode.next = head;
        head = newNode;
        if (tail == null) {
            tail = head;
        }
        count++;
    }

    @Override
    public boolean add(T val) {
        linkLast(val);
        return true;
    }

    private void checkIsInsertable(int index) {
        if (!isInInsertableBoundary(index)) {
            throwIndexOutOfBoundException(index);
        }
    }

    private boolean isInInsertableBoundary(int index) {
        return index >= 0 && index <= count;
    }

    private void throwIndexOutOfBoundException(int index) {
        throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + count);
    }

    private void insertAt(T val, int index) {
        checkIsInsertable(index);

        if (index == 0) {
            linkFirst(val);
            return;
        }
        if (index == count) {
            linkLast(val);
            return;
        }

        Node<T> newNode = new Node<>(val);
        Node<T> currNode = head;
        for (int i = 1; i < index; i++) {
            currNode = currNode.next;
        }
        newNode.next = currNode.next;
        currNode.next = newNode;
        count++;
    }

    @Override
    public boolean add(T val, int index) {
        insertAt(val, index);
        return true;
    }

    private boolean isValidIndex(int index) {
        return index >= 0 && index < count;
    }

    private void checkIndex(int index) {
        if (!isValidIndex(index)) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + count);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);

        Node<T> current = head;
        int pos = 0;
        while (pos != index) {
            current = current.next;
            pos++;
        }
        return current.data;
    }

    @Override
    public boolean remove(T val) {
        if (Objects.isNull(head))
            return false;
        if (Objects.equals(head.data, val)) {
            head = head.next;
            count--;
            return true;
        }
        Node<T> prev = head;
        Node<T> current = head.next;
        while (current != null) {
            if (Objects.equals(current.data, val)) {
                prev.next = current.next;
                count--;
                return true;
            } else {
                prev = current;
                current = current.next;
            }
        }
        return false;
    }

    //==================== Search Operations ====================================================//
    @Override
    public boolean contains(Object val) {
        if (isAllowNull && val == null)
            return false;
        Node<T> current = head;
        while (current != null) {
            if (Objects.equals(current.data, val)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }
    //===========================================================================================//
    @Override
    public int size() {
        return count;
    }

    @Override
    public boolean isEmpty() {
        return count == 0;
    }

    @Override
    public Iterator<T> iterator() {
        return new LinkedListIterator();
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return super.toString();
    }

    //=============================== LINKED LIST ITERATOR ==========================================//
    private class LinkedListIterator implements Iterator<T> {
        Node<T> currentNode = head;

        @Override
        public boolean hasNext() {
            return currentNode != null;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            T data = currentNode.data;
            currentNode = currentNode.next;
            return data;
        }
    }
    //===============================================================================================//
}

