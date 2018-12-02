/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

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
import modelo.GeneralInfoContacto;
import modelo.GeneralInfoPersonal;
import modelo.GeneralInfoPersonalPK;
import modelo.GeneralInfoSucursal;

/**
 *
 * @author Jose G
 */
public class GeneralInfoPersonalJpaController implements Serializable {

    public GeneralInfoPersonalJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(GeneralInfoPersonal generalInfoPersonal) throws PreexistingEntityException, Exception {
        if (generalInfoPersonal.getGeneralInfoPersonalPK() == null) {
            generalInfoPersonal.setGeneralInfoPersonalPK(new GeneralInfoPersonalPK());
        }
        generalInfoPersonal.getGeneralInfoPersonalPK().setNoSeguroSocial(generalInfoPersonal.getGeneralInfoSucursal().getNoSeguroSocial());
        generalInfoPersonal.getGeneralInfoPersonalPK().setRfc(generalInfoPersonal.getGeneralInfoContacto().getGeneralInfoContactoPK().getRfc());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            GeneralInfoContacto generalInfoContacto = generalInfoPersonal.getGeneralInfoContacto();
            if (generalInfoContacto != null) {
                generalInfoContacto = em.getReference(generalInfoContacto.getClass(), generalInfoContacto.getGeneralInfoContactoPK());
                generalInfoPersonal.setGeneralInfoContacto(generalInfoContacto);
            }
            GeneralInfoSucursal generalInfoSucursal = generalInfoPersonal.getGeneralInfoSucursal();
            if (generalInfoSucursal != null) {
                generalInfoSucursal = em.getReference(generalInfoSucursal.getClass(), generalInfoSucursal.getNoSeguroSocial());
                generalInfoPersonal.setGeneralInfoSucursal(generalInfoSucursal);
            }
            em.persist(generalInfoPersonal);
            if (generalInfoContacto != null) {
                generalInfoContacto.getGeneralInfoPersonalCollection().add(generalInfoPersonal);
                generalInfoContacto = em.merge(generalInfoContacto);
            }
            if (generalInfoSucursal != null) {
                generalInfoSucursal.getGeneralInfoPersonalCollection().add(generalInfoPersonal);
                generalInfoSucursal = em.merge(generalInfoSucursal);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findGeneralInfoPersonal(generalInfoPersonal.getGeneralInfoPersonalPK()) != null) {
                throw new PreexistingEntityException("GeneralInfoPersonal " + generalInfoPersonal + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(GeneralInfoPersonal generalInfoPersonal) throws NonexistentEntityException, Exception {
        generalInfoPersonal.getGeneralInfoPersonalPK().setNoSeguroSocial(generalInfoPersonal.getGeneralInfoSucursal().getNoSeguroSocial());
        generalInfoPersonal.getGeneralInfoPersonalPK().setRfc(generalInfoPersonal.getGeneralInfoContacto().getGeneralInfoContactoPK().getRfc());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            GeneralInfoPersonal persistentGeneralInfoPersonal = em.find(GeneralInfoPersonal.class, generalInfoPersonal.getGeneralInfoPersonalPK());
            GeneralInfoContacto generalInfoContactoOld = persistentGeneralInfoPersonal.getGeneralInfoContacto();
            GeneralInfoContacto generalInfoContactoNew = generalInfoPersonal.getGeneralInfoContacto();
            GeneralInfoSucursal generalInfoSucursalOld = persistentGeneralInfoPersonal.getGeneralInfoSucursal();
            GeneralInfoSucursal generalInfoSucursalNew = generalInfoPersonal.getGeneralInfoSucursal();
            if (generalInfoContactoNew != null) {
                generalInfoContactoNew = em.getReference(generalInfoContactoNew.getClass(), generalInfoContactoNew.getGeneralInfoContactoPK());
                generalInfoPersonal.setGeneralInfoContacto(generalInfoContactoNew);
            }
            if (generalInfoSucursalNew != null) {
                generalInfoSucursalNew = em.getReference(generalInfoSucursalNew.getClass(), generalInfoSucursalNew.getNoSeguroSocial());
                generalInfoPersonal.setGeneralInfoSucursal(generalInfoSucursalNew);
            }
            generalInfoPersonal = em.merge(generalInfoPersonal);
            if (generalInfoContactoOld != null && !generalInfoContactoOld.equals(generalInfoContactoNew)) {
                generalInfoContactoOld.getGeneralInfoPersonalCollection().remove(generalInfoPersonal);
                generalInfoContactoOld = em.merge(generalInfoContactoOld);
            }
            if (generalInfoContactoNew != null && !generalInfoContactoNew.equals(generalInfoContactoOld)) {
                generalInfoContactoNew.getGeneralInfoPersonalCollection().add(generalInfoPersonal);
                generalInfoContactoNew = em.merge(generalInfoContactoNew);
            }
            if (generalInfoSucursalOld != null && !generalInfoSucursalOld.equals(generalInfoSucursalNew)) {
                generalInfoSucursalOld.getGeneralInfoPersonalCollection().remove(generalInfoPersonal);
                generalInfoSucursalOld = em.merge(generalInfoSucursalOld);
            }
            if (generalInfoSucursalNew != null && !generalInfoSucursalNew.equals(generalInfoSucursalOld)) {
                generalInfoSucursalNew.getGeneralInfoPersonalCollection().add(generalInfoPersonal);
                generalInfoSucursalNew = em.merge(generalInfoSucursalNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                GeneralInfoPersonalPK id = generalInfoPersonal.getGeneralInfoPersonalPK();
                if (findGeneralInfoPersonal(id) == null) {
                    throw new NonexistentEntityException("The generalInfoPersonal with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(GeneralInfoPersonalPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            GeneralInfoPersonal generalInfoPersonal;
            try {
                generalInfoPersonal = em.getReference(GeneralInfoPersonal.class, id);
                generalInfoPersonal.getGeneralInfoPersonalPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The generalInfoPersonal with id " + id + " no longer exists.", enfe);
            }
            GeneralInfoContacto generalInfoContacto = generalInfoPersonal.getGeneralInfoContacto();
            if (generalInfoContacto != null) {
                generalInfoContacto.getGeneralInfoPersonalCollection().remove(generalInfoPersonal);
                generalInfoContacto = em.merge(generalInfoContacto);
            }
            GeneralInfoSucursal generalInfoSucursal = generalInfoPersonal.getGeneralInfoSucursal();
            if (generalInfoSucursal != null) {
                generalInfoSucursal.getGeneralInfoPersonalCollection().remove(generalInfoPersonal);
                generalInfoSucursal = em.merge(generalInfoSucursal);
            }
            em.remove(generalInfoPersonal);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<GeneralInfoPersonal> findGeneralInfoPersonalEntities() {
        return findGeneralInfoPersonalEntities(true, -1, -1);
    }

    public List<GeneralInfoPersonal> findGeneralInfoPersonalEntities(int maxResults, int firstResult) {
        return findGeneralInfoPersonalEntities(false, maxResults, firstResult);
    }

    private List<GeneralInfoPersonal> findGeneralInfoPersonalEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(GeneralInfoPersonal.class));
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

    public GeneralInfoPersonal findGeneralInfoPersonal(GeneralInfoPersonalPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(GeneralInfoPersonal.class, id);
        } finally {
            em.close();
        }
    }

    public int getGeneralInfoPersonalCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<GeneralInfoPersonal> rt = cq.from(GeneralInfoPersonal.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
