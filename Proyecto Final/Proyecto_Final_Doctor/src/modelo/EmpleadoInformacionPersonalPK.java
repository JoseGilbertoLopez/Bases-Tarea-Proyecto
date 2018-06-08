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
public class EmpleadoInformacionPersonalPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "ID_CLIENTE")
    private BigInteger idCliente;
    @Basic(optional = false)
    @Column(name = "NO_SEGURO_SOCIAL")
    private String noSeguroSocial;
    @Basic(optional = false)
    @Column(name = "RFC")
    private String rfc;

    public EmpleadoInformacionPersonalPK() {
    }

    public EmpleadoInformacionPersonalPK(BigInteger idCliente, String noSeguroSocial, String rfc) {
        this.idCliente = idCliente;
        this.noSeguroSocial = noSeguroSocial;
        this.rfc = rfc;
    }

    public BigInteger getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(BigInteger idCliente) {
        this.idCliente = idCliente;
    }

    public String getNoSeguroSocial() {
        return noSeguroSocial;
    }

    public void setNoSeguroSocial(String noSeguroSocial) {
        this.noSeguroSocial = noSeguroSocial;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCliente != null ? idCliente.hashCode() : 0);
        hash += (noSeguroSocial != null ? noSeguroSocial.hashCode() : 0);
        hash += (rfc != null ? rfc.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EmpleadoInformacionPersonalPK)) {
            return false;
        }
        EmpleadoInformacionPersonalPK other = (EmpleadoInformacionPersonalPK) object;
        if ((this.idCliente == null && other.idCliente != null) || (this.idCliente != null && !this.idCliente.equals(other.idCliente))) {
            return false;
        }
        if ((this.noSeguroSocial == null && other.noSeguroSocial != null) || (this.noSeguroSocial != null && !this.noSeguroSocial.equals(other.noSeguroSocial))) {
            return false;
        }
        if ((this.rfc == null && other.rfc != null) || (this.rfc != null && !this.rfc.equals(other.rfc))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "proyecto_final_doctor.EmpleadoInformacionPersonalPK[ idCliente=" + idCliente + ", noSeguroSocial=" + noSeguroSocial + ", rfc=" + rfc + " ]";
    }
    
}
