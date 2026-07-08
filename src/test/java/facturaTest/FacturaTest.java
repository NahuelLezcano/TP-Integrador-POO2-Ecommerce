package facturaTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import catalogo.Item;
import factura.Factura;

class FacturaTest {

	Factura factura;
	Item item1;
	Item item2;

	@BeforeEach
	void setUp() {
		factura = new Factura();
		item1 = mock(Item.class);
        item2 = mock(Item.class);
	}

	@Test
	void testAgregarItem() {
		when(item1.getPrecioFinal()).thenReturn(100);
		
		factura.agregarItem(item1);
		
		assertEquals(100.0, factura.montoTotal(), 0.0001);
        verify(item1, times(1)).getPrecioFinal();
	}

	@Test
	void testMontoTotal() {
		when(item1.getPrecioFinal()).thenReturn(100);
		when(item2.getPrecioFinal()).thenReturn(250);

		factura.agregarItem(item1);
		factura.agregarItem(item2);

		assertEquals(350.0, factura.montoTotal(), 0.0001);

		verify(item1, times(1)).getPrecioFinal();
		verify(item2, times(1)).getPrecioFinal();
	}

}
