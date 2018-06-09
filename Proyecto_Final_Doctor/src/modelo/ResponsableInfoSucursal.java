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
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name = "RESPONSABLE_INFO_SUCURSAL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ResponsableInfoSucursal.findAll", query = "SELECT r FROM ResponsableInfoSucursal r")
    , @NamedQuery(name = "ResponsableInfoSucursal.findByNoSeguroSocial", query = "SELECT r FROM ResponsableInfoSucursal r WHERE r.noSeguroSocial = :noSeguroSocial")
    , @NamedQuery(name = "ResponsableInfoSucursal.findBySucursal", query = "SELECT r FROM ResponsableInfoSucursal r WHERE r.sucursal = :sucursal")})
public class ResponsableInfoSucursal implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "NO_SEGURO_SOCIAL")
    private String noSeguroSocial;
    @Basic(optional = false)
    @Column(name = "SUCURSAL")
    private String sucursal;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "responsableInfoSucursal")
    private Collection<ResponsableInfoPersonal> responsableInfoPersonalCollection;

    public ResponsableInfoSucursal() {
    }

    public ResponsableInfoSucursal(String noSeguroSocial) {
        this.noSeguroSocial = noSeguroSocial;
    }

    public ResponsableInfoSucursal(String noSeguroSocial, String sucursal) {
        this.noSeguroSocial = noSeguroSocial;
        this.sucursal = sucursal;
    }

    public String getNoSeguroSocial() {
        return noSeguroSocial;
    }

    public void setNoSeguroSocial(String noSeguroSocial) {
        this.noSeguroSocial = noSeguroSocial;
    }

    public String getSucursal() {
        return sucursal;
    }

    public void setSucursal(String sucursal) {
        this.sucursal = sucursal;
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
        hash += (noSeguroSocial != null ? noSeguroSocial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ResponsableInfoSucursal)) {
            return false;
        }
        ResponsableInfoSucursal other = (ResponsableInfoSucursal) object;
        if ((this.noSeguroSocial == null && other.noSeguroSocial != null) || (this.noSeguroSocial != null && !this.noSeguroSocial.equals(other.noSeguroSocial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "proyecto_final_doctor.ResponsableInfoSucursal[ noSeguroSocial=" + noSeguroSocial + " ]";
    }
    
}
