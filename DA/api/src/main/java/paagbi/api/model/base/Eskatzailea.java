package paagbi.api.model.base;

import java.util.List;

import org.bson.types.ObjectId;

public class Eskatzailea {
    
    private ObjectId id;
    private String izena;
    private List<Oparia> opariak;

    public Eskatzailea() {}

    public Eskatzailea(ObjectId id, String izena, List<Oparia> opariak) {
        this.id = id;
        this.izena = izena;
        this.opariak = opariak;
    }

    //#region Getters&Setters

    public ObjectId getId() { return id; }

    public void setId(ObjectId id) { this.id = id; }

    public String getIzena() { return izena; }

    public void setIzena(String izena) { this.izena = izena; }

    public List<Oparia> getOpariak() { return opariak; }

    public void setOpariak(List<Oparia> opariak) { this.opariak = opariak; }

    //#endregion

    @Override
    public String toString() {
        return "Eskatzailea [id=" + id + ", izena=" + izena + ", opariak=" + opariak + "]";
    }

    //#region Oparia

    public static class Oparia {

        private String zer;
        private int lehentasuna;
        private Nori nori;

        public Oparia() {}

        public Oparia(String zer, int lehentasuna, Nori nori) {
            this.zer = zer;
            this.lehentasuna = lehentasuna;
            this.nori = nori;
        }

        //#region Getters&Setters

        public String getZer() { return zer; }

        public void setZer(String zer) { this.zer = zer; }

        public int getLehentasuna() { return lehentasuna; }

        public void setLehentasuna(int lehentasuna) { this.lehentasuna = lehentasuna; }

        public Nori getNori() { return nori; }

        public void setNori(Nori nori) { this.nori = nori; }

        //#endregion

        @Override
        public String toString() {
            return "Oparia [zer=" + zer + ", lehentasuna=" + lehentasuna + ", nori=" + nori + "]";
        }

    //#endregion

        //#region Nori

        public static class Nori {

            private String izena;
            private int adina;

            public Nori() {}

            public Nori(String izena, int adina) {
                this.izena = izena;
                this.adina = adina;
            }

            //#region Getters&Setters

            public String getIzena() { return izena; }

            public void setIzena(String izena) { this.izena = izena; }

            public int getAdina() { return adina; }

            public void setAdina(int adina) { this.adina = adina; }

            //#endregion

            @Override
            public String toString() {
                return "Nori [izena=" + izena + ", adina=" + adina + "]";
            }
        }
        
        //#endregion
    }
}
