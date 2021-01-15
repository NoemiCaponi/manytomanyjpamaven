package it.manytomanyjpamaven.test;

import java.util.Date;
import java.util.List;

import it.manytomanyjpamaven.dao.EntityManagerUtil;
import it.manytomanyjpamaven.model.Ruolo;
import it.manytomanyjpamaven.model.StatoUtente;
import it.manytomanyjpamaven.model.Utente;
import it.manytomanyjpamaven.service.MyServiceFactory;
import it.manytomanyjpamaven.service.RuoloService;
import it.manytomanyjpamaven.service.UtenteService;

public class ManyToManyTest {

	public static void main(String[] args) {
		UtenteService utenteServiceInstance = MyServiceFactory.getUtenteServiceInstance();
		RuoloService ruoloServiceInstance = MyServiceFactory.getRuoloServiceInstance();

		// ora passo alle operazioni CRUD
		try {

			// inizializzo i ruoli sul db
			initRuoli(ruoloServiceInstance);

			System.out.println("Elenca utenti: ");
			for (Utente utenteItem : utenteServiceInstance.listAll()) {
				System.out.println(utenteItem);
			}

			System.out.println("Ruoli presenti sul database: ");
			for (Ruolo ruoloItem : ruoloServiceInstance.listAll()) {
				System.out.println(ruoloItem);
			}

//			Utente utenteNuovo = new Utente("mario.rossi", "xxx", "Mario", "Rossi", new Date());
//			utenteServiceInstance.inserisciNuovo(utenteNuovo);
//
//			Ruolo ruoloDaDb = ruoloServiceInstance.caricaSingoloElemento(1L);
//
//			Utente utenteDaDb = utenteServiceInstance.listAll().stream().findFirst().orElse(null);
//			if (utenteDaDb != null) {
//				utenteServiceInstance.aggiungiRuolo(utenteDaDb, ruoloDaDb);
//			}

//			Ruolo ruoloProva=ruoloServiceInstance.listAll().stream().findFirst().orElse(null);
//			if(ruoloProva!=null) {
//				ruoloProva.setDescrizione("Amministratore");
//				ruoloServiceInstance.aggiorna(ruoloProva);
//				System.out.println("Nuova descrizione: "+ruoloProva.getDescrizione());
//			}

			// test Delete

			Ruolo ruoloDaCancellare = ruoloServiceInstance.caricaSingoloElemento(4L);
			// Ruolo
			// ruoloDaCancellare=ruoloServiceInstance.cercaPerDescrizione("Amministratore");
			if (ruoloDaCancellare != null) {
				System.out.println("Vado a cancellare: " + ruoloDaCancellare);
				ruoloServiceInstance.rimuovi(ruoloDaCancellare);
				ruoloDaCancellare = ruoloServiceInstance.caricaSingoloElemento(1L);
				if (ruoloDaCancellare == null)
					System.out.println("Cancellazione eseeguita");
				else
					System.out.println("Cancellazione non eseguita");
			}

//			Ruolo test=ruoloServiceInstance.cercaPerDescrizione("Amministratore");
//			List<Utente> prova=utenteServiceInstance.findByRuolo(test);
//			for(Utente utenteItem:prova)
//				System.out.println(utenteItem);
//
//			Ruolo prova=ruoloServiceInstance.cercaPerDescrizione("Amministratore");
//			System.out.println(prova);

//			// proviamo a passarlo nello stato ATTIVO
//			Utente utenteDaDb2 = utenteServiceInstance.listAll().stream().findFirst().orElse(null);
//			if (utenteDaDb2 != null) {
//				System.out.println(
//						"stato attuale dell'utente :" + utenteDaDb2.getUsername() + " " + utenteDaDb2.getStato());
//				utenteDaDb2.setStato(StatoUtente.ATTIVO);
//				utenteServiceInstance.aggiorna(utenteDaDb2);
//				System.out.println(
//						"stato nuovo dell'utente :" + utenteDaDb2.getUsername() + " " + utenteDaDb2.getStato());
//			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// questa è necessaria per chiudere tutte le connessioni quindi rilasciare il
			// main
			EntityManagerUtil.shutdown();
		}

	}

	private static void initRuoli(RuoloService ruoloServiceInstance) throws Exception {
		if (ruoloServiceInstance.cercaPerDescrizioneECodice("Administrator", "ROLE_ADMIN") == null) {
			ruoloServiceInstance.inserisciNuovo(new Ruolo("Administrator", "ROLE_ADMIN"));
		}

		if (ruoloServiceInstance.cercaPerDescrizioneECodice("Classic User", "ROLE_CLASSIC_USER") == null) {
			ruoloServiceInstance.inserisciNuovo(new Ruolo("Classic User", "ROLE_CLASSIC_USER"));
		}
	}

}
