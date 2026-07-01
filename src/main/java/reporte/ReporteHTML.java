package reporte;

import java.util.*;

import Tienda.Tienda;
import catalogo.*;

public class ReporteHTML extends Reporte {
	
	public ReporteHTML(Tienda tienda) {
		super(tienda);
	}

	@Override
	public void generarReporte(List<Item> items) {
  	escribir(cabecera());
      for (Item item : items) {
			escribir(escribirFila(item));
		}
		escribir(cierre());
	}
	
	@Override
	public String escribirFila(Item item) {
		return "<tr><td>" + item.getNombre() + 
				"</td><td>" + tienda.cantidadVendida(item) + 
				"</td><td>" + tienda.precioPromedioCobrado(item) + 
				"</td></tr>\n";
	}
	
	@Override
	public String cabecera() {
		return "<!DOCTYPE html>\n" +
				"<html>\n" +
				"<head>\n" +
				"<meta charset=\"UTF-8\">\n" +
				"<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
				"<title>Reporte de Aduana</title>\n" +
				"</head>\n" +
				"<body>\n" +
				"<table>\n" +
				"<tr><th>Nombre</th><th>Vendidos</th><th>Precio Promedio</th></tr>\n";
	}
	
	public String cierre() {
		return "</table>\n</body>\n</html>";
	}
	
}
