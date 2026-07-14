package envio;

import Pedido.Pedido;

public class Envio {

    private MetodoDeEnvio metodo;
    private Pedido pedido;

    public Envio(MetodoDeEnvio metodo, Pedido pedido) {
        this.metodo = metodo;
        this.pedido = pedido;
    }

    public void setMetodo(MetodoDeEnvio metodo) {
        this.metodo = metodo;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public double costoEnvio() {
        return metodo.costoDeEnvio(pedido);
    }

    public int estimacionDeDias() {
        return metodo.estimacionDeDias(pedido);
    }
}
