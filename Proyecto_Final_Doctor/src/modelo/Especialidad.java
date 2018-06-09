/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Jose G
 */
@Entity
@Table(name = "ESPECIALIDAD")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Especialidad.findAll", query = "SELECT e FROM Especialidad e")
    , @NamedQuery(name = "Especialidad.findByIdEspecialidad", query = "SELECT e FROM Especialidad e WHERE e.idEspecialidad = :idEspecialidad")
    , @NamedQuery(name = "Especialidad.findByEspecialidad", query = "SELECT e FROM Especialidad e WHERE e.especialidad = :especialidad")})
public class Especialidad implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID_ESPECIALIDAD")
    private BigDecimal idEspecialidad;
    @Column(name = "ESPECIALIDAD")
    private String especialidad;
    @JoinTable(name = "MEDICO_ESPECIALIDAD", joinColumns = {
        @JoinColumn(name = "ID_ESPECIALIDAD", referencedColumnName = "ID_ESPECIALIDAD")}, inverseJoinColumns = {
        @JoinColumn(name = "ID_MEDICO", referencedColumnName = "ID_MEDICO")
        , @JoinColumn(name = "NO_SEGURO_SOCIAL", referencedColumnName = "NO_SEGURO_SOCIAL")
        , @JoinColumn(name = "RFC", referencedColumnName = "RFC")
        , @JoinColumn(name = "CEDULA_PROFESIONAL", referencedColumnName = "CEDULA_PROFESIONAL")})
    @ManyToMany
    private Collection<MedicoInformacionPersonal> medicoInformacionPersonalCollection;

    public Especialidad() {
    }

    public Especialidad(BigDecimal idEspecialidad) {
        this.idEspecialidad = idEspecialidad;
    }

    public BigDecimal getIdEspecialidad() {
        return idEspecialidad;
    }

    public void setIdEspecialidad(BigDecimal idEspecialidad) {
        this.idEspecialidad = idEspecialidad;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    @XmlTransient
    public Collection<MedicoInformacionPersonal> getMedicoInformacionPersonalCollection() {
        return medicoInformacionPersonalCollection;
    }

    public void setMedicoInformacionPersonalCollection(Collection<MedicoInformacionPersonal> medicoInformacionPersonalCollection) {
        this.medicoInformacionPersonalCollection = medicoInformacionPersonalCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEspecialidad != null ? idEspecialidad.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Especialidad)) {
            return false;
        }
        Especialidad other = (Especialidad) object;
        if ((this.idEspecialidad == null && other.idEspecialidad != null) || (this.idEspecialidad != null && !this.idEspecialidad.equals(other.idEspecialidad))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "proyecto_final_doctor.Especialidad[ idEspecialidad=" + idEspecialidad + " ]";
    }
    
}
