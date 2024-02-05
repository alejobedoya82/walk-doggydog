
package igu;

import Buscar.Buscador;
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
import logica.Cliente;
import logica.Limpiar_txt;
import logica.ProcesoCliente;


public final class RegistrarClientes extends javax.swing.JFrame {
    Limpiar_txt lt = new Limpiar_txt();

    private String ruta_txt = "src//Archivo//Cliente.txt";
    Cliente cliente;
    ProcesoCliente procesocliente;
    int clic_tabla;
    
   
   
    public RegistrarClientes() {
        initComponents();
        
        procesocliente = new ProcesoCliente();

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
            BufferedReader bu = new BufferedReader(fi);

            String linea = null;
            while ((linea = bu.readLine()) != null) {
                StringTokenizer st = new StringTokenizer(linea, ",");
                cliente = new Cliente();
                
                cliente.setIdCliente(Integer.parseInt(st.nextToken()));
                cliente.setNombreCliente(st.nextToken());
                cliente.setMascota(st.nextToken());
                cliente.setRaza(st.nextToken());       
                cliente.setAtencion(st.nextToken());
                
                procesocliente.agregarRegistro(cliente);
            }
            bu.close();
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

            for (int i = 0; i < procesocliente.cantidadRegistro(); i++) {
                cliente = (Cliente) procesocliente.obtenerRegistro(i);
                pw.println(String.valueOf(cliente.getIdCliente()+ "," + cliente.getNombreCliente()+ "," + cliente.getMascota()+ "," + cliente.getRaza()+ "," + cliente.getAtencion()));
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
            } else if (leerNombreC()== null) {
                mensaje("Ingresar Nombre");
            } else if (leerMascota()== null) {
                mensaje("Ingresar Mascota");
                } else if (leerRaza()== null) {
                mensaje("Ingresar Raza");

            } else if ("SELECCIONAR".equals(leerAtencion())) {
                mensaje("Ingresar una Opción");
            } else {

                cliente = new Cliente(leerId(), leerNombreC(), leerMascota(),leerRaza(), leerAtencion());
                if (procesocliente.buscaCodigo(cliente.getIdCliente()) != -1) {
                    mensaje("Este codigo ya existe");
                } else {
                    procesocliente.agregarRegistro(cliente);
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
                } else if (leerNombreC()== null) {
                    mensaje("Ingresar Nombre");
                } else if (leerMascota()== null) {
                    mensaje("Ingresar Mascota");
                    } else if (leerRaza()== null) {
                    mensaje("Ingresar Raza");

                } else if ("SELECCIONAR".equals(leerAtencion())) {
                    mensaje("Ingresar una Opción");
            } else {
                int codigo = procesocliente.buscaCodigo(leerId());
                cliente = new Cliente(leerId(), leerNombreC(), leerMascota(),leerRaza(), leerAtencion());

                if (codigo == -1) {
                    procesocliente.agregarRegistro(cliente);
                } else {
                    procesocliente.modificarRegistro(codigo, cliente);
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
                int codigo = procesocliente.buscaCodigo(leerId());
                if (codigo == -1) {
                    mensaje("ID no existe");
                } else {
                    int s = JOptionPane.showConfirmDialog(null, "Esta seguro de eliminar esta fila", "Si/No", 0);
                    if (s == 0) {
                        procesocliente.eliminarRegistro(codigo);

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
        dt.addColumn("# ID");
        dt.addColumn("NOMBRE PROPIETARIO");
        dt.addColumn("NOMBRE MASCOTA");
        dt.addColumn("RAZA");
        dt.addColumn("ATENCIÓN ESPECIAL");

        Object fila[] = new Object[dt.getColumnCount()];
        for (int i = 0; i < procesocliente.cantidadRegistro(); i++) {
            cliente = procesocliente.obtenerRegistro(i);
            fila[0] = cliente.getIdCliente();
            fila[1] = cliente.getNombreCliente();
            fila[2] = cliente.getMascota();
            fila[3] = cliente.getRaza();
            fila[4] = cliente.getAtencion();
            dt.addRow(fila);
        }
        tablaCliente.setModel(dt);
        tablaCliente.setRowHeight(30);
        
        // Obtener la cantidad de registros y mostrarla en un componente de tu interfaz
        int cantidadRegistros = procesocliente.cantidadRegistro();
        txtTamanio.setText(" Registros Actuales: " + cantidadRegistros);
       // txtTamanio.setText( String.valueOf(cantidadRegistros));
    }
    
    
    
    
    public int leerId() {
        try {
            int codigo = Integer.parseInt(txtId.getText().trim());
            return codigo;
        } catch (NumberFormatException ex) {
            return -666;
        }
    }
    
    
    public String leerNombreC() {
        try {
            String nombre = txtNombreC.getText().trim().replace(" ", "_");
            return nombre;
        } catch (Exception ex) {
            return null;
        }
    }
    
    
    public String leerMascota() {
        try {
            String mascota = (txtMascota.getText().trim());
            return mascota;
        } catch (Exception ex) {
            return null;
        }
    }
    
    public String leerRaza() {
        try {
            String raza = (txtRaza.getText().trim());
            return raza;
        } catch (Exception ex) {
            return null;
        }
    }
    
    
    public String leerAtencion() {
        try {
            String atencion = cmbA.getSelectedItem().toString().trim();
            return atencion;
        } catch (Exception ex) {
            return null;
        }
    }
    
    
    public void mensaje(String texto) {
        JOptionPane.showMessageDialog(null, texto);
    }
    
    
    
    public void limpiarCajasDeTexto(){
        txtId.setText("");
        txtNombreC.setText("");
        txtMascota.setText("");
        txtRaza.setText("");
        cmbA.setSelectedIndex(0);    //los combox trabajan como vector con indice desde 0, 1, 2...     
    }

    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtNombreC = new javax.swing.JTextField();
        txtId = new javax.swing.JTextField();
        txtMascota = new javax.swing.JTextField();
        txtRaza = new javax.swing.JTextField();
        cmbA = new javax.swing.JComboBox<>();
        btnEditar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaCliente = new javax.swing.JTable();
        rbc = new javax.swing.JRadioButton();
        rbn = new javax.swing.JRadioButton();
        txtbuscar = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        TablaCitasBuscar = new javax.swing.JTable();
        btnLimpiar1 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtTamanio = new javax.swing.JTextField();
        btnAtras = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel3.setText("ATENCIÓN ESPECIAL");

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/textogestiondeclientes.png"))); // NOI18N

        jLabel17.setText("# ID");

        jLabel6.setText("NOMBRE MASCOTA");

        jLabel7.setText("NOMBRE PROPIETARIO");

        jLabel8.setText("RAZA");

        cmbA.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "SELECCIONAR", "SI", "NO" }));

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

        jSeparator1.setForeground(new java.awt.Color(204, 204, 204));

        tablaCliente.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "# ID", "NOMBRE PROPIETARIO", "NOMBRE MASCOTA", "RAZA", "ATENCIÓN ESPECIAL"
            }
        ));
        tablaCliente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaClienteMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tablaCliente);

        rbc.setText("FILTRAR POR CÓDIGO");
        rbc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbcActionPerformed(evt);
            }
        });

        rbn.setText("FILTRAR POR NOMBRE DEL PROPIETARIO");
        rbn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbnActionPerformed(evt);
            }
        });

        txtbuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtbuscarActionPerformed(evt);
            }
        });

        btnBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/botonbuscar.png"))); // NOI18N
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        TablaCitasBuscar.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "# ID", "NOMBRE PROPIETARIO", "NOMBRE MASCOTA", "RAZA", "ATENCIÓN ESPECIAL"
            }
        ));
        jScrollPane2.setViewportView(TablaCitasBuscar);

        btnLimpiar1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnLimpiar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/botonlimpiar.png"))); // NOI18N
        btnLimpiar1.setText("Limpiar");
        btnLimpiar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiar1ActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Perpetua Titling MT", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 204, 204));
        jLabel2.setText("FORMULARIO");

        jLabel4.setFont(new java.awt.Font("Perpetua Titling MT", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 204, 204));
        jLabel4.setText("BUSCAR DATOs");

        jLabel9.setFont(new java.awt.Font("Perpetua Titling MT", 1, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 204, 204));
        jLabel9.setText("TAMAÑO TABLA");

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
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(rbc)
                            .addComponent(txtbuscar)
                            .addComponent(rbn))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnLimpiar1, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 153, Short.MAX_VALUE))
                        .addGap(6, 6, 6)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cmbA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtMascota)
                            .addComponent(txtRaza)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 133, Short.MAX_VALUE)
                            .addComponent(jLabel17, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(26, 26, 26)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNombreC, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(35, 35, 35)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 779, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(txtTamanio, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 777, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel9)))
                .addGap(852, 852, 852))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnLimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel2)
                        .addGap(349, 349, 349)
                        .addComponent(jLabel1))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel4))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 1254, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNombreC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(11, 11, 11)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtMascota, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtRaza, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(21, 21, 21))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTamanio, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(btnLimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(42, 42, 42)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(rbc)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(rbn)
                        .addGap(18, 18, 18)
                        .addComponent(txtbuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnLimpiar1, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(3, 3, 3))
        );

        btnAtras.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnAtras.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/botonatras.png"))); // NOI18N
        btnAtras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAtrasActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(313, 313, 313)
                .addComponent(btnAtras, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAtras, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1280, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        Buscador buscar = new Buscador ();
        if(rbc.isSelected()){
            int codigo = Integer.parseInt(txtbuscar.getText().trim());
            buscar.buscar_codigo(TablaCitasBuscar, cliente, procesocliente, codigo);
            
        }
        if (rbn.isSelected()){
            String nombre = txtbuscar.getText().trim();
            buscar.buscar_nombre(TablaCitasBuscar, cliente,procesocliente, nombre);
         
        }
    }//GEN-LAST:event_btnBuscarActionPerformed

    
    
    private void rbnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbnActionPerformed
        txtbuscar.setText("");
    }//GEN-LAST:event_rbnActionPerformed

    private void rbcActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbcActionPerformed
        txtbuscar.setText("");
    }//GEN-LAST:event_rbcActionPerformed

    
    
    private void tablaClienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaClienteMouseClicked
        clic_tabla = tablaCliente.rowAtPoint(evt.getPoint());

        int codigo = (int) tablaCliente.getValueAt(clic_tabla, 0);
        String nombre = "" + tablaCliente.getValueAt(clic_tabla, 1);
        String mascota = "" + tablaCliente.getValueAt(clic_tabla, 2);
        String raza = "" + tablaCliente.getValueAt(clic_tabla, 3);
        String atencion = "" + tablaCliente.getValueAt(clic_tabla, 4);

        txtId.setText(String.valueOf(codigo));
        txtNombreC.setText(nombre);
        txtMascota.setText(mascota);
        txtRaza.setText(raza);
        cmbA.setSelectedItem(atencion);   
    }//GEN-LAST:event_tablaClienteMouseClicked

    
    
    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        File ruta = new File(ruta_txt);
        this.ingresarRegistro( ruta);
    }//GEN-LAST:event_btnGuardarActionPerformed

    
    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        limpiarCajasDeTexto();
    }//GEN-LAST:event_btnLimpiarActionPerformed

    
    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        this.eliminarRegistro();
    }//GEN-LAST:event_btnEliminarActionPerformed

    
    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        File ruta = new File(ruta_txt);
        this.modificarRegistro(ruta);
    }//GEN-LAST:event_btnEditarActionPerformed

    
    
    private void btnAtrasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtrasActionPerformed
        JPanel panel = new JPanel();
        setVisible(false);
        Principal princ = new Principal();
        princ.setVisible(true);
        princ.setLocationRelativeTo(null);
    }//GEN-LAST:event_btnAtrasActionPerformed

    
    
    private void btnLimpiar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiar1ActionPerformed
        txtbuscar.setText("");
        rbc.setSelected(false);
        rbn.setSelected(false); 
        DefaultTableModel modelo = (DefaultTableModel) TablaCitasBuscar.getModel();
        modelo.setRowCount(0);
        TablaCitasBuscar.repaint();
        
        
    }//GEN-LAST:event_btnLimpiar1ActionPerformed

    private void txtTamanioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTamanioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTamanioActionPerformed

    private void txtbuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtbuscarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtbuscarActionPerformed
   
                                      
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JTable TablaCitasBuscar;
    private javax.swing.JButton btnAtras;
    private javax.swing.JButton btnBuscar;
    public javax.swing.JButton btnEditar;
    public javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JButton btnLimpiar1;
    private javax.swing.JComboBox<String> cmbA;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JRadioButton rbc;
    private javax.swing.JRadioButton rbn;
    public javax.swing.JTable tablaCliente;
    private javax.swing.JTextField txtId;
    private javax.swing.JTextField txtMascota;
    private javax.swing.JTextField txtNombreC;
    private javax.swing.JTextField txtRaza;
    private javax.swing.JTextField txtTamanio;
    private javax.swing.JTextField txtbuscar;
    // End of variables declaration//GEN-END:variables
    private JPanel panel = new JPanel();
}
