package upc.poo;

import java.util.*;

import upc.poo.busqueda.Criterio;
import upc.poo.esquema.Clave;
import upc.poo.esquema.Esquema;
import upc.poo.tabla.FilaDatos;
import upc.poo.tabla.Tabla;
import upc.poo.ui.InterfazUsuario;

/**
 * Controlador de la base de datos, que guardará las diferentes tablas y
 * facilitará la comunicación entre la {@link upc.poo.ui.InterfazUsuario} y los
 * demás elementos de la base de datos.
 * <p>
 * <b>IMPORTANTE: SE OS ENTREGA EL SIGUIENTE CÓDIGO, QUE NO TENÉIS QUE
 * MODIFICAR:</b></p>
 * <ol>
 * <li><b>LAS DEFINICIONES DE LOS ATRIBUTOS PÚBLICOS FINALES Y ESTÁTICOS
 * (CONSTANTES) QUE DEFINEN CARACTERES ESPECIALES UTILIZADOS EN LOS COMANDOS QUE
 * CONLLEVEN BÚSQUEDA EN UNA TABLA, LOS COMANDOS, Y ANCHO DE COLUMNA</b></li>
 * </ol>
 *
 */
public class Controlador {

    /**
     * Símbolo que, durante la definición del esquema de una tabla (comando
     * 'crea') delante del nombre de una clave indica que ésta es clave única
     */
    public static final char SIMB_CLAVE_ÚNICA = '*';

    /**
     * Símbolo que, durante la definición de un criterio de
     * búsqueda/eliminación, especifica que es un criterio del tipo
     * {@link Criterio#IGUAL}
     */
    public static final String SIMB_IGUAL = "=";

    /**
     * Símbolo que, durante la definición de un criterio de
     * búsqueda/eliminación, especifica que es un criterio del tipo
     * {@link Criterio#CONTIENE}
     */
    public static final String SIMB_CONTIENE = "#";

    /**
     * Cuando se muestran las filas de una tabla, el ancho de la columna (en
     * carácteres) que los valores de ésta ocuparán.
     */
    public static final int ANCHO_COLUMNA = 15;

    /**
     * Comando 'crear tabla'
     */
    public static final String CMD_CREA_TABLA = "crea";

    /**
     * Comando 'buscar en tabla'
     */
    public static final String CMD_BUSCA = "busca";

    /**
     * Comando 'añadir datos a una tabla'
     */
    public static final String CMD_AÑADE = "anyade";

    /**
     * Comando 'eliminar datos de una tabla'
     */
    public static final String CMD_ELIMINA = "elimina";

    /**
     * Comando 'crear tabla'
     */
    public static final String CMD_AYUDA = "ayuda";

    /**
     * Comando 'crear tabla'
     */
    public static final String CMD_SALIR = "salir";

    /**
     * Tablas almacenadas por el usuario. La clave del Mapa pertenece al nombre
     * de la tabla.
     */
    private Map<String, Tabla> tablas;

    /**
     * El interfaz de usuario del programa
     */
    private InterfazUsuario iu;

    /**
     * Constructor sin argumentos que crea un nuevo mapa vacío
     */
    public Controlador() {
        this.tablas = new TreeMap<String, Tabla>();
    }

    /**
     * Asigna valor a atributo iu
     *
     * @param iu el nuevo valor de atributo iu
     */
    public void setIu(InterfazUsuario iu) {
        this.iu = iu;
    }

    /**
     * <p>
     * Gestiona el comando 'crea', que se encarga de crear una tabla nueva con
     * un esquema dado.</p>
     * <p>
     * Crea una tabla dado un nombre y un esquema, y la guarda en el sistema. Si
     * ya existe una tabla con dicho nombre, no la creará y ordenará al interfaz
     * de usuario que presente un mensaje de error por pantalla. Hará lo mismo
     * si el formato del comando es erróneo (el número de palabras es menor que
     * 3).</p>
     *
     * <p>
     * Formato:
     * <code>alumnos crea nombre apellidos *dni año_nacimiento</code></p>
     *
     * <p>
     * Se debe considerar que las claves precedidas por el carácter
     * {@link #SIMB_CLAVE_ÚNICA} son claves únicas (es decir, su valor no puede
     * estar repetido en la tabla)</p>
     *
     * <p>
     * Si el usuario no introduce al menos una clave para la tabla, el sistema
     * muestra el mensaje: <code>Error en formato del comando</code>.</p>
     *
     * <p>
     * Para facilitar la tarea, asumiremos que las claves del esquema son
     * cadenas alfanuméricas sin espacios en blanco</p>
     *
     * @param palabras Array con las palabras pertenecientes a un comando, por
     * ejemplo: <code>{ "canciones", "crea", "artista", "título",
     *                 "duración" }</code>
     */
    public void crearTabla(String[] palabras) { //Sol funciona quan hi ha un *

        int asterisc = 0;

        for (int i = 0; i < palabras.length; i++) {
            if (palabras[i].startsWith("*")) {
                asterisc++;
            }
        }

        if (palabras.length < 3) {
            System.out.println("Format incorrecte");
        } else if (asterisc == 0) {
            System.out.println("Falta clau única '*'");
        } else if (asterisc > 1) {
            System.out.println("Només hi pot haver una clau única");
        } else {
            Esquema esquema = new Esquema();
            for (int i = 2; i < palabras.length; i++) {
                if (palabras[i].startsWith("*")) {
                    String palabras2 = palabras[i].replace("*", "");
                    Clave clau = new Clave(palabras2, true);
                    esquema.addClave(clau);
                } else {
                    Clave clau = new Clave(palabras[i]);
                    esquema.addClave(clau);
                }
            }
            tablas.put(palabras[0], new Tabla(esquema, palabras[0]));
        }
        Set<String> keys = tablas.keySet() ;
        
    }

