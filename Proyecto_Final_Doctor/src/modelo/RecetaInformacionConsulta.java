/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
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
@Table(name = "RECETA_INFORMACION_CONSULTA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RecetaInformacionConsulta.findAll", query = "SELECT r FROM RecetaInformacionConsulta r")
    , @NamedQuery(name = "RecetaInformacionConsulta.findByIdReceta", query = "SELECT r FROM RecetaInformacionConsulta r WHERE r.idReceta = :idReceta")
    , @NamedQuery(name = "RecetaInformacionConsulta.findByFirma", query = "SELECT r FROM RecetaInformacionConsulta r WHERE r.firma = :firma")
    , @NamedQuery(name = "RecetaInformacionConsulta.findByDatosPaciente", query = "SELECT r FROM RecetaInformacionConsulta r WHERE r.datosPaciente = :datosPaciente")
    , @NamedQuery(name = "RecetaInformacionConsulta.findByTurnoConsulta", query = "SELECT r FROM RecetaInformacionConsulta r WHERE r.turnoConsulta = :turnoConsulta")})
public class RecetaInformacionConsulta implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID_RECETA")
    private BigDecimal idReceta;
    @Basic(optional = false)
    @Column(name = "FIRMA")
    private Serializable firma;
    @Basic(optional = false)
    @Column(name = "DATOS_PACIENTE")
    private BigInteger datosPaciente;
    @Column(name = "TURNO_CONSULTA")
    private BigInteger turnoConsulta;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recetaInformacionConsulta")
    private Collection<ClienteReceta> clienteRecetaCollection;
    @JoinColumns({
        @JoinColumn(name = "ID_RECETA", referencedColumnName = "ID_RECETA", insertable = false, updatable = false)
        , @JoinColumn(name = "MEDICO", referencedColumnName = "MEDICO")})
    @ManyToOne(optional = false)
    private RecetaInformacionEspecial recetaInformacionEspecial;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recetaInformacionConsulta")
    private Collection<MedicoReceta> medicoRecetaCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recetaInformacionConsulta")
    private Collection<MedicamentoReceta> medicamentoRecetaCollection;

    public RecetaInformacionConsulta() {
    }

    public RecetaInformacionConsulta(BigDecimal idReceta) {
        this.idReceta = idReceta;
    }

    public RecetaInformacionConsulta(BigDecimal idReceta, Serializable firma, BigInteger datosPaciente) {
        this.idReceta = idReceta;
        this.firma = firma;
        this.datosPaciente = datosPaciente;
    }

    public BigDecimal getIdReceta() {
        return idReceta;
    }

    public void setIdReceta(BigDecimal idReceta) {
        this.idReceta = idReceta;
    }

    public Serializable getFirma() {
        return firma;
    }

    public void setFirma(Serializable firma) {
        this.firma = firma;
    }

    public BigInteger getDatosPaciente() {
        return datosPaciente;
    }

    public void setDatosPaciente(BigInteger datosPaciente) {
        this.datosPaciente = datosPaciente;
    }

    public BigInteger getTurnoConsulta() {
        return turnoConsulta;
    }

    public void setTurnoConsulta(BigInteger turnoConsulta) {
        this.turnoConsulta = turnoConsulta;
    }

    @XmlTransient
    public Collection<ClienteReceta> getClienteRecetaCollection() {
        return clienteRecetaCollection;
    }

    public void setClienteRecetaCollection(Collection<ClienteReceta> clienteRecetaCollection) {
        this.clienteRecetaCollection = clienteRecetaCollection;
    }

    public RecetaInformacionEspecial getRecetaInformacionEspecial() {
        return recetaInformacionEspecial;
    }

    public void setRecetaInformacionEspecial(RecetaInformacionEspecial recetaInformacionEspecial) {
        this.recetaInformacionEspecial = recetaInformacionEspecial;
    }

    @XmlTransient
    public Collection<MedicoReceta> getMedicoRecetaCollection() {
        return medicoRecetaCollection;
    }

    public void setMedicoRecetaCollection(Collection<MedicoReceta> medicoRecetaCollection) {
        this.medicoRecetaCollection = medicoRecetaCollection;
    }

    @XmlTransient
    public Collection<MedicamentoReceta> getMedicamentoRecetaCollection() {
        return medicamentoRecetaCollection;
    }

    public void setMedicamentoRecetaCollection(Collection<MedicamentoReceta> medicamentoRecetaCollection) {
        this.medicamentoRecetaCollection = medicamentoRecetaCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idReceta != null ? idReceta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RecetaInformacionConsulta)) {
            return false;
        }
        RecetaInformacionConsulta other = (RecetaInformacionConsulta) object;
        if ((this.idReceta == null && other.idReceta != null) || (this.idReceta != null && !this.idReceta.equals(other.idReceta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "proyecto_final_doctor.RecetaInformacionConsulta[ idReceta=" + idReceta + " ]";
    }
    
}
