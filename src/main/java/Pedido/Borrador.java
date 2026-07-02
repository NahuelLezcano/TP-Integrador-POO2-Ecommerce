package Pedido;

import Tienda.Tienda;
import catalogo.Item;

import java.time.LocalDate;
import java.util.Date;

public class Borrador extends Estado {

    public Borrador(Pedido pedido) {
        super(pedido);
    }

    @Override
    public void agregarItem(Item item) {
        pedido.agregarItemAlPedido(item);
    }

    @Override
    public void removerItem(Item item) {
        pedido.quitarItemAlPedido(item);
    }

    @Override
    public String confirmar() {
        pedido.cambiarEstado(new Pago(pedido));
        this.realizarCompra(pedido.getTienda());
        return "El borrador del pedido se a confirmado";
    }

    @Override
    public String cancelar() {
        pedido.cambiarEstado(new Cancelado(pedido));
        return "El borrador del pedido se a cancelado";
    }

    private void realizarCompra(Tienda tienda) {
        pedido.getItems().forEach(item -> tienda.registrarVenta(item, 1, item.getPrecioFinal(), LocalDate.now()));
    }
}
