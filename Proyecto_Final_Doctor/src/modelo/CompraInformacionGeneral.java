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
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Jose G
 */
@Entity
@Table(name = "COMPRA_INFORMACION_GENERAL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CompraInformacionGeneral.findAll", query = "SELECT c FROM CompraInformacionGeneral c")
    , @NamedQuery(name = "CompraInformacionGeneral.findByIdCompra", query = "SELECT c FROM CompraInformacionGeneral c WHERE c.idCompra = :idCompra")
    , @NamedQuery(name = "CompraInformacionGeneral.findByNoReceta", query = "SELECT c FROM CompraInformacionGeneral c WHERE c.noReceta = :noReceta")
    , @NamedQuery(name = "CompraInformacionGeneral.findBySucursal", query = "SELECT c FROM CompraInformacionGeneral c WHERE c.sucursal = :sucursal")
    , @NamedQuery(name = "CompraInformacionGeneral.findByFecha", query = "SELECT c FROM CompraInformacionGeneral c WHERE c.fecha = :fecha")
    , @NamedQuery(name = "CompraInformacionGeneral.findByTotal", query = "SELECT c FROM CompraInformacionGeneral c WHERE c.total = :total")
    , @NamedQuery(name = "CompraInformacionGeneral.findByTipoPago", query = "SELECT c FROM CompraInformacionGeneral c WHERE c.tipoPago = :tipoPago")})
public class CompraInformacionGeneral implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID_COMPRA")
    private BigDecimal idCompra;
    @Column(name = "NO_RECETA")
    private BigInteger noReceta;
    @Basic(optional = false)
    @Column(name = "SUCURSAL")
    private BigInteger sucursal;
    @Basic(optional = false)
    @Column(name = "FECHA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Basic(optional = false)
    @Column(name = "TOTAL")
    private int total;
    @Basic(optional = false)
    @Column(name = "TIPO_PAGO")
    private String tipoPago;
    @JoinColumn(name = "VENDEDOR", referencedColumnName = "VENDEDOR")
    @ManyToOne(optional = false)
    private CompraInformacionSucven vendedor;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "compraInformacionGeneral")
    private Collection<MedicamentoCompra> medicamentoCompraCollection;

    public CompraInformacionGeneral() {
    }

    public CompraInformacionGeneral(BigDecimal idCompra) {
        this.idCompra = idCompra;
    }

    public CompraInformacionGeneral(BigDecimal idCompra, BigInteger sucursal, Date fecha, int total, String tipoPago) {
        this.idCompra = idCompra;
        this.sucursal = sucursal;
        this.fecha = fecha;
        this.total = total;
        this.tipoPago = tipoPago;
    }

    public BigDecimal getIdCompra() {
        return idCompra;
    }

    public void setIdCompra(BigDecimal idCompra) {
        this.idCompra = idCompra;
    }

    public BigInteger getNoReceta() {
        return noReceta;
    }

    public void setNoReceta(BigInteger noReceta) {
        this.noReceta = noReceta;
    }

    public BigInteger getSucursal() {
        return sucursal;
    }

    public void setSucursal(BigInteger sucursal) {
        this.sucursal = sucursal;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getTipoPago() {
        return tipoPago;
    }

    public void setTipoPago(String tipoPago) {
        this.tipoPago = tipoPago;
    }

    public CompraInformacionSucven getVendedor() {
        return vendedor;
    }

    public void setVendedor(CompraInformacionSucven vendedor) {
        this.vendedor = vendedor;
    }

    @XmlTransient
    public Collection<MedicamentoCompra> getMedicamentoCompraCollection() {
        return medicamentoCompraCollection;
    }

    public void setMedicamentoCompraCollection(Collection<MedicamentoCompra> medicamentoCompraCollection) {
        this.medicamentoCompraCollection = medicamentoCompraCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCompra != null ? idCompra.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CompraInformacionGeneral)) {
            return false;
        }
        CompraInformacionGeneral other = (CompraInformacionGeneral) object;
        if ((this.idCompra == null && other.idCompra != null) || (this.idCompra != null && !this.idCompra.equals(other.idCompra))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "proyecto_final_doctor.CompraInformacionGeneral[ idCompra=" + idCompra + " ]";
    }
    
}
