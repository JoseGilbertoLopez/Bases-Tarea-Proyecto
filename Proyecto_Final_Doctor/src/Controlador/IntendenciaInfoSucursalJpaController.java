/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Controlador.exceptions.IllegalOrphanException;
import Controlador.exceptions.NonexistentEntityException;
import Controlador.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelo.IntendenciaInfoPersonal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.IntendenciaInfoSucursal;

/**
 *
 * @author Jose G
 */
public class IntendenciaInfoSucursalJpaController implements Serializable {

    public IntendenciaInfoSucursalJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(IntendenciaInfoSucursal intendenciaInfoSucursal) throws PreexistingEntityException, Exception {
        if (intendenciaInfoSucursal.getIntendenciaInfoPersonalCollection() == null) {
            intendenciaInfoSucursal.setIntendenciaInfoPersonalCollection(new ArrayList<IntendenciaInfoPersonal>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<IntendenciaInfoPersonal> attachedIntendenciaInfoPersonalCollection = new ArrayList<IntendenciaInfoPersonal>();
            for (IntendenciaInfoPersonal intendenciaInfoPersonalCollectionIntendenciaInfoPersonalToAttach : intendenciaInfoSucursal.getIntendenciaInfoPersonalCollection()) {
                intendenciaInfoPersonalCollectionIntendenciaInfoPersonalToAttach = em.getReference(intendenciaInfoPersonalCollectionIntendenciaInfoPersonalToAttach.getClass(), intendenciaInfoPersonalCollectionIntendenciaInfoPersonalToAttach.getIntendenciaInfoPersonalPK());
                attachedIntendenciaInfoPersonalCollection.add(intendenciaInfoPersonalCollectionIntendenciaInfoPersonalToAttach);
            }
            intendenciaInfoSucursal.setIntendenciaInfoPersonalCollection(attachedIntendenciaInfoPersonalCollection);
            em.persist(intendenciaInfoSucursal);
            for (IntendenciaInfoPersonal intendenciaInfoPersonalCollectionIntendenciaInfoPersonal : intendenciaInfoSucursal.getIntendenciaInfoPersonalCollection()) {
                IntendenciaInfoSucursal oldIntendenciaInfoSucursalOfIntendenciaInfoPersonalCollectionIntendenciaInfoPersonal = intendenciaInfoPersonalCollectionIntendenciaInfoPersonal.getIntendenciaInfoSucursal();
                intendenciaInfoPersonalCollectionIntendenciaInfoPersonal.setIntendenciaInfoSucursal(intendenciaInfoSucursal);
                intendenciaInfoPersonalCollectionIntendenciaInfoPersonal = em.merge(intendenciaInfoPersonalCollectionIntendenciaInfoPersonal);
                if (oldIntendenciaInfoSucursalOfIntendenciaInfoPersonalCollectionIntendenciaInfoPersonal != null) {
                    oldIntendenciaInfoSucursalOfIntendenciaInfoPersonalCollectionIntendenciaInfoPersonal.getIntendenciaInfoPersonalCollection().remove(intendenciaInfoPersonalCollectionIntendenciaInfoPersonal);
                    oldIntendenciaInfoSucursalOfIntendenciaInfoPersonalCollectionIntendenciaInfoPersonal = em.merge(oldIntendenciaInfoSucursalOfIntendenciaInfoPersonalCollectionIntendenciaInfoPersonal);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findIntendenciaInfoSucursal(intendenciaInfoSucursal.getNoSeguroSocial()) != null) {
                throw new PreexistingEntityException("IntendenciaInfoSucursal " + intendenciaInfoSucursal + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(IntendenciaInfoSucursal intendenciaInfoSucursal) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            IntendenciaInfoSucursal persistentIntendenciaInfoSucursal = em.find(IntendenciaInfoSucursal.class, intendenciaInfoSucursal.getNoSeguroSocial());
            Collection<IntendenciaInfoPersonal> intendenciaInfoPersonalCollectionOld = persistentIntendenciaInfoSucursal.getIntendenciaInfoPersonalCollection();
            Collection<IntendenciaInfoPersonal> intendenciaInfoPersonalCollectionNew = intendenciaInfoSucursal.getIntendenciaInfoPersonalCollection();
            List<String> illegalOrphanMessages = null;
            for (IntendenciaInfoPersonal intendenciaInfoPersonalCollectionOldIntendenciaInfoPersonal : intendenciaInfoPersonalCollectionOld) {
                if (!intendenciaInfoPersonalCollectionNew.contains(intendenciaInfoPersonalCollectionOldIntendenciaInfoPersonal)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain IntendenciaInfoPersonal " + intendenciaInfoPersonalCollectionOldIntendenciaInfoPersonal + " since its intendenciaInfoSucursal field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<IntendenciaInfoPersonal> attachedIntendenciaInfoPersonalCollectionNew = new ArrayList<IntendenciaInfoPersonal>();
            for (IntendenciaInfoPersonal intendenciaInfoPersonalCollectionNewIntendenciaInfoPersonalToAttach : intendenciaInfoPersonalCollectionNew) {
                intendenciaInfoPersonalCollectionNewIntendenciaInfoPersonalToAttach = em.getReference(intendenciaInfoPersonalCollectionNewIntendenciaInfoPersonalToAttach.getClass(), intendenciaInfoPersonalCollectionNewIntendenciaInfoPersonalToAttach.getIntendenciaInfoPersonalPK());
                attachedIntendenciaInfoPersonalCollectionNew.add(intendenciaInfoPersonalCollectionNewIntendenciaInfoPersonalToAttach);
            }
            intendenciaInfoPersonalCollectionNew = attachedIntendenciaInfoPersonalCollectionNew;
            intendenciaInfoSucursal.setIntendenciaInfoPersonalCollection(intendenciaInfoPersonalCollectionNew);
            intendenciaInfoSucursal = em.merge(intendenciaInfoSucursal);
            for (IntendenciaInfoPersonal intendenciaInfoPersonalCollectionNewIntendenciaInfoPersonal : intendenciaInfoPersonalCollectionNew) {
                if (!intendenciaInfoPersonalCollectionOld.contains(intendenciaInfoPersonalCollectionNewIntendenciaInfoPersonal)) {
                    IntendenciaInfoSucursal oldIntendenciaInfoSucursalOfIntendenciaInfoPersonalCollectionNewIntendenciaInfoPersonal = intendenciaInfoPersonalCollectionNewIntendenciaInfoPersonal.getIntendenciaInfoSucursal();
                    intendenciaInfoPersonalCollectionNewIntendenciaInfoPersonal.setIntendenciaInfoSucursal(intendenciaInfoSucursal);
                    intendenciaInfoPersonalCollectionNewIntendenciaInfoPersonal = em.merge(intendenciaInfoPersonalCollectionNewIntendenciaInfoPersonal);
                    if (oldIntendenciaInfoSucursalOfIntendenciaInfoPersonalCollectionNewIntendenciaInfoPersonal != null && !oldIntendenciaInfoSucursalOfIntendenciaInfoPersonalCollectionNewIntendenciaInfoPersonal.equals(intendenciaInfoSucursal)) {
                        oldIntendenciaInfoSucursalOfIntendenciaInfoPersonalCollectionNewIntendenciaInfoPersonal.getIntendenciaInfoPersonalCollection().remove(intendenciaInfoPersonalCollectionNewIntendenciaInfoPersonal);
                        oldIntendenciaInfoSucursalOfIntendenciaInfoPersonalCollectionNewIntendenciaInfoPersonal = em.merge(oldIntendenciaInfoSucursalOfIntendenciaInfoPersonalCollectionNewIntendenciaInfoPersonal);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = intendenciaInfoSucursal.getNoSeguroSocial();
                if (findIntendenciaInfoSucursal(id) == null) {
                    throw new NonexistentEntityException("The intendenciaInfoSucursal with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            IntendenciaInfoSucursal intendenciaInfoSucursal;
            try {
                intendenciaInfoSucursal = em.getReference(IntendenciaInfoSucursal.class, id);
                intendenciaInfoSucursal.getNoSeguroSocial();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The intendenciaInfoSucursal with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<IntendenciaInfoPersonal> intendenciaInfoPersonalCollectionOrphanCheck = intendenciaInfoSucursal.getIntendenciaInfoPersonalCollection();
            for (IntendenciaInfoPersonal intendenciaInfoPersonalCollectionOrphanCheckIntendenciaInfoPersonal : intendenciaInfoPersonalCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This IntendenciaInfoSucursal (" + intendenciaInfoSucursal + ") cannot be destroyed since the IntendenciaInfoPersonal " + intendenciaInfoPersonalCollectionOrphanCheckIntendenciaInfoPersonal + " in its intendenciaInfoPersonalCollection field has a non-nullable intendenciaInfoSucursal field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(intendenciaInfoSucursal);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<IntendenciaInfoSucursal> findIntendenciaInfoSucursalEntities() {
        return findIntendenciaInfoSucursalEntities(true, -1, -1);
    }

    public List<IntendenciaInfoSucursal> findIntendenciaInfoSucursalEntities(int maxResults, int firstResult) {
        return findIntendenciaInfoSucursalEntities(false, maxResults, firstResult);
    }

    private List<IntendenciaInfoSucursal> findIntendenciaInfoSucursalEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(IntendenciaInfoSucursal.class));
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

    public IntendenciaInfoSucursal findIntendenciaInfoSucursal(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(IntendenciaInfoSucursal.class, id);
        } finally {
            em.close();
        }
    }

    public int getIntendenciaInfoSucursalCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<IntendenciaInfoSucursal> rt = cq.from(IntendenciaInfoSucursal.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
