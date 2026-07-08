package Pagos;

public class TarjetaDeCredito extends MetodoDePago {

    private int nroTarjeta;
    private int cvv;
    private String vencimiento;
    private ITarjetaDeCredito api;

    public TarjetaDeCredito() {
    }

    public TarjetaDeCredito(int nroTarjeta, int cvv, String vencimiento) {
        this.nroTarjeta = nroTarjeta;
        this.cvv = cvv;
        this.vencimiento = vencimiento;
    }

    public void setApi(ITarjetaDeCredito api) {
        this.api = api;
    }

    @Override
    protected void validarDatos() {
        api.validarDatos();
    }

    @Override
    protected void reservarFondos() {
        api.preAutorizacion();
    }

    @Override
    protected void ejecutarTransaccion() {
        api.transferir();
    }

}
