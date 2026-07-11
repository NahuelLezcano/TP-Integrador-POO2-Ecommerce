package busqueda;

import catalogo.Item;

public class Negacion implements Criterio {

    private Criterio condicion;

    public Negacion(Criterio condicion) {
        this.condicion = condicion;
    }

    @Override
    public boolean cumpleCondicion(Item item) {
        return !condicion.cumpleCondicion(item);
    }
}
