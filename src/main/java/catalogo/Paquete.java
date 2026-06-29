package catalogo;

import java.util.ArrayList;
import java.util.List;

public class Paquete implements Item {

    private int descuento;
    private String nombre;
    private final List<Item> elementos = new ArrayList<>();

    public Paquete(int descuento) {
        this.descuento = descuento;
    }

    public double precioFinal() {
        //TODO falta completar
    }

    public void agregarElemento(Item elemento) {
        elementos.add(elemento);
    }

    public void removerElemento(Item elemento) {
        elementos.remove(elemento);
    }

    @Override
    public String getNombre() {
        return nombre;
    }
}
