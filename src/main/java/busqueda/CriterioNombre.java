package busqueda;

import catalogo.Item;

public class CriterioNombre implements Criterio {

    private String palabra;

    public CriterioNombre(String palabra) {
        this.palabra = palabra;
    }

    @Override
    public boolean cumpleCondicion(Item item) {
        return item.getNombre().toLowerCase().contains(palabra.toLowerCase());
    }
}
