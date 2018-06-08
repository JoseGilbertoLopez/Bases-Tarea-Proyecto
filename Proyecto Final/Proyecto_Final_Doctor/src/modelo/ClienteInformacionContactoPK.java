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
public class ClienteInformacionContactoPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "NOMBRE")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "APELLIDO_MATERNO")
    private String apellidoMaterno;
    @Basic(optional = false)
    @Column(name = "APELLIDO_PATERNO")
    private String apellidoPaterno;

    public ClienteInformacionContactoPK() {
    }

    public ClienteInformacionContactoPK(String nombre, String apellidoMaterno, String apellidoPaterno) {
        this.nombre = nombre;
        this.apellidoMaterno = apellidoMaterno;
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (nombre != null ? nombre.hashCode() : 0);
        hash += (apellidoMaterno != null ? apellidoMaterno.hashCode() : 0);
        hash += (apellidoPaterno != null ? apellidoPaterno.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ClienteInformacionContactoPK)) {
            return false;
        }
        ClienteInformacionContactoPK other = (ClienteInformacionContactoPK) object;
        if ((this.nombre == null && other.nombre != null) || (this.nombre != null && !this.nombre.equals(other.nombre))) {
            return false;
        }
        if ((this.apellidoMaterno == null && other.apellidoMaterno != null) || (this.apellidoMaterno != null && !this.apellidoMaterno.equals(other.apellidoMaterno))) {
            return false;
        }
        if ((this.apellidoPaterno == null && other.apellidoPaterno != null) || (this.apellidoPaterno != null && !this.apellidoPaterno.equals(other.apellidoPaterno))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "proyecto_final_doctor.ClienteInformacionContactoPK[ nombre=" + nombre + ", apellidoMaterno=" + apellidoMaterno + ", apellidoPaterno=" + apellidoPaterno + " ]";
    }
    
}
