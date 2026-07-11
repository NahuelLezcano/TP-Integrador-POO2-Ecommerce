package Pedido;

import catalogo.Item;

public class Enviado extends Estado {

    public Enviado(Pedido pedido) {
        super(pedido);
        this.nombreDelEstado = "Enviado";
    }

    @Override
    public void agregarItem(Item item, Integer cantidad) {
        throw new OperacionInvalidaException("El pedido está en camino, no se pueden agregar más items");
    }

    @Override
    public void removerItem(Item item, Integer cantidad) {
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
        return "El pedido fue cancelado.";
    }
}
