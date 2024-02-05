
package logica;



public class Cita extends UsuarioPadre{   //herencia
    
    String fecha; 
    String momento;
    String ruta;

  
    public Cita(int idCliente, String nombreCliente, String mascota,String fecha, String momento,String ruta){
        super(idCliente,nombreCliente,mascota);
        this.fecha=fecha;    
        this.momento=momento;
        this.ruta=ruta;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public String getMomento() {
        return momento;
    }

    public void setMomento(String momento) {
        this.momento = momento;
    }
  
}
