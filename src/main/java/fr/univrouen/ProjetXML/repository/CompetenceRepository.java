package fr.univrouen.ProjetXML.repository;


import fr.univrouen.ProjetXML.entities.Competence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.webmvc.RepositoryRestController;


@RepositoryRestController
public interface CompetenceRepository extends JpaRepository<Competence,Long> {
}
