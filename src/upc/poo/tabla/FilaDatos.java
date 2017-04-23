package upc.poo.tabla;

import java.util.Iterator;
import java.util.TreeMap;
import upc.poo.esquema.Esquema;

/**
 * Clase que representa una fila de datos dentro de una {@link Tabla}. Cada
 * fila de datos es un conjunto de claves-valor, cuya clave representa el nombre
 * de una columna, y el valor será el contenido de esa columna para la fila
 * dada
 * 
 */
public class FilaDatos {
    
    private TreeMap<String,String> Fila;
    
    public FilaDatos(){
        Fila = new TreeMap<>();
    }
    
    public void put(String nombreClave, String valor){
        Fila.put(nombreClave, valor);
    }
    
    public String getValor(String cabecera) {
        if(Fila.containsKey(cabecera))
            return(Fila.get(cabecera));
        else
            return(null);
    }
    
    public boolean valida(Esquema esquema){
        int i,c=0;
        Iterator<String> llista = Fila.descendingKeySet().descendingIterator();
        for(i=0;i<Fila.size();i++){                    //Comprova si cada nom de la fila existeix
            if(esquema.contieneClave(llista.next()))   //en els noms de l'esquema. Si existeix es suma 1 a c. 
                c++;
        }
        
        if(c==Fila.size())      
            return true;
        else
            return false;
        
    }

}
