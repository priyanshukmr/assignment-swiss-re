package org.assignment;

public class Metric<T> {
    String id;
    T value;

    Metric(String id, T value) {
        this.id = id;
        this.value = value;
    }

    public String getId() {
        return this.id;
    }

    @Override
    public String toString() {
        return "id=" + id + ", value=" + value;
    }
}
