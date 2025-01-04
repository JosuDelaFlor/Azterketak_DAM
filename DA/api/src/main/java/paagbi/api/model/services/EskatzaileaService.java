package paagbi.api.model.services;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;

import jakarta.annotation.PostConstruct;
import paagbi.api.model.base.Eskatzailea;
import paagbi.api.model.base.Eskatzailea.Oparia;
import paagbi.api.model.repositories.IEskatzaileaRepository;

import static com.mongodb.client.model.Filters.eq;

@Service
public class EskatzaileaService implements IEskatzaileaRepository {

    @Autowired
    private MongoClient client;
    private MongoCollection<Eskatzailea> eskatzaileaCollection;

    @PostConstruct
    private void init() {
        eskatzaileaCollection = client.getDatabase("gabonak").getCollection("eskatzaileak", Eskatzailea.class);
    }

    //#region GetMethods

    @Override
    public List<Eskatzailea> getAll() {
        eskatzaileaCollection.find().into(new ArrayList<>()).forEach(System.out::println);

        return eskatzaileaCollection.find().into(new ArrayList<>());
    }

    @Override
    public Eskatzailea findById(String id) {
        try {
            Eskatzailea eskatzailea = eskatzaileaCollection.find(eq("_id", new ObjectId(id))).first();
            System.out.println(eskatzailea);

            return eskatzailea;
        } catch (IllegalArgumentException e) {
            System.err.println("Ez da string baliodun bat id-arentzako mongoDb-an, edo ez dago");

            return null;
        }
    }

    @Override
    public Eskatzailea findByName(String name) {
        Eskatzailea eskatzailea = eskatzaileaCollection.find(eq("izena", name)).first();

        if (eskatzailea == null) {
            System.err.println("Ez dago izen hori duen Eskatzaile bat");

            return null;
        } else {
            System.out.println(eskatzailea);
            return eskatzailea;
        }
    }

    @Override
    public List<Eskatzailea> findByMinimunGifts(int minGifts) {
        return eskatzaileaCollection.find(
            new org.bson.Document("opariak", 
                new org.bson.Document("$size", new org.bson.Document("$gte", minGifts)))
        ).into(new ArrayList<>());
    }

    @Override
    public List<Eskatzailea> findByGiftPriority(int priority) {
        return eskatzaileaCollection.find(
            new org.bson.Document("opariak.lehentasuna", priority)
        ).into(new ArrayList<>());
    }

    @Override
    public List<Eskatzailea> findByGiftRecipientName(String recipientName) {
        return eskatzaileaCollection.find(
            new org.bson.Document("opariak.nori.izena", recipientName)
        ).into(new ArrayList<>());
    }

    @Override
    public List<Eskatzailea> findByGiftRecipientAgeGreaterThan(int age) {
        return eskatzaileaCollection.find(
            new org.bson.Document("opariak.nori.adina", 
                new org.bson.Document("$gt", age))
        ).into(new ArrayList<>());
    }

    @Override
    public List<Eskatzailea> findWithNoGifts() {
        return eskatzaileaCollection.find(
            new org.bson.Document("opariak", new org.bson.Document("$size", 0))
        ).into(new ArrayList<>());
    }

    @Override
    public List<Eskatzailea> findByExactGiftCount(int count) {
        return eskatzaileaCollection.find(
            new org.bson.Document("opariak", new org.bson.Document("$size", count))
        ).into(new ArrayList<>());
    }

    @Override
    public List<Eskatzailea> findByGiftPriorityAndRecipientAge(int priority, int age) {
        return eskatzaileaCollection.find(
            new org.bson.Document("$and", List.of(
                new org.bson.Document("opariak.lehentasuna", priority),
                new org.bson.Document("opariak.nori.adina", age)
            ))
        ).into(new ArrayList<>());
    }

    @Override
    public List<Oparia> findUniqueGiftsForRecipient(String recipientName) {
        List<Eskatzailea> eskatzaileak = eskatzaileaCollection.find(
            new org.bson.Document("opariak.nori.izena", recipientName)
        ).into(new ArrayList<>());
    
        List<Eskatzailea.Oparia> opariaList = new ArrayList<>();
        for (Eskatzailea eskatzailea : eskatzaileak) {
            eskatzailea.getOpariak().stream()
                .filter(oparia -> oparia.getNori().getIzena().equals(recipientName))
                .forEach(opariaList::add);
        }
        return opariaList;
    }

    //#endregion

    //#region ActionMethods

    @Override
    public Eskatzailea add(Eskatzailea eskatzailea) {
        InsertOneResult insertOneResult = eskatzaileaCollection.insertOne(eskatzailea);
        
        if (insertOneResult.wasAcknowledged()) {
            System.out.println(eskatzailea);

            return eskatzailea;
        } else {
            System.err.println("Errore bat egon da Eskatzaile barri bat sortzerakoan");
            return null;
        }
    }

    @Override
    public Eskatzailea update(Eskatzailea eskatzailea, String name) {
        try {
            Eskatzailea existingEskatzailea = eskatzaileaCollection.find(eq("izena", name)).first();
    
            if (existingEskatzailea == null) {
                System.err.println("Ez dago izen hori duen Eskatzaile bat");
                return null;
            }

            eskatzailea.setId(existingEskatzailea.getId());
    
            UpdateResult updateResult = eskatzaileaCollection.updateOne(
                eq("izena", name),
                new Document("$set", new Document()
                    .append("izena", eskatzailea.getIzena())
                    .append("opariak", eskatzailea.getOpariak()))
            );
    
            if (updateResult.getMatchedCount() > 0) {
                System.out.println(eskatzailea);

                return eskatzailea;
            } else {
                return null;
            }
    
        } catch (MongoException e) {
            System.err.println("Errore bat egon da Eskatzaile-a eguneratzeko: " + e.getMessage());
            return null;
        }
    }

    @Override
    public long delete(String id) {
        DeleteResult result = eskatzaileaCollection.deleteOne(eq("_id", new ObjectId(id)));

        return result.getDeletedCount();
    }

    //#endregion
}
