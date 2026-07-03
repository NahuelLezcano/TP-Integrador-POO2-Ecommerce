package Pedido;

import Tienda.*;
import catalogo.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class PedidoTest {

    Pedido unPedido;
    Tienda unaTienda = mock(Tienda.class);
    Item unItem = mock(Item.class);

    @BeforeEach
    public void setUp() {
        unPedido = new Pedido(unaTienda);
    }

    @Test
    public void unPedidoAlIniciarEsUnBorradorYSePuedenAgregarYQuitarItems() {
        unPedido.agregarItem(unItem, 1);
        assertEquals(1, unPedido.cantidadItemsAgregados());

        unPedido.removerItem(unItem, 1);
        assertEquals(0, unPedido.cantidadItemsAgregados());
    }

    @Test
    public void unPedidoQueEstaEnBorradorAlConfirmarPasaAPago() {
        unPedido.agregarItem(unItem, 1);
        assertEquals("Borrador", unPedido.estadoActual().getNombreDelEstado());
        unPedido.confirmar();
        assertEquals("Pago", unPedido.estadoActual().getNombreDelEstado());
    }

    @Test
    public void unPedidoQueEstaEnBorradorAlCancelarPasaAlEstadoCancelado() {
        unPedido.cancelar();
        assertEquals("Cancelado", unPedido.estadoActual().getNombreDelEstado());
    }

    @Test
    public void siUnBorradorDePedidoEstaVacioNoSePuedeConfirmar() {
        assertThrows(OperacionInvalidaException.class, () -> unPedido.confirmar(), "No hay items en el pedido.");
    }

    @Test
    public void alConfirmarPedidoQueEstaPagoPasaAEstarEnPreparacion() {
        unPedido.agregarItem(unItem, 1);
        unPedido.confirmar(); // Estado: Pago
        unPedido.confirmar(); // Estado: EnPreparación
        assertEquals("EnPreparación", unPedido.estadoActual().getNombreDelEstado());
    }

    @Test
    public void alConfirmarPedidoQueEstaEnPreparacionPasaAEstarEnviado() {
        unPedido.agregarItem(unItem, 1);
        unPedido.confirmar(); // Estado: Pago
        unPedido.confirmar(); // Estado: EnPreparación
        unPedido.confirmar(); // Estado: Enviado
        assertEquals("Enviado", unPedido.estadoActual().getNombreDelEstado());
    }

    @Test
    public void alConfirmarPedidoQueEstaEnviadoPasaAEstarEntregado() {
        unPedido.agregarItem(unItem, 1);
        unPedido.confirmar(); // Estado: Pago
        unPedido.confirmar(); // Estado: EnPreparación
        unPedido.confirmar(); // Estado: Enviado
        unPedido.confirmar(); // Estado: Entregado
        assertEquals("Entregado", unPedido.estadoActual().getNombreDelEstado());
    }

    @Test
    public void siUnPedidoEstaPago_NoSePuedenAgregarNiQuitarItems() {
        unPedido.cambiarEstado(new Pago(unPedido));

        assertThrows(OperacionInvalidaException.class, () -> unPedido.agregarItem(unItem, 2), "El pedido está pago, no se pueden agregar más items");
        assertThrows(OperacionInvalidaException.class, () -> unPedido.removerItem(unItem, 2), "El pedido está pago, no se pueden remover más items");
    }

    @Test
    public void siUnPedidoEstaEnPreparacion_NoSePuedenAgregarNiQuitarItems() {
        unPedido.cambiarEstado(new EnPreparacion(unPedido));

        assertThrows(OperacionInvalidaException.class, () -> unPedido.agregarItem(unItem, 2), "El pedido está en preparación, no se pueden agregar más items");
        assertThrows(OperacionInvalidaException.class, () -> unPedido.removerItem(unItem, 2), "El pedido está en preparación, no se pueden remover más items");
    }

    @Test
    public void siUnPedidoEstaEnviado_NoSePuedenAgregarNiQuitarItems() {
        unPedido.cambiarEstado(new Enviado(unPedido));

        assertThrows(OperacionInvalidaException.class, () -> unPedido.agregarItem(unItem, 2), "El pedido está enviado, no se pueden agregar más items");
        assertThrows(OperacionInvalidaException.class, () -> unPedido.removerItem(unItem, 2), "El pedido está enviado, no se pueden remover más items");
    }

    @Test
    public void siUnPedidoEstaEntregado_NoSePuedenAgregarNiQuitarItems() {
        unPedido.cambiarEstado(new Entregado(unPedido));

        assertThrows(OperacionInvalidaException.class, () -> unPedido.agregarItem(unItem, 2), "No se pueden agregar items, el pedido ha sido entregado.");
        assertThrows(OperacionInvalidaException.class, () -> unPedido.removerItem(unItem, 2), "No se pueden remover items, el pedido ha sido entregado.");
    }

    @Test
    public void siUnPedidoEstaEntregado_NoSePuedeConfirmarNiCancelar() {
        unPedido.cambiarEstado(new Entregado(unPedido));

        assertThrows(OperacionInvalidaException.class, () -> unPedido.confirmar(), "El cliente recibió el pedido. Estado terminal.");
        assertThrows(OperacionInvalidaException.class, () -> unPedido.cancelar(), "El cliente recibió el pedido. Estado terminal.");
    }

    @Test
    public void siUnPedidoEstaCancelado_NoSePuedenAgregarNiQuitarItems() {
        unPedido.cambiarEstado(new Cancelado(unPedido));

        assertThrows(OperacionInvalidaException.class, () -> unPedido.agregarItem(unItem, 2), "No se pueden agregar items, el pedido ha sido cancelado.");
        assertThrows(OperacionInvalidaException.class, () -> unPedido.removerItem(unItem, 2), "No se pueden remover items, el pedido ha sido cancelado.");
    }

    @Test
    public void siUnPedidoEstaCancelado_NoSePuedeConfirmarNiCancelar() {
        unPedido.cambiarEstado(new Cancelado(unPedido));

        assertThrows(OperacionInvalidaException.class, () -> unPedido.confirmar(), "El pedido fue cancelado. Estado terminal.");
        assertThrows(OperacionInvalidaException.class, () -> unPedido.cancelar(), "El pedido fue cancelado. Estado terminal.");
    }
}
