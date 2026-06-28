package reporte;

//necesito importar Item para que funcione
import java.util.*;

public class ReporteHTML extends Reporte {
	
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
				"</td><td>" + item.getCantidadVendida() + 
				"</td><td>" + item.getPrecioPromedioCobrado() + 
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
