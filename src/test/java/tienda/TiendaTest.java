package tienda;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Pagos.MetodoDePago;
import Pedido.OperacionInvalidaException;
import Pedido.Pedido;
import catalogo.*;
import cliente.Cliente;
import envio.Direccion;
import envio.MetodoDeEnvio;
import envioTest.*;
import Tienda.*;

class TiendaTest {

	Producto celular;
	Producto auriculares;
	Paquete celularNuevo;
	Catalogo catalogo;
	Direccion direccion;
	Deposito deposito;
	Tienda tienda;
	LocalDate fecha;
	Cliente cliente;
    MetodoDePago metodoDePago;
    CarritoDeCompra carrito;
    Pedido pedido;
	MetodoDeEnvio metodoEnvio;

	@BeforeEach
	void setUp() throws Exception {
		
		celular = mock(Producto.class);
		auriculares = mock(Producto.class);
		celularNuevo = mock(Paquete.class);
		catalogo = mock(Catalogo.class);
		direccion = mock(Direccion.class);
		cliente = mock(Cliente.class);
		metodoDePago = mock(MetodoDePago.class);
		pedido = mock(Pedido.class);
		metodoEnvio = mock(MetodoDeEnvio.class);
		
		when(celular.getPrecioFinal()).thenReturn(127000);
		when(celular.validar()).thenReturn(true);
		
		when(auriculares.getPrecioFinal()).thenReturn(620000);
		when(auriculares.validar()).thenReturn(true);
		
		when(celularNuevo.getPrecioFinal()).thenReturn(672300);
		when(celularNuevo.getItems()).thenReturn(new ArrayList<>(List.of(celular, auriculares)));
		when(celularNuevo.validar()).thenReturn(true);
		
		when(cliente.getNombre()).thenReturn("Sebastian");
		
		when(pedido.getCliente()).thenReturn(cliente);
		
		deposito = new Deposito(new HashMap<>(), direccion);
		deposito.agregarItemAlStock(celularNuevo, 1);
		deposito.agregarItemAlStock(celular, 5);
		deposito.agregarItemAlStock(auriculares, 3);
		
		tienda = new Tienda(deposito, catalogo);
		
		carrito = new CarritoDeCompra(tienda, metodoDePago, cliente, metodoEnvio);

		fecha = LocalDate.of(2025, 10, 5);

	}

	@Test
	void testDepositoYTiendaGettersYSetters() {

		Deposito depositoNuevo = new Deposito(new HashMap<>(), direccion);
		depositoNuevo.agregarItemAlStock(celularNuevo, 1);
		Catalogo catalogoNuevo = new Catalogo(new ArrayList<>(List.of(auriculares)), deposito);

		tienda.setCatalogo(catalogoNuevo);
		tienda.setDeposito(depositoNuevo);

		tienda.getCatalogo();
		tienda.getDeposito();

	}

	@Test
    void testRegistrarVentasYStock() {

        tienda.registrarVenta(celular, 3, fecha);
        tienda.registrarVenta(auriculares, 2, fecha);
        tienda.registrarVenta(celularNuevo, 1, fecha);

        assertEquals(2, tienda.cantidadEnStock(celular));
        assertEquals(1, tienda.cantidadEnStock(auriculares));
        assertEquals(0, tienda.cantidadEnStock(celularNuevo));

        assertEquals(3, tienda.cantidadVendida(celular));
        assertEquals(2, tienda.cantidadVendida(auriculares));
        assertEquals(1, tienda.cantidadVendida(celularNuevo));
    }

	@Test
    void testPrecioPromedioYProductosMasVendidos() {
		assertEquals(0.0, tienda.precioPromedioCobrado(celular));

        tienda.registrarVenta(celular, 1, fecha);
        tienda.registrarVenta(celular, 1, fecha);
        tienda.registrarVenta(auriculares, 1, fecha);

        assertEquals(127000.0, tienda.precioPromedioCobrado(celular));
        assertEquals(620000.0, tienda.precioPromedioCobrado(auriculares));

        List<Item> masVendidos = tienda.productosMasVendidos();
        assertFalse(masVendidos.isEmpty());
        assertEquals(celular, masVendidos.get(0));
        assertEquals(auriculares, masVendidos.get(1));
    }

	@Test
	void testVentaGetters() {
		Venta venta = new Venta(celular, 2, 254000, fecha);
		assertEquals(celular, venta.getItem());
		assertEquals(2, venta.getCantidad());
		assertEquals(254000, venta.getPrecio());
		assertEquals(fecha, venta.getFecha());
	}
	
	@Test
	void testDepositoNoPuedeSacarElItem() {
		assertThrows(IllegalArgumentException.class, () -> deposito.removerCantidadDelStock(auriculares, 5));
	}
	
	@Test
	void testCarritoGetters() {
		assertEquals("Sebastian", carrito.getNombreUsuario());

		assertEquals(pedido.getCliente(), carrito.getPedido().getCliente());
	}
	
	@Test
	void testCarritoAgregarYRemoverItems() {
		carrito.agregarItem(celular, 3);

		assertTrue(carrito.getItems().containsKey(celular));
		assertEquals(3, carrito.getItems().get(celular).intValue());

		carrito.removerItem(celular, 1);
		assertEquals(2, carrito.getItems().get(celular).intValue());

		carrito.removerItem(celular, 2);
		assertTrue(carrito.getItems().isEmpty());
	}
	
	@Test
    void testPagarConPedidoVacioLanzaOperacionInvalidaException() {
        assertThrows(OperacionInvalidaException.class, () -> carrito.pagar());
    }
	
}
