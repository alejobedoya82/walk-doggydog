
package igu;


import java.awt.HeadlessException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import logica.Empleado;
import logica.Limpiar_txt;
import logica.Proceso;
//import logica.imgTabla;

//ruta_txt
public final class RegistrarEmpleados extends javax.swing.JFrame {
    Limpiar_txt lt = new Limpiar_txt();

    private String ruta_txt = "src//Archivo//Empleado.txt";

    Empleado empleado;
    Proceso proceso;

    int clic_tabla;

    
    public RegistrarEmpleados() {
        initComponents();
        proceso = new Proceso();

        try {
            cargar_txt();
            listarRegistro();
        } catch (Exception ex) {
            mensaje("No existe el archivo txt");
        }         
    }
    
    
    public void cargar_txt() {
        File ruta = new File(ruta_txt);
        try {

            FileReader fi = new FileReader(ruta);
            try (BufferedReader bu = new BufferedReader(fi)) {
                String linea = null;
                while ((linea = bu.readLine()) != null) {
                    StringTokenizer st = new StringTokenizer(linea, ",");
                    
                    empleado = new Empleado();
                    
                    empleado.setIdEmpleado(Integer.parseInt(st.nextToken()));
                    empleado.setNombreEmpleado(st.nextToken());
                    empleado.setApellidoEmpleado(st.nextToken());
                    empleado.setRolEmpleado(st.nextToken());
                    
                    proceso.agregarRegistro(empleado);
                }
            }
        } catch (IOException | NumberFormatException ex) {
            mensaje("Error al cargar archivo: " + ex.getMessage());
            System.out.println(ex.getMessage());
        }
    }
    
    
    public void grabar_txt() {
        FileWriter fw;
        PrintWriter pw;
        try {
            fw = new FileWriter(ruta_txt);
            pw = new PrintWriter(fw);

            for (int i = 0; i < proceso.cantidadRegistro(); i++) {
                empleado = (Empleado) proceso.obtenerRegistro(i);
                pw.println(String.valueOf(empleado.getIdEmpleado()+ "," + empleado.getNombreEmpleado()+ "," + empleado.getApellidoEmpleado()+ "," + empleado.getRolEmpleado()));
            }
            pw.close();

        } catch (IOException ex) {
            mensaje("Error al grabar archivo: " + ex.getMessage());
            System.out.println(ex.getMessage());
        }
    }
    
    
    public void ingresarRegistro(File ruta) {
        try {
            if (leerId()== -666) {
                mensaje("Ingresar ID entero");
            } else if (leerNombreEmpleado()== null) {
                mensaje("Ingresar Nombre");
            } else if (leerApellidoEmpleado()== null) {
                mensaje("Ingresar Apellido");
            } else if ("SELECCIONAR".equals(leerRol())) {
                mensaje("Ingresar Rol");
            } else {
                empleado = new Empleado(leerId(), leerNombreEmpleado(), leerApellidoEmpleado(), leerRol());
                if (proceso.buscaCodigo(empleado.getIdEmpleado()) != -1) {
                    mensaje("Este codigo ya existe");
                } else {
                    proceso.agregarRegistro(empleado);
                }

                grabar_txt();
                listarRegistro();
                lt.limpiar_texto(panel);
                limpiarCajasDeTexto();
            }
        } catch (Exception ex) {
            mensaje(ex.getMessage());
        }
    }
    
    
    public void modificarRegistro(File ruta) {
        try {
            if (leerId()== -666) {
                mensaje("Ingresar ID entero");
            } else if (leerNombreEmpleado()== null) {
                mensaje("Ingresar Nombre");
            } else if (leerApellidoEmpleado()== null) {
                mensaje("Ingresar Apellido");
            } else if ("SELECCIONAR".equals(leerRol())) {
                mensaje("Ingresar Rol");
            } else {
                int codigo = proceso.buscaCodigo(leerId());
                empleado = new Empleado(leerId(), leerNombreEmpleado(), leerApellidoEmpleado(), leerRol());

                if (codigo == -1) {
                    proceso.agregarRegistro(empleado);
                } else {
                    proceso.modificarRegistro(codigo, empleado);
                }
                grabar_txt();
                listarRegistro();
                lt.limpiar_texto(panel);
                limpiarCajasDeTexto();
            }
        } catch (Exception ex) {
            mensaje(ex.getMessage());
        }
    }
    
    
    public void eliminarRegistro() {
        try {
            if (leerId()== -666) {
                mensaje("Ingrese ID entero");
            } else {
                int codigo = proceso.buscaCodigo(leerId());
                if (codigo == -1) {
                    mensaje("ID no existe");
                } else {
                    int s = JOptionPane.showConfirmDialog(null, "Esta seguro de eliminar esta fila", "Si/No", 0);
                    if (s == 0) {
                        proceso.eliminarRegistro(codigo);

                        grabar_txt();
                        listarRegistro();
                        lt.limpiar_texto(panel);
                        limpiarCajasDeTexto();
                    }
                }
            }
        } catch (HeadlessException ex) {
            mensaje(ex.getMessage());
        }
    }
    
    
    public void listarRegistro() {
        DefaultTableModel dt = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        dt.addColumn("ID");
        dt.addColumn("NOMBRE");
        dt.addColumn("APELLIDO");
        dt.addColumn("CARGO");

        //tabla.setDefaultRenderer(Object.class, new imgTabla());

        Object fila[] = new Object[dt.getColumnCount()];
        for (int i = 0; i < proceso.cantidadRegistro(); i++) {
            empleado = proceso.obtenerRegistro(i);
            fila[0] = empleado.getIdEmpleado();
            fila[1] = empleado.getNombreEmpleado();
            fila[2] = empleado.getApellidoEmpleado();
            fila[3] = empleado.getRolEmpleado();
            dt.addRow(fila);
        }
        tabla.setModel(dt);
        tabla.setRowHeight(30);
         // Obtener la cantidad de registros y mostrarla en un componente de tu interfaz
        int cantidadRegistros = proceso.cantidadRegistro();
        txtTamanio.setText(" Registros Actuales: " + cantidadRegistros);
       // txtTamanio.setText( String.valueOf(cantidadRegistros));               esta forma comentada muestra solo el numero
    }
    
    
    public int leerId() {
        try {
            int codigo = Integer.parseInt(txtId.getText().trim());
            return codigo;
        } catch (NumberFormatException ex) {
            return -666;
        }
    }
    
    
    public String leerNombreEmpleado() {
        try {
            String nombre = txtNombreEmpleado.getText().trim().replace(" ", "_");
            return nombre;
        } catch (Exception ex) {
            return null;
        }
    }
    
    
    public String leerApellidoEmpleado() {
        try {
            String apellido = (txtApellidoEmpleado.getText().trim());
            return apellido;
        } catch (Exception ex) {
            return null;
        }
    }
    
    
    public String leerRol() {
        try {
            String rol = cmbRol.getSelectedItem().toString().trim();
            return rol;
        } catch (Exception ex) {
            return null;
        }
    }
    
    
    public void mensaje(String texto) {
        JOptionPane.showMessageDialog(null, texto);
    }
    
    
    public void limpiarCajasDeTexto(){
        txtNombreEmpleado.setText("");
        txtApellidoEmpleado.setText("");
        txtId.setText("");
        cmbRol.setSelectedIndex(0);
    }
    
    
    @SuppressWarnings("unchecked")
 
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        txtId = new javax.swing.JTextField();
        txtNombreEmpleado = new javax.swing.JTextField();
        btnAtras = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        cmbRol = new javax.swing.JComboBox<>();
        txtApellidoEmpleado = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabla = new javax.swing.JTable();
        btnEditar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtTamanio = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel7.setText("NOMBRE ");

