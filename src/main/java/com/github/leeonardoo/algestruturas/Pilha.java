package com.github.leeonardoo.algestruturas;

import com.github.leeonardoo.algestruturas.data.ListaEncadeada;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Pilha<t> {

    /*
    private ListaEncadeada lista;
    public String content;

    public String entrada() {
        StringBuilder contentBuilder = new StringBuilder();
        try {
            BufferedReader in = new BufferedReader(new FileReader("C:\\Users\\Mauros\\Downloads\\exemplo.html")); //caminho do arquivo
            String str;
            while ((str = in.readLine()) != null) {
                contentBuilder.append(str);
            }
            in.close();
        } catch (IOException e) {
        }
        this.content = contentBuilder.toString();

        System.out.println(content);
        return content;
    }


    //public static void main(String[] args) {
    // TODO Auto-generated method stub
	*//*	StringBuilder contentBuilder = new StringBuilder();
		try {
		    BufferedReader in = new BufferedReader(new FileReader("C:\\Users\\Mauros\\Downloads\\exemplo.html")); //caminho do arquivo
		    String str;
		    while ((str = in.readLine()) != null) {
		        contentBuilder.append(str);
		    }
		    in.close();
		} catch (IOException e) {
		}
		String content = contentBuilder.toString();
		
		System.out.println(content); 
		**//*
    //}

    public void inserirPilha() {
        String temp = this.content;
        String tag = "";
        //	char ponteiro="";
        //	int cont = 0;
        //	while (ponteiro == ) {
        //	ponteiro = temp.charAt(cont);

//		}
        //	System.out.println(ponteiro);
        //	temp = temp.substring(2);

        this.push(temp);

    }


    public Pilha() {
        lista = new ListaEncadeada();
        lista = null;
    }

    public void push(String info) {
        ListaEncadeada<t> novo = new ListaEncadeada<>();
        novo.setInfo(info);
        novo.setLista(lista);
        this.lista = novo;
    }

    public String pop() {
        ListaEncadeada<t> p;
        p = lista;
        lista = lista.getLista();
        return p.getInfo();
    }

    public String peek() {
        if (this.estaVazia()) {
            //throw new PilhaVaziaException();
        }
        return lista.getInfo();
    }

    public boolean estaVazia() {
        if (lista == null) {
            return true;
        } else {
            return false;
        }
    }

    public void liberar() {
        while (!estaVazia()) {
            pop();
        }
    }

    public String toString() {
        return lista.toString();
    }
    */
}