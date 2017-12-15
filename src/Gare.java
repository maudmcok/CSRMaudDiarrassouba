package org.inria.restlet.mta.internals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.Semaphore;

/**
 * Gare - Implementation du patron de conception Singleton pour permettre
 * l'accès à objet Gare unique partout dans le projet
 *
 * Classe qui gère les trains, voyageurs et espace quai.
 *
 *
 * @author diarranabe - Nanourgo DIARRASSOUBA
 * @author mcok - Charles Oliviers MAUD
 */

public class Gare {
    /**
     * Instance unique
     */
    public static Gare instance = null;

    /**
     * Liste de tous trains
     */
    ArrayList<Train> trains = new ArrayList<Train>();

    /**
     * Map pour acceder au voyageurs depuis la base de données interne
     */
    public static Map<Integer, Voyageur> voyageurs_ = new HashMap<Integer, Voyageur>();

    /**
     * Map pour acceder au trains depuis la base de données interne
     */
    public static Map<Integer, Train> trains_ = new HashMap<Integer, Train>();

    /**
     * Liste de tous les voyageurs muni de tickets
     */
    public static ArrayList<Voyageur> voyageursAvecTicket = new ArrayList<Voyageur>();

    /**
     * Liste des trains sur le quai
     */
    private static ArrayList<Train> trainsSurQuai = new ArrayList<Train>();

    /**
     * Semaphore initialisé à 1 pour ajouter un voyageur à la fois à la liste des voyageurs avec ticket
     */
    public Semaphore semaAddVoyageurAvecTicket = new Semaphore(1);

    /**
     * Constructeur privé
     * Empêche la creation de plusieurs instance de Gare
     */
    private Gare() {}

    /**
     * Crée une nouvelle instance de Gare ou retourne la seule instance disponible
     * @return la seule instance disponible
     */
    public static Gare getInstance() {
        if (instance == null) {
            instance = new Gare();
        }
        return instance;
    }

    /**
     * Ajoute un nouveau train
     * @param train le nouveau train
     */
    public synchronized void addTrain(Train train) {
        trains.add(train);
        trains_.put(train.getTrainId(), train);
        System.out.println(ConsoleColors.CYAN + "gare: --> nouveau train !!! " + train + ConsoleColors.RESET);
    }

    /**
     * Ajoute un nouveau train
     * @param capacity la capacité du nouveau train
     */
    public synchronized void addTrain(int capacity) {
        Train train = new Train(capacity);
        trains.add(train);
        trains_.put(train.getTrainId(), train);
        train.start();
        System.out.println(ConsoleColors.CYAN + "gare: --> nouveau train !!! " + train + ConsoleColors.RESET);
    }

    /**
     * Ajoute un nouveau train
     * @param capacity la capacité du nouveau train
     * @param flag le nom du nouveau train
     */
    public synchronized void addTrain(int capacity, String flag) {
        Train train = new Train(capacity, flag);
        trains.add(train);
        trains_.put(train.getTrainId(), train);
        train.start();
        System.out.println(ConsoleColors.CYAN + "gare: --> nouveau train !!! " + train + ConsoleColors.RESET);
    }

    public synchronized boolean addVoyayeur(Voyageur voyageur) {
        try {
            semaAddVoyageurAvecTicket.acquire();
            voyageursAvecTicket.add(voyageur);
            voyageurs_.put(voyageur.getIdVoyageur(),voyageur);
            voyageur.start();
            System.out.println(ConsoleColors.CYAN + "gare: --> " + voyageursAvecTicket.size() + " voyageur(s) attend(ent) le prochain train. Le dernier -> " + voyageur + ConsoleColors.RESET);
            voyageur.attendTrain = true;
            semaAddVoyageurAvecTicket.release();
            return true;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }

    public synchronized void addTrainQuai(Train train) {
        trainsSurQuai.add(train);
    }

    public synchronized void removeTrainQuai(Train train) {
        trainsSurQuai.remove(train);
    }

    public void monterTrain(Voyageur voyageur){
        for (Map.Entry<Integer, Train> trainEntry : trains_.entrySet())
        {
            if (trainEntry.getValue().getEtat() == Constantes.TRAIN_SUR_QUAI && (trainEntry.getValue().free_seats>0)){
                trainEntry.getValue().prendre(voyageur);
            }
        }
    }


    public static void main(String [] args){
        Gare.getInstance().addTrain(55);
        Gare.getInstance().addTrain(new Train(1,"A",55));
        Gare.getInstance().addVoyayeur(new Voyageur(1,"nabe"));
    }
}
