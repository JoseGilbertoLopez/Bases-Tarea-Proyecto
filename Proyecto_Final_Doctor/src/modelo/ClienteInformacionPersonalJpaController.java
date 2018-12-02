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
import modelo.ClienteInformacionContacto;
import modelo.ClienteReceta;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.ClienteInformacionPersonal;

/**
 *
 * @author Jose G
 */
public class ClienteInformacionPersonalJpaController implements Serializable {

    public ClienteInformacionPersonalJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ClienteInformacionPersonal clienteInformacionPersonal) throws PreexistingEntityException, Exception {
        if (clienteInformacionPersonal.getClienteRecetaCollection() == null) {
            clienteInformacionPersonal.setClienteRecetaCollection(new ArrayList<ClienteReceta>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ClienteInformacionContacto clienteInformacionContacto = clienteInformacionPersonal.getClienteInformacionContacto();
            if (clienteInformacionContacto != null) {
                clienteInformacionContacto = em.getReference(clienteInformacionContacto.getClass(), clienteInformacionContacto.getClienteInformacionContactoPK());
                clienteInformacionPersonal.setClienteInformacionContacto(clienteInformacionContacto);
            }
            Collection<ClienteReceta> attachedClienteRecetaCollection = new ArrayList<ClienteReceta>();
            for (ClienteReceta clienteRecetaCollectionClienteRecetaToAttach : clienteInformacionPersonal.getClienteRecetaCollection()) {
                clienteRecetaCollectionClienteRecetaToAttach = em.getReference(clienteRecetaCollectionClienteRecetaToAttach.getClass(), clienteRecetaCollectionClienteRecetaToAttach.getClienteRecetaPK());
                attachedClienteRecetaCollection.add(clienteRecetaCollectionClienteRecetaToAttach);
            }
            clienteInformacionPersonal.setClienteRecetaCollection(attachedClienteRecetaCollection);
            em.persist(clienteInformacionPersonal);
            if (clienteInformacionContacto != null) {
                clienteInformacionContacto.getClienteInformacionPersonalCollection().add(clienteInformacionPersonal);
                clienteInformacionContacto = em.merge(clienteInformacionContacto);
            }
            for (ClienteReceta clienteRecetaCollectionClienteReceta : clienteInformacionPersonal.getClienteRecetaCollection()) {
                ClienteInformacionPersonal oldClienteInformacionPersonalOfClienteRecetaCollectionClienteReceta = clienteRecetaCollectionClienteReceta.getClienteInformacionPersonal();
                clienteRecetaCollectionClienteReceta.setClienteInformacionPersonal(clienteInformacionPersonal);
                clienteRecetaCollectionClienteReceta = em.merge(clienteRecetaCollectionClienteReceta);
                if (oldClienteInformacionPersonalOfClienteRecetaCollectionClienteReceta != null) {
                    oldClienteInformacionPersonalOfClienteRecetaCollectionClienteReceta.getClienteRecetaCollection().remove(clienteRecetaCollectionClienteReceta);
                    oldClienteInformacionPersonalOfClienteRecetaCollectionClienteReceta = em.merge(oldClienteInformacionPersonalOfClienteRecetaCollectionClienteReceta);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findClienteInformacionPersonal(clienteInformacionPersonal.getIdCliente()) != null) {
                throw new PreexistingEntityException("ClienteInformacionPersonal " + clienteInformacionPersonal + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ClienteInformacionPersonal clienteInformacionPersonal) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ClienteInformacionPersonal persistentClienteInformacionPersonal = em.find(ClienteInformacionPersonal.class, clienteInformacionPersonal.getIdCliente());
            ClienteInformacionContacto clienteInformacionContactoOld = persistentClienteInformacionPersonal.getClienteInformacionContacto();
            ClienteInformacionContacto clienteInformacionContactoNew = clienteInformacionPersonal.getClienteInformacionContacto();
            Collection<ClienteReceta> clienteRecetaCollectionOld = persistentClienteInformacionPersonal.getClienteRecetaCollection();
            Collection<ClienteReceta> clienteRecetaCollectionNew = clienteInformacionPersonal.getClienteRecetaCollection();
            List<String> illegalOrphanMessages = null;
            for (ClienteReceta clienteRecetaCollectionOldClienteReceta : clienteRecetaCollectionOld) {
                if (!clienteRecetaCollectionNew.contains(clienteRecetaCollectionOldClienteReceta)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ClienteReceta " + clienteRecetaCollectionOldClienteReceta + " since its clienteInformacionPersonal field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (clienteInformacionContactoNew != null) {
                clienteInformacionContactoNew = em.getReference(clienteInformacionContactoNew.getClass(), clienteInformacionContactoNew.getClienteInformacionContactoPK());
                clienteInformacionPersonal.setClienteInformacionContacto(clienteInformacionContactoNew);
            }
            Collection<ClienteReceta> attachedClienteRecetaCollectionNew = new ArrayList<ClienteReceta>();
            for (ClienteReceta clienteRecetaCollectionNewClienteRecetaToAttach : clienteRecetaCollectionNew) {
                clienteRecetaCollectionNewClienteRecetaToAttach = em.getReference(clienteRecetaCollectionNewClienteRecetaToAttach.getClass(), clienteRecetaCollectionNewClienteRecetaToAttach.getClienteRecetaPK());
                attachedClienteRecetaCollectionNew.add(clienteRecetaCollectionNewClienteRecetaToAttach);
            }
            clienteRecetaCollectionNew = attachedClienteRecetaCollectionNew;
            clienteInformacionPersonal.setClienteRecetaCollection(clienteRecetaCollectionNew);
            clienteInformacionPersonal = em.merge(clienteInformacionPersonal);
            if (clienteInformacionContactoOld != null && !clienteInformacionContactoOld.equals(clienteInformacionContactoNew)) {
                clienteInformacionContactoOld.getClienteInformacionPersonalCollection().remove(clienteInformacionPersonal);
                clienteInformacionContactoOld = em.merge(clienteInformacionContactoOld);
            }
            if (clienteInformacionContactoNew != null && !clienteInformacionContactoNew.equals(clienteInformacionContactoOld)) {
                clienteInformacionContactoNew.getClienteInformacionPersonalCollection().add(clienteInformacionPersonal);
                clienteInformacionContactoNew = em.merge(clienteInformacionContactoNew);
            }
            for (ClienteReceta clienteRecetaCollectionNewClienteReceta : clienteRecetaCollectionNew) {
                if (!clienteRecetaCollectionOld.contains(clienteRecetaCollectionNewClienteReceta)) {
                    ClienteInformacionPersonal oldClienteInformacionPersonalOfClienteRecetaCollectionNewClienteReceta = clienteRecetaCollectionNewClienteReceta.getClienteInformacionPersonal();
                    clienteRecetaCollectionNewClienteReceta.setClienteInformacionPersonal(clienteInformacionPersonal);
                    clienteRecetaCollectionNewClienteReceta = em.merge(clienteRecetaCollectionNewClienteReceta);
                    if (oldClienteInformacionPersonalOfClienteRecetaCollectionNewClienteReceta != null && !oldClienteInformacionPersonalOfClienteRecetaCollectionNewClienteReceta.equals(clienteInformacionPersonal)) {
                        oldClienteInformacionPersonalOfClienteRecetaCollectionNewClienteReceta.getClienteRecetaCollection().remove(clienteRecetaCollectionNewClienteReceta);
                        oldClienteInformacionPersonalOfClienteRecetaCollectionNewClienteReceta = em.merge(oldClienteInformacionPersonalOfClienteRecetaCollectionNewClienteReceta);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = clienteInformacionPersonal.getIdCliente();
                if (findClienteInformacionPersonal(id) == null) {
                    throw new NonexistentEntityException("The clienteInformacionPersonal with id " + id + " no longer exists.");
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
            ClienteInformacionPersonal clienteInformacionPersonal;
            try {
                clienteInformacionPersonal = em.getReference(ClienteInformacionPersonal.class, id);
                clienteInformacionPersonal.getIdCliente();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The clienteInformacionPersonal with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<ClienteReceta> clienteRecetaCollectionOrphanCheck = clienteInformacionPersonal.getClienteRecetaCollection();
            for (ClienteReceta clienteRecetaCollectionOrphanCheckClienteReceta : clienteRecetaCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This ClienteInformacionPersonal (" + clienteInformacionPersonal + ") cannot be destroyed since the ClienteReceta " + clienteRecetaCollectionOrphanCheckClienteReceta + " in its clienteRecetaCollection field has a non-nullable clienteInformacionPersonal field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            ClienteInformacionContacto clienteInformacionContacto = clienteInformacionPersonal.getClienteInformacionContacto();
            if (clienteInformacionContacto != null) {
                clienteInformacionContacto.getClienteInformacionPersonalCollection().remove(clienteInformacionPersonal);
                clienteInformacionContacto = em.merge(clienteInformacionContacto);
            }
            em.remove(clienteInformacionPersonal);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ClienteInformacionPersonal> findClienteInformacionPersonalEntities() {
        return findClienteInformacionPersonalEntities(true, -1, -1);
    }

    public List<ClienteInformacionPersonal> findClienteInformacionPersonalEntities(int maxResults, int firstResult) {
        return findClienteInformacionPersonalEntities(false, maxResults, firstResult);
    }

    private List<ClienteInformacionPersonal> findClienteInformacionPersonalEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ClienteInformacionPersonal.class));
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

    public ClienteInformacionPersonal findClienteInformacionPersonal(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ClienteInformacionPersonal.class, id);
        } finally {
            em.close();
        }
    }

    public int getClienteInformacionPersonalCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ClienteInformacionPersonal> rt = cq.from(ClienteInformacionPersonal.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
