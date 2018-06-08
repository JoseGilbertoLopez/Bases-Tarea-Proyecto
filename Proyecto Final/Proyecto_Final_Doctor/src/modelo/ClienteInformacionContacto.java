/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
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
@Table(name = "CLIENTE_INFORMACION_CONTACTO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ClienteInformacionContacto.findAll", query = "SELECT c FROM ClienteInformacionContacto c")
    , @NamedQuery(name = "ClienteInformacionContacto.findByNombre", query = "SELECT c FROM ClienteInformacionContacto c WHERE c.clienteInformacionContactoPK.nombre = :nombre")
    , @NamedQuery(name = "ClienteInformacionContacto.findByApellidoMaterno", query = "SELECT c FROM ClienteInformacionContacto c WHERE c.clienteInformacionContactoPK.apellidoMaterno = :apellidoMaterno")
    , @NamedQuery(name = "ClienteInformacionContacto.findByApellidoPaterno", query = "SELECT c FROM ClienteInformacionContacto c WHERE c.clienteInformacionContactoPK.apellidoPaterno = :apellidoPaterno")
    , @NamedQuery(name = "ClienteInformacionContacto.findByDireccion", query = "SELECT c FROM ClienteInformacionContacto c WHERE c.direccion = :direccion")
    , @NamedQuery(name = "ClienteInformacionContacto.findByTelefono", query = "SELECT c FROM ClienteInformacionContacto c WHERE c.telefono = :telefono")
    , @NamedQuery(name = "ClienteInformacionContacto.findByCorreoElectronico", query = "SELECT c FROM ClienteInformacionContacto c WHERE c.correoElectronico = :correoElectronico")})
public class ClienteInformacionContacto implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ClienteInformacionContactoPK clienteInformacionContactoPK;
    @Column(name = "DIRECCION")
    private String direccion;
    @Column(name = "TELEFONO")
    private String telefono;
    @Column(name = "CORREO_ELECTRONICO")
    private String correoElectronico;
    @OneToMany(mappedBy = "clienteInformacionContacto")
    private Collection<ClienteInformacionPersonal> clienteInformacionPersonalCollection;

    public ClienteInformacionContacto() {
    }

    public ClienteInformacionContacto(ClienteInformacionContactoPK clienteInformacionContactoPK) {
        this.clienteInformacionContactoPK = clienteInformacionContactoPK;
    }

    public ClienteInformacionContacto(String nombre, String apellidoMaterno, String apellidoPaterno) {
        this.clienteInformacionContactoPK = new ClienteInformacionContactoPK(nombre, apellidoMaterno, apellidoPaterno);
    }

    public ClienteInformacionContactoPK getClienteInformacionContactoPK() {
        return clienteInformacionContactoPK;
    }

    public void setClienteInformacionContactoPK(ClienteInformacionContactoPK clienteInformacionContactoPK) {
        this.clienteInformacionContactoPK = clienteInformacionContactoPK;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    @XmlTransient
    public Collection<ClienteInformacionPersonal> getClienteInformacionPersonalCollection() {
        return clienteInformacionPersonalCollection;
    }

    public void setClienteInformacionPersonalCollection(Collection<ClienteInformacionPersonal> clienteInformacionPersonalCollection) {
        this.clienteInformacionPersonalCollection = clienteInformacionPersonalCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (clienteInformacionContactoPK != null ? clienteInformacionContactoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ClienteInformacionContacto)) {
            return false;
        }
        ClienteInformacionContacto other = (ClienteInformacionContacto) object;
        if ((this.clienteInformacionContactoPK == null && other.clienteInformacionContactoPK != null) || (this.clienteInformacionContactoPK != null && !this.clienteInformacionContactoPK.equals(other.clienteInformacionContactoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "proyecto_final_doctor.ClienteInformacionContacto[ clienteInformacionContactoPK=" + clienteInformacionContactoPK + " ]";
    }
    
}
