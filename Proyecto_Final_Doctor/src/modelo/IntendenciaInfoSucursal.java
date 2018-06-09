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
@Table(name = "INTENDENCIA_INFO_SUCURSAL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IntendenciaInfoSucursal.findAll", query = "SELECT i FROM IntendenciaInfoSucursal i")
    , @NamedQuery(name = "IntendenciaInfoSucursal.findByNoSeguroSocial", query = "SELECT i FROM IntendenciaInfoSucursal i WHERE i.noSeguroSocial = :noSeguroSocial")
    , @NamedQuery(name = "IntendenciaInfoSucursal.findBySucursal", query = "SELECT i FROM IntendenciaInfoSucursal i WHERE i.sucursal = :sucursal")})
public class IntendenciaInfoSucursal implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "NO_SEGURO_SOCIAL")
    private String noSeguroSocial;
    @Basic(optional = false)
    @Column(name = "SUCURSAL")
    private String sucursal;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "intendenciaInfoSucursal")
    private Collection<IntendenciaInfoPersonal> intendenciaInfoPersonalCollection;

    public IntendenciaInfoSucursal() {
    }

    public IntendenciaInfoSucursal(String noSeguroSocial) {
        this.noSeguroSocial = noSeguroSocial;
    }

    public IntendenciaInfoSucursal(String noSeguroSocial, String sucursal) {
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
    public Collection<IntendenciaInfoPersonal> getIntendenciaInfoPersonalCollection() {
        return intendenciaInfoPersonalCollection;
    }

    public void setIntendenciaInfoPersonalCollection(Collection<IntendenciaInfoPersonal> intendenciaInfoPersonalCollection) {
        this.intendenciaInfoPersonalCollection = intendenciaInfoPersonalCollection;
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
        if (!(object instanceof IntendenciaInfoSucursal)) {
            return false;
        }
        IntendenciaInfoSucursal other = (IntendenciaInfoSucursal) object;
        if ((this.noSeguroSocial == null && other.noSeguroSocial != null) || (this.noSeguroSocial != null && !this.noSeguroSocial.equals(other.noSeguroSocial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "proyecto_final_doctor.IntendenciaInfoSucursal[ noSeguroSocial=" + noSeguroSocial + " ]";
    }
    
}
