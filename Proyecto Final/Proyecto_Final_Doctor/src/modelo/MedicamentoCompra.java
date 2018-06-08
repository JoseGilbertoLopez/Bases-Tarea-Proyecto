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
@Table(name = "MEDICAMENTO_COMPRA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MedicamentoCompra.findAll", query = "SELECT m FROM MedicamentoCompra m")
    , @NamedQuery(name = "MedicamentoCompra.findByMarca", query = "SELECT m FROM MedicamentoCompra m WHERE m.medicamentoCompraPK.marca = :marca")
    , @NamedQuery(name = "MedicamentoCompra.findByNombre", query = "SELECT m FROM MedicamentoCompra m WHERE m.medicamentoCompraPK.nombre = :nombre")
    , @NamedQuery(name = "MedicamentoCompra.findByPrecio", query = "SELECT m FROM MedicamentoCompra m WHERE m.precio = :precio")
    , @NamedQuery(name = "MedicamentoCompra.findByEspecialidad", query = "SELECT m FROM MedicamentoCompra m WHERE m.especialidad = :especialidad")
    , @NamedQuery(name = "MedicamentoCompra.findByIdCompra", query = "SELECT m FROM MedicamentoCompra m WHERE m.medicamentoCompraPK.idCompra = :idCompra")})
public class MedicamentoCompra implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected MedicamentoCompraPK medicamentoCompraPK;
    @Column(name = "PRECIO")
    private Integer precio;
    @Column(name = "ESPECIALIDAD")
    private String especialidad;
    @JoinColumn(name = "ID_COMPRA", referencedColumnName = "ID_COMPRA", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private CompraInformacionGeneral compraInformacionGeneral;
    @JoinColumns({
        @JoinColumn(name = "MARCA", referencedColumnName = "MARCA", insertable = false, updatable = false)
        , @JoinColumn(name = "NOMBRE", referencedColumnName = "NOMBRE", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private Medicamento medicamento;

    public MedicamentoCompra() {
    }

    public MedicamentoCompra(MedicamentoCompraPK medicamentoCompraPK) {
        this.medicamentoCompraPK = medicamentoCompraPK;
    }

    public MedicamentoCompra(String marca, String nombre, BigInteger idCompra) {
        this.medicamentoCompraPK = new MedicamentoCompraPK(marca, nombre, idCompra);
    }

    public MedicamentoCompraPK getMedicamentoCompraPK() {
        return medicamentoCompraPK;
    }

    public void setMedicamentoCompraPK(MedicamentoCompraPK medicamentoCompraPK) {
        this.medicamentoCompraPK = medicamentoCompraPK;
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

    public CompraInformacionGeneral getCompraInformacionGeneral() {
        return compraInformacionGeneral;
    }

    public void setCompraInformacionGeneral(CompraInformacionGeneral compraInformacionGeneral) {
        this.compraInformacionGeneral = compraInformacionGeneral;
    }

    public Medicamento getMedicamento() {
        return medicamento;
    }

    public void setMedicamento(Medicamento medicamento) {
        this.medicamento = medicamento;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (medicamentoCompraPK != null ? medicamentoCompraPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MedicamentoCompra)) {
            return false;
        }
        MedicamentoCompra other = (MedicamentoCompra) object;
        if ((this.medicamentoCompraPK == null && other.medicamentoCompraPK != null) || (this.medicamentoCompraPK != null && !this.medicamentoCompraPK.equals(other.medicamentoCompraPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "proyecto_final_doctor.MedicamentoCompra[ medicamentoCompraPK=" + medicamentoCompraPK + " ]";
    }
    
}
