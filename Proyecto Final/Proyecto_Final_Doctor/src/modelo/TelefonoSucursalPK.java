/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Jose G
 */
@Embeddable
public class TelefonoSucursalPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "ID_TELEFONO")
    private BigInteger idTelefono;
    @Basic(optional = false)
    @Column(name = "ID_SUCURSAL")
    private BigInteger idSucursal;
    @Basic(optional = false)
    @Column(name = "DIRECCION")
    private String direccion;

    public TelefonoSucursalPK() {
    }

    public TelefonoSucursalPK(BigInteger idTelefono, BigInteger idSucursal, String direccion) {
        this.idTelefono = idTelefono;
        this.idSucursal = idSucursal;
        this.direccion = direccion;
    }

    public BigInteger getIdTelefono() {
        return idTelefono;
    }

    public void setIdTelefono(BigInteger idTelefono) {
        this.idTelefono = idTelefono;
    }

    public BigInteger getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(BigInteger idSucursal) {
        this.idSucursal = idSucursal;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTelefono != null ? idTelefono.hashCode() : 0);
        hash += (idSucursal != null ? idSucursal.hashCode() : 0);
        hash += (direccion != null ? direccion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TelefonoSucursalPK)) {
            return false;
        }
        TelefonoSucursalPK other = (TelefonoSucursalPK) object;
        if ((this.idTelefono == null && other.idTelefono != null) || (this.idTelefono != null && !this.idTelefono.equals(other.idTelefono))) {
            return false;
        }
        if ((this.idSucursal == null && other.idSucursal != null) || (this.idSucursal != null && !this.idSucursal.equals(other.idSucursal))) {
            return false;
        }
        if ((this.direccion == null && other.direccion != null) || (this.direccion != null && !this.direccion.equals(other.direccion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "proyecto_final_doctor.TelefonoSucursalPK[ idTelefono=" + idTelefono + ", idSucursal=" + idSucursal + ", direccion=" + direccion + " ]";
    }
    
}
