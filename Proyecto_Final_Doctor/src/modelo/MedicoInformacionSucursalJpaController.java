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
import modelo.MedicoInformacionSucursal;
import modelo.MedicoInformacionSucursalPK;

/**
 *
 * @author Jose G
 */
public class MedicoInformacionSucursalJpaController implements Serializable {

    public MedicoInformacionSucursalJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(MedicoInformacionSucursal medicoInformacionSucursal) throws PreexistingEntityException, Exception {
        if (medicoInformacionSucursal.getMedicoInformacionSucursalPK() == null) {
            medicoInformacionSucursal.setMedicoInformacionSucursalPK(new MedicoInformacionSucursalPK());
        }
        if (medicoInformacionSucursal.getMedicoInformacionPersonalCollection() == null) {
            medicoInformacionSucursal.setMedicoInformacionPersonalCollection(new ArrayList<MedicoInformacionPersonal>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<MedicoInformacionPersonal> attachedMedicoInformacionPersonalCollection = new ArrayList<MedicoInformacionPersonal>();
            for (MedicoInformacionPersonal medicoInformacionPersonalCollectionMedicoInformacionPersonalToAttach : medicoInformacionSucursal.getMedicoInformacionPersonalCollection()) {
                medicoInformacionPersonalCollectionMedicoInformacionPersonalToAttach = em.getReference(medicoInformacionPersonalCollectionMedicoInformacionPersonalToAttach.getClass(), medicoInformacionPersonalCollectionMedicoInformacionPersonalToAttach.getMedicoInformacionPersonalPK());
                attachedMedicoInformacionPersonalCollection.add(medicoInformacionPersonalCollectionMedicoInformacionPersonalToAttach);
            }
            medicoInformacionSucursal.setMedicoInformacionPersonalCollection(attachedMedicoInformacionPersonalCollection);
            em.persist(medicoInformacionSucursal);
            for (MedicoInformacionPersonal medicoInformacionPersonalCollectionMedicoInformacionPersonal : medicoInformacionSucursal.getMedicoInformacionPersonalCollection()) {
                MedicoInformacionSucursal oldMedicoInformacionSucursalOfMedicoInformacionPersonalCollectionMedicoInformacionPersonal = medicoInformacionPersonalCollectionMedicoInformacionPersonal.getMedicoInformacionSucursal();
                medicoInformacionPersonalCollectionMedicoInformacionPersonal.setMedicoInformacionSucursal(medicoInformacionSucursal);
                medicoInformacionPersonalCollectionMedicoInformacionPersonal = em.merge(medicoInformacionPersonalCollectionMedicoInformacionPersonal);
                if (oldMedicoInformacionSucursalOfMedicoInformacionPersonalCollectionMedicoInformacionPersonal != null) {
                    oldMedicoInformacionSucursalOfMedicoInformacionPersonalCollectionMedicoInformacionPersonal.getMedicoInformacionPersonalCollection().remove(medicoInformacionPersonalCollectionMedicoInformacionPersonal);
                    oldMedicoInformacionSucursalOfMedicoInformacionPersonalCollectionMedicoInformacionPersonal = em.merge(oldMedicoInformacionSucursalOfMedicoInformacionPersonalCollectionMedicoInformacionPersonal);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findMedicoInformacionSucursal(medicoInformacionSucursal.getMedicoInformacionSucursalPK()) != null) {
                throw new PreexistingEntityException("MedicoInformacionSucursal " + medicoInformacionSucursal + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(MedicoInformacionSucursal medicoInformacionSucursal) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            MedicoInformacionSucursal persistentMedicoInformacionSucursal = em.find(MedicoInformacionSucursal.class, medicoInformacionSucursal.getMedicoInformacionSucursalPK());
            Collection<MedicoInformacionPersonal> medicoInformacionPersonalCollectionOld = persistentMedicoInformacionSucursal.getMedicoInformacionPersonalCollection();
            Collection<MedicoInformacionPersonal> medicoInformacionPersonalCollectionNew = medicoInformacionSucursal.getMedicoInformacionPersonalCollection();
            List<String> illegalOrphanMessages = null;
            for (MedicoInformacionPersonal medicoInformacionPersonalCollectionOldMedicoInformacionPersonal : medicoInformacionPersonalCollectionOld) {
                if (!medicoInformacionPersonalCollectionNew.contains(medicoInformacionPersonalCollectionOldMedicoInformacionPersonal)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain MedicoInformacionPersonal " + medicoInformacionPersonalCollectionOldMedicoInformacionPersonal + " since its medicoInformacionSucursal field is not nullable.");
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
            medicoInformacionSucursal.setMedicoInformacionPersonalCollection(medicoInformacionPersonalCollectionNew);
            medicoInformacionSucursal = em.merge(medicoInformacionSucursal);
            for (MedicoInformacionPersonal medicoInformacionPersonalCollectionNewMedicoInformacionPersonal : medicoInformacionPersonalCollectionNew) {
                if (!medicoInformacionPersonalCollectionOld.contains(medicoInformacionPersonalCollectionNewMedicoInformacionPersonal)) {
                    MedicoInformacionSucursal oldMedicoInformacionSucursalOfMedicoInformacionPersonalCollectionNewMedicoInformacionPersonal = medicoInformacionPersonalCollectionNewMedicoInformacionPersonal.getMedicoInformacionSucursal();
                    medicoInformacionPersonalCollectionNewMedicoInformacionPersonal.setMedicoInformacionSucursal(medicoInformacionSucursal);
                    medicoInformacionPersonalCollectionNewMedicoInformacionPersonal = em.merge(medicoInformacionPersonalCollectionNewMedicoInformacionPersonal);
                    if (oldMedicoInformacionSucursalOfMedicoInformacionPersonalCollectionNewMedicoInformacionPersonal != null && !oldMedicoInformacionSucursalOfMedicoInformacionPersonalCollectionNewMedicoInformacionPersonal.equals(medicoInformacionSucursal)) {
                        oldMedicoInformacionSucursalOfMedicoInformacionPersonalCollectionNewMedicoInformacionPersonal.getMedicoInformacionPersonalCollection().remove(medicoInformacionPersonalCollectionNewMedicoInformacionPersonal);
                        oldMedicoInformacionSucursalOfMedicoInformacionPersonalCollectionNewMedicoInformacionPersonal = em.merge(oldMedicoInformacionSucursalOfMedicoInformacionPersonalCollectionNewMedicoInformacionPersonal);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                MedicoInformacionSucursalPK id = medicoInformacionSucursal.getMedicoInformacionSucursalPK();
                if (findMedicoInformacionSucursal(id) == null) {
                    throw new NonexistentEntityException("The medicoInformacionSucursal with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(MedicoInformacionSucursalPK id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            MedicoInformacionSucursal medicoInformacionSucursal;
            try {
                medicoInformacionSucursal = em.getReference(MedicoInformacionSucursal.class, id);
                medicoInformacionSucursal.getMedicoInformacionSucursalPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The medicoInformacionSucursal with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<MedicoInformacionPersonal> medicoInformacionPersonalCollectionOrphanCheck = medicoInformacionSucursal.getMedicoInformacionPersonalCollection();
            for (MedicoInformacionPersonal medicoInformacionPersonalCollectionOrphanCheckMedicoInformacionPersonal : medicoInformacionPersonalCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This MedicoInformacionSucursal (" + medicoInformacionSucursal + ") cannot be destroyed since the MedicoInformacionPersonal " + medicoInformacionPersonalCollectionOrphanCheckMedicoInformacionPersonal + " in its medicoInformacionPersonalCollection field has a non-nullable medicoInformacionSucursal field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(medicoInformacionSucursal);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<MedicoInformacionSucursal> findMedicoInformacionSucursalEntities() {
        return findMedicoInformacionSucursalEntities(true, -1, -1);
    }

    public List<MedicoInformacionSucursal> findMedicoInformacionSucursalEntities(int maxResults, int firstResult) {
        return findMedicoInformacionSucursalEntities(false, maxResults, firstResult);
    }

    private List<MedicoInformacionSucursal> findMedicoInformacionSucursalEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(MedicoInformacionSucursal.class));
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

    public MedicoInformacionSucursal findMedicoInformacionSucursal(MedicoInformacionSucursalPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(MedicoInformacionSucursal.class, id);
        } finally {
            em.close();
        }
    }

    public int getMedicoInformacionSucursalCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<MedicoInformacionSucursal> rt = cq.from(MedicoInformacionSucursal.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
