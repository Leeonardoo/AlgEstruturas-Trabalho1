package com.github.leeonardoo.algestruturas.html;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class HTMLParser {

    private File htmlFile;

    //Tags that close with '/>' or doesn't ever close (i.e <img src="https://somewebsite.com/someimage.jpg">)
    private static final String[] singletonTags = {"meta", "base", "br", "col", "command", "embed", "hr", "img", "input", "link", "param", "source", "!DOCTYPE"};

    public void setHtmlFile(File newFile) {
        htmlFile = newFile;
    }

    public void parseFile() {
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
        }

        char[] htmlChars = contentBuilder.toString().toCharArray();

        for (int i = 0; i < htmlChars.length - 1; i++) {
            //Iterate for possible tags
            if (htmlChars[i] == '<') {
                StringBuilder tagBuilder = new StringBuilder();

                //Maybe an opening tag was found @[i]
                openLoop:
                for (int j = i + 1; j < htmlChars.length - 1; j++) {
                    if (htmlChars[j] != '/' && htmlChars[j] != ' ' && htmlChars[j] != '>') {
                        //Now we know it isn't a closing tag
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

                        for (String singletonTag : singletonTags) {
                            if (singletonTag.equals(tag)) {
                                onSingletonCloseTagFound(tag);
                                break openLoop;
                            }
                        }

                        onOpenTagFound(tagBuilder.toString());
                        break;
                    }
                }
            }
        }
    }

    private void onOpenTagFound(String tag) {
        System.out.println("Open tag found: " + tag);
    }

    private void onCloseTagFound(String tag) {
        System.out.println("Close tag found: " + tag);
    }

    private void onSingletonCloseTagFound(String tag) {
        System.out.println("singleton Close tag found: " + tag);
    }
}