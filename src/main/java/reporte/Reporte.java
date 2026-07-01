package reporte;

import java.util.*;

import Tienda.*;
import catalogo.*;

public abstract class Reporte {
	
	protected Tienda tienda;
	protected StringBuilder reporte = new StringBuilder();
	
	public Reporte(Tienda tienda) {
		this.tienda = tienda;
	}

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