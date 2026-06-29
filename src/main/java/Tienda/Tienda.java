package Tienda;

import catalogo.*;
import java.util.List;
import java.util.Map;

public class Tienda {

    private Catalogo catalogo;
    private Deposito deposito;

    public void setCatalogo(List<Item> items) {
        catalogo = new Catalogo(items);
    }

    public void setDeposito(Map<Item, Integer> stock) {
        deposito = new Deposito(stock);
    }
}
