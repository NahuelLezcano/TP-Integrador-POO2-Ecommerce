package Tienda;

import catalogo.Item;
import java.util.Map;

public class Deposito {

    private Map<Item, Integer> stock;

    public Deposito(Map<Item, Integer> stock) {
        this.stock = stock;
    }

    public void agregarItemAlStock(Item item, Integer cantidad) {
        stock.put(item, cantidad);
    }

    //TODO agregar más métodos
}
