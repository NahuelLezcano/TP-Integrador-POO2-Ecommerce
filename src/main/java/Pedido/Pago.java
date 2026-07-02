package Pedido;

import Tienda.Deposito;
import catalogo.Item;

public class Pago extends Estado {

    public Pago(Pedido pedido) {
        super(pedido);
    }

    @Override
    public void agregarItem(Item item, Integer cantidad) {
        throw new OperacionInvalidaException("El pedido está pago, no se pueden agregar más items");
    }

    @Override
    public void removerItem(Item item, Integer cantidad) {
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
        this.reponerStock(pedido.getTienda().getDeposito());
        return "El pedido fue cancelado.";
    }

    private void reponerStock(Deposito deposito) {
        pedido.getItems().forEach((item, cantidad) -> deposito.agregarItemAlStock(item, cantidad));
    }
}
