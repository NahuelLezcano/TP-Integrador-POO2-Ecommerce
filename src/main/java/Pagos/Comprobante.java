package Pagos;

public class Comprobante {

    private String usuario;
    private final int nroOperacion;
    private static int contador = 1000;

    public Comprobante(String usuario) {
        this.usuario = usuario;
        this.nroOperacion = contador;
        contador++;
    }

    public String getUsuario() {
        return usuario;
    }

    public int getNroOperacion() {
        return nroOperacion;
    }
}
