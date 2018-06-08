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
@Table(name = "MEDICO_INFORMACION_SUCURSAL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MedicoInformacionSucursal.findAll", query = "SELECT m FROM MedicoInformacionSucursal m")
    , @NamedQuery(name = "MedicoInformacionSucursal.findByNoSeguroSocial", query = "SELECT m FROM MedicoInformacionSucursal m WHERE m.medicoInformacionSucursalPK.noSeguroSocial = :noSeguroSocial")
    , @NamedQuery(name = "MedicoInformacionSucursal.findByCedulaProfesional", query = "SELECT m FROM MedicoInformacionSucursal m WHERE m.medicoInformacionSucursalPK.cedulaProfesional = :cedulaProfesional")
    , @NamedQuery(name = "MedicoInformacionSucursal.findBySucursal", query = "SELECT m FROM MedicoInformacionSucursal m WHERE m.sucursal = :sucursal")})
public class MedicoInformacionSucursal implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected MedicoInformacionSucursalPK medicoInformacionSucursalPK;
    @Basic(optional = false)
    @Column(name = "SUCURSAL")
    private String sucursal;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "medicoInformacionSucursal")
    private Collection<MedicoInformacionPersonal> medicoInformacionPersonalCollection;

    public MedicoInformacionSucursal() {
    }

    public MedicoInformacionSucursal(MedicoInformacionSucursalPK medicoInformacionSucursalPK) {
        this.medicoInformacionSucursalPK = medicoInformacionSucursalPK;
    }

    public MedicoInformacionSucursal(MedicoInformacionSucursalPK medicoInformacionSucursalPK, String sucursal) {
        this.medicoInformacionSucursalPK = medicoInformacionSucursalPK;
        this.sucursal = sucursal;
    }

    public MedicoInformacionSucursal(String noSeguroSocial, String cedulaProfesional) {
        this.medicoInformacionSucursalPK = new MedicoInformacionSucursalPK(noSeguroSocial, cedulaProfesional);
    }

    public MedicoInformacionSucursalPK getMedicoInformacionSucursalPK() {
        return medicoInformacionSucursalPK;
    }

    public void setMedicoInformacionSucursalPK(MedicoInformacionSucursalPK medicoInformacionSucursalPK) {
        this.medicoInformacionSucursalPK = medicoInformacionSucursalPK;
    }

    public String getSucursal() {
        return sucursal;
    }

    public void setSucursal(String sucursal) {
        this.sucursal = sucursal;
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
        hash += (medicoInformacionSucursalPK != null ? medicoInformacionSucursalPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MedicoInformacionSucursal)) {
            return false;
        }
        MedicoInformacionSucursal other = (MedicoInformacionSucursal) object;
        if ((this.medicoInformacionSucursalPK == null && other.medicoInformacionSucursalPK != null) || (this.medicoInformacionSucursalPK != null && !this.medicoInformacionSucursalPK.equals(other.medicoInformacionSucursalPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "proyecto_final_doctor.MedicoInformacionSucursal[ medicoInformacionSucursalPK=" + medicoInformacionSucursalPK + " ]";
    }
    
}
