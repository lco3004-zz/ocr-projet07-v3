package fr.ocr.service_batchmail.service;

import fr.ocr.service_batchmail.domain.InfosMailDtoBatch;
import fr.ocr.service_batchmail.domain.OuvrageDtoBatch;
import fr.ocr.service_batchmail.domain.PretDtoBatch;

import fr.ocr.service_batchmail.domain.UsagerDtoBatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BatchEmailService {

    private static final Logger logger = LoggerFactory.getLogger(BatchEmailService.class);

    final PretServiceClient pretServiceClient;
    final  UsagerServiceClient usagerServiceClient;
    final OuvrageServiceClient ouvrageServiceClient;


    public BatchEmailService(PretServiceClient pretServiceClient, UsagerServiceClient usagerServiceClient, OuvrageServiceClient ouvrageServiceClient) {

        this.pretServiceClient = pretServiceClient;
        this.usagerServiceClient = usagerServiceClient;
        this.ouvrageServiceClient = ouvrageServiceClient;
    }

    public List<InfosMailDtoBatch> getPretHorsDelai()
    {
        List<InfosMailDtoBatch> infosMailDtoBatchList = new ArrayList<>();

        try {
            List<PretDtoBatch> pretDtoBatchList =  pretServiceClient.listePretHorsDelai();
            for (PretDtoBatch pretDtoBatch : pretDtoBatchList) {
                InfosMailDtoBatch infosMailDtoBatch = new InfosMailDtoBatch();

                UsagerDtoBatch usagerDtoBatch = usagerServiceClient.getUnfairBorrower(pretDtoBatch.getUsagerIdusager());

                OuvrageDtoBatch ouvrageDtoBatch = ouvrageServiceClient.getInfosOuvrage(pretDtoBatch.getOuvrageIdouvrage());

                infosMailDtoBatch.setCourriel(usagerDtoBatch.getCourriel());
                infosMailDtoBatch.setNom(usagerDtoBatch.getNom());
                infosMailDtoBatch.setDateEmprunt(pretDtoBatch.getDateEmprunt());
                infosMailDtoBatch.setTitre(ouvrageDtoBatch.getTitre());
                infosMailDtoBatch.setAuteur(ouvrageDtoBatch.getAuteur());

                infosMailDtoBatchList.add(infosMailDtoBatch);

                logger.info(infosMailDtoBatch.toString());
            }

        } catch (Exception e) {
            infosMailDtoBatchList.clear();
            logger.warn("Exception leve dans reception infos  : " +e.getLocalizedMessage() +e.getMessage());
        }
        return infosMailDtoBatchList;
    }
}
