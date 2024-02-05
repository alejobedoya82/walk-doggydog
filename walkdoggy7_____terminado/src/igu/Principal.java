
package igu;

import javax.swing.JPanel;

public class Principal extends javax.swing.JFrame {
    

    public Principal() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        btnRegistroEmpleados = new javax.swing.JButton();
        btnRegistroClientes = new javax.swing.JButton();
        btnCitas = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/sieteL.png"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 636, Short.MAX_VALUE)
        );

        btnRegistroEmpleados.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/texto_registrarempleado.png"))); // NOI18N
        btnRegistroEmpleados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistroEmpleadosActionPerformed(evt);
            }
        });

        btnRegistroClientes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/textogestiondeclientes.png"))); // NOI18N
        btnRegistroClientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistroClientesActionPerformed(evt);
            }
        });

        btnCitas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/txtgestiondecitas.png"))); // NOI18N
        btnCitas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCitasActionPerformed(evt);
            }
        });

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/txtbienvenido.png"))); // NOI18N

        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/walkdoggyPNG.png"))); // NOI18N
        jLabel12.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(173, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnRegistroClientes, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGap(37, 37, 37)
                                        .addComponent(btnRegistroEmpleados))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGap(63, 63, 63)
                                        .addComponent(btnCitas)))
                                .addGap(38, 38, 38)))
                        .addGap(41, 41, 41))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(29, 29, 29)))
                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 464, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(50, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 378, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(73, 73, 73)
                        .addComponent(btnRegistroEmpleados)
                        .addGap(18, 18, 18)
                        .addComponent(btnRegistroClientes)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnCitas)))
                .addGap(32, 32, 32))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    
    private void btnCitasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCitasActionPerformed
        JPanel panel = new JPanel();
        setVisible(false);
        RegistrarCitas ci = new RegistrarCitas();
        ci.setVisible(true);
        ci.setLocationRelativeTo(null);
        panel.add(btnCitas);
        add(panel);
    }//GEN-LAST:event_btnCitasActionPerformed

    private void btnRegistroClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistroClientesActionPerformed
        JPanel panel = new JPanel();
        setVisible(false);
        RegistrarClientes rc = new RegistrarClientes();
        rc.setVisible(true);
        rc.setLocationRelativeTo(null);
        panel.add(btnRegistroClientes);
        add(panel);
        
    }//GEN-LAST:event_btnRegistroClientesActionPerformed

    private void btnRegistroEmpleadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistroEmpleadosActionPerformed
        JPanel panel = new JPanel();
        setVisible(false);
        RegistrarEmpleados re = new RegistrarEmpleados();
        re.setVisible(true);
        re.setLocationRelativeTo(null);
        panel.add(btnRegistroEmpleados);
        add(panel);
       
    }//GEN-LAST:event_btnRegistroEmpleadosActionPerformed

  

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCitas;
    private javax.swing.JButton btnRegistroClientes;
    public static javax.swing.JButton btnRegistroEmpleados;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    // End of variables declaration//GEN-END:variables
    private JPanel panel = new JPanel();
}
