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
@Table(name = "MOSTRADOR_INFO_CONTACTO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MostradorInfoContacto.findAll", query = "SELECT m FROM MostradorInfoContacto m")
    , @NamedQuery(name = "MostradorInfoContacto.findByRfc", query = "SELECT m FROM MostradorInfoContacto m WHERE m.mostradorInfoContactoPK.rfc = :rfc")
    , @NamedQuery(name = "MostradorInfoContacto.findByNombre", query = "SELECT m FROM MostradorInfoContacto m WHERE m.mostradorInfoContactoPK.nombre = :nombre")
    , @NamedQuery(name = "MostradorInfoContacto.findByApellidoMaterno", query = "SELECT m FROM MostradorInfoContacto m WHERE m.apellidoMaterno = :apellidoMaterno")
    , @NamedQuery(name = "MostradorInfoContacto.findByApellidoPaterno", query = "SELECT m FROM MostradorInfoContacto m WHERE m.apellidoPaterno = :apellidoPaterno")
    , @NamedQuery(name = "MostradorInfoContacto.findByDireccion", query = "SELECT m FROM MostradorInfoContacto m WHERE m.direccion = :direccion")
    , @NamedQuery(name = "MostradorInfoContacto.findByCorreoElectronico", query = "SELECT m FROM MostradorInfoContacto m WHERE m.correoElectronico = :correoElectronico")
    , @NamedQuery(name = "MostradorInfoContacto.findByTelefono", query = "SELECT m FROM MostradorInfoContacto m WHERE m.telefono = :telefono")})
public class MostradorInfoContacto implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected MostradorInfoContactoPK mostradorInfoContactoPK;
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "mostradorInfoContacto")
    private Collection<MostradorInfoPersonal> mostradorInfoPersonalCollection;

    public MostradorInfoContacto() {
    }

    public MostradorInfoContacto(MostradorInfoContactoPK mostradorInfoContactoPK) {
        this.mostradorInfoContactoPK = mostradorInfoContactoPK;
    }

    public MostradorInfoContacto(MostradorInfoContactoPK mostradorInfoContactoPK, String apellidoMaterno, String apellidoPaterno, String direccion, String correoElectronico, String telefono) {
        this.mostradorInfoContactoPK = mostradorInfoContactoPK;
        this.apellidoMaterno = apellidoMaterno;
        this.apellidoPaterno = apellidoPaterno;
        this.direccion = direccion;
        this.correoElectronico = correoElectronico;
        this.telefono = telefono;
    }

    public MostradorInfoContacto(String rfc, String nombre) {
        this.mostradorInfoContactoPK = new MostradorInfoContactoPK(rfc, nombre);
    }

    public MostradorInfoContactoPK getMostradorInfoContactoPK() {
        return mostradorInfoContactoPK;
    }

    public void setMostradorInfoContactoPK(MostradorInfoContactoPK mostradorInfoContactoPK) {
        this.mostradorInfoContactoPK = mostradorInfoContactoPK;
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
    public Collection<MostradorInfoPersonal> getMostradorInfoPersonalCollection() {
        return mostradorInfoPersonalCollection;
    }

    public void setMostradorInfoPersonalCollection(Collection<MostradorInfoPersonal> mostradorInfoPersonalCollection) {
        this.mostradorInfoPersonalCollection = mostradorInfoPersonalCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (mostradorInfoContactoPK != null ? mostradorInfoContactoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MostradorInfoContacto)) {
            return false;
        }
        MostradorInfoContacto other = (MostradorInfoContacto) object;
        if ((this.mostradorInfoContactoPK == null && other.mostradorInfoContactoPK != null) || (this.mostradorInfoContactoPK != null && !this.mostradorInfoContactoPK.equals(other.mostradorInfoContactoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "proyecto_final_doctor.MostradorInfoContacto[ mostradorInfoContactoPK=" + mostradorInfoContactoPK + " ]";
    }
    
}
