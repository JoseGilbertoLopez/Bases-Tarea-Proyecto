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
import modelo.EmpleadoInformacionContacto;
import modelo.EmpleadoInformacionSucursal;
import modelo.EmpleadoSucursal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.EmpleadoInformacionPersonal;
import modelo.EmpleadoInformacionPersonalPK;

/**
 *
 * @author Jose G
 */
public class EmpleadoInformacionPersonalJpaController implements Serializable {

    public EmpleadoInformacionPersonalJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(EmpleadoInformacionPersonal empleadoInformacionPersonal) throws PreexistingEntityException, Exception {
        if (empleadoInformacionPersonal.getEmpleadoInformacionPersonalPK() == null) {
            empleadoInformacionPersonal.setEmpleadoInformacionPersonalPK(new EmpleadoInformacionPersonalPK());
        }
        if (empleadoInformacionPersonal.getEmpleadoSucursalCollection() == null) {
            empleadoInformacionPersonal.setEmpleadoSucursalCollection(new ArrayList<EmpleadoSucursal>());
        }
        empleadoInformacionPersonal.getEmpleadoInformacionPersonalPK().setRfc(empleadoInformacionPersonal.getEmpleadoInformacionContacto().getEmpleadoInformacionContactoPK().getRfc());
        empleadoInformacionPersonal.getEmpleadoInformacionPersonalPK().setNoSeguroSocial(empleadoInformacionPersonal.getEmpleadoInformacionSucursal().getNoSeguroSocial());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            EmpleadoInformacionContacto empleadoInformacionContacto = empleadoInformacionPersonal.getEmpleadoInformacionContacto();
            if (empleadoInformacionContacto != null) {
                empleadoInformacionContacto = em.getReference(empleadoInformacionContacto.getClass(), empleadoInformacionContacto.getEmpleadoInformacionContactoPK());
                empleadoInformacionPersonal.setEmpleadoInformacionContacto(empleadoInformacionContacto);
            }
            EmpleadoInformacionSucursal empleadoInformacionSucursal = empleadoInformacionPersonal.getEmpleadoInformacionSucursal();
            if (empleadoInformacionSucursal != null) {
                empleadoInformacionSucursal = em.getReference(empleadoInformacionSucursal.getClass(), empleadoInformacionSucursal.getNoSeguroSocial());
                empleadoInformacionPersonal.setEmpleadoInformacionSucursal(empleadoInformacionSucursal);
            }
            Collection<EmpleadoSucursal> attachedEmpleadoSucursalCollection = new ArrayList<EmpleadoSucursal>();
            for (EmpleadoSucursal empleadoSucursalCollectionEmpleadoSucursalToAttach : empleadoInformacionPersonal.getEmpleadoSucursalCollection()) {
                empleadoSucursalCollectionEmpleadoSucursalToAttach = em.getReference(empleadoSucursalCollectionEmpleadoSucursalToAttach.getClass(), empleadoSucursalCollectionEmpleadoSucursalToAttach.getEmpleadoSucursalPK());
                attachedEmpleadoSucursalCollection.add(empleadoSucursalCollectionEmpleadoSucursalToAttach);
            }
            empleadoInformacionPersonal.setEmpleadoSucursalCollection(attachedEmpleadoSucursalCollection);
            em.persist(empleadoInformacionPersonal);
            if (empleadoInformacionContacto != null) {
                empleadoInformacionContacto.getEmpleadoInformacionPersonalCollection().add(empleadoInformacionPersonal);
                empleadoInformacionContacto = em.merge(empleadoInformacionContacto);
            }
            if (empleadoInformacionSucursal != null) {
                empleadoInformacionSucursal.getEmpleadoInformacionPersonalCollection().add(empleadoInformacionPersonal);
                empleadoInformacionSucursal = em.merge(empleadoInformacionSucursal);
            }
            for (EmpleadoSucursal empleadoSucursalCollectionEmpleadoSucursal : empleadoInformacionPersonal.getEmpleadoSucursalCollection()) {
                EmpleadoInformacionPersonal oldEmpleadoInformacionPersonalOfEmpleadoSucursalCollectionEmpleadoSucursal = empleadoSucursalCollectionEmpleadoSucursal.getEmpleadoInformacionPersonal();
                empleadoSucursalCollectionEmpleadoSucursal.setEmpleadoInformacionPersonal(empleadoInformacionPersonal);
                empleadoSucursalCollectionEmpleadoSucursal = em.merge(empleadoSucursalCollectionEmpleadoSucursal);
                if (oldEmpleadoInformacionPersonalOfEmpleadoSucursalCollectionEmpleadoSucursal != null) {
                    oldEmpleadoInformacionPersonalOfEmpleadoSucursalCollectionEmpleadoSucursal.getEmpleadoSucursalCollection().remove(empleadoSucursalCollectionEmpleadoSucursal);
                    oldEmpleadoInformacionPersonalOfEmpleadoSucursalCollectionEmpleadoSucursal = em.merge(oldEmpleadoInformacionPersonalOfEmpleadoSucursalCollectionEmpleadoSucursal);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findEmpleadoInformacionPersonal(empleadoInformacionPersonal.getEmpleadoInformacionPersonalPK()) != null) {
                throw new PreexistingEntityException("EmpleadoInformacionPersonal " + empleadoInformacionPersonal + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(EmpleadoInformacionPersonal empleadoInformacionPersonal) throws IllegalOrphanException, NonexistentEntityException, Exception {
        empleadoInformacionPersonal.getEmpleadoInformacionPersonalPK().setRfc(empleadoInformacionPersonal.getEmpleadoInformacionContacto().getEmpleadoInformacionContactoPK().getRfc());
        empleadoInformacionPersonal.getEmpleadoInformacionPersonalPK().setNoSeguroSocial(empleadoInformacionPersonal.getEmpleadoInformacionSucursal().getNoSeguroSocial());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            EmpleadoInformacionPersonal persistentEmpleadoInformacionPersonal = em.find(EmpleadoInformacionPersonal.class, empleadoInformacionPersonal.getEmpleadoInformacionPersonalPK());
            EmpleadoInformacionContacto empleadoInformacionContactoOld = persistentEmpleadoInformacionPersonal.getEmpleadoInformacionContacto();
            EmpleadoInformacionContacto empleadoInformacionContactoNew = empleadoInformacionPersonal.getEmpleadoInformacionContacto();
            EmpleadoInformacionSucursal empleadoInformacionSucursalOld = persistentEmpleadoInformacionPersonal.getEmpleadoInformacionSucursal();
            EmpleadoInformacionSucursal empleadoInformacionSucursalNew = empleadoInformacionPersonal.getEmpleadoInformacionSucursal();
            Collection<EmpleadoSucursal> empleadoSucursalCollectionOld = persistentEmpleadoInformacionPersonal.getEmpleadoSucursalCollection();
            Collection<EmpleadoSucursal> empleadoSucursalCollectionNew = empleadoInformacionPersonal.getEmpleadoSucursalCollection();
            List<String> illegalOrphanMessages = null;
            for (EmpleadoSucursal empleadoSucursalCollectionOldEmpleadoSucursal : empleadoSucursalCollectionOld) {
                if (!empleadoSucursalCollectionNew.contains(empleadoSucursalCollectionOldEmpleadoSucursal)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain EmpleadoSucursal " + empleadoSucursalCollectionOldEmpleadoSucursal + " since its empleadoInformacionPersonal field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (empleadoInformacionContactoNew != null) {
                empleadoInformacionContactoNew = em.getReference(empleadoInformacionContactoNew.getClass(), empleadoInformacionContactoNew.getEmpleadoInformacionContactoPK());
                empleadoInformacionPersonal.setEmpleadoInformacionContacto(empleadoInformacionContactoNew);
            }
            if (empleadoInformacionSucursalNew != null) {
                empleadoInformacionSucursalNew = em.getReference(empleadoInformacionSucursalNew.getClass(), empleadoInformacionSucursalNew.getNoSeguroSocial());
                empleadoInformacionPersonal.setEmpleadoInformacionSucursal(empleadoInformacionSucursalNew);
            }
            Collection<EmpleadoSucursal> attachedEmpleadoSucursalCollectionNew = new ArrayList<EmpleadoSucursal>();
            for (EmpleadoSucursal empleadoSucursalCollectionNewEmpleadoSucursalToAttach : empleadoSucursalCollectionNew) {
                empleadoSucursalCollectionNewEmpleadoSucursalToAttach = em.getReference(empleadoSucursalCollectionNewEmpleadoSucursalToAttach.getClass(), empleadoSucursalCollectionNewEmpleadoSucursalToAttach.getEmpleadoSucursalPK());
                attachedEmpleadoSucursalCollectionNew.add(empleadoSucursalCollectionNewEmpleadoSucursalToAttach);
            }
            empleadoSucursalCollectionNew = attachedEmpleadoSucursalCollectionNew;
            empleadoInformacionPersonal.setEmpleadoSucursalCollection(empleadoSucursalCollectionNew);
            empleadoInformacionPersonal = em.merge(empleadoInformacionPersonal);
            if (empleadoInformacionContactoOld != null && !empleadoInformacionContactoOld.equals(empleadoInformacionContactoNew)) {
                empleadoInformacionContactoOld.getEmpleadoInformacionPersonalCollection().remove(empleadoInformacionPersonal);
                empleadoInformacionContactoOld = em.merge(empleadoInformacionContactoOld);
            }
            if (empleadoInformacionContactoNew != null && !empleadoInformacionContactoNew.equals(empleadoInformacionContactoOld)) {
                empleadoInformacionContactoNew.getEmpleadoInformacionPersonalCollection().add(empleadoInformacionPersonal);
                empleadoInformacionContactoNew = em.merge(empleadoInformacionContactoNew);
            }
            if (empleadoInformacionSucursalOld != null && !empleadoInformacionSucursalOld.equals(empleadoInformacionSucursalNew)) {
                empleadoInformacionSucursalOld.getEmpleadoInformacionPersonalCollection().remove(empleadoInformacionPersonal);
                empleadoInformacionSucursalOld = em.merge(empleadoInformacionSucursalOld);
            }
            if (empleadoInformacionSucursalNew != null && !empleadoInformacionSucursalNew.equals(empleadoInformacionSucursalOld)) {
                empleadoInformacionSucursalNew.getEmpleadoInformacionPersonalCollection().add(empleadoInformacionPersonal);
                empleadoInformacionSucursalNew = em.merge(empleadoInformacionSucursalNew);
            }
            for (EmpleadoSucursal empleadoSucursalCollectionNewEmpleadoSucursal : empleadoSucursalCollectionNew) {
                if (!empleadoSucursalCollectionOld.contains(empleadoSucursalCollectionNewEmpleadoSucursal)) {
                    EmpleadoInformacionPersonal oldEmpleadoInformacionPersonalOfEmpleadoSucursalCollectionNewEmpleadoSucursal = empleadoSucursalCollectionNewEmpleadoSucursal.getEmpleadoInformacionPersonal();
                    empleadoSucursalCollectionNewEmpleadoSucursal.setEmpleadoInformacionPersonal(empleadoInformacionPersonal);
                    empleadoSucursalCollectionNewEmpleadoSucursal = em.merge(empleadoSucursalCollectionNewEmpleadoSucursal);
                    if (oldEmpleadoInformacionPersonalOfEmpleadoSucursalCollectionNewEmpleadoSucursal != null && !oldEmpleadoInformacionPersonalOfEmpleadoSucursalCollectionNewEmpleadoSucursal.equals(empleadoInformacionPersonal)) {
                        oldEmpleadoInformacionPersonalOfEmpleadoSucursalCollectionNewEmpleadoSucursal.getEmpleadoSucursalCollection().remove(empleadoSucursalCollectionNewEmpleadoSucursal);
                        oldEmpleadoInformacionPersonalOfEmpleadoSucursalCollectionNewEmpleadoSucursal = em.merge(oldEmpleadoInformacionPersonalOfEmpleadoSucursalCollectionNewEmpleadoSucursal);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                EmpleadoInformacionPersonalPK id = empleadoInformacionPersonal.getEmpleadoInformacionPersonalPK();
                if (findEmpleadoInformacionPersonal(id) == null) {
                    throw new NonexistentEntityException("The empleadoInformacionPersonal with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(EmpleadoInformacionPersonalPK id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            EmpleadoInformacionPersonal empleadoInformacionPersonal;
            try {
                empleadoInformacionPersonal = em.getReference(EmpleadoInformacionPersonal.class, id);
                empleadoInformacionPersonal.getEmpleadoInformacionPersonalPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The empleadoInformacionPersonal with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<EmpleadoSucursal> empleadoSucursalCollectionOrphanCheck = empleadoInformacionPersonal.getEmpleadoSucursalCollection();
            for (EmpleadoSucursal empleadoSucursalCollectionOrphanCheckEmpleadoSucursal : empleadoSucursalCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This EmpleadoInformacionPersonal (" + empleadoInformacionPersonal + ") cannot be destroyed since the EmpleadoSucursal " + empleadoSucursalCollectionOrphanCheckEmpleadoSucursal + " in its empleadoSucursalCollection field has a non-nullable empleadoInformacionPersonal field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            EmpleadoInformacionContacto empleadoInformacionContacto = empleadoInformacionPersonal.getEmpleadoInformacionContacto();
            if (empleadoInformacionContacto != null) {
                empleadoInformacionContacto.getEmpleadoInformacionPersonalCollection().remove(empleadoInformacionPersonal);
                empleadoInformacionContacto = em.merge(empleadoInformacionContacto);
            }
            EmpleadoInformacionSucursal empleadoInformacionSucursal = empleadoInformacionPersonal.getEmpleadoInformacionSucursal();
            if (empleadoInformacionSucursal != null) {
                empleadoInformacionSucursal.getEmpleadoInformacionPersonalCollection().remove(empleadoInformacionPersonal);
                empleadoInformacionSucursal = em.merge(empleadoInformacionSucursal);
            }
            em.remove(empleadoInformacionPersonal);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<EmpleadoInformacionPersonal> findEmpleadoInformacionPersonalEntities() {
        return findEmpleadoInformacionPersonalEntities(true, -1, -1);
    }

    public List<EmpleadoInformacionPersonal> findEmpleadoInformacionPersonalEntities(int maxResults, int firstResult) {
        return findEmpleadoInformacionPersonalEntities(false, maxResults, firstResult);
    }

    private List<EmpleadoInformacionPersonal> findEmpleadoInformacionPersonalEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(EmpleadoInformacionPersonal.class));
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

    public EmpleadoInformacionPersonal findEmpleadoInformacionPersonal(EmpleadoInformacionPersonalPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(EmpleadoInformacionPersonal.class, id);
        } finally {
            em.close();
        }
    }

    public int getEmpleadoInformacionPersonalCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<EmpleadoInformacionPersonal> rt = cq.from(EmpleadoInformacionPersonal.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
