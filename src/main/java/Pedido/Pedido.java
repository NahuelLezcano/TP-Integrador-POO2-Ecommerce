package Pedido;

import catalogo.Item;
import Tienda.Deposito;
import java.util.ArrayList;
import java.util.List;

public class Pedido {

    private List<Item> items = new ArrayList<>();
    private Deposito deposito;
    private Estado estadoDelPedido = new Borrador(this);

    public Pedido(Deposito deposito) {
        this.deposito = deposito;
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

    // Estos metodos solo deberían ser usado por el estado Borrador
    protected void agregarItemAlPedido(Item item) {
        items.add(item);
    }

    protected void quitarItemAlPedido(Item item) {
        items.remove(item);
    }
}
