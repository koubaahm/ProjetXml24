package fr.univrouen.ProjetXML.controller;

import fr.univrouen.ProjetXML.repository.CV24Repository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import fr.univrouen.ProjetXML.entities.CV24;
import fr.univrouen.ProjetXML.services.BindingXmlService;
import fr.univrouen.ProjetXML.services.CvService;
import lombok.AllArgsConstructor;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.util.List;



/**
 * contrôleur responsable de la gestion des requêtes liées aux CV.
 */
@Controller
@AllArgsConstructor
@RequestMapping("/cv")
public class CvController {

    private final CvService cvService;
    private final BindingXmlService bindingXmlService;
    private final CV24Repository cv24Repository;

    /**
     * récupère tous les CV.
     *
     * @return une liste de tous les CV
     */
    @GetMapping("/all")
    public String getAllCv24(Model model) {
        // Récupérer tous les CVs du service
        List<CV24> cvList = cvService.getAllCv24();

        // Ajouter la liste de CVs au modèle pour l'utiliser dans la vue
        model.addAttribute("CV24", cvList);

        // Retourner le nom de la vue Thymeleaf
        return "cvs";
    }

    /**
     * récupère un CV par son ID.
     *
     * @param id    l'ID du CV à récupérer
     * @param model le modèle pour la vue
     * @return le nom de la vue à afficher
     */

    @GetMapping("/{id}")
    public String getCvById(@PathVariable Long id, Model model) {
        try {
            // Chemin absolu vers le fichier XSLT
            File xsltFile = new ClassPathResource("cv24.tp4.xslt").getFile();

            // Vérifie que la ressource XSLT existe
            if (!xsltFile.exists()) {
                throw new IOException("XSLT file not found at specified path");
            }

            // Trouve le CV par son ID ou lance une exception s'il n'est pas trouvé
            CV24 cv = cvService.getCvById(id);

            // Convertit l'entité CV en données XML
            String xmlData = bindingXmlService.convertEntityToXml(cv);

            // Applique la transformation XSLT
            String htmlData = bindingXmlService.applyXsltTransformation(xmlData, xsltFile);

            // Affiche le contenu HTML de la vue cvDisplay dans la console
            System.out.println("HTML Content of cvDisplay view: \n" + htmlData);

            // Ajoute le contenu HTML au modèle pour qu'il soit affiché dans la vue
            model.addAttribute("cvHtml", htmlData);

            // Retourne le nom de la vue à afficher
            return "cvDetail";
        } catch (Exception e) {
            // Gère les exceptions et affiche une page d'erreur si nécessaire
            model.addAttribute("error", e.getMessage());
            return "errorPage"; // Retourne le nom de la vue d'erreur
        }
    }



    /**
     * enregistre un nouveau CV.
     *
     * @param xmlData le XML représentant le CV à enregistrer
     * @return une réponse HTTP indiquant le succès ou l'échec de l'opération
     */
    @PostMapping
    public ResponseEntity<String> saveCv(@RequestBody String xmlData) {
        return cvService.saveCv(xmlData);
    }

    /**
     * met à jour un CV existant.
     *
     * @param id      l'ID du CV à mettre à jour
     * @param xmlData le XML représentant le CV mis à jour
     * @return une réponse HTTP indiquant le succès ou l'échec de l'opération
     */
    @PutMapping("/{id}")
    public ResponseEntity<String> updateCv(@PathVariable Long id, @RequestBody String xmlData) {
        return cvService.updateCv(id, xmlData);
    }

    /**
     * supprime un CV par son ID.
     *
     * @param id l'ID du CV à supprimer
     * @return une réponse HTTP indiquant le succès ou l'échec de l'opération
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCv(@PathVariable Long id) {
        cvService.deleteCv(id);
        return ResponseEntity.ok("CV supprimé avec succès !");
    }


}
