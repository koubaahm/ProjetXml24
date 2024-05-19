package fr.univrouen.ProjetXML.repository;

import fr.univrouen.ProjetXML.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
