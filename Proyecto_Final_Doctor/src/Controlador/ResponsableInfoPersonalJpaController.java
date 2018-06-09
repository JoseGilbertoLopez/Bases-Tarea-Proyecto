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
import modelo.ResponsableInfoContacto;
import modelo.ResponsableInfoPersonal;
import modelo.ResponsableInfoPersonalPK;
import modelo.ResponsableInfoSucursal;

/**
 *
 * @author Jose G
 */
public class ResponsableInfoPersonalJpaController implements Serializable {

    public ResponsableInfoPersonalJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ResponsableInfoPersonal responsableInfoPersonal) throws PreexistingEntityException, Exception {
        if (responsableInfoPersonal.getResponsableInfoPersonalPK() == null) {
            responsableInfoPersonal.setResponsableInfoPersonalPK(new ResponsableInfoPersonalPK());
        }
        responsableInfoPersonal.getResponsableInfoPersonalPK().setNoSeguroSocial(responsableInfoPersonal.getResponsableInfoSucursal().getNoSeguroSocial());
        responsableInfoPersonal.getResponsableInfoPersonalPK().setRfc(responsableInfoPersonal.getResponsableInfoContacto().getResponsableInfoContactoPK().getRfc());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ResponsableInfoContacto responsableInfoContacto = responsableInfoPersonal.getResponsableInfoContacto();
            if (responsableInfoContacto != null) {
                responsableInfoContacto = em.getReference(responsableInfoContacto.getClass(), responsableInfoContacto.getResponsableInfoContactoPK());
                responsableInfoPersonal.setResponsableInfoContacto(responsableInfoContacto);
            }
            ResponsableInfoSucursal responsableInfoSucursal = responsableInfoPersonal.getResponsableInfoSucursal();
            if (responsableInfoSucursal != null) {
                responsableInfoSucursal = em.getReference(responsableInfoSucursal.getClass(), responsableInfoSucursal.getNoSeguroSocial());
                responsableInfoPersonal.setResponsableInfoSucursal(responsableInfoSucursal);
            }
            em.persist(responsableInfoPersonal);
            if (responsableInfoContacto != null) {
                responsableInfoContacto.getResponsableInfoPersonalCollection().add(responsableInfoPersonal);
                responsableInfoContacto = em.merge(responsableInfoContacto);
            }
            if (responsableInfoSucursal != null) {
                responsableInfoSucursal.getResponsableInfoPersonalCollection().add(responsableInfoPersonal);
                responsableInfoSucursal = em.merge(responsableInfoSucursal);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findResponsableInfoPersonal(responsableInfoPersonal.getResponsableInfoPersonalPK()) != null) {
                throw new PreexistingEntityException("ResponsableInfoPersonal " + responsableInfoPersonal + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ResponsableInfoPersonal responsableInfoPersonal) throws NonexistentEntityException, Exception {
        responsableInfoPersonal.getResponsableInfoPersonalPK().setNoSeguroSocial(responsableInfoPersonal.getResponsableInfoSucursal().getNoSeguroSocial());
        responsableInfoPersonal.getResponsableInfoPersonalPK().setRfc(responsableInfoPersonal.getResponsableInfoContacto().getResponsableInfoContactoPK().getRfc());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ResponsableInfoPersonal persistentResponsableInfoPersonal = em.find(ResponsableInfoPersonal.class, responsableInfoPersonal.getResponsableInfoPersonalPK());
            ResponsableInfoContacto responsableInfoContactoOld = persistentResponsableInfoPersonal.getResponsableInfoContacto();
            ResponsableInfoContacto responsableInfoContactoNew = responsableInfoPersonal.getResponsableInfoContacto();
            ResponsableInfoSucursal responsableInfoSucursalOld = persistentResponsableInfoPersonal.getResponsableInfoSucursal();
            ResponsableInfoSucursal responsableInfoSucursalNew = responsableInfoPersonal.getResponsableInfoSucursal();
            if (responsableInfoContactoNew != null) {
                responsableInfoContactoNew = em.getReference(responsableInfoContactoNew.getClass(), responsableInfoContactoNew.getResponsableInfoContactoPK());
                responsableInfoPersonal.setResponsableInfoContacto(responsableInfoContactoNew);
            }
            if (responsableInfoSucursalNew != null) {
                responsableInfoSucursalNew = em.getReference(responsableInfoSucursalNew.getClass(), responsableInfoSucursalNew.getNoSeguroSocial());
                responsableInfoPersonal.setResponsableInfoSucursal(responsableInfoSucursalNew);
            }
            responsableInfoPersonal = em.merge(responsableInfoPersonal);
            if (responsableInfoContactoOld != null && !responsableInfoContactoOld.equals(responsableInfoContactoNew)) {
                responsableInfoContactoOld.getResponsableInfoPersonalCollection().remove(responsableInfoPersonal);
                responsableInfoContactoOld = em.merge(responsableInfoContactoOld);
            }
            if (responsableInfoContactoNew != null && !responsableInfoContactoNew.equals(responsableInfoContactoOld)) {
                responsableInfoContactoNew.getResponsableInfoPersonalCollection().add(responsableInfoPersonal);
                responsableInfoContactoNew = em.merge(responsableInfoContactoNew);
            }
            if (responsableInfoSucursalOld != null && !responsableInfoSucursalOld.equals(responsableInfoSucursalNew)) {
                responsableInfoSucursalOld.getResponsableInfoPersonalCollection().remove(responsableInfoPersonal);
                responsableInfoSucursalOld = em.merge(responsableInfoSucursalOld);
            }
            if (responsableInfoSucursalNew != null && !responsableInfoSucursalNew.equals(responsableInfoSucursalOld)) {
                responsableInfoSucursalNew.getResponsableInfoPersonalCollection().add(responsableInfoPersonal);
                responsableInfoSucursalNew = em.merge(responsableInfoSucursalNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                ResponsableInfoPersonalPK id = responsableInfoPersonal.getResponsableInfoPersonalPK();
                if (findResponsableInfoPersonal(id) == null) {
                    throw new NonexistentEntityException("The responsableInfoPersonal with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(ResponsableInfoPersonalPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ResponsableInfoPersonal responsableInfoPersonal;
            try {
                responsableInfoPersonal = em.getReference(ResponsableInfoPersonal.class, id);
                responsableInfoPersonal.getResponsableInfoPersonalPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The responsableInfoPersonal with id " + id + " no longer exists.", enfe);
            }
            ResponsableInfoContacto responsableInfoContacto = responsableInfoPersonal.getResponsableInfoContacto();
            if (responsableInfoContacto != null) {
                responsableInfoContacto.getResponsableInfoPersonalCollection().remove(responsableInfoPersonal);
                responsableInfoContacto = em.merge(responsableInfoContacto);
            }
            ResponsableInfoSucursal responsableInfoSucursal = responsableInfoPersonal.getResponsableInfoSucursal();
            if (responsableInfoSucursal != null) {
                responsableInfoSucursal.getResponsableInfoPersonalCollection().remove(responsableInfoPersonal);
                responsableInfoSucursal = em.merge(responsableInfoSucursal);
            }
            em.remove(responsableInfoPersonal);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ResponsableInfoPersonal> findResponsableInfoPersonalEntities() {
        return findResponsableInfoPersonalEntities(true, -1, -1);
    }

    public List<ResponsableInfoPersonal> findResponsableInfoPersonalEntities(int maxResults, int firstResult) {
        return findResponsableInfoPersonalEntities(false, maxResults, firstResult);
    }

    private List<ResponsableInfoPersonal> findResponsableInfoPersonalEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ResponsableInfoPersonal.class));
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

    public ResponsableInfoPersonal findResponsableInfoPersonal(ResponsableInfoPersonalPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ResponsableInfoPersonal.class, id);
        } finally {
            em.close();
        }
    }

    public int getResponsableInfoPersonalCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ResponsableInfoPersonal> rt = cq.from(ResponsableInfoPersonal.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
