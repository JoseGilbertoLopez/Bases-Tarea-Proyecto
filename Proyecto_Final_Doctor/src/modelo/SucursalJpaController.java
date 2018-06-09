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
import java.math.BigDecimal;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelo.TelefonoSucursal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import modelo.EmpleadoSucursal;
import modelo.Sucursal;
import modelo.SucursalMedicamento;

/**
 *
 * @author Jose G
 */
public class SucursalJpaController implements Serializable {

    /**
     * Cambiamos por la unidad de percistencia del proyecto.
     * @param emf 
     */
<<<<<<< HEAD
    public SucursalJpaController(EntityManagerFactory emf) {
        this.emf = emf;
=======
    public SucursalJpaController() {
        this.emf = Persistence.createEntityManagerFactory("Proyecto_Final_DoctorPU") ;
>>>>>>> 8371d5b425d6093cb27155109521731abb54d505
    }
    private EntityManagerFactory emf = null;

    public SucursalJpaController() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Sucursal sucursal) throws PreexistingEntityException, Exception {
        if (sucursal.getTelefonoSucursalCollection() == null) {
            sucursal.setTelefonoSucursalCollection(new ArrayList<TelefonoSucursal>());
        }
        if (sucursal.getEmpleadoSucursalCollection() == null) {
            sucursal.setEmpleadoSucursalCollection(new ArrayList<EmpleadoSucursal>());
        }
        if (sucursal.getSucursalMedicamentoCollection() == null) {
            sucursal.setSucursalMedicamentoCollection(new ArrayList<SucursalMedicamento>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<TelefonoSucursal> attachedTelefonoSucursalCollection = new ArrayList<TelefonoSucursal>();
            for (TelefonoSucursal telefonoSucursalCollectionTelefonoSucursalToAttach : sucursal.getTelefonoSucursalCollection()) {
                telefonoSucursalCollectionTelefonoSucursalToAttach = em.getReference(telefonoSucursalCollectionTelefonoSucursalToAttach.getClass(), telefonoSucursalCollectionTelefonoSucursalToAttach.getTelefonoSucursalPK());
                attachedTelefonoSucursalCollection.add(telefonoSucursalCollectionTelefonoSucursalToAttach);
            }
            sucursal.setTelefonoSucursalCollection(attachedTelefonoSucursalCollection);
            Collection<EmpleadoSucursal> attachedEmpleadoSucursalCollection = new ArrayList<EmpleadoSucursal>();
            for (EmpleadoSucursal empleadoSucursalCollectionEmpleadoSucursalToAttach : sucursal.getEmpleadoSucursalCollection()) {
                empleadoSucursalCollectionEmpleadoSucursalToAttach = em.getReference(empleadoSucursalCollectionEmpleadoSucursalToAttach.getClass(), empleadoSucursalCollectionEmpleadoSucursalToAttach.getEmpleadoSucursalPK());
                attachedEmpleadoSucursalCollection.add(empleadoSucursalCollectionEmpleadoSucursalToAttach);
            }
            sucursal.setEmpleadoSucursalCollection(attachedEmpleadoSucursalCollection);
            Collection<SucursalMedicamento> attachedSucursalMedicamentoCollection = new ArrayList<SucursalMedicamento>();
            for (SucursalMedicamento sucursalMedicamentoCollectionSucursalMedicamentoToAttach : sucursal.getSucursalMedicamentoCollection()) {
                sucursalMedicamentoCollectionSucursalMedicamentoToAttach = em.getReference(sucursalMedicamentoCollectionSucursalMedicamentoToAttach.getClass(), sucursalMedicamentoCollectionSucursalMedicamentoToAttach.getSucursalMedicamentoPK());
                attachedSucursalMedicamentoCollection.add(sucursalMedicamentoCollectionSucursalMedicamentoToAttach);
            }
            sucursal.setSucursalMedicamentoCollection(attachedSucursalMedicamentoCollection);
            em.persist(sucursal);
            for (TelefonoSucursal telefonoSucursalCollectionTelefonoSucursal : sucursal.getTelefonoSucursalCollection()) {
                Sucursal oldSucursalOfTelefonoSucursalCollectionTelefonoSucursal = telefonoSucursalCollectionTelefonoSucursal.getSucursal();
                telefonoSucursalCollectionTelefonoSucursal.setSucursal(sucursal);
                telefonoSucursalCollectionTelefonoSucursal = em.merge(telefonoSucursalCollectionTelefonoSucursal);
                if (oldSucursalOfTelefonoSucursalCollectionTelefonoSucursal != null) {
                    oldSucursalOfTelefonoSucursalCollectionTelefonoSucursal.getTelefonoSucursalCollection().remove(telefonoSucursalCollectionTelefonoSucursal);
                    oldSucursalOfTelefonoSucursalCollectionTelefonoSucursal = em.merge(oldSucursalOfTelefonoSucursalCollectionTelefonoSucursal);
                }
            }
            for (EmpleadoSucursal empleadoSucursalCollectionEmpleadoSucursal : sucursal.getEmpleadoSucursalCollection()) {
                Sucursal oldSucursalOfEmpleadoSucursalCollectionEmpleadoSucursal = empleadoSucursalCollectionEmpleadoSucursal.getSucursal();
                empleadoSucursalCollectionEmpleadoSucursal.setSucursal(sucursal);
                empleadoSucursalCollectionEmpleadoSucursal = em.merge(empleadoSucursalCollectionEmpleadoSucursal);
                if (oldSucursalOfEmpleadoSucursalCollectionEmpleadoSucursal != null) {
                    oldSucursalOfEmpleadoSucursalCollectionEmpleadoSucursal.getEmpleadoSucursalCollection().remove(empleadoSucursalCollectionEmpleadoSucursal);
                    oldSucursalOfEmpleadoSucursalCollectionEmpleadoSucursal = em.merge(oldSucursalOfEmpleadoSucursalCollectionEmpleadoSucursal);
                }
            }
            for (SucursalMedicamento sucursalMedicamentoCollectionSucursalMedicamento : sucursal.getSucursalMedicamentoCollection()) {
                Sucursal oldSucursalOfSucursalMedicamentoCollectionSucursalMedicamento = sucursalMedicamentoCollectionSucursalMedicamento.getSucursal();
                sucursalMedicamentoCollectionSucursalMedicamento.setSucursal(sucursal);
                sucursalMedicamentoCollectionSucursalMedicamento = em.merge(sucursalMedicamentoCollectionSucursalMedicamento);
                if (oldSucursalOfSucursalMedicamentoCollectionSucursalMedicamento != null) {
                    oldSucursalOfSucursalMedicamentoCollectionSucursalMedicamento.getSucursalMedicamentoCollection().remove(sucursalMedicamentoCollectionSucursalMedicamento);
                    oldSucursalOfSucursalMedicamentoCollectionSucursalMedicamento = em.merge(oldSucursalOfSucursalMedicamentoCollectionSucursalMedicamento);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSucursal(sucursal.getIdSucursal()) != null) {
                throw new PreexistingEntityException("Sucursal " + sucursal + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Sucursal sucursal) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Sucursal persistentSucursal = em.find(Sucursal.class, sucursal.getIdSucursal());
            Collection<TelefonoSucursal> telefonoSucursalCollectionOld = persistentSucursal.getTelefonoSucursalCollection();
            Collection<TelefonoSucursal> telefonoSucursalCollectionNew = sucursal.getTelefonoSucursalCollection();
            Collection<EmpleadoSucursal> empleadoSucursalCollectionOld = persistentSucursal.getEmpleadoSucursalCollection();
            Collection<EmpleadoSucursal> empleadoSucursalCollectionNew = sucursal.getEmpleadoSucursalCollection();
            Collection<SucursalMedicamento> sucursalMedicamentoCollectionOld = persistentSucursal.getSucursalMedicamentoCollection();
            Collection<SucursalMedicamento> sucursalMedicamentoCollectionNew = sucursal.getSucursalMedicamentoCollection();
            List<String> illegalOrphanMessages = null;
            for (TelefonoSucursal telefonoSucursalCollectionOldTelefonoSucursal : telefonoSucursalCollectionOld) {
                if (!telefonoSucursalCollectionNew.contains(telefonoSucursalCollectionOldTelefonoSucursal)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain TelefonoSucursal " + telefonoSucursalCollectionOldTelefonoSucursal + " since its sucursal field is not nullable.");
                }
            }
            for (EmpleadoSucursal empleadoSucursalCollectionOldEmpleadoSucursal : empleadoSucursalCollectionOld) {
                if (!empleadoSucursalCollectionNew.contains(empleadoSucursalCollectionOldEmpleadoSucursal)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain EmpleadoSucursal " + empleadoSucursalCollectionOldEmpleadoSucursal + " since its sucursal field is not nullable.");
                }
            }
            for (SucursalMedicamento sucursalMedicamentoCollectionOldSucursalMedicamento : sucursalMedicamentoCollectionOld) {
                if (!sucursalMedicamentoCollectionNew.contains(sucursalMedicamentoCollectionOldSucursalMedicamento)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain SucursalMedicamento " + sucursalMedicamentoCollectionOldSucursalMedicamento + " since its sucursal field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<TelefonoSucursal> attachedTelefonoSucursalCollectionNew = new ArrayList<TelefonoSucursal>();
            for (TelefonoSucursal telefonoSucursalCollectionNewTelefonoSucursalToAttach : telefonoSucursalCollectionNew) {
                telefonoSucursalCollectionNewTelefonoSucursalToAttach = em.getReference(telefonoSucursalCollectionNewTelefonoSucursalToAttach.getClass(), telefonoSucursalCollectionNewTelefonoSucursalToAttach.getTelefonoSucursalPK());
                attachedTelefonoSucursalCollectionNew.add(telefonoSucursalCollectionNewTelefonoSucursalToAttach);
            }
            telefonoSucursalCollectionNew = attachedTelefonoSucursalCollectionNew;
            sucursal.setTelefonoSucursalCollection(telefonoSucursalCollectionNew);
            Collection<EmpleadoSucursal> attachedEmpleadoSucursalCollectionNew = new ArrayList<EmpleadoSucursal>();
            for (EmpleadoSucursal empleadoSucursalCollectionNewEmpleadoSucursalToAttach : empleadoSucursalCollectionNew) {
                empleadoSucursalCollectionNewEmpleadoSucursalToAttach = em.getReference(empleadoSucursalCollectionNewEmpleadoSucursalToAttach.getClass(), empleadoSucursalCollectionNewEmpleadoSucursalToAttach.getEmpleadoSucursalPK());
                attachedEmpleadoSucursalCollectionNew.add(empleadoSucursalCollectionNewEmpleadoSucursalToAttach);
            }
            empleadoSucursalCollectionNew = attachedEmpleadoSucursalCollectionNew;
            sucursal.setEmpleadoSucursalCollection(empleadoSucursalCollectionNew);
            Collection<SucursalMedicamento> attachedSucursalMedicamentoCollectionNew = new ArrayList<SucursalMedicamento>();
            for (SucursalMedicamento sucursalMedicamentoCollectionNewSucursalMedicamentoToAttach : sucursalMedicamentoCollectionNew) {
                sucursalMedicamentoCollectionNewSucursalMedicamentoToAttach = em.getReference(sucursalMedicamentoCollectionNewSucursalMedicamentoToAttach.getClass(), sucursalMedicamentoCollectionNewSucursalMedicamentoToAttach.getSucursalMedicamentoPK());
                attachedSucursalMedicamentoCollectionNew.add(sucursalMedicamentoCollectionNewSucursalMedicamentoToAttach);
            }
            sucursalMedicamentoCollectionNew = attachedSucursalMedicamentoCollectionNew;
            sucursal.setSucursalMedicamentoCollection(sucursalMedicamentoCollectionNew);
            sucursal = em.merge(sucursal);
            for (TelefonoSucursal telefonoSucursalCollectionNewTelefonoSucursal : telefonoSucursalCollectionNew) {
                if (!telefonoSucursalCollectionOld.contains(telefonoSucursalCollectionNewTelefonoSucursal)) {
                    Sucursal oldSucursalOfTelefonoSucursalCollectionNewTelefonoSucursal = telefonoSucursalCollectionNewTelefonoSucursal.getSucursal();
                    telefonoSucursalCollectionNewTelefonoSucursal.setSucursal(sucursal);
                    telefonoSucursalCollectionNewTelefonoSucursal = em.merge(telefonoSucursalCollectionNewTelefonoSucursal);
                    if (oldSucursalOfTelefonoSucursalCollectionNewTelefonoSucursal != null && !oldSucursalOfTelefonoSucursalCollectionNewTelefonoSucursal.equals(sucursal)) {
                        oldSucursalOfTelefonoSucursalCollectionNewTelefonoSucursal.getTelefonoSucursalCollection().remove(telefonoSucursalCollectionNewTelefonoSucursal);
                        oldSucursalOfTelefonoSucursalCollectionNewTelefonoSucursal = em.merge(oldSucursalOfTelefonoSucursalCollectionNewTelefonoSucursal);
                    }
                }
            }
            for (EmpleadoSucursal empleadoSucursalCollectionNewEmpleadoSucursal : empleadoSucursalCollectionNew) {
                if (!empleadoSucursalCollectionOld.contains(empleadoSucursalCollectionNewEmpleadoSucursal)) {
                    Sucursal oldSucursalOfEmpleadoSucursalCollectionNewEmpleadoSucursal = empleadoSucursalCollectionNewEmpleadoSucursal.getSucursal();
                    empleadoSucursalCollectionNewEmpleadoSucursal.setSucursal(sucursal);
                    empleadoSucursalCollectionNewEmpleadoSucursal = em.merge(empleadoSucursalCollectionNewEmpleadoSucursal);
                    if (oldSucursalOfEmpleadoSucursalCollectionNewEmpleadoSucursal != null && !oldSucursalOfEmpleadoSucursalCollectionNewEmpleadoSucursal.equals(sucursal)) {
                        oldSucursalOfEmpleadoSucursalCollectionNewEmpleadoSucursal.getEmpleadoSucursalCollection().remove(empleadoSucursalCollectionNewEmpleadoSucursal);
                        oldSucursalOfEmpleadoSucursalCollectionNewEmpleadoSucursal = em.merge(oldSucursalOfEmpleadoSucursalCollectionNewEmpleadoSucursal);
                    }
                }
            }
            for (SucursalMedicamento sucursalMedicamentoCollectionNewSucursalMedicamento : sucursalMedicamentoCollectionNew) {
                if (!sucursalMedicamentoCollectionOld.contains(sucursalMedicamentoCollectionNewSucursalMedicamento)) {
                    Sucursal oldSucursalOfSucursalMedicamentoCollectionNewSucursalMedicamento = sucursalMedicamentoCollectionNewSucursalMedicamento.getSucursal();
                    sucursalMedicamentoCollectionNewSucursalMedicamento.setSucursal(sucursal);
                    sucursalMedicamentoCollectionNewSucursalMedicamento = em.merge(sucursalMedicamentoCollectionNewSucursalMedicamento);
                    if (oldSucursalOfSucursalMedicamentoCollectionNewSucursalMedicamento != null && !oldSucursalOfSucursalMedicamentoCollectionNewSucursalMedicamento.equals(sucursal)) {
                        oldSucursalOfSucursalMedicamentoCollectionNewSucursalMedicamento.getSucursalMedicamentoCollection().remove(sucursalMedicamentoCollectionNewSucursalMedicamento);
                        oldSucursalOfSucursalMedicamentoCollectionNewSucursalMedicamento = em.merge(oldSucursalOfSucursalMedicamentoCollectionNewSucursalMedicamento);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = sucursal.getIdSucursal();
                if (findSucursal(id) == null) {
                    throw new NonexistentEntityException("The sucursal with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(BigDecimal id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Sucursal sucursal;
            try {
                sucursal = em.getReference(Sucursal.class, id);
                sucursal.getIdSucursal();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The sucursal with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<TelefonoSucursal> telefonoSucursalCollectionOrphanCheck = sucursal.getTelefonoSucursalCollection();
            for (TelefonoSucursal telefonoSucursalCollectionOrphanCheckTelefonoSucursal : telefonoSucursalCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Sucursal (" + sucursal + ") cannot be destroyed since the TelefonoSucursal " + telefonoSucursalCollectionOrphanCheckTelefonoSucursal + " in its telefonoSucursalCollection field has a non-nullable sucursal field.");
            }
            Collection<EmpleadoSucursal> empleadoSucursalCollectionOrphanCheck = sucursal.getEmpleadoSucursalCollection();
            for (EmpleadoSucursal empleadoSucursalCollectionOrphanCheckEmpleadoSucursal : empleadoSucursalCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Sucursal (" + sucursal + ") cannot be destroyed since the EmpleadoSucursal " + empleadoSucursalCollectionOrphanCheckEmpleadoSucursal + " in its empleadoSucursalCollection field has a non-nullable sucursal field.");
            }
            Collection<SucursalMedicamento> sucursalMedicamentoCollectionOrphanCheck = sucursal.getSucursalMedicamentoCollection();
            for (SucursalMedicamento sucursalMedicamentoCollectionOrphanCheckSucursalMedicamento : sucursalMedicamentoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Sucursal (" + sucursal + ") cannot be destroyed since the SucursalMedicamento " + sucursalMedicamentoCollectionOrphanCheckSucursalMedicamento + " in its sucursalMedicamentoCollection field has a non-nullable sucursal field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(sucursal);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Sucursal> findSucursalEntities() {
        return findSucursalEntities(true, -1, -1);
    }

    public List<Sucursal> findSucursalEntities(int maxResults, int firstResult) {
        return findSucursalEntities(false, maxResults, firstResult);
    }

    private List<Sucursal> findSucursalEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Sucursal.class));
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

    public Sucursal findSucursal(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Sucursal.class, id);
        } finally {
            em.close();
        }
    }

    public int getSucursalCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Sucursal> rt = cq.from(Sucursal.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
