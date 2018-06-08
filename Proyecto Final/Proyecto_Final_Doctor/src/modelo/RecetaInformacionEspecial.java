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
@Table(name = "RECETA_INFORMACION_ESPECIAL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RecetaInformacionEspecial.findAll", query = "SELECT r FROM RecetaInformacionEspecial r")
    , @NamedQuery(name = "RecetaInformacionEspecial.findByIdReceta", query = "SELECT r FROM RecetaInformacionEspecial r WHERE r.recetaInformacionEspecialPK.idReceta = :idReceta")
    , @NamedQuery(name = "RecetaInformacionEspecial.findByMedico", query = "SELECT r FROM RecetaInformacionEspecial r WHERE r.recetaInformacionEspecialPK.medico = :medico")
    , @NamedQuery(name = "RecetaInformacionEspecial.findByFirma", query = "SELECT r FROM RecetaInformacionEspecial r WHERE r.firma = :firma")
    , @NamedQuery(name = "RecetaInformacionEspecial.findByPdf", query = "SELECT r FROM RecetaInformacionEspecial r WHERE r.pdf = :pdf")})
public class RecetaInformacionEspecial implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected RecetaInformacionEspecialPK recetaInformacionEspecialPK;
    @Basic(optional = false)
    @Column(name = "FIRMA")
    private Serializable firma;
    @Column(name = "PDF")
    private String pdf;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recetaInformacionEspecial")
    private Collection<RecetaInformacionConsulta> recetaInformacionConsultaCollection;

    public RecetaInformacionEspecial() {
    }

    public RecetaInformacionEspecial(RecetaInformacionEspecialPK recetaInformacionEspecialPK) {
        this.recetaInformacionEspecialPK = recetaInformacionEspecialPK;
    }

    public RecetaInformacionEspecial(RecetaInformacionEspecialPK recetaInformacionEspecialPK, Serializable firma) {
        this.recetaInformacionEspecialPK = recetaInformacionEspecialPK;
        this.firma = firma;
    }

    public RecetaInformacionEspecial(BigInteger idReceta, BigInteger medico) {
        this.recetaInformacionEspecialPK = new RecetaInformacionEspecialPK(idReceta, medico);
    }

    public RecetaInformacionEspecialPK getRecetaInformacionEspecialPK() {
        return recetaInformacionEspecialPK;
    }

    public void setRecetaInformacionEspecialPK(RecetaInformacionEspecialPK recetaInformacionEspecialPK) {
        this.recetaInformacionEspecialPK = recetaInformacionEspecialPK;
    }

    public Serializable getFirma() {
        return firma;
    }

    public void setFirma(Serializable firma) {
        this.firma = firma;
    }

    public String getPdf() {
        return pdf;
    }

    public void setPdf(String pdf) {
        this.pdf = pdf;
    }

    @XmlTransient
    public Collection<RecetaInformacionConsulta> getRecetaInformacionConsultaCollection() {
        return recetaInformacionConsultaCollection;
    }

    public void setRecetaInformacionConsultaCollection(Collection<RecetaInformacionConsulta> recetaInformacionConsultaCollection) {
        this.recetaInformacionConsultaCollection = recetaInformacionConsultaCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (recetaInformacionEspecialPK != null ? recetaInformacionEspecialPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RecetaInformacionEspecial)) {
            return false;
        }
        RecetaInformacionEspecial other = (RecetaInformacionEspecial) object;
        if ((this.recetaInformacionEspecialPK == null && other.recetaInformacionEspecialPK != null) || (this.recetaInformacionEspecialPK != null && !this.recetaInformacionEspecialPK.equals(other.recetaInformacionEspecialPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "proyecto_final_doctor.RecetaInformacionEspecial[ recetaInformacionEspecialPK=" + recetaInformacionEspecialPK + " ]";
    }
    
}
