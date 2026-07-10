package busqueda;

import catalogo.Item;

public class CriterioCategoria implements Criterio {

    private String categoria;

    public CriterioCategoria(String categoria) {
        this.categoria = categoria;
    }

    @Override
    public boolean cumpleCondicion(Item item) {
        return item.getCategoria() == categoria;
    }
}
