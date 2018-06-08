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
public class RecetaInformacionEspecialPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "ID_RECETA")
    private BigInteger idReceta;
    @Basic(optional = false)
    @Column(name = "MEDICO")
    private BigInteger medico;

    public RecetaInformacionEspecialPK() {
    }

    public RecetaInformacionEspecialPK(BigInteger idReceta, BigInteger medico) {
        this.idReceta = idReceta;
        this.medico = medico;
    }

    public BigInteger getIdReceta() {
        return idReceta;
    }

    public void setIdReceta(BigInteger idReceta) {
        this.idReceta = idReceta;
    }

    public BigInteger getMedico() {
        return medico;
    }

    public void setMedico(BigInteger medico) {
        this.medico = medico;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idReceta != null ? idReceta.hashCode() : 0);
        hash += (medico != null ? medico.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RecetaInformacionEspecialPK)) {
            return false;
        }
        RecetaInformacionEspecialPK other = (RecetaInformacionEspecialPK) object;
        if ((this.idReceta == null && other.idReceta != null) || (this.idReceta != null && !this.idReceta.equals(other.idReceta))) {
            return false;
        }
        if ((this.medico == null && other.medico != null) || (this.medico != null && !this.medico.equals(other.medico))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "proyecto_final_doctor.RecetaInformacionEspecialPK[ idReceta=" + idReceta + ", medico=" + medico + " ]";
    }
    
}
