package Pagos;

public class TransferenciaBancaria extends MetodoDePago {

    private int cbu;
    private int cvu;
    private String alias;
    private ITransferenciaBancaria api;

    public TransferenciaBancaria() {
    }

    public TransferenciaBancaria(int cbu, int cvu, String alias) {
        this.cbu = cbu;
        this.cvu = cvu;
        this.alias = alias;
    }

    public void setApi(ITransferenciaBancaria api) {
        this.api = api;
    }

    @Override
    protected void validarDatos() {
        api.validarDatos();
    }

    @Override
    protected void ejecutarTransaccion() {
        api.transferir();
    }

}
