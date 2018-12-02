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
public class IntendenciaInfoPersonalPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "ID_INTENDENCIA")
    private BigInteger idIntendencia;
    @Basic(optional = false)
    @Column(name = "NO_SEGURO_SOCIAL")
    private String noSeguroSocial;
    @Basic(optional = false)
    @Column(name = "RFC")
    private String rfc;

    public IntendenciaInfoPersonalPK() {
    }

    public IntendenciaInfoPersonalPK(BigInteger idIntendencia, String noSeguroSocial, String rfc) {
        this.idIntendencia = idIntendencia;
        this.noSeguroSocial = noSeguroSocial;
        this.rfc = rfc;
    }

    public BigInteger getIdIntendencia() {
        return idIntendencia;
    }

    public void setIdIntendencia(BigInteger idIntendencia) {
        this.idIntendencia = idIntendencia;
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
        hash += (idIntendencia != null ? idIntendencia.hashCode() : 0);
        hash += (noSeguroSocial != null ? noSeguroSocial.hashCode() : 0);
        hash += (rfc != null ? rfc.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof IntendenciaInfoPersonalPK)) {
            return false;
        }
        IntendenciaInfoPersonalPK other = (IntendenciaInfoPersonalPK) object;
        if ((this.idIntendencia == null && other.idIntendencia != null) || (this.idIntendencia != null && !this.idIntendencia.equals(other.idIntendencia))) {
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
        return "proyecto_final_doctor.IntendenciaInfoPersonalPK[ idIntendencia=" + idIntendencia + ", noSeguroSocial=" + noSeguroSocial + ", rfc=" + rfc + " ]";
    }
    
}
