package Pagos;

public class ModuloDePago {

    private boolean pagoCompletado = false;

    public void realizarCobro(MetodoDePago metodo, String usuario) {
        metodo.procesarPago(this, usuario);
    }

    public boolean elPagoEstaCompletado() {
        return pagoCompletado;
    }

    protected void registrarPago() {
        pagoCompletado = true;
    }
}
