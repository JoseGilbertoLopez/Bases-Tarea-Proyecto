/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

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
import modelo.Medicamento;
import modelo.Sucursal;
import modelo.SucursalMedicamento;
import modelo.SucursalMedicamentoPK;

/**
 *
 * @author Jose G
 */
public class SucursalMedicamentoJpaController implements Serializable {

    public SucursalMedicamentoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(SucursalMedicamento sucursalMedicamento) throws PreexistingEntityException, Exception {
        if (sucursalMedicamento.getSucursalMedicamentoPK() == null) {
            sucursalMedicamento.setSucursalMedicamentoPK(new SucursalMedicamentoPK());
        }
        //sucursalMedicamento.getSucursalMedicamentoPK().setIdSucursal(sucursalMedicamento.getSucursal().getIdSucursal());
        sucursalMedicamento.getSucursalMedicamentoPK().setNombre(sucursalMedicamento.getMedicamento().getMedicamentoPK().getNombre());
        sucursalMedicamento.getSucursalMedicamentoPK().setMarca(sucursalMedicamento.getMedicamento().getMedicamentoPK().getMarca());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Medicamento medicamento = sucursalMedicamento.getMedicamento();
            if (medicamento != null) {
                medicamento = em.getReference(medicamento.getClass(), medicamento.getMedicamentoPK());
                sucursalMedicamento.setMedicamento(medicamento);
            }
            Sucursal sucursal = sucursalMedicamento.getSucursal();
            if (sucursal != null) {
                sucursal = em.getReference(sucursal.getClass(), sucursal.getIdSucursal());
                sucursalMedicamento.setSucursal(sucursal);
            }
            em.persist(sucursalMedicamento);
            if (medicamento != null) {
                medicamento.getSucursalMedicamentoCollection().add(sucursalMedicamento);
                medicamento = em.merge(medicamento);
            }
            if (sucursal != null) {
                sucursal.getSucursalMedicamentoCollection().add(sucursalMedicamento);
                sucursal = em.merge(sucursal);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSucursalMedicamento(sucursalMedicamento.getSucursalMedicamentoPK()) != null) {
                throw new PreexistingEntityException("SucursalMedicamento " + sucursalMedicamento + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(SucursalMedicamento sucursalMedicamento) throws NonexistentEntityException, Exception {
        //sucursalMedicamento.getSucursalMedicamentoPK().setIdSucursal(sucursalMedicamento.getSucursal().getIdSucursal());
        sucursalMedicamento.getSucursalMedicamentoPK().setNombre(sucursalMedicamento.getMedicamento().getMedicamentoPK().getNombre());
        sucursalMedicamento.getSucursalMedicamentoPK().setMarca(sucursalMedicamento.getMedicamento().getMedicamentoPK().getMarca());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SucursalMedicamento persistentSucursalMedicamento = em.find(SucursalMedicamento.class, sucursalMedicamento.getSucursalMedicamentoPK());
            Medicamento medicamentoOld = persistentSucursalMedicamento.getMedicamento();
            Medicamento medicamentoNew = sucursalMedicamento.getMedicamento();
            Sucursal sucursalOld = persistentSucursalMedicamento.getSucursal();
            Sucursal sucursalNew = sucursalMedicamento.getSucursal();
            if (medicamentoNew != null) {
                medicamentoNew = em.getReference(medicamentoNew.getClass(), medicamentoNew.getMedicamentoPK());
                sucursalMedicamento.setMedicamento(medicamentoNew);
            }
            if (sucursalNew != null) {
                sucursalNew = em.getReference(sucursalNew.getClass(), sucursalNew.getIdSucursal());
                sucursalMedicamento.setSucursal(sucursalNew);
            }
            sucursalMedicamento = em.merge(sucursalMedicamento);
            if (medicamentoOld != null && !medicamentoOld.equals(medicamentoNew)) {
                medicamentoOld.getSucursalMedicamentoCollection().remove(sucursalMedicamento);
                medicamentoOld = em.merge(medicamentoOld);
            }
            if (medicamentoNew != null && !medicamentoNew.equals(medicamentoOld)) {
                medicamentoNew.getSucursalMedicamentoCollection().add(sucursalMedicamento);
                medicamentoNew = em.merge(medicamentoNew);
            }
            if (sucursalOld != null && !sucursalOld.equals(sucursalNew)) {
                sucursalOld.getSucursalMedicamentoCollection().remove(sucursalMedicamento);
                sucursalOld = em.merge(sucursalOld);
            }
            if (sucursalNew != null && !sucursalNew.equals(sucursalOld)) {
                sucursalNew.getSucursalMedicamentoCollection().add(sucursalMedicamento);
                sucursalNew = em.merge(sucursalNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                SucursalMedicamentoPK id = sucursalMedicamento.getSucursalMedicamentoPK();
                if (findSucursalMedicamento(id) == null) {
                    throw new NonexistentEntityException("The sucursalMedicamento with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(SucursalMedicamentoPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SucursalMedicamento sucursalMedicamento;
            try {
                sucursalMedicamento = em.getReference(SucursalMedicamento.class, id);
                sucursalMedicamento.getSucursalMedicamentoPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The sucursalMedicamento with id " + id + " no longer exists.", enfe);
            }
            Medicamento medicamento = sucursalMedicamento.getMedicamento();
            if (medicamento != null) {
                medicamento.getSucursalMedicamentoCollection().remove(sucursalMedicamento);
                medicamento = em.merge(medicamento);
            }
            Sucursal sucursal = sucursalMedicamento.getSucursal();
            if (sucursal != null) {
                sucursal.getSucursalMedicamentoCollection().remove(sucursalMedicamento);
                sucursal = em.merge(sucursal);
            }
            em.remove(sucursalMedicamento);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<SucursalMedicamento> findSucursalMedicamentoEntities() {
        return findSucursalMedicamentoEntities(true, -1, -1);
    }

    public List<SucursalMedicamento> findSucursalMedicamentoEntities(int maxResults, int firstResult) {
        return findSucursalMedicamentoEntities(false, maxResults, firstResult);
    }

    private List<SucursalMedicamento> findSucursalMedicamentoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(SucursalMedicamento.class));
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

    public SucursalMedicamento findSucursalMedicamento(SucursalMedicamentoPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(SucursalMedicamento.class, id);
        } finally {
            em.close();
        }
    }

    public int getSucursalMedicamentoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<SucursalMedicamento> rt = cq.from(SucursalMedicamento.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
