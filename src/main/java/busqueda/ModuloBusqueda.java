package busqueda;

import catalogo.*;
import java.util.List;

public class ModuloBusqueda {

    private Catalogo catalogo;

    public ModuloBusqueda(Catalogo catalogo) {
        this.catalogo = catalogo;
    }

    public List<Item> obtener(Criterio criterio) {
        return catalogo.getItems().stream().filter(item -> criterio.cumpleCondicion(item)).toList();
    }
}
