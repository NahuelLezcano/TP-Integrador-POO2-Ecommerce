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

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Pedido.Estado;
import Pedido.Pedido;
import catalogo.Item;
import cliente.Cliente;
import factura.Factura;
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
	Factura factura;
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
		factura = mock(Factura.class);
		
		when(celular.getNombre()).thenReturn("celular");
		when(celular.getPrecioFinal()).thenReturn(127000);

		when(estadoPago.getNombreDelEstado()).thenReturn("Pago");
		when(estadoEntregado.getNombreDelEstado()).thenReturn("Entregado");
		when(estadoEnviado.getNombreDelEstado()).thenReturn("Enviado");
		when(estadoCancelado.getNombreDelEstado()).thenReturn("Cancelado");
		when(estadoBorrador.getNombreDelEstado()).thenReturn("Borrador");

		when(pedido.nombresDeItems()).thenReturn("celular");
		when(pedido.montoTotal()).thenReturn(127000);
		when(pedido.getCliente()).thenReturn(cliente);
		when(pedido.getCliente().getCorreo()).thenReturn("sebastian@gmail.com");
		when(pedido.getItems()).thenReturn(Map.of(celular, 1));
		
		when(factura.getItems()).thenReturn(Map.of(celular, 1));

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
		assertEquals(factura.getItems(), generadorDeFactura.crearFactura().getItems());
	}

	@Test
	void testActualizarEnGeneradorDeFactura() {
		GeneradorDeFactura spyGenerador = spy(generadorDeFactura);

		spyGenerador.actualizar(pedido, estadoPago, estadoEntregado);

		verify(spyGenerador, times(1)).enviarMail(
				eq("sebastian@gmail.com"), 
				eq("Factura"),
				eq("Desglose de su pedido:"), 
				eq("Factura:\ncelular x1\nMonto Total: $127000.0"));
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
