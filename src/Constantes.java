package org.inria.restlet.mta.internals;

/**
 * Les constantes
 *
 * @author diarranabe - Nanourgo DIARRASSOUBA
 * @author mcok - Charles Oliviers MAUD
 */
public class Constantes {

    public static final int TRAIN_SUR_QUAI = 1;
    public static final int TRAIN_SUR_RAILL = 2;
    public static final int TRAIN_EN_ATTENTE = 3;
    public static final int TRAIN_EN_ROUTE_VERS_GARE = 5;

    public static final int TEMPS_SUR_QUAI = 5000;

    public static final int VOYAGEUR_VERS_GARE = 0;
    public static final int VOYAGEUR_HAS_TICKET = 1;
    public static final int VOYAGEUR_IN_TRAIN = 2;
    public static final int VOYAGEUR_TEMPS_TRAJET = 7000;

    public static final int TEMPS_VOYAGEUR_CHERCHER_TRAIN = 1000;
    public static final int TEMPS_FAIRE_PAUSE= 1000;
    public static final int TEMPS_VOYAGEUR_ARRIVE_EN_GARE = 1000;

    public static int VITESSE_RATIO = 10000;
    public static int NB_DE_VOIES = 4;

    /**
     * Le texte correspondant à l'etat d'un voyageur
     * @param id etat du voyageur
     * @return le texte
     */
    public static String getVoyStateLabel(int id) {
        switch (id) {
            case VOYAGEUR_VERS_GARE:
                return "EN ROUTE VERS LA GARE";
            case VOYAGEUR_HAS_TICKET:
                return "MUNI D'UN TICKET";
            case VOYAGEUR_IN_TRAIN:
                return "MONTE DANS UN TRAIN";
        }
        return "EGARE";
    }

    /**
     * Le texte correspondant à l'etat d'un train
     * @param idTrain etat du train
     * @return le texte
     */
    public static String getTrainStateLabel(int idTrain) {
        switch (idTrain) {
            case TRAIN_SUR_QUAI:
                return "QUAI";
            case TRAIN_SUR_RAILL:
                return "REPARTI";
            case TRAIN_EN_ATTENTE:
                return "EN ATTENTE DE VOIE LIBRE";
            case TRAIN_EN_ROUTE_VERS_GARE:
                return "EN ROUTE VERS LA GARE";
        }
        return "INQUIETANT";
    }
}
