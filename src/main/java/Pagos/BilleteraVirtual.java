package Pagos;

public class BilleteraVirtual extends MetodoDePago {

    private double saldo;
    private IBilleteraVirtual api;

    public BilleteraVirtual() {
    }

    public BilleteraVirtual(double saldo) {
        this.saldo = saldo;
    }

    public void setApi(IBilleteraVirtual api) {
        this.api = api;
    }

    @Override
    protected void validarDatos() {
        api.saldoSuficiente();
    }

    @Override
    protected void reservarFondos() {
        api.bloqueoDeFondos();
    }

    @Override
    protected void ejecutarTransaccion() {
        api.acreditacionInmediata();
    }

    @Override
    protected void notificarResultado(String usuario) {
        api.notificacionPush(usuario);
    }
}
