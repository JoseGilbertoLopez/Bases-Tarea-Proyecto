/*
 * To change this license header, choose License Headers in Project Properties.
 * Cammbios que github deberia marcar.
 */
package Controlador;

import Controlador.exceptions.NonexistentEntityException;
import Controlador.exceptions.PreexistingEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelo.Sucursal;
import modelo.TelefonoSucursal;
import modelo.TelefonoSucursalPK;
import modelo.TelefonosSucursales;

/**
 *
 * @author Jose G
 */
public class TelefonoSucursalJpaController implements Serializable {

    public TelefonoSucursalJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TelefonoSucursal telefonoSucursal) throws PreexistingEntityException, Exception {
        if (telefonoSucursal.getTelefonoSucursalPK() == null) {
            telefonoSucursal.setTelefonoSucursalPK(new TelefonoSucursalPK());
        }
        //telefonoSucursal.getTelefonoSucursalPK().setIdSucursal(telefonoSucursal.getSucursal().getIdSucursal());
        //telefonoSucursal.getTelefonoSucursalPK().setIdTelefono(telefonoSucursal.getTelefonosSucursales().getIdTelefono());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Sucursal sucursal = telefonoSucursal.getSucursal();
            if (sucursal != null) {
                sucursal = em.getReference(sucursal.getClass(), sucursal.getIdSucursal());
                telefonoSucursal.setSucursal(sucursal);
            }
            TelefonosSucursales telefonosSucursales = telefonoSucursal.getTelefonosSucursales();
            if (telefonosSucursales != null) {
                telefonosSucursales = em.getReference(telefonosSucursales.getClass(), telefonosSucursales.getIdTelefono());
                telefonoSucursal.setTelefonosSucursales(telefonosSucursales);
            }
            em.persist(telefonoSucursal);
            if (sucursal != null) {
                sucursal.getTelefonoSucursalCollection().add(telefonoSucursal);
                sucursal = em.merge(sucursal);
            }
            if (telefonosSucursales != null) {
                telefonosSucursales.getTelefonoSucursalCollection().add(telefonoSucursal);
                telefonosSucursales = em.merge(telefonosSucursales);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTelefonoSucursal(telefonoSucursal.getTelefonoSucursalPK()) != null) {
                throw new PreexistingEntityException("TelefonoSucursal " + telefonoSucursal + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TelefonoSucursal telefonoSucursal) throws NonexistentEntityException, Exception {
        //telefonoSucursal.getTelefonoSucursalPK().setIdSucursal(telefonoSucursal.getSucursal().getIdSucursal());
        //telefonoSucursal.getTelefonoSucursalPK().setIdTelefono(telefonoSucursal.getTelefonosSucursales().getIdTelefono());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TelefonoSucursal persistentTelefonoSucursal = em.find(TelefonoSucursal.class, telefonoSucursal.getTelefonoSucursalPK());
            Sucursal sucursalOld = persistentTelefonoSucursal.getSucursal();
            Sucursal sucursalNew = telefonoSucursal.getSucursal();
            TelefonosSucursales telefonosSucursalesOld = persistentTelefonoSucursal.getTelefonosSucursales();
            TelefonosSucursales telefonosSucursalesNew = telefonoSucursal.getTelefonosSucursales();
            if (sucursalNew != null) {
                sucursalNew = em.getReference(sucursalNew.getClass(), sucursalNew.getIdSucursal());
                telefonoSucursal.setSucursal(sucursalNew);
            }
            if (telefonosSucursalesNew != null) {
                telefonosSucursalesNew = em.getReference(telefonosSucursalesNew.getClass(), telefonosSucursalesNew.getIdTelefono());
                telefonoSucursal.setTelefonosSucursales(telefonosSucursalesNew);
            }
            telefonoSucursal = em.merge(telefonoSucursal);
            if (sucursalOld != null && !sucursalOld.equals(sucursalNew)) {
                sucursalOld.getTelefonoSucursalCollection().remove(telefonoSucursal);
                sucursalOld = em.merge(sucursalOld);
            }
            if (sucursalNew != null && !sucursalNew.equals(sucursalOld)) {
                sucursalNew.getTelefonoSucursalCollection().add(telefonoSucursal);
                sucursalNew = em.merge(sucursalNew);
            }
            if (telefonosSucursalesOld != null && !telefonosSucursalesOld.equals(telefonosSucursalesNew)) {
                telefonosSucursalesOld.getTelefonoSucursalCollection().remove(telefonoSucursal);
                telefonosSucursalesOld = em.merge(telefonosSucursalesOld);
            }
            if (telefonosSucursalesNew != null && !telefonosSucursalesNew.equals(telefonosSucursalesOld)) {
                telefonosSucursalesNew.getTelefonoSucursalCollection().add(telefonoSucursal);
                telefonosSucursalesNew = em.merge(telefonosSucursalesNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                TelefonoSucursalPK id = telefonoSucursal.getTelefonoSucursalPK();
                if (findTelefonoSucursal(id) == null) {
                    throw new NonexistentEntityException("The telefonoSucursal with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(TelefonoSucursalPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TelefonoSucursal telefonoSucursal;
            try {
                telefonoSucursal = em.getReference(TelefonoSucursal.class, id);
                telefonoSucursal.getTelefonoSucursalPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The telefonoSucursal with id " + id + " no longer exists.", enfe);
            }
            Sucursal sucursal = telefonoSucursal.getSucursal();
            if (sucursal != null) {
                sucursal.getTelefonoSucursalCollection().remove(telefonoSucursal);
                sucursal = em.merge(sucursal);
            }
            TelefonosSucursales telefonosSucursales = telefonoSucursal.getTelefonosSucursales();
            if (telefonosSucursales != null) {
                telefonosSucursales.getTelefonoSucursalCollection().remove(telefonoSucursal);
                telefonosSucursales = em.merge(telefonosSucursales);
            }
            em.remove(telefonoSucursal);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TelefonoSucursal> findTelefonoSucursalEntities() {
        return findTelefonoSucursalEntities(true, -1, -1);
    }

    public List<TelefonoSucursal> findTelefonoSucursalEntities(int maxResults, int firstResult) {
        return findTelefonoSucursalEntities(false, maxResults, firstResult);
    }

    private List<TelefonoSucursal> findTelefonoSucursalEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TelefonoSucursal.class));
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

    public TelefonoSucursal findTelefonoSucursal(TelefonoSucursalPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TelefonoSucursal.class, id);
        } finally {
            em.close();
        }
    }

    public int getTelefonoSucursalCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TelefonoSucursal> rt = cq.from(TelefonoSucursal.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