        btnAtras.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnAtras.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/botonatras.png"))); // NOI18N
        btnAtras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAtrasActionPerformed(evt);
            }
        });

        btnLimpiar.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnLimpiar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/botonlimpiar.png"))); // NOI18N
        btnLimpiar.setText("Limpiar");
        btnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarActionPerformed(evt);
            }
        });

        btnGuardar.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/botoncrear.png"))); // NOI18N
        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/walkdoggyPNG.png"))); // NOI18N

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/texto_registrarempleado.png"))); // NOI18N

        jLabel8.setText("APELLIDO");

        jLabel10.setText("# ID");

        jLabel12.setText("CARGO");

        cmbRol.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "SELECCIONAR", "EMPLEADO", "PASEADOR" }));
        cmbRol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbRolActionPerformed(evt);
            }
        });

        tabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "# ID", "NOMBRE ", "APELLIDO", "CARGO"
            }
        ));
        tabla.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabla);

        btnEditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/botonactualizar.png"))); // NOI18N
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });

        btnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/botoneliminar.png"))); // NOI18N
        btnEliminar.setText("jButton2");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Perpetua Titling MT", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 204, 204));
        jLabel3.setText("FORMULARIO");

        jLabel4.setFont(new java.awt.Font("Perpetua Titling MT", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 204, 204));
        jLabel4.setText("TAMAÑO TABLA");

        txtTamanio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTamanioActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(51, 51, 51)
                        .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnLimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnAtras))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 636, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(cmbRol, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtApellidoEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtNombreEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(147, 147, 147)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel4)
                                    .addComponent(txtTamanio, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 513, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel3))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(520, 520, 520)
                        .addComponent(jLabel1)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(158, 158, 158)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 17, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(btnLimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(79, 79, 79)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtNombreEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtTamanio, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(310, 310, 310))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGap(16, 16, 16)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtApellidoEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(18, 18, 18)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(cmbRol, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(52, 52, 52)
                                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 389, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addComponent(btnAtras, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(24, 24, 24))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(80, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(65, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(10, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    private void btnAtrasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtrasActionPerformed
        JPanel panel = new JPanel();
        setVisible(false);
        Principal princ = new Principal();
        princ.setVisible(true);
        princ.setLocationRelativeTo(null);   
        panel.add(btnAtras);
        add(panel);    
    }//GEN-LAST:event_btnAtrasActionPerformed

    
    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
      limpiarCajasDeTexto();
    }//GEN-LAST:event_btnLimpiarActionPerformed

    
    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        File ruta = new File(ruta_txt);
        this.ingresarRegistro( ruta);
   
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        File ruta = new File(ruta_txt);
        this.modificarRegistro(ruta);
    }//GEN-LAST:event_btnEditarActionPerformed

     
    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        this.eliminarRegistro();
    }//GEN-LAST:event_btnEliminarActionPerformed

    
    
    private void tablaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaMouseClicked
       clic_tabla = tabla.rowAtPoint(evt.getPoint());

        int codigo = (int) tabla.getValueAt(clic_tabla, 0);
        String nombre = "" + tabla.getValueAt(clic_tabla, 1);
        String apellido = "" + tabla.getValueAt(clic_tabla, 2);
        String rol = "" + tabla.getValueAt(clic_tabla, 3);

        txtId.setText(String.valueOf(codigo));
        txtNombreEmpleado.setText(nombre);
        txtApellidoEmpleado.setText(apellido);
        cmbRol.setSelectedItem(rol);    
    }//GEN-LAST:event_tablaMouseClicked

    
    
    
    private void cmbRolActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbRolActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbRolActionPerformed

    private void txtTamanioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTamanioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTamanioActionPerformed

     
   


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btnAtras;
    public javax.swing.JButton btnEditar;
    public javax.swing.JButton btnEliminar;
    public javax.swing.JButton btnGuardar;
    public javax.swing.JButton btnLimpiar;
    public javax.swing.JComboBox<String> cmbRol;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tabla;
    public javax.swing.JTextField txtApellidoEmpleado;
    public javax.swing.JTextField txtId;
    public javax.swing.JTextField txtNombreEmpleado;
    private javax.swing.JTextField txtTamanio;
    // End of variables declaration//GEN-END:variables
    private JPanel panel = new JPanel();  
}
