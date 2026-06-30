package Pedido;

import catalogo.Item;

public class Entregado extends Estado {

    public Entregado(Pedido pedido) {
        super(pedido);
    }

    @Override
    public void agregarItem(Item item) {
        throw new OperacionInvalidaException("No se pueden agregar items, el pedido ha sido entregado.");
    }

    @Override
    public void removerItem(Item item) {
        throw new OperacionInvalidaException("No se pueden remover items, el pedido ha sido entregado.");
    }

    @Override
    public String confirmar() {
        throw new OperacionInvalidaException("El cliente recibió el pedido. Estado terminal.");
    }

    @Override
    public String cancelar() {
        throw new OperacionInvalidaException("El cliente recibió el pedido. Estado terminal.");
    }
}
