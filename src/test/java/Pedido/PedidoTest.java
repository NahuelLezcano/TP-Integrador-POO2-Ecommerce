package Pedido;

import Tienda.*;
import catalogo.*;
import cliente.Cliente;
import notificacion.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

public class PedidoTest {

    // primera parte, Estados de un pedido
    Pedido unPedido;
    Tienda unaTienda;
    Item unItem;
    Cliente cliente;

    // segunda parte, tests que modifican el Stock de un Deposito
    Item cocina;
    Item celu;
    Item tv;
    Catalogo catalogoTest;
    Deposito depositoTest;
    Tienda tiendaTest;
    Pedido pedidoTest;
    
    // estados
    Estado estadoPago;
	Estado estadoBorrador;

    @BeforeEach
    public void setUp() {
        // primera parte, Estados de un pedido
        unaTienda = mock(Tienda.class);
        unItem = mock(Item.class);
        cliente = mock(Cliente.class);
        unPedido = new Pedido(unaTienda, cliente);

        // segunda parte, tests que modifican el Stock de un Deposito
        celu = new Producto("b123", "SmartPhone 5G", "SmartThink", "Celular", 1, 200, "Smartphone");
        tv = new Producto("c123", "TV 43", "TV inc", "Televisión", 10, 500, "TV smart");
        catalogoTest = mock(Catalogo.class);
        depositoTest = new Deposito();
        depositoTest.agregarItemAlStock(celu, 20);
        depositoTest.agregarItemAlStock(tv, 9);
        tiendaTest = new Tienda(depositoTest, catalogoTest);
        pedidoTest = new Pedido(tiendaTest, cliente);
        
        //Estados
        estadoPago = mock(Estado.class);
    	estadoBorrador = mock(Estado.class);
        when(estadoPago.getNombreDelEstado()).thenReturn("Pago");
		when(estadoBorrador.getNombreDelEstado()).thenReturn("Borrador");
    }

    // primera parte, Estados de un pedido
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
    
    // segunda parte, tests que modifican el Stock de un Deposito
    @Test
    public void elClienteAlConfirmarUnPedido_EsteEsPagoYSeDecrementaElStock() {
        pedidoTest.agregarItem(celu, 2);
        pedidoTest.agregarItem(tv, 1);
        pedidoTest.confirmar(); // Esta pago

        assertEquals(18, tiendaTest.cantidadEnStock(celu));
        assertEquals(8, tiendaTest.cantidadEnStock(tv));
    }

    @Test
    public void siUnPedidoEstaPagoYSeCancela_SeReponeElStock() {
        pedidoTest.agregarItem(celu, 2);
        pedidoTest.agregarItem(tv, 1);
        pedidoTest.confirmar(); // Esta pago

        assertEquals(18, tiendaTest.cantidadEnStock(celu));
        assertEquals(8, tiendaTest.cantidadEnStock(tv));

        pedidoTest.cancelar();

        assertEquals(20, tiendaTest.cantidadEnStock(celu));
        assertEquals(9, tiendaTest.cantidadEnStock(tv));
    }

    @Test
    public void siUnPedidoEstaEnPreparacionYSeCancela_SeReponeElStock() {
        pedidoTest.agregarItem(celu, 2);
        pedidoTest.agregarItem(tv, 1);
        pedidoTest.confirmar(); // Esta pago

        assertEquals(18, tiendaTest.cantidadEnStock(celu));
        assertEquals(8, tiendaTest.cantidadEnStock(tv));

        pedidoTest.confirmar(); // Esta en preparación
        pedidoTest.cancelar();

        assertEquals(20, tiendaTest.cantidadEnStock(celu));
        assertEquals(9, tiendaTest.cantidadEnStock(tv));
        assertEquals("Cancelado", pedidoTest.estadoActual().getNombreDelEstado());
    }

	@Test
	void testGetCliente() {
		assertEquals(cliente, unPedido.getCliente());
	}

	@Test
	void testNombresDeItems() {
		assertEquals("", pedidoTest.nombresDeItems());
		
		pedidoTest.agregarItem(celu, 1);

		assertEquals("SmartPhone 5G", pedidoTest.nombresDeItems());
	}

	@Test
	void testMontoTotal() {
		assertEquals(0, pedidoTest.montoTotal());
		
		pedidoTest.agregarItem(celu, 1);
		pedidoTest.agregarItem(tv, 1);

		assertEquals(700, pedidoTest.montoTotal());
	}

	@Test
	void testHayItems() {
		assertFalse(unPedido.hayItems());

		unPedido.agregarItem(celu, 1);

		assertTrue(unPedido.hayItems());
	}

	@Test
	void testNotificarObservadores() {
		Observador observador = mock(Observador.class);

		unPedido.cambiarEstado(estadoBorrador);

		unPedido.agregarObservador(observador);
		assertEquals(List.of(observador), unPedido.getObservadores());
		
		unPedido.cambiarEstado(estadoPago);

		verify(observador).actualizar(eq(unPedido), eq(estadoBorrador), eq(estadoPago));

	}

	@Test
	void testGetTienda() {
		assertEquals(unaTienda, unPedido.getTienda());
	}

	@Test
	void testQuitarItemCantidadInvalida() {
		unPedido.agregarItemAlPedido(celu, 5);

		unPedido.quitarItemAlPedido(celu, 0);
		assertEquals(1, unPedido.cantidadItemsAgregados());
		assertEquals(5, unPedido.getItems().get(celu));
	}

	@Test
	void testQuitarItemCasoNormal() {
		unPedido.agregarItemAlPedido(celu, 5);

		unPedido.quitarItemAlPedido(celu, 2);
		assertEquals(3, unPedido.getItems().get(celu));
	}

    @Test
    void testPesoTotal() {
        unPedido.agregarItem(celu, 10);
        unPedido.agregarItem(tv, 8);

        assertEquals(90, unPedido.pesoTotalPedido());
    }

    
    
}
