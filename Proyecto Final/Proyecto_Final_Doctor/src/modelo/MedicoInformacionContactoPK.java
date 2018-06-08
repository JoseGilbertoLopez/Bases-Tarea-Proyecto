/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Jose G
 */
@Embeddable
public class MedicoInformacionContactoPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "NO_SEGURO_SOCIAL")
    private String noSeguroSocial;
    @Basic(optional = false)
    @Column(name = "CEDULA_PROFESIONAL")
    private String cedulaProfesional;
    @Basic(optional = false)
    @Column(name = "NOMBRE")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "APELLIDO_PATERNO")
    private String apellidoPaterno;
    @Basic(optional = false)
    @Column(name = "APELLIDO_MATERNO")
    private String apellidoMaterno;

    public MedicoInformacionContactoPK() {
    }

    public MedicoInformacionContactoPK(String noSeguroSocial, String cedulaProfesional, String nombre, String apellidoPaterno, String apellidoMaterno) {
        this.noSeguroSocial = noSeguroSocial;
        this.cedulaProfesional = cedulaProfesional;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getNoSeguroSocial() {
        return noSeguroSocial;
    }

    public void setNoSeguroSocial(String noSeguroSocial) {
        this.noSeguroSocial = noSeguroSocial;
    }

    public String getCedulaProfesional() {
        return cedulaProfesional;
    }

    public void setCedulaProfesional(String cedulaProfesional) {
        this.cedulaProfesional = cedulaProfesional;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (noSeguroSocial != null ? noSeguroSocial.hashCode() : 0);
        hash += (cedulaProfesional != null ? cedulaProfesional.hashCode() : 0);
        hash += (nombre != null ? nombre.hashCode() : 0);
        hash += (apellidoPaterno != null ? apellidoPaterno.hashCode() : 0);
        hash += (apellidoMaterno != null ? apellidoMaterno.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MedicoInformacionContactoPK)) {
            return false;
        }
        MedicoInformacionContactoPK other = (MedicoInformacionContactoPK) object;
        if ((this.noSeguroSocial == null && other.noSeguroSocial != null) || (this.noSeguroSocial != null && !this.noSeguroSocial.equals(other.noSeguroSocial))) {
            return false;
        }
        if ((this.cedulaProfesional == null && other.cedulaProfesional != null) || (this.cedulaProfesional != null && !this.cedulaProfesional.equals(other.cedulaProfesional))) {
            return false;
        }
        if ((this.nombre == null && other.nombre != null) || (this.nombre != null && !this.nombre.equals(other.nombre))) {
            return false;
        }
        if ((this.apellidoPaterno == null && other.apellidoPaterno != null) || (this.apellidoPaterno != null && !this.apellidoPaterno.equals(other.apellidoPaterno))) {
            return false;
        }
        if ((this.apellidoMaterno == null && other.apellidoMaterno != null) || (this.apellidoMaterno != null && !this.apellidoMaterno.equals(other.apellidoMaterno))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "proyecto_final_doctor.MedicoInformacionContactoPK[ noSeguroSocial=" + noSeguroSocial + ", cedulaProfesional=" + cedulaProfesional + ", nombre=" + nombre + ", apellidoPaterno=" + apellidoPaterno + ", apellidoMaterno=" + apellidoMaterno + " ]";
    }
    
}
