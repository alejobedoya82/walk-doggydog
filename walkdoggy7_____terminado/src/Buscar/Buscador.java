
package Buscar;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import logica.Cliente;
import logica.ProcesoCliente;
import logica.Cita;
import logica.ProcesoCita;
//import logica.imgTabla;


public class Buscador {  
    public void buscar_nombre(JTable tablaCliente, Cliente cliente, ProcesoCliente procesocliente, String nombre) {
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

        //tablaCliente.setDefaultRenderer(Object.class, new imgTabla());

        Object fila[] = new Object[dt.getColumnCount()];
        boolean encontrado = false;                                             // Variable para verificar si se encontró el usuario

        for (int i = 0; i < procesocliente.cantidadRegistro(); i++) {
            cliente = procesocliente.obtenerRegistro(i);
            if (cliente.getNombreCliente().contains(nombre)) {
                encontrado = true;
                fila[0] = cliente.getIdCliente();
                fila[1] = cliente.getNombreCliente();
                fila[2] = cliente.getMascota();
                fila[3] = cliente.getRaza();
                fila[4] = cliente.getAtencion();
                dt.addRow(fila);
            }
        }
        
        if (encontrado) {
            JOptionPane.showMessageDialog(null, "Usuario encontrado");
        } else {
            JOptionPane.showMessageDialog(null, "Usuario no encontrado");
        }
        
        tablaCliente.setModel(dt);
        tablaCliente.setRowHeight(60);
    }
    
    
    
    
    
    
    public void buscar_codigo(JTable tablaCliente, Cliente cliente, ProcesoCliente procesocliente, int codigo) {
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

        //tablaCliente.setDefaultRenderer(Object.class, new imgTabla());

        Object fila[] = new Object[dt.getColumnCount()];
        boolean encontrado = false;                                             // Variable para verificar si se encontró el usuario
        for (int i = 0; i < procesocliente.cantidadRegistro(); i++) {
            cliente = procesocliente.obtenerRegistro(i);
            if(cliente.getIdCliente()== codigo){
                encontrado = true;
                fila[0] = cliente.getIdCliente();
                fila[1] = cliente.getNombreCliente();
                fila[2] = cliente.getMascota();
                fila[3] = cliente.getRaza();
                fila[4] = cliente.getAtencion();
                dt.addRow(fila);
            }      
        }
        
        if (encontrado) {
            JOptionPane.showMessageDialog(null, "Código encontrado");
            
        } else {
            JOptionPane.showMessageDialog(null, "Código no encontrado");
            
        }
        
        tablaCliente.setModel(dt);
        tablaCliente.setRowHeight(60);
    }  
    
}

