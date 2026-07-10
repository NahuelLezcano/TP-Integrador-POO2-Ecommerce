package busqueda;

import catalogo.Item;

public interface Criterio {

    boolean cumpleCondicion(Item item);
}

