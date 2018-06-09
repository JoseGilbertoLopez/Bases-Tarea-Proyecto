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
import modelo.MedicoInformacionPersonal;
import modelo.MedicoReceta;
import modelo.MedicoRecetaPK;
import modelo.RecetaInformacionConsulta;

/**
 *
 * @author Jose G
 */
public class MedicoRecetaJpaController implements Serializable {

    public MedicoRecetaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(MedicoReceta medicoReceta) throws PreexistingEntityException, Exception {
        if (medicoReceta.getMedicoRecetaPK() == null) {
            medicoReceta.setMedicoRecetaPK(new MedicoRecetaPK());
        }
        medicoReceta.getMedicoRecetaPK().setRfc(medicoReceta.getMedicoInformacionPersonal().getMedicoInformacionPersonalPK().getRfc());
        medicoReceta.getMedicoRecetaPK().setIdMedico(medicoReceta.getMedicoInformacionPersonal().getMedicoInformacionPersonalPK().getIdMedico());
        //medicoReceta.getMedicoRecetaPK().setIdReceta(medicoReceta.getRecetaInformacionConsulta().getIdReceta());
        medicoReceta.getMedicoRecetaPK().setNoSeguroSocial(medicoReceta.getMedicoInformacionPersonal().getMedicoInformacionPersonalPK().getNoSeguroSocial());
        medicoReceta.getMedicoRecetaPK().setCedulaProfesional(medicoReceta.getMedicoInformacionPersonal().getMedicoInformacionPersonalPK().getCedulaProfesional());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            MedicoInformacionPersonal medicoInformacionPersonal = medicoReceta.getMedicoInformacionPersonal();
            if (medicoInformacionPersonal != null) {
                medicoInformacionPersonal = em.getReference(medicoInformacionPersonal.getClass(), medicoInformacionPersonal.getMedicoInformacionPersonalPK());
                medicoReceta.setMedicoInformacionPersonal(medicoInformacionPersonal);
            }
            RecetaInformacionConsulta recetaInformacionConsulta = medicoReceta.getRecetaInformacionConsulta();
            if (recetaInformacionConsulta != null) {
                recetaInformacionConsulta = em.getReference(recetaInformacionConsulta.getClass(), recetaInformacionConsulta.getIdReceta());
                medicoReceta.setRecetaInformacionConsulta(recetaInformacionConsulta);
            }
            em.persist(medicoReceta);
            if (medicoInformacionPersonal != null) {
                medicoInformacionPersonal.getMedicoRecetaCollection().add(medicoReceta);
                medicoInformacionPersonal = em.merge(medicoInformacionPersonal);
            }
            if (recetaInformacionConsulta != null) {
                recetaInformacionConsulta.getMedicoRecetaCollection().add(medicoReceta);
                recetaInformacionConsulta = em.merge(recetaInformacionConsulta);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findMedicoReceta(medicoReceta.getMedicoRecetaPK()) != null) {
                throw new PreexistingEntityException("MedicoReceta " + medicoReceta + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(MedicoReceta medicoReceta) throws NonexistentEntityException, Exception {
        medicoReceta.getMedicoRecetaPK().setRfc(medicoReceta.getMedicoInformacionPersonal().getMedicoInformacionPersonalPK().getRfc());
        medicoReceta.getMedicoRecetaPK().setIdMedico(medicoReceta.getMedicoInformacionPersonal().getMedicoInformacionPersonalPK().getIdMedico());
        //medicoReceta.getMedicoRecetaPK().setIdReceta(medicoReceta.getRecetaInformacionConsulta().getIdReceta());
        medicoReceta.getMedicoRecetaPK().setNoSeguroSocial(medicoReceta.getMedicoInformacionPersonal().getMedicoInformacionPersonalPK().getNoSeguroSocial());
        medicoReceta.getMedicoRecetaPK().setCedulaProfesional(medicoReceta.getMedicoInformacionPersonal().getMedicoInformacionPersonalPK().getCedulaProfesional());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            MedicoReceta persistentMedicoReceta = em.find(MedicoReceta.class, medicoReceta.getMedicoRecetaPK());
            MedicoInformacionPersonal medicoInformacionPersonalOld = persistentMedicoReceta.getMedicoInformacionPersonal();
            MedicoInformacionPersonal medicoInformacionPersonalNew = medicoReceta.getMedicoInformacionPersonal();
            RecetaInformacionConsulta recetaInformacionConsultaOld = persistentMedicoReceta.getRecetaInformacionConsulta();
            RecetaInformacionConsulta recetaInformacionConsultaNew = medicoReceta.getRecetaInformacionConsulta();
            if (medicoInformacionPersonalNew != null) {
                medicoInformacionPersonalNew = em.getReference(medicoInformacionPersonalNew.getClass(), medicoInformacionPersonalNew.getMedicoInformacionPersonalPK());
                medicoReceta.setMedicoInformacionPersonal(medicoInformacionPersonalNew);
            }
            if (recetaInformacionConsultaNew != null) {
                recetaInformacionConsultaNew = em.getReference(recetaInformacionConsultaNew.getClass(), recetaInformacionConsultaNew.getIdReceta());
                medicoReceta.setRecetaInformacionConsulta(recetaInformacionConsultaNew);
            }
            medicoReceta = em.merge(medicoReceta);
            if (medicoInformacionPersonalOld != null && !medicoInformacionPersonalOld.equals(medicoInformacionPersonalNew)) {
                medicoInformacionPersonalOld.getMedicoRecetaCollection().remove(medicoReceta);
                medicoInformacionPersonalOld = em.merge(medicoInformacionPersonalOld);
            }
            if (medicoInformacionPersonalNew != null && !medicoInformacionPersonalNew.equals(medicoInformacionPersonalOld)) {
                medicoInformacionPersonalNew.getMedicoRecetaCollection().add(medicoReceta);
                medicoInformacionPersonalNew = em.merge(medicoInformacionPersonalNew);
            }
            if (recetaInformacionConsultaOld != null && !recetaInformacionConsultaOld.equals(recetaInformacionConsultaNew)) {
                recetaInformacionConsultaOld.getMedicoRecetaCollection().remove(medicoReceta);
                recetaInformacionConsultaOld = em.merge(recetaInformacionConsultaOld);
            }
            if (recetaInformacionConsultaNew != null && !recetaInformacionConsultaNew.equals(recetaInformacionConsultaOld)) {
                recetaInformacionConsultaNew.getMedicoRecetaCollection().add(medicoReceta);
                recetaInformacionConsultaNew = em.merge(recetaInformacionConsultaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                MedicoRecetaPK id = medicoReceta.getMedicoRecetaPK();
                if (findMedicoReceta(id) == null) {
                    throw new NonexistentEntityException("The medicoReceta with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(MedicoRecetaPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            MedicoReceta medicoReceta;
            try {
                medicoReceta = em.getReference(MedicoReceta.class, id);
                medicoReceta.getMedicoRecetaPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The medicoReceta with id " + id + " no longer exists.", enfe);
            }
            MedicoInformacionPersonal medicoInformacionPersonal = medicoReceta.getMedicoInformacionPersonal();
            if (medicoInformacionPersonal != null) {
                medicoInformacionPersonal.getMedicoRecetaCollection().remove(medicoReceta);
                medicoInformacionPersonal = em.merge(medicoInformacionPersonal);
            }
            RecetaInformacionConsulta recetaInformacionConsulta = medicoReceta.getRecetaInformacionConsulta();
            if (recetaInformacionConsulta != null) {
                recetaInformacionConsulta.getMedicoRecetaCollection().remove(medicoReceta);
                recetaInformacionConsulta = em.merge(recetaInformacionConsulta);
            }
            em.remove(medicoReceta);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<MedicoReceta> findMedicoRecetaEntities() {
        return findMedicoRecetaEntities(true, -1, -1);
    }

    public List<MedicoReceta> findMedicoRecetaEntities(int maxResults, int firstResult) {
        return findMedicoRecetaEntities(false, maxResults, firstResult);
    }

    private List<MedicoReceta> findMedicoRecetaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(MedicoReceta.class));
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

    public MedicoReceta findMedicoReceta(MedicoRecetaPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(MedicoReceta.class, id);
        } finally {
            em.close();
        }
    }

    public int getMedicoRecetaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<MedicoReceta> rt = cq.from(MedicoReceta.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
