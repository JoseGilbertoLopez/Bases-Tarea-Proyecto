/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
import modelo.IntendenciaInfoContacto;
import modelo.IntendenciaInfoPersonal;
import modelo.IntendenciaInfoPersonalPK;
import modelo.IntendenciaInfoSucursal;

/**
 *
 * @author Jose G
 */
public class IntendenciaInfoPersonalJpaController implements Serializable {

    public IntendenciaInfoPersonalJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(IntendenciaInfoPersonal intendenciaInfoPersonal) throws PreexistingEntityException, Exception {
        if (intendenciaInfoPersonal.getIntendenciaInfoPersonalPK() == null) {
            intendenciaInfoPersonal.setIntendenciaInfoPersonalPK(new IntendenciaInfoPersonalPK());
        }
        intendenciaInfoPersonal.getIntendenciaInfoPersonalPK().setRfc(intendenciaInfoPersonal.getIntendenciaInfoContacto().getIntendenciaInfoContactoPK().getRfc());
        intendenciaInfoPersonal.getIntendenciaInfoPersonalPK().setNoSeguroSocial(intendenciaInfoPersonal.getIntendenciaInfoSucursal().getNoSeguroSocial());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            IntendenciaInfoContacto intendenciaInfoContacto = intendenciaInfoPersonal.getIntendenciaInfoContacto();
            if (intendenciaInfoContacto != null) {
                intendenciaInfoContacto = em.getReference(intendenciaInfoContacto.getClass(), intendenciaInfoContacto.getIntendenciaInfoContactoPK());
                intendenciaInfoPersonal.setIntendenciaInfoContacto(intendenciaInfoContacto);
            }
            IntendenciaInfoSucursal intendenciaInfoSucursal = intendenciaInfoPersonal.getIntendenciaInfoSucursal();
            if (intendenciaInfoSucursal != null) {
                intendenciaInfoSucursal = em.getReference(intendenciaInfoSucursal.getClass(), intendenciaInfoSucursal.getNoSeguroSocial());
                intendenciaInfoPersonal.setIntendenciaInfoSucursal(intendenciaInfoSucursal);
            }
            em.persist(intendenciaInfoPersonal);
            if (intendenciaInfoContacto != null) {
                intendenciaInfoContacto.getIntendenciaInfoPersonalCollection().add(intendenciaInfoPersonal);
                intendenciaInfoContacto = em.merge(intendenciaInfoContacto);
            }
            if (intendenciaInfoSucursal != null) {
                intendenciaInfoSucursal.getIntendenciaInfoPersonalCollection().add(intendenciaInfoPersonal);
                intendenciaInfoSucursal = em.merge(intendenciaInfoSucursal);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findIntendenciaInfoPersonal(intendenciaInfoPersonal.getIntendenciaInfoPersonalPK()) != null) {
                throw new PreexistingEntityException("IntendenciaInfoPersonal " + intendenciaInfoPersonal + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(IntendenciaInfoPersonal intendenciaInfoPersonal) throws NonexistentEntityException, Exception {
        intendenciaInfoPersonal.getIntendenciaInfoPersonalPK().setRfc(intendenciaInfoPersonal.getIntendenciaInfoContacto().getIntendenciaInfoContactoPK().getRfc());
        intendenciaInfoPersonal.getIntendenciaInfoPersonalPK().setNoSeguroSocial(intendenciaInfoPersonal.getIntendenciaInfoSucursal().getNoSeguroSocial());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            IntendenciaInfoPersonal persistentIntendenciaInfoPersonal = em.find(IntendenciaInfoPersonal.class, intendenciaInfoPersonal.getIntendenciaInfoPersonalPK());
            IntendenciaInfoContacto intendenciaInfoContactoOld = persistentIntendenciaInfoPersonal.getIntendenciaInfoContacto();
            IntendenciaInfoContacto intendenciaInfoContactoNew = intendenciaInfoPersonal.getIntendenciaInfoContacto();
            IntendenciaInfoSucursal intendenciaInfoSucursalOld = persistentIntendenciaInfoPersonal.getIntendenciaInfoSucursal();
            IntendenciaInfoSucursal intendenciaInfoSucursalNew = intendenciaInfoPersonal.getIntendenciaInfoSucursal();
            if (intendenciaInfoContactoNew != null) {
                intendenciaInfoContactoNew = em.getReference(intendenciaInfoContactoNew.getClass(), intendenciaInfoContactoNew.getIntendenciaInfoContactoPK());
                intendenciaInfoPersonal.setIntendenciaInfoContacto(intendenciaInfoContactoNew);
            }
            if (intendenciaInfoSucursalNew != null) {
                intendenciaInfoSucursalNew = em.getReference(intendenciaInfoSucursalNew.getClass(), intendenciaInfoSucursalNew.getNoSeguroSocial());
                intendenciaInfoPersonal.setIntendenciaInfoSucursal(intendenciaInfoSucursalNew);
            }
            intendenciaInfoPersonal = em.merge(intendenciaInfoPersonal);
            if (intendenciaInfoContactoOld != null && !intendenciaInfoContactoOld.equals(intendenciaInfoContactoNew)) {
                intendenciaInfoContactoOld.getIntendenciaInfoPersonalCollection().remove(intendenciaInfoPersonal);
                intendenciaInfoContactoOld = em.merge(intendenciaInfoContactoOld);
            }
            if (intendenciaInfoContactoNew != null && !intendenciaInfoContactoNew.equals(intendenciaInfoContactoOld)) {
                intendenciaInfoContactoNew.getIntendenciaInfoPersonalCollection().add(intendenciaInfoPersonal);
                intendenciaInfoContactoNew = em.merge(intendenciaInfoContactoNew);
            }
            if (intendenciaInfoSucursalOld != null && !intendenciaInfoSucursalOld.equals(intendenciaInfoSucursalNew)) {
                intendenciaInfoSucursalOld.getIntendenciaInfoPersonalCollection().remove(intendenciaInfoPersonal);
                intendenciaInfoSucursalOld = em.merge(intendenciaInfoSucursalOld);
            }
            if (intendenciaInfoSucursalNew != null && !intendenciaInfoSucursalNew.equals(intendenciaInfoSucursalOld)) {
                intendenciaInfoSucursalNew.getIntendenciaInfoPersonalCollection().add(intendenciaInfoPersonal);
                intendenciaInfoSucursalNew = em.merge(intendenciaInfoSucursalNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                IntendenciaInfoPersonalPK id = intendenciaInfoPersonal.getIntendenciaInfoPersonalPK();
                if (findIntendenciaInfoPersonal(id) == null) {
                    throw new NonexistentEntityException("The intendenciaInfoPersonal with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(IntendenciaInfoPersonalPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            IntendenciaInfoPersonal intendenciaInfoPersonal;
            try {
                intendenciaInfoPersonal = em.getReference(IntendenciaInfoPersonal.class, id);
                intendenciaInfoPersonal.getIntendenciaInfoPersonalPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The intendenciaInfoPersonal with id " + id + " no longer exists.", enfe);
            }
            IntendenciaInfoContacto intendenciaInfoContacto = intendenciaInfoPersonal.getIntendenciaInfoContacto();
            if (intendenciaInfoContacto != null) {
                intendenciaInfoContacto.getIntendenciaInfoPersonalCollection().remove(intendenciaInfoPersonal);
                intendenciaInfoContacto = em.merge(intendenciaInfoContacto);
            }
            IntendenciaInfoSucursal intendenciaInfoSucursal = intendenciaInfoPersonal.getIntendenciaInfoSucursal();
            if (intendenciaInfoSucursal != null) {
                intendenciaInfoSucursal.getIntendenciaInfoPersonalCollection().remove(intendenciaInfoPersonal);
                intendenciaInfoSucursal = em.merge(intendenciaInfoSucursal);
            }
            em.remove(intendenciaInfoPersonal);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<IntendenciaInfoPersonal> findIntendenciaInfoPersonalEntities() {
        return findIntendenciaInfoPersonalEntities(true, -1, -1);
    }

    public List<IntendenciaInfoPersonal> findIntendenciaInfoPersonalEntities(int maxResults, int firstResult) {
        return findIntendenciaInfoPersonalEntities(false, maxResults, firstResult);
    }

    private List<IntendenciaInfoPersonal> findIntendenciaInfoPersonalEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(IntendenciaInfoPersonal.class));
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

    public IntendenciaInfoPersonal findIntendenciaInfoPersonal(IntendenciaInfoPersonalPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(IntendenciaInfoPersonal.class, id);
        } finally {
            em.close();
        }
    }

    public int getIntendenciaInfoPersonalCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<IntendenciaInfoPersonal> rt = cq.from(IntendenciaInfoPersonal.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
