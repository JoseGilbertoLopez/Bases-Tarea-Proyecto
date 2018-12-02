/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "TELEFONOS_SUCURSALES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TelefonosSucursales.findAll", query = "SELECT t FROM TelefonosSucursales t")
    , @NamedQuery(name = "TelefonosSucursales.findByIdTelefono", query = "SELECT t FROM TelefonosSucursales t WHERE t.idTelefono = :idTelefono")
    , @NamedQuery(name = "TelefonosSucursales.findByTelefono", query = "SELECT t FROM TelefonosSucursales t WHERE t.telefono = :telefono")})
public class TelefonosSucursales implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID_TELEFONO")
    private BigDecimal idTelefono;
    @Column(name = "TELEFONO")
    private String telefono;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "telefonosSucursales")
    private Collection<TelefonoSucursal> telefonoSucursalCollection;

    public TelefonosSucursales() {
    }

    public TelefonosSucursales(BigDecimal idTelefono) {
        this.idTelefono = idTelefono;
    }

    public BigDecimal getIdTelefono() {
        return idTelefono;
    }

    public void setIdTelefono(BigDecimal idTelefono) {
        this.idTelefono = idTelefono;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @XmlTransient
    public Collection<TelefonoSucursal> getTelefonoSucursalCollection() {
        return telefonoSucursalCollection;
    }

    public void setTelefonoSucursalCollection(Collection<TelefonoSucursal> telefonoSucursalCollection) {
        this.telefonoSucursalCollection = telefonoSucursalCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTelefono != null ? idTelefono.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TelefonosSucursales)) {
            return false;
        }
        TelefonosSucursales other = (TelefonosSucursales) object;
        if ((this.idTelefono == null && other.idTelefono != null) || (this.idTelefono != null && !this.idTelefono.equals(other.idTelefono))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "proyecto_final_doctor.TelefonosSucursales[ idTelefono=" + idTelefono + " ]";
    }
    
}
