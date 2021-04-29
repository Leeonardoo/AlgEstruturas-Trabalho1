package com.github.leeonardoo.algestruturas.data;

public interface Pilha<T> {

    void push(T valor);

    T pop();

    T peek();

    boolean estaVazia();

    void liberar();
}
