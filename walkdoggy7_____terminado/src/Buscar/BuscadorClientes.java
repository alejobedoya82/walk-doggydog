/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Buscar;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import logica.ProcesoCliente;

/**
 *
 * @author Personal
 */
public class BuscadorClientes {
    private String ruta_txt;

    public BuscadorClientes(String ruta_txt) {
        this.ruta_txt = ruta_txt;
    
    }
    public void buscar_nombre(JTable tablaCliente, ProcesoCliente procesocliente, String nombre) {
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
        boolean encontrado = false;

        try (BufferedReader br = new BufferedReader(new FileReader(ruta_txt))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                StringTokenizer st = new StringTokenizer(linea, ",");
                int idCliente = Integer.parseInt(st.nextToken());
                String nombreCliente = st.nextToken();
                String mascota = st.nextToken();
                String raza = st.nextToken();
                String atencion = st.nextToken();

                if (nombreCliente.contains(nombre)) {
                    encontrado = true;
                    fila[0] = idCliente;
                    fila[1] = nombreCliente;
                    fila[2] = mascota;
                    fila[3] = raza;
                    fila[4] = atencion;
                    dt.addRow(fila);
                }
            }
        } catch (IOException | NumberFormatException ex) {
            ex.printStackTrace();
        }

        if (encontrado) {
            JOptionPane.showMessageDialog(null, "Usuario encontrado con exito!!!");
        } else {
            JOptionPane.showMessageDialog(null, "Usuario no encontrado");
        }

        tablaCliente.setModel(dt);
        tablaCliente.setRowHeight(60);
    }

    
    
    
    
    
    public void buscar_codigo(JTable tablaCliente, ProcesoCliente procesocliente, int codigo) {
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
        boolean encontrado = false;

        try (BufferedReader br = new BufferedReader(new FileReader(ruta_txt))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                StringTokenizer st = new StringTokenizer(linea, ",");
                int idCliente = Integer.parseInt(st.nextToken());
                String nombreCliente = st.nextToken();
                String mascota = st.nextToken();
                String raza = st.nextToken();
                String atencion = st.nextToken();

                if (idCliente == codigo) {
                    encontrado = true;
                    fila[0] = idCliente;
                    fila[1] = nombreCliente;
                    fila[2] = mascota;
                    fila[3] = raza;
                    fila[4] = atencion;
                    dt.addRow(fila);
                }
            }
        } catch (IOException | NumberFormatException ex) {
            ex.printStackTrace();
        }

        if (encontrado) {
            JOptionPane.showMessageDialog(null, "Usuario encontrado con exito!!! ");
        } else {
            JOptionPane.showMessageDialog(null, "Usuario no encontrado");
        }

        tablaCliente.setModel(dt);
        tablaCliente.setRowHeight(60);
    }
}
