/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

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
import modelo.Ingrediente;

/**
 *
 * @author Jose G
 */
public class IngredienteJpaController implements Serializable {

    public IngredienteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Ingrediente ingrediente) throws PreexistingEntityException, Exception {
        if (ingrediente.getMedicamentoCollection() == null) {
            ingrediente.setMedicamentoCollection(new ArrayList<Medicamento>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Medicamento> attachedMedicamentoCollection = new ArrayList<Medicamento>();
            for (Medicamento medicamentoCollectionMedicamentoToAttach : ingrediente.getMedicamentoCollection()) {
                medicamentoCollectionMedicamentoToAttach = em.getReference(medicamentoCollectionMedicamentoToAttach.getClass(), medicamentoCollectionMedicamentoToAttach.getMedicamentoPK());
                attachedMedicamentoCollection.add(medicamentoCollectionMedicamentoToAttach);
            }
            ingrediente.setMedicamentoCollection(attachedMedicamentoCollection);
            em.persist(ingrediente);
            for (Medicamento medicamentoCollectionMedicamento : ingrediente.getMedicamentoCollection()) {
                medicamentoCollectionMedicamento.getIngredienteCollection().add(ingrediente);
                medicamentoCollectionMedicamento = em.merge(medicamentoCollectionMedicamento);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findIngrediente(ingrediente.getIdIngrediente()) != null) {
                throw new PreexistingEntityException("Ingrediente " + ingrediente + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Ingrediente ingrediente) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ingrediente persistentIngrediente = em.find(Ingrediente.class, ingrediente.getIdIngrediente());
            Collection<Medicamento> medicamentoCollectionOld = persistentIngrediente.getMedicamentoCollection();
            Collection<Medicamento> medicamentoCollectionNew = ingrediente.getMedicamentoCollection();
            Collection<Medicamento> attachedMedicamentoCollectionNew = new ArrayList<Medicamento>();
            for (Medicamento medicamentoCollectionNewMedicamentoToAttach : medicamentoCollectionNew) {
                medicamentoCollectionNewMedicamentoToAttach = em.getReference(medicamentoCollectionNewMedicamentoToAttach.getClass(), medicamentoCollectionNewMedicamentoToAttach.getMedicamentoPK());
                attachedMedicamentoCollectionNew.add(medicamentoCollectionNewMedicamentoToAttach);
            }
            medicamentoCollectionNew = attachedMedicamentoCollectionNew;
            ingrediente.setMedicamentoCollection(medicamentoCollectionNew);
            ingrediente = em.merge(ingrediente);
            for (Medicamento medicamentoCollectionOldMedicamento : medicamentoCollectionOld) {
                if (!medicamentoCollectionNew.contains(medicamentoCollectionOldMedicamento)) {
                    medicamentoCollectionOldMedicamento.getIngredienteCollection().remove(ingrediente);
                    medicamentoCollectionOldMedicamento = em.merge(medicamentoCollectionOldMedicamento);
                }
            }
            for (Medicamento medicamentoCollectionNewMedicamento : medicamentoCollectionNew) {
                if (!medicamentoCollectionOld.contains(medicamentoCollectionNewMedicamento)) {
                    medicamentoCollectionNewMedicamento.getIngredienteCollection().add(ingrediente);
                    medicamentoCollectionNewMedicamento = em.merge(medicamentoCollectionNewMedicamento);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = ingrediente.getIdIngrediente();
                if (findIngrediente(id) == null) {
                    throw new NonexistentEntityException("The ingrediente with id " + id + " no longer exists.");
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
            Ingrediente ingrediente;
            try {
                ingrediente = em.getReference(Ingrediente.class, id);
                ingrediente.getIdIngrediente();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ingrediente with id " + id + " no longer exists.", enfe);
            }
            Collection<Medicamento> medicamentoCollection = ingrediente.getMedicamentoCollection();
            for (Medicamento medicamentoCollectionMedicamento : medicamentoCollection) {
                medicamentoCollectionMedicamento.getIngredienteCollection().remove(ingrediente);
                medicamentoCollectionMedicamento = em.merge(medicamentoCollectionMedicamento);
            }
            em.remove(ingrediente);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Ingrediente> findIngredienteEntities() {
        return findIngredienteEntities(true, -1, -1);
    }

    public List<Ingrediente> findIngredienteEntities(int maxResults, int firstResult) {
        return findIngredienteEntities(false, maxResults, firstResult);
    }

    private List<Ingrediente> findIngredienteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Ingrediente.class));
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

    public Ingrediente findIngrediente(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Ingrediente.class, id);
        } finally {
            em.close();
        }
    }

    public int getIngredienteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Ingrediente> rt = cq.from(Ingrediente.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
