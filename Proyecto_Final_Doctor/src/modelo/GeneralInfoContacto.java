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
@Table(name = "GENERAL_INFO_CONTACTO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GeneralInfoContacto.findAll", query = "SELECT g FROM GeneralInfoContacto g")
    , @NamedQuery(name = "GeneralInfoContacto.findByRfc", query = "SELECT g FROM GeneralInfoContacto g WHERE g.generalInfoContactoPK.rfc = :rfc")
    , @NamedQuery(name = "GeneralInfoContacto.findByNombre", query = "SELECT g FROM GeneralInfoContacto g WHERE g.generalInfoContactoPK.nombre = :nombre")
    , @NamedQuery(name = "GeneralInfoContacto.findByApellidoMaterno", query = "SELECT g FROM GeneralInfoContacto g WHERE g.apellidoMaterno = :apellidoMaterno")
    , @NamedQuery(name = "GeneralInfoContacto.findByApellidoPaterno", query = "SELECT g FROM GeneralInfoContacto g WHERE g.apellidoPaterno = :apellidoPaterno")
    , @NamedQuery(name = "GeneralInfoContacto.findByDireccion", query = "SELECT g FROM GeneralInfoContacto g WHERE g.direccion = :direccion")
    , @NamedQuery(name = "GeneralInfoContacto.findByCorreoElectronico", query = "SELECT g FROM GeneralInfoContacto g WHERE g.correoElectronico = :correoElectronico")
    , @NamedQuery(name = "GeneralInfoContacto.findByTelefono", query = "SELECT g FROM GeneralInfoContacto g WHERE g.telefono = :telefono")})
public class GeneralInfoContacto implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected GeneralInfoContactoPK generalInfoContactoPK;
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "generalInfoContacto")
    private Collection<GeneralInfoPersonal> generalInfoPersonalCollection;

    public GeneralInfoContacto() {
    }

    public GeneralInfoContacto(GeneralInfoContactoPK generalInfoContactoPK) {
        this.generalInfoContactoPK = generalInfoContactoPK;
    }

    public GeneralInfoContacto(GeneralInfoContactoPK generalInfoContactoPK, String apellidoMaterno, String apellidoPaterno, String direccion, String correoElectronico, String telefono) {
        this.generalInfoContactoPK = generalInfoContactoPK;
        this.apellidoMaterno = apellidoMaterno;
        this.apellidoPaterno = apellidoPaterno;
        this.direccion = direccion;
        this.correoElectronico = correoElectronico;
        this.telefono = telefono;
    }

    public GeneralInfoContacto(String rfc, String nombre) {
        this.generalInfoContactoPK = new GeneralInfoContactoPK(rfc, nombre);
    }

    public GeneralInfoContactoPK getGeneralInfoContactoPK() {
        return generalInfoContactoPK;
    }

    public void setGeneralInfoContactoPK(GeneralInfoContactoPK generalInfoContactoPK) {
        this.generalInfoContactoPK = generalInfoContactoPK;
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
    public Collection<GeneralInfoPersonal> getGeneralInfoPersonalCollection() {
        return generalInfoPersonalCollection;
    }

    public void setGeneralInfoPersonalCollection(Collection<GeneralInfoPersonal> generalInfoPersonalCollection) {
        this.generalInfoPersonalCollection = generalInfoPersonalCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (generalInfoContactoPK != null ? generalInfoContactoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GeneralInfoContacto)) {
            return false;
        }
        GeneralInfoContacto other = (GeneralInfoContacto) object;
        if ((this.generalInfoContactoPK == null && other.generalInfoContactoPK != null) || (this.generalInfoContactoPK != null && !this.generalInfoContactoPK.equals(other.generalInfoContactoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "proyecto_final_doctor.GeneralInfoContacto[ generalInfoContactoPK=" + generalInfoContactoPK + " ]";
    }
    
}
