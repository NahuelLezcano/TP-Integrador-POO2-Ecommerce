package facturaTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Map;

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
		item1 = mock(Item.class);
		item2 = mock(Item.class);

		when(item1.getPrecioFinal()).thenReturn(100);
		when(item1.getNombre()).thenReturn("celular");
		when(item2.getPrecioFinal()).thenReturn(250);
		when(item2.getNombre()).thenReturn("auricualres");
	}

	@Test
	void testGetItem() {
		factura = new Factura(Map.of(item1, 1));

		assertEquals(Map.of(item1, 1), factura.getItems());
	}

	@Test
	void testMontoTotal() {
		factura = new Factura(Map.of(item1, 1, item2, 2));

		assertEquals(600.0, factura.montoTotal());

		verify(item1, times(1)).getPrecioFinal();
		verify(item2, times(1)).getPrecioFinal();
	}

	@Test
	void testDesgloseDeFactura() {
		factura = new Factura(Map.of(item1, 1, item2, 2));

		String desglose = "Factura:\n" + 
							"celular x1\n" +
							"auricualres x2\n" +
							"Monto Total: $600.0";

		assertEquals(desglose, factura.desgloseDeFactura());
	}

}
