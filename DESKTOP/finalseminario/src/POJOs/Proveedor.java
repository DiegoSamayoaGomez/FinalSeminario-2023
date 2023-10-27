package POJOs;
// Generated 27/10/2023 03:26:35 PM by Hibernate Tools 4.3.1


import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Proveedor generated by hbm2java
 */
public class Proveedor  implements java.io.Serializable {


     private Integer idProveedor;
     private Usuario usuarioByUsuarioModifica;
     private Usuario usuarioByUsuarioIngresa;
     private Boolean estado;
     private String nombre;
     private String direccion;
     private String telefono;
     private Date fechaIngresa;
     private Date fechaModifica;
     private Set<Compra> compras = new HashSet<Compra>(0);

    public Proveedor() {
    }

    public Proveedor(Usuario usuarioByUsuarioModifica, Usuario usuarioByUsuarioIngresa, Boolean estado, String nombre, String direccion, String telefono, Date fechaIngresa, Date fechaModifica, Set<Compra> compras) {
       this.usuarioByUsuarioModifica = usuarioByUsuarioModifica;
       this.usuarioByUsuarioIngresa = usuarioByUsuarioIngresa;
       this.estado = estado;
       this.nombre = nombre;
       this.direccion = direccion;
       this.telefono = telefono;
       this.fechaIngresa = fechaIngresa;
       this.fechaModifica = fechaModifica;
       this.compras = compras;
    }
   
    public Integer getIdProveedor() {
        return this.idProveedor;
    }
    
    public void setIdProveedor(Integer idProveedor) {
        this.idProveedor = idProveedor;
    }
    public Usuario getUsuarioByUsuarioModifica() {
        return this.usuarioByUsuarioModifica;
    }
    
    public void setUsuarioByUsuarioModifica(Usuario usuarioByUsuarioModifica) {
        this.usuarioByUsuarioModifica = usuarioByUsuarioModifica;
    }
    public Usuario getUsuarioByUsuarioIngresa() {
        return this.usuarioByUsuarioIngresa;
    }
    
    public void setUsuarioByUsuarioIngresa(Usuario usuarioByUsuarioIngresa) {
        this.usuarioByUsuarioIngresa = usuarioByUsuarioIngresa;
    }
    public Boolean getEstado() {
        return this.estado;
    }
    
    public void setEstado(Boolean estado) {
        this.estado = estado;
    }
    public String getNombre() {
        return this.nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getDireccion() {
        return this.direccion;
    }
    
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    public String getTelefono() {
        return this.telefono;
    }
    
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    public Date getFechaIngresa() {
        return this.fechaIngresa;
    }
    
    public void setFechaIngresa(Date fechaIngresa) {
        this.fechaIngresa = fechaIngresa;
    }
    public Date getFechaModifica() {
        return this.fechaModifica;
    }
    
    public void setFechaModifica(Date fechaModifica) {
        this.fechaModifica = fechaModifica;
    }
    public Set<Compra> getCompras() {
        return this.compras;
    }
    
    public void setCompras(Set<Compra> compras) {
        this.compras = compras;
    }




}


