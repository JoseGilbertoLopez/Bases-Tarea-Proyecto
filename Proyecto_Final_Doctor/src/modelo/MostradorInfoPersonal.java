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
@Table(name = "MOSTRADOR_INFO_PERSONAL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MostradorInfoPersonal.findAll", query = "SELECT m FROM MostradorInfoPersonal m")
    , @NamedQuery(name = "MostradorInfoPersonal.findByIdMostrador", query = "SELECT m FROM MostradorInfoPersonal m WHERE m.mostradorInfoPersonalPK.idMostrador = :idMostrador")
    , @NamedQuery(name = "MostradorInfoPersonal.findByNoSeguroSocial", query = "SELECT m FROM MostradorInfoPersonal m WHERE m.mostradorInfoPersonalPK.noSeguroSocial = :noSeguroSocial")
    , @NamedQuery(name = "MostradorInfoPersonal.findByRfc", query = "SELECT m FROM MostradorInfoPersonal m WHERE m.mostradorInfoPersonalPK.rfc = :rfc")
    , @NamedQuery(name = "MostradorInfoPersonal.findByApellidoPaterno", query = "SELECT m FROM MostradorInfoPersonal m WHERE m.apellidoPaterno = :apellidoPaterno")
    , @NamedQuery(name = "MostradorInfoPersonal.findByApellidoMaterno", query = "SELECT m FROM MostradorInfoPersonal m WHERE m.apellidoMaterno = :apellidoMaterno")})
public class MostradorInfoPersonal implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected MostradorInfoPersonalPK mostradorInfoPersonalPK;
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
    private MostradorInfoContacto mostradorInfoContacto;
    @JoinColumn(name = "NO_SEGURO_SOCIAL", referencedColumnName = "NO_SEGURO_SOCIAL", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private MostradorInfoSucursal mostradorInfoSucursal;

    public MostradorInfoPersonal() {
    }

    public MostradorInfoPersonal(MostradorInfoPersonalPK mostradorInfoPersonalPK) {
        this.mostradorInfoPersonalPK = mostradorInfoPersonalPK;
    }

    public MostradorInfoPersonal(MostradorInfoPersonalPK mostradorInfoPersonalPK, String apellidoPaterno, String apellidoMaterno) {
        this.mostradorInfoPersonalPK = mostradorInfoPersonalPK;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
    }

    public MostradorInfoPersonal(BigInteger idMostrador, String noSeguroSocial, String rfc) {
        this.mostradorInfoPersonalPK = new MostradorInfoPersonalPK(idMostrador, noSeguroSocial, rfc);
    }

    public MostradorInfoPersonalPK getMostradorInfoPersonalPK() {
        return mostradorInfoPersonalPK;
    }

    public void setMostradorInfoPersonalPK(MostradorInfoPersonalPK mostradorInfoPersonalPK) {
        this.mostradorInfoPersonalPK = mostradorInfoPersonalPK;
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

    public MostradorInfoContacto getMostradorInfoContacto() {
        return mostradorInfoContacto;
    }

    public void setMostradorInfoContacto(MostradorInfoContacto mostradorInfoContacto) {
        this.mostradorInfoContacto = mostradorInfoContacto;
    }

    public MostradorInfoSucursal getMostradorInfoSucursal() {
        return mostradorInfoSucursal;
    }

    public void setMostradorInfoSucursal(MostradorInfoSucursal mostradorInfoSucursal) {
        this.mostradorInfoSucursal = mostradorInfoSucursal;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (mostradorInfoPersonalPK != null ? mostradorInfoPersonalPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MostradorInfoPersonal)) {
            return false;
        }
        MostradorInfoPersonal other = (MostradorInfoPersonal) object;
        if ((this.mostradorInfoPersonalPK == null && other.mostradorInfoPersonalPK != null) || (this.mostradorInfoPersonalPK != null && !this.mostradorInfoPersonalPK.equals(other.mostradorInfoPersonalPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "proyecto_final_doctor.MostradorInfoPersonal[ mostradorInfoPersonalPK=" + mostradorInfoPersonalPK + " ]";
    }
    
}
