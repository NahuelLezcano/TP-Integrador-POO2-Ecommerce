package reporte;

//necesito importar Item para que funcione
import catalogo.Item;
import java.util.*;

public class ReporteTxt extends Reporte {
  
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
      		" | Vendidos: " + item.getCantidadVendida() + 
      		" | Precio promedio: " + item.getPrecioPromedioCobrado() + 
      		"\n";
  }

	@Override
	public String cabecera() {
		return "Reporte:\n";
	}
  
}
