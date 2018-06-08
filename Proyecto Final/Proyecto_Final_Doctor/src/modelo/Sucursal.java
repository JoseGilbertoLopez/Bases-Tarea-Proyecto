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
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name = "SUCURSAL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Sucursal.findAll", query = "SELECT s FROM Sucursal s")
    , @NamedQuery(name = "Sucursal.findByIdSucursal", query = "SELECT s FROM Sucursal s WHERE s.idSucursal = :idSucursal")
    , @NamedQuery(name = "Sucursal.findByDireccion", query = "SELECT s FROM Sucursal s WHERE s.direccion = :direccion")
    , @NamedQuery(name = "Sucursal.findByCiudad", query = "SELECT s FROM Sucursal s WHERE s.ciudad = :ciudad")
    , @NamedQuery(name = "Sucursal.findByEstado", query = "SELECT s FROM Sucursal s WHERE s.estado = :estado")})
public class Sucursal implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID_SUCURSAL")
    private BigDecimal idSucursal;
    @Column(name = "DIRECCION")
    private String direccion;
    @Column(name = "CIUDAD")
    private String ciudad;
    @Column(name = "ESTADO")
    private String estado;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sucursal")
    private Collection<TelefonoSucursal> telefonoSucursalCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sucursal")
    private Collection<EmpleadoSucursal> empleadoSucursalCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sucursal")
    private Collection<SucursalMedicamento> sucursalMedicamentoCollection;

    public Sucursal() {
    }

    public Sucursal(BigDecimal idSucursal) {
        this.idSucursal = idSucursal;
    }

    public BigDecimal getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(BigDecimal idSucursal) {
        this.idSucursal = idSucursal;
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

    @XmlTransient
    public Collection<TelefonoSucursal> getTelefonoSucursalCollection() {
        return telefonoSucursalCollection;
    }

    public void setTelefonoSucursalCollection(Collection<TelefonoSucursal> telefonoSucursalCollection) {
        this.telefonoSucursalCollection = telefonoSucursalCollection;
    }

    @XmlTransient
    public Collection<EmpleadoSucursal> getEmpleadoSucursalCollection() {
        return empleadoSucursalCollection;
    }

    public void setEmpleadoSucursalCollection(Collection<EmpleadoSucursal> empleadoSucursalCollection) {
        this.empleadoSucursalCollection = empleadoSucursalCollection;
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
        hash += (idSucursal != null ? idSucursal.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Sucursal)) {
            return false;
        }
        Sucursal other = (Sucursal) object;
        if ((this.idSucursal == null && other.idSucursal != null) || (this.idSucursal != null && !this.idSucursal.equals(other.idSucursal))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "proyecto_final_doctor.Sucursal[ idSucursal=" + idSucursal + " ]";
    }
    
}
