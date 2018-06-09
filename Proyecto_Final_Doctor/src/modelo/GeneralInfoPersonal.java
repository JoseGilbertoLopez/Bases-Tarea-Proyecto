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
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
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
@Table(name = "GENERAL_INFO_PERSONAL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GeneralInfoPersonal.findAll", query = "SELECT g FROM GeneralInfoPersonal g")
    , @NamedQuery(name = "GeneralInfoPersonal.findByIdGeneral", query = "SELECT g FROM GeneralInfoPersonal g WHERE g.generalInfoPersonalPK.idGeneral = :idGeneral")
    , @NamedQuery(name = "GeneralInfoPersonal.findByNoSeguroSocial", query = "SELECT g FROM GeneralInfoPersonal g WHERE g.generalInfoPersonalPK.noSeguroSocial = :noSeguroSocial")
    , @NamedQuery(name = "GeneralInfoPersonal.findByRfc", query = "SELECT g FROM GeneralInfoPersonal g WHERE g.generalInfoPersonalPK.rfc = :rfc")
    , @NamedQuery(name = "GeneralInfoPersonal.findByApellidoPaterno", query = "SELECT g FROM GeneralInfoPersonal g WHERE g.apellidoPaterno = :apellidoPaterno")
    , @NamedQuery(name = "GeneralInfoPersonal.findByApellidoMaterno", query = "SELECT g FROM GeneralInfoPersonal g WHERE g.apellidoMaterno = :apellidoMaterno")})
public class GeneralInfoPersonal implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected GeneralInfoPersonalPK generalInfoPersonalPK;
    @Basic(optional = false)
    @Column(name = "APELLIDO_PATERNO")
    private String apellidoPaterno;
    @Basic(optional = false)
    @Column(name = "APELLIDO_MATERNO")
    private String apellidoMaterno;
    @JoinColumns({
        @JoinColumn(name = "RFC", referencedColumnName = "RFC", insertable = false, updatable = false)
        , @JoinColumn(name = "NOMBRE", referencedColumnName = "NOMBRE")})
    @ManyToOne(optional = false)
    private GeneralInfoContacto generalInfoContacto;
    @JoinColumn(name = "NO_SEGURO_SOCIAL", referencedColumnName = "NO_SEGURO_SOCIAL", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private GeneralInfoSucursal generalInfoSucursal;

    public GeneralInfoPersonal() {
    }

    public GeneralInfoPersonal(GeneralInfoPersonalPK generalInfoPersonalPK) {
        this.generalInfoPersonalPK = generalInfoPersonalPK;
    }

    public GeneralInfoPersonal(GeneralInfoPersonalPK generalInfoPersonalPK, String apellidoPaterno, String apellidoMaterno) {
        this.generalInfoPersonalPK = generalInfoPersonalPK;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
    }

    public GeneralInfoPersonal(BigInteger idGeneral, String noSeguroSocial, String rfc) {
        this.generalInfoPersonalPK = new GeneralInfoPersonalPK(idGeneral, noSeguroSocial, rfc);
    }

    public GeneralInfoPersonalPK getGeneralInfoPersonalPK() {
        return generalInfoPersonalPK;
    }

    public void setGeneralInfoPersonalPK(GeneralInfoPersonalPK generalInfoPersonalPK) {
        this.generalInfoPersonalPK = generalInfoPersonalPK;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public GeneralInfoContacto getGeneralInfoContacto() {
        return generalInfoContacto;
    }

    public void setGeneralInfoContacto(GeneralInfoContacto generalInfoContacto) {
        this.generalInfoContacto = generalInfoContacto;
    }

    public GeneralInfoSucursal getGeneralInfoSucursal() {
        return generalInfoSucursal;
    }

    public void setGeneralInfoSucursal(GeneralInfoSucursal generalInfoSucursal) {
        this.generalInfoSucursal = generalInfoSucursal;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (generalInfoPersonalPK != null ? generalInfoPersonalPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GeneralInfoPersonal)) {
            return false;
        }
        GeneralInfoPersonal other = (GeneralInfoPersonal) object;
        if ((this.generalInfoPersonalPK == null && other.generalInfoPersonalPK != null) || (this.generalInfoPersonalPK != null && !this.generalInfoPersonalPK.equals(other.generalInfoPersonalPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "proyecto_final_doctor.GeneralInfoPersonal[ generalInfoPersonalPK=" + generalInfoPersonalPK + " ]";
    }
    
}
