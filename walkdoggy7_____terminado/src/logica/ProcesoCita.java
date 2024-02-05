
package logica;

import java.util.ArrayList;


public class ProcesoCita {
    
    // almacena INSTANCIAS de la clase Cita
    private ArrayList<Cita> a = new ArrayList<>();                           

    public ProcesoCita() {                                       //constructor sin argumentos
    }

    public ProcesoCita(ArrayList<Cita> a) {                      //constructor. toma una lista de citas como argumento y la asigna al atributo "a" de la clase ProcesoCita. 
        this.a = a;
    }

    public void agregarRegistro(Cita p) {                         //Este método agrega una instancia de la clase Cita a la lista "a" de la clase ProcesoCita.
        this.a.add(p);                                          //
    }

    public void modificarRegistro(int i, Cita p) {                //Este método reemplaza la instancia de la clase Cita en la posición i de la lista a con la nueva instancia p.
        this.a.set(i, p);                              
    }

    public void eliminarRegistro(int i) {                        // Este método elimina la instancia de la clase Cita en la posición i de la lista a. 
        this.a.remove(i);
    }

    public Cita obtenerRegistro(int i) {                            //Este método devuelve la instancia de la clase Cita en la posición i de la lista a.        
        return (Cita) a.get(i);                               
    }

    public int cantidadRegistro() {                                //Este método devuelve la cantidad de elementos en la lista a, que corresponde al número de registros de citas.
        return this.a.size();
    }

    public int buscaCodigo(int codigo) {                          //Este método busca un código específico en la propiedad idCliente de las instancias de Cita en la lista a. 
        for (int i = 0; i < cantidadRegistro(); i++) {            //Si encuentra una coincidencia, devuelve la posición de la cita en la lista; de lo contrario, devuelve -1.
            if (codigo == obtenerRegistro(i).getIdCliente()) {
                return i;
            }
        }
        return -1;
    }
}
