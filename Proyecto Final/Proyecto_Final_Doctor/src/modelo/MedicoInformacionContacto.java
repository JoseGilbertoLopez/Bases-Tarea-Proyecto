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
@Table(name = "MEDICO_INFORMACION_CONTACTO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MedicoInformacionContacto.findAll", query = "SELECT m FROM MedicoInformacionContacto m")
    , @NamedQuery(name = "MedicoInformacionContacto.findByNoSeguroSocial", query = "SELECT m FROM MedicoInformacionContacto m WHERE m.medicoInformacionContactoPK.noSeguroSocial = :noSeguroSocial")
    , @NamedQuery(name = "MedicoInformacionContacto.findByCedulaProfesional", query = "SELECT m FROM MedicoInformacionContacto m WHERE m.medicoInformacionContactoPK.cedulaProfesional = :cedulaProfesional")
    , @NamedQuery(name = "MedicoInformacionContacto.findByNombre", query = "SELECT m FROM MedicoInformacionContacto m WHERE m.medicoInformacionContactoPK.nombre = :nombre")
    , @NamedQuery(name = "MedicoInformacionContacto.findByApellidoPaterno", query = "SELECT m FROM MedicoInformacionContacto m WHERE m.medicoInformacionContactoPK.apellidoPaterno = :apellidoPaterno")
    , @NamedQuery(name = "MedicoInformacionContacto.findByApellidoMaterno", query = "SELECT m FROM MedicoInformacionContacto m WHERE m.medicoInformacionContactoPK.apellidoMaterno = :apellidoMaterno")
    , @NamedQuery(name = "MedicoInformacionContacto.findByDireccion", query = "SELECT m FROM MedicoInformacionContacto m WHERE m.direccion = :direccion")
    , @NamedQuery(name = "MedicoInformacionContacto.findByTelefono", query = "SELECT m FROM MedicoInformacionContacto m WHERE m.telefono = :telefono")
    , @NamedQuery(name = "MedicoInformacionContacto.findByCorreoElectronico", query = "SELECT m FROM MedicoInformacionContacto m WHERE m.correoElectronico = :correoElectronico")})
public class MedicoInformacionContacto implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected MedicoInformacionContactoPK medicoInformacionContactoPK;
    @Basic(optional = false)
    @Column(name = "DIRECCION")
    private String direccion;
    @Basic(optional = false)
    @Column(name = "TELEFONO")
    private String telefono;
    @Basic(optional = false)
    @Column(name = "CORREO_ELECTRONICO")
    private String correoElectronico;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "medicoInformacionContacto")
    private Collection<MedicoInformacionPersonal> medicoInformacionPersonalCollection;

    public MedicoInformacionContacto() {
    }

    public MedicoInformacionContacto(MedicoInformacionContactoPK medicoInformacionContactoPK) {
        this.medicoInformacionContactoPK = medicoInformacionContactoPK;
    }

    public MedicoInformacionContacto(MedicoInformacionContactoPK medicoInformacionContactoPK, String direccion, String telefono, String correoElectronico) {
        this.medicoInformacionContactoPK = medicoInformacionContactoPK;
        this.direccion = direccion;
        this.telefono = telefono;
        this.correoElectronico = correoElectronico;
    }

    public MedicoInformacionContacto(String noSeguroSocial, String cedulaProfesional, String nombre, String apellidoPaterno, String apellidoMaterno) {
        this.medicoInformacionContactoPK = new MedicoInformacionContactoPK(noSeguroSocial, cedulaProfesional, nombre, apellidoPaterno, apellidoMaterno);
    }

    public MedicoInformacionContactoPK getMedicoInformacionContactoPK() {
        return medicoInformacionContactoPK;
    }

    public void setMedicoInformacionContactoPK(MedicoInformacionContactoPK medicoInformacionContactoPK) {
        this.medicoInformacionContactoPK = medicoInformacionContactoPK;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    @XmlTransient
    public Collection<MedicoInformacionPersonal> getMedicoInformacionPersonalCollection() {
        return medicoInformacionPersonalCollection;
    }

    public void setMedicoInformacionPersonalCollection(Collection<MedicoInformacionPersonal> medicoInformacionPersonalCollection) {
        this.medicoInformacionPersonalCollection = medicoInformacionPersonalCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (medicoInformacionContactoPK != null ? medicoInformacionContactoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MedicoInformacionContacto)) {
            return false;
        }
        MedicoInformacionContacto other = (MedicoInformacionContacto) object;
        if ((this.medicoInformacionContactoPK == null && other.medicoInformacionContactoPK != null) || (this.medicoInformacionContactoPK != null && !this.medicoInformacionContactoPK.equals(other.medicoInformacionContactoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "proyecto_final_doctor.MedicoInformacionContacto[ medicoInformacionContactoPK=" + medicoInformacionContactoPK + " ]";
    }
    
}
