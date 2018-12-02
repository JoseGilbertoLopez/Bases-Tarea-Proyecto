/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

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
import modelo.ResponsableInfoContacto;
import modelo.ResponsableInfoContactoPK;

/**
 *
 * @author Jose G
 */
public class ResponsableInfoContactoJpaController implements Serializable {

    public ResponsableInfoContactoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ResponsableInfoContacto responsableInfoContacto) throws PreexistingEntityException, Exception {
        if (responsableInfoContacto.getResponsableInfoContactoPK() == null) {
            responsableInfoContacto.setResponsableInfoContactoPK(new ResponsableInfoContactoPK());
        }
        if (responsableInfoContacto.getResponsableInfoPersonalCollection() == null) {
            responsableInfoContacto.setResponsableInfoPersonalCollection(new ArrayList<ResponsableInfoPersonal>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<ResponsableInfoPersonal> attachedResponsableInfoPersonalCollection = new ArrayList<ResponsableInfoPersonal>();
            for (ResponsableInfoPersonal responsableInfoPersonalCollectionResponsableInfoPersonalToAttach : responsableInfoContacto.getResponsableInfoPersonalCollection()) {
                responsableInfoPersonalCollectionResponsableInfoPersonalToAttach = em.getReference(responsableInfoPersonalCollectionResponsableInfoPersonalToAttach.getClass(), responsableInfoPersonalCollectionResponsableInfoPersonalToAttach.getResponsableInfoPersonalPK());
                attachedResponsableInfoPersonalCollection.add(responsableInfoPersonalCollectionResponsableInfoPersonalToAttach);
            }
            responsableInfoContacto.setResponsableInfoPersonalCollection(attachedResponsableInfoPersonalCollection);
            em.persist(responsableInfoContacto);
            for (ResponsableInfoPersonal responsableInfoPersonalCollectionResponsableInfoPersonal : responsableInfoContacto.getResponsableInfoPersonalCollection()) {
                ResponsableInfoContacto oldResponsableInfoContactoOfResponsableInfoPersonalCollectionResponsableInfoPersonal = responsableInfoPersonalCollectionResponsableInfoPersonal.getResponsableInfoContacto();
                responsableInfoPersonalCollectionResponsableInfoPersonal.setResponsableInfoContacto(responsableInfoContacto);
                responsableInfoPersonalCollectionResponsableInfoPersonal = em.merge(responsableInfoPersonalCollectionResponsableInfoPersonal);
                if (oldResponsableInfoContactoOfResponsableInfoPersonalCollectionResponsableInfoPersonal != null) {
                    oldResponsableInfoContactoOfResponsableInfoPersonalCollectionResponsableInfoPersonal.getResponsableInfoPersonalCollection().remove(responsableInfoPersonalCollectionResponsableInfoPersonal);
                    oldResponsableInfoContactoOfResponsableInfoPersonalCollectionResponsableInfoPersonal = em.merge(oldResponsableInfoContactoOfResponsableInfoPersonalCollectionResponsableInfoPersonal);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findResponsableInfoContacto(responsableInfoContacto.getResponsableInfoContactoPK()) != null) {
                throw new PreexistingEntityException("ResponsableInfoContacto " + responsableInfoContacto + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ResponsableInfoContacto responsableInfoContacto) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ResponsableInfoContacto persistentResponsableInfoContacto = em.find(ResponsableInfoContacto.class, responsableInfoContacto.getResponsableInfoContactoPK());
            Collection<ResponsableInfoPersonal> responsableInfoPersonalCollectionOld = persistentResponsableInfoContacto.getResponsableInfoPersonalCollection();
            Collection<ResponsableInfoPersonal> responsableInfoPersonalCollectionNew = responsableInfoContacto.getResponsableInfoPersonalCollection();
            List<String> illegalOrphanMessages = null;
            for (ResponsableInfoPersonal responsableInfoPersonalCollectionOldResponsableInfoPersonal : responsableInfoPersonalCollectionOld) {
                if (!responsableInfoPersonalCollectionNew.contains(responsableInfoPersonalCollectionOldResponsableInfoPersonal)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ResponsableInfoPersonal " + responsableInfoPersonalCollectionOldResponsableInfoPersonal + " since its responsableInfoContacto field is not nullable.");
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
            responsableInfoContacto.setResponsableInfoPersonalCollection(responsableInfoPersonalCollectionNew);
            responsableInfoContacto = em.merge(responsableInfoContacto);
            for (ResponsableInfoPersonal responsableInfoPersonalCollectionNewResponsableInfoPersonal : responsableInfoPersonalCollectionNew) {
                if (!responsableInfoPersonalCollectionOld.contains(responsableInfoPersonalCollectionNewResponsableInfoPersonal)) {
                    ResponsableInfoContacto oldResponsableInfoContactoOfResponsableInfoPersonalCollectionNewResponsableInfoPersonal = responsableInfoPersonalCollectionNewResponsableInfoPersonal.getResponsableInfoContacto();
                    responsableInfoPersonalCollectionNewResponsableInfoPersonal.setResponsableInfoContacto(responsableInfoContacto);
                    responsableInfoPersonalCollectionNewResponsableInfoPersonal = em.merge(responsableInfoPersonalCollectionNewResponsableInfoPersonal);
                    if (oldResponsableInfoContactoOfResponsableInfoPersonalCollectionNewResponsableInfoPersonal != null && !oldResponsableInfoContactoOfResponsableInfoPersonalCollectionNewResponsableInfoPersonal.equals(responsableInfoContacto)) {
                        oldResponsableInfoContactoOfResponsableInfoPersonalCollectionNewResponsableInfoPersonal.getResponsableInfoPersonalCollection().remove(responsableInfoPersonalCollectionNewResponsableInfoPersonal);
                        oldResponsableInfoContactoOfResponsableInfoPersonalCollectionNewResponsableInfoPersonal = em.merge(oldResponsableInfoContactoOfResponsableInfoPersonalCollectionNewResponsableInfoPersonal);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                ResponsableInfoContactoPK id = responsableInfoContacto.getResponsableInfoContactoPK();
                if (findResponsableInfoContacto(id) == null) {
                    throw new NonexistentEntityException("The responsableInfoContacto with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(ResponsableInfoContactoPK id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ResponsableInfoContacto responsableInfoContacto;
            try {
                responsableInfoContacto = em.getReference(ResponsableInfoContacto.class, id);
                responsableInfoContacto.getResponsableInfoContactoPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The responsableInfoContacto with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<ResponsableInfoPersonal> responsableInfoPersonalCollectionOrphanCheck = responsableInfoContacto.getResponsableInfoPersonalCollection();
            for (ResponsableInfoPersonal responsableInfoPersonalCollectionOrphanCheckResponsableInfoPersonal : responsableInfoPersonalCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This ResponsableInfoContacto (" + responsableInfoContacto + ") cannot be destroyed since the ResponsableInfoPersonal " + responsableInfoPersonalCollectionOrphanCheckResponsableInfoPersonal + " in its responsableInfoPersonalCollection field has a non-nullable responsableInfoContacto field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(responsableInfoContacto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ResponsableInfoContacto> findResponsableInfoContactoEntities() {
        return findResponsableInfoContactoEntities(true, -1, -1);
    }

    public List<ResponsableInfoContacto> findResponsableInfoContactoEntities(int maxResults, int firstResult) {
        return findResponsableInfoContactoEntities(false, maxResults, firstResult);
    }

    private List<ResponsableInfoContacto> findResponsableInfoContactoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ResponsableInfoContacto.class));
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

    public ResponsableInfoContacto findResponsableInfoContacto(ResponsableInfoContactoPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ResponsableInfoContacto.class, id);
        } finally {
            em.close();
        }
    }

    public int getResponsableInfoContactoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ResponsableInfoContacto> rt = cq.from(ResponsableInfoContacto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
