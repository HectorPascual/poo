package upc.poo.busqueda;

import upc.poo.tabla.FilaDatos;

/**
 * Clase que representa un criterio de búsqueda o eliminación en las filas
 * (cada una de ellas siendo una instancia de {@link FilaDatos}) de una 
 * {@link upc.poo.tabla.Tabla}. El criterio se
 * cumplirá cuando una {@link FilaDatos} contenga una pareja [nombre de la clave, valor de la clave]
 * con una clave cuyo nombre sea igual al atributo {@link #nombreClave} y cuyo valor de clave cumpla
 con el criterio ({@link #IGUAL} o {@link #CONTIENE}) del valor del 
 * atributo {@link #valorAComprobar} de la instancia de Criterio. Para más detalles 
 * leer descripción de método {@link #esCumplido(upc.poo.tabla.FilaDatos) }.
 * 
 */
public class Criterio {
    
    public static final int CONTIENE = 0 ;
    public static final int IGUAL = 1;
    
    private String nombreClave;
    private int tipo; 
    private String valorAComprobar;

    public Criterio(String Clave, int tipo, String valorAComprobar) {
        this.nombreClave = Clave;
        this.tipo = tipo;
        this.valorAComprobar = valorAComprobar;
    }

    public boolean esCumplido(FilaDatos f){
        
        if(this.valorAComprobar==null && f.getValor(nombreClave)==null)
            return true;
        else if(f.getValor(nombreClave)==null){
            return false;
        }
        else{
            if(tipo==IGUAL)
                return f.getValor(nombreClave).equalsIgnoreCase(valorAComprobar);
            if (tipo == CONTIENE)
                return f.getValor(nombreClave).toLowerCase().contains(valorAComprobar.toLowerCase()); //Ho passem tot a minuscules per a analitzar-ho.
            else
                return false;
        }

        }
    
}