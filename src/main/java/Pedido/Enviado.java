package Pedido;

import catalogo.Item;

public class Enviado extends Estado {

    public Enviado(Pedido pedido) {
        super(pedido);
    }

    @Override
    public void agregarItem(Item item) {
        throw new OperacionInvalidaException("El pedido está en camino, no se pueden agregar más items");
    }

    @Override
    public void removerItem(Item item) {
        throw new OperacionInvalidaException("El pedido está en camino, no se pueden remover más items");
    }

    @Override
    public String confirmar() {
        pedido.cambiarEstado(new Entregado(pedido));
        return "El pedido a llegado.";
    }

    @Override
    public String cancelar() {
        pedido.cambiarEstado(new Cancelado(pedido));
        //TODO reembolso (se reembolsa el costo del producto pero no del envío)
        return "El pedido fue cancelado.";
    }
}
