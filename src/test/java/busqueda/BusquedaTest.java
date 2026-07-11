package busqueda;

import Tienda.*;
import catalogo.*;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class BusquedaTest {

    Producto laptop;
    Producto mouse;
    Producto escritorio;
    Producto silla;
    Producto teclado;
    Paquete packOficina;
    Deposito unDeposito;
    Catalogo unCatalogo;
    ModuloBusqueda buscador;

    @BeforeEach
    void setUp() {
        laptop = new Producto("1", "Laptop Pro", "x", "Computación", 1, 1500, "Computadora personal");
        mouse = new Producto("2", "Mouse Inalámbrico", "x", "Computación", 1, 25, "Mouse para computadora");
        escritorio = new Producto("3", "Escritorio Madera", "x", "Muebles", 15, 200, "Mueble de madera");
        silla = new Producto("4", "Silla Ergonómica Pro", "x", "Muebles", 5, 150, "Silla para oficina");
        teclado = new Producto("5", "Teclado Mecánico", "x", "Computación", 1, 80, "Teclado para computadora");
        packOficina = new Paquete("Pack oficina", "Paquete oficina", "Oficina", 1, List.of(laptop, mouse, escritorio, silla, teclado));

        unDeposito = mock(Deposito.class);
        unCatalogo = new Catalogo(List.of(laptop, mouse, escritorio, silla, teclado,packOficina), unDeposito);
        buscador = new ModuloBusqueda(unCatalogo);
    }


    @Test
    void filtroSimple_BuscarPalabra() {
        Criterio criterioABuscar = new CriterioNombre("Mecánico");

        List<Item> resultadoConsulta = buscador.obtener(criterioABuscar);
        List<Producto> listaEsperada = List.of(teclado);

        // Solo funciona si tienen el mismo orden las dos listas.
        assertIterableEquals(listaEsperada, resultadoConsulta);
    }

    @Test
    void filtroSimple_BuscarPorPrecioMaximo() {
        Criterio criterioABuscar = new CriterioPrecioMax(100);

        List<Item> resultadoConsulta = buscador.obtener(criterioABuscar);
        List<Producto> listaEsperada = List.of(mouse, teclado);

        assertIterableEquals(listaEsperada, resultadoConsulta);
    }

    @Test
    void filtroSimple_BuscarPorCategoria() {
        Criterio criterioABuscar = new CriterioCategoria("Computación");

        List<Item> resultadoConsulta = buscador.obtener(criterioABuscar);
        List<Producto> listaEsperada = List.of(laptop, mouse, teclado);

        assertIterableEquals(listaEsperada, resultadoConsulta);
    }

    @Test
    void filtroSimple_BuscarPorCategoriaPack() {
        Criterio criterioABuscar = new CriterioCategoria("Oficina");

        List<Item> resultadoConsulta = buscador.obtener(criterioABuscar);
        List<Item> listaEsperada = List.of(packOficina);

        assertIterableEquals(listaEsperada, resultadoConsulta);
    }

    @Test
    void filtroSimple_BuscarPorDisponibilidad() {
        when(unDeposito.existeEnStockYEsValido(laptop)).thenReturn(true);
        when(unDeposito.existeEnStockYEsValido(mouse)).thenReturn(false); // No hay stock de mouse
        when(unDeposito.existeEnStockYEsValido(escritorio)).thenReturn(true);
        when(unDeposito.existeEnStockYEsValido(silla)).thenReturn(true);
        when(unDeposito.existeEnStockYEsValido(teclado)).thenReturn(false); // No hay stock de teclados
        when(unDeposito.existeEnStockYEsValido(packOficina)).thenReturn(true);

        Criterio criterioABuscar = new CriterioDisponibilidad(unDeposito);

        List<Item> resultadoConsulta = buscador.obtener(criterioABuscar);
        List<Item> listaEsperada = List.of(laptop, escritorio, silla,packOficina);

        assertIterableEquals(listaEsperada, resultadoConsulta);
    }

    @Test
    void filtroComplejo_LosItemsQueSatisfacenCriteriosCombinados() {
//        Computación AND precio ≤ $100

        Criterio itemsDeComputacion = new CriterioCategoria("Computación");
        Criterio precioMaximo = new CriterioPrecioMax(100);
        Criterio criterioCompuesto = new Conjuncion(itemsDeComputacion, precioMaximo);

        List<Item> resultadoConsulta = buscador.obtener(criterioCompuesto);
        List<Item> listaEsperada = List.of(mouse, teclado);

        assertIterableEquals(listaEsperada, resultadoConsulta);
    }

    @Test
    void filtroComplejo_LosItemsQueSatisfacenAlMenosUnoDeLosCriteriosCombinados() {
//        Muebles OR precio ≤ $180

        Criterio itemsMuebles = new CriterioCategoria("Muebles");
        Criterio itemsConPrecioHasta180 = new CriterioPrecioMax(180);
        Criterio criterioCompuesto = new Disyuncion(itemsMuebles, itemsConPrecioHasta180);

        List<Item> resultadoConsulta = buscador.obtener(criterioCompuesto);
        List<Item> listaEsperada = List.of(mouse, escritorio, silla, teclado);

        assertIterableEquals(listaEsperada, resultadoConsulta);
    }

    @Test
    void filtroComplejo_LosItemsQueNoCumplenUnCriterio() {
//        NOT Computación

        Criterio itemsDeComputacion = new CriterioCategoria("Computación");
        Criterio criterio = new Negacion(itemsDeComputacion);

        List<Item> resultadoConsulta = buscador.obtener(criterio);
        List<Item> listaEsperada = List.of(escritorio, silla, packOficina);

        assertIterableEquals(listaEsperada, resultadoConsulta);
    }

    @Test
    void filtroComplejo_CriteriosCompuestos() {
//      (Nombre contiene "pro" AND (NOT categoría = muebles)) OR precio ≤ $180

        Criterio elNombreContiene = new CriterioNombre("pro");
        Criterio itemsMuebles = new CriterioCategoria("Muebles");
        Criterio criterioNegado = new Negacion(itemsMuebles);
        Criterio primeraCombinacion = new Conjuncion(elNombreContiene,criterioNegado);
        Criterio precioMaximo = new CriterioPrecioMax(180);
        Criterio segundaCombinacion = new Disyuncion(primeraCombinacion, precioMaximo);

        List<Item> resultadoConsulta = buscador.obtener(segundaCombinacion);
        List<Item> listaEsperada = List.of(laptop, mouse, silla, teclado);

        assertIterableEquals(listaEsperada, resultadoConsulta);
    }
}

