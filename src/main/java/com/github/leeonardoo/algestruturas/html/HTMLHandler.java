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

    //Tags that close with '/>' or doesn't ever close (i.e <img src="https://somewebsite.com/someimage.jpg">)
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
                                onCloseTag(closeTagBuilder.toString());
                                break openLoop;
                            } else {
                                closeTagBuilder.append(htmlChars[k]);
                            }
                        }
                    } else {
                        String tag = tagBuilder.toString();

                        boolean isSingleton = isSingletonTag(tag);

                        for (String singletonTag : singletonTags) {
                            if (singletonTag.equals(tag)) {
                                isSingleton = true;
                                break;
                            }
                        }

                        if (isSingleton) {
                            onSingletonOpen(tag);
                        } else {
                            onTagOpen(tag);
                        }

                        for (int l = j; l < htmlChars.length; l++) {
                            if ((l + 1 < htmlChars.length && htmlChars[l + 1] == '>') || (l + 2 < htmlChars.length && (htmlChars[l + 1] == '/' && htmlChars[l + 2] == '>'))) {
                                if (isSingleton) {
                                    onCloseSingleton(tag);
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