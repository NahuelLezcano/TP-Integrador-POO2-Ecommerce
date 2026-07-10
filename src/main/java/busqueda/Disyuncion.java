package busqueda;

import catalogo.Item;

public class Disyuncion implements Criterio {

    private Criterio condicion1;
    private Criterio condicion2;

    public Disyuncion(Criterio condicion1, Criterio condicion2) {
        this.condicion1 = condicion1;
        this.condicion2 = condicion2;
    }

    @Override
    public boolean cumpleCondicion(Item item) {
        return condicion1.cumpleCondicion(item) || condicion2.cumpleCondicion(item);
    }
}
