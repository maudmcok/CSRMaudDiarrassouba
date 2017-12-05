/**
 * Created by 18012666 on 27/11/17.
 */
public class EspaceVente {
    public int nb_ticket = 70 ;


    public synchronized void sellTicket(Voyageur voyageur){
        if ( nb_ticket>= voyageur.nbTickets ){
            nb_ticket -=voyageur.nbTickets ;
            voyageur.etat = Constantes.VOYAGEUR_HAS_TICKET;
            System.out.println("espaceVente: sell a ticket");
        }else {
            addTicket(20);
            System.out.println("espaceVente: Add ticket");
        }

    }

    public synchronized void addTicket (int nb){
        nb_ticket += nb ;
        System.out.println("espaceVente: added "+nb+"tickets; Total : "+nb_ticket);
        notifyAll();
    }

}
