package upc.poo.esquema;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NavigableSet;
import java.util.TreeMap;

/**
 * Un Esquema indica la estructura de una {@link upc.poo.tabla.Tabla}, es decir,
 * qué claves tiene ésta.
 */
public class Esquema {
    
    private TreeMap<String,Clave> campos;
    
    
    public Esquema(){
        campos = new TreeMap<>();
    }
    
    public void addClave(Clave clave){
        campos.put(clave.getNombre(), clave);
    }
    
    public boolean contieneClave(String nombre){
        return campos.containsKey(nombre); 
    }
    
    public Iterator<Clave> iterator(){
        Iterator<Clave> it = campos.values().iterator();   
        return it;
    }
    
    
    public String getFilaUnica(){       //He afegit aquesta funcio1 per a simplificar la funció "Añade" de tabla.java
        
        Iterator<Clave> it = campos.values().iterator();
        Clave var;
        
        
        for(int i=0;i<campos.size();i++){
             var = it.next();
             if(var.isUnica())
                 return var.getNombre();
        }
        
        return null;
    }
    
    public ArrayList<String> getCabeceras(){    //Classe per a conseguir les cabeceras directament del esquema
        ArrayList<String> cabeceras = new ArrayList<>();
        
        NavigableSet set = campos.descendingKeySet();
      
        cabeceras.addAll(set);
  
        return cabeceras;
    }
}
