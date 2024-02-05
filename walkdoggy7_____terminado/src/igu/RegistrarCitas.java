
package igu;

import Buscar.BuscadorClientes;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import logica.Limpiar_txt;
import java.awt.HeadlessException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import logica.Cita;
import logica.Cliente;
import logica.ProcesoCita;
import logica.ProcesoCliente;


public final class RegistrarCitas extends javax.swing.JFrame {
    Limpiar_txt lt = new Limpiar_txt();                          //instancia de la clase limpiar

    private String ruta_txtcita = "src//Archivo//citas.txt";
    
    Cita cita;                
    ProcesoCita procesocita;                                     //variable de la clase creada en logica
    int clic_tabla;                                              // variable entera

    public RegistrarCitas() {                                   //constructor
        initComponents();                                       //inicializa los componentes de la interfaz grafica
        procesocita = new ProcesoCita();                        //instancia  de la clase ProcesoCita
        
        try {
            cargar_txtCitas();
            listarRegistroCitas();
        } catch (ParseException ex) {          
        }         
    }
    
    
    
    //carga información desde un archivo de texto (citas.txt). 
    private void cargar_txtCitas() throws ParseException {
        
        File ruta = new File(ruta_txtcita);
        try {

            FileReader fi = new FileReader(ruta);                               //objeto que lee caracteres desde un  archivo  en este caso lee lo que este separedo por una ,
            try (BufferedReader bu = new BufferedReader(fi)) {                      // Un BufferedReader proporciona una forma más eficiente de leer líneas de texto
                String linea = null;
                while ((linea = bu.readLine()) != null) {                              //se ejecutará mientras haya líneas por leer en el archivo. En cada iteración, una línea 
                                                                                       //se lee y se asigna a la variable linea, y el bucle se ejecutará hasta que se llegue al final del archivo.
                    StringTokenizer st = new StringTokenizer(linea, ",");      //crea un objeto.
                    
                    cita = new Cita(leerId(), leerNombre(), leerMascota(),leerfecha(), leerMomento(), leerRuta());           //Se crea un objeto de la clase Cita llamado cita utilizando el constructor de esa clase, y se le pasan como argumentos los resultados de los métodos 
                    
                    cita.setIdCliente(Integer.parseInt(st.nextToken()));         //realiza varias operaciones utilizando un objeto StringTokenizer para dividir una línea en tokens y asignar esos valores a las propiedades de un objeto cita.
                    cita.setNombreCliente(st.nextToken());
                    cita.setMascota(st.nextToken());                   
                    cita.setFecha(st.nextToken());
                    cita.setMomento(st.nextToken());
                    cita.setRuta(st.nextToken());
                    
                    procesocita.agregarRegistro(cita);                            //Se llama al método agregarRegistro del objeto procesocita y se le pasa como argumento el objeto cita.    
                } 
            }
        } catch (IOException | NumberFormatException ex) {
            mensaje("Error al cargar archivo: " + ex.getMessage());
            System.out.println(ex.getMessage());
        }
    }
    
    
    
    //grabar los datos de citas en un archivo de texto
    private void grabar_txtCitas() {
        FileWriter fw;
        PrintWriter pw;
        try {
            fw = new FileWriter(ruta_txtcita);                       //se inicializan fw con un nuevo objeto FileWriter asociado al archivo especificado por ruta_txtcita,
            pw = new PrintWriter(fw);                                    // y pw con un nuevo objeto PrintWriter que utilizará el FileWriter para escribir en el archivo.

            for (int i = 0; i < procesocita.cantidadRegistro(); i++) {       //obtiene cada registro, que se asume es una instancia de la clase Cita. Luego, escribe en el archivo una línea que contiene los datos de la cita separados por comas.
                cita = (Cita) procesocita.obtenerRegistro(i);
                pw.println(String.valueOf(cita.getIdCliente()+ "," + cita.getNombreCliente()+ "," + cita.getMascota()+ "," + cita.getFecha() + "," + cita.getMomento()+ "," + cita.getRuta()));
            }
            pw.close();

        } catch (IOException ex) {
            mensaje("Error al grabar archivo: " + ex.getMessage());
            System.out.println(ex.getMessage());
        }
    }
    
    
    
