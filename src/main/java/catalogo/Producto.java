package catalogo;

import java.util.HashMap;
import java.util.Map;

public class Producto implements Item {

    private int sku;
    private String nombre;
    private int peso;
    private String marca;
    private String categoria;
    private double precioBase;
    private final Map<String, Object> atributosDinamicos = new HashMap<>();


    public Producto(String categoria, String marca, String nombre, int peso, double precioBase, int sku) {
        this.categoria = categoria;
        this.marca = marca;
        this.nombre = nombre;
        this.peso = peso;
        this.precioBase = precioBase;
        this.sku = sku;
    }

    public void set(String clave, Object valor) {
        this.atributosDinamicos.put(clave, valor);
    }

    @Override
    public double precioFinal() {
        //TODO falta completar
    }

    public String getCategoria() {
        return categoria;
    }

    public String getMarca() {
        return marca;
    }

    public String getNombre() {
        return nombre;
    }

    public int getPeso() {
        return peso;
    }

    public double getPrecioBase() {
        return precioBase;
    }

    public int getSku() {
        return sku;
    }
}
