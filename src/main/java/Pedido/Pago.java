package Pedido;

import catalogo.Item;

public class Pago extends Estado {

    public Pago(Pedido pedido) {
        super(pedido);
    }

    @Override
    public void agregarItem(Item item) {
        throw new OperacionInvalidaException("El pedido está pago, no se pueden agregar más items");
    }

    @Override
    public void removerItem(Item item) {
        throw new OperacionInvalidaException("El pedido está pago, no se pueden remover más items");
    }

    @Override
    public String confirmar() {
        pedido.cambiarEstado(new EnPreparacion(pedido));
        return "El cliente confirmó.";
    }

    @Override
    public String cancelar() {
        pedido.cambiarEstado(new Cancelado(pedido));
        //TODO incrementar Stock
        return "El pedido fue cancelado.";
    }
}
