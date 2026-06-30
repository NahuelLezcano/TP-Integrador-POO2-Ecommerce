package Pedido;

import catalogo.Item;

public abstract class Estado { //Elegí una clase abstracta para que todos los estados compartan la variable del contexto

    Pedido pedido; //Contexto

    public Estado(Pedido pedido) {
        this.pedido = pedido;
    }

    public abstract void agregarItem(Item item);

    public abstract void removerItem(Item item);

    public abstract String confirmar();

    public abstract String cancelar();
}
