package envioTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Pedido.Pedido;
import envio.*;
import Tienda.*;


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
		
		when(pedido.montoTotal()).thenReturn(15000);
		
		origen = new Direccion(2, 4);
		destino = new Direccion(12, 16);
		estandar = new EnvioEstandar(destino, correoArgentino);
		express = new EnvioExpress(8500.0);
		sucursal = new RetiroSucursal(tienda);
		envio = new Envio(express, pedido);
		
		
	}

	@Test
	void testDireccion() {
		assertEquals(15.620499351813308, origen.distanciaEnKmA(destino));
	}
	
	@Test
	void testEnvio() {
		assertEquals(10000.0, envio.costoEnvio());
		assertEquals(1, envio.estimacionDeDias());
	}
	

}
