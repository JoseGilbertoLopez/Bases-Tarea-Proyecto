/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import Controlador.exceptions.NonexistentEntityException;
import Controlador.exceptions.PreexistingEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelo.Medicamento;
import modelo.MedicamentoReceta;
import modelo.MedicamentoRecetaPK;
import modelo.RecetaInformacionConsulta;

/**
 *
 * @author Jose G
 */
public class MedicamentoRecetaJpaController implements Serializable {

    public MedicamentoRecetaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(MedicamentoReceta medicamentoReceta) throws PreexistingEntityException, Exception {
        if (medicamentoReceta.getMedicamentoRecetaPK() == null) {
            medicamentoReceta.setMedicamentoRecetaPK(new MedicamentoRecetaPK());
        }
        //medicamentoReceta.getMedicamentoRecetaPK().setIdReceta(medicamentoReceta.getRecetaInformacionConsulta().getIdReceta());
        medicamentoReceta.getMedicamentoRecetaPK().setMarca(medicamentoReceta.getMedicamento().getMedicamentoPK().getMarca());
        medicamentoReceta.getMedicamentoRecetaPK().setNombre(medicamentoReceta.getMedicamento().getMedicamentoPK().getNombre());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Medicamento medicamento = medicamentoReceta.getMedicamento();
            if (medicamento != null) {
                medicamento = em.getReference(medicamento.getClass(), medicamento.getMedicamentoPK());
                medicamentoReceta.setMedicamento(medicamento);
            }
            RecetaInformacionConsulta recetaInformacionConsulta = medicamentoReceta.getRecetaInformacionConsulta();
            if (recetaInformacionConsulta != null) {
                recetaInformacionConsulta = em.getReference(recetaInformacionConsulta.getClass(), recetaInformacionConsulta.getIdReceta());
                medicamentoReceta.setRecetaInformacionConsulta(recetaInformacionConsulta);
            }
            em.persist(medicamentoReceta);
            if (medicamento != null) {
                medicamento.getMedicamentoRecetaCollection().add(medicamentoReceta);
                medicamento = em.merge(medicamento);
            }
            if (recetaInformacionConsulta != null) {
                recetaInformacionConsulta.getMedicamentoRecetaCollection().add(medicamentoReceta);
                recetaInformacionConsulta = em.merge(recetaInformacionConsulta);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findMedicamentoReceta(medicamentoReceta.getMedicamentoRecetaPK()) != null) {
                throw new PreexistingEntityException("MedicamentoReceta " + medicamentoReceta + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(MedicamentoReceta medicamentoReceta) throws NonexistentEntityException, Exception {
        //medicamentoReceta.getMedicamentoRecetaPK().setIdReceta(medicamentoReceta.getRecetaInformacionConsulta().getIdReceta());
        medicamentoReceta.getMedicamentoRecetaPK().setMarca(medicamentoReceta.getMedicamento().getMedicamentoPK().getMarca());
        medicamentoReceta.getMedicamentoRecetaPK().setNombre(medicamentoReceta.getMedicamento().getMedicamentoPK().getNombre());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            MedicamentoReceta persistentMedicamentoReceta = em.find(MedicamentoReceta.class, medicamentoReceta.getMedicamentoRecetaPK());
            Medicamento medicamentoOld = persistentMedicamentoReceta.getMedicamento();
            Medicamento medicamentoNew = medicamentoReceta.getMedicamento();
            RecetaInformacionConsulta recetaInformacionConsultaOld = persistentMedicamentoReceta.getRecetaInformacionConsulta();
            RecetaInformacionConsulta recetaInformacionConsultaNew = medicamentoReceta.getRecetaInformacionConsulta();
            if (medicamentoNew != null) {
                medicamentoNew = em.getReference(medicamentoNew.getClass(), medicamentoNew.getMedicamentoPK());
                medicamentoReceta.setMedicamento(medicamentoNew);
            }
            if (recetaInformacionConsultaNew != null) {
                recetaInformacionConsultaNew = em.getReference(recetaInformacionConsultaNew.getClass(), recetaInformacionConsultaNew.getIdReceta());
                medicamentoReceta.setRecetaInformacionConsulta(recetaInformacionConsultaNew);
            }
            medicamentoReceta = em.merge(medicamentoReceta);
            if (medicamentoOld != null && !medicamentoOld.equals(medicamentoNew)) {
                medicamentoOld.getMedicamentoRecetaCollection().remove(medicamentoReceta);
                medicamentoOld = em.merge(medicamentoOld);
            }
            if (medicamentoNew != null && !medicamentoNew.equals(medicamentoOld)) {
                medicamentoNew.getMedicamentoRecetaCollection().add(medicamentoReceta);
                medicamentoNew = em.merge(medicamentoNew);
            }
            if (recetaInformacionConsultaOld != null && !recetaInformacionConsultaOld.equals(recetaInformacionConsultaNew)) {
                recetaInformacionConsultaOld.getMedicamentoRecetaCollection().remove(medicamentoReceta);
                recetaInformacionConsultaOld = em.merge(recetaInformacionConsultaOld);
            }
            if (recetaInformacionConsultaNew != null && !recetaInformacionConsultaNew.equals(recetaInformacionConsultaOld)) {
                recetaInformacionConsultaNew.getMedicamentoRecetaCollection().add(medicamentoReceta);
                recetaInformacionConsultaNew = em.merge(recetaInformacionConsultaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                MedicamentoRecetaPK id = medicamentoReceta.getMedicamentoRecetaPK();
                if (findMedicamentoReceta(id) == null) {
                    throw new NonexistentEntityException("The medicamentoReceta with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(MedicamentoRecetaPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            MedicamentoReceta medicamentoReceta;
            try {
                medicamentoReceta = em.getReference(MedicamentoReceta.class, id);
                medicamentoReceta.getMedicamentoRecetaPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The medicamentoReceta with id " + id + " no longer exists.", enfe);
            }
            Medicamento medicamento = medicamentoReceta.getMedicamento();
            if (medicamento != null) {
                medicamento.getMedicamentoRecetaCollection().remove(medicamentoReceta);
                medicamento = em.merge(medicamento);
            }
            RecetaInformacionConsulta recetaInformacionConsulta = medicamentoReceta.getRecetaInformacionConsulta();
            if (recetaInformacionConsulta != null) {
                recetaInformacionConsulta.getMedicamentoRecetaCollection().remove(medicamentoReceta);
                recetaInformacionConsulta = em.merge(recetaInformacionConsulta);
            }
            em.remove(medicamentoReceta);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<MedicamentoReceta> findMedicamentoRecetaEntities() {
        return findMedicamentoRecetaEntities(true, -1, -1);
    }

    public List<MedicamentoReceta> findMedicamentoRecetaEntities(int maxResults, int firstResult) {
        return findMedicamentoRecetaEntities(false, maxResults, firstResult);
    }

    private List<MedicamentoReceta> findMedicamentoRecetaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(MedicamentoReceta.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public MedicamentoReceta findMedicamentoReceta(MedicamentoRecetaPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(MedicamentoReceta.class, id);
        } finally {
            em.close();
        }
    }

    public int getMedicamentoRecetaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<MedicamentoReceta> rt = cq.from(MedicamentoReceta.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
