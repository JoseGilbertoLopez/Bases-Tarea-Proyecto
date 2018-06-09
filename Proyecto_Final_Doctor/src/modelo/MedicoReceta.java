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
@Table(name = "MEDICO_RECETA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MedicoReceta.findAll", query = "SELECT m FROM MedicoReceta m")
    , @NamedQuery(name = "MedicoReceta.findByIdMedico", query = "SELECT m FROM MedicoReceta m WHERE m.medicoRecetaPK.idMedico = :idMedico")
    , @NamedQuery(name = "MedicoReceta.findByNoSeguroSocial", query = "SELECT m FROM MedicoReceta m WHERE m.medicoRecetaPK.noSeguroSocial = :noSeguroSocial")
    , @NamedQuery(name = "MedicoReceta.findByRfc", query = "SELECT m FROM MedicoReceta m WHERE m.medicoRecetaPK.rfc = :rfc")
    , @NamedQuery(name = "MedicoReceta.findByCedulaProfesional", query = "SELECT m FROM MedicoReceta m WHERE m.medicoRecetaPK.cedulaProfesional = :cedulaProfesional")
    , @NamedQuery(name = "MedicoReceta.findByNombre", query = "SELECT m FROM MedicoReceta m WHERE m.nombre = :nombre")
    , @NamedQuery(name = "MedicoReceta.findByApellidoPaterno", query = "SELECT m FROM MedicoReceta m WHERE m.apellidoPaterno = :apellidoPaterno")
    , @NamedQuery(name = "MedicoReceta.findByApellidoMaterno", query = "SELECT m FROM MedicoReceta m WHERE m.apellidoMaterno = :apellidoMaterno")
    , @NamedQuery(name = "MedicoReceta.findByIdReceta", query = "SELECT m FROM MedicoReceta m WHERE m.medicoRecetaPK.idReceta = :idReceta")})
public class MedicoReceta implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected MedicoRecetaPK medicoRecetaPK;
    @Basic(optional = false)
    @Column(name = "NOMBRE")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "APELLIDO_PATERNO")
    private String apellidoPaterno;
    @Basic(optional = false)
    @Column(name = "APELLIDO_MATERNO")
    private String apellidoMaterno;
    @JoinColumns({
        @JoinColumn(name = "ID_MEDICO", referencedColumnName = "ID_MEDICO", insertable = false, updatable = false)
        , @JoinColumn(name = "NO_SEGURO_SOCIAL", referencedColumnName = "NO_SEGURO_SOCIAL", insertable = false, updatable = false)
        , @JoinColumn(name = "RFC", referencedColumnName = "RFC", insertable = false, updatable = false)
        , @JoinColumn(name = "CEDULA_PROFESIONAL", referencedColumnName = "CEDULA_PROFESIONAL", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private MedicoInformacionPersonal medicoInformacionPersonal;
    @JoinColumn(name = "ID_RECETA", referencedColumnName = "ID_RECETA", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private RecetaInformacionConsulta recetaInformacionConsulta;

    public MedicoReceta() {
    }

    public MedicoReceta(MedicoRecetaPK medicoRecetaPK) {
        this.medicoRecetaPK = medicoRecetaPK;
    }

    public MedicoReceta(MedicoRecetaPK medicoRecetaPK, String nombre, String apellidoPaterno, String apellidoMaterno) {
        this.medicoRecetaPK = medicoRecetaPK;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
    }

    public MedicoReceta(BigInteger idMedico, String noSeguroSocial, String rfc, String cedulaProfesional, BigInteger idReceta) {
        this.medicoRecetaPK = new MedicoRecetaPK(idMedico, noSeguroSocial, rfc, cedulaProfesional, idReceta);
    }

    public MedicoRecetaPK getMedicoRecetaPK() {
        return medicoRecetaPK;
    }

    public void setMedicoRecetaPK(MedicoRecetaPK medicoRecetaPK) {
        this.medicoRecetaPK = medicoRecetaPK;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public MedicoInformacionPersonal getMedicoInformacionPersonal() {
        return medicoInformacionPersonal;
    }

    public void setMedicoInformacionPersonal(MedicoInformacionPersonal medicoInformacionPersonal) {
        this.medicoInformacionPersonal = medicoInformacionPersonal;
    }

    public RecetaInformacionConsulta getRecetaInformacionConsulta() {
        return recetaInformacionConsulta;
    }

    public void setRecetaInformacionConsulta(RecetaInformacionConsulta recetaInformacionConsulta) {
        this.recetaInformacionConsulta = recetaInformacionConsulta;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (medicoRecetaPK != null ? medicoRecetaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MedicoReceta)) {
            return false;
        }
        MedicoReceta other = (MedicoReceta) object;
        if ((this.medicoRecetaPK == null && other.medicoRecetaPK != null) || (this.medicoRecetaPK != null && !this.medicoRecetaPK.equals(other.medicoRecetaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "proyecto_final_doctor.MedicoReceta[ medicoRecetaPK=" + medicoRecetaPK + " ]";
    }
    
}
