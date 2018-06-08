/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
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
@Table(name = "COMPRA_INFORMACION_SUCVEN")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CompraInformacionSucven.findAll", query = "SELECT c FROM CompraInformacionSucven c")
    , @NamedQuery(name = "CompraInformacionSucven.findBySucursal", query = "SELECT c FROM CompraInformacionSucven c WHERE c.sucursal = :sucursal")
    , @NamedQuery(name = "CompraInformacionSucven.findByVendedor", query = "SELECT c FROM CompraInformacionSucven c WHERE c.vendedor = :vendedor")})
public class CompraInformacionSucven implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "SUCURSAL")
    private BigInteger sucursal;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "VENDEDOR")
    private BigDecimal vendedor;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "vendedor")
    private Collection<CompraInformacionGeneral> compraInformacionGeneralCollection;

    public CompraInformacionSucven() {
    }

    public CompraInformacionSucven(BigDecimal vendedor) {
        this.vendedor = vendedor;
    }

    public CompraInformacionSucven(BigDecimal vendedor, BigInteger sucursal) {
        this.vendedor = vendedor;
        this.sucursal = sucursal;
    }

    public BigInteger getSucursal() {
        return sucursal;
    }

    public void setSucursal(BigInteger sucursal) {
        this.sucursal = sucursal;
    }

    public BigDecimal getVendedor() {
        return vendedor;
    }

    public void setVendedor(BigDecimal vendedor) {
        this.vendedor = vendedor;
    }

    @XmlTransient
    public Collection<CompraInformacionGeneral> getCompraInformacionGeneralCollection() {
        return compraInformacionGeneralCollection;
    }

    public void setCompraInformacionGeneralCollection(Collection<CompraInformacionGeneral> compraInformacionGeneralCollection) {
        this.compraInformacionGeneralCollection = compraInformacionGeneralCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (vendedor != null ? vendedor.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CompraInformacionSucven)) {
            return false;
        }
        CompraInformacionSucven other = (CompraInformacionSucven) object;
        if ((this.vendedor == null && other.vendedor != null) || (this.vendedor != null && !this.vendedor.equals(other.vendedor))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "proyecto_final_doctor.CompraInformacionSucven[ vendedor=" + vendedor + " ]";
    }
    
}
