package envio;

import Pedido.Pedido;

public class EnvioEstandar implements MetodoDeEnvio {

    private Direccion direccionEnvio;
    private CorreoArgentino correoArgentino;

    public EnvioEstandar(Direccion direccionEnvio, CorreoArgentino correoArgentino) {
        this.direccionEnvio = direccionEnvio;
        this.correoArgentino = correoArgentino;
    }

    @Override
    public double costoDeEnvio(Pedido pedido) {
        return correoArgentino.estimarEnvio(pedido.pesoTotalPedido(), direccionEnvio) ;
    }

    @Override
    public int estimacionDeDias(Pedido pedido) {
        return 0;  // Falta completar. La estimación de días es fija entre 5 y 7 días hábiles
    }
}
