package org.inria.restlet.mta.internals;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.Semaphore;

/**
 * @author diarranabe - Nanourgo DIARRASSOUBA
 * @author mcok - Charles Oliviers MAUD
 */
public class Train extends Thread {
    /**
     * Id unique
     */
    int id = 0;

    /**
     * Le drapeau (nom)
     */
    private String flag = "TRAIN";

    /**
     * La capacité
     */
    int capacity;

    /**
     * Siège libre
     */
    int free_seats;

    /**
     * Vitesse du train
     */
    int speed = 50;

    /**
     * Etat
     */
    private int etat;

    /**
     * Espace quai où se garer
     */
    private EspaceQuai espaceQuai;

    /**
     * Liste de voyageurs à bord
     */
    public ArrayList<Voyageur> embarques = new ArrayList<Voyageur>();

    /**
     * Semaphore pour faire monter un voyageur
     */
    private Semaphore semaPrendreVoy = new Semaphore(1);

    /**
     * Objet de synchronisation
     */
    public Object syncObject = new Object();

    /**
     * Constructeur
     *
     * @param capacity capacité
     */
    public Train(int capacity) {
        this.capacity = capacity;
        this.free_seats = capacity;
        this.espaceQuai = EspaceQuai.getInstance();
        this.etat = Constantes.TRAIN_EN_ATTENTE;
    }

    /**
     * Constructeur
     *
     * @param capacity capacité
     */
    public Train(int capacity, int speed) {
        this.capacity = capacity;
        this.free_seats = capacity;
        this.speed = speed;
        this.espaceQuai = EspaceQuai.getInstance();
        this.etat = Constantes.TRAIN_EN_ATTENTE;
    }

    /**
     * Constructeur
     *
     * @param capacity capacité
     * @param flag     le nom
     */
    public Train(int capacity, String flag) {
        this.flag += flag;
        this.capacity = capacity;
        this.free_seats = capacity;
        this.espaceQuai = EspaceQuai.getInstance();
        this.etat = Constantes.TRAIN_EN_ATTENTE;
    }

    /**
     * Constructeur
     *
     * @param id       id
     * @param capacity capacité
     * @param flag     le nom
     */
    public Train(int id, String flag, int capacity) {
        this.id = id;
        this.flag += flag;
        this.capacity = capacity;
        this.free_seats = capacity;
        this.espaceQuai = EspaceQuai.getInstance();
        this.etat = Constantes.TRAIN_EN_ATTENTE;
    }

    /**
     * Constructeur
     *
     * @param id       id
     * @param capacity capacité
     * @param flag     le nom
     */
    public Train(int id, String flag, int capacity, int speed) {
        this.id = id;
        this.flag += flag;
        this.capacity = capacity;
        this.free_seats = capacity;
        this.espaceQuai = EspaceQuai.getInstance();
        this.etat = Constantes.TRAIN_EN_ATTENTE;
        this.speed = speed;
    }

    /**
     * Getter id
     *
     * @return id
     */
    public int getTrainId() {
        return this.id;
    }

    /**
     * Getter capacity
     *
     * @return capacity
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Getter free_seats
     *
     * @return
     */
    public int getFree_seats() {
        return free_seats;
    }

    /**
     * Getter etat
     *
     * @return etat
     */
    public int getEtat() {
        return etat;
    }

    /**
     * Setter etat
     *
     * @param etat nouvel etat
     */
    public void setEtat(int etat) {
        this.etat = etat;
    }


    /**
     * Getter speed
     *
     * @return speed
     */
    public int getSpeed() {
        return speed;
    }

    /**
     * Setter speed
     *
     * @param speed speed
     */
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    /**
     * Boucle du train
     */
    @Override
    public void run() {
        super.run();

        while (true) {
            switch (this.etat) {
                case Constantes.TRAIN_EN_ATTENTE:
                    this.free_seats = capacity;
                    espaceQuai.entreGare(this);
                    break;
                case Constantes.TRAIN_SUR_QUAI:
                    /*try {
                        synchronized (this) {
                            this.wait(Constantes.TEMPS_SUR_QUAI);
                            this.espaceQuai.sortirGare(this);
                            System.out.println(ConsoleColors.GREEN + "Let's go ! " + this + ConsoleColors.RESET);
                        }

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }*/
                    break;
                case Constantes.TRAIN_SUR_RAILL:
                    try {
                        sleep(this.getSleepTime());
                        descendreVoyageurs();
                        this.setEtat(Constantes.TRAIN_EN_ATTENTE);
                        System.out.println(ConsoleColors.GREEN + "train --> " + this + ConsoleColors.RESET);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    break;
                default:// Rien
            }
        }
    }

    /**
     * Getter flag
     *
     * @return
     */
    public String getFlag() {
        return flag;
    }

    /**
     * Temps passé sur les raills
     *
     * @return la durée
     */
    private long getSleepTime() {
        return Constantes.VITESSE_RATIO / this.speed;
    }

    /**
     * @return Toutes les données sous forme de chaine
     */
    public String toString() {
        return "Train{id:"+this.getTrainId()+", name:" + flag + ", seats:" + capacity + ", free:" + free_seats + ", state:" + Constantes.getTrainStateLabel(this.etat) + ", speed:" + this.speed + "}";
    }

    /**
     * Identifiant et nom
     * @return id+name
     */
    public String getText() {
        return "Train{id:" + this.id + ", name:" + this.flag +"}";
    }
    /**
     * Prendre ubn voyageur
     *
     * @param voyageur voyageur
     */
    public void prendre(Voyageur voyageur) {
        try {
            System.out.println(ConsoleColors.GREEN + "train -->" + voyageur.getText() + " veut monter dans : " + this + ConsoleColors.RESET);
            semaPrendreVoy.acquire();
            if ((this.etat == Constantes.TRAIN_SUR_QUAI) && (this.free_seats > 0)) {
                this.free_seats--;
                voyageur.etat = Constantes.VOYAGEUR_IN_TRAIN;
                embarques.add(voyageur);
                System.out.println(ConsoleColors.GREEN + "train -->" + this + " a pri : " + voyageur.getText() + ConsoleColors.RESET);
            }
            semaPrendreVoy.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Descendre les voyageurs
     */
    public void descendreVoyageurs() {
        Iterator<Voyageur> iter = this.embarques.iterator();

        /**
         * Parcours tous les voyageurs à bord
         */
        while (iter.hasNext()) {
            try {
                semaPrendreVoy.acquire();
                Voyageur voyageur = iter.next();
                synchronized (voyageur.notifyObject) {
                    iter.remove();
                    System.out.println(ConsoleColors.GREEN + "train -->" + this + " va descendre : " + voyageur.getText() + ConsoleColors.RESET);
                    this.free_seats++;
                    voyageur.etat = Constantes.VOYAGEUR_VERS_GARE;
                    voyageur.attendTrain = false;

                    /**
                     * Reveiller le voyageur
                     */

                    voyageur.notifyObject.notify();
                }
                semaPrendreVoy.release();
                this.sleep(Constantes.TEMPS_FAIRE_PAUSE); // Pause entre deux tours
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
