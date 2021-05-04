package com.github.leeonardoo.algestruturas.html;

import com.github.leeonardoo.algestruturas.data.ListaEstaticaTag;

public interface HandlerCallback {

    void onError(String msg);

    void onSuccess(ListaEstaticaTag listaTags);
}
