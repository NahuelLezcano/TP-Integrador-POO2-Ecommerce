package Tienda;

import Pagos.*;
import Pedido.*;
import catalogo.Item;
import java.util.Map;

public class CarritoDeCompra {

    private MetodoDePago metodoDePago;
    private Pedido pedido;
    private String usuario;

    public CarritoDeCompra(Tienda tienda, MetodoDePago metodoDePago, String usuario) {
        this.metodoDePago = metodoDePago;
        this.usuario = usuario;
        pedido = new Pedido(tienda);
    }

    public String getUsuario() {
        return usuario;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void agregarItem(Item item, Integer cantidad) {
        pedido.agregarItem(item, cantidad);
    }

    public void removerItem(Item item, Integer cantidad) {
        pedido.removerItem(item, cantidad);
    }

    public Map<Item, Integer> getItems() {
        return pedido.getItems();
    }

    public void cancelarCompra() {
        pedido.cancelar();
    }

    public void pagar() {
        this.validarPedido(pedido);
        ModuloDePago moduloDePago = new ModuloDePago();
        moduloDePago.realizarCobro(metodoDePago, usuario);
        this.confirmarPedido(moduloDePago);
    }

    private void confirmarPedido(ModuloDePago moduloDePago) {
        this.validarPago(moduloDePago);
        pedido.confirmar();
    }

    private void validarPago(ModuloDePago moduloDePago) {
        if (!moduloDePago.elPagoEstaCompletado()) {
            throw new OperacionInvalidaException("No se puede confirmar el pedido, el pago no se a realizado con éxito.");
        }
    }

    private void validarPedido(Pedido unPedido) {
        if (unPedido.getItems().isEmpty()) {
            throw new OperacionInvalidaException("No se puede procesar el pago, el pedido está vacío.");
        }
    }

}
