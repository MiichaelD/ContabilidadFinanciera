package paquete;
import java.util.Calendar.*;
import javax.swing.table.*;
import javax.swing.*;
import java.sql.*;


public final class Transacciones extends javax.swing.JPanel {

    public Transacciones() {
        initComponents();
        updateCon();
        updateTabla();
        updateCombo();
        
    }
      DefaultTableModel model;
      DefaultComboBoxModel modelCodigo;
     static Connection cone1;
     static Statement St1;
     static ResultSet Rs1;
     boolean error=false;
     
      public static void updateCon(){
     try{
         Class.forName("com.mysql.jdbc.Driver");
        cone1=DriverManager.getConnection("jdbc:mysql://"+ContaF.host+"/"+ContaF.bd+"?user="+ContaF.usuario+"&password="+ContaF.password);
        St1=cone1.createStatement();
        }catch(Exception e){System.out.println(e);}
     }
     
     
    public  void updateTabla(){
        model=(DefaultTableModel)(new DefaultTableModel(new Object [][] {},new String [] {"No.Poliza","Concepto",
            "Dinero","Cuenta Cargar","Cuenta Abonar","Fecha"}));
        ContaF.coneccionBD("else","select IdTransaccion,concepto,dinero,CuentaCargo,CuentaAbono,a.nomcuenta as \"Cuenta Cargar\",b.nomcuenta as "
                + "\"Cuenta Abonar\",fecha, actualizada from cuentas as a inner join ( transacciones inner join cuentas as b on "
                + "b.IdCuenta=CuentaAbono) on a.IdCuenta=CuentaCargo order by IdTransaccion;");
        try{
        while(ContaF.Rs.next()){
            
           model.addRow(new Object[]{ContaF.Rs.getString("IdTransaccion"),ContaF.Rs.getString("Concepto"),ContaF.Rs.getString("dinero"),
               ContaF.Rs.getString("Cuenta Cargar"),ContaF.Rs.getString("Cuenta Abonar"),ContaF.Rs.getString("Fecha")});
           if(ContaF.Rs.getString("actualizada").equals("0"))
               updateSaldos("tabla");
           
         }jTable1.setModel(model);
        }catch(Exception e){
            JOptionPane.showMessageDialog(
                    this,
                    e+"\nen Transacciones: updateTabla");}
        }

    public void coneBDLocal(String s1, String s2){
        try{
        if(s1.equals("update"))    St1.executeUpdate(s2);
        else                       Rs1=St1.executeQuery(s2);
    }catch(Exception e){error=true;JOptionPane.showMessageDialog(this,e+"\nen Transacciones ConeBDL");}
          }
    
    public  void updateSaldos(String s1){try{
            String money=null,cc=null,ca=null;
        if(s1.equals("tabla")){//lee de otra consulta
            money=ContaF.Rs.getString("dinero");
            cc=ContaF.Rs.getString("CuentaCargo");
            ca=ContaF.Rs.getString("CuentaAbono");
            coneBDLocal("update","update transacciones set actualizada='1' where idtransaccion="+ContaF.Rs.getString("IdTransaccion")+";");
        }
        else if(s1.equals("agregar")){// lee de interfaz
             money=tfdinero.getText();
            cc=cbcc.getSelectedItem().toString().substring(0,4);
            ca=cbca.getSelectedItem().toString().substring(0,4);
             }
        coneBDLocal("update","update cuentas set saldo=(saldo+"+money+") where IdCuenta="+cc+";");
        coneBDLocal("update","update cuentas set saldo=(saldo-"+money+") where IdCuenta="+ca+";");
        
        }catch(Exception e){JOptionPane.showMessageDialog(this,e+"\nen Transacciones: updateSaldos");}
        
        }
    
    public  void updateCombo(){
        cbcc.enable(true);
         try{
         cbcc.setModel(new DefaultComboBoxModel(new String[]{"Seleccionar Cuenta"}));
         cbca.setModel(new DefaultComboBoxModel(new String[]{"Seleccionar Cuenta"}));
        ContaF.coneccionBD("else","select IdCuenta,NomCuenta from cuentas;"); 
        while(ContaF.Rs.next()){
           cbcc.addItem(ContaF.Rs.getString("IdCuenta")+"-"+ContaF.Rs.getString("NomCuenta"));
           }
        ContaF.coneccionBD("else","select IdCuenta,NomCuenta from cuentas where idcuenta<1200 and saldo<>0;"); 
        while(ContaF.Rs.next()){
        cbca.addItem(ContaF.Rs.getString("IdCuenta")+"-"+ContaF.Rs.getString("NomCuenta"));   
         }
        }catch(Exception e){JOptionPane.showMessageDialog(this,e+"\nen Transacciones: updateCombo");}   
    }

