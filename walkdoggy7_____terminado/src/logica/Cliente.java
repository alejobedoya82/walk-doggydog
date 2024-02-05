
package logica;

public class Cliente extends UsuarioPadre{    //herencia 
    
    String raza;
    String atencion;

    public Cliente() {
    }
    
    
    
    public Cliente(int idCliente, String nombreCliente, String mascota, String raza, String atencion){
        super(idCliente,nombreCliente,mascota);
        this.raza=raza;
        this.atencion=atencion;
        
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public String getAtencion() {
        return atencion;
    }

    public void setAtencion(String atencion) {
        this.atencion = atencion;
    }

    
    
    
    
}
