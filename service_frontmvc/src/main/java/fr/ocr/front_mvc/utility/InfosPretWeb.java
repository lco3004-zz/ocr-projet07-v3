package fr.ocr.front_mvc.utility;


import fr.ocr.front_mvc.model.PretWeb;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public final  class InfosPretWeb {

    private Integer idUser;
    private Integer idOuvrage;

    public static ResponseEntity<Map<String, Object>> formeReponseEntity(HttpServletResponse httpResponse,
                                                                         PretWeb pretWeb) {

        Map<String, Object> stringObjectMap = new HashMap<>();

        stringObjectMap.put("ouvrageIdouvrage",pretWeb.getOuvrageIdouvrage());
        stringObjectMap.put("userIduser",pretWeb.getUserIduser());
        stringObjectMap.put("pretprolonge",pretWeb.getPretprolonge());
        stringObjectMap.put("dateEmprunt",pretWeb.getDateEmprunt());
        stringObjectMap.put("auteur",pretWeb.getAuteur());
        stringObjectMap.put("titre",pretWeb.getTitre());

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();

        ResponseEntity.BodyBuilder bodyBuilder = ResponseEntity.status(httpResponse.getStatus());

        bodyBuilder.location(location).header("Content-Type", "application/json");

        return bodyBuilder.body(stringObjectMap);

    }

    public static ResponseEntity<List<PretWeb>> formeReponseEntity(HttpServletResponse httpResponse,
                                                                         List<PretWeb> pretWebs) {
        List<PretWeb> pretWebArrayList = new ArrayList<>();

        for (PretWeb pretWeb :pretWebs) {
            pretWebArrayList.add(pretWeb);
        }

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();

        ResponseEntity.BodyBuilder bodyBuilder = ResponseEntity.status(httpResponse.getStatus());

        bodyBuilder.location(location).header("Content-Type", "application/json");

        return bodyBuilder.body(pretWebArrayList);

    }
}