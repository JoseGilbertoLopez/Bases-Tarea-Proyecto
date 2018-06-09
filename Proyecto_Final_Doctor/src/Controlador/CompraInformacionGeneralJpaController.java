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
import java.math.BigDecimal;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelo.CompraInformacionSucven;
import modelo.MedicamentoCompra;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.CompraInformacionGeneral;

/**
 *
 * @author Jose G
 */
public class CompraInformacionGeneralJpaController implements Serializable {

    public CompraInformacionGeneralJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(CompraInformacionGeneral compraInformacionGeneral) throws PreexistingEntityException, Exception {
        if (compraInformacionGeneral.getMedicamentoCompraCollection() == null) {
            compraInformacionGeneral.setMedicamentoCompraCollection(new ArrayList<MedicamentoCompra>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CompraInformacionSucven vendedor = compraInformacionGeneral.getVendedor();
            if (vendedor != null) {
                vendedor = em.getReference(vendedor.getClass(), vendedor.getVendedor());
                compraInformacionGeneral.setVendedor(vendedor);
            }
            Collection<MedicamentoCompra> attachedMedicamentoCompraCollection = new ArrayList<MedicamentoCompra>();
            for (MedicamentoCompra medicamentoCompraCollectionMedicamentoCompraToAttach : compraInformacionGeneral.getMedicamentoCompraCollection()) {
                medicamentoCompraCollectionMedicamentoCompraToAttach = em.getReference(medicamentoCompraCollectionMedicamentoCompraToAttach.getClass(), medicamentoCompraCollectionMedicamentoCompraToAttach.getMedicamentoCompraPK());
                attachedMedicamentoCompraCollection.add(medicamentoCompraCollectionMedicamentoCompraToAttach);
            }
            compraInformacionGeneral.setMedicamentoCompraCollection(attachedMedicamentoCompraCollection);
            em.persist(compraInformacionGeneral);
            if (vendedor != null) {
                vendedor.getCompraInformacionGeneralCollection().add(compraInformacionGeneral);
                vendedor = em.merge(vendedor);
            }
            for (MedicamentoCompra medicamentoCompraCollectionMedicamentoCompra : compraInformacionGeneral.getMedicamentoCompraCollection()) {
                CompraInformacionGeneral oldCompraInformacionGeneralOfMedicamentoCompraCollectionMedicamentoCompra = medicamentoCompraCollectionMedicamentoCompra.getCompraInformacionGeneral();
                medicamentoCompraCollectionMedicamentoCompra.setCompraInformacionGeneral(compraInformacionGeneral);
                medicamentoCompraCollectionMedicamentoCompra = em.merge(medicamentoCompraCollectionMedicamentoCompra);
                if (oldCompraInformacionGeneralOfMedicamentoCompraCollectionMedicamentoCompra != null) {
                    oldCompraInformacionGeneralOfMedicamentoCompraCollectionMedicamentoCompra.getMedicamentoCompraCollection().remove(medicamentoCompraCollectionMedicamentoCompra);
                    oldCompraInformacionGeneralOfMedicamentoCompraCollectionMedicamentoCompra = em.merge(oldCompraInformacionGeneralOfMedicamentoCompraCollectionMedicamentoCompra);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCompraInformacionGeneral(compraInformacionGeneral.getIdCompra()) != null) {
                throw new PreexistingEntityException("CompraInformacionGeneral " + compraInformacionGeneral + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(CompraInformacionGeneral compraInformacionGeneral) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CompraInformacionGeneral persistentCompraInformacionGeneral = em.find(CompraInformacionGeneral.class, compraInformacionGeneral.getIdCompra());
            CompraInformacionSucven vendedorOld = persistentCompraInformacionGeneral.getVendedor();
            CompraInformacionSucven vendedorNew = compraInformacionGeneral.getVendedor();
            Collection<MedicamentoCompra> medicamentoCompraCollectionOld = persistentCompraInformacionGeneral.getMedicamentoCompraCollection();
            Collection<MedicamentoCompra> medicamentoCompraCollectionNew = compraInformacionGeneral.getMedicamentoCompraCollection();
            List<String> illegalOrphanMessages = null;
            for (MedicamentoCompra medicamentoCompraCollectionOldMedicamentoCompra : medicamentoCompraCollectionOld) {
                if (!medicamentoCompraCollectionNew.contains(medicamentoCompraCollectionOldMedicamentoCompra)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain MedicamentoCompra " + medicamentoCompraCollectionOldMedicamentoCompra + " since its compraInformacionGeneral field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (vendedorNew != null) {
                vendedorNew = em.getReference(vendedorNew.getClass(), vendedorNew.getVendedor());
                compraInformacionGeneral.setVendedor(vendedorNew);
            }
            Collection<MedicamentoCompra> attachedMedicamentoCompraCollectionNew = new ArrayList<MedicamentoCompra>();
            for (MedicamentoCompra medicamentoCompraCollectionNewMedicamentoCompraToAttach : medicamentoCompraCollectionNew) {
                medicamentoCompraCollectionNewMedicamentoCompraToAttach = em.getReference(medicamentoCompraCollectionNewMedicamentoCompraToAttach.getClass(), medicamentoCompraCollectionNewMedicamentoCompraToAttach.getMedicamentoCompraPK());
                attachedMedicamentoCompraCollectionNew.add(medicamentoCompraCollectionNewMedicamentoCompraToAttach);
            }
            medicamentoCompraCollectionNew = attachedMedicamentoCompraCollectionNew;
            compraInformacionGeneral.setMedicamentoCompraCollection(medicamentoCompraCollectionNew);
            compraInformacionGeneral = em.merge(compraInformacionGeneral);
            if (vendedorOld != null && !vendedorOld.equals(vendedorNew)) {
                vendedorOld.getCompraInformacionGeneralCollection().remove(compraInformacionGeneral);
                vendedorOld = em.merge(vendedorOld);
            }
            if (vendedorNew != null && !vendedorNew.equals(vendedorOld)) {
                vendedorNew.getCompraInformacionGeneralCollection().add(compraInformacionGeneral);
                vendedorNew = em.merge(vendedorNew);
            }
            for (MedicamentoCompra medicamentoCompraCollectionNewMedicamentoCompra : medicamentoCompraCollectionNew) {
                if (!medicamentoCompraCollectionOld.contains(medicamentoCompraCollectionNewMedicamentoCompra)) {
                    CompraInformacionGeneral oldCompraInformacionGeneralOfMedicamentoCompraCollectionNewMedicamentoCompra = medicamentoCompraCollectionNewMedicamentoCompra.getCompraInformacionGeneral();
                    medicamentoCompraCollectionNewMedicamentoCompra.setCompraInformacionGeneral(compraInformacionGeneral);
                    medicamentoCompraCollectionNewMedicamentoCompra = em.merge(medicamentoCompraCollectionNewMedicamentoCompra);
                    if (oldCompraInformacionGeneralOfMedicamentoCompraCollectionNewMedicamentoCompra != null && !oldCompraInformacionGeneralOfMedicamentoCompraCollectionNewMedicamentoCompra.equals(compraInformacionGeneral)) {
                        oldCompraInformacionGeneralOfMedicamentoCompraCollectionNewMedicamentoCompra.getMedicamentoCompraCollection().remove(medicamentoCompraCollectionNewMedicamentoCompra);
                        oldCompraInformacionGeneralOfMedicamentoCompraCollectionNewMedicamentoCompra = em.merge(oldCompraInformacionGeneralOfMedicamentoCompraCollectionNewMedicamentoCompra);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = compraInformacionGeneral.getIdCompra();
                if (findCompraInformacionGeneral(id) == null) {
                    throw new NonexistentEntityException("The compraInformacionGeneral with id " + id + " no longer exists.");
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
            CompraInformacionGeneral compraInformacionGeneral;
            try {
                compraInformacionGeneral = em.getReference(CompraInformacionGeneral.class, id);
                compraInformacionGeneral.getIdCompra();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The compraInformacionGeneral with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<MedicamentoCompra> medicamentoCompraCollectionOrphanCheck = compraInformacionGeneral.getMedicamentoCompraCollection();
            for (MedicamentoCompra medicamentoCompraCollectionOrphanCheckMedicamentoCompra : medicamentoCompraCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This CompraInformacionGeneral (" + compraInformacionGeneral + ") cannot be destroyed since the MedicamentoCompra " + medicamentoCompraCollectionOrphanCheckMedicamentoCompra + " in its medicamentoCompraCollection field has a non-nullable compraInformacionGeneral field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            CompraInformacionSucven vendedor = compraInformacionGeneral.getVendedor();
            if (vendedor != null) {
                vendedor.getCompraInformacionGeneralCollection().remove(compraInformacionGeneral);
                vendedor = em.merge(vendedor);
            }
            em.remove(compraInformacionGeneral);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<CompraInformacionGeneral> findCompraInformacionGeneralEntities() {
        return findCompraInformacionGeneralEntities(true, -1, -1);
    }

    public List<CompraInformacionGeneral> findCompraInformacionGeneralEntities(int maxResults, int firstResult) {
        return findCompraInformacionGeneralEntities(false, maxResults, firstResult);
    }

    private List<CompraInformacionGeneral> findCompraInformacionGeneralEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(CompraInformacionGeneral.class));
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

    public CompraInformacionGeneral findCompraInformacionGeneral(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CompraInformacionGeneral.class, id);
        } finally {
            em.close();
        }
    }

    public int getCompraInformacionGeneralCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<CompraInformacionGeneral> rt = cq.from(CompraInformacionGeneral.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
