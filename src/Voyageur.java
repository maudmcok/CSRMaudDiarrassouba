/**
 * Created by 18012666 on 27/11/17.
 */
public class Voyageur extends Thread {
    boolean hasTicket = false ;

    public void acheterTicket(EspaceVente espaceVente){
                espaceVente.vendreTicketAClient(this);
    }

    public void giveTicket() {
        this.hasTicket = true;
    }
}
