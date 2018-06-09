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
import modelo.MostradorInfoPersonal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.MostradorInfoContacto;
import modelo.MostradorInfoContactoPK;

/**
 *
 * @author Jose G
 */
public class MostradorInfoContactoJpaController implements Serializable {

    public MostradorInfoContactoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(MostradorInfoContacto mostradorInfoContacto) throws PreexistingEntityException, Exception {
        if (mostradorInfoContacto.getMostradorInfoContactoPK() == null) {
            mostradorInfoContacto.setMostradorInfoContactoPK(new MostradorInfoContactoPK());
        }
        if (mostradorInfoContacto.getMostradorInfoPersonalCollection() == null) {
            mostradorInfoContacto.setMostradorInfoPersonalCollection(new ArrayList<MostradorInfoPersonal>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<MostradorInfoPersonal> attachedMostradorInfoPersonalCollection = new ArrayList<MostradorInfoPersonal>();
            for (MostradorInfoPersonal mostradorInfoPersonalCollectionMostradorInfoPersonalToAttach : mostradorInfoContacto.getMostradorInfoPersonalCollection()) {
                mostradorInfoPersonalCollectionMostradorInfoPersonalToAttach = em.getReference(mostradorInfoPersonalCollectionMostradorInfoPersonalToAttach.getClass(), mostradorInfoPersonalCollectionMostradorInfoPersonalToAttach.getMostradorInfoPersonalPK());
                attachedMostradorInfoPersonalCollection.add(mostradorInfoPersonalCollectionMostradorInfoPersonalToAttach);
            }
            mostradorInfoContacto.setMostradorInfoPersonalCollection(attachedMostradorInfoPersonalCollection);
            em.persist(mostradorInfoContacto);
            for (MostradorInfoPersonal mostradorInfoPersonalCollectionMostradorInfoPersonal : mostradorInfoContacto.getMostradorInfoPersonalCollection()) {
                MostradorInfoContacto oldMostradorInfoContactoOfMostradorInfoPersonalCollectionMostradorInfoPersonal = mostradorInfoPersonalCollectionMostradorInfoPersonal.getMostradorInfoContacto();
                mostradorInfoPersonalCollectionMostradorInfoPersonal.setMostradorInfoContacto(mostradorInfoContacto);
                mostradorInfoPersonalCollectionMostradorInfoPersonal = em.merge(mostradorInfoPersonalCollectionMostradorInfoPersonal);
                if (oldMostradorInfoContactoOfMostradorInfoPersonalCollectionMostradorInfoPersonal != null) {
                    oldMostradorInfoContactoOfMostradorInfoPersonalCollectionMostradorInfoPersonal.getMostradorInfoPersonalCollection().remove(mostradorInfoPersonalCollectionMostradorInfoPersonal);
                    oldMostradorInfoContactoOfMostradorInfoPersonalCollectionMostradorInfoPersonal = em.merge(oldMostradorInfoContactoOfMostradorInfoPersonalCollectionMostradorInfoPersonal);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findMostradorInfoContacto(mostradorInfoContacto.getMostradorInfoContactoPK()) != null) {
                throw new PreexistingEntityException("MostradorInfoContacto " + mostradorInfoContacto + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(MostradorInfoContacto mostradorInfoContacto) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            MostradorInfoContacto persistentMostradorInfoContacto = em.find(MostradorInfoContacto.class, mostradorInfoContacto.getMostradorInfoContactoPK());
            Collection<MostradorInfoPersonal> mostradorInfoPersonalCollectionOld = persistentMostradorInfoContacto.getMostradorInfoPersonalCollection();
            Collection<MostradorInfoPersonal> mostradorInfoPersonalCollectionNew = mostradorInfoContacto.getMostradorInfoPersonalCollection();
            List<String> illegalOrphanMessages = null;
            for (MostradorInfoPersonal mostradorInfoPersonalCollectionOldMostradorInfoPersonal : mostradorInfoPersonalCollectionOld) {
                if (!mostradorInfoPersonalCollectionNew.contains(mostradorInfoPersonalCollectionOldMostradorInfoPersonal)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain MostradorInfoPersonal " + mostradorInfoPersonalCollectionOldMostradorInfoPersonal + " since its mostradorInfoContacto field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<MostradorInfoPersonal> attachedMostradorInfoPersonalCollectionNew = new ArrayList<MostradorInfoPersonal>();
            for (MostradorInfoPersonal mostradorInfoPersonalCollectionNewMostradorInfoPersonalToAttach : mostradorInfoPersonalCollectionNew) {
                mostradorInfoPersonalCollectionNewMostradorInfoPersonalToAttach = em.getReference(mostradorInfoPersonalCollectionNewMostradorInfoPersonalToAttach.getClass(), mostradorInfoPersonalCollectionNewMostradorInfoPersonalToAttach.getMostradorInfoPersonalPK());
                attachedMostradorInfoPersonalCollectionNew.add(mostradorInfoPersonalCollectionNewMostradorInfoPersonalToAttach);
            }
            mostradorInfoPersonalCollectionNew = attachedMostradorInfoPersonalCollectionNew;
            mostradorInfoContacto.setMostradorInfoPersonalCollection(mostradorInfoPersonalCollectionNew);
            mostradorInfoContacto = em.merge(mostradorInfoContacto);
            for (MostradorInfoPersonal mostradorInfoPersonalCollectionNewMostradorInfoPersonal : mostradorInfoPersonalCollectionNew) {
                if (!mostradorInfoPersonalCollectionOld.contains(mostradorInfoPersonalCollectionNewMostradorInfoPersonal)) {
                    MostradorInfoContacto oldMostradorInfoContactoOfMostradorInfoPersonalCollectionNewMostradorInfoPersonal = mostradorInfoPersonalCollectionNewMostradorInfoPersonal.getMostradorInfoContacto();
                    mostradorInfoPersonalCollectionNewMostradorInfoPersonal.setMostradorInfoContacto(mostradorInfoContacto);
                    mostradorInfoPersonalCollectionNewMostradorInfoPersonal = em.merge(mostradorInfoPersonalCollectionNewMostradorInfoPersonal);
                    if (oldMostradorInfoContactoOfMostradorInfoPersonalCollectionNewMostradorInfoPersonal != null && !oldMostradorInfoContactoOfMostradorInfoPersonalCollectionNewMostradorInfoPersonal.equals(mostradorInfoContacto)) {
                        oldMostradorInfoContactoOfMostradorInfoPersonalCollectionNewMostradorInfoPersonal.getMostradorInfoPersonalCollection().remove(mostradorInfoPersonalCollectionNewMostradorInfoPersonal);
                        oldMostradorInfoContactoOfMostradorInfoPersonalCollectionNewMostradorInfoPersonal = em.merge(oldMostradorInfoContactoOfMostradorInfoPersonalCollectionNewMostradorInfoPersonal);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                MostradorInfoContactoPK id = mostradorInfoContacto.getMostradorInfoContactoPK();
                if (findMostradorInfoContacto(id) == null) {
                    throw new NonexistentEntityException("The mostradorInfoContacto with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(MostradorInfoContactoPK id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            MostradorInfoContacto mostradorInfoContacto;
            try {
                mostradorInfoContacto = em.getReference(MostradorInfoContacto.class, id);
                mostradorInfoContacto.getMostradorInfoContactoPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The mostradorInfoContacto with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<MostradorInfoPersonal> mostradorInfoPersonalCollectionOrphanCheck = mostradorInfoContacto.getMostradorInfoPersonalCollection();
            for (MostradorInfoPersonal mostradorInfoPersonalCollectionOrphanCheckMostradorInfoPersonal : mostradorInfoPersonalCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This MostradorInfoContacto (" + mostradorInfoContacto + ") cannot be destroyed since the MostradorInfoPersonal " + mostradorInfoPersonalCollectionOrphanCheckMostradorInfoPersonal + " in its mostradorInfoPersonalCollection field has a non-nullable mostradorInfoContacto field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(mostradorInfoContacto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<MostradorInfoContacto> findMostradorInfoContactoEntities() {
        return findMostradorInfoContactoEntities(true, -1, -1);
    }

    public List<MostradorInfoContacto> findMostradorInfoContactoEntities(int maxResults, int firstResult) {
        return findMostradorInfoContactoEntities(false, maxResults, firstResult);
    }

    private List<MostradorInfoContacto> findMostradorInfoContactoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(MostradorInfoContacto.class));
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

    public MostradorInfoContacto findMostradorInfoContacto(MostradorInfoContactoPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(MostradorInfoContacto.class, id);
        } finally {
            em.close();
        }
    }

    public int getMostradorInfoContactoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<MostradorInfoContacto> rt = cq.from(MostradorInfoContacto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
