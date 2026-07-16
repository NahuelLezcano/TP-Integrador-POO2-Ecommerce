package envio;

import Pedido.Pedido;

public class EnvioExpress implements MetodoDeEnvio {

    private double cargoBase;

    public EnvioExpress(double cargoBase) {
        this.cargoBase = cargoBase;
    }

    @Override
    public double costoDeEnvio(Pedido pedido) {
        int valorTotalDelPedido = pedido.montoTotal();
        double porcentaje = 0.10; // 10%
        return (valorTotalDelPedido * porcentaje) + cargoBase;
    }

    @Override
    public int estimacionDeDias(Pedido pedido) {
        return 1;
    }
}
