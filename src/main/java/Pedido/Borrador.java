package Pedido;

import catalogo.Item;

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
        //TODO decrementar el Stock
        return "El borrador del pedido se a confirmado";
    }

    @Override
    public String cancelar() {
        pedido.cambiarEstado(new Cancelado(pedido));
        return "El borrador del pedido se a cancelado";
    }
}
