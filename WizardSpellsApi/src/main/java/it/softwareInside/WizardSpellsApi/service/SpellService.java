package it.softwareInside.WizardSpellsApi.service;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import it.softwareInside.WizardSpellsApi.models.Spell;
import it.softwareInside.WizardSpellsApi.repository.SpellRepository;

@Service
public class SpellService {

	@Autowired
	SpellRepository spellRepository;
	
	/**
	 * Metodo che prende in ingresso un ID e, tramite il RestTemplate, recupera uno specifico oggetto
	 * dall'API, mappato nella classe POJO Spell, e ritorna quel singolo oggetto Spell
	 * @param id
	 * @return
	 */
	public Spell getSpell(String id) {
		RestTemplate restTemplate = new RestTemplate();
		Spell ris = restTemplate.getForObject("https://wizard-world-api.herokuapp.com/Spells/" + id, Spell.class);
		return ris;
	}
	
	/**
	 * Metodo che utilizza la ResponseEntity per recuperare tutti gli oggetti dall'API sottoforma di
	 * Array di oggetti di tipo Spell e ritorna quello stesso Array
	 * @return
	 */
	public Spell[] getAllSpells() {
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Spell[]> response = restTemplate.getForEntity("https://wizard-world-api.herokuapp.com/Spells/", Spell[].class);
		Spell[] spells = response.getBody();
		return spells;
		
	}
	
	/**
	 * Metodo che accetta in ingresso un ID e sfrutta il metodo getSpell per salvare a DB uno specifico
	 * oggetto di tipo Spell. Ritorna true se l'operazione è andata a buon fine, altrimenti false 
	 * @param id
	 * @return
	 */
	public boolean addSpellToDB(String id) {
		try {
			spellRepository.save(this.getSpell(id));
			return true;
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}
	}
	
	/**
	 * Metodo che recupera un Array di oggetti di tipo Spell recuperato dal metodo getAllSpells.
	 * Cicla tutto l'Array e aggiunge ogni iterazione al DB. Ritorna true se l'operazione è andata
	 * a buon fine, altrimenti false.
	 * @return
	 */
	public boolean addAllSpellsToDB() {
		try {
			Spell[] spells = this.getAllSpells();
			for (int i = 0; i < spells.length; i++) {
				spellRepository.save(spells[i]);
			}
			return true;
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}
	}
	
	/**
	 * Metodo void che cancella tutti gli elementi presenti nel DB.
	 */
	public void deleteAllSpells() {
		try {
			spellRepository.deleteAll();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public Iterable<Spell> findAllSpells(){
        return spellRepository.findAll();
    }
	
    /**
     * il metodo restituisce un oggetto di tipo Spell preso dall'array che viene restituito dall'API
     * 
     * @return
     */
    public Spell[] getArraySpell(){
        RestTemplate rt = new RestTemplate();
        ResponseEntity<Spell[]> spell = rt.getForEntity("https://wizard-world-api.herokuapp.com/Spells/", Spell[].class);
        return spell.getBody();
    }
    
    /**
     * il metodo restituisce il numero di incantesimi presenti nell'API
     * 
     * lo useremo nel metodo addRandomSpell per aggiungere un incantesimo dell'API al nostro database,
     * senza dover passare un id al metodo
     * 
     * @return
     */
    public int quantita(){
        try {
            int quantita = getArraySpell().length;
            return quantita;
            
        } catch (Exception e) {
            System.out.println("errore: " + e);
            return -1;
        }
    }
    
    /**
     * Metodo che aggiunge un oggetto di tipo Spell casuale a DB, ritornando true se l'operazione
     * è andata a buon fine, altrimenti false.
     * @return
     */
    public boolean addRandomSpell(){
        try {
            Random casuale = new Random();
            spellRepository.save(getArraySpell()[casuale.nextInt(0, quantita())]);
            return true;
        } catch (Exception e) {
            System.out.println("errore: " + e);
            return false;
        }
    }
	
}
