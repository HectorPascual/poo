package upc.poo.ui;

import upc.poo.Main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import upc.poo.InicialGUI;

/**
 * Funciones auxiliares para el teclado.
 * <p><b>ESTA CLASE SE OS DA HECHA.</b></p>
 * <p>Asignando a atributo estático <code>desdeTeclado</code> el valor <code>true</code> 
 * la entrada deberá hacerse por teclado; asignándole el valor <code>false</code> el 
 * programa leerá los comandos desde el archivo input.txt presente en el directorio en 
 * el que se encuentra el proyecto NetBeans</p>
 */
public class Teclado {

    //// IMPORTANTE: esta clase se da hecha. No debéis modificar nada
    /**
     * Constructor privado que simplemente impide que la clase se instancie
     */
    private static boolean desdeTeclado = false;
    private static boolean desdeGUI = false;
    private static boolean comandosIniciales = true;
    private static int id = 0;

    public static void setDesdeTeclado(boolean desdeTeclado) {
        Teclado.desdeTeclado = desdeTeclado;
    }

    public static void setDesdeGUI(boolean desdeGUI) {
        Teclado.desdeGUI = desdeGUI;
    }

    public static void setComandosIniciales(boolean comandosIniciales) {
        Teclado.comandosIniciales = comandosIniciales;
    }
    
    private Teclado() {
        // Evita que la clase se instancie

        
    }

    /**
     * Lector de teclado
     */
    private static Scanner reader;

    /**
    ESTE ATRIBUTO ESTÁTICO, PUESTO A true HACE QUE EL PROGRAMA LEA DESDE 
    TECLADO; 
    PUESTO A false, HACE QUE EL PROGRAMA LEA DESDE EL ARCHVIVO input.txt QUE 
    DEBE ESTAR EN LA CARPETA QUE CONTIENE EL PROYECTO NETBEANS.
    POR TANTO PARA LEER DESDE ARCHIVO TENÉIS QUE ASIGNARLE EL VALOR false 
    */
    

    static {
        /*
        ESTE BLOQUE CONTIENE CODIGO PARA ENTRAR LOS COMANDOS POR TECLADO
        (OPCIÓN 1) O DESDE UN ARCHIVO DE TEXTO (OPCIÓN 2). CUANDO SE ELIJA UNA
        DE LAS DOS OPCIONES, EL CÓDIGO CORRESPONDIENTE A LA OTRA DEBE COMENTARSE
         */
        // OPCION 1: TODO DEBE SER ENTRADO POR TECLADO
        if(new InicialGUI().isGUI()) desdeGUI = true;
        if(new InicialGUI().isTeclat()) desdeTeclado = true;
        if (desdeTeclado) {
            reader = new Scanner(System.in);
        } /*
        OPCIÓN 2: EN LUGAR DE ENTRAR POR TECLADO EL PROGRAMA LEE LOS COMANDOS 
        DEL ARCHIVO input.txt QUE SE ENCUENTRA EN EL DIRECTORIO QUE CONTIENE 
        EL PROYECTO NETBEANS.
         */ else if(desdeTeclado == false && comandosIniciales == false) {
            try {
                reader = new Scanner(new FileInputStream(System.getProperty("user.dir") + File.separator
                        + "input.txt"));
            } catch (Exception ex) {
                System.out.println("Error al crear el canal de lectura.");
                System.exit(-1);
            }
          
        }
         else if(comandosIniciales){
              try {
                reader = new Scanner(new FileInputStream(System.getProperty("user.dir") + File.separator
                        + "ComandosGuardados.txt"));
            } catch (Exception ex) {
                System.out.println("Error al crear el canal de lectura.");
                System.exit(-1);
            }
         }
    }

    public static boolean isComandosIniciales() {
        return comandosIniciales;
    }

    /**
     * Lee una línea de texto del teclado y la retorna como un
     * <code>String</code>
     *
     * @return Una línea de texto leída del teclado.
     */
    public static String linea() {
        id++;
        System.out.flush();
        /*
        ESTE MÉTODO CONTIENE CODIGO PARA ENTRAR LOS COMANDOS POR TECLADO
        (OPCIÓN 1) O DESDE UN ARCHIVO DE TEXTO (OPCIÓN 2). CUANDO SE ELIJA UNA
        DE LAS DOS OPCIONES, EL CÓDIGO CORRESPONDIENTE A LA OTRA DEBE COMENTARSE
         */
        // OPCION 1: PARA LECTURA DESDE TECLADO
        if(new InicialGUI().isInputtxt() && id == 1){
            comandosIniciales = false;
            try {
                reader = new Scanner(new FileInputStream(System.getProperty("user.dir") + File.separator
                        + "input.txt"));
                
            } catch (Exception ex) {
                System.out.println("Error al crear el canal de lectura.");
                System.exit(-1);
            }
        }

        if(new InicialGUI().isGUI()) desdeGUI = true;
        if(new InicialGUI().isTeclat()){
            desdeTeclado = true;
            reader = new Scanner(System.in);

        }
        if(comandosIniciales){
            String nextLine = reader.nextLine();
            if (nextLine.equals("salir") && reader.hasNextLine() == false){
                comandosIniciales = false;
                return nextLine;
            }
            else if (nextLine.equals("salir") && reader.hasNextLine() == true){
                return "";
            }
            else{
                return nextLine;
            }
        }
        else if (desdeTeclado) {
            return reader.nextLine();
        } /*
        OPCION 2: PARA LECTURA DESDE ARCHIVO. SE LEEN LAS LINEAS DEL ARCHVIO Y
        SE PRESENTAN POR PANTALLA PARA VER QUÉ COMANDOS HA IDO EJECUTANDO EL 
        PROGRAMA
        
//        */ 
        
        else if(!desdeGUI){
            String linea = reader.nextLine();
            System.out.println(linea);
            return linea;
        }
        else {
            if(Main.getLine() != null) {
                return Main.getLine();
            }
            else {
                return "Vacio";
            }
        }
    }

    /**
     * Lee una línea de texto del teclado, y la retorna como un array de
     * <code>String</code>, en el que cada elemento se corresponde con una
     * palabra diferente. Por ejemplo, si el usuario introduce por teclado
     * <code>Esta es una frase cualquiera 123.</code> el método retornará un
     * array de String con el contenido:
     * <code>{"Esta", "es", "una", "frase", "cualquiera", "123"}</code>
     *
     * @return La línea de texto introducida mediante teclado, como un array de
     * <code>String</code>, en el que cada elemento se corresponde con una
     * palabra diferente.
     */
    public static String[] palabras() throws IOException {
        String linea = linea();
        if(desdeTeclado == false && new InicialGUI().isInputtxt() == false){
            System.out.println(linea);
        }
        else{
            Main.guardarComandos(linea);
        }
        return linea.split(" ");
    }
}
