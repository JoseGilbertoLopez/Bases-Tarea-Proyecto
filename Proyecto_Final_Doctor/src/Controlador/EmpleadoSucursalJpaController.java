/*
 * To change this license header, choose License Headers in Project Properties.
 * parcheando el programa con comentarios en algunas clases controladores.
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
import modelo.EmpleadoInformacionPersonal;
import modelo.EmpleadoSucursal;
import modelo.EmpleadoSucursalPK;
import modelo.Sucursal;

/**
 *
 * @author Jose G
 */
public class EmpleadoSucursalJpaController implements Serializable {

    public EmpleadoSucursalJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(EmpleadoSucursal empleadoSucursal) throws PreexistingEntityException, Exception {
        if (empleadoSucursal.getEmpleadoSucursalPK() == null) {
            empleadoSucursal.setEmpleadoSucursalPK(new EmpleadoSucursalPK());
        }
        empleadoSucursal.getEmpleadoSucursalPK().setNoSeguroSocial(empleadoSucursal.getEmpleadoInformacionPersonal().getEmpleadoInformacionPersonalPK().getNoSeguroSocial());
        //empleadoSucursal.getEmpleadoSucursalPK().setIdSucursal(empleadoSucursal.getSucursal().getIdSucursal());
        empleadoSucursal.getEmpleadoSucursalPK().setRfc(empleadoSucursal.getEmpleadoInformacionPersonal().getEmpleadoInformacionPersonalPK().getRfc());
        empleadoSucursal.getEmpleadoSucursalPK().setIdCliente(empleadoSucursal.getEmpleadoInformacionPersonal().getEmpleadoInformacionPersonalPK().getIdCliente());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            EmpleadoInformacionPersonal empleadoInformacionPersonal = empleadoSucursal.getEmpleadoInformacionPersonal();
            if (empleadoInformacionPersonal != null) {
                empleadoInformacionPersonal = em.getReference(empleadoInformacionPersonal.getClass(), empleadoInformacionPersonal.getEmpleadoInformacionPersonalPK());
                empleadoSucursal.setEmpleadoInformacionPersonal(empleadoInformacionPersonal);
            }
            Sucursal sucursal = empleadoSucursal.getSucursal();
            if (sucursal != null) {
                sucursal = em.getReference(sucursal.getClass(), sucursal.getIdSucursal());
                empleadoSucursal.setSucursal(sucursal);
            }
            em.persist(empleadoSucursal);
            if (empleadoInformacionPersonal != null) {
                empleadoInformacionPersonal.getEmpleadoSucursalCollection().add(empleadoSucursal);
                empleadoInformacionPersonal = em.merge(empleadoInformacionPersonal);
            }
            if (sucursal != null) {
                sucursal.getEmpleadoSucursalCollection().add(empleadoSucursal);
                sucursal = em.merge(sucursal);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findEmpleadoSucursal(empleadoSucursal.getEmpleadoSucursalPK()) != null) {
                throw new PreexistingEntityException("EmpleadoSucursal " + empleadoSucursal + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(EmpleadoSucursal empleadoSucursal) throws NonexistentEntityException, Exception {
        empleadoSucursal.getEmpleadoSucursalPK().setNoSeguroSocial(empleadoSucursal.getEmpleadoInformacionPersonal().getEmpleadoInformacionPersonalPK().getNoSeguroSocial());
        //empleadoSucursal.getEmpleadoSucursalPK().setIdSucursal(empleadoSucursal.getSucursal().getIdSucursal());
        empleadoSucursal.getEmpleadoSucursalPK().setRfc(empleadoSucursal.getEmpleadoInformacionPersonal().getEmpleadoInformacionPersonalPK().getRfc());
        empleadoSucursal.getEmpleadoSucursalPK().setIdCliente(empleadoSucursal.getEmpleadoInformacionPersonal().getEmpleadoInformacionPersonalPK().getIdCliente());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            EmpleadoSucursal persistentEmpleadoSucursal = em.find(EmpleadoSucursal.class, empleadoSucursal.getEmpleadoSucursalPK());
            EmpleadoInformacionPersonal empleadoInformacionPersonalOld = persistentEmpleadoSucursal.getEmpleadoInformacionPersonal();
            EmpleadoInformacionPersonal empleadoInformacionPersonalNew = empleadoSucursal.getEmpleadoInformacionPersonal();
            Sucursal sucursalOld = persistentEmpleadoSucursal.getSucursal();
            Sucursal sucursalNew = empleadoSucursal.getSucursal();
            if (empleadoInformacionPersonalNew != null) {
                empleadoInformacionPersonalNew = em.getReference(empleadoInformacionPersonalNew.getClass(), empleadoInformacionPersonalNew.getEmpleadoInformacionPersonalPK());
                empleadoSucursal.setEmpleadoInformacionPersonal(empleadoInformacionPersonalNew);
            }
            if (sucursalNew != null) {
                sucursalNew = em.getReference(sucursalNew.getClass(), sucursalNew.getIdSucursal());
                empleadoSucursal.setSucursal(sucursalNew);
            }
            empleadoSucursal = em.merge(empleadoSucursal);
            if (empleadoInformacionPersonalOld != null && !empleadoInformacionPersonalOld.equals(empleadoInformacionPersonalNew)) {
                empleadoInformacionPersonalOld.getEmpleadoSucursalCollection().remove(empleadoSucursal);
                empleadoInformacionPersonalOld = em.merge(empleadoInformacionPersonalOld);
            }
            if (empleadoInformacionPersonalNew != null && !empleadoInformacionPersonalNew.equals(empleadoInformacionPersonalOld)) {
                empleadoInformacionPersonalNew.getEmpleadoSucursalCollection().add(empleadoSucursal);
                empleadoInformacionPersonalNew = em.merge(empleadoInformacionPersonalNew);
            }
            if (sucursalOld != null && !sucursalOld.equals(sucursalNew)) {
                sucursalOld.getEmpleadoSucursalCollection().remove(empleadoSucursal);
                sucursalOld = em.merge(sucursalOld);
            }
            if (sucursalNew != null && !sucursalNew.equals(sucursalOld)) {
                sucursalNew.getEmpleadoSucursalCollection().add(empleadoSucursal);
                sucursalNew = em.merge(sucursalNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                EmpleadoSucursalPK id = empleadoSucursal.getEmpleadoSucursalPK();
                if (findEmpleadoSucursal(id) == null) {
                    throw new NonexistentEntityException("The empleadoSucursal with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(EmpleadoSucursalPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            EmpleadoSucursal empleadoSucursal;
            try {
                empleadoSucursal = em.getReference(EmpleadoSucursal.class, id);
                empleadoSucursal.getEmpleadoSucursalPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The empleadoSucursal with id " + id + " no longer exists.", enfe);
            }
            EmpleadoInformacionPersonal empleadoInformacionPersonal = empleadoSucursal.getEmpleadoInformacionPersonal();
            if (empleadoInformacionPersonal != null) {
                empleadoInformacionPersonal.getEmpleadoSucursalCollection().remove(empleadoSucursal);
                empleadoInformacionPersonal = em.merge(empleadoInformacionPersonal);
            }
            Sucursal sucursal = empleadoSucursal.getSucursal();
            if (sucursal != null) {
                sucursal.getEmpleadoSucursalCollection().remove(empleadoSucursal);
                sucursal = em.merge(sucursal);
            }
            em.remove(empleadoSucursal);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<EmpleadoSucursal> findEmpleadoSucursalEntities() {
        return findEmpleadoSucursalEntities(true, -1, -1);
    }

    public List<EmpleadoSucursal> findEmpleadoSucursalEntities(int maxResults, int firstResult) {
        return findEmpleadoSucursalEntities(false, maxResults, firstResult);
    }

    private List<EmpleadoSucursal> findEmpleadoSucursalEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(EmpleadoSucursal.class));
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

    public EmpleadoSucursal findEmpleadoSucursal(EmpleadoSucursalPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(EmpleadoSucursal.class, id);
        } finally {
            em.close();
        }
    }

    public int getEmpleadoSucursalCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<EmpleadoSucursal> rt = cq.from(EmpleadoSucursal.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