    //ingresar registros al TXT, toma un parámetro de tipo File llamado ruta. 
    public void ingresarRegistro(File ruta) throws ParseException {
        try {
        if (leerId() == -666) {
            mensaje("Ingresar ID entero");
        } else if (leerNombre() == null) {
            mensaje("Ingresar Nombre");
        } else if (leerMascota() == null) {
            mensaje("Ingresar Mascota");
        } else if (leerfecha() == null) {
            mensaje("Ingresar fecha");
        } else {
            // Convertir la fecha de String a Date para realizar la comparación
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                Date fechaSeleccionada = sdf.parse(leerfecha());

                if (fechaSeleccionada.before(obtenerAyer())) {
                    mensaje("La fecha debe ser igual a hoy o después de hoy");
                } else if ("SELECCIONAR".equals(leerMomento())) {
                    mensaje("Ingresar Momento del día");
                } else if ("SELECCIONAR".equals(leerRuta())) {
                    mensaje("Ingresar Ruta");
                } else {
                    // Verificar si el código no existe en el archivo cliente.txt
                    if (buscarCodigoEnClientes(leerId())) {
                        mensaje("Este codigo no existe en el registro de clientes");
                    } else {
                        // Si ninguna de estas condiciones es verdadera, se procede a crear una nueva instancia de la clase Cita con los datos leídos
                        cita = new Cita(leerId(), leerNombre(), leerMascota(), leerfecha(), leerMomento(), leerRuta());
                        procesocita.agregarRegistro(cita);

                        grabar_txtCitas();
                        listarRegistroCitas();

                        lt.limpiar_texto(panel);
                        limpiarCajasDeTexto();
                    }
                }
            } catch (ParseException ex) {
                mensaje("Error al convertir la fecha: " + ex.getMessage());
            }
        }
    } catch (Exception ex) {
        mensaje(ex.getMessage());
    
    }
    
    }
    
        
    
    
    
    public void modificarRegistro(File ruta) {
        try {
        if (leerId() == -666) {
            mensaje("Ingresar ID entero");
        } else if (leerNombre() == null) {
            mensaje("Ingresar Nombre");
        } else if (leerMascota() == null) {
            mensaje("Ingresar Mascota");
        } else if (leerfecha() == null) {
            mensaje("Ingresar fecha");
        } else {
            // Convertir la fecha de String a Date para realizar la comparación
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                Date fechaSeleccionada = sdf.parse(leerfecha());

                if (fechaSeleccionada.before(obtenerAyer())) {
                    mensaje("La fecha debe ser igual o después de hoy");
                } else if ("SELECCIONAR".equals(leerMomento())) {
                    mensaje("Ingresar Momento del día");
                } else if ("SELECCIONAR".equals(leerRuta())) {
                    mensaje("Ingresar Ruta");
                } else {
                    int codigo = procesocita.buscaCodigo(leerId());
                    cita = new Cita(leerId(), leerNombre(), leerMascota(), leerfecha(), leerMomento(), leerRuta());

                    if (codigo == -1) {
                        procesocita.agregarRegistro(cita);
                    } else {
                        procesocita.modificarRegistro(codigo, cita);
                    }
                    grabar_txtCitas();
                    listarRegistroCitas();
                    lt.limpiar_texto(panel);
                    limpiarCajasDeTexto();
                }
            } catch (ParseException ex) {
                mensaje("Error al convertir la fecha: " + ex.getMessage());
            }
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
                int codigo = procesocita.buscaCodigo(leerId());
                if (codigo == -1) {
                    mensaje("ID no existe");
                } else {                                                         //Si el código existe, se muestra un cuadro de diálogo de confirmación para asegurarse de que el usuario desea eliminar la fila
                    int s = JOptionPane.showConfirmDialog(null, "Esta seguro de eliminar esta fila", "Si/No", 0);
                    if (s == 0) {
                        procesocita.eliminarRegistro(codigo);

                        grabar_txtCitas();
                        listarRegistroCitas();
                        lt.limpiar_texto(panel);
                        limpiarCajasDeTexto();
                    }
                }
            }
        } catch (HeadlessException ex) {
            mensaje(ex.getMessage());
        }
    }
    
    
    
    // listar los registros de citas en una tabla. 
    public void listarRegistroCitas() {
        DefaultTableModel dt = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {             //se anula el método isCellEditable para que ninguna celda de la tabla sea editable.
                return false;
            }
        };
        dt.addColumn("#ID");                                         //Se agregan columnas al modelo de tabla representando los diferentes atributos de una cita
        dt.addColumn("NOMBRE PROPIETARIO");
        dt.addColumn("NOMBRE MASCOTA");
        dt.addColumn("FECHA");
        dt.addColumn("MOMENTO DEL DÍA");
        dt.addColumn("RUTA");

