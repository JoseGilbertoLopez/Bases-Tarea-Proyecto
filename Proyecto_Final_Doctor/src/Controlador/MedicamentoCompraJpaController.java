/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

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
import modelo.CompraInformacionGeneral;
import modelo.Medicamento;
import modelo.MedicamentoCompra;
import modelo.MedicamentoCompraPK;

/**
 *
 * @author Jose G
 */
public class MedicamentoCompraJpaController implements Serializable {

    public MedicamentoCompraJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(MedicamentoCompra medicamentoCompra) throws PreexistingEntityException, Exception {
        if (medicamentoCompra.getMedicamentoCompraPK() == null) {
            medicamentoCompra.setMedicamentoCompraPK(new MedicamentoCompraPK());
        }
        medicamentoCompra.getMedicamentoCompraPK().setNombre(medicamentoCompra.getMedicamento().getMedicamentoPK().getNombre());
        medicamentoCompra.getMedicamentoCompraPK().setMarca(medicamentoCompra.getMedicamento().getMedicamentoPK().getMarca());
        //medicamentoCompra.getMedicamentoCompraPK().setIdCompra(medicamentoCompra.getCompraInformacionGeneral().getIdCompra());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CompraInformacionGeneral compraInformacionGeneral = medicamentoCompra.getCompraInformacionGeneral();
            if (compraInformacionGeneral != null) {
                compraInformacionGeneral = em.getReference(compraInformacionGeneral.getClass(), compraInformacionGeneral.getIdCompra());
                medicamentoCompra.setCompraInformacionGeneral(compraInformacionGeneral);
            }
            Medicamento medicamento = medicamentoCompra.getMedicamento();
            if (medicamento != null) {
                medicamento = em.getReference(medicamento.getClass(), medicamento.getMedicamentoPK());
                medicamentoCompra.setMedicamento(medicamento);
            }
            em.persist(medicamentoCompra);
            if (compraInformacionGeneral != null) {
                compraInformacionGeneral.getMedicamentoCompraCollection().add(medicamentoCompra);
                compraInformacionGeneral = em.merge(compraInformacionGeneral);
            }
            if (medicamento != null) {
                medicamento.getMedicamentoCompraCollection().add(medicamentoCompra);
                medicamento = em.merge(medicamento);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findMedicamentoCompra(medicamentoCompra.getMedicamentoCompraPK()) != null) {
                throw new PreexistingEntityException("MedicamentoCompra " + medicamentoCompra + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(MedicamentoCompra medicamentoCompra) throws NonexistentEntityException, Exception {
        medicamentoCompra.getMedicamentoCompraPK().setNombre(medicamentoCompra.getMedicamento().getMedicamentoPK().getNombre());
        medicamentoCompra.getMedicamentoCompraPK().setMarca(medicamentoCompra.getMedicamento().getMedicamentoPK().getMarca());
        //medicamentoCompra.getMedicamentoCompraPK().setIdCompra(medicamentoCompra.getCompraInformacionGeneral().getIdCompra());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            MedicamentoCompra persistentMedicamentoCompra = em.find(MedicamentoCompra.class, medicamentoCompra.getMedicamentoCompraPK());
            CompraInformacionGeneral compraInformacionGeneralOld = persistentMedicamentoCompra.getCompraInformacionGeneral();
            CompraInformacionGeneral compraInformacionGeneralNew = medicamentoCompra.getCompraInformacionGeneral();
            Medicamento medicamentoOld = persistentMedicamentoCompra.getMedicamento();
            Medicamento medicamentoNew = medicamentoCompra.getMedicamento();
            if (compraInformacionGeneralNew != null) {
                compraInformacionGeneralNew = em.getReference(compraInformacionGeneralNew.getClass(), compraInformacionGeneralNew.getIdCompra());
                medicamentoCompra.setCompraInformacionGeneral(compraInformacionGeneralNew);
            }
            if (medicamentoNew != null) {
                medicamentoNew = em.getReference(medicamentoNew.getClass(), medicamentoNew.getMedicamentoPK());
                medicamentoCompra.setMedicamento(medicamentoNew);
            }
            medicamentoCompra = em.merge(medicamentoCompra);
            if (compraInformacionGeneralOld != null && !compraInformacionGeneralOld.equals(compraInformacionGeneralNew)) {
                compraInformacionGeneralOld.getMedicamentoCompraCollection().remove(medicamentoCompra);
                compraInformacionGeneralOld = em.merge(compraInformacionGeneralOld);
            }
            if (compraInformacionGeneralNew != null && !compraInformacionGeneralNew.equals(compraInformacionGeneralOld)) {
                compraInformacionGeneralNew.getMedicamentoCompraCollection().add(medicamentoCompra);
                compraInformacionGeneralNew = em.merge(compraInformacionGeneralNew);
            }
            if (medicamentoOld != null && !medicamentoOld.equals(medicamentoNew)) {
                medicamentoOld.getMedicamentoCompraCollection().remove(medicamentoCompra);
                medicamentoOld = em.merge(medicamentoOld);
            }
            if (medicamentoNew != null && !medicamentoNew.equals(medicamentoOld)) {
                medicamentoNew.getMedicamentoCompraCollection().add(medicamentoCompra);
                medicamentoNew = em.merge(medicamentoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                MedicamentoCompraPK id = medicamentoCompra.getMedicamentoCompraPK();
                if (findMedicamentoCompra(id) == null) {
                    throw new NonexistentEntityException("The medicamentoCompra with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(MedicamentoCompraPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            MedicamentoCompra medicamentoCompra;
            try {
                medicamentoCompra = em.getReference(MedicamentoCompra.class, id);
                medicamentoCompra.getMedicamentoCompraPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The medicamentoCompra with id " + id + " no longer exists.", enfe);
            }
            CompraInformacionGeneral compraInformacionGeneral = medicamentoCompra.getCompraInformacionGeneral();
            if (compraInformacionGeneral != null) {
                compraInformacionGeneral.getMedicamentoCompraCollection().remove(medicamentoCompra);
                compraInformacionGeneral = em.merge(compraInformacionGeneral);
            }
            Medicamento medicamento = medicamentoCompra.getMedicamento();
            if (medicamento != null) {
                medicamento.getMedicamentoCompraCollection().remove(medicamentoCompra);
                medicamento = em.merge(medicamento);
            }
            em.remove(medicamentoCompra);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<MedicamentoCompra> findMedicamentoCompraEntities() {
        return findMedicamentoCompraEntities(true, -1, -1);
    }

    public List<MedicamentoCompra> findMedicamentoCompraEntities(int maxResults, int firstResult) {
        return findMedicamentoCompraEntities(false, maxResults, firstResult);
    }

    private List<MedicamentoCompra> findMedicamentoCompraEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(MedicamentoCompra.class));
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

    public MedicamentoCompra findMedicamentoCompra(MedicamentoCompraPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(MedicamentoCompra.class, id);
        } finally {
            em.close();
        }
    }

    public int getMedicamentoCompraCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<MedicamentoCompra> rt = cq.from(MedicamentoCompra.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
