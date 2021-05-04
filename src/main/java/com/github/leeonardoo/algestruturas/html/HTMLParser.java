package com.github.leeonardoo.algestruturas.html;

import com.github.leeonardoo.algestruturas.data.ListaEstaticaTag;
import com.github.leeonardoo.algestruturas.data.PilhaLista;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class HTMLParser {

    private File htmlFile;
    private final ParserCallback callback;

    //Tags that close with '/>' or doesn't ever close (i.e <img src="https://somewebsite.com/someimage.jpg">)
    private static final String[] singletonTags = {"meta", "base", "br", "col", "command", "embed", "hr", "img", "input", "link", "param", "source", "!DOCTYPE"};

    private final PilhaLista<String> openStack = new PilhaLista<>();

    ListaEstaticaTag tags = new ListaEstaticaTag();

    public HTMLParser(ParserCallback callback) {
        this.callback = callback;
    }

    public void setHtmlFile(File newFile) {
        htmlFile = newFile;
    }

    public void parseFile() {
        openStack.liberar();
        tags.liberar();

        StringBuilder contentBuilder = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(htmlFile));
            String str;

            while ((str = reader.readLine()) != null) {
                contentBuilder.append(str);
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
            callback.onError(e.getMessage());
        } catch (NullPointerException e) {
            callback.onError("Selecione um arquivo primeiro!");
        }

        try {
            handleString(contentBuilder.toString());
            callback.onSuccess(tags);
        } catch (Exception e) {
            e.printStackTrace();
            callback.onError(e.getMessage());
        }
    }

    private void handleString(String htmlText) {
        char[] htmlChars = htmlText.toCharArray();

        for (int i = 0; i < htmlChars.length - 1; i++) {
            if (htmlChars[i] == '<') {
                StringBuilder tagBuilder = new StringBuilder();

                openLoop:
                for (int j = i + 1; j < htmlChars.length; j++) {
                    if (htmlChars[j] != '/' && htmlChars[j] != ' ' && htmlChars[j] != '>') {
                        tagBuilder.append(htmlChars[j]);
                    } else if (htmlChars[j] == '/') {
                        StringBuilder closeTagBuilder = new StringBuilder();
                        for (int k = j + 1; k < htmlChars.length; k++) {
                            if (htmlChars[k] == '>') {
                                onCloseTagFound(closeTagBuilder.toString());
                                break openLoop;
                            } else {
                                closeTagBuilder.append(htmlChars[k]);
                            }
                        }
                    } else {
                        String tag = tagBuilder.toString();

                        boolean singletonFound = isSingletonTag(tag);

                        for (String singletonTag : singletonTags) {
                            if (singletonTag.equals(tag)) {
                                singletonFound = true;
                                break;
                            }
                        }

                        if (singletonFound) {
                            onOpenSingletonTagFound(tag);
                        } else {
                            onOpenTagFound(tag);
                        }

                        for (int l = j; l < htmlChars.length; l++) {
                            if ((l + 1 < htmlChars.length && htmlChars[l + 1] == '>') || (l + 2 < htmlChars.length && (htmlChars[l + 1] == '/' && htmlChars[l + 2] == '>'))) {
                                if (singletonFound) {
                                    onSingletonCloseTagFound(tag);
                                } else {

                                }
                                break openLoop;
                            }
                        }
                        break;
                    }
                }
            }
        }
    }

    private boolean isSingletonTag(String tag) {
        boolean singletonFound = false;

        for (String singletonTag : singletonTags) {
            if (singletonTag.equals(tag)) {
                singletonFound = true;
                break;
            }
        }

        return singletonFound;
    }

    private void onOpenTagFound(String tag) {
        openStack.push(tag);
    }

    private void onOpenSingletonTagFound(String tag) {
        openStack.push(tag);
    }

    private void onCloseTagFound(String tag) {
        if (!openStack.peek().equals(tag)) {
            if (isSingletonTag(tag)) {
                throw new IllegalStateException("Foi encontrada a tag de fechamento " + tag + " enquanto a tag esperada era '>' ou '/>'");
            } else {
                throw new IllegalStateException("Foi encontrada a tag de fechamento " + tag + " enquanto a tag esperada era " + openStack.peek());
            }
        }
        openStack.pop();
        insert(tag);
    }

    private void onSingletonCloseTagFound(String tag) {
        if (!openStack.peek().equals(tag)) {
            if (isSingletonTag(openStack.peek())) {
                throw new IllegalStateException("Foi encontrada a tag de fechamento " + tag + " enquanto a tag esperada era '>' ou '/>'");
            } else {
                throw new IllegalStateException("Foi encontrada a tag de fechamento " + tag + " enquanto a tag esperada era " + openStack.peek());
            }
        }
        openStack.pop();
        insert(tag);
    }

    private void insert(String tag) {
        int index = tags.buscarIndexInicio(tag);

        if (index != -1) {
            String current = tags.obterElemento(index);
            int newIndex = current.indexOf('!');
            int count = Integer.parseInt(current.substring(newIndex + 1));
            count++;

            tags.inserirEm(index, current.substring(0, newIndex) + "!" + count);
        } else {
            tags.inserir(tag + "!1");
        }
    }
}