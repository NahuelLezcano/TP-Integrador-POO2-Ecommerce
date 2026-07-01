package catalogoTest;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Tienda.Deposito;
import catalogo.*;

class CatalogoTest {

	Catalogo catalogo;
	Deposito deposito;
	Producto celular;
	Producto auriculares;
	Producto memoria;
	Producto cargador;
	Paquete celularNuevo;
	Paquete celularExpandido;

	@BeforeEach
	void setUp() {

		celular = new Producto("AR528", "Samsung Galaxy A20", "Samsung", "Tecnología", 169, 127000, "Celular duradero");

		auriculares = new Producto("BR111", "Galaxy Buds 4 Pro", "Samsung", "Tecnología", 10, 620000, "Sonido cálido");

		memoria = new Producto("NY871", "Msd Kingston Canvas Select Plus", "Kingston", "Memoria", 1, 33500,
				"Velocidad de lectura de hasta 100 MB/s");

		cargador = new Producto("MM169", "Cargador", "Samsung", "Tecnología", 25, 35000, "Carga Rápida",
				List.of("Carga=15W", "Color=Negro"));

		celularNuevo = new Paquete("Pack celular nuevo", "Incluye un celular y auriculares", 10,
				new ArrayList<>(List.of(celular, auriculares)));

		celularExpandido = new Paquete("Pack celular nuevo con más memoria",
				"Incluye un celular con memoria msd, auriculares y cargador", 20,
				new ArrayList<>(List.of(celularNuevo, memoria)));

		deposito = new Deposito(new HashMap<>());
		deposito.agregarItemAlStock(celular, 1);
		deposito.agregarItemAlStock(auriculares, 1);
		deposito.agregarItemAlStock(memoria, 1);
		deposito.agregarItemAlStock(celularNuevo, 1);
		deposito.agregarItemAlStock(celularExpandido, 1);

		catalogo = new Catalogo(new ArrayList<>(List.of(celularExpandido, celularNuevo)), deposito);
	}

	@Test
	void catalogoTieneItems() {

		assertEquals(2, catalogo.cantidadDeItems());
		assertTrue(catalogo.contiene(celularExpandido));
		assertTrue(catalogo.contiene(celularNuevo));

	}

	@Test
	void catalogoNoTieneItems() {

		catalogo.removerItem(celularExpandido);
		catalogo.removerItem(celularNuevo);

		assertEquals(0, catalogo.cantidadDeItems());
		assertFalse(catalogo.contiene(celularExpandido));
		assertFalse(catalogo.contiene(celularNuevo));

	}

	@Test
	void catalogoTieneCargador() {

		assertEquals(2, catalogo.cantidadDeItems());

		deposito.agregarItemAlStock(cargador, 1);
		catalogo.agregarItemSiHayStock(cargador);

		assertEquals(3, catalogo.cantidadDeItems());
		assertTrue(catalogo.contiene(cargador));

	}

	@Test
	void agregarItemSinStock() {

		assertThrows(IllegalArgumentException.class, () -> catalogo.agregarItemSiHayStock(cargador));

	}

	@Test
	void skuNombreYMarcaDeProducto() {
		assertEquals("AR528", celular.getSKU());
		assertEquals("BR111", auriculares.getSKU());

		assertEquals("Samsung Galaxy A20", celular.getNombre());
		assertEquals("Galaxy Buds 4 Pro", auriculares.getNombre());

		assertEquals("Samsung", celular.getMarca());
		assertEquals("Samsung", auriculares.getMarca());
	}

	@Test
	void pesoDescripcionYCategoriaDeProducto() {
		assertEquals(169, celular.getPeso());
		assertEquals(10, auriculares.getPeso());

		assertEquals("Celular duradero", celular.getDescripcion());
		assertEquals("Sonido cálido", auriculares.getDescripcion());

		assertEquals("Tecnología", celular.getCategoria());
		assertEquals("Tecnología", auriculares.getCategoria());
	}

