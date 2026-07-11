package notificacionTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Pedido.Estado;
import Pedido.Pedido;
import catalogo.Item;
import cliente.Cliente;
import notificacion.*;

class norificaionTest {

	Cliente cliente;
	Item celular;
	Pedido pedido;
	Estado estadoPago;
	Estado estadoEntregado;
	Estado estadoEnviado;
	Estado estadoCancelado;
	Estado estadoBorrador;
	NotificadorDeEmail notificadorDeEmail;
	Fidelizacion fidelizacion;
	GeneradorDeFactura generadorDeFactura;

	@BeforeEach
	void setUp() {
		cliente = mock(Cliente.class);
		celular = mock(Item.class);
		pedido = mock(Pedido.class);
		estadoPago = mock(Estado.class);
		estadoEntregado = mock(Estado.class);
		estadoEnviado = mock(Estado.class);
		estadoCancelado = mock(Estado.class);
		estadoBorrador = mock(Estado.class);

		when(estadoPago.getNombreDelEstado()).thenReturn("Pago");
		when(estadoEntregado.getNombreDelEstado()).thenReturn("Entregado");
		when(estadoEnviado.getNombreDelEstado()).thenReturn("Enviado");
		when(estadoCancelado.getNombreDelEstado()).thenReturn("Cancelado");
		when(estadoBorrador.getNombreDelEstado()).thenReturn("Borrador");

		when(pedido.nombresDeItems()).thenReturn("celular");
		when(pedido.montoTotal()).thenReturn(127000);
		when(pedido.getCliente()).thenReturn(cliente);
		when(pedido.getCliente().getCorreo()).thenReturn("sebastian@gmail.com");

		generadorDeFactura = new GeneradorDeFactura(pedido);
		notificadorDeEmail = new NotificadorDeEmail(pedido);
		fidelizacion = new Fidelizacion(pedido);

	}

	@Test
	void testGettersYDescuento() {
		assertEquals(pedido, generadorDeFactura.getPedido());
		assertEquals(pedido, notificadorDeEmail.getPedido());
		assertEquals(pedido, fidelizacion.getPedido());
		assertEquals(5.0, fidelizacion.getDescuento());
		assertEquals(0.05, fidelizacion.calcularDescuento());
	}

	@Test
	void testCrearFactura() {
		assertEquals("Factura:\n" + "celular.\n" + "Monto Final: $127000", generadorDeFactura.crearFactura());
	}

	@Test
	void testActualizarEnGeneradorDeFactura() {
		GeneradorDeFactura spyGenerador = spy(generadorDeFactura);

		spyGenerador.actualizar(pedido, estadoPago, estadoEntregado);

		verify(spyGenerador, times(1)).enviarMail(
				eq("sebastian@gmail.com"), 
				eq("Factura"),
				eq("Se adjunta la factura del pedido"), 
				eq("Factura:\n" + "celular.\n" + "Monto Final: $127000"));
	}
	
	@Test
	void testActualizarEnGeneradorDeFacturaNoHaceNada() {
		GeneradorDeFactura spyNoGeneraFactura = spy(generadorDeFactura);
		spyNoGeneraFactura.actualizar(pedido, estadoBorrador, estadoCancelado);

		verify(spyNoGeneraFactura, never()).enviarMail(anyString(), anyString(), anyString(), anyString());
	}

	@Test 
	void testActualizarEnNotificadorDeEmailPago() { 
		NotificadorDeEmail spyPago = spy(notificadorDeEmail);
		
		spyPago.actualizar(pedido, estadoBorrador, estadoPago);
		
		verify(spyPago, times(1)).enviarMail(
				eq("sebastian@gmail.com"), 
				eq("Estado actualizado"), 
				eq("El estado del pedido cambió de Borrador a Pago"), 
				anyString());
	}
	
	@Test
	void testActualizarEnNotificadorDeEmailEnviado() {
		NotificadorDeEmail spyEnviado = spy(notificadorDeEmail);
		
		spyEnviado.actualizar(pedido, estadoPago, estadoEnviado);
		
		verify(spyEnviado, times(1)).enviarMail(
				eq("sebastian@gmail.com"), 
				eq("Estado actualizado"), 
				eq("El estado del pedido cambió de Pago a Enviado"), 
				anyString());
	}
	
	@Test
	void testActualizarEnNotificadorDeEmailEntregado() {
		NotificadorDeEmail spyEntregado = spy(notificadorDeEmail);
		
		spyEntregado.actualizar(pedido, estadoEnviado, estadoEntregado);
		
		verify(spyEntregado, times(1)).enviarMail(
				eq("sebastian@gmail.com"), 
				eq("Estado actualizado"), 
				eq("El estado del pedido cambió de Enviado a Entregado"), 
				anyString());
	}
	
	@Test
	void testActualizarEnNotificadorDeEmailNoNotificado(){
		NotificadorDeEmail spyNoNotifica = spy(notificadorDeEmail);
		
		spyNoNotifica.actualizar(pedido, estadoBorrador, estadoCancelado);
		verify(spyNoNotifica, never()).enviarMail(anyString(), anyString(), anyString(), anyString());
	}
	
	@Test
	void testActualizarEnFidelizacion() {
		Fidelizacion spyFidelizacion = spy(fidelizacion);
		
		spyFidelizacion.actualizar(pedido, estadoBorrador, estadoCancelado);
		
		verify(spyFidelizacion, times(1)).enviarMail(
				eq("sebastian@gmail.com"), 
				eq("Descuento"), 
				eq("Recibió un descuento del 5.0%"), 
				anyString());
	}
	
	@Test
	void testActualizarEnFidelizacionNoHaceNada() {
		Fidelizacion spyNoFidelizacion = spy(fidelizacion);
		
		spyNoFidelizacion.actualizar(pedido, estadoPago, estadoEnviado);
		verify(spyNoFidelizacion, never()).enviarMail(anyString(), anyString(), anyString(), anyString());
	}
	 
}
