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
import java.math.BigDecimal;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelo.TelefonoSucursal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.TelefonosSucursales;

/**
 *
 * @author Jose G
 */
public class TelefonosSucursalesJpaController implements Serializable {

    public TelefonosSucursalesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TelefonosSucursales telefonosSucursales) throws PreexistingEntityException, Exception {
        if (telefonosSucursales.getTelefonoSucursalCollection() == null) {
            telefonosSucursales.setTelefonoSucursalCollection(new ArrayList<TelefonoSucursal>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<TelefonoSucursal> attachedTelefonoSucursalCollection = new ArrayList<TelefonoSucursal>();
            for (TelefonoSucursal telefonoSucursalCollectionTelefonoSucursalToAttach : telefonosSucursales.getTelefonoSucursalCollection()) {
                telefonoSucursalCollectionTelefonoSucursalToAttach = em.getReference(telefonoSucursalCollectionTelefonoSucursalToAttach.getClass(), telefonoSucursalCollectionTelefonoSucursalToAttach.getTelefonoSucursalPK());
                attachedTelefonoSucursalCollection.add(telefonoSucursalCollectionTelefonoSucursalToAttach);
            }
            telefonosSucursales.setTelefonoSucursalCollection(attachedTelefonoSucursalCollection);
            em.persist(telefonosSucursales);
            for (TelefonoSucursal telefonoSucursalCollectionTelefonoSucursal : telefonosSucursales.getTelefonoSucursalCollection()) {
                TelefonosSucursales oldTelefonosSucursalesOfTelefonoSucursalCollectionTelefonoSucursal = telefonoSucursalCollectionTelefonoSucursal.getTelefonosSucursales();
                telefonoSucursalCollectionTelefonoSucursal.setTelefonosSucursales(telefonosSucursales);
                telefonoSucursalCollectionTelefonoSucursal = em.merge(telefonoSucursalCollectionTelefonoSucursal);
                if (oldTelefonosSucursalesOfTelefonoSucursalCollectionTelefonoSucursal != null) {
                    oldTelefonosSucursalesOfTelefonoSucursalCollectionTelefonoSucursal.getTelefonoSucursalCollection().remove(telefonoSucursalCollectionTelefonoSucursal);
                    oldTelefonosSucursalesOfTelefonoSucursalCollectionTelefonoSucursal = em.merge(oldTelefonosSucursalesOfTelefonoSucursalCollectionTelefonoSucursal);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTelefonosSucursales(telefonosSucursales.getIdTelefono()) != null) {
                throw new PreexistingEntityException("TelefonosSucursales " + telefonosSucursales + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TelefonosSucursales telefonosSucursales) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TelefonosSucursales persistentTelefonosSucursales = em.find(TelefonosSucursales.class, telefonosSucursales.getIdTelefono());
            Collection<TelefonoSucursal> telefonoSucursalCollectionOld = persistentTelefonosSucursales.getTelefonoSucursalCollection();
            Collection<TelefonoSucursal> telefonoSucursalCollectionNew = telefonosSucursales.getTelefonoSucursalCollection();
            List<String> illegalOrphanMessages = null;
            for (TelefonoSucursal telefonoSucursalCollectionOldTelefonoSucursal : telefonoSucursalCollectionOld) {
                if (!telefonoSucursalCollectionNew.contains(telefonoSucursalCollectionOldTelefonoSucursal)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain TelefonoSucursal " + telefonoSucursalCollectionOldTelefonoSucursal + " since its telefonosSucursales field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<TelefonoSucursal> attachedTelefonoSucursalCollectionNew = new ArrayList<TelefonoSucursal>();
            for (TelefonoSucursal telefonoSucursalCollectionNewTelefonoSucursalToAttach : telefonoSucursalCollectionNew) {
                telefonoSucursalCollectionNewTelefonoSucursalToAttach = em.getReference(telefonoSucursalCollectionNewTelefonoSucursalToAttach.getClass(), telefonoSucursalCollectionNewTelefonoSucursalToAttach.getTelefonoSucursalPK());
                attachedTelefonoSucursalCollectionNew.add(telefonoSucursalCollectionNewTelefonoSucursalToAttach);
            }
            telefonoSucursalCollectionNew = attachedTelefonoSucursalCollectionNew;
            telefonosSucursales.setTelefonoSucursalCollection(telefonoSucursalCollectionNew);
            telefonosSucursales = em.merge(telefonosSucursales);
            for (TelefonoSucursal telefonoSucursalCollectionNewTelefonoSucursal : telefonoSucursalCollectionNew) {
                if (!telefonoSucursalCollectionOld.contains(telefonoSucursalCollectionNewTelefonoSucursal)) {
                    TelefonosSucursales oldTelefonosSucursalesOfTelefonoSucursalCollectionNewTelefonoSucursal = telefonoSucursalCollectionNewTelefonoSucursal.getTelefonosSucursales();
                    telefonoSucursalCollectionNewTelefonoSucursal.setTelefonosSucursales(telefonosSucursales);
                    telefonoSucursalCollectionNewTelefonoSucursal = em.merge(telefonoSucursalCollectionNewTelefonoSucursal);
                    if (oldTelefonosSucursalesOfTelefonoSucursalCollectionNewTelefonoSucursal != null && !oldTelefonosSucursalesOfTelefonoSucursalCollectionNewTelefonoSucursal.equals(telefonosSucursales)) {
                        oldTelefonosSucursalesOfTelefonoSucursalCollectionNewTelefonoSucursal.getTelefonoSucursalCollection().remove(telefonoSucursalCollectionNewTelefonoSucursal);
                        oldTelefonosSucursalesOfTelefonoSucursalCollectionNewTelefonoSucursal = em.merge(oldTelefonosSucursalesOfTelefonoSucursalCollectionNewTelefonoSucursal);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = telefonosSucursales.getIdTelefono();
                if (findTelefonosSucursales(id) == null) {
                    throw new NonexistentEntityException("The telefonosSucursales with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(BigDecimal id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TelefonosSucursales telefonosSucursales;
            try {
                telefonosSucursales = em.getReference(TelefonosSucursales.class, id);
                telefonosSucursales.getIdTelefono();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The telefonosSucursales with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<TelefonoSucursal> telefonoSucursalCollectionOrphanCheck = telefonosSucursales.getTelefonoSucursalCollection();
            for (TelefonoSucursal telefonoSucursalCollectionOrphanCheckTelefonoSucursal : telefonoSucursalCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TelefonosSucursales (" + telefonosSucursales + ") cannot be destroyed since the TelefonoSucursal " + telefonoSucursalCollectionOrphanCheckTelefonoSucursal + " in its telefonoSucursalCollection field has a non-nullable telefonosSucursales field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(telefonosSucursales);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TelefonosSucursales> findTelefonosSucursalesEntities() {
        return findTelefonosSucursalesEntities(true, -1, -1);
    }

    public List<TelefonosSucursales> findTelefonosSucursalesEntities(int maxResults, int firstResult) {
        return findTelefonosSucursalesEntities(false, maxResults, firstResult);
    }

    private List<TelefonosSucursales> findTelefonosSucursalesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TelefonosSucursales.class));
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

    public TelefonosSucursales findTelefonosSucursales(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TelefonosSucursales.class, id);
        } finally {
            em.close();
        }
    }

    public int getTelefonosSucursalesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TelefonosSucursales> rt = cq.from(TelefonosSucursales.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
