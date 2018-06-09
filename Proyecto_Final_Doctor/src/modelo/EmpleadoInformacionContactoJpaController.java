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
import modelo.EmpleadoInformacionContacto;
import modelo.EmpleadoInformacionContactoPK;

/**
 *
 * @author Jose G
 */
public class EmpleadoInformacionContactoJpaController implements Serializable {

    public EmpleadoInformacionContactoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(EmpleadoInformacionContacto empleadoInformacionContacto) throws PreexistingEntityException, Exception {
        if (empleadoInformacionContacto.getEmpleadoInformacionContactoPK() == null) {
            empleadoInformacionContacto.setEmpleadoInformacionContactoPK(new EmpleadoInformacionContactoPK());
        }
        if (empleadoInformacionContacto.getEmpleadoInformacionPersonalCollection() == null) {
            empleadoInformacionContacto.setEmpleadoInformacionPersonalCollection(new ArrayList<EmpleadoInformacionPersonal>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<EmpleadoInformacionPersonal> attachedEmpleadoInformacionPersonalCollection = new ArrayList<EmpleadoInformacionPersonal>();
            for (EmpleadoInformacionPersonal empleadoInformacionPersonalCollectionEmpleadoInformacionPersonalToAttach : empleadoInformacionContacto.getEmpleadoInformacionPersonalCollection()) {
                empleadoInformacionPersonalCollectionEmpleadoInformacionPersonalToAttach = em.getReference(empleadoInformacionPersonalCollectionEmpleadoInformacionPersonalToAttach.getClass(), empleadoInformacionPersonalCollectionEmpleadoInformacionPersonalToAttach.getEmpleadoInformacionPersonalPK());
                attachedEmpleadoInformacionPersonalCollection.add(empleadoInformacionPersonalCollectionEmpleadoInformacionPersonalToAttach);
            }
            empleadoInformacionContacto.setEmpleadoInformacionPersonalCollection(attachedEmpleadoInformacionPersonalCollection);
            em.persist(empleadoInformacionContacto);
            for (EmpleadoInformacionPersonal empleadoInformacionPersonalCollectionEmpleadoInformacionPersonal : empleadoInformacionContacto.getEmpleadoInformacionPersonalCollection()) {
                EmpleadoInformacionContacto oldEmpleadoInformacionContactoOfEmpleadoInformacionPersonalCollectionEmpleadoInformacionPersonal = empleadoInformacionPersonalCollectionEmpleadoInformacionPersonal.getEmpleadoInformacionContacto();
                empleadoInformacionPersonalCollectionEmpleadoInformacionPersonal.setEmpleadoInformacionContacto(empleadoInformacionContacto);
                empleadoInformacionPersonalCollectionEmpleadoInformacionPersonal = em.merge(empleadoInformacionPersonalCollectionEmpleadoInformacionPersonal);
                if (oldEmpleadoInformacionContactoOfEmpleadoInformacionPersonalCollectionEmpleadoInformacionPersonal != null) {
                    oldEmpleadoInformacionContactoOfEmpleadoInformacionPersonalCollectionEmpleadoInformacionPersonal.getEmpleadoInformacionPersonalCollection().remove(empleadoInformacionPersonalCollectionEmpleadoInformacionPersonal);
                    oldEmpleadoInformacionContactoOfEmpleadoInformacionPersonalCollectionEmpleadoInformacionPersonal = em.merge(oldEmpleadoInformacionContactoOfEmpleadoInformacionPersonalCollectionEmpleadoInformacionPersonal);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findEmpleadoInformacionContacto(empleadoInformacionContacto.getEmpleadoInformacionContactoPK()) != null) {
                throw new PreexistingEntityException("EmpleadoInformacionContacto " + empleadoInformacionContacto + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(EmpleadoInformacionContacto empleadoInformacionContacto) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            EmpleadoInformacionContacto persistentEmpleadoInformacionContacto = em.find(EmpleadoInformacionContacto.class, empleadoInformacionContacto.getEmpleadoInformacionContactoPK());
            Collection<EmpleadoInformacionPersonal> empleadoInformacionPersonalCollectionOld = persistentEmpleadoInformacionContacto.getEmpleadoInformacionPersonalCollection();
            Collection<EmpleadoInformacionPersonal> empleadoInformacionPersonalCollectionNew = empleadoInformacionContacto.getEmpleadoInformacionPersonalCollection();
            List<String> illegalOrphanMessages = null;
            for (EmpleadoInformacionPersonal empleadoInformacionPersonalCollectionOldEmpleadoInformacionPersonal : empleadoInformacionPersonalCollectionOld) {
                if (!empleadoInformacionPersonalCollectionNew.contains(empleadoInformacionPersonalCollectionOldEmpleadoInformacionPersonal)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain EmpleadoInformacionPersonal " + empleadoInformacionPersonalCollectionOldEmpleadoInformacionPersonal + " since its empleadoInformacionContacto field is not nullable.");
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
            empleadoInformacionContacto.setEmpleadoInformacionPersonalCollection(empleadoInformacionPersonalCollectionNew);
            empleadoInformacionContacto = em.merge(empleadoInformacionContacto);
            for (EmpleadoInformacionPersonal empleadoInformacionPersonalCollectionNewEmpleadoInformacionPersonal : empleadoInformacionPersonalCollectionNew) {
                if (!empleadoInformacionPersonalCollectionOld.contains(empleadoInformacionPersonalCollectionNewEmpleadoInformacionPersonal)) {
                    EmpleadoInformacionContacto oldEmpleadoInformacionContactoOfEmpleadoInformacionPersonalCollectionNewEmpleadoInformacionPersonal = empleadoInformacionPersonalCollectionNewEmpleadoInformacionPersonal.getEmpleadoInformacionContacto();
                    empleadoInformacionPersonalCollectionNewEmpleadoInformacionPersonal.setEmpleadoInformacionContacto(empleadoInformacionContacto);
                    empleadoInformacionPersonalCollectionNewEmpleadoInformacionPersonal = em.merge(empleadoInformacionPersonalCollectionNewEmpleadoInformacionPersonal);
                    if (oldEmpleadoInformacionContactoOfEmpleadoInformacionPersonalCollectionNewEmpleadoInformacionPersonal != null && !oldEmpleadoInformacionContactoOfEmpleadoInformacionPersonalCollectionNewEmpleadoInformacionPersonal.equals(empleadoInformacionContacto)) {
                        oldEmpleadoInformacionContactoOfEmpleadoInformacionPersonalCollectionNewEmpleadoInformacionPersonal.getEmpleadoInformacionPersonalCollection().remove(empleadoInformacionPersonalCollectionNewEmpleadoInformacionPersonal);
                        oldEmpleadoInformacionContactoOfEmpleadoInformacionPersonalCollectionNewEmpleadoInformacionPersonal = em.merge(oldEmpleadoInformacionContactoOfEmpleadoInformacionPersonalCollectionNewEmpleadoInformacionPersonal);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                EmpleadoInformacionContactoPK id = empleadoInformacionContacto.getEmpleadoInformacionContactoPK();
                if (findEmpleadoInformacionContacto(id) == null) {
                    throw new NonexistentEntityException("The empleadoInformacionContacto with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(EmpleadoInformacionContactoPK id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            EmpleadoInformacionContacto empleadoInformacionContacto;
            try {
                empleadoInformacionContacto = em.getReference(EmpleadoInformacionContacto.class, id);
                empleadoInformacionContacto.getEmpleadoInformacionContactoPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The empleadoInformacionContacto with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<EmpleadoInformacionPersonal> empleadoInformacionPersonalCollectionOrphanCheck = empleadoInformacionContacto.getEmpleadoInformacionPersonalCollection();
            for (EmpleadoInformacionPersonal empleadoInformacionPersonalCollectionOrphanCheckEmpleadoInformacionPersonal : empleadoInformacionPersonalCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This EmpleadoInformacionContacto (" + empleadoInformacionContacto + ") cannot be destroyed since the EmpleadoInformacionPersonal " + empleadoInformacionPersonalCollectionOrphanCheckEmpleadoInformacionPersonal + " in its empleadoInformacionPersonalCollection field has a non-nullable empleadoInformacionContacto field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(empleadoInformacionContacto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<EmpleadoInformacionContacto> findEmpleadoInformacionContactoEntities() {
        return findEmpleadoInformacionContactoEntities(true, -1, -1);
    }

    public List<EmpleadoInformacionContacto> findEmpleadoInformacionContactoEntities(int maxResults, int firstResult) {
        return findEmpleadoInformacionContactoEntities(false, maxResults, firstResult);
    }

    private List<EmpleadoInformacionContacto> findEmpleadoInformacionContactoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(EmpleadoInformacionContacto.class));
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

    public EmpleadoInformacionContacto findEmpleadoInformacionContacto(EmpleadoInformacionContactoPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(EmpleadoInformacionContacto.class, id);
        } finally {
            em.close();
        }
    }

    public int getEmpleadoInformacionContactoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<EmpleadoInformacionContacto> rt = cq.from(EmpleadoInformacionContacto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
