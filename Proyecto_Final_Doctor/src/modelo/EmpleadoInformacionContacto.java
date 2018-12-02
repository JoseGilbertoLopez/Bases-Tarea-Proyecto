/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Jose G
 */
@Entity
@Table(name = "EMPLEADO_INFORMACION_CONTACTO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EmpleadoInformacionContacto.findAll", query = "SELECT e FROM EmpleadoInformacionContacto e")
    , @NamedQuery(name = "EmpleadoInformacionContacto.findByRfc", query = "SELECT e FROM EmpleadoInformacionContacto e WHERE e.empleadoInformacionContactoPK.rfc = :rfc")
    , @NamedQuery(name = "EmpleadoInformacionContacto.findByNombre", query = "SELECT e FROM EmpleadoInformacionContacto e WHERE e.empleadoInformacionContactoPK.nombre = :nombre")
    , @NamedQuery(name = "EmpleadoInformacionContacto.findByApellidoMaterno", query = "SELECT e FROM EmpleadoInformacionContacto e WHERE e.apellidoMaterno = :apellidoMaterno")
    , @NamedQuery(name = "EmpleadoInformacionContacto.findByApellidoPaterno", query = "SELECT e FROM EmpleadoInformacionContacto e WHERE e.apellidoPaterno = :apellidoPaterno")
    , @NamedQuery(name = "EmpleadoInformacionContacto.findByDireccion", query = "SELECT e FROM EmpleadoInformacionContacto e WHERE e.direccion = :direccion")
    , @NamedQuery(name = "EmpleadoInformacionContacto.findByCorreoElectronico", query = "SELECT e FROM EmpleadoInformacionContacto e WHERE e.correoElectronico = :correoElectronico")
    , @NamedQuery(name = "EmpleadoInformacionContacto.findByTelefono", query = "SELECT e FROM EmpleadoInformacionContacto e WHERE e.telefono = :telefono")})
public class EmpleadoInformacionContacto implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected EmpleadoInformacionContactoPK empleadoInformacionContactoPK;
    @Basic(optional = false)
    @Column(name = "APELLIDO_MATERNO")
    private String apellidoMaterno;
    @Basic(optional = false)
    @Column(name = "APELLIDO_PATERNO")
    private String apellidoPaterno;
    @Basic(optional = false)
    @Column(name = "DIRECCION")
    private String direccion;
    @Basic(optional = false)
    @Column(name = "CORREO_ELECTRONICO")
    private String correoElectronico;
    @Basic(optional = false)
    @Column(name = "TELEFONO")
    private String telefono;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "empleadoInformacionContacto")
    private Collection<EmpleadoInformacionPersonal> empleadoInformacionPersonalCollection;

    public EmpleadoInformacionContacto() {
    }

    public EmpleadoInformacionContacto(EmpleadoInformacionContactoPK empleadoInformacionContactoPK) {
        this.empleadoInformacionContactoPK = empleadoInformacionContactoPK;
    }

    public EmpleadoInformacionContacto(EmpleadoInformacionContactoPK empleadoInformacionContactoPK, String apellidoMaterno, String apellidoPaterno, String direccion, String correoElectronico, String telefono) {
        this.empleadoInformacionContactoPK = empleadoInformacionContactoPK;
        this.apellidoMaterno = apellidoMaterno;
        this.apellidoPaterno = apellidoPaterno;
        this.direccion = direccion;
        this.correoElectronico = correoElectronico;
        this.telefono = telefono;
    }

    public EmpleadoInformacionContacto(String rfc, String nombre) {
        this.empleadoInformacionContactoPK = new EmpleadoInformacionContactoPK(rfc, nombre);
    }

    public EmpleadoInformacionContactoPK getEmpleadoInformacionContactoPK() {
        return empleadoInformacionContactoPK;
    }

    public void setEmpleadoInformacionContactoPK(EmpleadoInformacionContactoPK empleadoInformacionContactoPK) {
        this.empleadoInformacionContactoPK = empleadoInformacionContactoPK;
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

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @XmlTransient
    public Collection<EmpleadoInformacionPersonal> getEmpleadoInformacionPersonalCollection() {
        return empleadoInformacionPersonalCollection;
    }

    public void setEmpleadoInformacionPersonalCollection(Collection<EmpleadoInformacionPersonal> empleadoInformacionPersonalCollection) {
        this.empleadoInformacionPersonalCollection = empleadoInformacionPersonalCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (empleadoInformacionContactoPK != null ? empleadoInformacionContactoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EmpleadoInformacionContacto)) {
            return false;
        }
        EmpleadoInformacionContacto other = (EmpleadoInformacionContacto) object;
        if ((this.empleadoInformacionContactoPK == null && other.empleadoInformacionContactoPK != null) || (this.empleadoInformacionContactoPK != null && !this.empleadoInformacionContactoPK.equals(other.empleadoInformacionContactoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "proyecto_final_doctor.EmpleadoInformacionContacto[ empleadoInformacionContactoPK=" + empleadoInformacionContactoPK + " ]";
    }
    
}
