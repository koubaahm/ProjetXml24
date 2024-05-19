package fr.univrouen.ProjetXML.services;

import fr.univrouen.ProjetXML.entities.CV24;
import fr.univrouen.ProjetXML.repository.CV24Repository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.util.List;

/**
 * Service responsable de la gestion des CV (CRUD).
 */
@Service
@AllArgsConstructor
public class CvService {

    private final CV24Repository cv24Repository;
    private final BindingXmlService bindingXmlService;

    /**
     * récupère tous les CV.
     *
     * @return une liste de tous les CV
     */
    public List<CV24> getAllCv24() {
        return cv24Repository.findAll();
    }

    /**
     * récupère un CV par son id.
     *
     * @param id l'id du CV à récupérer
     * @return le CV correspondant à l'id spécifié, ou null s'il n'existe pas
     */
    public CV24 getCvById(Long id) {
        return cv24Repository.findById(id).orElse(null);
    }

    /**
     * enregistre un nouveau CV.
     *
     * @param xmlData le CV à enregistrer
     * @return le CV enregistré
     */
    public ResponseEntity<String> saveCv(String xmlData) {
        try {
            // Convertir le XML en entité
            CV24 cv = bindingXmlService.convertXmlToEntity(xmlData);

            // Vérifier les données
            if (cv.getObjectif() == null || cv.getObjectif().getStatus() == null) {
                throw new RuntimeException("L'objectif ou le statut est nul après la désérialisation.");
            }

            // Sauvegarder l'entité dans la base de données
            cv24Repository.save(cv);

            return ResponseEntity.ok("CV enregistré avec succès !");
        } catch (JAXBException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Erreur lors de la conversion du XML en entité.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Erreur lors de l'enregistrement du CV.");
        }
    }

    /**
     * met à jour un CV existant.
     *
     * @param id l'id du CV à mettre à jour
     * @param xmlData le CV mis à jour
     * @return le CV mis à jour
     * @throws IllegalArgumentException si l'id du CV à mettre à jour ne correspond à aucun CV existant
     */
    public ResponseEntity<String> updateCv(Long id, String xmlData) {
        try {
            // Vérifier si le CV existe dans la base de données
            if (!cv24Repository.existsById(id)) {
                return ResponseEntity.badRequest().body("CV non trouvé !");
            }

            // Convertir le XML en entité CV24
            CV24 cvToUpdate = bindingXmlService.convertXmlToEntity(xmlData);
            cvToUpdate.setId(id);

            // Vérifier les données
            if (cvToUpdate.getObjectif() == null || cvToUpdate.getObjectif().getStatus() == null) {
                throw new RuntimeException("L'objectif ou le statut est nul après la désérialisation.");
            }

            // Mettre à jour le CV dans la base de données
            cv24Repository.save(cvToUpdate);

            return ResponseEntity.ok("CV mis à jour avec succès !");
        } catch (JAXBException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Erreur lors de la conversion du XML en entité.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Erreur lors de la mise à jour du CV.");
        }
    }

    /**
     * supprime un CV par son id.
     *
     * @param id l'id du CV à supprimer
     */
    public void deleteCv(Long id) {
        cv24Repository.deleteById(id);
    }


}
