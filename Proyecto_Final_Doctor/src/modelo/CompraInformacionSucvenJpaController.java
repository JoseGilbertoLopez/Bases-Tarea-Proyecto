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
import modelo.CompraInformacionGeneral;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.CompraInformacionSucven;

/**
 *
 * @author Jose G
 */
public class CompraInformacionSucvenJpaController implements Serializable {

    public CompraInformacionSucvenJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(CompraInformacionSucven compraInformacionSucven) throws PreexistingEntityException, Exception {
        if (compraInformacionSucven.getCompraInformacionGeneralCollection() == null) {
            compraInformacionSucven.setCompraInformacionGeneralCollection(new ArrayList<CompraInformacionGeneral>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<CompraInformacionGeneral> attachedCompraInformacionGeneralCollection = new ArrayList<CompraInformacionGeneral>();
            for (CompraInformacionGeneral compraInformacionGeneralCollectionCompraInformacionGeneralToAttach : compraInformacionSucven.getCompraInformacionGeneralCollection()) {
                compraInformacionGeneralCollectionCompraInformacionGeneralToAttach = em.getReference(compraInformacionGeneralCollectionCompraInformacionGeneralToAttach.getClass(), compraInformacionGeneralCollectionCompraInformacionGeneralToAttach.getIdCompra());
                attachedCompraInformacionGeneralCollection.add(compraInformacionGeneralCollectionCompraInformacionGeneralToAttach);
            }
            compraInformacionSucven.setCompraInformacionGeneralCollection(attachedCompraInformacionGeneralCollection);
            em.persist(compraInformacionSucven);
            for (CompraInformacionGeneral compraInformacionGeneralCollectionCompraInformacionGeneral : compraInformacionSucven.getCompraInformacionGeneralCollection()) {
                CompraInformacionSucven oldVendedorOfCompraInformacionGeneralCollectionCompraInformacionGeneral = compraInformacionGeneralCollectionCompraInformacionGeneral.getVendedor();
                compraInformacionGeneralCollectionCompraInformacionGeneral.setVendedor(compraInformacionSucven);
                compraInformacionGeneralCollectionCompraInformacionGeneral = em.merge(compraInformacionGeneralCollectionCompraInformacionGeneral);
                if (oldVendedorOfCompraInformacionGeneralCollectionCompraInformacionGeneral != null) {
                    oldVendedorOfCompraInformacionGeneralCollectionCompraInformacionGeneral.getCompraInformacionGeneralCollection().remove(compraInformacionGeneralCollectionCompraInformacionGeneral);
                    oldVendedorOfCompraInformacionGeneralCollectionCompraInformacionGeneral = em.merge(oldVendedorOfCompraInformacionGeneralCollectionCompraInformacionGeneral);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCompraInformacionSucven(compraInformacionSucven.getVendedor()) != null) {
                throw new PreexistingEntityException("CompraInformacionSucven " + compraInformacionSucven + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(CompraInformacionSucven compraInformacionSucven) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CompraInformacionSucven persistentCompraInformacionSucven = em.find(CompraInformacionSucven.class, compraInformacionSucven.getVendedor());
            Collection<CompraInformacionGeneral> compraInformacionGeneralCollectionOld = persistentCompraInformacionSucven.getCompraInformacionGeneralCollection();
            Collection<CompraInformacionGeneral> compraInformacionGeneralCollectionNew = compraInformacionSucven.getCompraInformacionGeneralCollection();
            List<String> illegalOrphanMessages = null;
            for (CompraInformacionGeneral compraInformacionGeneralCollectionOldCompraInformacionGeneral : compraInformacionGeneralCollectionOld) {
                if (!compraInformacionGeneralCollectionNew.contains(compraInformacionGeneralCollectionOldCompraInformacionGeneral)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain CompraInformacionGeneral " + compraInformacionGeneralCollectionOldCompraInformacionGeneral + " since its vendedor field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<CompraInformacionGeneral> attachedCompraInformacionGeneralCollectionNew = new ArrayList<CompraInformacionGeneral>();
            for (CompraInformacionGeneral compraInformacionGeneralCollectionNewCompraInformacionGeneralToAttach : compraInformacionGeneralCollectionNew) {
                compraInformacionGeneralCollectionNewCompraInformacionGeneralToAttach = em.getReference(compraInformacionGeneralCollectionNewCompraInformacionGeneralToAttach.getClass(), compraInformacionGeneralCollectionNewCompraInformacionGeneralToAttach.getIdCompra());
                attachedCompraInformacionGeneralCollectionNew.add(compraInformacionGeneralCollectionNewCompraInformacionGeneralToAttach);
            }
            compraInformacionGeneralCollectionNew = attachedCompraInformacionGeneralCollectionNew;
            compraInformacionSucven.setCompraInformacionGeneralCollection(compraInformacionGeneralCollectionNew);
            compraInformacionSucven = em.merge(compraInformacionSucven);
            for (CompraInformacionGeneral compraInformacionGeneralCollectionNewCompraInformacionGeneral : compraInformacionGeneralCollectionNew) {
                if (!compraInformacionGeneralCollectionOld.contains(compraInformacionGeneralCollectionNewCompraInformacionGeneral)) {
                    CompraInformacionSucven oldVendedorOfCompraInformacionGeneralCollectionNewCompraInformacionGeneral = compraInformacionGeneralCollectionNewCompraInformacionGeneral.getVendedor();
                    compraInformacionGeneralCollectionNewCompraInformacionGeneral.setVendedor(compraInformacionSucven);
                    compraInformacionGeneralCollectionNewCompraInformacionGeneral = em.merge(compraInformacionGeneralCollectionNewCompraInformacionGeneral);
                    if (oldVendedorOfCompraInformacionGeneralCollectionNewCompraInformacionGeneral != null && !oldVendedorOfCompraInformacionGeneralCollectionNewCompraInformacionGeneral.equals(compraInformacionSucven)) {
                        oldVendedorOfCompraInformacionGeneralCollectionNewCompraInformacionGeneral.getCompraInformacionGeneralCollection().remove(compraInformacionGeneralCollectionNewCompraInformacionGeneral);
                        oldVendedorOfCompraInformacionGeneralCollectionNewCompraInformacionGeneral = em.merge(oldVendedorOfCompraInformacionGeneralCollectionNewCompraInformacionGeneral);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = compraInformacionSucven.getVendedor();
                if (findCompraInformacionSucven(id) == null) {
                    throw new NonexistentEntityException("The compraInformacionSucven with id " + id + " no longer exists.");
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
            CompraInformacionSucven compraInformacionSucven;
            try {
                compraInformacionSucven = em.getReference(CompraInformacionSucven.class, id);
                compraInformacionSucven.getVendedor();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The compraInformacionSucven with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<CompraInformacionGeneral> compraInformacionGeneralCollectionOrphanCheck = compraInformacionSucven.getCompraInformacionGeneralCollection();
            for (CompraInformacionGeneral compraInformacionGeneralCollectionOrphanCheckCompraInformacionGeneral : compraInformacionGeneralCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This CompraInformacionSucven (" + compraInformacionSucven + ") cannot be destroyed since the CompraInformacionGeneral " + compraInformacionGeneralCollectionOrphanCheckCompraInformacionGeneral + " in its compraInformacionGeneralCollection field has a non-nullable vendedor field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(compraInformacionSucven);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<CompraInformacionSucven> findCompraInformacionSucvenEntities() {
        return findCompraInformacionSucvenEntities(true, -1, -1);
    }

    public List<CompraInformacionSucven> findCompraInformacionSucvenEntities(int maxResults, int firstResult) {
        return findCompraInformacionSucvenEntities(false, maxResults, firstResult);
    }

    private List<CompraInformacionSucven> findCompraInformacionSucvenEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(CompraInformacionSucven.class));
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

    public CompraInformacionSucven findCompraInformacionSucven(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CompraInformacionSucven.class, id);
        } finally {
            em.close();
        }
    }

    public int getCompraInformacionSucvenCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<CompraInformacionSucven> rt = cq.from(CompraInformacionSucven.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
