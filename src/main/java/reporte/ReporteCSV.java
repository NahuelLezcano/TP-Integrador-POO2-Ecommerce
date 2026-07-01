package reporte;

import java.util.*;

import Tienda.Tienda;
import catalogo.*;

public class ReporteCSV extends Reporte {
	
  public ReporteCSV(Tienda tienda) {
		super(tienda);
	}

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
  			tienda.cantidadVendida(item) + "," +
  			tienda.precioPromedioCobrado(item) + "\n";
  }
  
  @Override
  public String cabecera() {
  	return "Nombre,Vendidos,PrecioPromedio\n";
  }
  
}
