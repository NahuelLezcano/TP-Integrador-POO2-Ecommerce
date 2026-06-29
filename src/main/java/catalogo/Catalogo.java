package catalogo;

import java.util.ArrayList;
import java.util.List;

public class Catalogo {

    private Tienda tienda;
    private final List<Item> elementos = new ArrayList<>();

    public void agregarElemento(Item item) {
        elementos.add(item);
    }

    public void quitarElemento(Item item) {
        elementos.remove(item);
    }
}
