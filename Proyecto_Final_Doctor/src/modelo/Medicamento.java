/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
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
@Table(name = "MEDICAMENTO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Medicamento.findAll", query = "SELECT m FROM Medicamento m")
    , @NamedQuery(name = "Medicamento.findByMarca", query = "SELECT m FROM Medicamento m WHERE m.medicamentoPK.marca = :marca")
    , @NamedQuery(name = "Medicamento.findByNombre", query = "SELECT m FROM Medicamento m WHERE m.medicamentoPK.nombre = :nombre")
    , @NamedQuery(name = "Medicamento.findByPrecio", query = "SELECT m FROM Medicamento m WHERE m.precio = :precio")
    , @NamedQuery(name = "Medicamento.findByEspecialidad", query = "SELECT m FROM Medicamento m WHERE m.especialidad = :especialidad")})
public class Medicamento implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected MedicamentoPK medicamentoPK;
    @Column(name = "PRECIO")
    private Integer precio;
    @Column(name = "ESPECIALIDAD")
    private String especialidad;
    @ManyToMany(mappedBy = "medicamentoCollection")
    private Collection<Ingrediente> ingredienteCollection;
    @ManyToMany(mappedBy = "medicamentoCollection")
    private Collection<Presentacion> presentacionCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "medicamento")
    private Collection<MedicamentoReceta> medicamentoRecetaCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "medicamento")
    private Collection<MedicamentoCompra> medicamentoCompraCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "medicamento")
    private Collection<SucursalMedicamento> sucursalMedicamentoCollection;

    public Medicamento() {
    }

    public Medicamento(MedicamentoPK medicamentoPK) {
        this.medicamentoPK = medicamentoPK;
    }

    public Medicamento(String marca, String nombre) {
        this.medicamentoPK = new MedicamentoPK(marca, nombre);
    }

    public MedicamentoPK getMedicamentoPK() {
        return medicamentoPK;
    }

    public void setMedicamentoPK(MedicamentoPK medicamentoPK) {
        this.medicamentoPK = medicamentoPK;
    }

    public Integer getPrecio() {
        return precio;
    }

    public void setPrecio(Integer precio) {
        this.precio = precio;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    @XmlTransient
    public Collection<Ingrediente> getIngredienteCollection() {
        return ingredienteCollection;
    }

    public void setIngredienteCollection(Collection<Ingrediente> ingredienteCollection) {
        this.ingredienteCollection = ingredienteCollection;
    }

    @XmlTransient
    public Collection<Presentacion> getPresentacionCollection() {
        return presentacionCollection;
    }

    public void setPresentacionCollection(Collection<Presentacion> presentacionCollection) {
        this.presentacionCollection = presentacionCollection;
    }

    @XmlTransient
    public Collection<MedicamentoReceta> getMedicamentoRecetaCollection() {
        return medicamentoRecetaCollection;
    }

    public void setMedicamentoRecetaCollection(Collection<MedicamentoReceta> medicamentoRecetaCollection) {
        this.medicamentoRecetaCollection = medicamentoRecetaCollection;
    }

    @XmlTransient
    public Collection<MedicamentoCompra> getMedicamentoCompraCollection() {
        return medicamentoCompraCollection;
    }

    public void setMedicamentoCompraCollection(Collection<MedicamentoCompra> medicamentoCompraCollection) {
        this.medicamentoCompraCollection = medicamentoCompraCollection;
    }

    @XmlTransient
    public Collection<SucursalMedicamento> getSucursalMedicamentoCollection() {
        return sucursalMedicamentoCollection;
    }

    public void setSucursalMedicamentoCollection(Collection<SucursalMedicamento> sucursalMedicamentoCollection) {
        this.sucursalMedicamentoCollection = sucursalMedicamentoCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (medicamentoPK != null ? medicamentoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Medicamento)) {
            return false;
        }
        Medicamento other = (Medicamento) object;
        if ((this.medicamentoPK == null && other.medicamentoPK != null) || (this.medicamentoPK != null && !this.medicamentoPK.equals(other.medicamentoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "proyecto_final_doctor.Medicamento[ medicamentoPK=" + medicamentoPK + " ]";
    }
    
}
