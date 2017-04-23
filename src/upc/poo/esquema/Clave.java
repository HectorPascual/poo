package upc.poo.esquema;

/**
 * Clase que representa una columna dentro del {@link Esquema} de una
 * {@link upc.poo.tabla.Tabla}.
 */
public class Clave {
    
    private String nombre;
    private boolean unica;
    
    public Clave(String nombre){
        this.nombre = nombre;
        this.unica = false;
    }
    
    public Clave(String nombre, boolean unica){
        this.nombre = nombre;
        this.unica = unica; 
    }
    
    public String getNombre(){
        return nombre;
    }
    
    public boolean isUnica(){
        return unica;
    }
    
}
