package paagbi.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import paagbi.api.model.base.Eskatzailea;
import paagbi.api.model.base.Eskatzailea.Oparia;
import paagbi.api.model.services.EskatzaileaService;

@RestController
@RequestMapping(path = "/eskatzailea")
@Tag(name = "Eskatzaile kudeaketa")
public class EskatzaileaController {
    
    @Autowired
    private EskatzaileaService eskatzaileaService;

    //#region GetMethods

    @GetMapping(path = "/getAll")
    @Operation(summary = "Eskatzaile guztiak bilatzen ditu")
    private @ResponseBody Iterable<Eskatzailea> getAll() {
        return eskatzaileaService.getAll();
    }

    @GetMapping(path = "/findById/{id}")
    @Operation(summary = "Eskatzaile bat bilatzen di Id-aren arabera")
    private @ResponseBody Eskatzailea findById(@PathVariable String id) {
        return eskatzaileaService.findById(id);
    }

    @GetMapping(path = "/findByName/{name}")
    @Operation(summary = "Eskatzaile bat bilatzen du Izena-ren arabera")
    private @ResponseBody Eskatzailea findByName(@PathVariable String name) {
        return eskatzaileaService.findByName(name);
    }

    @GetMapping(path = "/findByMinimunGifts/{minGifts}")
    @Operation(summary = "Eskuratu Eskatzaileak guztiak gutxieneko Opariak kopuruarekin")
    private @ResponseBody Iterable<Eskatzailea> findByMinimunGifts(@PathVariable int minGifts) {
        return eskatzaileaService.findByMinimunGifts(minGifts);
    }

    @GetMapping(path = "/findByGiftPriority/{priority}")
    @Operation(summary = "Lehentasuna duten opariak dituzten Eskatzaileak guztiak lortzea")
    private @ResponseBody Iterable<Eskatzailea> findByGiftPriority(@PathVariable int priority) {
        return eskatzaileaService.findByGiftPriority(priority);
    }

    @GetMapping(path = "/findByGiftRecipientName/{recipietName}")
    @Operation(summary = "Bilatu Eskatzaileak opariaren hartzailearen izenaren arabera (Nori)")
    private @ResponseBody Iterable<Eskatzailea> findByGiftRecipientName(@PathVariable String recipietName) {
        return eskatzaileaService.findByGiftRecipientName(recipietName);
    }

    @GetMapping(path = "/findByGiftRecipientAgeGreaterThan/{age}")
    @Operation(summary = "Adin batetik gorako pertsonei zuzendutako opariak dituzten Eskatzaileak lortzea")
    private @ResponseBody Iterable<Eskatzailea> findByGiftRecipientAgeGreaterThan(@PathVariable int age) {
        return eskatzaileaService.findByGiftRecipientAgeGreaterThan(age);
    }

    @GetMapping(path = "/findWithNoGifts")
    @Operation(summary = "Eskatzaileak guztiak opari-zerrenda huts batekin bilatu")
    private @ResponseBody Iterable<Eskatzailea> findWithNoGifts() {
        return eskatzaileaService.findWithNoGifts();
    }

    @GetMapping(path = "/findByExactGiftCount/{count}")
    @Operation(summary = "Eskuratu Eskatzaileak guztiak opari kopuru zehatz batekin")
    private @ResponseBody Iterable<Eskatzailea> findByExactGiftCount(@PathVariable int count) {
        return eskatzaileaService.findByExactGiftCount(count);
    }

    @GetMapping(path = "/findByGiftPriorityAndRecipientAge/{priority}/{age}")
    @Operation(summary = "Lehentasun espezifiko bateko opariak bilatzea adin jakin bateko pertsonei")
    private @ResponseBody Iterable<Eskatzailea> findByGiftPriorityAndRecipientAge(@PathVariable int priority, @PathVariable int age) {
        return eskatzaileaService.findByGiftPriorityAndRecipientAge(priority, age);
    }

    @GetMapping(path = "/findUniqueGiftsForRecipient/{recipientName}")
    @Operation(summary = "Pertsona jakin bati zuzendutako opari bakarrak lortzea")
    private @ResponseBody Iterable<Oparia> findUniqueGiftsForRecipient(@PathVariable String recipientName) {
        return eskatzaileaService.findUniqueGiftsForRecipient(recipientName);
    }

    //#endregion

    //#region ActionMethods

    @PostMapping(path = "/add")
    @Operation(summary = "Eskatzaile berri bat sartzen du", description = "Eskatzailea JSON formatuan bidalu behar duzu")
    private @ResponseBody Eskatzailea add(@RequestBody Eskatzailea eskatzailea) {
        return eskatzaileaService.add(eskatzailea);
    }

    @PutMapping(path = "/update/{name}")
    @Operation(summary = "Eskatzaile bat eguneratzen du", description = "Izen horrekin aurkitzen duen lehen Eskatzailea eguneratuko du")
    private @ResponseBody Eskatzailea update(@RequestBody Eskatzailea eskatzailea, @PathVariable String name) {
        return eskatzaileaService.update(eskatzailea, name);
    }

    @DeleteMapping(path = "/delete/{id}")
    @Operation(summary = "Eskatzaile bat ezabatzen du Id-aren arabera")
    private long delete(@PathVariable String id) {
        return eskatzaileaService.delete(id);
    }

    //#endregion
}
