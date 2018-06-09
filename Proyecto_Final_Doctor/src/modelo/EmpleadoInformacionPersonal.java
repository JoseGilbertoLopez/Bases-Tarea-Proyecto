/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
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
@Table(name = "EMPLEADO_INFORMACION_PERSONAL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EmpleadoInformacionPersonal.findAll", query = "SELECT e FROM EmpleadoInformacionPersonal e")
    , @NamedQuery(name = "EmpleadoInformacionPersonal.findByIdCliente", query = "SELECT e FROM EmpleadoInformacionPersonal e WHERE e.empleadoInformacionPersonalPK.idCliente = :idCliente")
    , @NamedQuery(name = "EmpleadoInformacionPersonal.findByNoSeguroSocial", query = "SELECT e FROM EmpleadoInformacionPersonal e WHERE e.empleadoInformacionPersonalPK.noSeguroSocial = :noSeguroSocial")
    , @NamedQuery(name = "EmpleadoInformacionPersonal.findByRfc", query = "SELECT e FROM EmpleadoInformacionPersonal e WHERE e.empleadoInformacionPersonalPK.rfc = :rfc")
    , @NamedQuery(name = "EmpleadoInformacionPersonal.findByApellidoPaterno", query = "SELECT e FROM EmpleadoInformacionPersonal e WHERE e.apellidoPaterno = :apellidoPaterno")
    , @NamedQuery(name = "EmpleadoInformacionPersonal.findByApellidoMaterno", query = "SELECT e FROM EmpleadoInformacionPersonal e WHERE e.apellidoMaterno = :apellidoMaterno")})
public class EmpleadoInformacionPersonal implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected EmpleadoInformacionPersonalPK empleadoInformacionPersonalPK;
    @Basic(optional = false)
    @Column(name = "APELLIDO_PATERNO")
    private String apellidoPaterno;
    @Basic(optional = false)
    @Column(name = "APELLIDO_MATERNO")
    private String apellidoMaterno;
    @JoinColumns({
        @JoinColumn(name = "RFC", referencedColumnName = "RFC", insertable = false, updatable = false)
        , @JoinColumn(name = "NOMBRE", referencedColumnName = "NOMBRE")})
    @ManyToOne(optional = false)
    private EmpleadoInformacionContacto empleadoInformacionContacto;
    @JoinColumn(name = "NO_SEGURO_SOCIAL", referencedColumnName = "NO_SEGURO_SOCIAL", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private EmpleadoInformacionSucursal empleadoInformacionSucursal;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "empleadoInformacionPersonal")
    private Collection<EmpleadoSucursal> empleadoSucursalCollection;

    public EmpleadoInformacionPersonal() {
    }

    public EmpleadoInformacionPersonal(EmpleadoInformacionPersonalPK empleadoInformacionPersonalPK) {
        this.empleadoInformacionPersonalPK = empleadoInformacionPersonalPK;
    }

    public EmpleadoInformacionPersonal(EmpleadoInformacionPersonalPK empleadoInformacionPersonalPK, String apellidoPaterno, String apellidoMaterno) {
        this.empleadoInformacionPersonalPK = empleadoInformacionPersonalPK;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
    }

    public EmpleadoInformacionPersonal(BigInteger idCliente, String noSeguroSocial, String rfc) {
        this.empleadoInformacionPersonalPK = new EmpleadoInformacionPersonalPK(idCliente, noSeguroSocial, rfc);
    }

    public EmpleadoInformacionPersonalPK getEmpleadoInformacionPersonalPK() {
        return empleadoInformacionPersonalPK;
    }

    public void setEmpleadoInformacionPersonalPK(EmpleadoInformacionPersonalPK empleadoInformacionPersonalPK) {
        this.empleadoInformacionPersonalPK = empleadoInformacionPersonalPK;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public EmpleadoInformacionContacto getEmpleadoInformacionContacto() {
        return empleadoInformacionContacto;
    }

    public void setEmpleadoInformacionContacto(EmpleadoInformacionContacto empleadoInformacionContacto) {
        this.empleadoInformacionContacto = empleadoInformacionContacto;
    }

    public EmpleadoInformacionSucursal getEmpleadoInformacionSucursal() {
        return empleadoInformacionSucursal;
    }

    public void setEmpleadoInformacionSucursal(EmpleadoInformacionSucursal empleadoInformacionSucursal) {
        this.empleadoInformacionSucursal = empleadoInformacionSucursal;
    }

    @XmlTransient
    public Collection<EmpleadoSucursal> getEmpleadoSucursalCollection() {
        return empleadoSucursalCollection;
    }

    public void setEmpleadoSucursalCollection(Collection<EmpleadoSucursal> empleadoSucursalCollection) {
        this.empleadoSucursalCollection = empleadoSucursalCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (empleadoInformacionPersonalPK != null ? empleadoInformacionPersonalPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EmpleadoInformacionPersonal)) {
            return false;
        }
        EmpleadoInformacionPersonal other = (EmpleadoInformacionPersonal) object;
        if ((this.empleadoInformacionPersonalPK == null && other.empleadoInformacionPersonalPK != null) || (this.empleadoInformacionPersonalPK != null && !this.empleadoInformacionPersonalPK.equals(other.empleadoInformacionPersonalPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "proyecto_final_doctor.EmpleadoInformacionPersonal[ empleadoInformacionPersonalPK=" + empleadoInformacionPersonalPK + " ]";
    }
    
}
