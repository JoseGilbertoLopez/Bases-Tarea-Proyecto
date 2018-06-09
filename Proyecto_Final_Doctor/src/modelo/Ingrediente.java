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
@Table(name = "INGREDIENTE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ingrediente.findAll", query = "SELECT i FROM Ingrediente i")
    , @NamedQuery(name = "Ingrediente.findByIdIngrediente", query = "SELECT i FROM Ingrediente i WHERE i.idIngrediente = :idIngrediente")
    , @NamedQuery(name = "Ingrediente.findByIngrediente", query = "SELECT i FROM Ingrediente i WHERE i.ingrediente = :ingrediente")})
public class Ingrediente implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID_INGREDIENTE")
    private BigDecimal idIngrediente;
    @Column(name = "INGREDIENTE")
    private String ingrediente;
    @JoinTable(name = "MEDICAMENTO_INGREDIENTE", joinColumns = {
        @JoinColumn(name = "ID_INGREDIENTE", referencedColumnName = "ID_INGREDIENTE")}, inverseJoinColumns = {
        @JoinColumn(name = "MARCA", referencedColumnName = "MARCA")
        , @JoinColumn(name = "NOMBRE", referencedColumnName = "NOMBRE")})
    @ManyToMany
    private Collection<Medicamento> medicamentoCollection;

    public Ingrediente() {
    }

    public Ingrediente(BigDecimal idIngrediente) {
        this.idIngrediente = idIngrediente;
    }

    public BigDecimal getIdIngrediente() {
        return idIngrediente;
    }

    public void setIdIngrediente(BigDecimal idIngrediente) {
        this.idIngrediente = idIngrediente;
    }

    public String getIngrediente() {
        return ingrediente;
    }

    public void setIngrediente(String ingrediente) {
        this.ingrediente = ingrediente;
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
        hash += (idIngrediente != null ? idIngrediente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ingrediente)) {
            return false;
        }
        Ingrediente other = (Ingrediente) object;
        if ((this.idIngrediente == null && other.idIngrediente != null) || (this.idIngrediente != null && !this.idIngrediente.equals(other.idIngrediente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "proyecto_final_doctor.Ingrediente[ idIngrediente=" + idIngrediente + " ]";
    }
    
}
