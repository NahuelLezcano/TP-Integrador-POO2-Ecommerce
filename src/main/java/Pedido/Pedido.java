package Pedido;

import Tienda.*;
import catalogo.Item;
import java.util.HashMap;
import java.util.Map;

public class Pedido {

    private Map<Item, Integer> items = new HashMap<>();
    private Tienda tienda;
    private Estado estadoDelPedido = new Borrador(this);

    public Pedido(Tienda tienda) {
        this.tienda = tienda;
    }

    //Métodos
    public void agregarItem(Item item, Integer cantidad) {
        estadoDelPedido.agregarItem(item, cantidad);
    }

    public void removerItem(Item item, Integer cantidad) {
        estadoDelPedido.removerItem(item, cantidad);
    }

    public String confirmar() {
        return estadoDelPedido.confirmar();
    }

    public String cancelar() {
        return estadoDelPedido.cancelar();
    }

    public void cambiarEstado(Estado estado) {
        estadoDelPedido = estado;
    }

    public Tienda getTienda() {
        return tienda;
    }

    public Map<Item, Integer> getItems() {
        return items;
    }

    // Estos métodos solo deberían ser usado por el estado Borrador
    protected void agregarItemAlPedido(Item item, Integer cantidad) {
        items.merge(item, cantidad, Integer::sum);
    }

    protected void quitarItemAlPedido(Item item, Integer cantidad) {
        // Validar entrada inválida
        if (cantidad <= 0) return;

        items.computeIfPresent(item, (key, stockActual) -> {
            if (cantidad >= stockActual) {
                // Caso borde: Se pide más o igual de lo que hay -> Eliminamos del mapa (retorna null)
                return null;
            }
            // Caso normal: Queda stock positivo restante
            return stockActual - cantidad;
        });
    }
}
