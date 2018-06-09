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
@Table(name = "INTENDENCIA_INFO_PERSONAL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IntendenciaInfoPersonal.findAll", query = "SELECT i FROM IntendenciaInfoPersonal i")
    , @NamedQuery(name = "IntendenciaInfoPersonal.findByIdIntendencia", query = "SELECT i FROM IntendenciaInfoPersonal i WHERE i.intendenciaInfoPersonalPK.idIntendencia = :idIntendencia")
    , @NamedQuery(name = "IntendenciaInfoPersonal.findByNoSeguroSocial", query = "SELECT i FROM IntendenciaInfoPersonal i WHERE i.intendenciaInfoPersonalPK.noSeguroSocial = :noSeguroSocial")
    , @NamedQuery(name = "IntendenciaInfoPersonal.findByRfc", query = "SELECT i FROM IntendenciaInfoPersonal i WHERE i.intendenciaInfoPersonalPK.rfc = :rfc")
    , @NamedQuery(name = "IntendenciaInfoPersonal.findByApellidoPaterno", query = "SELECT i FROM IntendenciaInfoPersonal i WHERE i.apellidoPaterno = :apellidoPaterno")
    , @NamedQuery(name = "IntendenciaInfoPersonal.findByApellidoMaterno", query = "SELECT i FROM IntendenciaInfoPersonal i WHERE i.apellidoMaterno = :apellidoMaterno")})
public class IntendenciaInfoPersonal implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected IntendenciaInfoPersonalPK intendenciaInfoPersonalPK;
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
    private IntendenciaInfoContacto intendenciaInfoContacto;
    @JoinColumn(name = "NO_SEGURO_SOCIAL", referencedColumnName = "NO_SEGURO_SOCIAL", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private IntendenciaInfoSucursal intendenciaInfoSucursal;

    public IntendenciaInfoPersonal() {
    }

    public IntendenciaInfoPersonal(IntendenciaInfoPersonalPK intendenciaInfoPersonalPK) {
        this.intendenciaInfoPersonalPK = intendenciaInfoPersonalPK;
    }

    public IntendenciaInfoPersonal(IntendenciaInfoPersonalPK intendenciaInfoPersonalPK, String apellidoPaterno, String apellidoMaterno) {
        this.intendenciaInfoPersonalPK = intendenciaInfoPersonalPK;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
    }

    public IntendenciaInfoPersonal(BigInteger idIntendencia, String noSeguroSocial, String rfc) {
        this.intendenciaInfoPersonalPK = new IntendenciaInfoPersonalPK(idIntendencia, noSeguroSocial, rfc);
    }

    public IntendenciaInfoPersonalPK getIntendenciaInfoPersonalPK() {
        return intendenciaInfoPersonalPK;
    }

    public void setIntendenciaInfoPersonalPK(IntendenciaInfoPersonalPK intendenciaInfoPersonalPK) {
        this.intendenciaInfoPersonalPK = intendenciaInfoPersonalPK;
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

    public IntendenciaInfoContacto getIntendenciaInfoContacto() {
        return intendenciaInfoContacto;
    }

    public void setIntendenciaInfoContacto(IntendenciaInfoContacto intendenciaInfoContacto) {
        this.intendenciaInfoContacto = intendenciaInfoContacto;
    }

    public IntendenciaInfoSucursal getIntendenciaInfoSucursal() {
        return intendenciaInfoSucursal;
    }

    public void setIntendenciaInfoSucursal(IntendenciaInfoSucursal intendenciaInfoSucursal) {
        this.intendenciaInfoSucursal = intendenciaInfoSucursal;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (intendenciaInfoPersonalPK != null ? intendenciaInfoPersonalPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof IntendenciaInfoPersonal)) {
            return false;
        }
        IntendenciaInfoPersonal other = (IntendenciaInfoPersonal) object;
        if ((this.intendenciaInfoPersonalPK == null && other.intendenciaInfoPersonalPK != null) || (this.intendenciaInfoPersonalPK != null && !this.intendenciaInfoPersonalPK.equals(other.intendenciaInfoPersonalPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "proyecto_final_doctor.IntendenciaInfoPersonal[ intendenciaInfoPersonalPK=" + intendenciaInfoPersonalPK + " ]";
    }
    
}
