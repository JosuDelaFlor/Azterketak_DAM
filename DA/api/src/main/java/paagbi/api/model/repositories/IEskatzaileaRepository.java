package paagbi.api.model.repositories;

import java.util.List;

import org.springframework.stereotype.Repository;

import paagbi.api.model.base.Eskatzailea;
import paagbi.api.model.base.Eskatzailea.Oparia;

@Repository
public interface IEskatzaileaRepository {

    /**
     * Eskatzaile guztiak bilatzen ditu
     * @return
     */
    public List<Eskatzailea> getAll();

    /**
     * Eskatzaile bat bilatzen di Id-aren arabera
     * @param id
     * @return
     */
    public Eskatzailea findById(String id);

    /**
     * Eskatzaile bat bilatzen du Izena-ren arabera
     * @param name
     * @return
     */
    public Eskatzailea findByName(String name);

    /**
     * Eskuratu Eskatzaileak guztiak gutxieneko Opariak kopuruarekin
     * @param minGifts
     * @return
     */
    public List<Eskatzailea> findByMinimunGifts(int minGifts);

    /**
     * Lehentasuna duten opariak dituzten Eskatzaileak guztiak lortzea
     * @param priority
     * @return
     */
    public List<Eskatzailea> findByGiftPriority(int priority);

    /**
     * Bilatu Eskatzaileak opariaren hartzailearen izenaren arabera (Nori)
     * @param recipientName
     * @return
     */
    public List<Eskatzailea> findByGiftRecipientName(String recipientName);

    /**
     * Adin batetik gorako pertsonei zuzendutako opariak dituzten Eskatzaileak lortzea
     * @param age
     * @return
     */
    public List<Eskatzailea> findByGiftRecipientAgeGreaterThan(int age);

    /**
     * Eskatzaileak guztiak opari-zerrenda huts batekin bilatu
     * @return
     */
    public List<Eskatzailea> findWithNoGifts();

    /**
     * Eskuratu Eskatzaileak guztiak opari kopuru zehatz batekin
     * @param count
     * @return
     */
    public List<Eskatzailea> findByExactGiftCount(int count);

    /**
     * Lehentasun espezifiko bateko opariak bilatzea adin jakin bateko pertsonei
     * @param priority
     * @param age
     * @return
     */
    public List<Eskatzailea> findByGiftPriorityAndRecipientAge(int priority, int age);

    /**
     * Pertsona jakin bati zuzendutako opari bakarrak lortzea
     * @param resipientName
     * @return
     */
    public List<Oparia> findUniqueGiftsForRecipient(String resipientName);

    /**
     * Eskatzaile berri bat sartzen du
     * @param eskatzailea
     * @return
     */
    public Eskatzailea add(Eskatzailea eskatzailea);

    /**
     * Eskatzaile bat eguneratzen du
     * @param eskatzailea
     * @param name
     * @return
     */
    public Eskatzailea update(Eskatzailea eskatzailea, String name);

    /**
     * Eskatzaile bat ezabatzen du Id-aren arabera
     * @param id
     * @return
     */
    public long delete(String id);
}
