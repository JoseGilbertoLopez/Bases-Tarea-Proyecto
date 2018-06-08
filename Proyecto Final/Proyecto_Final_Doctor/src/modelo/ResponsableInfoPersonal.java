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
@Table(name = "RESPONSABLE_INFO_PERSONAL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ResponsableInfoPersonal.findAll", query = "SELECT r FROM ResponsableInfoPersonal r")
    , @NamedQuery(name = "ResponsableInfoPersonal.findByIdResponsable", query = "SELECT r FROM ResponsableInfoPersonal r WHERE r.responsableInfoPersonalPK.idResponsable = :idResponsable")
    , @NamedQuery(name = "ResponsableInfoPersonal.findByNoSeguroSocial", query = "SELECT r FROM ResponsableInfoPersonal r WHERE r.responsableInfoPersonalPK.noSeguroSocial = :noSeguroSocial")
    , @NamedQuery(name = "ResponsableInfoPersonal.findByRfc", query = "SELECT r FROM ResponsableInfoPersonal r WHERE r.responsableInfoPersonalPK.rfc = :rfc")
    , @NamedQuery(name = "ResponsableInfoPersonal.findByApellidoPaterno", query = "SELECT r FROM ResponsableInfoPersonal r WHERE r.apellidoPaterno = :apellidoPaterno")
    , @NamedQuery(name = "ResponsableInfoPersonal.findByApellidoMaterno", query = "SELECT r FROM ResponsableInfoPersonal r WHERE r.apellidoMaterno = :apellidoMaterno")})
public class ResponsableInfoPersonal implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ResponsableInfoPersonalPK responsableInfoPersonalPK;
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
    private ResponsableInfoContacto responsableInfoContacto;
    @JoinColumn(name = "NO_SEGURO_SOCIAL", referencedColumnName = "NO_SEGURO_SOCIAL", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private ResponsableInfoSucursal responsableInfoSucursal;

    public ResponsableInfoPersonal() {
    }

    public ResponsableInfoPersonal(ResponsableInfoPersonalPK responsableInfoPersonalPK) {
        this.responsableInfoPersonalPK = responsableInfoPersonalPK;
    }

    public ResponsableInfoPersonal(ResponsableInfoPersonalPK responsableInfoPersonalPK, String apellidoPaterno, String apellidoMaterno) {
        this.responsableInfoPersonalPK = responsableInfoPersonalPK;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
    }

    public ResponsableInfoPersonal(BigInteger idResponsable, String noSeguroSocial, String rfc) {
        this.responsableInfoPersonalPK = new ResponsableInfoPersonalPK(idResponsable, noSeguroSocial, rfc);
    }

    public ResponsableInfoPersonalPK getResponsableInfoPersonalPK() {
        return responsableInfoPersonalPK;
    }

    public void setResponsableInfoPersonalPK(ResponsableInfoPersonalPK responsableInfoPersonalPK) {
        this.responsableInfoPersonalPK = responsableInfoPersonalPK;
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

    public ResponsableInfoContacto getResponsableInfoContacto() {
        return responsableInfoContacto;
    }

    public void setResponsableInfoContacto(ResponsableInfoContacto responsableInfoContacto) {
        this.responsableInfoContacto = responsableInfoContacto;
    }

    public ResponsableInfoSucursal getResponsableInfoSucursal() {
        return responsableInfoSucursal;
    }

    public void setResponsableInfoSucursal(ResponsableInfoSucursal responsableInfoSucursal) {
        this.responsableInfoSucursal = responsableInfoSucursal;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (responsableInfoPersonalPK != null ? responsableInfoPersonalPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ResponsableInfoPersonal)) {
            return false;
        }
        ResponsableInfoPersonal other = (ResponsableInfoPersonal) object;
        if ((this.responsableInfoPersonalPK == null && other.responsableInfoPersonalPK != null) || (this.responsableInfoPersonalPK != null && !this.responsableInfoPersonalPK.equals(other.responsableInfoPersonalPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "proyecto_final_doctor.ResponsableInfoPersonal[ responsableInfoPersonalPK=" + responsableInfoPersonalPK + " ]";
    }
    
}
