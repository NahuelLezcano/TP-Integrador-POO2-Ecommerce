package busqueda;

import catalogo.Item;

public class CriterioPrecioMax implements Criterio {

    private int precioMaximo;

    public CriterioPrecioMax(int precioMaximo) {
        this.precioMaximo = precioMaximo;
    }

    @Override
    public boolean cumpleCondicion(Item item) {
        return item.getPrecioBase() <= precioMaximo;
    }
}
