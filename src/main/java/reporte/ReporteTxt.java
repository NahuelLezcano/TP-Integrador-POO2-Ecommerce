package reporte;

import java.util.*;

import Tienda.Tienda;
import catalogo.*;

public class ReporteTxt extends Reporte {
  
  public ReporteTxt(Tienda tienda) {
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
  	return "Item: " + item.getNombre() + 
      		" | Vendidos: " + tienda.cantidadVendida(item) + 
      		" | Precio promedio: " + tienda.precioPromedioCobrado(item) + 
      		"\n";
  }

	@Override
	public String cabecera() {
		return "Reporte:\n";
	}
  
}
