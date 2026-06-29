package catalogoTest;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

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
		catalogo = new Catalogo(List.of(celularExpandido, celularNuevo));
		
		celular = new Producto("AR528", "Samsung Galaxy A20", "Samsung",
				"Tecnología", 169, 127000, "Celular duradero");
		
		auriculares = new Producto("BR111", "Galaxy Buds 4 Pro", "Samsung",
				"Accesorio", 10, 620000, "Sonido cálido");
		
		memoria = new Producto("NY871", "Msd Kingston Canvas Select Plus",
				"Kingston", "Memoria", 1, 33500, "Velocidad de lectura de hasta 100 MB/s");
		
		cargador = new Producto("MM169", "Cargador", "Samsung", 
				"Tecnología", 25, 35000, "Carga Rápida", List.of("Carga=15W", "Color=Negro"));
		
		celularNuevo = new Paquete("Pack celular nuevo", "Incluye un celular y auriculares",
						10, List.of(celular, auriculares));
		
		celularExpandido = new Paquete("Pack celular nuevo con más memoria",
							"Incluye un celular con memoria msd, auriculares y cargador", 20,
							List.of(celularNuevo, memoria));
	}

	@Test
	void test() {
		fail("Not yet implemented");
	}

}
