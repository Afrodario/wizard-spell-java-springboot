package it.softwareInside.WizardSpellsApi.restController;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.ByteArrayInputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.softwareInside.WizardSpellsApi.service.PdfService;
import it.softwareInside.WizardSpellsApi.service.SpellService;

@RequestMapping("/api/")
@RestController
public class SpellController {
	
	@Autowired
	SpellService spellService;
	
	@Autowired
	PdfService pdfService;
	
	@GetMapping("/aggiungi")
	public boolean getSpell(@RequestParam("id") String id) {
		return spellService.addSpellToDB(id);
	}
	
	@GetMapping("/aggiungi-random")
	public boolean getRandomSpell() {
		return spellService.addRandomSpell();
	}
	
	@GetMapping("/aggiungi-tutto")
	public boolean getAllSpells() {
		return spellService.addAllSpellsToDB();
	}
	
	@GetMapping("/cancella-tutto")
	public void deleteAllSpells() {
		spellService.deleteAllSpells();
	}
	
	@RequestMapping(value = "/pdf", method = RequestMethod.GET, produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> generaSpellPDF() {
    	try {
            ByteArrayInputStream bis = pdfService.generaPDF();
            var headers = new HttpHeaders();
            headers.add("Content-Disposition", "inline; filename=example.pdf");
            return ResponseEntity
                    .ok()
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(new InputStreamResource(bis));
    	}catch (Exception e) {
    		System.out.println(e);
			return null;
    	}
    }
}
