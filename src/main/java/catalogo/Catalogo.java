package catalogo;

import java.util.*;

import Tienda.Deposito;

public class Catalogo {
	
    private List<Item> items = new ArrayList<>();
    private Deposito deposito;
    
    public Catalogo(List<Item> items, Deposito deposito) {
    	this.items = items;
    	this.deposito = deposito;
    }

    public void agregarItemSiHayStock(Item item) {
    	if (deposito.cantidadEnStock(item) > 0) {
    		items.add(item);
    	} else {
    		throw new IllegalArgumentException("No se puede agregar al catálogo porque el item no está en stock");
    	}
    }

    public void removerItem(Item item) {
        items.remove(item);
    }

    public List<Item> getItems() {
        return items;
    }
    
    public int cantidadDeItems() {
    	return getItems().size();
    }
    
    public boolean contiene(Item item) {
    	return getItems().contains(item);
    }
    
}

