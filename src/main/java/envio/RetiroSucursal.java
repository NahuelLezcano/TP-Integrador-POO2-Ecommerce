package envio;

import Pedido.Pedido;
import Tienda.Tienda;

public class RetiroSucursal implements MetodoDeEnvio {

    private Tienda tienda;  // La tienda seria la sucursal.

    public RetiroSucursal(Tienda tienda) {
        this.tienda = tienda;
    }

    @Override
    public double costoDeEnvio(Pedido pedido) {
        return 0;
    }

    @Override
    public int estimacionDeDias(Pedido pedido) { //Consultar stock. Falta completar.
        return 0;
    }
}
