package factura;

import java.util.*;

import catalogo.Item;

public class Factura {

	private Map<Item, Integer> items;

	public Factura(Map<Item, Integer> items) {
		this.items = new HashMap<>(items);
	}

	public Map<Item, Integer> getItems() {
		return items;
	}

	public double montoTotal() {
		return items.entrySet().stream().mapToDouble(items -> items.getKey().getPrecioFinal() * items.getValue()).sum();
	}

	public String desgloseDeFactura() {
		StringBuilder sb = new StringBuilder();
		sb.append("Factura:\n");
		items.forEach((item, cantidad) -> sb.append(item.getNombre()).append(" x").append(cantidad).append("\n"));
		sb.append("Monto Total: $").append(montoTotal());
		return sb.toString();
	}
}
