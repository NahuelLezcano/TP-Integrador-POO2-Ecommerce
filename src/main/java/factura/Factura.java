package factura;

import java.util.*;

import catalogo.Item;

public class Factura {
	List<Item> items;
	
	public Factura() {
		this.items = new ArrayList<Item>();
	}

	public void agregarItem(Item item) {
		this.items.add(item);
	}
	
	public double montoTotal() {
		return this.items.stream().mapToDouble( i -> i.getPrecioFinal()).sum();
	}
}
