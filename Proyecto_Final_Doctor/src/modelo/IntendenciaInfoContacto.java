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
@Table(name = "INTENDENCIA_INFO_CONTACTO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IntendenciaInfoContacto.findAll", query = "SELECT i FROM IntendenciaInfoContacto i")
    , @NamedQuery(name = "IntendenciaInfoContacto.findByRfc", query = "SELECT i FROM IntendenciaInfoContacto i WHERE i.intendenciaInfoContactoPK.rfc = :rfc")
    , @NamedQuery(name = "IntendenciaInfoContacto.findByNombre", query = "SELECT i FROM IntendenciaInfoContacto i WHERE i.intendenciaInfoContactoPK.nombre = :nombre")
    , @NamedQuery(name = "IntendenciaInfoContacto.findByApellidoMaterno", query = "SELECT i FROM IntendenciaInfoContacto i WHERE i.apellidoMaterno = :apellidoMaterno")
    , @NamedQuery(name = "IntendenciaInfoContacto.findByApellidoPaterno", query = "SELECT i FROM IntendenciaInfoContacto i WHERE i.apellidoPaterno = :apellidoPaterno")
    , @NamedQuery(name = "IntendenciaInfoContacto.findByDireccion", query = "SELECT i FROM IntendenciaInfoContacto i WHERE i.direccion = :direccion")
    , @NamedQuery(name = "IntendenciaInfoContacto.findByCorreoElectronico", query = "SELECT i FROM IntendenciaInfoContacto i WHERE i.correoElectronico = :correoElectronico")
    , @NamedQuery(name = "IntendenciaInfoContacto.findByTelefono", query = "SELECT i FROM IntendenciaInfoContacto i WHERE i.telefono = :telefono")})
public class IntendenciaInfoContacto implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected IntendenciaInfoContactoPK intendenciaInfoContactoPK;
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "intendenciaInfoContacto")
    private Collection<IntendenciaInfoPersonal> intendenciaInfoPersonalCollection;

    public IntendenciaInfoContacto() {
    }

    public IntendenciaInfoContacto(IntendenciaInfoContactoPK intendenciaInfoContactoPK) {
        this.intendenciaInfoContactoPK = intendenciaInfoContactoPK;
    }

    public IntendenciaInfoContacto(IntendenciaInfoContactoPK intendenciaInfoContactoPK, String apellidoMaterno, String apellidoPaterno, String direccion, String correoElectronico, String telefono) {
        this.intendenciaInfoContactoPK = intendenciaInfoContactoPK;
        this.apellidoMaterno = apellidoMaterno;
        this.apellidoPaterno = apellidoPaterno;
        this.direccion = direccion;
        this.correoElectronico = correoElectronico;
        this.telefono = telefono;
    }

    public IntendenciaInfoContacto(String rfc, String nombre) {
        this.intendenciaInfoContactoPK = new IntendenciaInfoContactoPK(rfc, nombre);
    }

    public IntendenciaInfoContactoPK getIntendenciaInfoContactoPK() {
        return intendenciaInfoContactoPK;
    }

    public void setIntendenciaInfoContactoPK(IntendenciaInfoContactoPK intendenciaInfoContactoPK) {
        this.intendenciaInfoContactoPK = intendenciaInfoContactoPK;
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
    public Collection<IntendenciaInfoPersonal> getIntendenciaInfoPersonalCollection() {
        return intendenciaInfoPersonalCollection;
    }

    public void setIntendenciaInfoPersonalCollection(Collection<IntendenciaInfoPersonal> intendenciaInfoPersonalCollection) {
        this.intendenciaInfoPersonalCollection = intendenciaInfoPersonalCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (intendenciaInfoContactoPK != null ? intendenciaInfoContactoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof IntendenciaInfoContacto)) {
            return false;
        }
        IntendenciaInfoContacto other = (IntendenciaInfoContacto) object;
        if ((this.intendenciaInfoContactoPK == null && other.intendenciaInfoContactoPK != null) || (this.intendenciaInfoContactoPK != null && !this.intendenciaInfoContactoPK.equals(other.intendenciaInfoContactoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "proyecto_final_doctor.IntendenciaInfoContacto[ intendenciaInfoContactoPK=" + intendenciaInfoContactoPK + " ]";
    }
    
}
