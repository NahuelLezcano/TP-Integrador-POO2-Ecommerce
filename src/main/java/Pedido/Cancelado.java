package Pedido;

import catalogo.Item;

public class Cancelado extends Estado {

    public Cancelado(Pedido pedido) {
        super(pedido);
    }

    @Override
    public void agregarItem(Item item) {
        throw new OperacionInvalidaException("No se pueden agregar items, el pedido está cancelado.");
    }

    @Override
    public void removerItem(Item item) {
        throw new OperacionInvalidaException("No se pueden remover items, el pedido está cancelado.");
    }

    @Override
    public String confirmar() {
        throw new OperacionInvalidaException("El pedido fue cancelado. Estado terminal.");
    }

    @Override
    public String cancelar() {
        throw new OperacionInvalidaException("El pedido fue cancelado. Estado terminal.");
    }
}
