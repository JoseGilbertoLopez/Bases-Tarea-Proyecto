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
import modelo.ClienteInformacionPersonal;
import modelo.ClienteReceta;
import modelo.ClienteRecetaPK;
import modelo.RecetaInformacionConsulta;

/**
 *
 * @author Jose G
 */
public class ClienteRecetaJpaController implements Serializable {

    public ClienteRecetaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public ClienteRecetaJpaController() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ClienteReceta clienteReceta) throws PreexistingEntityException, Exception {
        if (clienteReceta.getClienteRecetaPK() == null) {
            clienteReceta.setClienteRecetaPK(new ClienteRecetaPK());
        }
        //clienteReceta.getClienteRecetaPK().setIdReceta(clienteReceta.getRecetaInformacionConsulta().getIdReceta());
        //clienteReceta.getClienteRecetaPK().setIdCliente(clienteReceta.getClienteInformacionPersonal().getIdCliente());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ClienteInformacionPersonal clienteInformacionPersonal = clienteReceta.getClienteInformacionPersonal();
            if (clienteInformacionPersonal != null) {
                clienteInformacionPersonal = em.getReference(clienteInformacionPersonal.getClass(), clienteInformacionPersonal.getIdCliente());
                clienteReceta.setClienteInformacionPersonal(clienteInformacionPersonal);
            }
            RecetaInformacionConsulta recetaInformacionConsulta = clienteReceta.getRecetaInformacionConsulta();
            if (recetaInformacionConsulta != null) {
                recetaInformacionConsulta = em.getReference(recetaInformacionConsulta.getClass(), recetaInformacionConsulta.getIdReceta());
                clienteReceta.setRecetaInformacionConsulta(recetaInformacionConsulta);
            }
            em.persist(clienteReceta);
            if (clienteInformacionPersonal != null) {
                clienteInformacionPersonal.getClienteRecetaCollection().add(clienteReceta);
                clienteInformacionPersonal = em.merge(clienteInformacionPersonal);
            }
            if (recetaInformacionConsulta != null) {
                recetaInformacionConsulta.getClienteRecetaCollection().add(clienteReceta);
                recetaInformacionConsulta = em.merge(recetaInformacionConsulta);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findClienteReceta(clienteReceta.getClienteRecetaPK()) != null) {
                throw new PreexistingEntityException("ClienteReceta " + clienteReceta + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ClienteReceta clienteReceta) throws NonexistentEntityException, Exception {
        //clienteReceta.getClienteRecetaPK().setIdReceta(clienteReceta.getRecetaInformacionConsulta().getIdReceta());
        //clienteReceta.getClienteRecetaPK().setIdCliente(clienteReceta.getClienteInformacionPersonal().getIdCliente());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ClienteReceta persistentClienteReceta = em.find(ClienteReceta.class, clienteReceta.getClienteRecetaPK());
            ClienteInformacionPersonal clienteInformacionPersonalOld = persistentClienteReceta.getClienteInformacionPersonal();
            ClienteInformacionPersonal clienteInformacionPersonalNew = clienteReceta.getClienteInformacionPersonal();
            RecetaInformacionConsulta recetaInformacionConsultaOld = persistentClienteReceta.getRecetaInformacionConsulta();
            RecetaInformacionConsulta recetaInformacionConsultaNew = clienteReceta.getRecetaInformacionConsulta();
            if (clienteInformacionPersonalNew != null) {
                clienteInformacionPersonalNew = em.getReference(clienteInformacionPersonalNew.getClass(), clienteInformacionPersonalNew.getIdCliente());
                clienteReceta.setClienteInformacionPersonal(clienteInformacionPersonalNew);
            }
            if (recetaInformacionConsultaNew != null) {
                recetaInformacionConsultaNew = em.getReference(recetaInformacionConsultaNew.getClass(), recetaInformacionConsultaNew.getIdReceta());
                clienteReceta.setRecetaInformacionConsulta(recetaInformacionConsultaNew);
            }
            clienteReceta = em.merge(clienteReceta);
            if (clienteInformacionPersonalOld != null && !clienteInformacionPersonalOld.equals(clienteInformacionPersonalNew)) {
                clienteInformacionPersonalOld.getClienteRecetaCollection().remove(clienteReceta);
                clienteInformacionPersonalOld = em.merge(clienteInformacionPersonalOld);
            }
            if (clienteInformacionPersonalNew != null && !clienteInformacionPersonalNew.equals(clienteInformacionPersonalOld)) {
                clienteInformacionPersonalNew.getClienteRecetaCollection().add(clienteReceta);
                clienteInformacionPersonalNew = em.merge(clienteInformacionPersonalNew);
            }
            if (recetaInformacionConsultaOld != null && !recetaInformacionConsultaOld.equals(recetaInformacionConsultaNew)) {
                recetaInformacionConsultaOld.getClienteRecetaCollection().remove(clienteReceta);
                recetaInformacionConsultaOld = em.merge(recetaInformacionConsultaOld);
            }
            if (recetaInformacionConsultaNew != null && !recetaInformacionConsultaNew.equals(recetaInformacionConsultaOld)) {
                recetaInformacionConsultaNew.getClienteRecetaCollection().add(clienteReceta);
                recetaInformacionConsultaNew = em.merge(recetaInformacionConsultaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                ClienteRecetaPK id = clienteReceta.getClienteRecetaPK();
                if (findClienteReceta(id) == null) {
                    throw new NonexistentEntityException("The clienteReceta with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(ClienteRecetaPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ClienteReceta clienteReceta;
            try {
                clienteReceta = em.getReference(ClienteReceta.class, id);
                clienteReceta.getClienteRecetaPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The clienteReceta with id " + id + " no longer exists.", enfe);
            }
            ClienteInformacionPersonal clienteInformacionPersonal = clienteReceta.getClienteInformacionPersonal();
            if (clienteInformacionPersonal != null) {
                clienteInformacionPersonal.getClienteRecetaCollection().remove(clienteReceta);
                clienteInformacionPersonal = em.merge(clienteInformacionPersonal);
            }
            RecetaInformacionConsulta recetaInformacionConsulta = clienteReceta.getRecetaInformacionConsulta();
            if (recetaInformacionConsulta != null) {
                recetaInformacionConsulta.getClienteRecetaCollection().remove(clienteReceta);
                recetaInformacionConsulta = em.merge(recetaInformacionConsulta);
            }
            em.remove(clienteReceta);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ClienteReceta> findClienteRecetaEntities() {
        return findClienteRecetaEntities(true, -1, -1);
    }

    public List<ClienteReceta> findClienteRecetaEntities(int maxResults, int firstResult) {
        return findClienteRecetaEntities(false, maxResults, firstResult);
    }

    private List<ClienteReceta> findClienteRecetaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ClienteReceta.class));
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

    public ClienteReceta findClienteReceta(ClienteRecetaPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ClienteReceta.class, id);
        } finally {
            em.close();
        }
    }

    public int getClienteRecetaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ClienteReceta> rt = cq.from(ClienteReceta.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
