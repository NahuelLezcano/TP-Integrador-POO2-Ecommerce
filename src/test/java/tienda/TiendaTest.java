package tienda;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import catalogo.*;
import Tienda.*;

class TiendaTest {

	Producto celular;
	Producto auriculares;
	Paquete celularNuevo;
	Catalogo catalogo;
	Deposito deposito;
	Tienda tienda;
	LocalDate fecha;

	/*
	 * No me funciona el mock ni importandolo, por eso inicializo todo, la idea del
	 * mock es no inicializar cosas que no tienen que ver con las clases a testear
	 * en este caso, tienda, deposito y venta.
	 */
	@BeforeEach
	void setUp() throws Exception {
		celular = new Producto("AR528", "Samsung Galaxy A20", "Samsung", "Tecnología", 169, 127000, "Celular duradero");

		auriculares = new Producto("BR111", "Galaxy Buds 4 Pro", "Samsung", "Tecnología", 10, 620000, "Sonido cálido");

		celularNuevo = new Paquete("Pack celular nuevo", "Incluye un celular y auriculares", 10,
				new ArrayList<>(List.of(celular, auriculares)));

		deposito = new Deposito(new HashMap<>());
		deposito.agregarItemAlStock(celularNuevo, 1);
		deposito.agregarItemAlStock(celular, 5);
		deposito.agregarItemAlStock(auriculares, 3);

		catalogo = new Catalogo(new ArrayList<>(List.of(celularNuevo)), deposito);

		tienda = new Tienda(deposito, catalogo);

		fecha = LocalDate.of(2025, 10, 5);

	}

	@Test
	void gettersYSetters() {

		Deposito depositoNuevo = new Deposito(new HashMap<>());
		depositoNuevo.agregarItemAlStock(celularNuevo, 2);
		Catalogo catalogoNuevo = new Catalogo(new ArrayList<>(List.of(auriculares)), deposito);

		tienda.setCatalogo(catalogoNuevo);
		tienda.setDeposito(depositoNuevo);

		tienda.getCatalogo();
		tienda.getDeposito();

	}

	@Test
	void registrarVentasYStock() {

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
	void precioPromedioYProductosMasVendidos() {

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
	void ventaGetters() {
		Venta venta = new Venta(celular, 2, 254000, fecha);
		assertEquals(celular, venta.getItem());
		assertEquals(2, venta.getCantidad());
		assertEquals(254000, venta.getPrecio());
		assertEquals(fecha, venta.getFecha());
	}
	
	@Test
	void depositoNoPuedeSacarElItem() {
		assertThrows(IllegalArgumentException.class, () -> deposito.removerCantidadDelStock(auriculares, 10));
	}
}
