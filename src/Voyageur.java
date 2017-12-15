package org.inria.restlet.mta.internals;

/**
 * Voyageur
 * Toutes les actions rélisées par le voyageur
 *
 * @author diarranabe - Nanourgo DIARRASSOUBA
 * @author mcok - Charles Oliviers MAUD
 */
public class Voyageur extends Thread {
    /**
     * Identifiant unique
     */
    int id = 0;

    /**
     * Nom
     */
    String name;

    /**
     * Nombre de tickets
     */
    int nbTickets = 1;

    /**
     * Etat
     */
    int etat = Constantes.VOYAGEUR_VERS_GARE;

    /**
     * Espace de vente où acheter son ticket
     */
    EspaceVente espaceVente;

    /**
     * Espace quai où monter dans un train
     */
    EspaceQuai espaceQuai;

    /**
     * Dit si le voyageur attend un un train quand il à déja un ticket
     */
    boolean attendTrain = false;

    /**
     * Objet de gestion du voyageur: wait()...
     */
    public Object notifyObject = new Object();

    /**
     * Getter de id
     *
     * @return id
     */
    public int getIdVoyageur() {
        return this.id;
    }

    /**
     * Getter de etat
     *
     * @return etat
     */
    public int getEtat() {
        return this.etat;
    }

    /**
     * Getter de name
     *
     * @return name
     */
    public String getNom() {
        return this.name;
    }

    /**
     * Constructeur
     *
     * @param id   id
     * @param name nom
     */
    public Voyageur(int id, String name) {
        this.id = id;
        this.name = name;
        this.etat = Constantes.VOYAGEUR_VERS_GARE;
        espaceVente = EspaceVente.getInstance();
        espaceQuai = EspaceQuai.getInstance();
    }

    /**
     * @return Toutes les données sous forme de chaine
     */
    @Override
    public String toString() {
        return "Voyageur{id:" + id + ", name:" + name + ", state:" + Constantes.getVoyStateLabel(etat) + "}";
    }

    /**
     * Identifiant et nom
     * @return id+name
     */
    public String getText() {
        return "Voyageur{id:" + id + ", name:" + name +"}";
    }

    /**
     * La boucle du voyageur
     */
    @Override
    public void run() {
        super.run();
        while (true) {
            switch (this.etat) {
                case Constantes.VOYAGEUR_VERS_GARE:// Quand il n'a pas de ticket
                    System.out.println(ConsoleColors.BLUE + "voyageur --> Je suis en route " + this + ConsoleColors.RESET);
                    try {
                        this.sleep(Constantes.TEMPS_FAIRE_PAUSE); // Pause entre deux tours
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    EspaceVente.getInstance().sellTicket(this);
                    System.out.println(ConsoleColors.BLUE + "voyageur --> Je suis là " + this + ConsoleColors.RESET);
                    break;
                case Constantes.VOYAGEUR_HAS_TICKET:// Quand il à son ticket
                    System.out.println(ConsoleColors.BLUE + "voyageur --> a son ticket " + this + ConsoleColors.RESET);
                    while (this.etat == Constantes.VOYAGEUR_HAS_TICKET) {
                        try {
                            sleep(Constantes.TEMPS_VOYAGEUR_CHERCHER_TRAIN);
                            Gare.getInstance().monterTrain(this);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                default: // Attend si jamais tu arrive ici
                    try {
                        sleep(Constantes.VOYAGEUR_TEMPS_TRAJET);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
            }
//            System.out.println(ConsoleColors.BLUE + "voyageur -- " + this + ConsoleColors.RESET);
        }
    }


}
