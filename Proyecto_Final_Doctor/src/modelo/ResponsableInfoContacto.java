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
@Table(name = "RESPONSABLE_INFO_CONTACTO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ResponsableInfoContacto.findAll", query = "SELECT r FROM ResponsableInfoContacto r")
    , @NamedQuery(name = "ResponsableInfoContacto.findByRfc", query = "SELECT r FROM ResponsableInfoContacto r WHERE r.responsableInfoContactoPK.rfc = :rfc")
    , @NamedQuery(name = "ResponsableInfoContacto.findByNombre", query = "SELECT r FROM ResponsableInfoContacto r WHERE r.responsableInfoContactoPK.nombre = :nombre")
    , @NamedQuery(name = "ResponsableInfoContacto.findByApellidoMaterno", query = "SELECT r FROM ResponsableInfoContacto r WHERE r.apellidoMaterno = :apellidoMaterno")
    , @NamedQuery(name = "ResponsableInfoContacto.findByApellidoPaterno", query = "SELECT r FROM ResponsableInfoContacto r WHERE r.apellidoPaterno = :apellidoPaterno")
    , @NamedQuery(name = "ResponsableInfoContacto.findByDireccion", query = "SELECT r FROM ResponsableInfoContacto r WHERE r.direccion = :direccion")
    , @NamedQuery(name = "ResponsableInfoContacto.findByCorreoElectronico", query = "SELECT r FROM ResponsableInfoContacto r WHERE r.correoElectronico = :correoElectronico")
    , @NamedQuery(name = "ResponsableInfoContacto.findByTelefono", query = "SELECT r FROM ResponsableInfoContacto r WHERE r.telefono = :telefono")})
public class ResponsableInfoContacto implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ResponsableInfoContactoPK responsableInfoContactoPK;
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "responsableInfoContacto")
    private Collection<ResponsableInfoPersonal> responsableInfoPersonalCollection;

    public ResponsableInfoContacto() {
    }

    public ResponsableInfoContacto(ResponsableInfoContactoPK responsableInfoContactoPK) {
        this.responsableInfoContactoPK = responsableInfoContactoPK;
    }

    public ResponsableInfoContacto(ResponsableInfoContactoPK responsableInfoContactoPK, String apellidoMaterno, String apellidoPaterno, String direccion, String correoElectronico, String telefono) {
        this.responsableInfoContactoPK = responsableInfoContactoPK;
        this.apellidoMaterno = apellidoMaterno;
        this.apellidoPaterno = apellidoPaterno;
        this.direccion = direccion;
        this.correoElectronico = correoElectronico;
        this.telefono = telefono;
    }

    public ResponsableInfoContacto(String rfc, String nombre) {
        this.responsableInfoContactoPK = new ResponsableInfoContactoPK(rfc, nombre);
    }

    public ResponsableInfoContactoPK getResponsableInfoContactoPK() {
        return responsableInfoContactoPK;
    }

    public void setResponsableInfoContactoPK(ResponsableInfoContactoPK responsableInfoContactoPK) {
        this.responsableInfoContactoPK = responsableInfoContactoPK;
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
    public Collection<ResponsableInfoPersonal> getResponsableInfoPersonalCollection() {
        return responsableInfoPersonalCollection;
    }

    public void setResponsableInfoPersonalCollection(Collection<ResponsableInfoPersonal> responsableInfoPersonalCollection) {
        this.responsableInfoPersonalCollection = responsableInfoPersonalCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (responsableInfoContactoPK != null ? responsableInfoContactoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ResponsableInfoContacto)) {
            return false;
        }
        ResponsableInfoContacto other = (ResponsableInfoContacto) object;
        if ((this.responsableInfoContactoPK == null && other.responsableInfoContactoPK != null) || (this.responsableInfoContactoPK != null && !this.responsableInfoContactoPK.equals(other.responsableInfoContactoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "proyecto_final_doctor.ResponsableInfoContacto[ responsableInfoContactoPK=" + responsableInfoContactoPK + " ]";
    }
    
}
