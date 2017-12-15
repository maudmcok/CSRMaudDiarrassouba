package org.inria.restlet.mta.internals;

import java.util.concurrent.Semaphore;

/**
 * L'espace quai
 *
 * @author diarranabe - Nanourgo DIARRASSOUBA
 * @author mcok - Charles Oliviers MAUD
 */
public class EspaceQuai {
    public static Semaphore semaTrainsSurQuai = new Semaphore(Constantes.NB_DE_VOIES);
    public static EspaceQuai instance = null;
    private int VOIES_OCCUPEES = 0;
    private Semaphore semaTrainsSurVoie = new Semaphore(1);

    /**
     * Instance unique d'espace Quai
     *
     * @return EspaceQuai
     */
    public static EspaceQuai getInstance() {
        if (instance == null) {
            instance = new EspaceQuai();
        }
        return instance;
    }

    /**
     * Constructeur privé
     */
    private EspaceQuai() {
    }

    public void entreGare(Train train) {
        System.out.println(ConsoleColors.PURPLE + "espacequai --> train à la gare " + train + ConsoleColors.RESET);
        checkQuai(train);
        try {
            semaTrainsSurQuai.acquire();
            semaTrainsSurVoie.acquire();
            VOIES_OCCUPEES++;
            semaTrainsSurVoie.release();
            train.setEtat(Constantes.TRAIN_SUR_QUAI);
            System.out.println("--> voie n° " + VOIES_OCCUPEES);
            System.out.println(ConsoleColors.PURPLE + "espacequai --> train sur le quai " + train + ConsoleColors.RESET);

            //

            synchronized (train) {
                train.wait(Constantes.TEMPS_SUR_QUAI);
                train.setEtat(Constantes.TRAIN_SUR_RAILL);
                System.out.println(ConsoleColors.GREEN + "Let's go ! " + train + ConsoleColors.RESET);
            }

            //
            System.out.println(ConsoleColors.PURPLE + "espacequai --> Depart imminant " + train + ConsoleColors.RESET);
            semaTrainsSurVoie.acquire();
            VOIES_OCCUPEES--;
            semaTrainsSurVoie.release();
            System.out.println("voies -- " + VOIES_OCCUPEES);

            notifyTrains();

            semaTrainsSurQuai.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void checkQuai(Train train) {
        if (VOIES_OCCUPEES >= Constantes.NB_DE_VOIES) {
            synchronized (train.syncObject) {
                try {
                    train.syncObject.wait();
                    checkQuai(train);
                    System.out.println("block "+train);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /*public  void sortirGare(Train train) {
        try {
            semaTrainsSurQuai.acquire();
            System.out.println(ConsoleColors.PURPLE + "espacequai --> Depart imminant " + train + ConsoleColors.RESET);
            train.setEtat(Constantes.TRAIN_SUR_RAILL);

            VOIES_OCCUPEES--;
            System.out.println("voies -- " + VOIES_OCCUPEES);

            notifyTrains();

            semaTrainsSurQuai.release();
            System.out.println(ConsoleColors.PURPLE + "espacequai --> Est reparti " + train + ConsoleColors.RESET);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }*/

    public synchronized void notifyTrains() {
        for (Train train : Gare.getInstance().trains_.values()) {
            if (train.getEtat() == Constantes.TRAIN_EN_ATTENTE) {
                synchronized (train.syncObject) {
                    train.syncObject.notify();
                }
            }
        }
    }
}
