package entidades;

package inge2_sgp.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author willians_ojeda
 */
@Entity
@Table(name = "tareas")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "Tareas.findAll", query = "SELECT t FROM Tareas t")
        , @NamedQuery(name = "Tareas.findByIdProyecto", query = "SELECT t FROM Tareas t WHERE t.tareasPK.idProyecto = :idProyecto")
        , @NamedQuery(name = "Tareas.findByIdTarea", query = "SELECT t FROM Tareas t WHERE t.tareasPK.idTarea = :idTarea")
        , @NamedQuery(name = "Tareas.findByDescripcion", query = "SELECT t FROM Tareas t WHERE t.descripcion = :descripcion")
        , @NamedQuery(name = "Tareas.findByEstado", query = "SELECT t FROM Tareas t WHERE t.estado = :estado")
        , @NamedQuery(name = "Tareas.findByFechaInicio", query = "SELECT t FROM Tareas t WHERE t.fechaInicio = :fechaInicio")
        , @NamedQuery(name = "Tareas.findByPrioridad", query = "SELECT t FROM Tareas t WHERE t.prioridad = :prioridad")
        , @NamedQuery(name = "Tareas.findByFechaFin", query = "SELECT t FROM Tareas t WHERE t.fechaFin = :fechaFin")
        , @NamedQuery(name = "Tareas.findByUserAlta", query = "SELECT t FROM Tareas t WHERE t.userAlta = :userAlta")})
public class Tareas implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected TareasPK tareasPK;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "descripcion")
    private String descripcion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "estado")
    private String estado;
    @Column(name = "fecha_inicio")
    @Temporal(TemporalType.DATE)
    private Date fechaInicio;
    @Basic(optional = false)
    @NotNull
    @Column(name = "prioridad")
    private int prioridad;
    @Column(name = "fecha_fin")
    @Temporal(TemporalType.DATE)
    private Date fechaFin;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "user_alta")
    private String userAlta;
    @JoinColumn(name = "id_empleado", referencedColumnName = "id_empleado")
    @ManyToOne(optional = false)
    private Empleados idEmpleado;
    @JoinColumn(name = "id_proyecto", referencedColumnName = "id_proyecto", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Proyectos proyectos;

    public Tareas() {
    }

    public Tareas(TareasPK tareasPK) {
        this.tareasPK = tareasPK;
    }

    public Tareas(TareasPK tareasPK, String descripcion, String estado, int prioridad, String userAlta) {
        this.tareasPK = tareasPK;
        this.descripcion = descripcion;
        this.estado = estado;
        this.prioridad = prioridad;
        this.userAlta = userAlta;
    }

    public Tareas(int idProyecto, int idTarea) {
        this.tareasPK = new TareasPK(idProyecto, idTarea);
    }

    public TareasPK getTareasPK() {
        return tareasPK;
    }

    public void setTareasPK(TareasPK tareasPK) {
        this.tareasPK = tareasPK;
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

    public Proyectos getProyectos() {
        return proyectos;
    }

    public void setProyectos(Proyectos proyectos) {
        this.proyectos = proyectos;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tareasPK != null ? tareasPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tareas)) {
            return false;
        }
        Tareas other = (Tareas) object;
        if ((this.tareasPK == null && other.tareasPK != null) || (this.tareasPK != null && !this.tareasPK.equals(other.tareasPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "inge2_sgp.entities.Tareas[ tareasPK=" + tareasPK + " ]";
    }

}

