package com.github.leeonardoo.algestruturas.data;

public class PilhaVetor<T> implements Pilha<T> {

    private T[] info;
    private final int limite;
    private int tamanho = 0;

    @SuppressWarnings("unchecked")
    public PilhaVetor(int limite) {
        this.limite = limite;
        info = (T[]) new Object[limite];
    }

    @Override
    public void push(T valor) {
        if (limite == tamanho) {
            throw new PilhaCheiaException("Capacidade esgotada da pilha");
        }

        info[tamanho] = valor;
        tamanho++;
    }

    @Override
    public T pop() {
        T valor = peek();

        tamanho--;
        return valor;
    }

    @Override
    public T peek() {
        if (estaVazia()) {
            throw new PilhaVaziaException("A pilha está vazia");
        }

        return info[tamanho - 1];
    }

    @Override
    public boolean estaVazia() {
        return tamanho == 0;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void liberar() {
        info = (T[]) new Object[limite];
        tamanho = 0;
    }

    @Override
    public String toString() {
        StringBuilder elements = new StringBuilder();

        for (int index = 0; index < tamanho; index++) {
            elements.append(info[index].toString()).append(",");
        }

        elements.deleteCharAt(elements.lastIndexOf(","));
        return elements.toString();
    }

    public void concatenar(PilhaVetor<T> p) {
        for (int i = 0; i < p.tamanho; i++) {
            this.push(p.info[i]);
        }
    }
}
