package Pedido;

import Tienda.Tienda;
import catalogo.Item;

import java.time.LocalDate;

public class Borrador extends Estado {

    public Borrador(Pedido pedido) {
        super(pedido);
    }

    @Override
    public void agregarItem(Item item, Integer cantidad) {
        pedido.agregarItemAlPedido(item, cantidad);
    }

    @Override
    public void removerItem(Item item, Integer cantidad) {
        pedido.quitarItemAlPedido(item, cantidad);
    }

    @Override
    public String confirmar() {
        pedido.cambiarEstado(new Pago(pedido));
        this.realizarCompra(pedido.getTienda()); //Esta acción decrementa el stock
        return "El borrador del pedido se a confirmado";
    }

    @Override
    public String cancelar() {
        pedido.cambiarEstado(new Cancelado(pedido));
        return "El borrador del pedido se a cancelado";
    }

    private void realizarCompra(Tienda tienda) {
        pedido.getItems().forEach((item, cantidad) -> tienda.registrarVenta(item, cantidad, LocalDate.now()));
    }
}
