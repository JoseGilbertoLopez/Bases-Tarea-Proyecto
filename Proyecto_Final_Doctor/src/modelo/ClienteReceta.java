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
@Table(name = "CLIENTE_RECETA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ClienteReceta.findAll", query = "SELECT c FROM ClienteReceta c")
    , @NamedQuery(name = "ClienteReceta.findByIdCliente", query = "SELECT c FROM ClienteReceta c WHERE c.clienteRecetaPK.idCliente = :idCliente")
    , @NamedQuery(name = "ClienteReceta.findByNombre", query = "SELECT c FROM ClienteReceta c WHERE c.nombre = :nombre")
    , @NamedQuery(name = "ClienteReceta.findByApellidoMaterno", query = "SELECT c FROM ClienteReceta c WHERE c.apellidoMaterno = :apellidoMaterno")
    , @NamedQuery(name = "ClienteReceta.findByApellidoPaterno", query = "SELECT c FROM ClienteReceta c WHERE c.apellidoPaterno = :apellidoPaterno")
    , @NamedQuery(name = "ClienteReceta.findByIdReceta", query = "SELECT c FROM ClienteReceta c WHERE c.clienteRecetaPK.idReceta = :idReceta")})
public class ClienteReceta implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ClienteRecetaPK clienteRecetaPK;
    @Basic(optional = false)
    @Column(name = "NOMBRE")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "APELLIDO_MATERNO")
    private String apellidoMaterno;
    @Basic(optional = false)
    @Column(name = "APELLIDO_PATERNO")
    private String apellidoPaterno;
    @JoinColumn(name = "ID_CLIENTE", referencedColumnName = "ID_CLIENTE", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private ClienteInformacionPersonal clienteInformacionPersonal;
    @JoinColumn(name = "ID_RECETA", referencedColumnName = "ID_RECETA", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private RecetaInformacionConsulta recetaInformacionConsulta;

    public ClienteReceta() {
    }

    public ClienteReceta(ClienteRecetaPK clienteRecetaPK) {
        this.clienteRecetaPK = clienteRecetaPK;
    }

    public ClienteReceta(ClienteRecetaPK clienteRecetaPK, String nombre, String apellidoMaterno, String apellidoPaterno) {
        this.clienteRecetaPK = clienteRecetaPK;
        this.nombre = nombre;
        this.apellidoMaterno = apellidoMaterno;
        this.apellidoPaterno = apellidoPaterno;
    }

    public ClienteReceta(BigInteger idCliente, BigInteger idReceta) {
        this.clienteRecetaPK = new ClienteRecetaPK(idCliente, idReceta);
    }

    public ClienteRecetaPK getClienteRecetaPK() {
        return clienteRecetaPK;
    }

    public void setClienteRecetaPK(ClienteRecetaPK clienteRecetaPK) {
        this.clienteRecetaPK = clienteRecetaPK;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public ClienteInformacionPersonal getClienteInformacionPersonal() {
        return clienteInformacionPersonal;
    }

    public void setClienteInformacionPersonal(ClienteInformacionPersonal clienteInformacionPersonal) {
        this.clienteInformacionPersonal = clienteInformacionPersonal;
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
        hash += (clienteRecetaPK != null ? clienteRecetaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ClienteReceta)) {
            return false;
        }
        ClienteReceta other = (ClienteReceta) object;
        if ((this.clienteRecetaPK == null && other.clienteRecetaPK != null) || (this.clienteRecetaPK != null && !this.clienteRecetaPK.equals(other.clienteRecetaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "proyecto_final_doctor.ClienteReceta[ clienteRecetaPK=" + clienteRecetaPK + " ]";
    }
    
}
