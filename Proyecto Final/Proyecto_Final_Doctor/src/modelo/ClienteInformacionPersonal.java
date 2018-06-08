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
@Table(name = "CLIENTE_INFORMACION_PERSONAL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ClienteInformacionPersonal.findAll", query = "SELECT c FROM ClienteInformacionPersonal c")
    , @NamedQuery(name = "ClienteInformacionPersonal.findByIdCliente", query = "SELECT c FROM ClienteInformacionPersonal c WHERE c.idCliente = :idCliente")
    , @NamedQuery(name = "ClienteInformacionPersonal.findByMonedero", query = "SELECT c FROM ClienteInformacionPersonal c WHERE c.monedero = :monedero")})
public class ClienteInformacionPersonal implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID_CLIENTE")
    private BigDecimal idCliente;
    @Column(name = "MONEDERO")
    private Integer monedero;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "clienteInformacionPersonal")
    private Collection<ClienteReceta> clienteRecetaCollection;
    @JoinColumns({
        @JoinColumn(name = "NOMBRE", referencedColumnName = "NOMBRE")
        , @JoinColumn(name = "APELLIDO_MATERNO", referencedColumnName = "APELLIDO_MATERNO")
        , @JoinColumn(name = "APELLIDO_PATERNO", referencedColumnName = "APELLIDO_PATERNO")})
    @ManyToOne
    private ClienteInformacionContacto clienteInformacionContacto;

    public ClienteInformacionPersonal() {
    }

    public ClienteInformacionPersonal(BigDecimal idCliente) {
        this.idCliente = idCliente;
    }

    public BigDecimal getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(BigDecimal idCliente) {
        this.idCliente = idCliente;
    }

    public Integer getMonedero() {
        return monedero;
    }

    public void setMonedero(Integer monedero) {
        this.monedero = monedero;
    }

    @XmlTransient
    public Collection<ClienteReceta> getClienteRecetaCollection() {
        return clienteRecetaCollection;
    }

    public void setClienteRecetaCollection(Collection<ClienteReceta> clienteRecetaCollection) {
        this.clienteRecetaCollection = clienteRecetaCollection;
    }

    public ClienteInformacionContacto getClienteInformacionContacto() {
        return clienteInformacionContacto;
    }

    public void setClienteInformacionContacto(ClienteInformacionContacto clienteInformacionContacto) {
        this.clienteInformacionContacto = clienteInformacionContacto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCliente != null ? idCliente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ClienteInformacionPersonal)) {
            return false;
        }
        ClienteInformacionPersonal other = (ClienteInformacionPersonal) object;
        if ((this.idCliente == null && other.idCliente != null) || (this.idCliente != null && !this.idCliente.equals(other.idCliente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "proyecto_final_doctor.ClienteInformacionPersonal[ idCliente=" + idCliente + " ]";
    }
    
}
