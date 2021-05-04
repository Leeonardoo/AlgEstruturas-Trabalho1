package com.github.leeonardoo.algestruturas.html;

import com.github.leeonardoo.algestruturas.TagCount;
import com.github.leeonardoo.algestruturas.data.ListaEstaticaTag;
import com.github.leeonardoo.algestruturas.data.PilhaLista;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class HTMLHandler {

    private File file;
    private final HandlerCallback callback;

    private static final String[] singletonTags = {"meta", "base", "br", "col", "command", "embed", "hr", "img", "input", "link", "param", "source", "!DOCTYPE"};

    private final PilhaLista<String> openStack = new PilhaLista<>();

    ListaEstaticaTag tags = new ListaEstaticaTag();

    public HTMLHandler(HandlerCallback callback) {
        this.callback = callback;
    }

    public void setFile(File newFile) {
        file = newFile;
    }

    public void handleFile() {
        openStack.liberar();
        tags.liberar();

        StringBuilder contentBuilder = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String str;

            while ((str = reader.readLine()) != null) {
                contentBuilder.append(str);
            }

            reader.close();

            handleTags(contentBuilder.toString());
            callback.onSuccess(tags);

        } catch (NullPointerException e) {
            callback.onError("Selecione um arquivo primeiro!");
        } catch (Exception e) {
            e.printStackTrace();
            callback.onError(e.getMessage());
        }
    }

    private void handleTags(String htmlText) {
        char[] htmlChars = htmlText.toCharArray();

        for (int c = 0; c < htmlChars.length - 1; c++) {
            if (htmlChars[c] == '<') {
                StringBuilder tagBuilder = new StringBuilder();

                tagLoop:
                for (int i = c + 1; i < htmlChars.length; i++) {
                    if (htmlChars[i] != '/' && htmlChars[i] != ' ' && htmlChars[i] != '>') {
                        tagBuilder.append(htmlChars[i]);
                    } else if (htmlChars[i] == '/') {
                        StringBuilder closeTagBuilder = new StringBuilder();
                        for (int k = i + 1; k < htmlChars.length; k++) {
                            if (htmlChars[k] == '>') {
                                onCloseTag(closeTagBuilder.toString());
                                break tagLoop;
                            } else {
                                closeTagBuilder.append(htmlChars[k]);
                            }
                        }
                    } else {
                        String tag = tagBuilder.toString();

                        boolean isSingleton = isSingletonTag(tag);

                        if (isSingleton) {
                            onSingletonOpen(tag);
                        } else {
                            onTagOpen(tag);
                        }

                        for (int j = i; j < htmlChars.length; j++) {
                            if ((j + 1 < htmlChars.length && htmlChars[j + 1] == '>') || (j + 2 < htmlChars.length && (htmlChars[j + 1] == '/' && htmlChars[j + 2] == '>'))) {
                                if (isSingleton) {
                                    onCloseSingleton(tag);
                                }
                                break tagLoop;
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

    private void onTagOpen(String tag) {
        openStack.push(tag);
    }

    private void onSingletonOpen(String tag) {
        openStack.push(tag);
    }

    private void onCloseTag(String tag) {
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

    private void onCloseSingleton(String tag) {
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
        int index = tags.buscarIndexTag(tag);

        if (index != -1) {
            TagCount tagCount = tags.obterElemento(index);
            tagCount.setCount(tagCount.getCount() + 1);

            tags.inserirEm(index, tagCount);
        } else {
            tags.inserir(new TagCount(tag, 1));
        }
    }
}