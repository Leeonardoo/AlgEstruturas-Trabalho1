package com.github.leeonardoo.algestruturas.data;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked")
public class ListaEstaticaTag {

    private String[] info = new String[10];
    private int tamanho = 0;

    @SuppressWarnings("unchecked")
    private void redimensionar() {
        String[] oldArr = info;
        info = new String[oldArr.length + 10];
        for (int i = 0; i < oldArr.length; i++) {
            info[i] = oldArr[i];
        }
    }

    public void inserir(String item) {
        if (tamanho == info.length) {
            redimensionar();
        }
        //Zero-indexed
        info[tamanho] = item;
        tamanho++;
    }

    public void exibir() {
        System.out.println(this.toString());
    }

    public int buscar(String item) {
        for (int i = 0; i < getTamanho() - 1; i++) {
            if (obterElemento(i).equals(item)) {
                return i;
            }
        }
        return -1;
    }

    public int buscarIndexInicio(String inicio) {
        for (int i = 0; i < getTamanho(); i++) {
            if (obterElemento(i).startsWith(inicio)) {
                return i;
            }
        }

        return -1;
    }

    public void inserirEm(int index, String s) {
        if (tamanho == info.length) {
            redimensionar();
        }

        info[index] = s;
    }

    public void retirar(String item) {
        int index = buscar(item);

        if (index != -1) {
            for (int i = index; i < getTamanho() - 1; i++) {
                info[i] = info[i + 1];
            }
            tamanho--;
        }
    }

    @SuppressWarnings("unchecked")
    public void liberar() {
        info = new String[10];
        tamanho = 0;
    }

    public String obterElemento(int position) {
        //Zero-indexed
        if (position > getTamanho() - 1 || position < 0) {
            throw new IndexOutOfBoundsException("Trying to access an index out the bounds of the current items on the list! Requested index was " + position + " while the size is " + getTamanho());
        }
        return info[position];
    }

    public boolean estaVazia() {
        return tamanho == 0;
    }

    public int getTamanho() {
        return tamanho;
    }

    public void inverter() {
        for (int i = 0; i < getTamanho() / 2; i++) {
            String temp = info[i];
            int farIndex = getTamanho() - 1 - i; //Zero-indexed
            info[i] = info[farIndex];
            info[farIndex] = temp;
        }
    }

    @Override
    public String toString() {
        StringBuilder elements = new StringBuilder();
        for (int i = 0; i < getTamanho(); i++) {
            elements.append(obterElemento(i).toString());
            if (i < getTamanho() - 1) {
                elements.append(",");
            }
        }

        return elements.toString();
    }
}