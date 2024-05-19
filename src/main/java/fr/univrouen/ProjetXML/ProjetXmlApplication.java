package fr.univrouen.ProjetXML;

import fr.univrouen.ProjetXML.entities.Competence;
import fr.univrouen.ProjetXML.entities.Diplome;

import fr.univrouen.ProjetXML.repository.CompetenceRepository;
import fr.univrouen.ProjetXML.repository.DiplomeRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;


import java.util.Date;

@SpringBootApplication
public class ProjetXmlApplication {

	@Autowired
	private CompetenceRepository competenceRepository;

	@Autowired
	private DiplomeRepository diplomeRepository;

	public static void main(String[] args) {
		SpringApplication.run(ProjetXmlApplication.class, args);
	}

//	@PostConstruct
//	@Transactional  // Assure que toutes les opérations dans cette méthode sont dans une même transaction
//	public void initDatabase() {
//		// Création et enregistrement de l'objet Competence
//		Competence competence = new Competence();
//		competenceRepository.save(competence);
//
//		// Création et enregistrement de l'objet Diplome
//		Diplome diplome = new Diplome();
//		diplome.setDate(new Date());
//		diplome.setInstitut("universite de rouen");
//		diplome.setNiveau(5);
//		diplome.setCompetence(competence); // Associer competence à diplome
//		diplomeRepository.save(diplome);
//	}
}

