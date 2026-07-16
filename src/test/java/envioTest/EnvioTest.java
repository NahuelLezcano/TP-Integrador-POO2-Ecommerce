package envioTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Pedido.Pedido;
import envio.*;
import Tienda.*;
import catalogo.Item;


class EnvioTest {
	Direccion origen;
	Direccion destino;
	Envio envio;
	MetodoDeEnvio estandar;
	MetodoDeEnvio express;
	MetodoDeEnvio sucursal;
	Pedido pedido;
	Tienda tienda;
	CorreoArgentino correoArgentino;
	Deposito deposito;

	@BeforeEach
	void setUp() throws Exception {
		tienda = mock(Tienda.class);
		pedido = mock(Pedido.class);
		deposito = mock(Deposito.class);
		correoArgentino = mock(CorreoArgentino.class);
		
		origen = new Direccion(2, 4);
		destino = new Direccion(12, 16);
		estandar = new EnvioEstandar(destino, correoArgentino);
		express = new EnvioExpress(8500.0);
		sucursal = new RetiroSucursal(tienda);
		
		when(pedido.montoTotal()).thenReturn(15000);
		when(pedido.pesoTotalPedido()).thenReturn(10);
		when(pedido.getTienda()).thenReturn(tienda);
		when(tienda.getDeposito()).thenReturn(deposito);
		when(deposito.getDireccion()).thenReturn(origen);
		when(correoArgentino.estimarEnvio(10, origen)).thenReturn(12000.0);
		
	}

	@Test
	void testDireccion() {
		assertEquals(15.620499351813308, origen.distanciaEnKmA(destino));
	}
	
	@Test
	void testEnvioExpress() {
		envio = new Envio(express, pedido);
		
		assertEquals(10000.0, envio.costoEnvio());
		assertEquals(1, envio.estimacionDeDias());
	}
	
	@Test
	void testEnvioEstandar() {
		envio = new Envio(estandar, pedido);
		
		assertEquals(12000.0, envio.costoEnvio());
		assertEquals(6, envio.estimacionDeDias());
		
	}
	
	@Test
	void testRetiroSucursalConStock() {
		envio = new Envio(sucursal, pedido);
		
		when(deposito.validar(any(), anyInt())).thenReturn(true);
		
		assertEquals(0.0, envio.costoEnvio());
		assertEquals(0, envio.estimacionDeDias());
	}
	
	@Test
	void testRetiroSucursalSinStock() {
		Map<Item, Integer> items = new HashMap<>();
	    Item item = mock(Item.class);
	    items.put(item, 2);

	    when(pedido.getItems()).thenReturn(items);
	    when(deposito.validar(any(), anyInt())).thenReturn(false);
		
		envio = new Envio(sucursal, pedido);
		
		assertEquals(0.0, envio.costoEnvio());
		assertEquals(1, envio.estimacionDeDias());
	}
	

}
