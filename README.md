# Azterketak DAM2 2.Ebal

## Aurkibidea
1. [DA](#da)

## DA

- **MongoDb Compassen shellean inplementatutako api-an agertzen diren kontsumoen adibide dira**

  1. **Eskatzaile guztiak bilatzen ditu**
  ```javascript
  db.eskatzaileak.find({});
  ```
  2. **ID-aren bidezko Eskatzailea bilatu**
  ```javascript
  db.eskatzaileak.find({ _id: ObjectId("id_del_eskatzailea") });
  ```
  
  3. **Izen bidezko Eskatzailea bilatu**
  ```javascript
  db.eskatzaileak.find({ izena: "Mikel" });
  ```
  
  4. **Bilatu Eskatzaileak gutxieneko opari kopuru batekin**
  ```javascript
  db.eskatzaileak.find({ "opariak": { $size: { $gte: 3 } } });
  ```
  
  5. **Bilatu Eskatzaileak lehentasun bereziko opariekin**
  ```javascript
  db.eskatzaileak.find({ "opariak.lehentasuna": 1 });
  ```
  
  6. **Eskatzaileak opariekin bilatu, hartzaile jakin batentzat, izenaren arabera**
  ```javascript
  db.eskatzaileak.find({ "opariak.nori.izena": "Jon" });
  ```
  
  7. **Bilatu Eskatzaileak adin batetik gorako oparien hartzaileekin**
  ```javascript
  db.eskatzaileak.find({ "opariak.nori.adina": { $gt: 10 } });
  ```
  
  8. **Bilatu Eskatzaileak oparirik gabe**
  ```javascript
  db.eskatzaileak.find({ "opariak": { $size: 0 } });
  ```
  
  9. **Bilatu Eskatzaileak opari kopuru zehatz batekin**
  ```javascript
  db.eskatzaileak.find({ "opariak": { $size: 2 } });
  ```
  
  10. **Eskatzaileak bilatzea lehentasun bereziko opariekin eta adin zehatz baten hartzaileekin**
  ```javascript
  db.eskatzaileak.find({
      $and: [
          { "opariak.lehentasuna": 1 },
          { "opariak.nori.adina": 15 }
      ]
  });
  ```
  
  11. **Hartzaile jakin batentzako opari bakar guztiak izenez bilatu**
  ```javascript
  db.eskatzaileak.aggregate([
      { $unwind: "$opariak" },
      { $match: { "opariak.nori.izena": "Jon" } },
      { $project: { _id: 0, "opariak.zer": 1, "opariak.lehentasuna": 1, "opariak.nori": 1 } }
  ]);
  ```
  12. **Ezabatu eskatzailea bere IDaren bidez**
  ```javascript
  db.eskatzaileak.deleteOne({ _id: ObjectId("id_del_eskatzailea") });
  ```
