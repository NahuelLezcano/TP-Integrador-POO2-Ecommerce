package Tienda;

import catalogo.Item;

import java.util.HashMap;
import java.util.Map;

public class Deposito {

	private Map<Item, Integer> stock = new HashMap<>();

	public Deposito(Map<Item, Integer> stock) {
		this.stock = stock;
	}

	public void agregarItemAlStock(Item item, Integer cantidad) {
		stock.put(item, cantidad);
	}

	public void removerCantidadDelStock(Item item, int cantidad) {
	    if (!validar(item, cantidad)) {
	        throw new IllegalArgumentException("Stock insuficiente o item invalido");
	    }
	    int cantidadActual = stock.get(item); //get agarra la cantidad asociada al item
	    stock.put(item, cantidadActual - cantidad);
	}

	public boolean validar(Item item, int cantidad) {
		return existeEnStockYEsValido(item) && cantidadEnStock(item) >= cantidad;

	}

	public boolean existeEnStockYEsValido(Item item) {
		return stock.containsKey(item) && item.validar();
	}

	public int cantidadEnStock(Item item) {
		return stock.getOrDefault(item, 0);
	}

}