	@Test
	void precioDeProducto() {
		assertEquals(127000, celular.getPrecioBase());
		assertEquals(620000, auriculares.getPrecioBase());

		assertEquals(127000, celular.getPrecioFinal());
		assertEquals(620000, auriculares.getPrecioFinal());

	}

	@Test
	void atributosExtrasDeProducto() {
		assertEquals(List.of(), celular.getAtributosExtras());

		celular.agregarAtributoExtra("Color=Naranja");

		assertEquals(List.of("Color=Naranja"), celular.getAtributosExtras());
	}

	@Test
	void validarProducto() {
		assertTrue(cargador.validarObligatorios());
		assertTrue(cargador.validarExtras());
		assertTrue(cargador.validar());
		assertTrue(cargador.getAtributosExtras().size() == 2);

		assertTrue(celular.validarExtras());
		assertTrue(celular.getAtributosExtras().size() == 0);
		assertTrue(celular.validarObligatorios());
		assertTrue(celular.validar());

	}

	@Test
	void nombreYDescripcionDePaquete() {

		assertEquals("Pack celular nuevo", celularNuevo.getNombre());
		assertEquals("Incluye un celular y auriculares", celularNuevo.getDescripcion());
	}

	@Test
	void descuentoDePaquete() {

		assertEquals(10, celularNuevo.getDescuento());
		assertEquals(20, celularExpandido.getDescuento());
	}

	@Test
	void itemsDePaquetes() {

		assertEquals(List.of(celular, auriculares), celularNuevo.getItems());
		assertEquals(List.of(celularNuevo, memoria), celularExpandido.getItems());

		celularExpandido.agregarItems(cargador);
		assertEquals(List.of(celularNuevo, memoria, cargador), celularExpandido.getItems());

		celularNuevo.removerItem(auriculares);
		assertEquals(List.of(celular), celularNuevo.getItems());

		celularNuevo.removerItem(celular);
		assertEquals(List.of(), celularNuevo.getItems());
		assertFalse(celularNuevo.tieneItems());

	}

	@Test
	void precioDePaquete() {

		assertTrue(celularNuevo.itemsSonValidos());
		assertTrue(celularNuevo.validar());
		assertEquals(747000, celularNuevo.getPrecioBase());
		assertEquals(672300, celularNuevo.getPrecioFinal());

		assertTrue(celularExpandido.itemsSonValidos());
		assertTrue(celularExpandido.validar());
		assertEquals(705800, celularExpandido.getPrecioBase());
		assertEquals(564640, celularExpandido.getPrecioFinal());
	}

	@Test
	void validarObligatoriosInvlaido() {

		Producto noSKU = new Producto("", "Nombre", "Marca", "Cat", 10, 100, "Desc");
		assertFalse(noSKU.validarObligatorios());
		assertFalse(noSKU.validar());

		Producto noNombre = new Producto("SKU2", "", "Marca", "Categoria", 10, 50, "Descripcion");
		assertFalse(noNombre.validarObligatorios());

		Producto marcaBlanca = new Producto("SKU2", "Nombre", "", "Categoria", 10, 50, "Descripcion");
		assertFalse(marcaBlanca.validarObligatorios());

		Producto sinCategoria = new Producto("SKU2", "Nombre", "Marca", "", 10, 50, "Descripcion");
		assertFalse(sinCategoria.validarObligatorios());

		Producto rompeFisicos = new Producto("SKU3", "Nombre", "Marca", "Categoria", -10, 50, "Descripcion");
		assertFalse(rompeFisicos.validarObligatorios());

		Producto compra = new Producto("SKU4", "Nombre", "Marca", "Categoria", 10, -50, "Descripcion");
		assertFalse(compra.validarObligatorios());

		Producto sinDescripcion = new Producto("SKU4", "Nombre", "Marca", "Categoria", 10, 50, "");
		assertFalse(sinDescripcion.validarObligatorios());
	}

}
