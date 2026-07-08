package Pagos;

public interface IBilleteraVirtual {

    void saldoSuficiente();

    void bloqueoDeFondos();

    void acreditacionInmediata();

    void notificacionPush(String usuario);
}
