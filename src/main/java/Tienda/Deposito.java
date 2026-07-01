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

    public void removerDelStock(Item item, int cantidad) {
        int actual = stock.getOrDefault(item, 0);
        if (actual < cantidad) {
            throw new IllegalArgumentException("Stock insuficiente");
        }
        stock.put(item, actual - cantidad);
    }

    public int cantidadEnStock(Item item) {
        return stock.getOrDefault(item, 0);
    }

    //TODO agregar más métodos
}
