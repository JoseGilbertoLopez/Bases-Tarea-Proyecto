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
@Table(name = "SUCURSAL_MEDICAMENTO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SucursalMedicamento.findAll", query = "SELECT s FROM SucursalMedicamento s")
    , @NamedQuery(name = "SucursalMedicamento.findByIdSucursal", query = "SELECT s FROM SucursalMedicamento s WHERE s.sucursalMedicamentoPK.idSucursal = :idSucursal")
    , @NamedQuery(name = "SucursalMedicamento.findByDireccion", query = "SELECT s FROM SucursalMedicamento s WHERE s.direccion = :direccion")
    , @NamedQuery(name = "SucursalMedicamento.findByCiudad", query = "SELECT s FROM SucursalMedicamento s WHERE s.ciudad = :ciudad")
    , @NamedQuery(name = "SucursalMedicamento.findByEstado", query = "SELECT s FROM SucursalMedicamento s WHERE s.estado = :estado")
    , @NamedQuery(name = "SucursalMedicamento.findByMarca", query = "SELECT s FROM SucursalMedicamento s WHERE s.sucursalMedicamentoPK.marca = :marca")
    , @NamedQuery(name = "SucursalMedicamento.findByNombre", query = "SELECT s FROM SucursalMedicamento s WHERE s.sucursalMedicamentoPK.nombre = :nombre")
    , @NamedQuery(name = "SucursalMedicamento.findByPrecio", query = "SELECT s FROM SucursalMedicamento s WHERE s.precio = :precio")
    , @NamedQuery(name = "SucursalMedicamento.findByEspecialidad", query = "SELECT s FROM SucursalMedicamento s WHERE s.especialidad = :especialidad")})
public class SucursalMedicamento implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SucursalMedicamentoPK sucursalMedicamentoPK;
    @Column(name = "DIRECCION")
    private String direccion;
    @Column(name = "CIUDAD")
    private String ciudad;
    @Column(name = "ESTADO")
    private String estado;
    @Column(name = "PRECIO")
    private Integer precio;
    @Column(name = "ESPECIALIDAD")
    private String especialidad;
    @JoinColumns({
        @JoinColumn(name = "MARCA", referencedColumnName = "MARCA", insertable = false, updatable = false)
        , @JoinColumn(name = "NOMBRE", referencedColumnName = "NOMBRE", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private Medicamento medicamento;
    @JoinColumn(name = "ID_SUCURSAL", referencedColumnName = "ID_SUCURSAL", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Sucursal sucursal;

    public SucursalMedicamento() {
    }

    public SucursalMedicamento(SucursalMedicamentoPK sucursalMedicamentoPK) {
        this.sucursalMedicamentoPK = sucursalMedicamentoPK;
    }

    public SucursalMedicamento(BigInteger idSucursal, String marca, String nombre) {
        this.sucursalMedicamentoPK = new SucursalMedicamentoPK(idSucursal, marca, nombre);
    }

    public SucursalMedicamentoPK getSucursalMedicamentoPK() {
        return sucursalMedicamentoPK;
    }

    public void setSucursalMedicamentoPK(SucursalMedicamentoPK sucursalMedicamentoPK) {
        this.sucursalMedicamentoPK = sucursalMedicamentoPK;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
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

    public Sucursal getSucursal() {
        return sucursal;
    }

    public void setSucursal(Sucursal sucursal) {
        this.sucursal = sucursal;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sucursalMedicamentoPK != null ? sucursalMedicamentoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SucursalMedicamento)) {
            return false;
        }
        SucursalMedicamento other = (SucursalMedicamento) object;
        if ((this.sucursalMedicamentoPK == null && other.sucursalMedicamentoPK != null) || (this.sucursalMedicamentoPK != null && !this.sucursalMedicamentoPK.equals(other.sucursalMedicamentoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "proyecto_final_doctor.SucursalMedicamento[ sucursalMedicamentoPK=" + sucursalMedicamentoPK + " ]";
    }
    
}
