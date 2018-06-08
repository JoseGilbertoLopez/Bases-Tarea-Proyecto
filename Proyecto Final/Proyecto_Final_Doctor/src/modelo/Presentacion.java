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
@Table(name = "PRESENTACION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Presentacion.findAll", query = "SELECT p FROM Presentacion p")
    , @NamedQuery(name = "Presentacion.findByIdPresentacion", query = "SELECT p FROM Presentacion p WHERE p.idPresentacion = :idPresentacion")
    , @NamedQuery(name = "Presentacion.findByPresentacion", query = "SELECT p FROM Presentacion p WHERE p.presentacion = :presentacion")})
public class Presentacion implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID_PRESENTACION")
    private BigDecimal idPresentacion;
    @Column(name = "PRESENTACION")
    private String presentacion;
    @JoinTable(name = "MEDICAMENTO_PRESENTACION", joinColumns = {
        @JoinColumn(name = "ID_PRESENTACION", referencedColumnName = "ID_PRESENTACION")}, inverseJoinColumns = {
        @JoinColumn(name = "MARCA", referencedColumnName = "MARCA")
        , @JoinColumn(name = "NOMBRE", referencedColumnName = "NOMBRE")})
    @ManyToMany
    private Collection<Medicamento> medicamentoCollection;

    public Presentacion() {
    }

    public Presentacion(BigDecimal idPresentacion) {
        this.idPresentacion = idPresentacion;
    }

    public BigDecimal getIdPresentacion() {
        return idPresentacion;
    }

    public void setIdPresentacion(BigDecimal idPresentacion) {
        this.idPresentacion = idPresentacion;
    }

    public String getPresentacion() {
        return presentacion;
    }

    public void setPresentacion(String presentacion) {
        this.presentacion = presentacion;
    }

    @XmlTransient
    public Collection<Medicamento> getMedicamentoCollection() {
        return medicamentoCollection;
    }

    public void setMedicamentoCollection(Collection<Medicamento> medicamentoCollection) {
        this.medicamentoCollection = medicamentoCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPresentacion != null ? idPresentacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Presentacion)) {
            return false;
        }
        Presentacion other = (Presentacion) object;
        if ((this.idPresentacion == null && other.idPresentacion != null) || (this.idPresentacion != null && !this.idPresentacion.equals(other.idPresentacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "proyecto_final_doctor.Presentacion[ idPresentacion=" + idPresentacion + " ]";
    }
    
}
