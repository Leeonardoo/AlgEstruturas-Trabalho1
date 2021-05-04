package com.github.leeonardoo.algestruturas.html;

import com.github.leeonardoo.algestruturas.data.ListaEstaticaTag;

public interface ParserCallback {

    void onError(String msg);

    void onSuccess(ListaEstaticaTag listaTags);
}
