package Pagos;

import java.util.ArrayList;
import java.util.List;

public abstract class MetodoDePago {

    protected List<Comprobante> comprobantes = new ArrayList<>();

    public void procesarPago(ModuloDePago modulo, String usuario) {
        this.validarDatos();
        this.reservarFondos();
        this.ejecutarTransaccion();
        this.notificarResultado(usuario);
        modulo.registrarPago(); //Se agrega este llamado para que el modulo actualice su estado
    }

    public List<Comprobante> getComprobantes() {
        return comprobantes;
    }

    protected abstract void validarDatos();

    protected void reservarFondos() {}

    protected abstract void ejecutarTransaccion();

    protected void notificarResultado(String usuario) {
        comprobantes.add(new Comprobante(usuario));
    }

}
