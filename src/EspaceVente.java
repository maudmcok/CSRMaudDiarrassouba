package org.inria.restlet.mta.internals;

/**
 * Espace vente
 *
 * @author diarranabe - Nanourgo DIARRASSOUBA
 * @author mcok - Charles Oliviers MAUD
 */
public class EspaceVente {
    public int nb_ticket = 2500 ;
    public int add_ticket = 50 ;

    public static EspaceVente instance = null;

    private EspaceVente(){
    }

    public static EspaceVente getInstance(){
        if (instance==null){
            instance = new EspaceVente();
        }
        return instance;
    }

    /**
     * Synchronisation de la vente d'un ticket à un voyageur
     * @param voyageur le voyageur
     */
    public synchronized void sellTicket(Voyageur voyageur){
        if ( nb_ticket>= voyageur.nbTickets ){
            nb_ticket -=voyageur.nbTickets ;
            voyageur.etat = Constantes.VOYAGEUR_HAS_TICKET;
            System.out.println("espacevente --> a vendu un ticket à "+voyageur);
        }else {
//            addTicket(add_ticket);
            System.out.println("espacevente --> RUPTURE DE TICKETS ");
        }
    }

    /**
     * Augmenter le nombre de tickets disponibles
     * @param nb le nombre
     */
    public synchronized int addTicket (int nb){
        nb_ticket += nb ;
        return this.nb_ticket;
    }
}
