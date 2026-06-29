package catalogo;

import java.util.*;

public class Catalogo {
	
    private List<Item> items = new ArrayList<>();
    
    public Catalogo(List<Item> items) {
    	this.items = items;
    }

    public void agregarItem(Item item) {
        items.add(item);
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
    
}

