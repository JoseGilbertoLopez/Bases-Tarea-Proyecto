/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import Controlador.exceptions.NonexistentEntityException;
import Controlador.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelo.ClienteInformacionPersonal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.ClienteInformacionContacto;
import modelo.ClienteInformacionContactoPK;

/**
 *
 * @author Jose G
 */
public class ClienteInformacionContactoJpaController implements Serializable {

    public ClienteInformacionContactoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ClienteInformacionContacto clienteInformacionContacto) throws PreexistingEntityException, Exception {
        if (clienteInformacionContacto.getClienteInformacionContactoPK() == null) {
            clienteInformacionContacto.setClienteInformacionContactoPK(new ClienteInformacionContactoPK());
        }
        if (clienteInformacionContacto.getClienteInformacionPersonalCollection() == null) {
            clienteInformacionContacto.setClienteInformacionPersonalCollection(new ArrayList<ClienteInformacionPersonal>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<ClienteInformacionPersonal> attachedClienteInformacionPersonalCollection = new ArrayList<ClienteInformacionPersonal>();
            for (ClienteInformacionPersonal clienteInformacionPersonalCollectionClienteInformacionPersonalToAttach : clienteInformacionContacto.getClienteInformacionPersonalCollection()) {
                clienteInformacionPersonalCollectionClienteInformacionPersonalToAttach = em.getReference(clienteInformacionPersonalCollectionClienteInformacionPersonalToAttach.getClass(), clienteInformacionPersonalCollectionClienteInformacionPersonalToAttach.getIdCliente());
                attachedClienteInformacionPersonalCollection.add(clienteInformacionPersonalCollectionClienteInformacionPersonalToAttach);
            }
            clienteInformacionContacto.setClienteInformacionPersonalCollection(attachedClienteInformacionPersonalCollection);
            em.persist(clienteInformacionContacto);
            for (ClienteInformacionPersonal clienteInformacionPersonalCollectionClienteInformacionPersonal : clienteInformacionContacto.getClienteInformacionPersonalCollection()) {
                ClienteInformacionContacto oldClienteInformacionContactoOfClienteInformacionPersonalCollectionClienteInformacionPersonal = clienteInformacionPersonalCollectionClienteInformacionPersonal.getClienteInformacionContacto();
                clienteInformacionPersonalCollectionClienteInformacionPersonal.setClienteInformacionContacto(clienteInformacionContacto);
                clienteInformacionPersonalCollectionClienteInformacionPersonal = em.merge(clienteInformacionPersonalCollectionClienteInformacionPersonal);
                if (oldClienteInformacionContactoOfClienteInformacionPersonalCollectionClienteInformacionPersonal != null) {
                    oldClienteInformacionContactoOfClienteInformacionPersonalCollectionClienteInformacionPersonal.getClienteInformacionPersonalCollection().remove(clienteInformacionPersonalCollectionClienteInformacionPersonal);
                    oldClienteInformacionContactoOfClienteInformacionPersonalCollectionClienteInformacionPersonal = em.merge(oldClienteInformacionContactoOfClienteInformacionPersonalCollectionClienteInformacionPersonal);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findClienteInformacionContacto(clienteInformacionContacto.getClienteInformacionContactoPK()) != null) {
                throw new PreexistingEntityException("ClienteInformacionContacto " + clienteInformacionContacto + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ClienteInformacionContacto clienteInformacionContacto) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ClienteInformacionContacto persistentClienteInformacionContacto = em.find(ClienteInformacionContacto.class, clienteInformacionContacto.getClienteInformacionContactoPK());
            Collection<ClienteInformacionPersonal> clienteInformacionPersonalCollectionOld = persistentClienteInformacionContacto.getClienteInformacionPersonalCollection();
            Collection<ClienteInformacionPersonal> clienteInformacionPersonalCollectionNew = clienteInformacionContacto.getClienteInformacionPersonalCollection();
            Collection<ClienteInformacionPersonal> attachedClienteInformacionPersonalCollectionNew = new ArrayList<ClienteInformacionPersonal>();
            for (ClienteInformacionPersonal clienteInformacionPersonalCollectionNewClienteInformacionPersonalToAttach : clienteInformacionPersonalCollectionNew) {
                clienteInformacionPersonalCollectionNewClienteInformacionPersonalToAttach = em.getReference(clienteInformacionPersonalCollectionNewClienteInformacionPersonalToAttach.getClass(), clienteInformacionPersonalCollectionNewClienteInformacionPersonalToAttach.getIdCliente());
                attachedClienteInformacionPersonalCollectionNew.add(clienteInformacionPersonalCollectionNewClienteInformacionPersonalToAttach);
            }
            clienteInformacionPersonalCollectionNew = attachedClienteInformacionPersonalCollectionNew;
            clienteInformacionContacto.setClienteInformacionPersonalCollection(clienteInformacionPersonalCollectionNew);
            clienteInformacionContacto = em.merge(clienteInformacionContacto);
            for (ClienteInformacionPersonal clienteInformacionPersonalCollectionOldClienteInformacionPersonal : clienteInformacionPersonalCollectionOld) {
                if (!clienteInformacionPersonalCollectionNew.contains(clienteInformacionPersonalCollectionOldClienteInformacionPersonal)) {
                    clienteInformacionPersonalCollectionOldClienteInformacionPersonal.setClienteInformacionContacto(null);
                    clienteInformacionPersonalCollectionOldClienteInformacionPersonal = em.merge(clienteInformacionPersonalCollectionOldClienteInformacionPersonal);
                }
            }
            for (ClienteInformacionPersonal clienteInformacionPersonalCollectionNewClienteInformacionPersonal : clienteInformacionPersonalCollectionNew) {
                if (!clienteInformacionPersonalCollectionOld.contains(clienteInformacionPersonalCollectionNewClienteInformacionPersonal)) {
                    ClienteInformacionContacto oldClienteInformacionContactoOfClienteInformacionPersonalCollectionNewClienteInformacionPersonal = clienteInformacionPersonalCollectionNewClienteInformacionPersonal.getClienteInformacionContacto();
                    clienteInformacionPersonalCollectionNewClienteInformacionPersonal.setClienteInformacionContacto(clienteInformacionContacto);
                    clienteInformacionPersonalCollectionNewClienteInformacionPersonal = em.merge(clienteInformacionPersonalCollectionNewClienteInformacionPersonal);
                    if (oldClienteInformacionContactoOfClienteInformacionPersonalCollectionNewClienteInformacionPersonal != null && !oldClienteInformacionContactoOfClienteInformacionPersonalCollectionNewClienteInformacionPersonal.equals(clienteInformacionContacto)) {
                        oldClienteInformacionContactoOfClienteInformacionPersonalCollectionNewClienteInformacionPersonal.getClienteInformacionPersonalCollection().remove(clienteInformacionPersonalCollectionNewClienteInformacionPersonal);
                        oldClienteInformacionContactoOfClienteInformacionPersonalCollectionNewClienteInformacionPersonal = em.merge(oldClienteInformacionContactoOfClienteInformacionPersonalCollectionNewClienteInformacionPersonal);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                ClienteInformacionContactoPK id = clienteInformacionContacto.getClienteInformacionContactoPK();
                if (findClienteInformacionContacto(id) == null) {
                    throw new NonexistentEntityException("The clienteInformacionContacto with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(ClienteInformacionContactoPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ClienteInformacionContacto clienteInformacionContacto;
            try {
                clienteInformacionContacto = em.getReference(ClienteInformacionContacto.class, id);
                clienteInformacionContacto.getClienteInformacionContactoPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The clienteInformacionContacto with id " + id + " no longer exists.", enfe);
            }
            Collection<ClienteInformacionPersonal> clienteInformacionPersonalCollection = clienteInformacionContacto.getClienteInformacionPersonalCollection();
            for (ClienteInformacionPersonal clienteInformacionPersonalCollectionClienteInformacionPersonal : clienteInformacionPersonalCollection) {
                clienteInformacionPersonalCollectionClienteInformacionPersonal.setClienteInformacionContacto(null);
                clienteInformacionPersonalCollectionClienteInformacionPersonal = em.merge(clienteInformacionPersonalCollectionClienteInformacionPersonal);
            }
            em.remove(clienteInformacionContacto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ClienteInformacionContacto> findClienteInformacionContactoEntities() {
        return findClienteInformacionContactoEntities(true, -1, -1);
    }

    public List<ClienteInformacionContacto> findClienteInformacionContactoEntities(int maxResults, int firstResult) {
        return findClienteInformacionContactoEntities(false, maxResults, firstResult);
    }

    private List<ClienteInformacionContacto> findClienteInformacionContactoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ClienteInformacionContacto.class));
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

    public ClienteInformacionContacto findClienteInformacionContacto(ClienteInformacionContactoPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ClienteInformacionContacto.class, id);
        } finally {
            em.close();
        }
    }

    public int getClienteInformacionContactoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ClienteInformacionContacto> rt = cq.from(ClienteInformacionContacto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
