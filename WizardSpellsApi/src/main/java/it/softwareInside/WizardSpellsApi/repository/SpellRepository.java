package it.softwareInside.WizardSpellsApi.repository;

import org.springframework.data.repository.CrudRepository;

import it.softwareInside.WizardSpellsApi.models.Spell;

public interface SpellRepository extends CrudRepository<Spell, String>{

}
