package Tienda;

import catalogo.Item;
import envio.Direccion;

import java.util.HashMap;
import java.util.Map;

public class Deposito {

	private Map<Item, Integer> stock = new HashMap<>();
	private Direccion direccion;

	public Deposito() {}

	public Deposito(Map<Item, Integer> stock, Direccion direccion) {
		this.stock = stock;
		this.direccion = direccion;
	}

	public void agregarItemAlStock(Item item, Integer cantidad) {
		stock.merge(item, cantidad, Integer::sum); // Si la clave no existe, añade el valor. Si ya existe, aplica la función de suma.
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

	public Direccion getDireccion() {
		return direccion;
	}

}
