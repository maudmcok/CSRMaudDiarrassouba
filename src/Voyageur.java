/**
 * Created by 18012666 on 27/11/17.
 */
public class Voyageur extends Thread {
    EspaceVente espaceVente;
    EspaceQuai espaceQuai;

    //    boolean hasTicket = false ;
    int nbTickets = 1;
    int etat = Constantes.VOYAGEUR_NO_TICKET;

    Voyageur(Gare gare, int nbTickets){
        this.espaceQuai = gare.espaceQuai;
        this.espaceVente = gare.espaceVente;
        this.nbTickets = nbTickets;

    }

    Voyageur(Gare gare){
        this.espaceVente = gare.espaceVente;
        this.espaceQuai = gare.espaceQuai;
        this.nbTickets = 1;

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
                    System.out.println("Voyageur: wants ticket");
                    this.espaceVente.sellTicket(this);
                    try {
                        sleep(1500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    break;
                case Constantes.VOYAGEUR_HAS_TICKET:
                    System.out.println("Voyageur: demande ");
                    monterTrain();
                    try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

            }
        }
        System.out.println("Voyageur: in train ");
    }

    void monterTrain() {
        System.out.println("Voyageur: monterTrain ");
        espaceQuai.monterTrain(this);
    }
}
