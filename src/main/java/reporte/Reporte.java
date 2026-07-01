package reporte;

import java.util.*;
import catalogo.*;

public abstract class Reporte {
	
	/*faltan cosas para poder hacer el reporte, como el promedio del producto mas vendido o
	 * esta es una base de donde partir.
	 */
	
	protected StringBuilder reporte = new StringBuilder();

	public void escribir(String info) {
		reporte.append(info);
	}

	public String devolverReporte() {
		return this.reporte.toString();
	}

	public abstract void generarReporte(List<Item> itemsVendidos);
	
	public abstract String escribirFila(Item item);
	
	public abstract String cabecera();
}