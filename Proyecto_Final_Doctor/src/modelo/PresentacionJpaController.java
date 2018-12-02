/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import Controlador.exceptions.NonexistentEntityException;
import Controlador.exceptions.PreexistingEntityException;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelo.Medicamento;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.Presentacion;

/**
 *
 * @author Jose G
 */
public class PresentacionJpaController implements Serializable {

    public PresentacionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Presentacion presentacion) throws PreexistingEntityException, Exception {
        if (presentacion.getMedicamentoCollection() == null) {
            presentacion.setMedicamentoCollection(new ArrayList<Medicamento>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Medicamento> attachedMedicamentoCollection = new ArrayList<Medicamento>();
            for (Medicamento medicamentoCollectionMedicamentoToAttach : presentacion.getMedicamentoCollection()) {
                medicamentoCollectionMedicamentoToAttach = em.getReference(medicamentoCollectionMedicamentoToAttach.getClass(), medicamentoCollectionMedicamentoToAttach.getMedicamentoPK());
                attachedMedicamentoCollection.add(medicamentoCollectionMedicamentoToAttach);
            }
            presentacion.setMedicamentoCollection(attachedMedicamentoCollection);
            em.persist(presentacion);
            for (Medicamento medicamentoCollectionMedicamento : presentacion.getMedicamentoCollection()) {
                medicamentoCollectionMedicamento.getPresentacionCollection().add(presentacion);
                medicamentoCollectionMedicamento = em.merge(medicamentoCollectionMedicamento);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPresentacion(presentacion.getIdPresentacion()) != null) {
                throw new PreexistingEntityException("Presentacion " + presentacion + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Presentacion presentacion) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Presentacion persistentPresentacion = em.find(Presentacion.class, presentacion.getIdPresentacion());
            Collection<Medicamento> medicamentoCollectionOld = persistentPresentacion.getMedicamentoCollection();
            Collection<Medicamento> medicamentoCollectionNew = presentacion.getMedicamentoCollection();
            Collection<Medicamento> attachedMedicamentoCollectionNew = new ArrayList<Medicamento>();
            for (Medicamento medicamentoCollectionNewMedicamentoToAttach : medicamentoCollectionNew) {
                medicamentoCollectionNewMedicamentoToAttach = em.getReference(medicamentoCollectionNewMedicamentoToAttach.getClass(), medicamentoCollectionNewMedicamentoToAttach.getMedicamentoPK());
                attachedMedicamentoCollectionNew.add(medicamentoCollectionNewMedicamentoToAttach);
            }
            medicamentoCollectionNew = attachedMedicamentoCollectionNew;
            presentacion.setMedicamentoCollection(medicamentoCollectionNew);
            presentacion = em.merge(presentacion);
            for (Medicamento medicamentoCollectionOldMedicamento : medicamentoCollectionOld) {
                if (!medicamentoCollectionNew.contains(medicamentoCollectionOldMedicamento)) {
                    medicamentoCollectionOldMedicamento.getPresentacionCollection().remove(presentacion);
                    medicamentoCollectionOldMedicamento = em.merge(medicamentoCollectionOldMedicamento);
                }
            }
            for (Medicamento medicamentoCollectionNewMedicamento : medicamentoCollectionNew) {
                if (!medicamentoCollectionOld.contains(medicamentoCollectionNewMedicamento)) {
                    medicamentoCollectionNewMedicamento.getPresentacionCollection().add(presentacion);
                    medicamentoCollectionNewMedicamento = em.merge(medicamentoCollectionNewMedicamento);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = presentacion.getIdPresentacion();
                if (findPresentacion(id) == null) {
                    throw new NonexistentEntityException("The presentacion with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(BigDecimal id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Presentacion presentacion;
            try {
                presentacion = em.getReference(Presentacion.class, id);
                presentacion.getIdPresentacion();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The presentacion with id " + id + " no longer exists.", enfe);
            }
            Collection<Medicamento> medicamentoCollection = presentacion.getMedicamentoCollection();
            for (Medicamento medicamentoCollectionMedicamento : medicamentoCollection) {
                medicamentoCollectionMedicamento.getPresentacionCollection().remove(presentacion);
                medicamentoCollectionMedicamento = em.merge(medicamentoCollectionMedicamento);
            }
            em.remove(presentacion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Presentacion> findPresentacionEntities() {
        return findPresentacionEntities(true, -1, -1);
    }

    public List<Presentacion> findPresentacionEntities(int maxResults, int firstResult) {
        return findPresentacionEntities(false, maxResults, firstResult);
    }

    private List<Presentacion> findPresentacionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Presentacion.class));
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

    public Presentacion findPresentacion(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Presentacion.class, id);
        } finally {
            em.close();
        }
    }

    public int getPresentacionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Presentacion> rt = cq.from(Presentacion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
