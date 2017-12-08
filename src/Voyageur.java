/**
 * Created by 18012666 on 27/11/17.
 */
public class Voyageur extends Thread {
    String name;
    EspaceVente espaceVente;
    EspaceQuai espaceQuai;

    //    boolean hasTicket = false ;
    int nbTickets = 1;
    int etat = Constantes.VOYAGEUR_NO_TICKET;

    Voyageur(String name){
        this.name = name;
        espaceVente = EspaceVente.getInstance();
        espaceQuai = EspaceQuai.getInstance();
    }

    public void buyTicket() {
        espaceVente.sellTicket(this);
    }

    @Override
    public void run() {
        super.run();

        while (etat != Constantes.VOYAGEUR_IN_TRAIN){
            switch (this.etat){
                case Constantes.VOYAGEUR_NO_TICKET:
                    System.out.println("Voyageur: needs ticket "+this.getNom());
                    this.espaceVente.sellTicket(this);
                    /*try {
                        sleep(1500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }*/
                    break;
                case Constantes.VOYAGEUR_HAS_TICKET:
                    System.out.println("Voyageur: demande "+this.getNom());
                    monterTrain();
                    try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
            }
        }
        System.out.println("Voyageur: in train "+this.getNom());
    }

    public String getNom(){
        return this.name;
    }
    void monterTrain() {
        System.out.println("Voyageur: jump in  Train "+this.getNom());
        espaceQuai.monterTrain(this);
    }
}
