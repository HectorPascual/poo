package upc.poo;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

import upc.poo.ui.InterfazUsuario;
import upc.poo.ui.Teclado;

/**
 * Clase con el método main().
 * <p><b>ESTA CLASE SE OS DA HECHA. NO DEBÉIS MODIFICARLA</b></p>
 */
public class Main implements ActionListener{

    private JTextArea textArea = new JTextArea(2,23);
    private JFrame frame = new JFrame("Enviador de comandos");
    private JButton boton = new JButton("Send");
    private JPanel panel = new JPanel();
    private int id = 1;
    private static String texto;
    private static Controlador controlador = new Controlador() ;
    private static InterfazUsuario iu = new InterfazUsuario(controlador);
    private static InicialGUI firstGUI = new InicialGUI();

    public static Controlador getControlador() {
        return controlador;
    }

    public static InterfazUsuario getIu() {
        return iu;
    }
    
    

    public static final void main(String[] args) throws IOException, InterruptedException {
        firstGUI.setVisible(true);
       
        //BateriaDePruebas.ejecutaTodas();
        boolean inicio = true;
        controlador.setIu(iu);
      /*  while(!firstGUI.isBotonPulsado()){
           Thread.sleep(1000);
        }*/
        System.out.println("Cargando información de sesiones anteriores...  ");
        while(!iu.procesaComando(inicio));
        for(int i=0;i<10;i++) System.out.println("*****");
        System.out.println("Gestor de base de datos 1.0");
        System.out.println("===========================\n");
        inicio = false;
       
    }
    
    public  void crearVentana() {

        panel.setLayout(new FlowLayout(FlowLayout.LEFT));

        System.out.println(textArea.getText());
	panel.add(textArea);
        panel.add(boton);
               
        boton.addActionListener(this);
	frame.add(panel);
	frame.setSize(350, 400);
	frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	frame.setVisible(true);

    }


    public static String getLine(){
        return texto;
    }
    public static void guardarComandos(String txt) throws FileNotFoundException, IOException{
        PrintWriter out = new PrintWriter(new FileWriter("ComandosGuardados.txt",true));
        out.println(txt);
        out.close();
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {

        texto = textArea.getText();
        JLabel label = new JLabel("Comanda " + id + ": "+texto);
        
        try {       // try-catch generado automatico ya que para escribir en un archivo se requiere manejo de excepciones
            new Main().guardarComandos(texto);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        id++;
        textArea.setText("");
        Dimension d = label.getPreferredSize();
        label.setPreferredSize(new Dimension(d.width+200,d.height));
        
        panel.add(label);
        frame.add(panel);
        
        frame.setSize(350, 400);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setVisible(true);
        try {
            if(iu.procesaComando(false)){
                frame.dispose();
                System.exit(0);
            }
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}

