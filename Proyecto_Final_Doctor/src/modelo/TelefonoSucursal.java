/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Jose G
 */
@Entity
@Table(name = "TELEFONO_SUCURSAL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TelefonoSucursal.findAll", query = "SELECT t FROM TelefonoSucursal t")
    , @NamedQuery(name = "TelefonoSucursal.findByIdTelefono", query = "SELECT t FROM TelefonoSucursal t WHERE t.telefonoSucursalPK.idTelefono = :idTelefono")
    , @NamedQuery(name = "TelefonoSucursal.findByIdSucursal", query = "SELECT t FROM TelefonoSucursal t WHERE t.telefonoSucursalPK.idSucursal = :idSucursal")
    , @NamedQuery(name = "TelefonoSucursal.findByDireccion", query = "SELECT t FROM TelefonoSucursal t WHERE t.telefonoSucursalPK.direccion = :direccion")})
public class TelefonoSucursal implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected TelefonoSucursalPK telefonoSucursalPK;
    @JoinColumn(name = "ID_SUCURSAL", referencedColumnName = "ID_SUCURSAL", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Sucursal sucursal;
    @JoinColumn(name = "ID_TELEFONO", referencedColumnName = "ID_TELEFONO", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private TelefonosSucursales telefonosSucursales;

    public TelefonoSucursal() {
    }

    public TelefonoSucursal(TelefonoSucursalPK telefonoSucursalPK) {
        this.telefonoSucursalPK = telefonoSucursalPK;
    }

    public TelefonoSucursal(BigInteger idTelefono, BigInteger idSucursal, String direccion) {
        this.telefonoSucursalPK = new TelefonoSucursalPK(idTelefono, idSucursal, direccion);
    }

    public TelefonoSucursalPK getTelefonoSucursalPK() {
        return telefonoSucursalPK;
    }

    public void setTelefonoSucursalPK(TelefonoSucursalPK telefonoSucursalPK) {
        this.telefonoSucursalPK = telefonoSucursalPK;
    }

    public Sucursal getSucursal() {
        return sucursal;
    }

    public void setSucursal(Sucursal sucursal) {
        this.sucursal = sucursal;
    }

    public TelefonosSucursales getTelefonosSucursales() {
        return telefonosSucursales;
    }

    public void setTelefonosSucursales(TelefonosSucursales telefonosSucursales) {
        this.telefonosSucursales = telefonosSucursales;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (telefonoSucursalPK != null ? telefonoSucursalPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TelefonoSucursal)) {
            return false;
        }
        TelefonoSucursal other = (TelefonoSucursal) object;
        if ((this.telefonoSucursalPK == null && other.telefonoSucursalPK != null) || (this.telefonoSucursalPK != null && !this.telefonoSucursalPK.equals(other.telefonoSucursalPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "proyecto_final_doctor.TelefonoSucursal[ telefonoSucursalPK=" + telefonoSucursalPK + " ]";
    }
    
}
