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
import modelo.GeneralInfoSucursal;

/**
 *
 * @author Jose G
 */
public class GeneralInfoSucursalJpaController implements Serializable {

    public GeneralInfoSucursalJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(GeneralInfoSucursal generalInfoSucursal) throws PreexistingEntityException, Exception {
        if (generalInfoSucursal.getGeneralInfoPersonalCollection() == null) {
            generalInfoSucursal.setGeneralInfoPersonalCollection(new ArrayList<GeneralInfoPersonal>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<GeneralInfoPersonal> attachedGeneralInfoPersonalCollection = new ArrayList<GeneralInfoPersonal>();
            for (GeneralInfoPersonal generalInfoPersonalCollectionGeneralInfoPersonalToAttach : generalInfoSucursal.getGeneralInfoPersonalCollection()) {
                generalInfoPersonalCollectionGeneralInfoPersonalToAttach = em.getReference(generalInfoPersonalCollectionGeneralInfoPersonalToAttach.getClass(), generalInfoPersonalCollectionGeneralInfoPersonalToAttach.getGeneralInfoPersonalPK());
                attachedGeneralInfoPersonalCollection.add(generalInfoPersonalCollectionGeneralInfoPersonalToAttach);
            }
            generalInfoSucursal.setGeneralInfoPersonalCollection(attachedGeneralInfoPersonalCollection);
            em.persist(generalInfoSucursal);
            for (GeneralInfoPersonal generalInfoPersonalCollectionGeneralInfoPersonal : generalInfoSucursal.getGeneralInfoPersonalCollection()) {
                GeneralInfoSucursal oldGeneralInfoSucursalOfGeneralInfoPersonalCollectionGeneralInfoPersonal = generalInfoPersonalCollectionGeneralInfoPersonal.getGeneralInfoSucursal();
                generalInfoPersonalCollectionGeneralInfoPersonal.setGeneralInfoSucursal(generalInfoSucursal);
                generalInfoPersonalCollectionGeneralInfoPersonal = em.merge(generalInfoPersonalCollectionGeneralInfoPersonal);
                if (oldGeneralInfoSucursalOfGeneralInfoPersonalCollectionGeneralInfoPersonal != null) {
                    oldGeneralInfoSucursalOfGeneralInfoPersonalCollectionGeneralInfoPersonal.getGeneralInfoPersonalCollection().remove(generalInfoPersonalCollectionGeneralInfoPersonal);
                    oldGeneralInfoSucursalOfGeneralInfoPersonalCollectionGeneralInfoPersonal = em.merge(oldGeneralInfoSucursalOfGeneralInfoPersonalCollectionGeneralInfoPersonal);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findGeneralInfoSucursal(generalInfoSucursal.getNoSeguroSocial()) != null) {
                throw new PreexistingEntityException("GeneralInfoSucursal " + generalInfoSucursal + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(GeneralInfoSucursal generalInfoSucursal) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            GeneralInfoSucursal persistentGeneralInfoSucursal = em.find(GeneralInfoSucursal.class, generalInfoSucursal.getNoSeguroSocial());
            Collection<GeneralInfoPersonal> generalInfoPersonalCollectionOld = persistentGeneralInfoSucursal.getGeneralInfoPersonalCollection();
            Collection<GeneralInfoPersonal> generalInfoPersonalCollectionNew = generalInfoSucursal.getGeneralInfoPersonalCollection();
            List<String> illegalOrphanMessages = null;
            for (GeneralInfoPersonal generalInfoPersonalCollectionOldGeneralInfoPersonal : generalInfoPersonalCollectionOld) {
                if (!generalInfoPersonalCollectionNew.contains(generalInfoPersonalCollectionOldGeneralInfoPersonal)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain GeneralInfoPersonal " + generalInfoPersonalCollectionOldGeneralInfoPersonal + " since its generalInfoSucursal field is not nullable.");
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
            generalInfoSucursal.setGeneralInfoPersonalCollection(generalInfoPersonalCollectionNew);
            generalInfoSucursal = em.merge(generalInfoSucursal);
            for (GeneralInfoPersonal generalInfoPersonalCollectionNewGeneralInfoPersonal : generalInfoPersonalCollectionNew) {
                if (!generalInfoPersonalCollectionOld.contains(generalInfoPersonalCollectionNewGeneralInfoPersonal)) {
                    GeneralInfoSucursal oldGeneralInfoSucursalOfGeneralInfoPersonalCollectionNewGeneralInfoPersonal = generalInfoPersonalCollectionNewGeneralInfoPersonal.getGeneralInfoSucursal();
                    generalInfoPersonalCollectionNewGeneralInfoPersonal.setGeneralInfoSucursal(generalInfoSucursal);
                    generalInfoPersonalCollectionNewGeneralInfoPersonal = em.merge(generalInfoPersonalCollectionNewGeneralInfoPersonal);
                    if (oldGeneralInfoSucursalOfGeneralInfoPersonalCollectionNewGeneralInfoPersonal != null && !oldGeneralInfoSucursalOfGeneralInfoPersonalCollectionNewGeneralInfoPersonal.equals(generalInfoSucursal)) {
                        oldGeneralInfoSucursalOfGeneralInfoPersonalCollectionNewGeneralInfoPersonal.getGeneralInfoPersonalCollection().remove(generalInfoPersonalCollectionNewGeneralInfoPersonal);
                        oldGeneralInfoSucursalOfGeneralInfoPersonalCollectionNewGeneralInfoPersonal = em.merge(oldGeneralInfoSucursalOfGeneralInfoPersonalCollectionNewGeneralInfoPersonal);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = generalInfoSucursal.getNoSeguroSocial();
                if (findGeneralInfoSucursal(id) == null) {
                    throw new NonexistentEntityException("The generalInfoSucursal with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            GeneralInfoSucursal generalInfoSucursal;
            try {
                generalInfoSucursal = em.getReference(GeneralInfoSucursal.class, id);
                generalInfoSucursal.getNoSeguroSocial();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The generalInfoSucursal with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<GeneralInfoPersonal> generalInfoPersonalCollectionOrphanCheck = generalInfoSucursal.getGeneralInfoPersonalCollection();
            for (GeneralInfoPersonal generalInfoPersonalCollectionOrphanCheckGeneralInfoPersonal : generalInfoPersonalCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This GeneralInfoSucursal (" + generalInfoSucursal + ") cannot be destroyed since the GeneralInfoPersonal " + generalInfoPersonalCollectionOrphanCheckGeneralInfoPersonal + " in its generalInfoPersonalCollection field has a non-nullable generalInfoSucursal field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(generalInfoSucursal);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<GeneralInfoSucursal> findGeneralInfoSucursalEntities() {
        return findGeneralInfoSucursalEntities(true, -1, -1);
    }

    public List<GeneralInfoSucursal> findGeneralInfoSucursalEntities(int maxResults, int firstResult) {
        return findGeneralInfoSucursalEntities(false, maxResults, firstResult);
    }

    private List<GeneralInfoSucursal> findGeneralInfoSucursalEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(GeneralInfoSucursal.class));
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

    public GeneralInfoSucursal findGeneralInfoSucursal(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(GeneralInfoSucursal.class, id);
        } finally {
            em.close();
        }
    }

    public int getGeneralInfoSucursalCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<GeneralInfoSucursal> rt = cq.from(GeneralInfoSucursal.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
