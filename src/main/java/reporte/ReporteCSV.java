package reporte;

import java.util.*;
import catalogo.*;

public class ReporteCSV extends Reporte {
	
  @Override
  public void generarReporte(List<Item> items) {
  	escribir(cabecera());
      for (Item item : items) {
      	escribir(escribirFila(item));
      }
 }

  @Override
  public String escribirFila(Item item) {
  	return item.getNombre() + "," +
  			item.getCantidadVendida() + "," +
  			item.getPrecioPromedioCobrado() + "\n";
  }
  
  @Override
  public String cabecera() {
  	return "Nombre,Vendidos,PrecioPromedio\n";
  }
  
}
