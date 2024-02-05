
package logica;

import java.util.ArrayList;


public class Proceso {

    private ArrayList<Object> a = new ArrayList<>();                            //es una lista de objetos

    public Proceso() {
    }

    public Proceso(ArrayList<Object> a) {
        this.a = a;
    }

    public void agregarRegistro(Empleado p) {                                   //toma un objeto de la clase Empleado como argumento.
        this.a.add(p);                                                        //grega el objeto Empleado proporcionado al ArrayList a utilizando el método add().
    }

    public void modificarRegistro(int i, Empleado p) {                          //que toma dos argumentos: un entero i y un objeto de la clase Empleado
        this.a.set(i, p);                                            // modifica el elemento en la posición i del ArrayList a con el nuevo objeto Empleado 
    }

    public void eliminarRegistro(int i) {                                       // eliminar el elemento en la posición i.
        this.a.remove(i);
    }

    public Empleado obtenerRegistro(int i) {                                    
        return (Empleado) a.get(i);                                        //Retorna el elemento en la posición i del ArrayList a como un objeto de tipo Empleado.
    }

    public int cantidadRegistro() {
        return this.a.size();
    }

    public int buscaCodigo(int codigo) {
        for (int i = 0; i < cantidadRegistro(); i++) {
            if (codigo == obtenerRegistro(i).getIdEmpleado()) {
                return i;
            }
        }
        return -1;
    }

}
