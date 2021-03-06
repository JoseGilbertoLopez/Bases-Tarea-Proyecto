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
import javax.persistence.Embeddable;

/**
 *
 * @author Jose G
 */
@Embeddable
public class MedicamentoRecetaPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "MARCA")
    private String marca;
    @Basic(optional = false)
    @Column(name = "NOMBRE")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "ID_RECETA")
    private BigInteger idReceta;

    public MedicamentoRecetaPK() {
    }

    public MedicamentoRecetaPK(String marca, String nombre, BigInteger idReceta) {
        this.marca = marca;
        this.nombre = nombre;
        this.idReceta = idReceta;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public BigInteger getIdReceta() {
        return idReceta;
    }

    public void setIdReceta(BigInteger idReceta) {
        this.idReceta = idReceta;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (marca != null ? marca.hashCode() : 0);
        hash += (nombre != null ? nombre.hashCode() : 0);
        hash += (idReceta != null ? idReceta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MedicamentoRecetaPK)) {
            return false;
        }
        MedicamentoRecetaPK other = (MedicamentoRecetaPK) object;
        if ((this.marca == null && other.marca != null) || (this.marca != null && !this.marca.equals(other.marca))) {
            return false;
        }
        if ((this.nombre == null && other.nombre != null) || (this.nombre != null && !this.nombre.equals(other.nombre))) {
            return false;
        }
        if ((this.idReceta == null && other.idReceta != null) || (this.idReceta != null && !this.idReceta.equals(other.idReceta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "proyecto_final_doctor.MedicamentoRecetaPK[ marca=" + marca + ", nombre=" + nombre + ", idReceta=" + idReceta + " ]";
    }
    
}
