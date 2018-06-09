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
import modelo.ResponsableInfoPersonal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.ResponsableInfoSucursal;

/**
 *
 * @author Jose G
 */
public class ResponsableInfoSucursalJpaController implements Serializable {

    public ResponsableInfoSucursalJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ResponsableInfoSucursal responsableInfoSucursal) throws PreexistingEntityException, Exception {
        if (responsableInfoSucursal.getResponsableInfoPersonalCollection() == null) {
            responsableInfoSucursal.setResponsableInfoPersonalCollection(new ArrayList<ResponsableInfoPersonal>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<ResponsableInfoPersonal> attachedResponsableInfoPersonalCollection = new ArrayList<ResponsableInfoPersonal>();
            for (ResponsableInfoPersonal responsableInfoPersonalCollectionResponsableInfoPersonalToAttach : responsableInfoSucursal.getResponsableInfoPersonalCollection()) {
                responsableInfoPersonalCollectionResponsableInfoPersonalToAttach = em.getReference(responsableInfoPersonalCollectionResponsableInfoPersonalToAttach.getClass(), responsableInfoPersonalCollectionResponsableInfoPersonalToAttach.getResponsableInfoPersonalPK());
                attachedResponsableInfoPersonalCollection.add(responsableInfoPersonalCollectionResponsableInfoPersonalToAttach);
            }
            responsableInfoSucursal.setResponsableInfoPersonalCollection(attachedResponsableInfoPersonalCollection);
            em.persist(responsableInfoSucursal);
            for (ResponsableInfoPersonal responsableInfoPersonalCollectionResponsableInfoPersonal : responsableInfoSucursal.getResponsableInfoPersonalCollection()) {
                ResponsableInfoSucursal oldResponsableInfoSucursalOfResponsableInfoPersonalCollectionResponsableInfoPersonal = responsableInfoPersonalCollectionResponsableInfoPersonal.getResponsableInfoSucursal();
                responsableInfoPersonalCollectionResponsableInfoPersonal.setResponsableInfoSucursal(responsableInfoSucursal);
                responsableInfoPersonalCollectionResponsableInfoPersonal = em.merge(responsableInfoPersonalCollectionResponsableInfoPersonal);
                if (oldResponsableInfoSucursalOfResponsableInfoPersonalCollectionResponsableInfoPersonal != null) {
                    oldResponsableInfoSucursalOfResponsableInfoPersonalCollectionResponsableInfoPersonal.getResponsableInfoPersonalCollection().remove(responsableInfoPersonalCollectionResponsableInfoPersonal);
                    oldResponsableInfoSucursalOfResponsableInfoPersonalCollectionResponsableInfoPersonal = em.merge(oldResponsableInfoSucursalOfResponsableInfoPersonalCollectionResponsableInfoPersonal);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findResponsableInfoSucursal(responsableInfoSucursal.getNoSeguroSocial()) != null) {
                throw new PreexistingEntityException("ResponsableInfoSucursal " + responsableInfoSucursal + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ResponsableInfoSucursal responsableInfoSucursal) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ResponsableInfoSucursal persistentResponsableInfoSucursal = em.find(ResponsableInfoSucursal.class, responsableInfoSucursal.getNoSeguroSocial());
            Collection<ResponsableInfoPersonal> responsableInfoPersonalCollectionOld = persistentResponsableInfoSucursal.getResponsableInfoPersonalCollection();
            Collection<ResponsableInfoPersonal> responsableInfoPersonalCollectionNew = responsableInfoSucursal.getResponsableInfoPersonalCollection();
            List<String> illegalOrphanMessages = null;
            for (ResponsableInfoPersonal responsableInfoPersonalCollectionOldResponsableInfoPersonal : responsableInfoPersonalCollectionOld) {
                if (!responsableInfoPersonalCollectionNew.contains(responsableInfoPersonalCollectionOldResponsableInfoPersonal)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ResponsableInfoPersonal " + responsableInfoPersonalCollectionOldResponsableInfoPersonal + " since its responsableInfoSucursal field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<ResponsableInfoPersonal> attachedResponsableInfoPersonalCollectionNew = new ArrayList<ResponsableInfoPersonal>();
            for (ResponsableInfoPersonal responsableInfoPersonalCollectionNewResponsableInfoPersonalToAttach : responsableInfoPersonalCollectionNew) {
                responsableInfoPersonalCollectionNewResponsableInfoPersonalToAttach = em.getReference(responsableInfoPersonalCollectionNewResponsableInfoPersonalToAttach.getClass(), responsableInfoPersonalCollectionNewResponsableInfoPersonalToAttach.getResponsableInfoPersonalPK());
                attachedResponsableInfoPersonalCollectionNew.add(responsableInfoPersonalCollectionNewResponsableInfoPersonalToAttach);
            }
            responsableInfoPersonalCollectionNew = attachedResponsableInfoPersonalCollectionNew;
            responsableInfoSucursal.setResponsableInfoPersonalCollection(responsableInfoPersonalCollectionNew);
            responsableInfoSucursal = em.merge(responsableInfoSucursal);
            for (ResponsableInfoPersonal responsableInfoPersonalCollectionNewResponsableInfoPersonal : responsableInfoPersonalCollectionNew) {
                if (!responsableInfoPersonalCollectionOld.contains(responsableInfoPersonalCollectionNewResponsableInfoPersonal)) {
                    ResponsableInfoSucursal oldResponsableInfoSucursalOfResponsableInfoPersonalCollectionNewResponsableInfoPersonal = responsableInfoPersonalCollectionNewResponsableInfoPersonal.getResponsableInfoSucursal();
                    responsableInfoPersonalCollectionNewResponsableInfoPersonal.setResponsableInfoSucursal(responsableInfoSucursal);
                    responsableInfoPersonalCollectionNewResponsableInfoPersonal = em.merge(responsableInfoPersonalCollectionNewResponsableInfoPersonal);
                    if (oldResponsableInfoSucursalOfResponsableInfoPersonalCollectionNewResponsableInfoPersonal != null && !oldResponsableInfoSucursalOfResponsableInfoPersonalCollectionNewResponsableInfoPersonal.equals(responsableInfoSucursal)) {
                        oldResponsableInfoSucursalOfResponsableInfoPersonalCollectionNewResponsableInfoPersonal.getResponsableInfoPersonalCollection().remove(responsableInfoPersonalCollectionNewResponsableInfoPersonal);
                        oldResponsableInfoSucursalOfResponsableInfoPersonalCollectionNewResponsableInfoPersonal = em.merge(oldResponsableInfoSucursalOfResponsableInfoPersonalCollectionNewResponsableInfoPersonal);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = responsableInfoSucursal.getNoSeguroSocial();
                if (findResponsableInfoSucursal(id) == null) {
                    throw new NonexistentEntityException("The responsableInfoSucursal with id " + id + " no longer exists.");
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
            ResponsableInfoSucursal responsableInfoSucursal;
            try {
                responsableInfoSucursal = em.getReference(ResponsableInfoSucursal.class, id);
                responsableInfoSucursal.getNoSeguroSocial();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The responsableInfoSucursal with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<ResponsableInfoPersonal> responsableInfoPersonalCollectionOrphanCheck = responsableInfoSucursal.getResponsableInfoPersonalCollection();
            for (ResponsableInfoPersonal responsableInfoPersonalCollectionOrphanCheckResponsableInfoPersonal : responsableInfoPersonalCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This ResponsableInfoSucursal (" + responsableInfoSucursal + ") cannot be destroyed since the ResponsableInfoPersonal " + responsableInfoPersonalCollectionOrphanCheckResponsableInfoPersonal + " in its responsableInfoPersonalCollection field has a non-nullable responsableInfoSucursal field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(responsableInfoSucursal);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ResponsableInfoSucursal> findResponsableInfoSucursalEntities() {
        return findResponsableInfoSucursalEntities(true, -1, -1);
    }

    public List<ResponsableInfoSucursal> findResponsableInfoSucursalEntities(int maxResults, int firstResult) {
        return findResponsableInfoSucursalEntities(false, maxResults, firstResult);
    }

    private List<ResponsableInfoSucursal> findResponsableInfoSucursalEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ResponsableInfoSucursal.class));
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

    public ResponsableInfoSucursal findResponsableInfoSucursal(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ResponsableInfoSucursal.class, id);
        } finally {
            em.close();
        }
    }

    public int getResponsableInfoSucursalCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ResponsableInfoSucursal> rt = cq.from(ResponsableInfoSucursal.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
