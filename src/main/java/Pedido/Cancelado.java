package Pedido;

import catalogo.Item;

public class Cancelado extends Estado {

    public Cancelado(Pedido pedido) {
        super(pedido);
    }

    @Override
    public void agregarItem(Item item) {
        //a completar
    }

    @Override
    public void removerItem(Item item) {
        //a completar
    }

    @Override
    public String confirmar() {
        return "A completar";
    }

    @Override
    public String cancelar() {
        return "A completar";
    }
}
