package envio;

import Pedido.Pedido;

public interface MetodoDeEnvio {

    double costoDeEnvio(Pedido pedido);

    int estimacionDeDias(Pedido pedido);
}
