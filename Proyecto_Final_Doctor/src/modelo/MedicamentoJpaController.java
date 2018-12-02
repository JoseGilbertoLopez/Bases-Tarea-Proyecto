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
import modelo.Ingrediente;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.Medicamento;
import modelo.Presentacion;
import modelo.MedicamentoReceta;
import modelo.MedicamentoCompra;
import modelo.MedicamentoPK;
import modelo.SucursalMedicamento;

/**
 *
 * @author Jose G
 */
public class MedicamentoJpaController implements Serializable {

    public MedicamentoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Medicamento medicamento) throws PreexistingEntityException, Exception {
        if (medicamento.getMedicamentoPK() == null) {
            medicamento.setMedicamentoPK(new MedicamentoPK());
        }
        if (medicamento.getIngredienteCollection() == null) {
            medicamento.setIngredienteCollection(new ArrayList<Ingrediente>());
        }
        if (medicamento.getPresentacionCollection() == null) {
            medicamento.setPresentacionCollection(new ArrayList<Presentacion>());
        }
        if (medicamento.getMedicamentoRecetaCollection() == null) {
            medicamento.setMedicamentoRecetaCollection(new ArrayList<MedicamentoReceta>());
        }
        if (medicamento.getMedicamentoCompraCollection() == null) {
            medicamento.setMedicamentoCompraCollection(new ArrayList<MedicamentoCompra>());
        }
        if (medicamento.getSucursalMedicamentoCollection() == null) {
            medicamento.setSucursalMedicamentoCollection(new ArrayList<SucursalMedicamento>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Ingrediente> attachedIngredienteCollection = new ArrayList<Ingrediente>();
            for (Ingrediente ingredienteCollectionIngredienteToAttach : medicamento.getIngredienteCollection()) {
                ingredienteCollectionIngredienteToAttach = em.getReference(ingredienteCollectionIngredienteToAttach.getClass(), ingredienteCollectionIngredienteToAttach.getIdIngrediente());
                attachedIngredienteCollection.add(ingredienteCollectionIngredienteToAttach);
            }
            medicamento.setIngredienteCollection(attachedIngredienteCollection);
            Collection<Presentacion> attachedPresentacionCollection = new ArrayList<Presentacion>();
            for (Presentacion presentacionCollectionPresentacionToAttach : medicamento.getPresentacionCollection()) {
                presentacionCollectionPresentacionToAttach = em.getReference(presentacionCollectionPresentacionToAttach.getClass(), presentacionCollectionPresentacionToAttach.getIdPresentacion());
                attachedPresentacionCollection.add(presentacionCollectionPresentacionToAttach);
            }
            medicamento.setPresentacionCollection(attachedPresentacionCollection);
            Collection<MedicamentoReceta> attachedMedicamentoRecetaCollection = new ArrayList<MedicamentoReceta>();
            for (MedicamentoReceta medicamentoRecetaCollectionMedicamentoRecetaToAttach : medicamento.getMedicamentoRecetaCollection()) {
                medicamentoRecetaCollectionMedicamentoRecetaToAttach = em.getReference(medicamentoRecetaCollectionMedicamentoRecetaToAttach.getClass(), medicamentoRecetaCollectionMedicamentoRecetaToAttach.getMedicamentoRecetaPK());
                attachedMedicamentoRecetaCollection.add(medicamentoRecetaCollectionMedicamentoRecetaToAttach);
            }
            medicamento.setMedicamentoRecetaCollection(attachedMedicamentoRecetaCollection);
            Collection<MedicamentoCompra> attachedMedicamentoCompraCollection = new ArrayList<MedicamentoCompra>();
            for (MedicamentoCompra medicamentoCompraCollectionMedicamentoCompraToAttach : medicamento.getMedicamentoCompraCollection()) {
                medicamentoCompraCollectionMedicamentoCompraToAttach = em.getReference(medicamentoCompraCollectionMedicamentoCompraToAttach.getClass(), medicamentoCompraCollectionMedicamentoCompraToAttach.getMedicamentoCompraPK());
                attachedMedicamentoCompraCollection.add(medicamentoCompraCollectionMedicamentoCompraToAttach);
            }
            medicamento.setMedicamentoCompraCollection(attachedMedicamentoCompraCollection);
            Collection<SucursalMedicamento> attachedSucursalMedicamentoCollection = new ArrayList<SucursalMedicamento>();
            for (SucursalMedicamento sucursalMedicamentoCollectionSucursalMedicamentoToAttach : medicamento.getSucursalMedicamentoCollection()) {
                sucursalMedicamentoCollectionSucursalMedicamentoToAttach = em.getReference(sucursalMedicamentoCollectionSucursalMedicamentoToAttach.getClass(), sucursalMedicamentoCollectionSucursalMedicamentoToAttach.getSucursalMedicamentoPK());
                attachedSucursalMedicamentoCollection.add(sucursalMedicamentoCollectionSucursalMedicamentoToAttach);
            }
            medicamento.setSucursalMedicamentoCollection(attachedSucursalMedicamentoCollection);
            em.persist(medicamento);
            for (Ingrediente ingredienteCollectionIngrediente : medicamento.getIngredienteCollection()) {
                ingredienteCollectionIngrediente.getMedicamentoCollection().add(medicamento);
                ingredienteCollectionIngrediente = em.merge(ingredienteCollectionIngrediente);
            }
            for (Presentacion presentacionCollectionPresentacion : medicamento.getPresentacionCollection()) {
                presentacionCollectionPresentacion.getMedicamentoCollection().add(medicamento);
                presentacionCollectionPresentacion = em.merge(presentacionCollectionPresentacion);
            }
            for (MedicamentoReceta medicamentoRecetaCollectionMedicamentoReceta : medicamento.getMedicamentoRecetaCollection()) {
                Medicamento oldMedicamentoOfMedicamentoRecetaCollectionMedicamentoReceta = medicamentoRecetaCollectionMedicamentoReceta.getMedicamento();
                medicamentoRecetaCollectionMedicamentoReceta.setMedicamento(medicamento);
                medicamentoRecetaCollectionMedicamentoReceta = em.merge(medicamentoRecetaCollectionMedicamentoReceta);
                if (oldMedicamentoOfMedicamentoRecetaCollectionMedicamentoReceta != null) {
                    oldMedicamentoOfMedicamentoRecetaCollectionMedicamentoReceta.getMedicamentoRecetaCollection().remove(medicamentoRecetaCollectionMedicamentoReceta);
                    oldMedicamentoOfMedicamentoRecetaCollectionMedicamentoReceta = em.merge(oldMedicamentoOfMedicamentoRecetaCollectionMedicamentoReceta);
                }
            }
            for (MedicamentoCompra medicamentoCompraCollectionMedicamentoCompra : medicamento.getMedicamentoCompraCollection()) {
                Medicamento oldMedicamentoOfMedicamentoCompraCollectionMedicamentoCompra = medicamentoCompraCollectionMedicamentoCompra.getMedicamento();
                medicamentoCompraCollectionMedicamentoCompra.setMedicamento(medicamento);
                medicamentoCompraCollectionMedicamentoCompra = em.merge(medicamentoCompraCollectionMedicamentoCompra);
                if (oldMedicamentoOfMedicamentoCompraCollectionMedicamentoCompra != null) {
                    oldMedicamentoOfMedicamentoCompraCollectionMedicamentoCompra.getMedicamentoCompraCollection().remove(medicamentoCompraCollectionMedicamentoCompra);
                    oldMedicamentoOfMedicamentoCompraCollectionMedicamentoCompra = em.merge(oldMedicamentoOfMedicamentoCompraCollectionMedicamentoCompra);
                }
            }
            for (SucursalMedicamento sucursalMedicamentoCollectionSucursalMedicamento : medicamento.getSucursalMedicamentoCollection()) {
                Medicamento oldMedicamentoOfSucursalMedicamentoCollectionSucursalMedicamento = sucursalMedicamentoCollectionSucursalMedicamento.getMedicamento();
                sucursalMedicamentoCollectionSucursalMedicamento.setMedicamento(medicamento);
                sucursalMedicamentoCollectionSucursalMedicamento = em.merge(sucursalMedicamentoCollectionSucursalMedicamento);
                if (oldMedicamentoOfSucursalMedicamentoCollectionSucursalMedicamento != null) {
                    oldMedicamentoOfSucursalMedicamentoCollectionSucursalMedicamento.getSucursalMedicamentoCollection().remove(sucursalMedicamentoCollectionSucursalMedicamento);
                    oldMedicamentoOfSucursalMedicamentoCollectionSucursalMedicamento = em.merge(oldMedicamentoOfSucursalMedicamentoCollectionSucursalMedicamento);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findMedicamento(medicamento.getMedicamentoPK()) != null) {
                throw new PreexistingEntityException("Medicamento " + medicamento + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Medicamento medicamento) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Medicamento persistentMedicamento = em.find(Medicamento.class, medicamento.getMedicamentoPK());
            Collection<Ingrediente> ingredienteCollectionOld = persistentMedicamento.getIngredienteCollection();
            Collection<Ingrediente> ingredienteCollectionNew = medicamento.getIngredienteCollection();
            Collection<Presentacion> presentacionCollectionOld = persistentMedicamento.getPresentacionCollection();
            Collection<Presentacion> presentacionCollectionNew = medicamento.getPresentacionCollection();
            Collection<MedicamentoReceta> medicamentoRecetaCollectionOld = persistentMedicamento.getMedicamentoRecetaCollection();
            Collection<MedicamentoReceta> medicamentoRecetaCollectionNew = medicamento.getMedicamentoRecetaCollection();
            Collection<MedicamentoCompra> medicamentoCompraCollectionOld = persistentMedicamento.getMedicamentoCompraCollection();
            Collection<MedicamentoCompra> medicamentoCompraCollectionNew = medicamento.getMedicamentoCompraCollection();
            Collection<SucursalMedicamento> sucursalMedicamentoCollectionOld = persistentMedicamento.getSucursalMedicamentoCollection();
            Collection<SucursalMedicamento> sucursalMedicamentoCollectionNew = medicamento.getSucursalMedicamentoCollection();
            List<String> illegalOrphanMessages = null;
            for (MedicamentoReceta medicamentoRecetaCollectionOldMedicamentoReceta : medicamentoRecetaCollectionOld) {
                if (!medicamentoRecetaCollectionNew.contains(medicamentoRecetaCollectionOldMedicamentoReceta)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain MedicamentoReceta " + medicamentoRecetaCollectionOldMedicamentoReceta + " since its medicamento field is not nullable.");
                }
            }
            for (MedicamentoCompra medicamentoCompraCollectionOldMedicamentoCompra : medicamentoCompraCollectionOld) {
                if (!medicamentoCompraCollectionNew.contains(medicamentoCompraCollectionOldMedicamentoCompra)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain MedicamentoCompra " + medicamentoCompraCollectionOldMedicamentoCompra + " since its medicamento field is not nullable.");
                }
            }
            for (SucursalMedicamento sucursalMedicamentoCollectionOldSucursalMedicamento : sucursalMedicamentoCollectionOld) {
                if (!sucursalMedicamentoCollectionNew.contains(sucursalMedicamentoCollectionOldSucursalMedicamento)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain SucursalMedicamento " + sucursalMedicamentoCollectionOldSucursalMedicamento + " since its medicamento field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Ingrediente> attachedIngredienteCollectionNew = new ArrayList<Ingrediente>();
            for (Ingrediente ingredienteCollectionNewIngredienteToAttach : ingredienteCollectionNew) {
                ingredienteCollectionNewIngredienteToAttach = em.getReference(ingredienteCollectionNewIngredienteToAttach.getClass(), ingredienteCollectionNewIngredienteToAttach.getIdIngrediente());
                attachedIngredienteCollectionNew.add(ingredienteCollectionNewIngredienteToAttach);
            }
            ingredienteCollectionNew = attachedIngredienteCollectionNew;
            medicamento.setIngredienteCollection(ingredienteCollectionNew);
            Collection<Presentacion> attachedPresentacionCollectionNew = new ArrayList<Presentacion>();
            for (Presentacion presentacionCollectionNewPresentacionToAttach : presentacionCollectionNew) {
                presentacionCollectionNewPresentacionToAttach = em.getReference(presentacionCollectionNewPresentacionToAttach.getClass(), presentacionCollectionNewPresentacionToAttach.getIdPresentacion());
                attachedPresentacionCollectionNew.add(presentacionCollectionNewPresentacionToAttach);
            }
            presentacionCollectionNew = attachedPresentacionCollectionNew;
            medicamento.setPresentacionCollection(presentacionCollectionNew);
            Collection<MedicamentoReceta> attachedMedicamentoRecetaCollectionNew = new ArrayList<MedicamentoReceta>();
            for (MedicamentoReceta medicamentoRecetaCollectionNewMedicamentoRecetaToAttach : medicamentoRecetaCollectionNew) {
                medicamentoRecetaCollectionNewMedicamentoRecetaToAttach = em.getReference(medicamentoRecetaCollectionNewMedicamentoRecetaToAttach.getClass(), medicamentoRecetaCollectionNewMedicamentoRecetaToAttach.getMedicamentoRecetaPK());
                attachedMedicamentoRecetaCollectionNew.add(medicamentoRecetaCollectionNewMedicamentoRecetaToAttach);
            }
            medicamentoRecetaCollectionNew = attachedMedicamentoRecetaCollectionNew;
            medicamento.setMedicamentoRecetaCollection(medicamentoRecetaCollectionNew);
            Collection<MedicamentoCompra> attachedMedicamentoCompraCollectionNew = new ArrayList<MedicamentoCompra>();
            for (MedicamentoCompra medicamentoCompraCollectionNewMedicamentoCompraToAttach : medicamentoCompraCollectionNew) {
                medicamentoCompraCollectionNewMedicamentoCompraToAttach = em.getReference(medicamentoCompraCollectionNewMedicamentoCompraToAttach.getClass(), medicamentoCompraCollectionNewMedicamentoCompraToAttach.getMedicamentoCompraPK());
                attachedMedicamentoCompraCollectionNew.add(medicamentoCompraCollectionNewMedicamentoCompraToAttach);
            }
            medicamentoCompraCollectionNew = attachedMedicamentoCompraCollectionNew;
            medicamento.setMedicamentoCompraCollection(medicamentoCompraCollectionNew);
            Collection<SucursalMedicamento> attachedSucursalMedicamentoCollectionNew = new ArrayList<SucursalMedicamento>();
            for (SucursalMedicamento sucursalMedicamentoCollectionNewSucursalMedicamentoToAttach : sucursalMedicamentoCollectionNew) {
                sucursalMedicamentoCollectionNewSucursalMedicamentoToAttach = em.getReference(sucursalMedicamentoCollectionNewSucursalMedicamentoToAttach.getClass(), sucursalMedicamentoCollectionNewSucursalMedicamentoToAttach.getSucursalMedicamentoPK());
                attachedSucursalMedicamentoCollectionNew.add(sucursalMedicamentoCollectionNewSucursalMedicamentoToAttach);
            }
            sucursalMedicamentoCollectionNew = attachedSucursalMedicamentoCollectionNew;
            medicamento.setSucursalMedicamentoCollection(sucursalMedicamentoCollectionNew);
            medicamento = em.merge(medicamento);
            for (Ingrediente ingredienteCollectionOldIngrediente : ingredienteCollectionOld) {
                if (!ingredienteCollectionNew.contains(ingredienteCollectionOldIngrediente)) {
                    ingredienteCollectionOldIngrediente.getMedicamentoCollection().remove(medicamento);
                    ingredienteCollectionOldIngrediente = em.merge(ingredienteCollectionOldIngrediente);
                }
            }
            for (Ingrediente ingredienteCollectionNewIngrediente : ingredienteCollectionNew) {
                if (!ingredienteCollectionOld.contains(ingredienteCollectionNewIngrediente)) {
                    ingredienteCollectionNewIngrediente.getMedicamentoCollection().add(medicamento);
                    ingredienteCollectionNewIngrediente = em.merge(ingredienteCollectionNewIngrediente);
                }
            }
            for (Presentacion presentacionCollectionOldPresentacion : presentacionCollectionOld) {
                if (!presentacionCollectionNew.contains(presentacionCollectionOldPresentacion)) {
                    presentacionCollectionOldPresentacion.getMedicamentoCollection().remove(medicamento);
                    presentacionCollectionOldPresentacion = em.merge(presentacionCollectionOldPresentacion);
                }
            }
            for (Presentacion presentacionCollectionNewPresentacion : presentacionCollectionNew) {
                if (!presentacionCollectionOld.contains(presentacionCollectionNewPresentacion)) {
                    presentacionCollectionNewPresentacion.getMedicamentoCollection().add(medicamento);
                    presentacionCollectionNewPresentacion = em.merge(presentacionCollectionNewPresentacion);
                }
            }
            for (MedicamentoReceta medicamentoRecetaCollectionNewMedicamentoReceta : medicamentoRecetaCollectionNew) {
                if (!medicamentoRecetaCollectionOld.contains(medicamentoRecetaCollectionNewMedicamentoReceta)) {
                    Medicamento oldMedicamentoOfMedicamentoRecetaCollectionNewMedicamentoReceta = medicamentoRecetaCollectionNewMedicamentoReceta.getMedicamento();
                    medicamentoRecetaCollectionNewMedicamentoReceta.setMedicamento(medicamento);
                    medicamentoRecetaCollectionNewMedicamentoReceta = em.merge(medicamentoRecetaCollectionNewMedicamentoReceta);
                    if (oldMedicamentoOfMedicamentoRecetaCollectionNewMedicamentoReceta != null && !oldMedicamentoOfMedicamentoRecetaCollectionNewMedicamentoReceta.equals(medicamento)) {
                        oldMedicamentoOfMedicamentoRecetaCollectionNewMedicamentoReceta.getMedicamentoRecetaCollection().remove(medicamentoRecetaCollectionNewMedicamentoReceta);
                        oldMedicamentoOfMedicamentoRecetaCollectionNewMedicamentoReceta = em.merge(oldMedicamentoOfMedicamentoRecetaCollectionNewMedicamentoReceta);
                    }
                }
            }
            for (MedicamentoCompra medicamentoCompraCollectionNewMedicamentoCompra : medicamentoCompraCollectionNew) {
                if (!medicamentoCompraCollectionOld.contains(medicamentoCompraCollectionNewMedicamentoCompra)) {
                    Medicamento oldMedicamentoOfMedicamentoCompraCollectionNewMedicamentoCompra = medicamentoCompraCollectionNewMedicamentoCompra.getMedicamento();
                    medicamentoCompraCollectionNewMedicamentoCompra.setMedicamento(medicamento);
                    medicamentoCompraCollectionNewMedicamentoCompra = em.merge(medicamentoCompraCollectionNewMedicamentoCompra);
                    if (oldMedicamentoOfMedicamentoCompraCollectionNewMedicamentoCompra != null && !oldMedicamentoOfMedicamentoCompraCollectionNewMedicamentoCompra.equals(medicamento)) {
                        oldMedicamentoOfMedicamentoCompraCollectionNewMedicamentoCompra.getMedicamentoCompraCollection().remove(medicamentoCompraCollectionNewMedicamentoCompra);
                        oldMedicamentoOfMedicamentoCompraCollectionNewMedicamentoCompra = em.merge(oldMedicamentoOfMedicamentoCompraCollectionNewMedicamentoCompra);
                    }
                }
            }
            for (SucursalMedicamento sucursalMedicamentoCollectionNewSucursalMedicamento : sucursalMedicamentoCollectionNew) {
                if (!sucursalMedicamentoCollectionOld.contains(sucursalMedicamentoCollectionNewSucursalMedicamento)) {
                    Medicamento oldMedicamentoOfSucursalMedicamentoCollectionNewSucursalMedicamento = sucursalMedicamentoCollectionNewSucursalMedicamento.getMedicamento();
                    sucursalMedicamentoCollectionNewSucursalMedicamento.setMedicamento(medicamento);
                    sucursalMedicamentoCollectionNewSucursalMedicamento = em.merge(sucursalMedicamentoCollectionNewSucursalMedicamento);
                    if (oldMedicamentoOfSucursalMedicamentoCollectionNewSucursalMedicamento != null && !oldMedicamentoOfSucursalMedicamentoCollectionNewSucursalMedicamento.equals(medicamento)) {
                        oldMedicamentoOfSucursalMedicamentoCollectionNewSucursalMedicamento.getSucursalMedicamentoCollection().remove(sucursalMedicamentoCollectionNewSucursalMedicamento);
                        oldMedicamentoOfSucursalMedicamentoCollectionNewSucursalMedicamento = em.merge(oldMedicamentoOfSucursalMedicamentoCollectionNewSucursalMedicamento);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                MedicamentoPK id = medicamento.getMedicamentoPK();
                if (findMedicamento(id) == null) {
                    throw new NonexistentEntityException("The medicamento with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(MedicamentoPK id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Medicamento medicamento;
            try {
                medicamento = em.getReference(Medicamento.class, id);
                medicamento.getMedicamentoPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The medicamento with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<MedicamentoReceta> medicamentoRecetaCollectionOrphanCheck = medicamento.getMedicamentoRecetaCollection();
            for (MedicamentoReceta medicamentoRecetaCollectionOrphanCheckMedicamentoReceta : medicamentoRecetaCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Medicamento (" + medicamento + ") cannot be destroyed since the MedicamentoReceta " + medicamentoRecetaCollectionOrphanCheckMedicamentoReceta + " in its medicamentoRecetaCollection field has a non-nullable medicamento field.");
            }
            Collection<MedicamentoCompra> medicamentoCompraCollectionOrphanCheck = medicamento.getMedicamentoCompraCollection();
            for (MedicamentoCompra medicamentoCompraCollectionOrphanCheckMedicamentoCompra : medicamentoCompraCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Medicamento (" + medicamento + ") cannot be destroyed since the MedicamentoCompra " + medicamentoCompraCollectionOrphanCheckMedicamentoCompra + " in its medicamentoCompraCollection field has a non-nullable medicamento field.");
            }
            Collection<SucursalMedicamento> sucursalMedicamentoCollectionOrphanCheck = medicamento.getSucursalMedicamentoCollection();
            for (SucursalMedicamento sucursalMedicamentoCollectionOrphanCheckSucursalMedicamento : sucursalMedicamentoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Medicamento (" + medicamento + ") cannot be destroyed since the SucursalMedicamento " + sucursalMedicamentoCollectionOrphanCheckSucursalMedicamento + " in its sucursalMedicamentoCollection field has a non-nullable medicamento field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Ingrediente> ingredienteCollection = medicamento.getIngredienteCollection();
            for (Ingrediente ingredienteCollectionIngrediente : ingredienteCollection) {
                ingredienteCollectionIngrediente.getMedicamentoCollection().remove(medicamento);
                ingredienteCollectionIngrediente = em.merge(ingredienteCollectionIngrediente);
            }
            Collection<Presentacion> presentacionCollection = medicamento.getPresentacionCollection();
            for (Presentacion presentacionCollectionPresentacion : presentacionCollection) {
                presentacionCollectionPresentacion.getMedicamentoCollection().remove(medicamento);
                presentacionCollectionPresentacion = em.merge(presentacionCollectionPresentacion);
            }
            em.remove(medicamento);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Medicamento> findMedicamentoEntities() {
        return findMedicamentoEntities(true, -1, -1);
    }

    public List<Medicamento> findMedicamentoEntities(int maxResults, int firstResult) {
        return findMedicamentoEntities(false, maxResults, firstResult);
    }

    private List<Medicamento> findMedicamentoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Medicamento.class));
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

    public Medicamento findMedicamento(MedicamentoPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Medicamento.class, id);
        } finally {
            em.close();
        }
    }

    public int getMedicamentoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Medicamento> rt = cq.from(Medicamento.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