//Se itera a través de los registros almacenados en procesocita y se crea una fila de datos para cada registro.
//Los valores de cada atributo de la cita se asignan a un array fila y se agrega esa fila al modelo de la tabla.        
        Object fila[] = new Object[dt.getColumnCount()];
        for (int i = 0; i < procesocita.cantidadRegistro(); i++) {
            cita = procesocita.obtenerRegistro(i);
            fila[0] = cita.getIdCliente();
            fila[1] = cita.getNombreCliente();
            fila[2] = cita.getMascota();
            fila[3] = cita.getFecha();           
            fila[4] = cita.getMomento();
            fila[5] = cita.getRuta();
            dt.addRow(fila);
        }
        TablaCitas.setModel(dt);                   //Se establece el modelo de la tabla TablaCitas con el modelo que se ha construido.
        TablaCitas.setRowHeight(30);               //Se establece la altura de las filas en la tabla TablaCitas a 30 píxeles.
       
        // Obtener la cantidad de registros y mostrarla en un componente de tu interfaz
        int cantidadRegistros = procesocita.cantidadRegistro();
        txtTamanio.setText(" Registros Actuales: " + cantidadRegistros);
       // txtTamanio.setText( String.valueOf(cantidadRegistros));               esta forma comentada muestra solo el numero
    }
    
    
    
    
    //este metodo busca directamente al archivo cliente.txt y retorna falso o verdadero para la validacion del otro metodo ingresar registros 
    private boolean buscarCodigoEnClientes(int codigo) {
    // Cargar los registros de cliente.txt y verificar si el código ya existe
        try {
            File rutaClientes = new File("src//Archivo//Cliente.txt");
            FileReader fr = new FileReader(rutaClientes);
            try (BufferedReader br = new BufferedReader(fr)) {
                String linea;
                while ((linea = br.readLine()) != null) {
                    StringTokenizer st = new StringTokenizer(linea, ",");
                    int codigoCliente = Integer.parseInt(st.nextToken());
                    if (codigoCliente == codigo) {
                        br.close();
                        return false;
                    }
                }
            }
        } catch (IOException | NumberFormatException e) {
            mensaje("Error al verificar código en cliente.txt: " + e.getMessage());
        }
        return true;
    }
    
   
    
    //método público llamado leerId que devuelve un valor entero (int).
    public int leerId() {
        try {
            int codigo = Integer.parseInt(txtId.getText().trim());          //obtiene el texto del campo, elimina espacios en blanco al principio y al final, y luego intenta convertirlo a un entero.
            return codigo; 
        } catch (NumberFormatException ex) {
            return -666;
        }
    }
    
    
    
    
    public String leerNombre() {
        try {
            String nombre = txtnombreCliente.getText().trim().replace(" ", "_");
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
    
    
    //eer una fecha seleccionada desde algún componente de la interfaz gráfica
    public String leerfecha() {
        try {
            Date fechaElegida = new Date();
            fechaElegida = dcFecha.getDate();                                //obtener la fecha seleccionada del componente dcFecha
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            String fecha = sdf.format(fechaElegida);                     //Se formatea la fecha obtenida en el paso anterior como una cadena de texto
            return fecha;
        } catch (Exception ex) {
            return null;
        }
    }
    
    
    private Date obtenerAyer() {
        Calendar ayer = Calendar.getInstance();
        ayer.add(Calendar.DATE, -1);  // Restar un día
    return ayer.getTime();
    }
  
    
    
    public String leerMomento() {
        try {
            String momento = cmbMomento.getSelectedItem().toString().trim();
            return momento;
        } catch (Exception ex) {
            return null;
        }
    }
    
    public String leerRuta() {
        try {
            String ruta = cmbRutas.getSelectedItem().toString().trim();
            return ruta;
        } catch (Exception ex) {
            return null;
        }
    }
    
    
    
    
    public void mensaje(String texto) {
        JOptionPane.showMessageDialog(null, texto);
    }
    

    
    
    public void limpiarCajasDeTexto(){
        txtId.setText("");
        txtnombreCliente.setText("");
        txtMascota.setText("");
        cmbRutas.setSelectedIndex(0);                           //los combox trabajan como vector con indice desde 0, 1, 2...     
        cmbMomento.setSelectedIndex(0);
        dcFecha.setDate(null);
    }
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        txtMascota = new javax.swing.JTextField();
        txtId = new javax.swing.JTextField();
        txtnombreCliente = new javax.swing.JTextField();
        cmbMomento = new javax.swing.JComboBox<>();
        cmbRutas = new javax.swing.JComboBox<>();
        dcFecha = new com.toedter.calendar.JDateChooser();
        btnAtras = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        TablaCitas = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        txtTamanio = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        rbc = new javax.swing.JRadioButton();
        rbn = new javax.swing.JRadioButton();
        txtbuscar = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JButton();
        btnLimpiar1 = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        TablaCitasBuscar = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(1334, 725));

        jLabel13.setText("NOMBRE MASCOTA");

        jLabel21.setText("RUTAS");

        jLabel15.setText("MOMENTO DEL DÍA");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/txtgestiondecitas.png"))); // NOI18N

        jLabel14.setText("FECHA");

        jLabel18.setText("# ID");

        jLabel17.setText("NOMBRE PROPIETARIO");

        txtId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdActionPerformed(evt);
            }
        });

        txtnombreCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtnombreClienteActionPerformed(evt);
            }
        });

        cmbMomento.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "SELECCIONAR", "MAÑANA (7am - 10am)", "TARDE (2pm - 5pm)", "NOCHE (7pm - 10pm)" }));
        cmbMomento.setToolTipText("");

        cmbRutas.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "SELECCIONAR", "RUTA 1", "RUTA 2" }));
        cmbRutas.setToolTipText("");

        btnAtras.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnAtras.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/botonatras.png"))); // NOI18N
        btnAtras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAtrasActionPerformed(evt);
            }
        });

        btnEditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/botonactualizar.png"))); // NOI18N
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });

        btnGuardar.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/botoncrear.png"))); // NOI18N
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
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

        TablaCitas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "# ID", "NOMBRE PROPIETARIO", "NOMBRE MASCOTA", "FECHA", "MOMENTO DEL DÍA", "RUTA"
            }
        ));
        TablaCitas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TablaCitasMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(TablaCitas);

        jLabel3.setFont(new java.awt.Font("Perpetua Titling MT", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 204, 204));
        jLabel3.setText("FORMULARIO citas");

        txtTamanio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTamanioActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Perpetua Titling MT", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 204, 204));
        jLabel4.setText("TAMAÑO TABLA");

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

        btnBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/botonbuscar.png"))); // NOI18N
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        btnLimpiar1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnLimpiar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/botonlimpiar.png"))); // NOI18N
        btnLimpiar1.setText("Limpiar");
        btnLimpiar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiar1ActionPerformed(evt);
            }
        });

        TablaCitasBuscar.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "# ID", "NOMBRE PROPIETARIO", "NOMBRE MASCOTA", "RAZA", "ATENCIÓN ESPECIAL"
            }
        ));
        jScrollPane3.setViewportView(TablaCitasBuscar);

        jLabel5.setFont(new java.awt.Font("Perpetua Titling MT", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 204, 204));
        jLabel5.setText("BUSCAR clientes registrados");

        jSeparator1.setForeground(new java.awt.Color(204, 204, 204));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnLimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnAtras)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 351, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(208, 208, 208)
                        .addComponent(jLabel2)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 1254, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(txtnombreCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(jPanel3Layout.createSequentialGroup()
                                            .addComponent(jLabel18)
                                            .addGap(132, 132, 132)
                                            .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(txtMascota, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(dcFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(jPanel3Layout.createSequentialGroup()
                                            .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(cmbRutas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(cmbMomento, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                    .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel13)
                                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane2))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(rbc)
                                    .addComponent(rbn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtbuscar))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnLimpiar1, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 888, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(txtTamanio, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel4))))
                        .addGap(29, 29, 29))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(43, 43, 43)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel2)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(rbc)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(rbn)
                        .addGap(18, 18, 18)
                        .addComponent(txtbuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnLimpiar1, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 38, Short.MAX_VALUE)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtTamanio, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtnombreCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtMascota, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(dcFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbMomento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbRutas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAtras, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnLimpiar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(36, 36, 36))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 96, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 39, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtTamanioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTamanioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTamanioActionPerformed

    //responde al clic en la tabla (TablaCitas)
    private void TablaCitasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TablaCitasMouseClicked
        clic_tabla = TablaCitas.rowAtPoint(evt.getPoint());                    //Obtiene la fila en la que se hizo clic dentro de la tabla y almacena el índice de la fila en la variable clic_tabla.

        int codigo = (int) TablaCitas.getValueAt(clic_tabla, 0);             //Obtiene los valores de las celdas de la fila seleccionada en la tabla y los asigna a variables locales
        String nombre = "" + TablaCitas.getValueAt(clic_tabla, 1);
        String mascota = "" + TablaCitas.getValueAt(clic_tabla, 2);
        String fecha = "" + TablaCitas.getValueAt(clic_tabla, 3);
        String momento = "" + TablaCitas.getValueAt(clic_tabla, 4);
        String ruta = "" + TablaCitas.getValueAt(clic_tabla, 5);

        txtId.setText(String.valueOf(codigo));                                 //Establece el texto de algunos campos de texto con los valores obtenidos de la tabla.
        txtnombreCliente.setText(nombre);
        txtMascota.setText(mascota);

        try {
            // conversión de una fecha (String) en un objeto (Date)
            java.util.Date fechaSeleccionada = new SimpleDateFormat("dd-MM-yyyy").parse(fecha);
            // Se hace que salga en el JDateChooser
            dcFecha.setDate(fechaSeleccionada);
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        cmbMomento.setSelectedItem(momento);
        cmbRutas.setSelectedItem(ruta);
    }//GEN-LAST:event_TablaCitasMouseClicked

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        limpiarCajasDeTexto();
    }//GEN-LAST:event_btnLimpiarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        this.eliminarRegistro();
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        File ruta = new File(ruta_txtcita);
        try {
            this.ingresarRegistro( ruta);
        } catch (ParseException ex) {
            Logger.getLogger(RegistrarCitas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        File ruta = new File(ruta_txtcita);
        this.modificarRegistro(ruta);
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnAtrasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtrasActionPerformed
        JPanel panel = new JPanel();
        setVisible(false);
        Principal princ = new Principal();
        princ.setVisible(true);
        princ.setLocationRelativeTo(null);
    }//GEN-LAST:event_btnAtrasActionPerformed

    private void txtnombreClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtnombreClienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtnombreClienteActionPerformed

    private void txtIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdActionPerformed

    private void rbcActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbcActionPerformed
        txtbuscar.setText("");
    }//GEN-LAST:event_rbcActionPerformed

    private void rbnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbnActionPerformed
        txtbuscar.setText("");
    }//GEN-LAST:event_rbnActionPerformed

    
    
    
    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
    Cliente cliente = new Cliente();                             //Se crea una instancia de Cliente para almacenar temporalmente los datos del cliente que se va a buscar o comparar.
    ProcesoCliente procesocliente = new ProcesoCliente();        // "manejador" de objetos Cliente. 
    // Obtener la ruta del archivo
    String ruta_txt = "src//Archivo//Cliente.txt"; 
    // Crear un nuevo BuscadorClientes
    BuscadorClientes buscador = new BuscadorClientes(ruta_txt);        // La lógica de búsqueda se encuentra en la clase BuscadorClientes, y se utiliza esta clase para realizar la búsqueda y mostrar los resultados en la tabla TablaCitasBuscar.

    if(rbc.isSelected()){
        // Obtener el código a buscar
        int codigo = Integer.parseInt(txtbuscar.getText().trim());
        // Realizar la búsqueda por código
        buscador.buscar_codigo(TablaCitasBuscar, procesocliente, codigo);
    }
    if (rbn.isSelected()){
        // Obtener el nombre a buscar
        String nombre = txtbuscar.getText().trim();
        // Realizar la búsqueda por nombre
        buscador.buscar_nombre(TablaCitasBuscar, procesocliente, nombre);
    }    
    }//GEN-LAST:event_btnBuscarActionPerformed

    
    
    
    
    
    
    
    private void btnLimpiar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiar1ActionPerformed
        txtbuscar.setText("");
        rbc.setSelected(false);
        rbn.setSelected(false);
        DefaultTableModel modelo = (DefaultTableModel) TablaCitasBuscar.getModel();
        modelo.setRowCount(0);
        TablaCitasBuscar.repaint();

    }//GEN-LAST:event_btnLimpiar1ActionPerformed

    
   
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JTable TablaCitas;
    public javax.swing.JTable TablaCitasBuscar;
    private javax.swing.JButton btnAtras;
    private javax.swing.JButton btnBuscar;
    public javax.swing.JButton btnEditar;
    public javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JButton btnLimpiar1;
    private javax.swing.JComboBox<String> cmbMomento;
    private javax.swing.JComboBox<String> cmbRutas;
    private com.toedter.calendar.JDateChooser dcFecha;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JRadioButton rbc;
    private javax.swing.JRadioButton rbn;
    private javax.swing.JTextField txtId;
    private javax.swing.JTextField txtMascota;
    private javax.swing.JTextField txtTamanio;
    private javax.swing.JTextField txtbuscar;
    private javax.swing.JTextField txtnombreCliente;
    // End of variables declaration//GEN-END:variables
    private JPanel panel = new JPanel();   
}
