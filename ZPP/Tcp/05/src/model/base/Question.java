package model.base;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Question {
    private static final List<String> QUESTIONS = new ArrayList<>();
    private static final List<String> ANSWERS = new ArrayList<>(); 

    private static void initializeQuestions() {
        QUESTIONS.addAll(Arrays.asList("Nola berrezarri nire kontu-pasahitza?",
            "Nola konektatu nire gailua Wi-Fi sare batera?",
            "Nola kompondu nire inprimagailuko inprimatze-azazoak?",
            "Zein urrats eman behar ditut nire sistema eragilearen softwarea eguneratzeko?",
            "Nola kopa ditzaket nire fitxategi garrantzutsuak?"));
    }

    private static void initializeAnswers() {
        ANSWERS.addAll(Arrays.asList("Pasahitza berrezar dezakezu, saioa hasteko orrian berreskuratzeko pausoak jarraituz",
            "Joan zure gailuko WiFi-aren konfiguraziora, hautatu sare erabilgarria eta sartu pasahitza, behar izanez gero",
            "Egiaztatu inprimagailuaren konexioa, tinta-/paper-mailak, eta berrabiarazi inprimagailua eta ordenagailua, behar izanez gero",
            "Bisitatu sistema eragilearen konfigurazioa, bilatu \"Eguneratzeak\" eta jarraitu argibideak eskuragarri dauden eguneratzeak egiaztatzeko eta aplikatzeko",
            "Zure fitxategi garrantzitsuak kopia ditzakezu hodeiko biltegiratze-zerbitzuak edo kanpoko biltegiratze-gailuak (adibidez, USB) erabiliz"));
    }

    public static List<String> getQuestions() {
        initializeQuestions();

        return QUESTIONS;
    }

    public static List<String> getAnswers() {
        initializeAnswers();

        return ANSWERS;
    }
}
