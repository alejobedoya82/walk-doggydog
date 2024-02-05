
package logica;


public class UsuarioPadre {
    int idCliente;
    String nombreCliente;
    String mascota;

    public UsuarioPadre() {
    }

    public UsuarioPadre(int idCliente, String nombreCliente, String mascota) {
        this.idCliente = idCliente;
        this.nombreCliente = nombreCliente;
        this.mascota = mascota;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getMascota() {
        return mascota;
    }

    public void setMascota(String mascota) {
        this.mascota = mascota;
    }

    
    
    
    
}
