/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Jose G
 */
@Entity
@Table(name = "MEDICO_INFORMACION_PERSONAL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MedicoInformacionPersonal.findAll", query = "SELECT m FROM MedicoInformacionPersonal m")
    , @NamedQuery(name = "MedicoInformacionPersonal.findByIdMedico", query = "SELECT m FROM MedicoInformacionPersonal m WHERE m.medicoInformacionPersonalPK.idMedico = :idMedico")
    , @NamedQuery(name = "MedicoInformacionPersonal.findByNoSeguroSocial", query = "SELECT m FROM MedicoInformacionPersonal m WHERE m.medicoInformacionPersonalPK.noSeguroSocial = :noSeguroSocial")
    , @NamedQuery(name = "MedicoInformacionPersonal.findByRfc", query = "SELECT m FROM MedicoInformacionPersonal m WHERE m.medicoInformacionPersonalPK.rfc = :rfc")
    , @NamedQuery(name = "MedicoInformacionPersonal.findByCedulaProfesional", query = "SELECT m FROM MedicoInformacionPersonal m WHERE m.medicoInformacionPersonalPK.cedulaProfesional = :cedulaProfesional")})
public class MedicoInformacionPersonal implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected MedicoInformacionPersonalPK medicoInformacionPersonalPK;
    @ManyToMany(mappedBy = "medicoInformacionPersonalCollection")
    private Collection<Especialidad> especialidadCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "medicoInformacionPersonal")
    private Collection<MedicoReceta> medicoRecetaCollection;
    @JoinColumns({
        @JoinColumn(name = "NO_SEGURO_SOCIAL", referencedColumnName = "NO_SEGURO_SOCIAL", insertable = false, updatable = false)
        , @JoinColumn(name = "CEDULA_PROFESIONAL", referencedColumnName = "CEDULA_PROFESIONAL", insertable = false, updatable = false)
        , @JoinColumn(name = "NOMBRE", referencedColumnName = "NOMBRE")
        , @JoinColumn(name = "APELLIDO_MATERNO", referencedColumnName = "APELLIDO_MATERNO")
        , @JoinColumn(name = "APELLIDO_PATERNO", referencedColumnName = "APELLIDO_PATERNO")})
    @ManyToOne(optional = false)
    private MedicoInformacionContacto medicoInformacionContacto;
    @JoinColumns({
        @JoinColumn(name = "NO_SEGURO_SOCIAL", referencedColumnName = "NO_SEGURO_SOCIAL", insertable = false, updatable = false)
        , @JoinColumn(name = "CEDULA_PROFESIONAL", referencedColumnName = "CEDULA_PROFESIONAL", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private MedicoInformacionSucursal medicoInformacionSucursal;

    public MedicoInformacionPersonal() {
    }

    public MedicoInformacionPersonal(MedicoInformacionPersonalPK medicoInformacionPersonalPK) {
        this.medicoInformacionPersonalPK = medicoInformacionPersonalPK;
    }

    public MedicoInformacionPersonal(BigInteger idMedico, String noSeguroSocial, String rfc, String cedulaProfesional) {
        this.medicoInformacionPersonalPK = new MedicoInformacionPersonalPK(idMedico, noSeguroSocial, rfc, cedulaProfesional);
    }

    public MedicoInformacionPersonalPK getMedicoInformacionPersonalPK() {
        return medicoInformacionPersonalPK;
    }

    public void setMedicoInformacionPersonalPK(MedicoInformacionPersonalPK medicoInformacionPersonalPK) {
        this.medicoInformacionPersonalPK = medicoInformacionPersonalPK;
    }

    @XmlTransient
    public Collection<Especialidad> getEspecialidadCollection() {
        return especialidadCollection;
    }

    public void setEspecialidadCollection(Collection<Especialidad> especialidadCollection) {
        this.especialidadCollection = especialidadCollection;
    }

    @XmlTransient
    public Collection<MedicoReceta> getMedicoRecetaCollection() {
        return medicoRecetaCollection;
    }

    public void setMedicoRecetaCollection(Collection<MedicoReceta> medicoRecetaCollection) {
        this.medicoRecetaCollection = medicoRecetaCollection;
    }

    public MedicoInformacionContacto getMedicoInformacionContacto() {
        return medicoInformacionContacto;
    }

    public void setMedicoInformacionContacto(MedicoInformacionContacto medicoInformacionContacto) {
        this.medicoInformacionContacto = medicoInformacionContacto;
    }

    public MedicoInformacionSucursal getMedicoInformacionSucursal() {
        return medicoInformacionSucursal;
    }

    public void setMedicoInformacionSucursal(MedicoInformacionSucursal medicoInformacionSucursal) {
        this.medicoInformacionSucursal = medicoInformacionSucursal;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (medicoInformacionPersonalPK != null ? medicoInformacionPersonalPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MedicoInformacionPersonal)) {
            return false;
        }
        MedicoInformacionPersonal other = (MedicoInformacionPersonal) object;
        if ((this.medicoInformacionPersonalPK == null && other.medicoInformacionPersonalPK != null) || (this.medicoInformacionPersonalPK != null && !this.medicoInformacionPersonalPK.equals(other.medicoInformacionPersonalPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "proyecto_final_doctor.MedicoInformacionPersonal[ medicoInformacionPersonalPK=" + medicoInformacionPersonalPK + " ]";
    }
    
}
