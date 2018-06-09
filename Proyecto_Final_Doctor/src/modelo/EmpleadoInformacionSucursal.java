/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
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
@Table(name = "EMPLEADO_INFORMACION_SUCURSAL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EmpleadoInformacionSucursal.findAll", query = "SELECT e FROM EmpleadoInformacionSucursal e")
    , @NamedQuery(name = "EmpleadoInformacionSucursal.findByNoSeguroSocial", query = "SELECT e FROM EmpleadoInformacionSucursal e WHERE e.noSeguroSocial = :noSeguroSocial")
    , @NamedQuery(name = "EmpleadoInformacionSucursal.findBySucursal", query = "SELECT e FROM EmpleadoInformacionSucursal e WHERE e.sucursal = :sucursal")})
public class EmpleadoInformacionSucursal implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "NO_SEGURO_SOCIAL")
    private String noSeguroSocial;
    @Basic(optional = false)
    @Column(name = "SUCURSAL")
    private String sucursal;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "empleadoInformacionSucursal")
    private Collection<EmpleadoInformacionPersonal> empleadoInformacionPersonalCollection;

    public EmpleadoInformacionSucursal() {
    }

    public EmpleadoInformacionSucursal(String noSeguroSocial) {
        this.noSeguroSocial = noSeguroSocial;
    }

    public EmpleadoInformacionSucursal(String noSeguroSocial, String sucursal) {
        this.noSeguroSocial = noSeguroSocial;
        this.sucursal = sucursal;
    }

    public String getNoSeguroSocial() {
        return noSeguroSocial;
    }

    public void setNoSeguroSocial(String noSeguroSocial) {
        this.noSeguroSocial = noSeguroSocial;
    }

    public String getSucursal() {
        return sucursal;
    }

    public void setSucursal(String sucursal) {
        this.sucursal = sucursal;
    }

    @XmlTransient
    public Collection<EmpleadoInformacionPersonal> getEmpleadoInformacionPersonalCollection() {
        return empleadoInformacionPersonalCollection;
    }

    public void setEmpleadoInformacionPersonalCollection(Collection<EmpleadoInformacionPersonal> empleadoInformacionPersonalCollection) {
        this.empleadoInformacionPersonalCollection = empleadoInformacionPersonalCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (noSeguroSocial != null ? noSeguroSocial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EmpleadoInformacionSucursal)) {
            return false;
        }
        EmpleadoInformacionSucursal other = (EmpleadoInformacionSucursal) object;
        if ((this.noSeguroSocial == null && other.noSeguroSocial != null) || (this.noSeguroSocial != null && !this.noSeguroSocial.equals(other.noSeguroSocial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "proyecto_final_doctor.EmpleadoInformacionSucursal[ noSeguroSocial=" + noSeguroSocial + " ]";
    }
    
}
