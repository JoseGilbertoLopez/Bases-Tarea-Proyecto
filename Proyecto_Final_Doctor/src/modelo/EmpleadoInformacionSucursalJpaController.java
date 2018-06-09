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
import modelo.EmpleadoInformacionPersonal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.EmpleadoInformacionSucursal;

/**
 *
 * @author Jose G
 */
public class EmpleadoInformacionSucursalJpaController implements Serializable {

    public EmpleadoInformacionSucursalJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(EmpleadoInformacionSucursal empleadoInformacionSucursal) throws PreexistingEntityException, Exception {
        if (empleadoInformacionSucursal.getEmpleadoInformacionPersonalCollection() == null) {
            empleadoInformacionSucursal.setEmpleadoInformacionPersonalCollection(new ArrayList<EmpleadoInformacionPersonal>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<EmpleadoInformacionPersonal> attachedEmpleadoInformacionPersonalCollection = new ArrayList<EmpleadoInformacionPersonal>();
            for (EmpleadoInformacionPersonal empleadoInformacionPersonalCollectionEmpleadoInformacionPersonalToAttach : empleadoInformacionSucursal.getEmpleadoInformacionPersonalCollection()) {
                empleadoInformacionPersonalCollectionEmpleadoInformacionPersonalToAttach = em.getReference(empleadoInformacionPersonalCollectionEmpleadoInformacionPersonalToAttach.getClass(), empleadoInformacionPersonalCollectionEmpleadoInformacionPersonalToAttach.getEmpleadoInformacionPersonalPK());
                attachedEmpleadoInformacionPersonalCollection.add(empleadoInformacionPersonalCollectionEmpleadoInformacionPersonalToAttach);
            }
            empleadoInformacionSucursal.setEmpleadoInformacionPersonalCollection(attachedEmpleadoInformacionPersonalCollection);
            em.persist(empleadoInformacionSucursal);
            for (EmpleadoInformacionPersonal empleadoInformacionPersonalCollectionEmpleadoInformacionPersonal : empleadoInformacionSucursal.getEmpleadoInformacionPersonalCollection()) {
                EmpleadoInformacionSucursal oldEmpleadoInformacionSucursalOfEmpleadoInformacionPersonalCollectionEmpleadoInformacionPersonal = empleadoInformacionPersonalCollectionEmpleadoInformacionPersonal.getEmpleadoInformacionSucursal();
                empleadoInformacionPersonalCollectionEmpleadoInformacionPersonal.setEmpleadoInformacionSucursal(empleadoInformacionSucursal);
                empleadoInformacionPersonalCollectionEmpleadoInformacionPersonal = em.merge(empleadoInformacionPersonalCollectionEmpleadoInformacionPersonal);
                if (oldEmpleadoInformacionSucursalOfEmpleadoInformacionPersonalCollectionEmpleadoInformacionPersonal != null) {
                    oldEmpleadoInformacionSucursalOfEmpleadoInformacionPersonalCollectionEmpleadoInformacionPersonal.getEmpleadoInformacionPersonalCollection().remove(empleadoInformacionPersonalCollectionEmpleadoInformacionPersonal);
                    oldEmpleadoInformacionSucursalOfEmpleadoInformacionPersonalCollectionEmpleadoInformacionPersonal = em.merge(oldEmpleadoInformacionSucursalOfEmpleadoInformacionPersonalCollectionEmpleadoInformacionPersonal);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findEmpleadoInformacionSucursal(empleadoInformacionSucursal.getNoSeguroSocial()) != null) {
                throw new PreexistingEntityException("EmpleadoInformacionSucursal " + empleadoInformacionSucursal + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(EmpleadoInformacionSucursal empleadoInformacionSucursal) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            EmpleadoInformacionSucursal persistentEmpleadoInformacionSucursal = em.find(EmpleadoInformacionSucursal.class, empleadoInformacionSucursal.getNoSeguroSocial());
            Collection<EmpleadoInformacionPersonal> empleadoInformacionPersonalCollectionOld = persistentEmpleadoInformacionSucursal.getEmpleadoInformacionPersonalCollection();
            Collection<EmpleadoInformacionPersonal> empleadoInformacionPersonalCollectionNew = empleadoInformacionSucursal.getEmpleadoInformacionPersonalCollection();
            List<String> illegalOrphanMessages = null;
            for (EmpleadoInformacionPersonal empleadoInformacionPersonalCollectionOldEmpleadoInformacionPersonal : empleadoInformacionPersonalCollectionOld) {
                if (!empleadoInformacionPersonalCollectionNew.contains(empleadoInformacionPersonalCollectionOldEmpleadoInformacionPersonal)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain EmpleadoInformacionPersonal " + empleadoInformacionPersonalCollectionOldEmpleadoInformacionPersonal + " since its empleadoInformacionSucursal field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<EmpleadoInformacionPersonal> attachedEmpleadoInformacionPersonalCollectionNew = new ArrayList<EmpleadoInformacionPersonal>();
            for (EmpleadoInformacionPersonal empleadoInformacionPersonalCollectionNewEmpleadoInformacionPersonalToAttach : empleadoInformacionPersonalCollectionNew) {
                empleadoInformacionPersonalCollectionNewEmpleadoInformacionPersonalToAttach = em.getReference(empleadoInformacionPersonalCollectionNewEmpleadoInformacionPersonalToAttach.getClass(), empleadoInformacionPersonalCollectionNewEmpleadoInformacionPersonalToAttach.getEmpleadoInformacionPersonalPK());
                attachedEmpleadoInformacionPersonalCollectionNew.add(empleadoInformacionPersonalCollectionNewEmpleadoInformacionPersonalToAttach);
            }
            empleadoInformacionPersonalCollectionNew = attachedEmpleadoInformacionPersonalCollectionNew;
            empleadoInformacionSucursal.setEmpleadoInformacionPersonalCollection(empleadoInformacionPersonalCollectionNew);
            empleadoInformacionSucursal = em.merge(empleadoInformacionSucursal);
            for (EmpleadoInformacionPersonal empleadoInformacionPersonalCollectionNewEmpleadoInformacionPersonal : empleadoInformacionPersonalCollectionNew) {
                if (!empleadoInformacionPersonalCollectionOld.contains(empleadoInformacionPersonalCollectionNewEmpleadoInformacionPersonal)) {
                    EmpleadoInformacionSucursal oldEmpleadoInformacionSucursalOfEmpleadoInformacionPersonalCollectionNewEmpleadoInformacionPersonal = empleadoInformacionPersonalCollectionNewEmpleadoInformacionPersonal.getEmpleadoInformacionSucursal();
                    empleadoInformacionPersonalCollectionNewEmpleadoInformacionPersonal.setEmpleadoInformacionSucursal(empleadoInformacionSucursal);
                    empleadoInformacionPersonalCollectionNewEmpleadoInformacionPersonal = em.merge(empleadoInformacionPersonalCollectionNewEmpleadoInformacionPersonal);
                    if (oldEmpleadoInformacionSucursalOfEmpleadoInformacionPersonalCollectionNewEmpleadoInformacionPersonal != null && !oldEmpleadoInformacionSucursalOfEmpleadoInformacionPersonalCollectionNewEmpleadoInformacionPersonal.equals(empleadoInformacionSucursal)) {
                        oldEmpleadoInformacionSucursalOfEmpleadoInformacionPersonalCollectionNewEmpleadoInformacionPersonal.getEmpleadoInformacionPersonalCollection().remove(empleadoInformacionPersonalCollectionNewEmpleadoInformacionPersonal);
                        oldEmpleadoInformacionSucursalOfEmpleadoInformacionPersonalCollectionNewEmpleadoInformacionPersonal = em.merge(oldEmpleadoInformacionSucursalOfEmpleadoInformacionPersonalCollectionNewEmpleadoInformacionPersonal);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = empleadoInformacionSucursal.getNoSeguroSocial();
                if (findEmpleadoInformacionSucursal(id) == null) {
                    throw new NonexistentEntityException("The empleadoInformacionSucursal with id " + id + " no longer exists.");
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
            EmpleadoInformacionSucursal empleadoInformacionSucursal;
            try {
                empleadoInformacionSucursal = em.getReference(EmpleadoInformacionSucursal.class, id);
                empleadoInformacionSucursal.getNoSeguroSocial();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The empleadoInformacionSucursal with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<EmpleadoInformacionPersonal> empleadoInformacionPersonalCollectionOrphanCheck = empleadoInformacionSucursal.getEmpleadoInformacionPersonalCollection();
            for (EmpleadoInformacionPersonal empleadoInformacionPersonalCollectionOrphanCheckEmpleadoInformacionPersonal : empleadoInformacionPersonalCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This EmpleadoInformacionSucursal (" + empleadoInformacionSucursal + ") cannot be destroyed since the EmpleadoInformacionPersonal " + empleadoInformacionPersonalCollectionOrphanCheckEmpleadoInformacionPersonal + " in its empleadoInformacionPersonalCollection field has a non-nullable empleadoInformacionSucursal field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(empleadoInformacionSucursal);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<EmpleadoInformacionSucursal> findEmpleadoInformacionSucursalEntities() {
        return findEmpleadoInformacionSucursalEntities(true, -1, -1);
    }

    public List<EmpleadoInformacionSucursal> findEmpleadoInformacionSucursalEntities(int maxResults, int firstResult) {
        return findEmpleadoInformacionSucursalEntities(false, maxResults, firstResult);
    }

    private List<EmpleadoInformacionSucursal> findEmpleadoInformacionSucursalEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(EmpleadoInformacionSucursal.class));
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

    public EmpleadoInformacionSucursal findEmpleadoInformacionSucursal(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(EmpleadoInformacionSucursal.class, id);
        } finally {
            em.close();
        }
    }

    public int getEmpleadoInformacionSucursalCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<EmpleadoInformacionSucursal> rt = cq.from(EmpleadoInformacionSucursal.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
