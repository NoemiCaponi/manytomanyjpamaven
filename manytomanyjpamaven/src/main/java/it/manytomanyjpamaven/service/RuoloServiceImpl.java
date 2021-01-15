package it.manytomanyjpamaven.service;

import java.util.List;

import javax.persistence.EntityManager;

import it.manytomanyjpamaven.dao.EntityManagerUtil;
import it.manytomanyjpamaven.dao.RuoloDAO;
import it.manytomanyjpamaven.dao.UtenteDAO;
import it.manytomanyjpamaven.dao.UtenteDAOImpl;
import it.manytomanyjpamaven.model.Ruolo;
import it.manytomanyjpamaven.model.Utente;

public class RuoloServiceImpl implements RuoloService {

	private RuoloDAO ruoloDAO;
	//private UtenteService utenteServiceInstance = MyServiceFactory.getUtenteServiceInstance();

	@Override
	public List<Ruolo> listAll() throws Exception {
		EntityManager entityManager = EntityManagerUtil.getEntityManager();
		try {
			ruoloDAO.setEntityManager(entityManager);
			return ruoloDAO.list();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			entityManager.close();
		}

	}

	@Override
	public Ruolo caricaSingoloElemento(Long id) throws Exception {
		// questo è come una connection
		EntityManager entityManager = EntityManagerUtil.getEntityManager();

		try {
			// uso l'injection per il dao
			ruoloDAO.setEntityManager(entityManager);

			// eseguo quello che realmente devo fare
			return ruoloDAO.get(id);

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			entityManager.close();
		}
	}

	@Override
	public void aggiorna(Ruolo ruoloInstance) throws Exception {
		EntityManager entityManager = EntityManagerUtil.getEntityManager();

		try {

			entityManager.getTransaction().begin();
			ruoloDAO.setEntityManager(entityManager);
			ruoloDAO.update(ruoloInstance);
			entityManager.getTransaction().commit();
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			e.printStackTrace();
			throw e;
		}

	}

	@Override
	public void inserisciNuovo(Ruolo ruoloInstance) throws Exception {
		// questo è come una connection
		EntityManager entityManager = EntityManagerUtil.getEntityManager();

		try {
			// questo è come il MyConnection.getConnection()
			entityManager.getTransaction().begin();

			// uso l'injection per il dao
			ruoloDAO.setEntityManager(entityManager);

			// eseguo quello che realmente devo fare
			ruoloDAO.insert(ruoloInstance);

			entityManager.getTransaction().commit();
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			e.printStackTrace();
			throw e;
		}

	}

	@Override
	public void rimuovi(Ruolo ruoloInstance) throws Exception {
		EntityManager entityManager = EntityManagerUtil.getEntityManager();
		UtenteDAO utenteDAO=new UtenteDAOImpl();

		try {

			entityManager.getTransaction().begin();
			
			utenteDAO.setEntityManager(entityManager);

			List<Utente> lista = utenteDAO.getEagerRuolo(ruoloInstance);
			for (Utente utenteItem : lista) {
				utenteItem.getRuoli().remove(ruoloInstance);
				utenteDAO.update(utenteItem);
			}

			ruoloDAO.setEntityManager(entityManager);
			ruoloDAO.delete(ruoloInstance);
			entityManager.getTransaction().commit();
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public void setRuoloDAO(RuoloDAO ruoloDAO) {
		this.ruoloDAO = ruoloDAO;
	}

	@Override
	public Ruolo cercaPerDescrizioneECodice(String descrizione, String codice) throws Exception {
		// questo è come una connection
		EntityManager entityManager = EntityManagerUtil.getEntityManager();

		try {
			// uso l'injection per il dao
			ruoloDAO.setEntityManager(entityManager);

			// eseguo quello che realmente devo fare
			return ruoloDAO.findByDescrizioneAndCodice(descrizione, codice);

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			entityManager.close();
		}
	}

	@Override
	public Ruolo cercaPerDescrizione(String descrizione) throws Exception {
		EntityManager entityManager = EntityManagerUtil.getEntityManager();

		try {
			ruoloDAO.setEntityManager(entityManager);
			return ruoloDAO.findByDescrizione(descrizione);

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

	}

}