    public void Agregar(){
        
        if(tfconcepto.getText().equals("")||tfconcepto.getText().equals("Campo Vacio")||
                tfdinero.getText().equals("")||tfdinero.getText().equals("0")||cbca.getSelectedItem().toString().equals("Seleccionar Cuenta")||
                cbcc.getSelectedItem().toString().equals("Seleccionar Cuenta")||cbca.getSelectedItem().toString().equals(cbcc.getSelectedItem().toString())){
            
              if(tfdinero.getText().equals("")||tfdinero.getText().equals("0")){
                    tfdinero.setText("0");      tfdinero.requestFocus();}    
              if(tfconcepto.getText().equals("")||tfconcepto.getText().equals("Campo Vacio")){
                    tfconcepto.setText("Campo Vacio");      tfconcepto.requestFocus();}
              if(cbcc.getSelectedItem().toString().equals("Seleccionar Cuenta"))
                  JOptionPane.showMessageDialog(this,"Seleccionar Cuenta a Cargar");
              else if(cbca.getSelectedItem().toString().equals("Seleccionar Cuenta"))
                  JOptionPane.showMessageDialog(this,"Seleccionar Cuenta a Abonar");
              else if(cbca.getSelectedItem().toString().equals(cbcc.getSelectedItem().toString())){
                    JOptionPane.showMessageDialog(this,"No puede Cargar y Abonar a la misma CUENTA,\nseleccione diferentes cuentas");}
        }   
                
        else{ 
            ContaF.error=false;
            ContaF.coneccionBD("update","insert into transacciones values (null,'"+tfconcepto.getText()+"',"+tfdinero.getText()+","
                 +cbcc.getSelectedItem().toString().substring(0,4)+","+cbca.getSelectedItem().toString().substring(0,4)+",current_timestamp(),'1');");
                 
        if(!ContaF.error){ 
            updateSaldos("agregar");    updateTabla();       tfconcepto.requestFocus();
            tfconcepto.setText(""); tfdinero.setText(""); cbcc.setSelectedIndex(0);
            cbca.setSelectedIndex(0);
         }
        } 
        ContaF.s.updateSaldos();
        ContaF.s.updateTabla();
    }
    
