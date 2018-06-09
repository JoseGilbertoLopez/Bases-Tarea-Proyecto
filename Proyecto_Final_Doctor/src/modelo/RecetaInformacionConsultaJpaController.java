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
import modelo.RecetaInformacionEspecial;
import modelo.ClienteReceta;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.MedicoReceta;
import modelo.MedicamentoReceta;
import modelo.RecetaInformacionConsulta;

/**
 *
 * @author Jose G
 */
public class RecetaInformacionConsultaJpaController implements Serializable {

    public RecetaInformacionConsultaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(RecetaInformacionConsulta recetaInformacionConsulta) throws PreexistingEntityException, Exception {
        if (recetaInformacionConsulta.getClienteRecetaCollection() == null) {
            recetaInformacionConsulta.setClienteRecetaCollection(new ArrayList<ClienteReceta>());
        }
        if (recetaInformacionConsulta.getMedicoRecetaCollection() == null) {
            recetaInformacionConsulta.setMedicoRecetaCollection(new ArrayList<MedicoReceta>());
        }
        if (recetaInformacionConsulta.getMedicamentoRecetaCollection() == null) {
            recetaInformacionConsulta.setMedicamentoRecetaCollection(new ArrayList<MedicamentoReceta>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            RecetaInformacionEspecial recetaInformacionEspecial = recetaInformacionConsulta.getRecetaInformacionEspecial();
            if (recetaInformacionEspecial != null) {
                recetaInformacionEspecial = em.getReference(recetaInformacionEspecial.getClass(), recetaInformacionEspecial.getRecetaInformacionEspecialPK());
                recetaInformacionConsulta.setRecetaInformacionEspecial(recetaInformacionEspecial);
            }
            Collection<ClienteReceta> attachedClienteRecetaCollection = new ArrayList<ClienteReceta>();
            for (ClienteReceta clienteRecetaCollectionClienteRecetaToAttach : recetaInformacionConsulta.getClienteRecetaCollection()) {
                clienteRecetaCollectionClienteRecetaToAttach = em.getReference(clienteRecetaCollectionClienteRecetaToAttach.getClass(), clienteRecetaCollectionClienteRecetaToAttach.getClienteRecetaPK());
                attachedClienteRecetaCollection.add(clienteRecetaCollectionClienteRecetaToAttach);
            }
            recetaInformacionConsulta.setClienteRecetaCollection(attachedClienteRecetaCollection);
            Collection<MedicoReceta> attachedMedicoRecetaCollection = new ArrayList<MedicoReceta>();
            for (MedicoReceta medicoRecetaCollectionMedicoRecetaToAttach : recetaInformacionConsulta.getMedicoRecetaCollection()) {
                medicoRecetaCollectionMedicoRecetaToAttach = em.getReference(medicoRecetaCollectionMedicoRecetaToAttach.getClass(), medicoRecetaCollectionMedicoRecetaToAttach.getMedicoRecetaPK());
                attachedMedicoRecetaCollection.add(medicoRecetaCollectionMedicoRecetaToAttach);
            }
            recetaInformacionConsulta.setMedicoRecetaCollection(attachedMedicoRecetaCollection);
            Collection<MedicamentoReceta> attachedMedicamentoRecetaCollection = new ArrayList<MedicamentoReceta>();
            for (MedicamentoReceta medicamentoRecetaCollectionMedicamentoRecetaToAttach : recetaInformacionConsulta.getMedicamentoRecetaCollection()) {
                medicamentoRecetaCollectionMedicamentoRecetaToAttach = em.getReference(medicamentoRecetaCollectionMedicamentoRecetaToAttach.getClass(), medicamentoRecetaCollectionMedicamentoRecetaToAttach.getMedicamentoRecetaPK());
                attachedMedicamentoRecetaCollection.add(medicamentoRecetaCollectionMedicamentoRecetaToAttach);
            }
            recetaInformacionConsulta.setMedicamentoRecetaCollection(attachedMedicamentoRecetaCollection);
            em.persist(recetaInformacionConsulta);
            if (recetaInformacionEspecial != null) {
                recetaInformacionEspecial.getRecetaInformacionConsultaCollection().add(recetaInformacionConsulta);
                recetaInformacionEspecial = em.merge(recetaInformacionEspecial);
            }
            for (ClienteReceta clienteRecetaCollectionClienteReceta : recetaInformacionConsulta.getClienteRecetaCollection()) {
                RecetaInformacionConsulta oldRecetaInformacionConsultaOfClienteRecetaCollectionClienteReceta = clienteRecetaCollectionClienteReceta.getRecetaInformacionConsulta();
                clienteRecetaCollectionClienteReceta.setRecetaInformacionConsulta(recetaInformacionConsulta);
                clienteRecetaCollectionClienteReceta = em.merge(clienteRecetaCollectionClienteReceta);
                if (oldRecetaInformacionConsultaOfClienteRecetaCollectionClienteReceta != null) {
                    oldRecetaInformacionConsultaOfClienteRecetaCollectionClienteReceta.getClienteRecetaCollection().remove(clienteRecetaCollectionClienteReceta);
                    oldRecetaInformacionConsultaOfClienteRecetaCollectionClienteReceta = em.merge(oldRecetaInformacionConsultaOfClienteRecetaCollectionClienteReceta);
                }
            }
            for (MedicoReceta medicoRecetaCollectionMedicoReceta : recetaInformacionConsulta.getMedicoRecetaCollection()) {
                RecetaInformacionConsulta oldRecetaInformacionConsultaOfMedicoRecetaCollectionMedicoReceta = medicoRecetaCollectionMedicoReceta.getRecetaInformacionConsulta();
                medicoRecetaCollectionMedicoReceta.setRecetaInformacionConsulta(recetaInformacionConsulta);
                medicoRecetaCollectionMedicoReceta = em.merge(medicoRecetaCollectionMedicoReceta);
                if (oldRecetaInformacionConsultaOfMedicoRecetaCollectionMedicoReceta != null) {
                    oldRecetaInformacionConsultaOfMedicoRecetaCollectionMedicoReceta.getMedicoRecetaCollection().remove(medicoRecetaCollectionMedicoReceta);
                    oldRecetaInformacionConsultaOfMedicoRecetaCollectionMedicoReceta = em.merge(oldRecetaInformacionConsultaOfMedicoRecetaCollectionMedicoReceta);
                }
            }
            for (MedicamentoReceta medicamentoRecetaCollectionMedicamentoReceta : recetaInformacionConsulta.getMedicamentoRecetaCollection()) {
                RecetaInformacionConsulta oldRecetaInformacionConsultaOfMedicamentoRecetaCollectionMedicamentoReceta = medicamentoRecetaCollectionMedicamentoReceta.getRecetaInformacionConsulta();
                medicamentoRecetaCollectionMedicamentoReceta.setRecetaInformacionConsulta(recetaInformacionConsulta);
                medicamentoRecetaCollectionMedicamentoReceta = em.merge(medicamentoRecetaCollectionMedicamentoReceta);
                if (oldRecetaInformacionConsultaOfMedicamentoRecetaCollectionMedicamentoReceta != null) {
                    oldRecetaInformacionConsultaOfMedicamentoRecetaCollectionMedicamentoReceta.getMedicamentoRecetaCollection().remove(medicamentoRecetaCollectionMedicamentoReceta);
                    oldRecetaInformacionConsultaOfMedicamentoRecetaCollectionMedicamentoReceta = em.merge(oldRecetaInformacionConsultaOfMedicamentoRecetaCollectionMedicamentoReceta);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findRecetaInformacionConsulta(recetaInformacionConsulta.getIdReceta()) != null) {
                throw new PreexistingEntityException("RecetaInformacionConsulta " + recetaInformacionConsulta + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(RecetaInformacionConsulta recetaInformacionConsulta) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            RecetaInformacionConsulta persistentRecetaInformacionConsulta = em.find(RecetaInformacionConsulta.class, recetaInformacionConsulta.getIdReceta());
            RecetaInformacionEspecial recetaInformacionEspecialOld = persistentRecetaInformacionConsulta.getRecetaInformacionEspecial();
            RecetaInformacionEspecial recetaInformacionEspecialNew = recetaInformacionConsulta.getRecetaInformacionEspecial();
            Collection<ClienteReceta> clienteRecetaCollectionOld = persistentRecetaInformacionConsulta.getClienteRecetaCollection();
            Collection<ClienteReceta> clienteRecetaCollectionNew = recetaInformacionConsulta.getClienteRecetaCollection();
            Collection<MedicoReceta> medicoRecetaCollectionOld = persistentRecetaInformacionConsulta.getMedicoRecetaCollection();
            Collection<MedicoReceta> medicoRecetaCollectionNew = recetaInformacionConsulta.getMedicoRecetaCollection();
            Collection<MedicamentoReceta> medicamentoRecetaCollectionOld = persistentRecetaInformacionConsulta.getMedicamentoRecetaCollection();
            Collection<MedicamentoReceta> medicamentoRecetaCollectionNew = recetaInformacionConsulta.getMedicamentoRecetaCollection();
            List<String> illegalOrphanMessages = null;
            for (ClienteReceta clienteRecetaCollectionOldClienteReceta : clienteRecetaCollectionOld) {
                if (!clienteRecetaCollectionNew.contains(clienteRecetaCollectionOldClienteReceta)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ClienteReceta " + clienteRecetaCollectionOldClienteReceta + " since its recetaInformacionConsulta field is not nullable.");
                }
            }
            for (MedicoReceta medicoRecetaCollectionOldMedicoReceta : medicoRecetaCollectionOld) {
                if (!medicoRecetaCollectionNew.contains(medicoRecetaCollectionOldMedicoReceta)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain MedicoReceta " + medicoRecetaCollectionOldMedicoReceta + " since its recetaInformacionConsulta field is not nullable.");
                }
            }
            for (MedicamentoReceta medicamentoRecetaCollectionOldMedicamentoReceta : medicamentoRecetaCollectionOld) {
                if (!medicamentoRecetaCollectionNew.contains(medicamentoRecetaCollectionOldMedicamentoReceta)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain MedicamentoReceta " + medicamentoRecetaCollectionOldMedicamentoReceta + " since its recetaInformacionConsulta field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (recetaInformacionEspecialNew != null) {
                recetaInformacionEspecialNew = em.getReference(recetaInformacionEspecialNew.getClass(), recetaInformacionEspecialNew.getRecetaInformacionEspecialPK());
                recetaInformacionConsulta.setRecetaInformacionEspecial(recetaInformacionEspecialNew);
            }
            Collection<ClienteReceta> attachedClienteRecetaCollectionNew = new ArrayList<ClienteReceta>();
            for (ClienteReceta clienteRecetaCollectionNewClienteRecetaToAttach : clienteRecetaCollectionNew) {
                clienteRecetaCollectionNewClienteRecetaToAttach = em.getReference(clienteRecetaCollectionNewClienteRecetaToAttach.getClass(), clienteRecetaCollectionNewClienteRecetaToAttach.getClienteRecetaPK());
                attachedClienteRecetaCollectionNew.add(clienteRecetaCollectionNewClienteRecetaToAttach);
            }
            clienteRecetaCollectionNew = attachedClienteRecetaCollectionNew;
            recetaInformacionConsulta.setClienteRecetaCollection(clienteRecetaCollectionNew);
            Collection<MedicoReceta> attachedMedicoRecetaCollectionNew = new ArrayList<MedicoReceta>();
            for (MedicoReceta medicoRecetaCollectionNewMedicoRecetaToAttach : medicoRecetaCollectionNew) {
                medicoRecetaCollectionNewMedicoRecetaToAttach = em.getReference(medicoRecetaCollectionNewMedicoRecetaToAttach.getClass(), medicoRecetaCollectionNewMedicoRecetaToAttach.getMedicoRecetaPK());
                attachedMedicoRecetaCollectionNew.add(medicoRecetaCollectionNewMedicoRecetaToAttach);
            }
            medicoRecetaCollectionNew = attachedMedicoRecetaCollectionNew;
            recetaInformacionConsulta.setMedicoRecetaCollection(medicoRecetaCollectionNew);
            Collection<MedicamentoReceta> attachedMedicamentoRecetaCollectionNew = new ArrayList<MedicamentoReceta>();
            for (MedicamentoReceta medicamentoRecetaCollectionNewMedicamentoRecetaToAttach : medicamentoRecetaCollectionNew) {
                medicamentoRecetaCollectionNewMedicamentoRecetaToAttach = em.getReference(medicamentoRecetaCollectionNewMedicamentoRecetaToAttach.getClass(), medicamentoRecetaCollectionNewMedicamentoRecetaToAttach.getMedicamentoRecetaPK());
                attachedMedicamentoRecetaCollectionNew.add(medicamentoRecetaCollectionNewMedicamentoRecetaToAttach);
            }
            medicamentoRecetaCollectionNew = attachedMedicamentoRecetaCollectionNew;
            recetaInformacionConsulta.setMedicamentoRecetaCollection(medicamentoRecetaCollectionNew);
            recetaInformacionConsulta = em.merge(recetaInformacionConsulta);
            if (recetaInformacionEspecialOld != null && !recetaInformacionEspecialOld.equals(recetaInformacionEspecialNew)) {
                recetaInformacionEspecialOld.getRecetaInformacionConsultaCollection().remove(recetaInformacionConsulta);
                recetaInformacionEspecialOld = em.merge(recetaInformacionEspecialOld);
            }
            if (recetaInformacionEspecialNew != null && !recetaInformacionEspecialNew.equals(recetaInformacionEspecialOld)) {
                recetaInformacionEspecialNew.getRecetaInformacionConsultaCollection().add(recetaInformacionConsulta);
                recetaInformacionEspecialNew = em.merge(recetaInformacionEspecialNew);
            }
            for (ClienteReceta clienteRecetaCollectionNewClienteReceta : clienteRecetaCollectionNew) {
                if (!clienteRecetaCollectionOld.contains(clienteRecetaCollectionNewClienteReceta)) {
                    RecetaInformacionConsulta oldRecetaInformacionConsultaOfClienteRecetaCollectionNewClienteReceta = clienteRecetaCollectionNewClienteReceta.getRecetaInformacionConsulta();
                    clienteRecetaCollectionNewClienteReceta.setRecetaInformacionConsulta(recetaInformacionConsulta);
                    clienteRecetaCollectionNewClienteReceta = em.merge(clienteRecetaCollectionNewClienteReceta);
                    if (oldRecetaInformacionConsultaOfClienteRecetaCollectionNewClienteReceta != null && !oldRecetaInformacionConsultaOfClienteRecetaCollectionNewClienteReceta.equals(recetaInformacionConsulta)) {
                        oldRecetaInformacionConsultaOfClienteRecetaCollectionNewClienteReceta.getClienteRecetaCollection().remove(clienteRecetaCollectionNewClienteReceta);
                        oldRecetaInformacionConsultaOfClienteRecetaCollectionNewClienteReceta = em.merge(oldRecetaInformacionConsultaOfClienteRecetaCollectionNewClienteReceta);
                    }
                }
            }
            for (MedicoReceta medicoRecetaCollectionNewMedicoReceta : medicoRecetaCollectionNew) {
                if (!medicoRecetaCollectionOld.contains(medicoRecetaCollectionNewMedicoReceta)) {
                    RecetaInformacionConsulta oldRecetaInformacionConsultaOfMedicoRecetaCollectionNewMedicoReceta = medicoRecetaCollectionNewMedicoReceta.getRecetaInformacionConsulta();
                    medicoRecetaCollectionNewMedicoReceta.setRecetaInformacionConsulta(recetaInformacionConsulta);
                    medicoRecetaCollectionNewMedicoReceta = em.merge(medicoRecetaCollectionNewMedicoReceta);
                    if (oldRecetaInformacionConsultaOfMedicoRecetaCollectionNewMedicoReceta != null && !oldRecetaInformacionConsultaOfMedicoRecetaCollectionNewMedicoReceta.equals(recetaInformacionConsulta)) {
                        oldRecetaInformacionConsultaOfMedicoRecetaCollectionNewMedicoReceta.getMedicoRecetaCollection().remove(medicoRecetaCollectionNewMedicoReceta);
                        oldRecetaInformacionConsultaOfMedicoRecetaCollectionNewMedicoReceta = em.merge(oldRecetaInformacionConsultaOfMedicoRecetaCollectionNewMedicoReceta);
                    }
                }
            }
            for (MedicamentoReceta medicamentoRecetaCollectionNewMedicamentoReceta : medicamentoRecetaCollectionNew) {
                if (!medicamentoRecetaCollectionOld.contains(medicamentoRecetaCollectionNewMedicamentoReceta)) {
                    RecetaInformacionConsulta oldRecetaInformacionConsultaOfMedicamentoRecetaCollectionNewMedicamentoReceta = medicamentoRecetaCollectionNewMedicamentoReceta.getRecetaInformacionConsulta();
                    medicamentoRecetaCollectionNewMedicamentoReceta.setRecetaInformacionConsulta(recetaInformacionConsulta);
                    medicamentoRecetaCollectionNewMedicamentoReceta = em.merge(medicamentoRecetaCollectionNewMedicamentoReceta);
                    if (oldRecetaInformacionConsultaOfMedicamentoRecetaCollectionNewMedicamentoReceta != null && !oldRecetaInformacionConsultaOfMedicamentoRecetaCollectionNewMedicamentoReceta.equals(recetaInformacionConsulta)) {
                        oldRecetaInformacionConsultaOfMedicamentoRecetaCollectionNewMedicamentoReceta.getMedicamentoRecetaCollection().remove(medicamentoRecetaCollectionNewMedicamentoReceta);
                        oldRecetaInformacionConsultaOfMedicamentoRecetaCollectionNewMedicamentoReceta = em.merge(oldRecetaInformacionConsultaOfMedicamentoRecetaCollectionNewMedicamentoReceta);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = recetaInformacionConsulta.getIdReceta();
                if (findRecetaInformacionConsulta(id) == null) {
                    throw new NonexistentEntityException("The recetaInformacionConsulta with id " + id + " no longer exists.");
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
            RecetaInformacionConsulta recetaInformacionConsulta;
            try {
                recetaInformacionConsulta = em.getReference(RecetaInformacionConsulta.class, id);
                recetaInformacionConsulta.getIdReceta();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The recetaInformacionConsulta with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<ClienteReceta> clienteRecetaCollectionOrphanCheck = recetaInformacionConsulta.getClienteRecetaCollection();
            for (ClienteReceta clienteRecetaCollectionOrphanCheckClienteReceta : clienteRecetaCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This RecetaInformacionConsulta (" + recetaInformacionConsulta + ") cannot be destroyed since the ClienteReceta " + clienteRecetaCollectionOrphanCheckClienteReceta + " in its clienteRecetaCollection field has a non-nullable recetaInformacionConsulta field.");
            }
            Collection<MedicoReceta> medicoRecetaCollectionOrphanCheck = recetaInformacionConsulta.getMedicoRecetaCollection();
            for (MedicoReceta medicoRecetaCollectionOrphanCheckMedicoReceta : medicoRecetaCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This RecetaInformacionConsulta (" + recetaInformacionConsulta + ") cannot be destroyed since the MedicoReceta " + medicoRecetaCollectionOrphanCheckMedicoReceta + " in its medicoRecetaCollection field has a non-nullable recetaInformacionConsulta field.");
            }
            Collection<MedicamentoReceta> medicamentoRecetaCollectionOrphanCheck = recetaInformacionConsulta.getMedicamentoRecetaCollection();
            for (MedicamentoReceta medicamentoRecetaCollectionOrphanCheckMedicamentoReceta : medicamentoRecetaCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This RecetaInformacionConsulta (" + recetaInformacionConsulta + ") cannot be destroyed since the MedicamentoReceta " + medicamentoRecetaCollectionOrphanCheckMedicamentoReceta + " in its medicamentoRecetaCollection field has a non-nullable recetaInformacionConsulta field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            RecetaInformacionEspecial recetaInformacionEspecial = recetaInformacionConsulta.getRecetaInformacionEspecial();
            if (recetaInformacionEspecial != null) {
                recetaInformacionEspecial.getRecetaInformacionConsultaCollection().remove(recetaInformacionConsulta);
                recetaInformacionEspecial = em.merge(recetaInformacionEspecial);
            }
            em.remove(recetaInformacionConsulta);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<RecetaInformacionConsulta> findRecetaInformacionConsultaEntities() {
        return findRecetaInformacionConsultaEntities(true, -1, -1);
    }

    public List<RecetaInformacionConsulta> findRecetaInformacionConsultaEntities(int maxResults, int firstResult) {
        return findRecetaInformacionConsultaEntities(false, maxResults, firstResult);
    }

    private List<RecetaInformacionConsulta> findRecetaInformacionConsultaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(RecetaInformacionConsulta.class));
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

    public RecetaInformacionConsulta findRecetaInformacionConsulta(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(RecetaInformacionConsulta.class, id);
        } finally {
            em.close();
        }
    }

    public int getRecetaInformacionConsultaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<RecetaInformacionConsulta> rt = cq.from(RecetaInformacionConsulta.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
