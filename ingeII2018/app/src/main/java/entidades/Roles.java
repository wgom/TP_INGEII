/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

/**
 *
 * @author willians_ojeda
 */

public class Roles implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer idRol;

    private String nombreRol;

    private String descripcion;

    private String usrAlta;

    private Date fecAlta;

    private Date fecUltAct;

    private Collection<Usuarios> usuariosCollection;

    public Roles() {
    }

    public Roles(Integer idRol) {
        this.idRol = idRol;
    }

    public Roles(Integer idRol, String nombreRol, String usrAlta, Date fecAlta, Date fecUltAct) {
        this.idRol = idRol;
        this.nombreRol = nombreRol;
        this.usrAlta = usrAlta;
        this.fecAlta = fecAlta;
        this.fecUltAct = fecUltAct;
    }

    public Integer getIdRol() {
        return idRol;
    }

    public void setIdRol(Integer idRol) {
        this.idRol = idRol;
    }

    public String getNombreRol() {
        return nombreRol;
    }

    public void setNombreRol(String nombreRol) {
        this.nombreRol = nombreRol;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUsrAlta() {
        return usrAlta;
    }

    public void setUsrAlta(String usrAlta) {
        this.usrAlta = usrAlta;
    }

    public Date getFecAlta() {
        return fecAlta;
    }

    public void setFecAlta(Date fecAlta) {
        this.fecAlta = fecAlta;
    }

    public Date getFecUltAct() {
        return fecUltAct;
    }

    public void setFecUltAct(Date fecUltAct) {
        this.fecUltAct = fecUltAct;
    }


    public Collection<Usuarios> getUsuariosCollection() {
        return usuariosCollection;
    }

    public void setUsuariosCollection(Collection<Usuarios> usuariosCollection) {
        this.usuariosCollection = usuariosCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idRol != null ? idRol.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Roles)) {
            return false;
        }
        Roles other = (Roles) object;
        if ((this.idRol == null && other.idRol != null) || (this.idRol != null && !this.idRol.equals(other.idRol))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "inge2_sgp.entities.Roles[ idRol=" + idRol + " ]";
    }
    
}
