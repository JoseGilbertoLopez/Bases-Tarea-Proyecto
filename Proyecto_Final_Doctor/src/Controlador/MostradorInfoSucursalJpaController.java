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
import modelo.MostradorInfoSucursal;

/**
 *
 * @author Jose G
 */
public class MostradorInfoSucursalJpaController implements Serializable {

    public MostradorInfoSucursalJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(MostradorInfoSucursal mostradorInfoSucursal) throws PreexistingEntityException, Exception {
        if (mostradorInfoSucursal.getMostradorInfoPersonalCollection() == null) {
            mostradorInfoSucursal.setMostradorInfoPersonalCollection(new ArrayList<MostradorInfoPersonal>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<MostradorInfoPersonal> attachedMostradorInfoPersonalCollection = new ArrayList<MostradorInfoPersonal>();
            for (MostradorInfoPersonal mostradorInfoPersonalCollectionMostradorInfoPersonalToAttach : mostradorInfoSucursal.getMostradorInfoPersonalCollection()) {
                mostradorInfoPersonalCollectionMostradorInfoPersonalToAttach = em.getReference(mostradorInfoPersonalCollectionMostradorInfoPersonalToAttach.getClass(), mostradorInfoPersonalCollectionMostradorInfoPersonalToAttach.getMostradorInfoPersonalPK());
                attachedMostradorInfoPersonalCollection.add(mostradorInfoPersonalCollectionMostradorInfoPersonalToAttach);
            }
            mostradorInfoSucursal.setMostradorInfoPersonalCollection(attachedMostradorInfoPersonalCollection);
            em.persist(mostradorInfoSucursal);
            for (MostradorInfoPersonal mostradorInfoPersonalCollectionMostradorInfoPersonal : mostradorInfoSucursal.getMostradorInfoPersonalCollection()) {
                MostradorInfoSucursal oldMostradorInfoSucursalOfMostradorInfoPersonalCollectionMostradorInfoPersonal = mostradorInfoPersonalCollectionMostradorInfoPersonal.getMostradorInfoSucursal();
                mostradorInfoPersonalCollectionMostradorInfoPersonal.setMostradorInfoSucursal(mostradorInfoSucursal);
                mostradorInfoPersonalCollectionMostradorInfoPersonal = em.merge(mostradorInfoPersonalCollectionMostradorInfoPersonal);
                if (oldMostradorInfoSucursalOfMostradorInfoPersonalCollectionMostradorInfoPersonal != null) {
                    oldMostradorInfoSucursalOfMostradorInfoPersonalCollectionMostradorInfoPersonal.getMostradorInfoPersonalCollection().remove(mostradorInfoPersonalCollectionMostradorInfoPersonal);
                    oldMostradorInfoSucursalOfMostradorInfoPersonalCollectionMostradorInfoPersonal = em.merge(oldMostradorInfoSucursalOfMostradorInfoPersonalCollectionMostradorInfoPersonal);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findMostradorInfoSucursal(mostradorInfoSucursal.getNoSeguroSocial()) != null) {
                throw new PreexistingEntityException("MostradorInfoSucursal " + mostradorInfoSucursal + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(MostradorInfoSucursal mostradorInfoSucursal) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            MostradorInfoSucursal persistentMostradorInfoSucursal = em.find(MostradorInfoSucursal.class, mostradorInfoSucursal.getNoSeguroSocial());
            Collection<MostradorInfoPersonal> mostradorInfoPersonalCollectionOld = persistentMostradorInfoSucursal.getMostradorInfoPersonalCollection();
            Collection<MostradorInfoPersonal> mostradorInfoPersonalCollectionNew = mostradorInfoSucursal.getMostradorInfoPersonalCollection();
            List<String> illegalOrphanMessages = null;
            for (MostradorInfoPersonal mostradorInfoPersonalCollectionOldMostradorInfoPersonal : mostradorInfoPersonalCollectionOld) {
                if (!mostradorInfoPersonalCollectionNew.contains(mostradorInfoPersonalCollectionOldMostradorInfoPersonal)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain MostradorInfoPersonal " + mostradorInfoPersonalCollectionOldMostradorInfoPersonal + " since its mostradorInfoSucursal field is not nullable.");
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
            mostradorInfoSucursal.setMostradorInfoPersonalCollection(mostradorInfoPersonalCollectionNew);
            mostradorInfoSucursal = em.merge(mostradorInfoSucursal);
            for (MostradorInfoPersonal mostradorInfoPersonalCollectionNewMostradorInfoPersonal : mostradorInfoPersonalCollectionNew) {
                if (!mostradorInfoPersonalCollectionOld.contains(mostradorInfoPersonalCollectionNewMostradorInfoPersonal)) {
                    MostradorInfoSucursal oldMostradorInfoSucursalOfMostradorInfoPersonalCollectionNewMostradorInfoPersonal = mostradorInfoPersonalCollectionNewMostradorInfoPersonal.getMostradorInfoSucursal();
                    mostradorInfoPersonalCollectionNewMostradorInfoPersonal.setMostradorInfoSucursal(mostradorInfoSucursal);
                    mostradorInfoPersonalCollectionNewMostradorInfoPersonal = em.merge(mostradorInfoPersonalCollectionNewMostradorInfoPersonal);
                    if (oldMostradorInfoSucursalOfMostradorInfoPersonalCollectionNewMostradorInfoPersonal != null && !oldMostradorInfoSucursalOfMostradorInfoPersonalCollectionNewMostradorInfoPersonal.equals(mostradorInfoSucursal)) {
                        oldMostradorInfoSucursalOfMostradorInfoPersonalCollectionNewMostradorInfoPersonal.getMostradorInfoPersonalCollection().remove(mostradorInfoPersonalCollectionNewMostradorInfoPersonal);
                        oldMostradorInfoSucursalOfMostradorInfoPersonalCollectionNewMostradorInfoPersonal = em.merge(oldMostradorInfoSucursalOfMostradorInfoPersonalCollectionNewMostradorInfoPersonal);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = mostradorInfoSucursal.getNoSeguroSocial();
                if (findMostradorInfoSucursal(id) == null) {
                    throw new NonexistentEntityException("The mostradorInfoSucursal with id " + id + " no longer exists.");
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
            MostradorInfoSucursal mostradorInfoSucursal;
            try {
                mostradorInfoSucursal = em.getReference(MostradorInfoSucursal.class, id);
                mostradorInfoSucursal.getNoSeguroSocial();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The mostradorInfoSucursal with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<MostradorInfoPersonal> mostradorInfoPersonalCollectionOrphanCheck = mostradorInfoSucursal.getMostradorInfoPersonalCollection();
            for (MostradorInfoPersonal mostradorInfoPersonalCollectionOrphanCheckMostradorInfoPersonal : mostradorInfoPersonalCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This MostradorInfoSucursal (" + mostradorInfoSucursal + ") cannot be destroyed since the MostradorInfoPersonal " + mostradorInfoPersonalCollectionOrphanCheckMostradorInfoPersonal + " in its mostradorInfoPersonalCollection field has a non-nullable mostradorInfoSucursal field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(mostradorInfoSucursal);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<MostradorInfoSucursal> findMostradorInfoSucursalEntities() {
        return findMostradorInfoSucursalEntities(true, -1, -1);
    }

    public List<MostradorInfoSucursal> findMostradorInfoSucursalEntities(int maxResults, int firstResult) {
        return findMostradorInfoSucursalEntities(false, maxResults, firstResult);
    }

    private List<MostradorInfoSucursal> findMostradorInfoSucursalEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(MostradorInfoSucursal.class));
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

    public MostradorInfoSucursal findMostradorInfoSucursal(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(MostradorInfoSucursal.class, id);
        } finally {
            em.close();
        }
    }

    public int getMostradorInfoSucursalCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<MostradorInfoSucursal> rt = cq.from(MostradorInfoSucursal.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
