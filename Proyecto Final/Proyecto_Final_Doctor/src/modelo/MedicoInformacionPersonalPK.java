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
public class MedicoInformacionPersonalPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "ID_MEDICO")
    private BigInteger idMedico;
    @Basic(optional = false)
    @Column(name = "NO_SEGURO_SOCIAL")
    private String noSeguroSocial;
    @Basic(optional = false)
    @Column(name = "RFC")
    private String rfc;
    @Basic(optional = false)
    @Column(name = "CEDULA_PROFESIONAL")
    private String cedulaProfesional;

    public MedicoInformacionPersonalPK() {
    }

    public MedicoInformacionPersonalPK(BigInteger idMedico, String noSeguroSocial, String rfc, String cedulaProfesional) {
        this.idMedico = idMedico;
        this.noSeguroSocial = noSeguroSocial;
        this.rfc = rfc;
        this.cedulaProfesional = cedulaProfesional;
    }

    public BigInteger getIdMedico() {
        return idMedico;
    }

    public void setIdMedico(BigInteger idMedico) {
        this.idMedico = idMedico;
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

    public String getCedulaProfesional() {
        return cedulaProfesional;
    }

    public void setCedulaProfesional(String cedulaProfesional) {
        this.cedulaProfesional = cedulaProfesional;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMedico != null ? idMedico.hashCode() : 0);
        hash += (noSeguroSocial != null ? noSeguroSocial.hashCode() : 0);
        hash += (rfc != null ? rfc.hashCode() : 0);
        hash += (cedulaProfesional != null ? cedulaProfesional.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MedicoInformacionPersonalPK)) {
            return false;
        }
        MedicoInformacionPersonalPK other = (MedicoInformacionPersonalPK) object;
        if ((this.idMedico == null && other.idMedico != null) || (this.idMedico != null && !this.idMedico.equals(other.idMedico))) {
            return false;
        }
        if ((this.noSeguroSocial == null && other.noSeguroSocial != null) || (this.noSeguroSocial != null && !this.noSeguroSocial.equals(other.noSeguroSocial))) {
            return false;
        }
        if ((this.rfc == null && other.rfc != null) || (this.rfc != null && !this.rfc.equals(other.rfc))) {
            return false;
        }
        if ((this.cedulaProfesional == null && other.cedulaProfesional != null) || (this.cedulaProfesional != null && !this.cedulaProfesional.equals(other.cedulaProfesional))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "proyecto_final_doctor.MedicoInformacionPersonalPK[ idMedico=" + idMedico + ", noSeguroSocial=" + noSeguroSocial + ", rfc=" + rfc + ", cedulaProfesional=" + cedulaProfesional + " ]";
    }
    
}