    public void añadir(String[] palabras) {
        if (palabras.length < 3) {
            System.out.println("Format incorrecte");
        } else if (!tablas.containsKey(palabras[0])) {
            System.out.println("La taula no existeix");
        } else {
            FilaDatos datos = new FilaDatos();
            String[] palabras2 = new String[3];

            for (int i = 2; i < palabras.length; i++) {
                if (palabras[i].split("=").length == 1) {
                    System.out.println("Format " + palabras[i] + " incorrecte");
                } else {
                    palabras2 = palabras[i].split("=");
                    datos.put(palabras2[0].trim(), palabras2[1]);

                }
            }
            Tabla tabla = tablas.get(palabras[0]);
            if (tabla != null) {
                if(tabla.anyade(datos)) //no funciona quan teclado = false
                    System.out.println("Introduït");
                else {
                    System.out.println("No s'ha pogut introduir");
                }
                    
            } else {
                System.out.println("La taula no existeix");
            }
        }
    }

    /**
     * <p>
     * Gestiona el comando 'elimina'. Para la tabla especificada por el usuario,
     * elimina todas las filas que cumplan con el criterio.</p>
     *
     * <p>
     * Elimina las filas de la tabla cuyo nombre está en
     * <code>palabras[0]</code> que cumplan el criterio especificado. Una vez
     * eliminadas ordenará al interfaz de usuario presentar un mensaje que
     * indique el número de filas eliminadas. Si la tabla no existe, ordenará al
     * interfaz de usuario presentar un mensaje que lo indique. Si el comando
     * tiene un error de formato (un criterio erróneo) ordenará al interfaz de
     * usuario presentar un mensaje que lo indique.</p>
     *
     * <p>
     * Formato: <code>nombreTabla elimina clave#valor</code> ó
     * <code>clave=valor</code></p>
     *
     * <p>
     * Si el usuario no introduce criterio, o éste es erróneo, muestra el
     * mensaje <code>Error en formato del comando</code>.</p>
     *
     * <p>
     * Puede usarse el método auxiliar {@link #interpretaCriterio(String[])}</p>
     *
     * <p>
     * Para facilitar la tarea, asumiremos que los criterios son cadenas
     * alfanuméricas sin espacios en blanco</p>
     *
     * @param palabras Un array con las diferentes palabras que el usuario ha
     * introducido en la línea de comandos, que conforman la orden completa, por
     * ejemplo: <code>{ "alumnos", "elimina", "apellido=garcia"}</code>
     */
    public void eliminar(String[] palabras) {

        if (palabras.length < 3) {
            System.out.println("Format incorrecte");
        } else {
            String[] palabras2 = palabras[2].split("=");
            String[] palabras3 = palabras[2].split("#");
            if (palabras2.length == 2 || palabras3.length == 2) {
                Criterio criterio = interpretaCriterio(palabras);
                tablas.get(palabras[0]).elimina(criterio);
            } else {
                System.out.println("Operador incorrecte");
            }
        }
    }

