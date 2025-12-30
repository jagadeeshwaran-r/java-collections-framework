package com.util.collections.list;

import java.util.Objects;

abstract class AbstractList<T> implements List<T> {

    protected int count = 0;
    protected final boolean isNullable;

    protected AbstractList(boolean isNullable) {
        this.isNullable = isNullable;
    }

    protected void checkIsInsertable(int index) {
        if (!isInInsertableBoundary(index)) {
            throwIndexOutOfBoundException(index);
        }
    }

    protected void checkIndex(int index) {
        if (!isValidIndex(index)) {
            throwIndexOutOfBoundException(index);
        }
    }

    protected boolean isInInsertableBoundary(int index) {
        return index >= 0 && index <= count;
    }

    protected boolean isValidIndex(int index) {
        return index >= 0 && index < count;
    }

    protected void throwIndexOutOfBoundException(int index) {
        throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + count);
    }

    protected void checkNullAllowed(T data) {
        if (!isNullable && Objects.isNull(data)) {
            throw new IllegalArgumentException("List doesn't allow null value");
        }
    }
}
