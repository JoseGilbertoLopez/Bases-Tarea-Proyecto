/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Basic;
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
@Table(name = "EMPLEADO_SUCURSAL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EmpleadoSucursal.findAll", query = "SELECT e FROM EmpleadoSucursal e")
    , @NamedQuery(name = "EmpleadoSucursal.findByIdCliente", query = "SELECT e FROM EmpleadoSucursal e WHERE e.empleadoSucursalPK.idCliente = :idCliente")
    , @NamedQuery(name = "EmpleadoSucursal.findByNoSeguroSocial", query = "SELECT e FROM EmpleadoSucursal e WHERE e.empleadoSucursalPK.noSeguroSocial = :noSeguroSocial")
    , @NamedQuery(name = "EmpleadoSucursal.findByRfc", query = "SELECT e FROM EmpleadoSucursal e WHERE e.empleadoSucursalPK.rfc = :rfc")
    , @NamedQuery(name = "EmpleadoSucursal.findByNombre", query = "SELECT e FROM EmpleadoSucursal e WHERE e.nombre = :nombre")
    , @NamedQuery(name = "EmpleadoSucursal.findByApellidoPaterno", query = "SELECT e FROM EmpleadoSucursal e WHERE e.apellidoPaterno = :apellidoPaterno")
    , @NamedQuery(name = "EmpleadoSucursal.findByApellidoMaterno", query = "SELECT e FROM EmpleadoSucursal e WHERE e.apellidoMaterno = :apellidoMaterno")
    , @NamedQuery(name = "EmpleadoSucursal.findByIdSucursal", query = "SELECT e FROM EmpleadoSucursal e WHERE e.empleadoSucursalPK.idSucursal = :idSucursal")})
public class EmpleadoSucursal implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected EmpleadoSucursalPK empleadoSucursalPK;
    @Basic(optional = false)
    @Column(name = "NOMBRE")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "APELLIDO_PATERNO")
    private String apellidoPaterno;
    @Basic(optional = false)
    @Column(name = "APELLIDO_MATERNO")
    private String apellidoMaterno;
    @JoinColumns({
        @JoinColumn(name = "ID_CLIENTE", referencedColumnName = "ID_CLIENTE", insertable = false, updatable = false)
        , @JoinColumn(name = "NO_SEGURO_SOCIAL", referencedColumnName = "NO_SEGURO_SOCIAL", insertable = false, updatable = false)
        , @JoinColumn(name = "RFC", referencedColumnName = "RFC", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private EmpleadoInformacionPersonal empleadoInformacionPersonal;
    @JoinColumn(name = "ID_SUCURSAL", referencedColumnName = "ID_SUCURSAL", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Sucursal sucursal;

    public EmpleadoSucursal() {
    }

    public EmpleadoSucursal(EmpleadoSucursalPK empleadoSucursalPK) {
        this.empleadoSucursalPK = empleadoSucursalPK;
    }

    public EmpleadoSucursal(EmpleadoSucursalPK empleadoSucursalPK, String nombre, String apellidoPaterno, String apellidoMaterno) {
        this.empleadoSucursalPK = empleadoSucursalPK;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
    }

    public EmpleadoSucursal(BigInteger idCliente, String noSeguroSocial, String rfc, BigInteger idSucursal) {
        this.empleadoSucursalPK = new EmpleadoSucursalPK(idCliente, noSeguroSocial, rfc, idSucursal);
    }

    public EmpleadoSucursalPK getEmpleadoSucursalPK() {
        return empleadoSucursalPK;
    }

    public void setEmpleadoSucursalPK(EmpleadoSucursalPK empleadoSucursalPK) {
        this.empleadoSucursalPK = empleadoSucursalPK;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public EmpleadoInformacionPersonal getEmpleadoInformacionPersonal() {
        return empleadoInformacionPersonal;
    }

    public void setEmpleadoInformacionPersonal(EmpleadoInformacionPersonal empleadoInformacionPersonal) {
        this.empleadoInformacionPersonal = empleadoInformacionPersonal;
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
        hash += (empleadoSucursalPK != null ? empleadoSucursalPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EmpleadoSucursal)) {
            return false;
        }
        EmpleadoSucursal other = (EmpleadoSucursal) object;
        if ((this.empleadoSucursalPK == null && other.empleadoSucursalPK != null) || (this.empleadoSucursalPK != null && !this.empleadoSucursalPK.equals(other.empleadoSucursalPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "proyecto_final_doctor.EmpleadoSucursal[ empleadoSucursalPK=" + empleadoSucursalPK + " ]";
    }
    
}
