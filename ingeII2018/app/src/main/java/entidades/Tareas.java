package entidades;



import java.io.Serializable;
import java.util.Date;



public class Tareas implements Serializable {

    private static final long serialVersionUID = 1L;

    private Usuarios usuario;

    private String descripcion;

    private String estado;

    private Date fechaInicio;

    private int prioridad;

    private Date fechaFin;

    private String userAlta;

    private Empleados idEmpleado;

//    private Proyectos proyectos;

    public Tareas() {
    }


    public Tareas(Usuarios usuario, String descripcion, String estado, int prioridad, String userAlta) {
        this.usuario = usuario;
        this.descripcion = descripcion;
        this.estado = estado;
        this.prioridad = prioridad;
        this.userAlta = userAlta;
    }

    public Usuarios getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuarios usuario) {
        this.usuario = usuario;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public int getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(int prioridad) {
        this.prioridad = prioridad;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getUserAlta() {
        return userAlta;
    }

    public void setUserAlta(String userAlta) {
        this.userAlta = userAlta;
    }

    public Empleados getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(Empleados idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

}

