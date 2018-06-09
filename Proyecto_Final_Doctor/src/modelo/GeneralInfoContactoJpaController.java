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
import modelo.GeneralInfoPersonal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.GeneralInfoContacto;
import modelo.GeneralInfoContactoPK;

/**
 *
 * @author Jose G
 */
public class GeneralInfoContactoJpaController implements Serializable {

    public GeneralInfoContactoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(GeneralInfoContacto generalInfoContacto) throws PreexistingEntityException, Exception {
        if (generalInfoContacto.getGeneralInfoContactoPK() == null) {
            generalInfoContacto.setGeneralInfoContactoPK(new GeneralInfoContactoPK());
        }
        if (generalInfoContacto.getGeneralInfoPersonalCollection() == null) {
            generalInfoContacto.setGeneralInfoPersonalCollection(new ArrayList<GeneralInfoPersonal>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<GeneralInfoPersonal> attachedGeneralInfoPersonalCollection = new ArrayList<GeneralInfoPersonal>();
            for (GeneralInfoPersonal generalInfoPersonalCollectionGeneralInfoPersonalToAttach : generalInfoContacto.getGeneralInfoPersonalCollection()) {
                generalInfoPersonalCollectionGeneralInfoPersonalToAttach = em.getReference(generalInfoPersonalCollectionGeneralInfoPersonalToAttach.getClass(), generalInfoPersonalCollectionGeneralInfoPersonalToAttach.getGeneralInfoPersonalPK());
                attachedGeneralInfoPersonalCollection.add(generalInfoPersonalCollectionGeneralInfoPersonalToAttach);
            }
            generalInfoContacto.setGeneralInfoPersonalCollection(attachedGeneralInfoPersonalCollection);
            em.persist(generalInfoContacto);
            for (GeneralInfoPersonal generalInfoPersonalCollectionGeneralInfoPersonal : generalInfoContacto.getGeneralInfoPersonalCollection()) {
                GeneralInfoContacto oldGeneralInfoContactoOfGeneralInfoPersonalCollectionGeneralInfoPersonal = generalInfoPersonalCollectionGeneralInfoPersonal.getGeneralInfoContacto();
                generalInfoPersonalCollectionGeneralInfoPersonal.setGeneralInfoContacto(generalInfoContacto);
                generalInfoPersonalCollectionGeneralInfoPersonal = em.merge(generalInfoPersonalCollectionGeneralInfoPersonal);
                if (oldGeneralInfoContactoOfGeneralInfoPersonalCollectionGeneralInfoPersonal != null) {
                    oldGeneralInfoContactoOfGeneralInfoPersonalCollectionGeneralInfoPersonal.getGeneralInfoPersonalCollection().remove(generalInfoPersonalCollectionGeneralInfoPersonal);
                    oldGeneralInfoContactoOfGeneralInfoPersonalCollectionGeneralInfoPersonal = em.merge(oldGeneralInfoContactoOfGeneralInfoPersonalCollectionGeneralInfoPersonal);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findGeneralInfoContacto(generalInfoContacto.getGeneralInfoContactoPK()) != null) {
                throw new PreexistingEntityException("GeneralInfoContacto " + generalInfoContacto + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(GeneralInfoContacto generalInfoContacto) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            GeneralInfoContacto persistentGeneralInfoContacto = em.find(GeneralInfoContacto.class, generalInfoContacto.getGeneralInfoContactoPK());
            Collection<GeneralInfoPersonal> generalInfoPersonalCollectionOld = persistentGeneralInfoContacto.getGeneralInfoPersonalCollection();
            Collection<GeneralInfoPersonal> generalInfoPersonalCollectionNew = generalInfoContacto.getGeneralInfoPersonalCollection();
            List<String> illegalOrphanMessages = null;
            for (GeneralInfoPersonal generalInfoPersonalCollectionOldGeneralInfoPersonal : generalInfoPersonalCollectionOld) {
                if (!generalInfoPersonalCollectionNew.contains(generalInfoPersonalCollectionOldGeneralInfoPersonal)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain GeneralInfoPersonal " + generalInfoPersonalCollectionOldGeneralInfoPersonal + " since its generalInfoContacto field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<GeneralInfoPersonal> attachedGeneralInfoPersonalCollectionNew = new ArrayList<GeneralInfoPersonal>();
            for (GeneralInfoPersonal generalInfoPersonalCollectionNewGeneralInfoPersonalToAttach : generalInfoPersonalCollectionNew) {
                generalInfoPersonalCollectionNewGeneralInfoPersonalToAttach = em.getReference(generalInfoPersonalCollectionNewGeneralInfoPersonalToAttach.getClass(), generalInfoPersonalCollectionNewGeneralInfoPersonalToAttach.getGeneralInfoPersonalPK());
                attachedGeneralInfoPersonalCollectionNew.add(generalInfoPersonalCollectionNewGeneralInfoPersonalToAttach);
            }
            generalInfoPersonalCollectionNew = attachedGeneralInfoPersonalCollectionNew;
            generalInfoContacto.setGeneralInfoPersonalCollection(generalInfoPersonalCollectionNew);
            generalInfoContacto = em.merge(generalInfoContacto);
            for (GeneralInfoPersonal generalInfoPersonalCollectionNewGeneralInfoPersonal : generalInfoPersonalCollectionNew) {
                if (!generalInfoPersonalCollectionOld.contains(generalInfoPersonalCollectionNewGeneralInfoPersonal)) {
                    GeneralInfoContacto oldGeneralInfoContactoOfGeneralInfoPersonalCollectionNewGeneralInfoPersonal = generalInfoPersonalCollectionNewGeneralInfoPersonal.getGeneralInfoContacto();
                    generalInfoPersonalCollectionNewGeneralInfoPersonal.setGeneralInfoContacto(generalInfoContacto);
                    generalInfoPersonalCollectionNewGeneralInfoPersonal = em.merge(generalInfoPersonalCollectionNewGeneralInfoPersonal);
                    if (oldGeneralInfoContactoOfGeneralInfoPersonalCollectionNewGeneralInfoPersonal != null && !oldGeneralInfoContactoOfGeneralInfoPersonalCollectionNewGeneralInfoPersonal.equals(generalInfoContacto)) {
                        oldGeneralInfoContactoOfGeneralInfoPersonalCollectionNewGeneralInfoPersonal.getGeneralInfoPersonalCollection().remove(generalInfoPersonalCollectionNewGeneralInfoPersonal);
                        oldGeneralInfoContactoOfGeneralInfoPersonalCollectionNewGeneralInfoPersonal = em.merge(oldGeneralInfoContactoOfGeneralInfoPersonalCollectionNewGeneralInfoPersonal);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                GeneralInfoContactoPK id = generalInfoContacto.getGeneralInfoContactoPK();
                if (findGeneralInfoContacto(id) == null) {
                    throw new NonexistentEntityException("The generalInfoContacto with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(GeneralInfoContactoPK id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            GeneralInfoContacto generalInfoContacto;
            try {
                generalInfoContacto = em.getReference(GeneralInfoContacto.class, id);
                generalInfoContacto.getGeneralInfoContactoPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The generalInfoContacto with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<GeneralInfoPersonal> generalInfoPersonalCollectionOrphanCheck = generalInfoContacto.getGeneralInfoPersonalCollection();
            for (GeneralInfoPersonal generalInfoPersonalCollectionOrphanCheckGeneralInfoPersonal : generalInfoPersonalCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This GeneralInfoContacto (" + generalInfoContacto + ") cannot be destroyed since the GeneralInfoPersonal " + generalInfoPersonalCollectionOrphanCheckGeneralInfoPersonal + " in its generalInfoPersonalCollection field has a non-nullable generalInfoContacto field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(generalInfoContacto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<GeneralInfoContacto> findGeneralInfoContactoEntities() {
        return findGeneralInfoContactoEntities(true, -1, -1);
    }

    public List<GeneralInfoContacto> findGeneralInfoContactoEntities(int maxResults, int firstResult) {
        return findGeneralInfoContactoEntities(false, maxResults, firstResult);
    }

    private List<GeneralInfoContacto> findGeneralInfoContactoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(GeneralInfoContacto.class));
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

    public GeneralInfoContacto findGeneralInfoContacto(GeneralInfoContactoPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(GeneralInfoContacto.class, id);
        } finally {
            em.close();
        }
    }

    public int getGeneralInfoContactoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<GeneralInfoContacto> rt = cq.from(GeneralInfoContacto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
