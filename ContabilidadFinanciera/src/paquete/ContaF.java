package paquete;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import java.sql.*;

public class ContaF extends JFrame {
    
    public ContaF() {
        try{
        Class.forName("com.mysql.jdbc.Driver");
        cone=DriverManager.getConnection("jdbc:mysql://"+ContaF.host+"/"+ContaF.bd+"?user="+ContaF.usuario+"&password="+ContaF.password);
        St=cone.createStatement();
        Rs=St.executeQuery("select * from cuentas");
        }catch(Exception e){CambiarDatos();}
        initComponents();
        setLocationRelativeTo(null);   
    }
    
    static String host="localhost",bd="contapoliza",usuario="Conta",password="conta";
    static boolean error=false;
    static Connection cone;
    static Statement St;
    static ResultSet Rs;
    static Cuentass c;
    static Transacciones t;
    static Saldos s;
    
    public void CambiarDatos(){
        host=JOptionPane.showInputDialog("Localizacion del Host",host);
        bd=JOptionPane.showInputDialog("Base de Datos a Utilizar",bd);
        usuario=JOptionPane.showInputDialog("Usuario de la BD",usuario);
        password=JOptionPane.showInputDialog("Contraseña del Usuario",password);
        try{
        Class.forName("com.mysql.jdbc.Driver");
        cone=DriverManager.getConnection("jdbc:mysql://"+ContaF.host+"/"+ContaF.bd+"?user="+ContaF.usuario+"&password="+ContaF.password);
        St=cone.createStatement();
        }catch(Exception e){JOptionPane.showMessageDialog(this,e+"en CambiarDatos");}
        Transacciones.updateCon();
        Saldos.updateCon();
    
    }

    public static void coneccionBD(String s1, String s2){try{
        if(s1.equals("update"))    St.executeUpdate(s2);
        else                         Rs=St.executeQuery(s2);
    }catch(Exception e){error=true;
    if((e+"").contains("IntegrityConstraintViolation"))
        JOptionPane.showMessageDialog(null,"Cuenta Utilizada Actualmente");
    else JOptionPane.showMessageDialog(null,e+"\nen Coneccion a Base de Datos");}}
    
    

    private void initComponents() {
        final JTabbedPane pestanas = new JTabbedPane();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("CONTABILIDAD FINANCIERA - POLIZAS");
        setBounds(new Rectangle(0, 0, 700, 540));
        setIconImage(getIconImage());
        getContentPane().setLayout(null);
        pestanas.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
        pestanas.setMinimumSize(new java.awt.Dimension(695, 517));
        pestanas.setPreferredSize(new java.awt.Dimension(695, 517));
        getContentPane().add(pestanas);
        pestanas.setBounds(0, -3, 695, 517);
        Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-711)/2, (screenSize.height-549)/2, 711, 549);
         setDefaultLookAndFeelDecorated(true);
         new Thread(new Runnable() {
		        public void run(){
                            c=new Cuentass();
                            t=new Transacciones();
                            s=new Saldos();
                            pestanas.addTab("Cuentas",null,c,"Ventana de Cuentas");
                            pestanas.addTab("Polizas",null,t,"Ventana de Polizas");
                            pestanas.addTab("Edo. Financiero",null,s,"Ventana de Saldos");
                            pestanas.addKeyListener(new KeyAdapter() {
                 public void keyPressed(KeyEvent e) {
    		if(KeyEvent.getKeyText(e.getKeyChar()).equals("Escape")){
    			int s=JOptionPane.showOptionDialog(null, "Cambiar de base de datos?\n      (o \"ESC\")",
                                "SALIR SISTEMA", 1, 0, null, (new String[]{"SI","NO"}),"SI");
    			if(s==0)CambiarDatos();
    		}
    	}
    });
		        	} }).start();
         addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                pestanas.setBounds(2,0,getWidth()-19,getHeight()-39);
            }
        });
    }

    public static void main(String args[]) {
        EventQueue.invokeLater(new Runnable() {
        public void run(){ new ContaF().setVisible(true);} });
    }

}
