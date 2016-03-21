package paquete;
import java.util.Calendar.*;
import javax.swing.table.*;
import javax.swing.*;
import java.sql.*;
public class Saldos extends javax.swing.JPanel {
    public Saldos() {
        initComponents();
        updateCon();
        updateTabla();
        
    }
    
    DefaultTableModel model;
    static Connection cone1;
    static Statement St1;
     String debe=null,haber=null;
     
     public static void updateCon(){
     try{
         Class.forName("com.mysql.jdbc.Driver");
        cone1=DriverManager.getConnection("jdbc:mysql://"+ContaF.host+"/"+ContaF.bd+"?user="+ContaF.usuario+"&password="+ContaF.password);
        St1=cone1.createStatement();
        }catch(Exception e){System.out.println(e);}
     }
    
public void updateTabla(){
        model=(DefaultTableModel)(new DefaultTableModel(new Object [][] {},new String [] {"No.Cuenta","Nombre Cuenta",
            "Debe","Haber"}));
        ContaF.coneccionBD("else","select * from cuentas where saldo<>0;");
        try{
        while(ContaF.Rs.next()){
            if(ContaF.Rs.getString("saldo").contains("-")){
                debe=""; haber=""+(Double.parseDouble(ContaF.Rs.getString("saldo"))*-1);}
            else{debe=ContaF.Rs.getString("saldo");haber="";}
           model.addRow(new Object[]{ContaF.Rs.getString("IdCuenta"),ContaF.Rs.getString("NomCuenta"),debe,haber});
         }jTable1.setModel(model);
        }catch(Exception e){JOptionPane.showMessageDialog(this,e+"\nen Saldos: updateTabla");}
        }

        public void updateSaldos(){ try{
         ContaF.coneccionBD("else","select IdTransaccion,dinero,CuentaCargo,CuentaAbono from transacciones where actualizada='0';");
        while(ContaF.Rs.next()){
           String money=null,cc=null,ca=null;
            money=ContaF.Rs.getString("dinero");
            cc=ContaF.Rs.getString("CuentaCargo");
            ca=ContaF.Rs.getString("CuentaAbono");
        coneBDLocal("update transacciones set actualizada='1' where idtransaccion="+ContaF.Rs.getString("IdTransaccion")+";");
        coneBDLocal("update cuentas set saldo=(saldo+"+money+") where IdCuenta="+cc+";");
        coneBDLocal("update cuentas set saldo=(saldo-"+money+") where IdCuenta="+ca+";");
         }
        }catch(Exception e){JOptionPane.showMessageDialog(this,e+"\nen Saldos: updateSaldos");}
        }
        
        public void coneBDLocal(String s2){
        try{St1.executeUpdate(s2);}catch(Exception e){JOptionPane.showMessageDialog(this,e+"\nen Saldos: coneBDL");}
          }
 
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        jLabel1.setText("Saldos");

        jButton1.setText("Actualizar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jButton1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jButton1KeyPressed(evt);
            }
        });

        jTable1.setAutoCreateRowSorter(true);
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Cuenta", "Debe", "Haber"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jTable1.setFillsViewportHeight(true);
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 639, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(121, 121, 121)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 418, Short.MAX_VALUE)
                        .addComponent(jButton1)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 433, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        updateSaldos();updateTabla();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButton1KeyPressed
         if(evt.getKeyCode()==10){updateSaldos();updateTabla();}
    }//GEN-LAST:event_jButton1KeyPressed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
