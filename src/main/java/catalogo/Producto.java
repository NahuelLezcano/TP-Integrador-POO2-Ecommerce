package catalogo;

import java.util.ArrayList;
import java.util.List;

public class Producto implements Item {
	
	private String SKU;
    private String nombre;
    private String marca;
    private String categoria;
    private int peso;
    private int precioBase;
    private String descripcion;
    private List<String> atributosExtra = new ArrayList<>();
    
    public Producto(String sku, String nombre, String marca, String categoria, int peso, int precioBase, String descripcion) {
        this.SKU = sku;
        this.nombre = nombre;
        this.marca = marca;
        this.categoria = categoria;
        this.peso = peso;
        this.precioBase = precioBase;
        this.descripcion = descripcion;
    }
    
    public Producto(String sku, String nombre, String marca, String categoria, int peso, int precioBase, String descripcion, List<String> atributosExtra) {
        this.SKU = sku;
        this.nombre = nombre;
        this.marca = marca;
        this.categoria = categoria;
        this.peso = peso;
        this.precioBase = precioBase;
        this.descripcion = descripcion;
        this.atributosExtra = atributosExtra;
    }
    
    public String getSKU() { 
    	return SKU; 
    }
    
    @Override
    public String getNombre() { 
    	return nombre; 
    }
    
    public String getMarca() {
		return marca;
	}
    
    public String getCategoria() {
    	return categoria;
    }
    
    public int getPeso() {
    	return peso;
    }
    
    @Override
    public int getPrecioBase() {
    	return precioBase;
    }
    
    @Override
    public String getDescripcion() {
		return descripcion;
	}
    
    /* ejemplo de como pueden escribirse:
   	 * "color=rojo", "ancho=20"...
   	 */
    public void agregarAtributoExtra(String atributo) {
    	atributosExtra.add(atributo);
    }

    public List<String> getAtributosExtras() {
        return atributosExtra;
    }
    
    @Override
    public boolean validar() {
        return validarObligatorios() && validarExtras();
    }
    
    //¿Qué se considera obligatorio?
	public boolean validarObligatorios() {
		return  (
				!getSKU().isEmpty() &&
				!getNombre().isEmpty() &&
				!getMarca().isEmpty() &&
				!getCategoria().isEmpty() &&
				!getDescripcion().isEmpty() &&
				getPeso() >= 0 &&
				getPrecioBase() >= 0
				);
	}
	
	public boolean validarExtras() {
		return  getAtributosExtras().isEmpty() || 
				getAtributosExtras().size() >= 1;
	}
	
	@Override
	public int getPrecioFinal() {
		return getPrecioBase();
	}
    
	
}