    public void Eliminar(){
        if(ffnumero.getText().equals("")||ffnumero.getText().equals("Campo Vacio")){
        ffnumero.setText("Campo Vacio"); JOptionPane.showMessageDialog(this,"Insertar Numero de Poliza a Eliminar");
        ffnumero.requestFocus();
        }
        else{ try{        String t0,t1,t2;
            error=false;
            coneBDLocal("else","select * from transacciones where idtransaccion="+ffnumero.getText()+";");
            if(!error){              Rs1.next();
              if(Rs1.getString("actualizada").equals("1")){
                     t0=Rs1.getString("dinero");
                     t1=Rs1.getString("CuentaCargo");
                     t2=Rs1.getString("CuentaAbono");
                     ContaF.coneccionBD("update","update cuentas set saldo=(saldo-"+t0+") where IdCuenta="+t1+";");
                     ContaF.coneccionBD("update","update cuentas set saldo=(saldo+"+t0+") where IdCuenta="+t2+";");}
              ContaF.coneccionBD("update","delete from transacciones where IdTransaccion="+ffnumero.getText()+";");
            }
        } catch(Exception e){JOptionPane.showMessageDialog(this,e+"\nen Transacciones: Eliminar");}     
        if(!ContaF.error){
            updateTabla();     ffnumero.setText("");     ffnumero.requestFocus();}
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        tfconcepto = new javax.swing.JTextField();
        tfdinero = new javax.swing.JFormattedTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        cbcc = new javax.swing.JComboBox();
        cbca = new javax.swing.JComboBox();
        jbaceptar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jbEliminar = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jbActualizar = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        ffnumero = new javax.swing.JFormattedTextField();

        jLabel1.setText("Polizas");

        jLabel2.setText("Agregar:");

        jLabel3.setText("Concepto");

        tfdinero.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        tfdinero.setNextFocusableComponent(cbcc);

        jLabel4.setText("Dinero");

        jLabel5.setText("Cuenta a Cargar:");

        jLabel6.setText("Cuenta a Abonar:");

        cbcc.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Seleccionar Cuenta" }));
        cbcc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbccActionPerformed(evt);
            }
        });

        cbca.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Seleccionar Cuenta" }));
        cbca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbcaActionPerformed(evt);
            }
        });
        cbca.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cbcaKeyPressed(evt);
            }
        });

        jbaceptar.setText("Aceptar");
        jbaceptar.setFocusable(false);
        jbaceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbaceptarActionPerformed(evt);
            }
        });

        jTable1.setAutoCreateRowSorter(true);
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No.Poliza", "Descripcion", "Dinero", "Cuenta a Cargar", "Cuenta a Abonar", "Fecha"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Double.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jTable1.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        jTable1.setFillsViewportHeight(true);
        jScrollPane1.setViewportView(jTable1);

        jLabel7.setText("Actualizar:");

        jLabel8.setText("Eliminar:");

        jbEliminar.setText("Eliminar");
        jbEliminar.setFocusable(false);
        jbEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbEliminarActionPerformed(evt);
            }
        });

        jLabel9.setText("Numero Poliza");

        jbActualizar.setText("Actualizar");
        jbActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbActualizarActionPerformed(evt);
            }
        });
        jbActualizar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jbActualizarKeyPressed(evt);
            }
        });

        ffnumero.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        ffnumero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ffnumeroActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jSeparator1, javax.swing.GroupLayout.DEFAULT_SIZE, 639, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 639, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(119, 119, 119)
                        .addComponent(jLabel1))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(30, 30, 30))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jbaceptar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addGap(18, 18, 18)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jbActualizar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 52, Short.MAX_VALUE)
                                .addComponent(jLabel8)
                                .addGap(12, 12, 12)
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(ffnumero, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jbEliminar)
                                .addGap(4, 4, 4))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel3)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(tfconcepto, javax.swing.GroupLayout.DEFAULT_SIZE, 351, Short.MAX_VALUE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel4)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(tfdinero, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel5)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(cbcc, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(10, 10, 10)
                                        .addComponent(jLabel6)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(cbca, 0, 198, Short.MAX_VALUE)))))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(tfconcepto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfdinero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbaceptar)
                    .addComponent(jLabel6)
                    .addComponent(cbcc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel8)
                        .addComponent(jbEliminar)
                        .addComponent(jLabel9)
                        .addComponent(ffnumero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel7)
                        .addComponent(jbActualizar)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 293, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cbccActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbccActionPerformed
       
    }//GEN-LAST:event_cbccActionPerformed

    private void jbActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbActualizarActionPerformed
    updateTabla();
    updateCombo();
    }//GEN-LAST:event_jbActualizarActionPerformed

    private void jbaceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbaceptarActionPerformed
     Agregar();
    }//GEN-LAST:event_jbaceptarActionPerformed

    private void cbcaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbcaActionPerformed
    
    }//GEN-LAST:event_cbcaActionPerformed

    private void ffnumeroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ffnumeroActionPerformed
    Eliminar();
    }//GEN-LAST:event_ffnumeroActionPerformed

    private void jbActualizarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jbActualizarKeyPressed
     if(evt.getKeyCode()==10)
     { updateTabla();updateCombo();}

    }//GEN-LAST:event_jbActualizarKeyPressed

    private void cbcaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cbcaKeyPressed
     if(evt.getKeyCode()==10) Agregar();
    }//GEN-LAST:event_cbcaKeyPressed

    private void jbEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbEliminarActionPerformed
    Eliminar();
    }//GEN-LAST:event_jbEliminarActionPerformed
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox cbca;
    private javax.swing.JComboBox cbcc;
    private javax.swing.JFormattedTextField ffnumero;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private static javax.swing.JTable jTable1;
    private javax.swing.JButton jbActualizar;
    private javax.swing.JButton jbEliminar;
    private javax.swing.JButton jbaceptar;
    private javax.swing.JTextField tfconcepto;
    private javax.swing.JFormattedTextField tfdinero;
    // End of variables declaration//GEN-END:variables
}