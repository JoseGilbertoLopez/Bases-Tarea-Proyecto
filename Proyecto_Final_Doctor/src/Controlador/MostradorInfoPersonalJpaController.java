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
import modelo.MostradorInfoContacto;
import modelo.MostradorInfoPersonal;
import modelo.MostradorInfoPersonalPK;
import modelo.MostradorInfoSucursal;

/**
 *
 * @author Jose G
 */
public class MostradorInfoPersonalJpaController implements Serializable {

    public MostradorInfoPersonalJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(MostradorInfoPersonal mostradorInfoPersonal) throws PreexistingEntityException, Exception {
        if (mostradorInfoPersonal.getMostradorInfoPersonalPK() == null) {
            mostradorInfoPersonal.setMostradorInfoPersonalPK(new MostradorInfoPersonalPK());
        }
        mostradorInfoPersonal.getMostradorInfoPersonalPK().setRfc(mostradorInfoPersonal.getMostradorInfoContacto().getMostradorInfoContactoPK().getRfc());
        mostradorInfoPersonal.getMostradorInfoPersonalPK().setNoSeguroSocial(mostradorInfoPersonal.getMostradorInfoSucursal().getNoSeguroSocial());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            MostradorInfoContacto mostradorInfoContacto = mostradorInfoPersonal.getMostradorInfoContacto();
            if (mostradorInfoContacto != null) {
                mostradorInfoContacto = em.getReference(mostradorInfoContacto.getClass(), mostradorInfoContacto.getMostradorInfoContactoPK());
                mostradorInfoPersonal.setMostradorInfoContacto(mostradorInfoContacto);
            }
            MostradorInfoSucursal mostradorInfoSucursal = mostradorInfoPersonal.getMostradorInfoSucursal();
            if (mostradorInfoSucursal != null) {
                mostradorInfoSucursal = em.getReference(mostradorInfoSucursal.getClass(), mostradorInfoSucursal.getNoSeguroSocial());
                mostradorInfoPersonal.setMostradorInfoSucursal(mostradorInfoSucursal);
            }
            em.persist(mostradorInfoPersonal);
            if (mostradorInfoContacto != null) {
                mostradorInfoContacto.getMostradorInfoPersonalCollection().add(mostradorInfoPersonal);
                mostradorInfoContacto = em.merge(mostradorInfoContacto);
            }
            if (mostradorInfoSucursal != null) {
                mostradorInfoSucursal.getMostradorInfoPersonalCollection().add(mostradorInfoPersonal);
                mostradorInfoSucursal = em.merge(mostradorInfoSucursal);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findMostradorInfoPersonal(mostradorInfoPersonal.getMostradorInfoPersonalPK()) != null) {
                throw new PreexistingEntityException("MostradorInfoPersonal " + mostradorInfoPersonal + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(MostradorInfoPersonal mostradorInfoPersonal) throws NonexistentEntityException, Exception {
        mostradorInfoPersonal.getMostradorInfoPersonalPK().setRfc(mostradorInfoPersonal.getMostradorInfoContacto().getMostradorInfoContactoPK().getRfc());
        mostradorInfoPersonal.getMostradorInfoPersonalPK().setNoSeguroSocial(mostradorInfoPersonal.getMostradorInfoSucursal().getNoSeguroSocial());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            MostradorInfoPersonal persistentMostradorInfoPersonal = em.find(MostradorInfoPersonal.class, mostradorInfoPersonal.getMostradorInfoPersonalPK());
            MostradorInfoContacto mostradorInfoContactoOld = persistentMostradorInfoPersonal.getMostradorInfoContacto();
            MostradorInfoContacto mostradorInfoContactoNew = mostradorInfoPersonal.getMostradorInfoContacto();
            MostradorInfoSucursal mostradorInfoSucursalOld = persistentMostradorInfoPersonal.getMostradorInfoSucursal();
            MostradorInfoSucursal mostradorInfoSucursalNew = mostradorInfoPersonal.getMostradorInfoSucursal();
            if (mostradorInfoContactoNew != null) {
                mostradorInfoContactoNew = em.getReference(mostradorInfoContactoNew.getClass(), mostradorInfoContactoNew.getMostradorInfoContactoPK());
                mostradorInfoPersonal.setMostradorInfoContacto(mostradorInfoContactoNew);
            }
            if (mostradorInfoSucursalNew != null) {
                mostradorInfoSucursalNew = em.getReference(mostradorInfoSucursalNew.getClass(), mostradorInfoSucursalNew.getNoSeguroSocial());
                mostradorInfoPersonal.setMostradorInfoSucursal(mostradorInfoSucursalNew);
            }
            mostradorInfoPersonal = em.merge(mostradorInfoPersonal);
            if (mostradorInfoContactoOld != null && !mostradorInfoContactoOld.equals(mostradorInfoContactoNew)) {
                mostradorInfoContactoOld.getMostradorInfoPersonalCollection().remove(mostradorInfoPersonal);
                mostradorInfoContactoOld = em.merge(mostradorInfoContactoOld);
            }
            if (mostradorInfoContactoNew != null && !mostradorInfoContactoNew.equals(mostradorInfoContactoOld)) {
                mostradorInfoContactoNew.getMostradorInfoPersonalCollection().add(mostradorInfoPersonal);
                mostradorInfoContactoNew = em.merge(mostradorInfoContactoNew);
            }
            if (mostradorInfoSucursalOld != null && !mostradorInfoSucursalOld.equals(mostradorInfoSucursalNew)) {
                mostradorInfoSucursalOld.getMostradorInfoPersonalCollection().remove(mostradorInfoPersonal);
                mostradorInfoSucursalOld = em.merge(mostradorInfoSucursalOld);
            }
            if (mostradorInfoSucursalNew != null && !mostradorInfoSucursalNew.equals(mostradorInfoSucursalOld)) {
                mostradorInfoSucursalNew.getMostradorInfoPersonalCollection().add(mostradorInfoPersonal);
                mostradorInfoSucursalNew = em.merge(mostradorInfoSucursalNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                MostradorInfoPersonalPK id = mostradorInfoPersonal.getMostradorInfoPersonalPK();
                if (findMostradorInfoPersonal(id) == null) {
                    throw new NonexistentEntityException("The mostradorInfoPersonal with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(MostradorInfoPersonalPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            MostradorInfoPersonal mostradorInfoPersonal;
            try {
                mostradorInfoPersonal = em.getReference(MostradorInfoPersonal.class, id);
                mostradorInfoPersonal.getMostradorInfoPersonalPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The mostradorInfoPersonal with id " + id + " no longer exists.", enfe);
            }
            MostradorInfoContacto mostradorInfoContacto = mostradorInfoPersonal.getMostradorInfoContacto();
            if (mostradorInfoContacto != null) {
                mostradorInfoContacto.getMostradorInfoPersonalCollection().remove(mostradorInfoPersonal);
                mostradorInfoContacto = em.merge(mostradorInfoContacto);
            }
            MostradorInfoSucursal mostradorInfoSucursal = mostradorInfoPersonal.getMostradorInfoSucursal();
            if (mostradorInfoSucursal != null) {
                mostradorInfoSucursal.getMostradorInfoPersonalCollection().remove(mostradorInfoPersonal);
                mostradorInfoSucursal = em.merge(mostradorInfoSucursal);
            }
            em.remove(mostradorInfoPersonal);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<MostradorInfoPersonal> findMostradorInfoPersonalEntities() {
        return findMostradorInfoPersonalEntities(true, -1, -1);
    }

    public List<MostradorInfoPersonal> findMostradorInfoPersonalEntities(int maxResults, int firstResult) {
        return findMostradorInfoPersonalEntities(false, maxResults, firstResult);
    }

    private List<MostradorInfoPersonal> findMostradorInfoPersonalEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(MostradorInfoPersonal.class));
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

    public MostradorInfoPersonal findMostradorInfoPersonal(MostradorInfoPersonalPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(MostradorInfoPersonal.class, id);
        } finally {
            em.close();
        }
    }

    public int getMostradorInfoPersonalCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<MostradorInfoPersonal> rt = cq.from(MostradorInfoPersonal.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
