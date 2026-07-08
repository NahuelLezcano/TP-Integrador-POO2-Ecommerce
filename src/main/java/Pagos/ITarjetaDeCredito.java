package Pagos;

public interface ITarjetaDeCredito {

    void validarDatos();

    void preAutorizacion();

    void transferir();
}
