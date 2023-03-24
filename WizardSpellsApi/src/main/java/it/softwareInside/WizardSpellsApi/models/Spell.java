package it.softwareInside.WizardSpellsApi.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Spell {
	@Id
    private String id;
	private String name;
	private String incantation;
	private String effect;
	private boolean canBeVerbal;
	private String type;
	private String light;
	private String creator;
}
