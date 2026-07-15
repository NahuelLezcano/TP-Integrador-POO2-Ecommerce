package envio;

public class Direccion {

    int coordX;
    int coordY;

    public Direccion(int coordX, int coordY) {
        this.coordX = coordX;
        this.coordY = coordY;
    }

    public double distanciaEnKmA(Direccion direccionDestino) {
        int diferenciaCoordX = this.coordX - direccionDestino.coordX;
        int diferenciaCoordY = this.coordY - direccionDestino.coordY;
        return Math.sqrt(diferenciaCoordX * diferenciaCoordX + diferenciaCoordY * diferenciaCoordY);
    }

}
