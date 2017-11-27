/**
 * Created by 18012666 on 27/11/17.
 */
public class EspaceVente {
    private int nb_ticket ;


    public synchronized void vendreTicketAClient(Voyageur voyageur){
        if ( nb_ticket> 0 ){
            nb_ticket -- ;
            voyageur.giveTicket();
        }else {
            try {
                voyageur.wait();
                vendreTicketAClient(voyageur);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public synchronized void addTicket (int nb){
        nb_ticket += nb ;
    }

}
