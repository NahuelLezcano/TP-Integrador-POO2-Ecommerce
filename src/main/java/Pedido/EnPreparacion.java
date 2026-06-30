package Pedido;

import catalogo.Item;

public class EnPreparacion extends Estado {

    public EnPreparacion(Pedido pedido) {
        super(pedido);
    }

    @Override
    public void agregarItem(Item item) {
        throw new OperacionInvalidaException("El pedido está en preparación, no se pueden agregar más items");
    }

    @Override
    public void removerItem(Item item) {
        throw new OperacionInvalidaException("El pedido está en preparación, no se pueden remover más items");
    }

    @Override
    public String confirmar() {
        pedido.cambiarEstado(new Enviado(pedido));
        return "El paquete será enviado.";
    }

    @Override
    public String cancelar() {
        pedido.cambiarEstado(new Cancelado(pedido));
        //TODO se repone Stock
        //TODO reembolso (generar y registrar una Nota de Crédito en el sistema)
        return "El pedido fue cancelado.";
    }
}
