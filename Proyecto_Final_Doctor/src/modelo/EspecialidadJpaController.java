/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import Controlador.exceptions.NonexistentEntityException;
import Controlador.exceptions.PreexistingEntityException;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelo.MedicoInformacionPersonal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.Especialidad;

/**
 *
 * @author Jose G
 */
public class EspecialidadJpaController implements Serializable {

    public EspecialidadJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Especialidad especialidad) throws PreexistingEntityException, Exception {
        if (especialidad.getMedicoInformacionPersonalCollection() == null) {
            especialidad.setMedicoInformacionPersonalCollection(new ArrayList<MedicoInformacionPersonal>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<MedicoInformacionPersonal> attachedMedicoInformacionPersonalCollection = new ArrayList<MedicoInformacionPersonal>();
            for (MedicoInformacionPersonal medicoInformacionPersonalCollectionMedicoInformacionPersonalToAttach : especialidad.getMedicoInformacionPersonalCollection()) {
                medicoInformacionPersonalCollectionMedicoInformacionPersonalToAttach = em.getReference(medicoInformacionPersonalCollectionMedicoInformacionPersonalToAttach.getClass(), medicoInformacionPersonalCollectionMedicoInformacionPersonalToAttach.getMedicoInformacionPersonalPK());
                attachedMedicoInformacionPersonalCollection.add(medicoInformacionPersonalCollectionMedicoInformacionPersonalToAttach);
            }
            especialidad.setMedicoInformacionPersonalCollection(attachedMedicoInformacionPersonalCollection);
            em.persist(especialidad);
            for (MedicoInformacionPersonal medicoInformacionPersonalCollectionMedicoInformacionPersonal : especialidad.getMedicoInformacionPersonalCollection()) {
                medicoInformacionPersonalCollectionMedicoInformacionPersonal.getEspecialidadCollection().add(especialidad);
                medicoInformacionPersonalCollectionMedicoInformacionPersonal = em.merge(medicoInformacionPersonalCollectionMedicoInformacionPersonal);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findEspecialidad(especialidad.getIdEspecialidad()) != null) {
                throw new PreexistingEntityException("Especialidad " + especialidad + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Especialidad especialidad) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Especialidad persistentEspecialidad = em.find(Especialidad.class, especialidad.getIdEspecialidad());
            Collection<MedicoInformacionPersonal> medicoInformacionPersonalCollectionOld = persistentEspecialidad.getMedicoInformacionPersonalCollection();
            Collection<MedicoInformacionPersonal> medicoInformacionPersonalCollectionNew = especialidad.getMedicoInformacionPersonalCollection();
            Collection<MedicoInformacionPersonal> attachedMedicoInformacionPersonalCollectionNew = new ArrayList<MedicoInformacionPersonal>();
            for (MedicoInformacionPersonal medicoInformacionPersonalCollectionNewMedicoInformacionPersonalToAttach : medicoInformacionPersonalCollectionNew) {
                medicoInformacionPersonalCollectionNewMedicoInformacionPersonalToAttach = em.getReference(medicoInformacionPersonalCollectionNewMedicoInformacionPersonalToAttach.getClass(), medicoInformacionPersonalCollectionNewMedicoInformacionPersonalToAttach.getMedicoInformacionPersonalPK());
                attachedMedicoInformacionPersonalCollectionNew.add(medicoInformacionPersonalCollectionNewMedicoInformacionPersonalToAttach);
            }
            medicoInformacionPersonalCollectionNew = attachedMedicoInformacionPersonalCollectionNew;
            especialidad.setMedicoInformacionPersonalCollection(medicoInformacionPersonalCollectionNew);
            especialidad = em.merge(especialidad);
            for (MedicoInformacionPersonal medicoInformacionPersonalCollectionOldMedicoInformacionPersonal : medicoInformacionPersonalCollectionOld) {
                if (!medicoInformacionPersonalCollectionNew.contains(medicoInformacionPersonalCollectionOldMedicoInformacionPersonal)) {
                    medicoInformacionPersonalCollectionOldMedicoInformacionPersonal.getEspecialidadCollection().remove(especialidad);
                    medicoInformacionPersonalCollectionOldMedicoInformacionPersonal = em.merge(medicoInformacionPersonalCollectionOldMedicoInformacionPersonal);
                }
            }
            for (MedicoInformacionPersonal medicoInformacionPersonalCollectionNewMedicoInformacionPersonal : medicoInformacionPersonalCollectionNew) {
                if (!medicoInformacionPersonalCollectionOld.contains(medicoInformacionPersonalCollectionNewMedicoInformacionPersonal)) {
                    medicoInformacionPersonalCollectionNewMedicoInformacionPersonal.getEspecialidadCollection().add(especialidad);
                    medicoInformacionPersonalCollectionNewMedicoInformacionPersonal = em.merge(medicoInformacionPersonalCollectionNewMedicoInformacionPersonal);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = especialidad.getIdEspecialidad();
                if (findEspecialidad(id) == null) {
                    throw new NonexistentEntityException("The especialidad with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(BigDecimal id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Especialidad especialidad;
            try {
                especialidad = em.getReference(Especialidad.class, id);
                especialidad.getIdEspecialidad();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The especialidad with id " + id + " no longer exists.", enfe);
            }
            Collection<MedicoInformacionPersonal> medicoInformacionPersonalCollection = especialidad.getMedicoInformacionPersonalCollection();
            for (MedicoInformacionPersonal medicoInformacionPersonalCollectionMedicoInformacionPersonal : medicoInformacionPersonalCollection) {
                medicoInformacionPersonalCollectionMedicoInformacionPersonal.getEspecialidadCollection().remove(especialidad);
                medicoInformacionPersonalCollectionMedicoInformacionPersonal = em.merge(medicoInformacionPersonalCollectionMedicoInformacionPersonal);
            }
            em.remove(especialidad);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Especialidad> findEspecialidadEntities() {
        return findEspecialidadEntities(true, -1, -1);
    }

    public List<Especialidad> findEspecialidadEntities(int maxResults, int firstResult) {
        return findEspecialidadEntities(false, maxResults, firstResult);
    }

    private List<Especialidad> findEspecialidadEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Especialidad.class));
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

    public Especialidad findEspecialidad(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Especialidad.class, id);
        } finally {
            em.close();
        }
    }

    public int getEspecialidadCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Especialidad> rt = cq.from(Especialidad.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
