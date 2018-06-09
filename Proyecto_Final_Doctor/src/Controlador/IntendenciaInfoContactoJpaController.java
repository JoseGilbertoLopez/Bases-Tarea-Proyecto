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
import modelo.IntendenciaInfoContacto;
import modelo.IntendenciaInfoContactoPK;

/**
 *
 * @author Jose G
 */
public class IntendenciaInfoContactoJpaController implements Serializable {

    public IntendenciaInfoContactoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(IntendenciaInfoContacto intendenciaInfoContacto) throws PreexistingEntityException, Exception {
        if (intendenciaInfoContacto.getIntendenciaInfoContactoPK() == null) {
            intendenciaInfoContacto.setIntendenciaInfoContactoPK(new IntendenciaInfoContactoPK());
        }
        if (intendenciaInfoContacto.getIntendenciaInfoPersonalCollection() == null) {
            intendenciaInfoContacto.setIntendenciaInfoPersonalCollection(new ArrayList<IntendenciaInfoPersonal>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<IntendenciaInfoPersonal> attachedIntendenciaInfoPersonalCollection = new ArrayList<IntendenciaInfoPersonal>();
            for (IntendenciaInfoPersonal intendenciaInfoPersonalCollectionIntendenciaInfoPersonalToAttach : intendenciaInfoContacto.getIntendenciaInfoPersonalCollection()) {
                intendenciaInfoPersonalCollectionIntendenciaInfoPersonalToAttach = em.getReference(intendenciaInfoPersonalCollectionIntendenciaInfoPersonalToAttach.getClass(), intendenciaInfoPersonalCollectionIntendenciaInfoPersonalToAttach.getIntendenciaInfoPersonalPK());
                attachedIntendenciaInfoPersonalCollection.add(intendenciaInfoPersonalCollectionIntendenciaInfoPersonalToAttach);
            }
            intendenciaInfoContacto.setIntendenciaInfoPersonalCollection(attachedIntendenciaInfoPersonalCollection);
            em.persist(intendenciaInfoContacto);
            for (IntendenciaInfoPersonal intendenciaInfoPersonalCollectionIntendenciaInfoPersonal : intendenciaInfoContacto.getIntendenciaInfoPersonalCollection()) {
                IntendenciaInfoContacto oldIntendenciaInfoContactoOfIntendenciaInfoPersonalCollectionIntendenciaInfoPersonal = intendenciaInfoPersonalCollectionIntendenciaInfoPersonal.getIntendenciaInfoContacto();
                intendenciaInfoPersonalCollectionIntendenciaInfoPersonal.setIntendenciaInfoContacto(intendenciaInfoContacto);
                intendenciaInfoPersonalCollectionIntendenciaInfoPersonal = em.merge(intendenciaInfoPersonalCollectionIntendenciaInfoPersonal);
                if (oldIntendenciaInfoContactoOfIntendenciaInfoPersonalCollectionIntendenciaInfoPersonal != null) {
                    oldIntendenciaInfoContactoOfIntendenciaInfoPersonalCollectionIntendenciaInfoPersonal.getIntendenciaInfoPersonalCollection().remove(intendenciaInfoPersonalCollectionIntendenciaInfoPersonal);
                    oldIntendenciaInfoContactoOfIntendenciaInfoPersonalCollectionIntendenciaInfoPersonal = em.merge(oldIntendenciaInfoContactoOfIntendenciaInfoPersonalCollectionIntendenciaInfoPersonal);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findIntendenciaInfoContacto(intendenciaInfoContacto.getIntendenciaInfoContactoPK()) != null) {
                throw new PreexistingEntityException("IntendenciaInfoContacto " + intendenciaInfoContacto + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(IntendenciaInfoContacto intendenciaInfoContacto) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            IntendenciaInfoContacto persistentIntendenciaInfoContacto = em.find(IntendenciaInfoContacto.class, intendenciaInfoContacto.getIntendenciaInfoContactoPK());
            Collection<IntendenciaInfoPersonal> intendenciaInfoPersonalCollectionOld = persistentIntendenciaInfoContacto.getIntendenciaInfoPersonalCollection();
            Collection<IntendenciaInfoPersonal> intendenciaInfoPersonalCollectionNew = intendenciaInfoContacto.getIntendenciaInfoPersonalCollection();
            List<String> illegalOrphanMessages = null;
            for (IntendenciaInfoPersonal intendenciaInfoPersonalCollectionOldIntendenciaInfoPersonal : intendenciaInfoPersonalCollectionOld) {
                if (!intendenciaInfoPersonalCollectionNew.contains(intendenciaInfoPersonalCollectionOldIntendenciaInfoPersonal)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain IntendenciaInfoPersonal " + intendenciaInfoPersonalCollectionOldIntendenciaInfoPersonal + " since its intendenciaInfoContacto field is not nullable.");
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
            intendenciaInfoContacto.setIntendenciaInfoPersonalCollection(intendenciaInfoPersonalCollectionNew);
            intendenciaInfoContacto = em.merge(intendenciaInfoContacto);
            for (IntendenciaInfoPersonal intendenciaInfoPersonalCollectionNewIntendenciaInfoPersonal : intendenciaInfoPersonalCollectionNew) {
                if (!intendenciaInfoPersonalCollectionOld.contains(intendenciaInfoPersonalCollectionNewIntendenciaInfoPersonal)) {
                    IntendenciaInfoContacto oldIntendenciaInfoContactoOfIntendenciaInfoPersonalCollectionNewIntendenciaInfoPersonal = intendenciaInfoPersonalCollectionNewIntendenciaInfoPersonal.getIntendenciaInfoContacto();
                    intendenciaInfoPersonalCollectionNewIntendenciaInfoPersonal.setIntendenciaInfoContacto(intendenciaInfoContacto);
                    intendenciaInfoPersonalCollectionNewIntendenciaInfoPersonal = em.merge(intendenciaInfoPersonalCollectionNewIntendenciaInfoPersonal);
                    if (oldIntendenciaInfoContactoOfIntendenciaInfoPersonalCollectionNewIntendenciaInfoPersonal != null && !oldIntendenciaInfoContactoOfIntendenciaInfoPersonalCollectionNewIntendenciaInfoPersonal.equals(intendenciaInfoContacto)) {
                        oldIntendenciaInfoContactoOfIntendenciaInfoPersonalCollectionNewIntendenciaInfoPersonal.getIntendenciaInfoPersonalCollection().remove(intendenciaInfoPersonalCollectionNewIntendenciaInfoPersonal);
                        oldIntendenciaInfoContactoOfIntendenciaInfoPersonalCollectionNewIntendenciaInfoPersonal = em.merge(oldIntendenciaInfoContactoOfIntendenciaInfoPersonalCollectionNewIntendenciaInfoPersonal);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                IntendenciaInfoContactoPK id = intendenciaInfoContacto.getIntendenciaInfoContactoPK();
                if (findIntendenciaInfoContacto(id) == null) {
                    throw new NonexistentEntityException("The intendenciaInfoContacto with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(IntendenciaInfoContactoPK id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            IntendenciaInfoContacto intendenciaInfoContacto;
            try {
                intendenciaInfoContacto = em.getReference(IntendenciaInfoContacto.class, id);
                intendenciaInfoContacto.getIntendenciaInfoContactoPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The intendenciaInfoContacto with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<IntendenciaInfoPersonal> intendenciaInfoPersonalCollectionOrphanCheck = intendenciaInfoContacto.getIntendenciaInfoPersonalCollection();
            for (IntendenciaInfoPersonal intendenciaInfoPersonalCollectionOrphanCheckIntendenciaInfoPersonal : intendenciaInfoPersonalCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This IntendenciaInfoContacto (" + intendenciaInfoContacto + ") cannot be destroyed since the IntendenciaInfoPersonal " + intendenciaInfoPersonalCollectionOrphanCheckIntendenciaInfoPersonal + " in its intendenciaInfoPersonalCollection field has a non-nullable intendenciaInfoContacto field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(intendenciaInfoContacto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<IntendenciaInfoContacto> findIntendenciaInfoContactoEntities() {
        return findIntendenciaInfoContactoEntities(true, -1, -1);
    }

    public List<IntendenciaInfoContacto> findIntendenciaInfoContactoEntities(int maxResults, int firstResult) {
        return findIntendenciaInfoContactoEntities(false, maxResults, firstResult);
    }

    private List<IntendenciaInfoContacto> findIntendenciaInfoContactoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(IntendenciaInfoContacto.class));
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

    public IntendenciaInfoContacto findIntendenciaInfoContacto(IntendenciaInfoContactoPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(IntendenciaInfoContacto.class, id);
        } finally {
            em.close();
        }
    }

    public int getIntendenciaInfoContactoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<IntendenciaInfoContacto> rt = cq.from(IntendenciaInfoContacto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
