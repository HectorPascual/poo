package upc.poo.tabla;

import java.util.ArrayList;
import java.util.Collection;
import upc.poo.busqueda.Criterio;
import upc.poo.esquema.Esquema;


/**
 * Una tabla guarda, siguiendo un {@link Esquema} que indica qué columnas tiene
 * dicha tabla, una sucesión de {@link FilaDatos} con los datos almacenados en
 * la base de datos.
 */
public class Tabla {

    private Esquema esquema;
    private ArrayList<FilaDatos> filas;
    private String nombre;

    public Tabla(Esquema esquema, String nombre) {
        this.esquema = esquema;
        this.nombre = nombre;
        
        filas = new ArrayList<>();
    }
    
    public boolean anyade(FilaDatos datos){ //necesita un valor unic sino no funciona
        
        int i=0,t=0;
        
        String claveUnica = esquema.getFilaUnica();
        String valorClaveUnica = datos.getValor(claveUnica);//valor de la fila unica que passem per referencia
      
        
        if(datos.valida(esquema)){  //1a Condicio: la fila ha de ser vàlida
            
            for(i=0;i<filas.size();i++){
                if(filas.get(i).getValor(claveUnica)==null);  //Cas en que la fila unica no tingui un valor assignat (?)
                else if(valorClaveUnica.equals(filas.get(i).getValor(claveUnica)))
                    t = 1;          //2a condició: Comprovem que la clau unica no existeixi en cap posicio en la fila de valor unics de la taula
            }
            
            if(t == 1){
                return false;
            }
            else{
                filas.add(datos);
                return true;
            }
        }
        else
            return false;   
    }
    
    public ArrayList<FilaDatos> buscaTodo(){
        
        ArrayList<FilaDatos> resultats = new ArrayList<>();
        int i;
        
        for(i=0;i<filas.size();i++){
            resultats.add(filas.get(i));
        }
        
        return resultats;
    }
    
    public ArrayList<FilaDatos> busca(Criterio criterio){
        
        ArrayList<FilaDatos> resultats = new ArrayList<>();
        
        //MODIFICAR
        for(int i=0;i<filas.size();i++){
            if(criterio.esCumplido(filas.get(i)))
                resultats.add(filas.get(i));
        }
        return resultats;
        
    }
    
    public ArrayList<FilaDatos> elimina(Criterio criterio){
        
        ArrayList<FilaDatos> resultats = new ArrayList<>();
        
        
        //MODIFICAR
        for(int i=0;i<filas.size();i++){
            if(criterio.esCumplido(filas.get(i))){
                resultats.add(filas.get(i));
                filas.remove(i); 
            }
        }
        return resultats;
       
    }
    
    public Esquema getEsquema(){
        
        Esquema esquema = new Esquema();
        
        return esquema;
    }
    
    public ArrayList<String> getCabeceras(){
        
        ArrayList<String> resultats;
        resultats = (ArrayList<String>) esquema.getCabeceras();
        
        return resultats;
    }
}
