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
import modelo.RecetaInformacionConsulta;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.RecetaInformacionEspecial;
import modelo.RecetaInformacionEspecialPK;

/**
 *
 * @author Jose G
 */
public class RecetaInformacionEspecialJpaController implements Serializable {

    public RecetaInformacionEspecialJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(RecetaInformacionEspecial recetaInformacionEspecial) throws PreexistingEntityException, Exception {
        if (recetaInformacionEspecial.getRecetaInformacionEspecialPK() == null) {
            recetaInformacionEspecial.setRecetaInformacionEspecialPK(new RecetaInformacionEspecialPK());
        }
        if (recetaInformacionEspecial.getRecetaInformacionConsultaCollection() == null) {
            recetaInformacionEspecial.setRecetaInformacionConsultaCollection(new ArrayList<RecetaInformacionConsulta>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<RecetaInformacionConsulta> attachedRecetaInformacionConsultaCollection = new ArrayList<RecetaInformacionConsulta>();
            for (RecetaInformacionConsulta recetaInformacionConsultaCollectionRecetaInformacionConsultaToAttach : recetaInformacionEspecial.getRecetaInformacionConsultaCollection()) {
                recetaInformacionConsultaCollectionRecetaInformacionConsultaToAttach = em.getReference(recetaInformacionConsultaCollectionRecetaInformacionConsultaToAttach.getClass(), recetaInformacionConsultaCollectionRecetaInformacionConsultaToAttach.getIdReceta());
                attachedRecetaInformacionConsultaCollection.add(recetaInformacionConsultaCollectionRecetaInformacionConsultaToAttach);
            }
            recetaInformacionEspecial.setRecetaInformacionConsultaCollection(attachedRecetaInformacionConsultaCollection);
            em.persist(recetaInformacionEspecial);
            for (RecetaInformacionConsulta recetaInformacionConsultaCollectionRecetaInformacionConsulta : recetaInformacionEspecial.getRecetaInformacionConsultaCollection()) {
                RecetaInformacionEspecial oldRecetaInformacionEspecialOfRecetaInformacionConsultaCollectionRecetaInformacionConsulta = recetaInformacionConsultaCollectionRecetaInformacionConsulta.getRecetaInformacionEspecial();
                recetaInformacionConsultaCollectionRecetaInformacionConsulta.setRecetaInformacionEspecial(recetaInformacionEspecial);
                recetaInformacionConsultaCollectionRecetaInformacionConsulta = em.merge(recetaInformacionConsultaCollectionRecetaInformacionConsulta);
                if (oldRecetaInformacionEspecialOfRecetaInformacionConsultaCollectionRecetaInformacionConsulta != null) {
                    oldRecetaInformacionEspecialOfRecetaInformacionConsultaCollectionRecetaInformacionConsulta.getRecetaInformacionConsultaCollection().remove(recetaInformacionConsultaCollectionRecetaInformacionConsulta);
                    oldRecetaInformacionEspecialOfRecetaInformacionConsultaCollectionRecetaInformacionConsulta = em.merge(oldRecetaInformacionEspecialOfRecetaInformacionConsultaCollectionRecetaInformacionConsulta);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findRecetaInformacionEspecial(recetaInformacionEspecial.getRecetaInformacionEspecialPK()) != null) {
                throw new PreexistingEntityException("RecetaInformacionEspecial " + recetaInformacionEspecial + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(RecetaInformacionEspecial recetaInformacionEspecial) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            RecetaInformacionEspecial persistentRecetaInformacionEspecial = em.find(RecetaInformacionEspecial.class, recetaInformacionEspecial.getRecetaInformacionEspecialPK());
            Collection<RecetaInformacionConsulta> recetaInformacionConsultaCollectionOld = persistentRecetaInformacionEspecial.getRecetaInformacionConsultaCollection();
            Collection<RecetaInformacionConsulta> recetaInformacionConsultaCollectionNew = recetaInformacionEspecial.getRecetaInformacionConsultaCollection();
            List<String> illegalOrphanMessages = null;
            for (RecetaInformacionConsulta recetaInformacionConsultaCollectionOldRecetaInformacionConsulta : recetaInformacionConsultaCollectionOld) {
                if (!recetaInformacionConsultaCollectionNew.contains(recetaInformacionConsultaCollectionOldRecetaInformacionConsulta)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain RecetaInformacionConsulta " + recetaInformacionConsultaCollectionOldRecetaInformacionConsulta + " since its recetaInformacionEspecial field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<RecetaInformacionConsulta> attachedRecetaInformacionConsultaCollectionNew = new ArrayList<RecetaInformacionConsulta>();
            for (RecetaInformacionConsulta recetaInformacionConsultaCollectionNewRecetaInformacionConsultaToAttach : recetaInformacionConsultaCollectionNew) {
                recetaInformacionConsultaCollectionNewRecetaInformacionConsultaToAttach = em.getReference(recetaInformacionConsultaCollectionNewRecetaInformacionConsultaToAttach.getClass(), recetaInformacionConsultaCollectionNewRecetaInformacionConsultaToAttach.getIdReceta());
                attachedRecetaInformacionConsultaCollectionNew.add(recetaInformacionConsultaCollectionNewRecetaInformacionConsultaToAttach);
            }
            recetaInformacionConsultaCollectionNew = attachedRecetaInformacionConsultaCollectionNew;
            recetaInformacionEspecial.setRecetaInformacionConsultaCollection(recetaInformacionConsultaCollectionNew);
            recetaInformacionEspecial = em.merge(recetaInformacionEspecial);
            for (RecetaInformacionConsulta recetaInformacionConsultaCollectionNewRecetaInformacionConsulta : recetaInformacionConsultaCollectionNew) {
                if (!recetaInformacionConsultaCollectionOld.contains(recetaInformacionConsultaCollectionNewRecetaInformacionConsulta)) {
                    RecetaInformacionEspecial oldRecetaInformacionEspecialOfRecetaInformacionConsultaCollectionNewRecetaInformacionConsulta = recetaInformacionConsultaCollectionNewRecetaInformacionConsulta.getRecetaInformacionEspecial();
                    recetaInformacionConsultaCollectionNewRecetaInformacionConsulta.setRecetaInformacionEspecial(recetaInformacionEspecial);
                    recetaInformacionConsultaCollectionNewRecetaInformacionConsulta = em.merge(recetaInformacionConsultaCollectionNewRecetaInformacionConsulta);
                    if (oldRecetaInformacionEspecialOfRecetaInformacionConsultaCollectionNewRecetaInformacionConsulta != null && !oldRecetaInformacionEspecialOfRecetaInformacionConsultaCollectionNewRecetaInformacionConsulta.equals(recetaInformacionEspecial)) {
                        oldRecetaInformacionEspecialOfRecetaInformacionConsultaCollectionNewRecetaInformacionConsulta.getRecetaInformacionConsultaCollection().remove(recetaInformacionConsultaCollectionNewRecetaInformacionConsulta);
                        oldRecetaInformacionEspecialOfRecetaInformacionConsultaCollectionNewRecetaInformacionConsulta = em.merge(oldRecetaInformacionEspecialOfRecetaInformacionConsultaCollectionNewRecetaInformacionConsulta);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                RecetaInformacionEspecialPK id = recetaInformacionEspecial.getRecetaInformacionEspecialPK();
                if (findRecetaInformacionEspecial(id) == null) {
                    throw new NonexistentEntityException("The recetaInformacionEspecial with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(RecetaInformacionEspecialPK id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            RecetaInformacionEspecial recetaInformacionEspecial;
            try {
                recetaInformacionEspecial = em.getReference(RecetaInformacionEspecial.class, id);
                recetaInformacionEspecial.getRecetaInformacionEspecialPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The recetaInformacionEspecial with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<RecetaInformacionConsulta> recetaInformacionConsultaCollectionOrphanCheck = recetaInformacionEspecial.getRecetaInformacionConsultaCollection();
            for (RecetaInformacionConsulta recetaInformacionConsultaCollectionOrphanCheckRecetaInformacionConsulta : recetaInformacionConsultaCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This RecetaInformacionEspecial (" + recetaInformacionEspecial + ") cannot be destroyed since the RecetaInformacionConsulta " + recetaInformacionConsultaCollectionOrphanCheckRecetaInformacionConsulta + " in its recetaInformacionConsultaCollection field has a non-nullable recetaInformacionEspecial field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(recetaInformacionEspecial);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<RecetaInformacionEspecial> findRecetaInformacionEspecialEntities() {
        return findRecetaInformacionEspecialEntities(true, -1, -1);
    }

    public List<RecetaInformacionEspecial> findRecetaInformacionEspecialEntities(int maxResults, int firstResult) {
        return findRecetaInformacionEspecialEntities(false, maxResults, firstResult);
    }

    private List<RecetaInformacionEspecial> findRecetaInformacionEspecialEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(RecetaInformacionEspecial.class));
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

    public RecetaInformacionEspecial findRecetaInformacionEspecial(RecetaInformacionEspecialPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(RecetaInformacionEspecial.class, id);
        } finally {
            em.close();
        }
    }

    public int getRecetaInformacionEspecialCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<RecetaInformacionEspecial> rt = cq.from(RecetaInformacionEspecial.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
