package Pedido;

import Tienda.*;
import catalogo.Item;
import java.util.ArrayList;
import java.util.List;

public class Pedido {

    private List<Item> items = new ArrayList<>();
    private Tienda tienda;
    private Estado estadoDelPedido = new Borrador(this);

    public Pedido(Tienda tienda) {
        this.tienda = tienda;
    }

    //Métodos
    public void agregarItem(Item item) {
        estadoDelPedido.agregarItem(item);
    }

    public void removerItem(Item item) {
        estadoDelPedido.removerItem(item);
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

    public List<Item> getItems() {
        return items;
    }

    // Estos metodos solo deberían ser usado por el estado Borrador
    protected void agregarItemAlPedido(Item item) {
        items.add(item);
    }

    protected void quitarItemAlPedido(Item item) {
        items.remove(item);
    }
}
