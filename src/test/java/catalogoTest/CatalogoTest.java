package catalogoTest;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import catalogo.*;

class CatalogoTest {

	Catalogo catalogo;
	Producto celular;
	Producto auriculares;
	Producto memoria;
	Producto cargador;
	Paquete celularNuevo;
	Paquete celularExpandido;

	@BeforeEach
	void setUp() {

		celular = new Producto("AR528", "Samsung Galaxy A20", "Samsung", "Tecnología", 169, 127000, "Celular duradero");

		auriculares = new Producto("BR111", "Galaxy Buds 4 Pro", "Samsung", "Accesorio", 10, 620000, "Sonido cálido");

		memoria = new Producto("NY871", "Msd Kingston Canvas Select Plus", "Kingston", "Memoria", 1, 33500,
				"Velocidad de lectura de hasta 100 MB/s");

		cargador = new Producto("MM169", "Cargador", "Samsung", "Tecnología", 25, 35000, "Carga Rápida",
				List.of("Carga=15W", "Color=Negro"));

		celularNuevo = new Paquete("Pack celular nuevo", "Incluye un celular y auriculares", 10,
				new ArrayList<>(List.of(celular, auriculares)));

		celularExpandido = new Paquete("Pack celular nuevo con más memoria",
				"Incluye un celular con memoria msd, auriculares y cargador", 20,
				new ArrayList<>(List.of(celularNuevo, memoria)));

		catalogo = new Catalogo(new ArrayList<>(List.of(celularExpandido, celularNuevo)));
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
	void catalogoTieneFunda() {
		Producto funda = new Producto("NH35", "Funda", "Genérica", "Accesorio", 5, 5000, "Funda Protectora",
				List.of("Color=Naranja"));

		assertEquals(2, catalogo.cantidadDeItems());

		catalogo.agregarItem(funda);
		assertEquals(3, catalogo.cantidadDeItems());
		assertTrue(catalogo.contiene(funda));

	}

}
