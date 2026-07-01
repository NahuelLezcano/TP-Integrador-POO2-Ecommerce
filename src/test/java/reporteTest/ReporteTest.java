package reporteTest;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Tienda.*;
import catalogo.*;
import reporte.*;

class ReporteTest {

	Producto celular;
	Producto auriculares;
	Paquete celularNuevo;
	Catalogo catalogo;
	Deposito deposito;
	Tienda tienda;

	@BeforeEach
	void setUp() throws Exception {
		celular = new Producto("AR528", "Samsung Galaxy A20", "Samsung", "Tecnología", 169, 127000, "Celular duradero");

		auriculares = new Producto("BR111", "Galaxy Buds 4 Pro", "Samsung", "Tecnología", 10, 620000, "Sonido cálido");

		celularNuevo = new Paquete("Pack celular nuevo", "Incluye un celular y auriculares", 10,
				new ArrayList<>(List.of(celular, auriculares)));

		deposito = new Deposito(new HashMap<>());
		deposito.agregarItemAlStock(celular, 5);
		deposito.agregarItemAlStock(auriculares, 1);
		deposito.agregarItemAlStock(celularNuevo, 1);

		catalogo = new Catalogo(new ArrayList<>(List.of(celularNuevo)), deposito);

		tienda = new Tienda(deposito);
		tienda.setCatalogo(catalogo);
		tienda.registrarVenta(celular, 1, 100, LocalDate.now());
		tienda.registrarVenta(celular, 1, 200, LocalDate.now());
		tienda.registrarVenta(celular, 1, 300, LocalDate.now());
		tienda.registrarVenta(auriculares, 1, 400, LocalDate.now());
	}
	
	@Test
	void ordenYPromedio() {
		
		assertEquals(3, tienda.cantidadVendida(celular));
		assertEquals(1, tienda.cantidadVendida(auriculares));
		
		List<Item> masVendidos = tienda.productosMasVendidos();
		assertFalse(masVendidos.isEmpty());
		assertEquals(celular, masVendidos.get(0));
		assertEquals(auriculares, masVendidos.get(1));
		
		assertEquals(200.0, tienda.precioPromedioCobrado(celular));

	}
	
	@Test
	void reporteTxt() {
		ReporteTxt reporteTxt = new ReporteTxt(tienda);
		reporteTxt.generarReporte(tienda.productosMasVendidos());
		String textoTXT = reporteTxt.devolverReporte();

		assertTrue(textoTXT.startsWith("Reporte:"));
		assertTrue(textoTXT.contains("Item: Samsung Galaxy A20"));
		assertTrue(textoTXT.contains("Vendidos: 3"));
		assertTrue(textoTXT.contains("Precio promedio: 200.0"));
	}

	@Test
	void reporteCSV() {
		ReporteCSV reporteCSV = new ReporteCSV(tienda);
		reporteCSV.generarReporte(tienda.productosMasVendidos());
		String textoCSV = reporteCSV.devolverReporte();

		assertTrue(textoCSV.startsWith("Nombre,Vendidos,PrecioPromedio"));
		assertTrue(textoCSV.contains("Samsung Galaxy A20,3,200.0"));
		assertTrue(textoCSV.contains("Galaxy Buds 4 Pro,1,400.0"));
	}

	@Test
	void reporteHTML() {
		ReporteHTML reporteHTML = new ReporteHTML(tienda);
		reporteHTML.generarReporte(tienda.productosMasVendidos());
		String textoHTML = reporteHTML.devolverReporte();

		assertTrue(textoHTML.contains("<!DOCTYPE html>"));
		assertTrue(textoHTML.contains("<table>"));
		assertTrue(textoHTML.contains("<th>Nombre</th><th>Vendidos</th><th>Precio Promedio</th>"));
		assertTrue(textoHTML.contains("<td>Samsung Galaxy A20</td>"));
		assertTrue(textoHTML.contains("<td>3</td>"));
		assertTrue(textoHTML.contains("<td>200.0</td>"));
	}

}
