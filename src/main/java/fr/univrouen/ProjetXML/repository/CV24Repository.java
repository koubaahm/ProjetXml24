package fr.univrouen.ProjetXML.repository;

import fr.univrouen.ProjetXML.entities.CV24;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CV24Repository extends JpaRepository<CV24,Long> {
//    // Utilisation de JPQL avec JOIN FETCH pour précharger les compétences et leurs relations
//    @Query("SELECT cv FROM CV24 cv LEFT JOIN FETCH cv.competence competence LEFT JOIN FETCH competence.diplomes LEFT JOIN FETCH competence.certifs")
//    List<CV24> findAllWithCompetences();
}
