package fr.univrouen.ProjetXML.repository;

import fr.univrouen.ProjetXML.entities.Certif;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface CertifRepository extends JpaRepository <Certif,Long> {
}
