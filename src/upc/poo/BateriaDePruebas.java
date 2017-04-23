package upc.poo;

import upc.poo.busqueda.Criterio;
import upc.poo.esquema.Clave;
import upc.poo.esquema.Esquema;
import upc.poo.tabla.FilaDatos;
import upc.poo.tabla.Tabla;

import java.util.Iterator;
import java.util.List;

/**
 * <p>
 * Batería de pruebas que ayudará a probar más rápida y agilmente las clases
 * {@link Criterio}, {@link Clave}, {@link Esquema}, {@link FilaDatos}, {@link Tabla}.</p>
 * <p>
 * <b>ESTA CLASE SE OS DA HECHA, PODEÍS MODIFICARLA SI QUERÉIS</b></p>
 *
 *
 * <p>
 * Para ver si funcionan, símplemente ejecuta lo siguiente desde el main:</p>
 *
 * <pre>BateriaDePruebas.ejecutaTodas()</pre>
 */
public class BateriaDePruebas {
    
    private static final String NC_MATRICULA = "Matricula";
    private static final String NC_MARCA = "Marca";
    private static final String NC_MODELO = "Modelo";
    private static final String NC_AÑO = "Año";


    //// IMPORTANTE: esta clase se da hecha. No debéis modificar nada
    public static void ejecutaTodas() {
        pruebaEsquemaClave();
        pruebaTablaAñadeFilas();
        pruebaCriterios();
        pruebaTablaBusqueda();
        pruebaTablaEliminacion();
    }

    public static void pruebaEsquemaClave() {

        // DESCOMENTAR CONFORME SE COMPLETE EL CÓDIGO PERTINENTE
        System.out.println("*** Probando esquema y claves ***");
        Esquema esquema = generaEsquemaCoche();

        System.out.println("Operación básica");
        comprueba(esquema.contieneClave(NC_MATRICULA), true);
        comprueba(esquema.contieneClave("Tirititiririt"), false);

        System.out.println("Comprueba datos básicos de clave");
        for(Iterator<Clave> itc = esquema.iterator(); itc.hasNext() ; ) {
            // Solo debe ser unica la clave de matricula
            Clave c = itc.next();
            System.out.println("\t- Clave: " + c.getNombre());
            comprueba(c.isUnica(), NC_MATRICULA.equals(c.getNombre()));
        }
    }

    public static void pruebaTablaAñadeFilas() {

        // DESCOMENTAR CONFORME SE COMPLETE EL CÓDIGO PERTINENTE
        System.out.println("*** Probando filas y tablas ***");
        Esquema esquema = generaEsquemaCoche();
        Tabla tabla = new Tabla(esquema,"coches");

        System.out.println("Validando y añadiendo fila correcta");
        FilaDatos f = filaCoche("12345XD", "Seat", "Córdoba", "2000");
        comprueba(f.valida(esquema), true);
        comprueba(tabla.anyade(f), true);
        comprueba(tabla.buscaTodo().size(), 1);
        f = tabla.buscaTodo().get(0);
        comprueba(f.getValor(NC_MATRICULA), "12345XD");
        comprueba(f.getValor(NC_MARCA), "Seat");
        comprueba(f.getValor(NC_MODELO), "Córdoba");
        comprueba(f.getValor(NC_AÑO), "2000");

        System.out.println("Validando fila con esquema incorrecto");
        f = new FilaDatos();
        f.put("ClaveIncorrecta", "unValorCualquiera");
        comprueba(f.valida(esquema), false);

        System.out.println("Añadiendo fila válida pero que viola clave única");
        f = filaCoche("12345XD", "Ford", "Fiesta", "2001");
        comprueba(f.valida(esquema), true);
        comprueba(tabla.anyade(f), false);
        comprueba(tabla.buscaTodo().size(), 1);
    }

    public static void pruebaCriterios() {
        // DESCOMENTAR CONFORME SE COMPLETE EL CÓDIGO PERTINENTE

        System.out.println("*** Probando criterios de búsqueda ***");
        FilaDatos f = filaCoche("3423VG", "Seat", "Ibiza", null); 

        System.out.println("Comprobando que los criterios funcionan bien");
        comprueba(new Criterio(NC_MATRICULA, Criterio.IGUAL, "3423vg").esCumplido(f), true);
        comprueba(new Criterio(NC_MODELO, Criterio.CONTIENE, "BIZ").esCumplido(f), true);
        comprueba(new Criterio(NC_AÑO, Criterio.IGUAL, null).esCumplido(f), true);          //Error amb el NULL
        comprueba(new Criterio(NC_AÑO, Criterio.CONTIENE, null).esCumplido(f), true);       //Error amb el NULL

        System.out.println("Comprobando criterios que no cumplen");
        comprueba(new Criterio(NC_AÑO, Criterio.CONTIENE, "1234").esCumplido(f), false);
        comprueba(new Criterio(NC_AÑO, Criterio.IGUAL, "1234").esCumplido(f), false);

        System.out.println("Comprobando claves incorrectas");
        comprueba(new Criterio("ClaveIncorrecta", Criterio.CONTIENE, "1234").esCumplido(f), false);
        comprueba(new Criterio("ClaveIncorrecta", Criterio.IGUAL, "1234").esCumplido(f), false);
    }

