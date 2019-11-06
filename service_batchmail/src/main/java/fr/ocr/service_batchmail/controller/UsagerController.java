package fr.ocr.service_batchmail.controller;

import fr.ocr.service_batchmail.domain.PretDtoBatch;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class UsagerController {

    final   ClientPretService clientPretService;


    public UsagerController(ClientPretService clientPretService) {

        this.clientPretService = clientPretService;
    }

    @GetMapping(value = "/horsdelai")
    @ResponseBody
    public List<PretDtoBatch> getPretHorsDelai() throws Exception {
        List<PretDtoBatch> pretDtoBatchList = clientPretService.listePretHorsDelai();
        return pretDtoBatchList;
    }
}
