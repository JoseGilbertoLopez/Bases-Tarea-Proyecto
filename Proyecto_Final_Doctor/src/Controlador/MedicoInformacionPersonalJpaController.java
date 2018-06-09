/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Controlador.exceptions.IllegalOrphanException;
import Controlador.exceptions.NonexistentEntityException;
import Controlador.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelo.MedicoInformacionContacto;
import modelo.MedicoInformacionSucursal;
import modelo.Especialidad;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.MedicoInformacionPersonal;
import modelo.MedicoInformacionPersonalPK;
import modelo.MedicoReceta;

/**
 *
 * @author Jose G
 */
public class MedicoInformacionPersonalJpaController implements Serializable {

    public MedicoInformacionPersonalJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(MedicoInformacionPersonal medicoInformacionPersonal) throws PreexistingEntityException, Exception {
        if (medicoInformacionPersonal.getMedicoInformacionPersonalPK() == null) {
            medicoInformacionPersonal.setMedicoInformacionPersonalPK(new MedicoInformacionPersonalPK());
        }
        if (medicoInformacionPersonal.getEspecialidadCollection() == null) {
            medicoInformacionPersonal.setEspecialidadCollection(new ArrayList<Especialidad>());
        }
        if (medicoInformacionPersonal.getMedicoRecetaCollection() == null) {
            medicoInformacionPersonal.setMedicoRecetaCollection(new ArrayList<MedicoReceta>());
        }
        medicoInformacionPersonal.getMedicoInformacionPersonalPK().setNoSeguroSocial(medicoInformacionPersonal.getMedicoInformacionSucursal().getMedicoInformacionSucursalPK().getNoSeguroSocial());
        medicoInformacionPersonal.getMedicoInformacionPersonalPK().setCedulaProfesional(medicoInformacionPersonal.getMedicoInformacionSucursal().getMedicoInformacionSucursalPK().getCedulaProfesional());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            MedicoInformacionContacto medicoInformacionContacto = medicoInformacionPersonal.getMedicoInformacionContacto();
            if (medicoInformacionContacto != null) {
                medicoInformacionContacto = em.getReference(medicoInformacionContacto.getClass(), medicoInformacionContacto.getMedicoInformacionContactoPK());
                medicoInformacionPersonal.setMedicoInformacionContacto(medicoInformacionContacto);
            }
            MedicoInformacionSucursal medicoInformacionSucursal = medicoInformacionPersonal.getMedicoInformacionSucursal();
            if (medicoInformacionSucursal != null) {
                medicoInformacionSucursal = em.getReference(medicoInformacionSucursal.getClass(), medicoInformacionSucursal.getMedicoInformacionSucursalPK());
                medicoInformacionPersonal.setMedicoInformacionSucursal(medicoInformacionSucursal);
            }
            Collection<Especialidad> attachedEspecialidadCollection = new ArrayList<Especialidad>();
            for (Especialidad especialidadCollectionEspecialidadToAttach : medicoInformacionPersonal.getEspecialidadCollection()) {
                especialidadCollectionEspecialidadToAttach = em.getReference(especialidadCollectionEspecialidadToAttach.getClass(), especialidadCollectionEspecialidadToAttach.getIdEspecialidad());
                attachedEspecialidadCollection.add(especialidadCollectionEspecialidadToAttach);
            }
            medicoInformacionPersonal.setEspecialidadCollection(attachedEspecialidadCollection);
            Collection<MedicoReceta> attachedMedicoRecetaCollection = new ArrayList<MedicoReceta>();
            for (MedicoReceta medicoRecetaCollectionMedicoRecetaToAttach : medicoInformacionPersonal.getMedicoRecetaCollection()) {
                medicoRecetaCollectionMedicoRecetaToAttach = em.getReference(medicoRecetaCollectionMedicoRecetaToAttach.getClass(), medicoRecetaCollectionMedicoRecetaToAttach.getMedicoRecetaPK());
                attachedMedicoRecetaCollection.add(medicoRecetaCollectionMedicoRecetaToAttach);
            }
            medicoInformacionPersonal.setMedicoRecetaCollection(attachedMedicoRecetaCollection);
            em.persist(medicoInformacionPersonal);
            if (medicoInformacionContacto != null) {
                medicoInformacionContacto.getMedicoInformacionPersonalCollection().add(medicoInformacionPersonal);
                medicoInformacionContacto = em.merge(medicoInformacionContacto);
            }
            if (medicoInformacionSucursal != null) {
                medicoInformacionSucursal.getMedicoInformacionPersonalCollection().add(medicoInformacionPersonal);
                medicoInformacionSucursal = em.merge(medicoInformacionSucursal);
            }
            for (Especialidad especialidadCollectionEspecialidad : medicoInformacionPersonal.getEspecialidadCollection()) {
                especialidadCollectionEspecialidad.getMedicoInformacionPersonalCollection().add(medicoInformacionPersonal);
                especialidadCollectionEspecialidad = em.merge(especialidadCollectionEspecialidad);
            }
            for (MedicoReceta medicoRecetaCollectionMedicoReceta : medicoInformacionPersonal.getMedicoRecetaCollection()) {
                MedicoInformacionPersonal oldMedicoInformacionPersonalOfMedicoRecetaCollectionMedicoReceta = medicoRecetaCollectionMedicoReceta.getMedicoInformacionPersonal();
                medicoRecetaCollectionMedicoReceta.setMedicoInformacionPersonal(medicoInformacionPersonal);
                medicoRecetaCollectionMedicoReceta = em.merge(medicoRecetaCollectionMedicoReceta);
                if (oldMedicoInformacionPersonalOfMedicoRecetaCollectionMedicoReceta != null) {
                    oldMedicoInformacionPersonalOfMedicoRecetaCollectionMedicoReceta.getMedicoRecetaCollection().remove(medicoRecetaCollectionMedicoReceta);
                    oldMedicoInformacionPersonalOfMedicoRecetaCollectionMedicoReceta = em.merge(oldMedicoInformacionPersonalOfMedicoRecetaCollectionMedicoReceta);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findMedicoInformacionPersonal(medicoInformacionPersonal.getMedicoInformacionPersonalPK()) != null) {
                throw new PreexistingEntityException("MedicoInformacionPersonal " + medicoInformacionPersonal + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(MedicoInformacionPersonal medicoInformacionPersonal) throws IllegalOrphanException, NonexistentEntityException, Exception {
        medicoInformacionPersonal.getMedicoInformacionPersonalPK().setNoSeguroSocial(medicoInformacionPersonal.getMedicoInformacionSucursal().getMedicoInformacionSucursalPK().getNoSeguroSocial());
        medicoInformacionPersonal.getMedicoInformacionPersonalPK().setCedulaProfesional(medicoInformacionPersonal.getMedicoInformacionSucursal().getMedicoInformacionSucursalPK().getCedulaProfesional());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            MedicoInformacionPersonal persistentMedicoInformacionPersonal = em.find(MedicoInformacionPersonal.class, medicoInformacionPersonal.getMedicoInformacionPersonalPK());
            MedicoInformacionContacto medicoInformacionContactoOld = persistentMedicoInformacionPersonal.getMedicoInformacionContacto();
            MedicoInformacionContacto medicoInformacionContactoNew = medicoInformacionPersonal.getMedicoInformacionContacto();
            MedicoInformacionSucursal medicoInformacionSucursalOld = persistentMedicoInformacionPersonal.getMedicoInformacionSucursal();
            MedicoInformacionSucursal medicoInformacionSucursalNew = medicoInformacionPersonal.getMedicoInformacionSucursal();
            Collection<Especialidad> especialidadCollectionOld = persistentMedicoInformacionPersonal.getEspecialidadCollection();
            Collection<Especialidad> especialidadCollectionNew = medicoInformacionPersonal.getEspecialidadCollection();
            Collection<MedicoReceta> medicoRecetaCollectionOld = persistentMedicoInformacionPersonal.getMedicoRecetaCollection();
            Collection<MedicoReceta> medicoRecetaCollectionNew = medicoInformacionPersonal.getMedicoRecetaCollection();
            List<String> illegalOrphanMessages = null;
            for (MedicoReceta medicoRecetaCollectionOldMedicoReceta : medicoRecetaCollectionOld) {
                if (!medicoRecetaCollectionNew.contains(medicoRecetaCollectionOldMedicoReceta)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain MedicoReceta " + medicoRecetaCollectionOldMedicoReceta + " since its medicoInformacionPersonal field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (medicoInformacionContactoNew != null) {
                medicoInformacionContactoNew = em.getReference(medicoInformacionContactoNew.getClass(), medicoInformacionContactoNew.getMedicoInformacionContactoPK());
                medicoInformacionPersonal.setMedicoInformacionContacto(medicoInformacionContactoNew);
            }
            if (medicoInformacionSucursalNew != null) {
                medicoInformacionSucursalNew = em.getReference(medicoInformacionSucursalNew.getClass(), medicoInformacionSucursalNew.getMedicoInformacionSucursalPK());
                medicoInformacionPersonal.setMedicoInformacionSucursal(medicoInformacionSucursalNew);
            }
            Collection<Especialidad> attachedEspecialidadCollectionNew = new ArrayList<Especialidad>();
            for (Especialidad especialidadCollectionNewEspecialidadToAttach : especialidadCollectionNew) {
                especialidadCollectionNewEspecialidadToAttach = em.getReference(especialidadCollectionNewEspecialidadToAttach.getClass(), especialidadCollectionNewEspecialidadToAttach.getIdEspecialidad());
                attachedEspecialidadCollectionNew.add(especialidadCollectionNewEspecialidadToAttach);
            }
            especialidadCollectionNew = attachedEspecialidadCollectionNew;
            medicoInformacionPersonal.setEspecialidadCollection(especialidadCollectionNew);
            Collection<MedicoReceta> attachedMedicoRecetaCollectionNew = new ArrayList<MedicoReceta>();
            for (MedicoReceta medicoRecetaCollectionNewMedicoRecetaToAttach : medicoRecetaCollectionNew) {
                medicoRecetaCollectionNewMedicoRecetaToAttach = em.getReference(medicoRecetaCollectionNewMedicoRecetaToAttach.getClass(), medicoRecetaCollectionNewMedicoRecetaToAttach.getMedicoRecetaPK());
                attachedMedicoRecetaCollectionNew.add(medicoRecetaCollectionNewMedicoRecetaToAttach);
            }
            medicoRecetaCollectionNew = attachedMedicoRecetaCollectionNew;
            medicoInformacionPersonal.setMedicoRecetaCollection(medicoRecetaCollectionNew);
            medicoInformacionPersonal = em.merge(medicoInformacionPersonal);
            if (medicoInformacionContactoOld != null && !medicoInformacionContactoOld.equals(medicoInformacionContactoNew)) {
                medicoInformacionContactoOld.getMedicoInformacionPersonalCollection().remove(medicoInformacionPersonal);
                medicoInformacionContactoOld = em.merge(medicoInformacionContactoOld);
            }
            if (medicoInformacionContactoNew != null && !medicoInformacionContactoNew.equals(medicoInformacionContactoOld)) {
                medicoInformacionContactoNew.getMedicoInformacionPersonalCollection().add(medicoInformacionPersonal);
                medicoInformacionContactoNew = em.merge(medicoInformacionContactoNew);
            }
            if (medicoInformacionSucursalOld != null && !medicoInformacionSucursalOld.equals(medicoInformacionSucursalNew)) {
                medicoInformacionSucursalOld.getMedicoInformacionPersonalCollection().remove(medicoInformacionPersonal);
                medicoInformacionSucursalOld = em.merge(medicoInformacionSucursalOld);
            }
            if (medicoInformacionSucursalNew != null && !medicoInformacionSucursalNew.equals(medicoInformacionSucursalOld)) {
                medicoInformacionSucursalNew.getMedicoInformacionPersonalCollection().add(medicoInformacionPersonal);
                medicoInformacionSucursalNew = em.merge(medicoInformacionSucursalNew);
            }
            for (Especialidad especialidadCollectionOldEspecialidad : especialidadCollectionOld) {
                if (!especialidadCollectionNew.contains(especialidadCollectionOldEspecialidad)) {
                    especialidadCollectionOldEspecialidad.getMedicoInformacionPersonalCollection().remove(medicoInformacionPersonal);
                    especialidadCollectionOldEspecialidad = em.merge(especialidadCollectionOldEspecialidad);
                }
            }
            for (Especialidad especialidadCollectionNewEspecialidad : especialidadCollectionNew) {
                if (!especialidadCollectionOld.contains(especialidadCollectionNewEspecialidad)) {
                    especialidadCollectionNewEspecialidad.getMedicoInformacionPersonalCollection().add(medicoInformacionPersonal);
                    especialidadCollectionNewEspecialidad = em.merge(especialidadCollectionNewEspecialidad);
                }
            }
            for (MedicoReceta medicoRecetaCollectionNewMedicoReceta : medicoRecetaCollectionNew) {
                if (!medicoRecetaCollectionOld.contains(medicoRecetaCollectionNewMedicoReceta)) {
                    MedicoInformacionPersonal oldMedicoInformacionPersonalOfMedicoRecetaCollectionNewMedicoReceta = medicoRecetaCollectionNewMedicoReceta.getMedicoInformacionPersonal();
                    medicoRecetaCollectionNewMedicoReceta.setMedicoInformacionPersonal(medicoInformacionPersonal);
                    medicoRecetaCollectionNewMedicoReceta = em.merge(medicoRecetaCollectionNewMedicoReceta);
                    if (oldMedicoInformacionPersonalOfMedicoRecetaCollectionNewMedicoReceta != null && !oldMedicoInformacionPersonalOfMedicoRecetaCollectionNewMedicoReceta.equals(medicoInformacionPersonal)) {
                        oldMedicoInformacionPersonalOfMedicoRecetaCollectionNewMedicoReceta.getMedicoRecetaCollection().remove(medicoRecetaCollectionNewMedicoReceta);
                        oldMedicoInformacionPersonalOfMedicoRecetaCollectionNewMedicoReceta = em.merge(oldMedicoInformacionPersonalOfMedicoRecetaCollectionNewMedicoReceta);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                MedicoInformacionPersonalPK id = medicoInformacionPersonal.getMedicoInformacionPersonalPK();
                if (findMedicoInformacionPersonal(id) == null) {
                    throw new NonexistentEntityException("The medicoInformacionPersonal with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(MedicoInformacionPersonalPK id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            MedicoInformacionPersonal medicoInformacionPersonal;
            try {
                medicoInformacionPersonal = em.getReference(MedicoInformacionPersonal.class, id);
                medicoInformacionPersonal.getMedicoInformacionPersonalPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The medicoInformacionPersonal with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<MedicoReceta> medicoRecetaCollectionOrphanCheck = medicoInformacionPersonal.getMedicoRecetaCollection();
            for (MedicoReceta medicoRecetaCollectionOrphanCheckMedicoReceta : medicoRecetaCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This MedicoInformacionPersonal (" + medicoInformacionPersonal + ") cannot be destroyed since the MedicoReceta " + medicoRecetaCollectionOrphanCheckMedicoReceta + " in its medicoRecetaCollection field has a non-nullable medicoInformacionPersonal field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            MedicoInformacionContacto medicoInformacionContacto = medicoInformacionPersonal.getMedicoInformacionContacto();
            if (medicoInformacionContacto != null) {
                medicoInformacionContacto.getMedicoInformacionPersonalCollection().remove(medicoInformacionPersonal);
                medicoInformacionContacto = em.merge(medicoInformacionContacto);
            }
            MedicoInformacionSucursal medicoInformacionSucursal = medicoInformacionPersonal.getMedicoInformacionSucursal();
            if (medicoInformacionSucursal != null) {
                medicoInformacionSucursal.getMedicoInformacionPersonalCollection().remove(medicoInformacionPersonal);
                medicoInformacionSucursal = em.merge(medicoInformacionSucursal);
            }
            Collection<Especialidad> especialidadCollection = medicoInformacionPersonal.getEspecialidadCollection();
            for (Especialidad especialidadCollectionEspecialidad : especialidadCollection) {
                especialidadCollectionEspecialidad.getMedicoInformacionPersonalCollection().remove(medicoInformacionPersonal);
                especialidadCollectionEspecialidad = em.merge(especialidadCollectionEspecialidad);
            }
            em.remove(medicoInformacionPersonal);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<MedicoInformacionPersonal> findMedicoInformacionPersonalEntities() {
        return findMedicoInformacionPersonalEntities(true, -1, -1);
    }

    public List<MedicoInformacionPersonal> findMedicoInformacionPersonalEntities(int maxResults, int firstResult) {
        return findMedicoInformacionPersonalEntities(false, maxResults, firstResult);
    }

    private List<MedicoInformacionPersonal> findMedicoInformacionPersonalEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(MedicoInformacionPersonal.class));
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

    public MedicoInformacionPersonal findMedicoInformacionPersonal(MedicoInformacionPersonalPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(MedicoInformacionPersonal.class, id);
        } finally {
            em.close();
        }
    }

    public int getMedicoInformacionPersonalCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<MedicoInformacionPersonal> rt = cq.from(MedicoInformacionPersonal.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
