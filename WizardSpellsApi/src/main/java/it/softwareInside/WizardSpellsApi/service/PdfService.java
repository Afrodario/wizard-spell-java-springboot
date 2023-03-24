package it.softwareInside.WizardSpellsApi.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.swing.text.StyleConstants.ColorConstants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itextpdf.text.Image;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.layout.element.Text;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.List;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import it.softwareInside.WizardSpellsApi.models.Spell;
import it.softwareInside.WizardSpellsApi.repository.SpellRepository;

@Service
public class PdfService {
	
	@Autowired
	SpellRepository spellRepository;
	
	@Autowired
	SpellService spellService;
	
	public Spell getSpell() {
		return spellService.getAllSpells()[0];
	}
	
	
	public ByteArrayInputStream generaPDF() throws IOException {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            Text whiteText = new Text("Incantesimi");
            whiteText.setFontColor(Color.WHITE);
        	Paragraph paragrafo = new Paragraph("Incantesimi: ");
            List list = new List();
            
            // The following line to prevent the "Server returned 
            // HTTP response code: 403" error.
            //IMPOSTAZIONI IMMAGINE
            System.setProperty("http.agent", "Chrome");
            String url = "https://tribality.com/wp-content/uploads/2015/11/harry_potter_spells_infographic_by_seanchunseianliew-d4g8n37.jpg";
            Image image = Image.getInstance(url);
            image.setAbsolutePosition(0, 0);
            image.scaleToFit(826, 1100);
            
            //INSTESTAZIONI
            for (int i = 0; i < 10; i++) {
            	String incantesimo = spellService.getAllSpells()[i].getIncantation();
            	String effetto = spellService.getAllSpells()[i].getEffect();
				list.add("Nome incantesimo: " + " " + incantesimo + "\n Effetto: " + effetto);
			}
     
            PdfWriter.getInstance(document, out);
            document.open();
            document.add(image);
            document.add(paragrafo);
            document.add(list);
            document.close();
        } catch (DocumentException ex) {
           return null;
        }
        return new ByteArrayInputStream(out.toByteArray());
    }
	
	
	
}