    /**
     * <p>
     * Gestiona el comando 'busca': para la tabla especificada por el usuario,
     * presenta por pantalla los detalles todas las filas que cumplan con el
     * criterio definido en el comando, invocando al método null     {@link upc.poo.ui.InterfazUsuario#presentaResultados(java.util.List, 
     * java.util.List) }.</p>
     *
     * <p>
     * Formato: <code>nombreTabla busca</code> ó
     * <code>nombretabla busca criterio=valor</code> (el valor a buscar debe
     * coincidir exacto) ó <code>nombreTabla busca criterio#valor</code> (el
     * valor debe estar contenido en la columna que se busque)</p>
     *
     * <p>
     * Si el usuario no introduce criterio, se mostrarán todas las filas de la
     * tabla.</p>
     *
     * <p>
     * Puedes usar el método auxiliar {@link #interpretaCriterio(String[])}</p>
     *
     * <p>
     * Para facilitar la tarea, asumiremos que los criterios son cadenas
     * alfanuméricas sin espacios en blanco</p>
     *
     * <p>
     * Si el usuario introduce un nombre de tabla que no existe, el método
     * mostrará el mensaje: <code>Tabla errónea</code></p>
     *
     * <p>
     * Las tablas se mostrarán con columnas de ancho fijo
     * ({@link #ANCHO_COLUMNA}) mostrando una cabecera y las diferentes filas.
     * Si el contenido de una columna no cabe en {@link #ANCHO_COLUMNA}, ésta se
     * cortará. Podéis usar el método
     * {@link InterfazUsuario#anchoFijo(String, int)}</p>
     *
     * <p>
     * Las tablas se mostrarán invocando al método null     {@link upc.poo.ui.InterfazUsuario#presentaResultados(java.util.List, 
     * java.util.List) }</p>
     *
     * <p>
     * Ejemplo:</p>
     * <pre>COMANDO&gt; canciones busca título#Bulería
     * =================================================
     * |Artista        |Título         |Duración       |
     * -------------------------------------------------
     * |David Bisbal   |Bulería        |2:33           |
     * |Camarón de la I|Bulerías inédit|3:15           |
     * |Pericón de Cádi|Papas Aliñá (Bu|1:44           |
     * =================================================
     * </pre>
     *
     * @param palabras Un array con las diferentes palabras que el usuario ha
     * introducido en la línea de comandos, que conforman la orden completa, por
     * ejemplo: <code>{ "alumnos", "busca", "apellido=Pérez"}</code>
     */
    public void buscar(String[] palabras) {
       Tabla tabla = tablas.get(palabras[0]);
       if(tabla != null){
        ArrayList cabeceras = tabla.getCabeceras();
         if (palabras.length < 3) {
             ArrayList ls = tabla.buscaTodo();
             iu.presentaResultados(cabeceras, ls);

         } else {
             String[] palabras2 = palabras[2].split("=");
             String[] palabras3 = palabras[2].split("#");
             if (palabras2.length == 2 || palabras3.length == 2) {
                 Criterio criterio = interpretaCriterio(palabras);
                 if(tabla != null){
                     ArrayList result = tabla.busca(criterio);
                     iu.presentaResultados(cabeceras, result);
                 }
                 else {
                     System.out.println("La taula no existeix");
                 }

             } else {
                 System.out.println("Operador incorrecte");
             }
         }
       }
       else{
           System.out.println("La taula no existeix");
       }

    }

    /**
     * Retorna una lista con los nombres de las claves de una tabla dada
     *
     * @param nombreTabla Nombre de la tabla
     * @return Una lista con las getCabeceras, o <code>null</code> si la tabla
     * no existe
     */
    public List<String> getCabeceras(String nombreTabla) {

        return tablas.get(nombreTabla).getCabeceras();
    }

    /**
     * <p>
     * Dado un array perteneciente a las palabras que acompañan a las órdenes
     * 'busca' o 'elimina', interpreta el tipo de criterio que acompañarían a
     * éstas (que coincidiría con el contenido de <code>palabras[2]</code>)</p>
     *
     * <p>
     * Si el número de <code>palabras</code> es menor a 3, devolvería
     * <code>null</code>.</p>
     *
     * <p>
     * El comando debe considerar el operador de criterio que el usuario
     * especifique: {@link #SIMB_IGUAL} o {@link #SIMB_CONTIENE}</p>
     *
     * <p>
     * Para facilitar la tarea, asumiremos que los criterios de búsqueda son
     * cadenas alfanuméricas sin espacios en blanco</p>
     *
     * @param palabras Array con las palabras pertenecientes a un comando, por
     * ejemplo: <code>{ "peliculas", "busca", "título#Padrino" }</code>
     * @return Una instancia de {@link Criterio} correspondiente a la
     * interpretación de <code>palabras[2]</code>.
     */
    private static Criterio interpretaCriterio(String[] palabras) { //Funciona 

        String[] palabras2 = palabras[2].split("=");
        int tipo = 1;

        if (palabras2.length == 1) {     //Si el operador es "=" la string serà de llargada dos, sino de un
            palabras2 = palabras[2].split(SIMB_CONTIENE);
            tipo = 0;
        }
        Criterio criterio = new Criterio(palabras2[0], tipo, palabras2[1]);

        return criterio;
    }

}
