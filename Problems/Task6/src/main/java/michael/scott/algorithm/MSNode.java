package michael.scott.algorithm;

import java.util.concurrent.atomic.AtomicReference;

public class MSNode<T> {
    public T data;
    public AtomicReference<MSNode<T>> next = new AtomicReference<>();

    public MSNode() {}

    public MSNode(T data) {
        this.data = data;
    }

}
