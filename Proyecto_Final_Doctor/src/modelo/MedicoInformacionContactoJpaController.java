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
import modelo.MedicoInformacionPersonal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.MedicoInformacionContacto;
import modelo.MedicoInformacionContactoPK;

/**
 *
 * @author Jose G
 */
public class MedicoInformacionContactoJpaController implements Serializable {

    public MedicoInformacionContactoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(MedicoInformacionContacto medicoInformacionContacto) throws PreexistingEntityException, Exception {
        if (medicoInformacionContacto.getMedicoInformacionContactoPK() == null) {
            medicoInformacionContacto.setMedicoInformacionContactoPK(new MedicoInformacionContactoPK());
        }
        if (medicoInformacionContacto.getMedicoInformacionPersonalCollection() == null) {
            medicoInformacionContacto.setMedicoInformacionPersonalCollection(new ArrayList<MedicoInformacionPersonal>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<MedicoInformacionPersonal> attachedMedicoInformacionPersonalCollection = new ArrayList<MedicoInformacionPersonal>();
            for (MedicoInformacionPersonal medicoInformacionPersonalCollectionMedicoInformacionPersonalToAttach : medicoInformacionContacto.getMedicoInformacionPersonalCollection()) {
                medicoInformacionPersonalCollectionMedicoInformacionPersonalToAttach = em.getReference(medicoInformacionPersonalCollectionMedicoInformacionPersonalToAttach.getClass(), medicoInformacionPersonalCollectionMedicoInformacionPersonalToAttach.getMedicoInformacionPersonalPK());
                attachedMedicoInformacionPersonalCollection.add(medicoInformacionPersonalCollectionMedicoInformacionPersonalToAttach);
            }
            medicoInformacionContacto.setMedicoInformacionPersonalCollection(attachedMedicoInformacionPersonalCollection);
            em.persist(medicoInformacionContacto);
            for (MedicoInformacionPersonal medicoInformacionPersonalCollectionMedicoInformacionPersonal : medicoInformacionContacto.getMedicoInformacionPersonalCollection()) {
                MedicoInformacionContacto oldMedicoInformacionContactoOfMedicoInformacionPersonalCollectionMedicoInformacionPersonal = medicoInformacionPersonalCollectionMedicoInformacionPersonal.getMedicoInformacionContacto();
                medicoInformacionPersonalCollectionMedicoInformacionPersonal.setMedicoInformacionContacto(medicoInformacionContacto);
                medicoInformacionPersonalCollectionMedicoInformacionPersonal = em.merge(medicoInformacionPersonalCollectionMedicoInformacionPersonal);
                if (oldMedicoInformacionContactoOfMedicoInformacionPersonalCollectionMedicoInformacionPersonal != null) {
                    oldMedicoInformacionContactoOfMedicoInformacionPersonalCollectionMedicoInformacionPersonal.getMedicoInformacionPersonalCollection().remove(medicoInformacionPersonalCollectionMedicoInformacionPersonal);
                    oldMedicoInformacionContactoOfMedicoInformacionPersonalCollectionMedicoInformacionPersonal = em.merge(oldMedicoInformacionContactoOfMedicoInformacionPersonalCollectionMedicoInformacionPersonal);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findMedicoInformacionContacto(medicoInformacionContacto.getMedicoInformacionContactoPK()) != null) {
                throw new PreexistingEntityException("MedicoInformacionContacto " + medicoInformacionContacto + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(MedicoInformacionContacto medicoInformacionContacto) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            MedicoInformacionContacto persistentMedicoInformacionContacto = em.find(MedicoInformacionContacto.class, medicoInformacionContacto.getMedicoInformacionContactoPK());
            Collection<MedicoInformacionPersonal> medicoInformacionPersonalCollectionOld = persistentMedicoInformacionContacto.getMedicoInformacionPersonalCollection();
            Collection<MedicoInformacionPersonal> medicoInformacionPersonalCollectionNew = medicoInformacionContacto.getMedicoInformacionPersonalCollection();
            List<String> illegalOrphanMessages = null;
            for (MedicoInformacionPersonal medicoInformacionPersonalCollectionOldMedicoInformacionPersonal : medicoInformacionPersonalCollectionOld) {
                if (!medicoInformacionPersonalCollectionNew.contains(medicoInformacionPersonalCollectionOldMedicoInformacionPersonal)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain MedicoInformacionPersonal " + medicoInformacionPersonalCollectionOldMedicoInformacionPersonal + " since its medicoInformacionContacto field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<MedicoInformacionPersonal> attachedMedicoInformacionPersonalCollectionNew = new ArrayList<MedicoInformacionPersonal>();
            for (MedicoInformacionPersonal medicoInformacionPersonalCollectionNewMedicoInformacionPersonalToAttach : medicoInformacionPersonalCollectionNew) {
                medicoInformacionPersonalCollectionNewMedicoInformacionPersonalToAttach = em.getReference(medicoInformacionPersonalCollectionNewMedicoInformacionPersonalToAttach.getClass(), medicoInformacionPersonalCollectionNewMedicoInformacionPersonalToAttach.getMedicoInformacionPersonalPK());
                attachedMedicoInformacionPersonalCollectionNew.add(medicoInformacionPersonalCollectionNewMedicoInformacionPersonalToAttach);
            }
            medicoInformacionPersonalCollectionNew = attachedMedicoInformacionPersonalCollectionNew;
            medicoInformacionContacto.setMedicoInformacionPersonalCollection(medicoInformacionPersonalCollectionNew);
            medicoInformacionContacto = em.merge(medicoInformacionContacto);
            for (MedicoInformacionPersonal medicoInformacionPersonalCollectionNewMedicoInformacionPersonal : medicoInformacionPersonalCollectionNew) {
                if (!medicoInformacionPersonalCollectionOld.contains(medicoInformacionPersonalCollectionNewMedicoInformacionPersonal)) {
                    MedicoInformacionContacto oldMedicoInformacionContactoOfMedicoInformacionPersonalCollectionNewMedicoInformacionPersonal = medicoInformacionPersonalCollectionNewMedicoInformacionPersonal.getMedicoInformacionContacto();
                    medicoInformacionPersonalCollectionNewMedicoInformacionPersonal.setMedicoInformacionContacto(medicoInformacionContacto);
                    medicoInformacionPersonalCollectionNewMedicoInformacionPersonal = em.merge(medicoInformacionPersonalCollectionNewMedicoInformacionPersonal);
                    if (oldMedicoInformacionContactoOfMedicoInformacionPersonalCollectionNewMedicoInformacionPersonal != null && !oldMedicoInformacionContactoOfMedicoInformacionPersonalCollectionNewMedicoInformacionPersonal.equals(medicoInformacionContacto)) {
                        oldMedicoInformacionContactoOfMedicoInformacionPersonalCollectionNewMedicoInformacionPersonal.getMedicoInformacionPersonalCollection().remove(medicoInformacionPersonalCollectionNewMedicoInformacionPersonal);
                        oldMedicoInformacionContactoOfMedicoInformacionPersonalCollectionNewMedicoInformacionPersonal = em.merge(oldMedicoInformacionContactoOfMedicoInformacionPersonalCollectionNewMedicoInformacionPersonal);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                MedicoInformacionContactoPK id = medicoInformacionContacto.getMedicoInformacionContactoPK();
                if (findMedicoInformacionContacto(id) == null) {
                    throw new NonexistentEntityException("The medicoInformacionContacto with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(MedicoInformacionContactoPK id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            MedicoInformacionContacto medicoInformacionContacto;
            try {
                medicoInformacionContacto = em.getReference(MedicoInformacionContacto.class, id);
                medicoInformacionContacto.getMedicoInformacionContactoPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The medicoInformacionContacto with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<MedicoInformacionPersonal> medicoInformacionPersonalCollectionOrphanCheck = medicoInformacionContacto.getMedicoInformacionPersonalCollection();
            for (MedicoInformacionPersonal medicoInformacionPersonalCollectionOrphanCheckMedicoInformacionPersonal : medicoInformacionPersonalCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This MedicoInformacionContacto (" + medicoInformacionContacto + ") cannot be destroyed since the MedicoInformacionPersonal " + medicoInformacionPersonalCollectionOrphanCheckMedicoInformacionPersonal + " in its medicoInformacionPersonalCollection field has a non-nullable medicoInformacionContacto field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(medicoInformacionContacto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<MedicoInformacionContacto> findMedicoInformacionContactoEntities() {
        return findMedicoInformacionContactoEntities(true, -1, -1);
    }

    public List<MedicoInformacionContacto> findMedicoInformacionContactoEntities(int maxResults, int firstResult) {
        return findMedicoInformacionContactoEntities(false, maxResults, firstResult);
    }

    private List<MedicoInformacionContacto> findMedicoInformacionContactoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(MedicoInformacionContacto.class));
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

    public MedicoInformacionContacto findMedicoInformacionContacto(MedicoInformacionContactoPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(MedicoInformacionContacto.class, id);
        } finally {
            em.close();
        }
    }

    public int getMedicoInformacionContactoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<MedicoInformacionContacto> rt = cq.from(MedicoInformacionContacto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
