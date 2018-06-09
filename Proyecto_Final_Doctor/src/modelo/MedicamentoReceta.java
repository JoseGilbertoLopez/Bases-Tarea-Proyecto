/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.math.BigInteger;
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
@Table(name = "MEDICAMENTO_RECETA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MedicamentoReceta.findAll", query = "SELECT m FROM MedicamentoReceta m")
    , @NamedQuery(name = "MedicamentoReceta.findByMarca", query = "SELECT m FROM MedicamentoReceta m WHERE m.medicamentoRecetaPK.marca = :marca")
    , @NamedQuery(name = "MedicamentoReceta.findByNombre", query = "SELECT m FROM MedicamentoReceta m WHERE m.medicamentoRecetaPK.nombre = :nombre")
    , @NamedQuery(name = "MedicamentoReceta.findByPrecio", query = "SELECT m FROM MedicamentoReceta m WHERE m.precio = :precio")
    , @NamedQuery(name = "MedicamentoReceta.findByEspecialidad", query = "SELECT m FROM MedicamentoReceta m WHERE m.especialidad = :especialidad")
    , @NamedQuery(name = "MedicamentoReceta.findByIdReceta", query = "SELECT m FROM MedicamentoReceta m WHERE m.medicamentoRecetaPK.idReceta = :idReceta")})
public class MedicamentoReceta implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected MedicamentoRecetaPK medicamentoRecetaPK;
    @Column(name = "PRECIO")
    private Integer precio;
    @Column(name = "ESPECIALIDAD")
    private String especialidad;
    @JoinColumns({
        @JoinColumn(name = "MARCA", referencedColumnName = "MARCA", insertable = false, updatable = false)
        , @JoinColumn(name = "NOMBRE", referencedColumnName = "NOMBRE", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private Medicamento medicamento;
    @JoinColumn(name = "ID_RECETA", referencedColumnName = "ID_RECETA", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private RecetaInformacionConsulta recetaInformacionConsulta;

    public MedicamentoReceta() {
    }

    public MedicamentoReceta(MedicamentoRecetaPK medicamentoRecetaPK) {
        this.medicamentoRecetaPK = medicamentoRecetaPK;
    }

    public MedicamentoReceta(String marca, String nombre, BigInteger idReceta) {
        this.medicamentoRecetaPK = new MedicamentoRecetaPK(marca, nombre, idReceta);
    }

    public MedicamentoRecetaPK getMedicamentoRecetaPK() {
        return medicamentoRecetaPK;
    }

    public void setMedicamentoRecetaPK(MedicamentoRecetaPK medicamentoRecetaPK) {
        this.medicamentoRecetaPK = medicamentoRecetaPK;
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

    public Medicamento getMedicamento() {
        return medicamento;
    }

    public void setMedicamento(Medicamento medicamento) {
        this.medicamento = medicamento;
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
        hash += (medicamentoRecetaPK != null ? medicamentoRecetaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MedicamentoReceta)) {
            return false;
        }
        MedicamentoReceta other = (MedicamentoReceta) object;
        if ((this.medicamentoRecetaPK == null && other.medicamentoRecetaPK != null) || (this.medicamentoRecetaPK != null && !this.medicamentoRecetaPK.equals(other.medicamentoRecetaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "proyecto_final_doctor.MedicamentoReceta[ medicamentoRecetaPK=" + medicamentoRecetaPK + " ]";
    }
    
}
