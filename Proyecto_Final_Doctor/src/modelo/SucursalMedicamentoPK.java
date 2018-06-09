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
public class SucursalMedicamentoPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "ID_SUCURSAL")
    private BigInteger idSucursal;
    @Basic(optional = false)
    @Column(name = "MARCA")
    private String marca;
    @Basic(optional = false)
    @Column(name = "NOMBRE")
    private String nombre;

    public SucursalMedicamentoPK() {
    }

    public SucursalMedicamentoPK(BigInteger idSucursal, String marca, String nombre) {
        this.idSucursal = idSucursal;
        this.marca = marca;
        this.nombre = nombre;
    }

    public BigInteger getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(BigInteger idSucursal) {
        this.idSucursal = idSucursal;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSucursal != null ? idSucursal.hashCode() : 0);
        hash += (marca != null ? marca.hashCode() : 0);
        hash += (nombre != null ? nombre.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SucursalMedicamentoPK)) {
            return false;
        }
        SucursalMedicamentoPK other = (SucursalMedicamentoPK) object;
        if ((this.idSucursal == null && other.idSucursal != null) || (this.idSucursal != null && !this.idSucursal.equals(other.idSucursal))) {
            return false;
        }
        if ((this.marca == null && other.marca != null) || (this.marca != null && !this.marca.equals(other.marca))) {
            return false;
        }
        if ((this.nombre == null && other.nombre != null) || (this.nombre != null && !this.nombre.equals(other.nombre))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "proyecto_final_doctor.SucursalMedicamentoPK[ idSucursal=" + idSucursal + ", marca=" + marca + ", nombre=" + nombre + " ]";
    }
    
}
