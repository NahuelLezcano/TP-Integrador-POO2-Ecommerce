package busqueda;

import Tienda.Deposito;
import catalogo.*;

public class CriterioDisponibilidad implements Criterio {

    private Deposito deposito;

    public CriterioDisponibilidad(Deposito deposito) {
        this.deposito = deposito;
    }

    @Override
    public boolean cumpleCondicion(Item item) {
        return deposito.existeEnStockYEsValido(item);
    }
}