    public static void pruebaTablaBusqueda() {
                // DESCOMENTAR CONFORME SE COMPLETE EL CÓDIGO PERTINENTE

        System.out.println("*** Probando búsquedas en tablas ***");

        System.out.println("Generando tabla...");
        Tabla coches = new Tabla(generaEsquemaCoche(),"Coches");
        coches.anyade(filaCoche("1234AAA", "Seat", "Ibiza", "2000"));
        coches.anyade(filaCoche("3212BCC", "Ford", "Mondeo", "2002"));
        coches.anyade(filaCoche("3423GGD", "Peugeot", "207", "2010"));
        coches.anyade(filaCoche("4432DVV", "Daewoo", "Lanos", "2008"));
        coches.anyade(filaCoche("5544DFG", "Seat", "León", "2015"));
        comprueba(coches.buscaTodo().size(), 5);

        System.out.println("Buscar todo...");
        List<FilaDatos> todo = coches.buscaTodo();
        comprueba(todo.size(), coches.buscaTodo().size());
        comprueba(todo.get(0).getValor(NC_AÑO), "2000");
        comprueba(todo.get(1).getValor(NC_MARCA), "Ford");
        comprueba(todo.get(2).getValor(NC_MODELO), "207");
        comprueba(todo.get(3).getValor(NC_MATRICULA), "4432DVV");
        comprueba(todo.get(4).getValor(NC_AÑO), "2015");

        System.out.println("Comprobando que las listas resultantes es una COPIA de la original");
        todo.remove(4);
        todo.remove(1);
        comprueba(coches.buscaTodo().size(), 5);
        comprueba(coches.buscaTodo().get(4).getValor(NC_AÑO), "2015");

        System.out.println("Búsqueda según criterio 'IGUAL'");
        List<FilaDatos> seats = coches.busca(new Criterio(NC_MARCA, Criterio.IGUAL, "SEAT"));
        comprueba(seats.size(), 2);
        comprueba(seats.get(0).getValor(NC_MODELO), "Ibiza");
        comprueba(seats.get(1).getValor(NC_MODELO), "León");

        System.out.println("Búsqueda según criterio 'CONTIENE'");
        List<FilaDatos> cuatros = coches.busca(new Criterio(NC_MATRICULA, Criterio.CONTIENE, "44"));
        comprueba(cuatros.size(), 2);
        comprueba(cuatros.get(0).getValor(NC_MATRICULA), "4432DVV");
        comprueba(cuatros.get(1).getValor(NC_MATRICULA), "5544DFG");

        System.out.println("Búsqueda según criterio no encontrado");
        List<FilaDatos> vacia = coches.busca(new Criterio(NC_AÑO, Criterio.IGUAL, null));
        comprueba(vacia.isEmpty(), true);
    }

    public static void pruebaTablaEliminacion() {
                // DESCOMENTAR CONFORME SE COMPLETE EL CÓDIGO PERTINENTE

        System.out.println("*** Probando eliminación en tablas ***");

        System.out.println("Generando tabla...");
        Tabla coches = new Tabla(generaEsquemaCoche(), "Coches");
        coches.anyade(filaCoche("1234AAA", "Seat", "Ibiza", "2000"));
        coches.anyade(filaCoche("3212BCC", "Ford", "Mondeo", "2002"));
        coches.anyade(filaCoche("3423GGD", "Peugeot", "207", "2010"));
        coches.anyade(filaCoche("4432DVV", "Daewoo", "Lanos", "2008"));
        coches.anyade(filaCoche("5544DFG", "Seat", "León", "2015"));
        comprueba(coches.buscaTodo().size(), 5);

        System.out.println("Si no coinciden criterios, no se elimina nada");
        List<FilaDatos> eliminados = coches.elimina(new Criterio(NC_MARCA, Criterio.IGUAL, "Ferrari"));
        comprueba(eliminados.isEmpty(), true);
        comprueba(coches.buscaTodo().size(), 5);

        System.out.println("Eliminar según criterio 'IGUAL'");
        eliminados = coches.elimina(new Criterio(NC_MARCA, Criterio.IGUAL, "Daewoo"));
        comprueba(eliminados.size(), 1);
        comprueba(eliminados.get(0).getValor(NC_AÑO), "2008");
        comprueba(coches.buscaTodo().size(), 4);
        comprueba(coches.buscaTodo().get(3).getValor(NC_MARCA), "Seat");

        System.out.println("Eliminar según criterio 'CONTIENE'");
        eliminados = coches.elimina(new Criterio(NC_MODELO, Criterio.CONTIENE, "N"));
        comprueba(eliminados.size(), 2);
        comprueba(eliminados.get(0).getValor(NC_MARCA), "Ford");
        comprueba(coches.buscaTodo().size(), 2);
        comprueba(coches.buscaTodo().get(1).getValor(NC_MODELO), "207");

    }

 private static Esquema generaEsquemaCoche() {
        // DESCOMENTAR
        
        Esquema esquema = new Esquema();
        esquema.addClave(new Clave(NC_MATRICULA, true));
        esquema.addClave(new Clave(NC_MARCA));
        esquema.addClave(new Clave(NC_MODELO));
        esquema.addClave(new Clave(NC_AÑO));
        return esquema;
    }

    private static FilaDatos filaCoche(String matricula, String marca, String modelo, String año) {
        FilaDatos fila = new FilaDatos();
        fila.put(NC_MATRICULA, matricula);
        fila.put(NC_MARCA, marca);
        fila.put(NC_MODELO, modelo);
        fila.put(NC_AÑO, año);
        return fila;
    }

    private static void comprueba(String valorActual, String valorEsperado) {
        if (!valorActual.equals(valorEsperado)) {
            throw new AssertionError("Esperaba: " + valorEsperado + ". Obtenido: " + valorActual);
        }
    }

    private static void comprueba(int valorActual, int valorEsperado) {
        if (valorActual != valorEsperado) {
            throw new AssertionError("Esperaba: " + valorEsperado + ". Obtenido: " + valorActual);
        }
    }

    private static void comprueba(boolean valorActual, boolean valorEsperado) {
        if (valorActual != valorEsperado) {
            throw new AssertionError("Esperaba: " + valorEsperado + ". Obtenido: " + valorActual);
        }
    }

}
