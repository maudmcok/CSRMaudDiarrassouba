import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by 18012666 on 27/11/17.
 */
public class EspaceQuai {
    private int nb_voies = 3 ;
    private int nbVoiesDispo = 3 ;


/*    ArrayList<Train> trainsSurQuai = new ArrayList<>();
    ArrayList<Train> waitingTrains = new ArrayList<>();*/

    private Gare gare ;

    public EspaceQuai(int nb_voies, Gare gare) {
        this.nb_voies = nb_voies;
        this.gare = gare;
    }

    public synchronized void garerTrain (Train train){

        if (nbVoiesDispo>0) {
            nbVoiesDispo--;
            train.setEtat(Constantes.SUR_QUAI);
        } else {
            train.setEtat(Constantes.EN_GARE);
        }

    }

    public synchronized void departTrain(Train train){

        train.setEtat(Constantes.SUR_RAILL);
        nbVoiesDispo++;
        System.out.println("espaceQuai: depart train");

    }
/*
    public synchronized void addTrainWait(Train train){
        waitingTrains.add(train);
        System.out.println("espaceQuai: train in waitingList");
    }*/

/*
    public synchronized boolean isOnGare(Train train){
        return  trainsSurQuai.contains(train);
    }
*/
/*

    public void addWaitingTrain(Train train){
        waitingTrains.add(train);
        System.out.println("espaceQuai: gare addWaiting train");
    }
*/

/*
    public void runbaka() {
        int i=0;
        while (true){

            Iterator<Train> iteratorSurQuai = trainsSurQuai.iterator();
            while (iteratorSurQuai.hasNext() && trainsSurQuai.size()>0){
                Train train = iteratorSurQuai.next() ;
                if (train.getEtat() == Constantes.DEPART_IMMINANT){
                    departTrain(train);
                }
            }


            Iterator<Train> it = waitingTrains.iterator();
            System.out.println("espaceQuai: wait size "+waitingTrains.size());
            while (it.hasNext() && waitingTrains.size()>0){
                System.out.println("espaceQuai: i "+i);
                i++;
                manageTrain(it.next());
                System.out.println("espaceQuai: manage end");
            }



        }
    }*/

    private void manageTrain(Train train) {
        System.out.println("espaceQuai: manage begin");
        garerTrain(train);
    }

    public  void monterTrain(Voyageur voyageur) {
        System.out.println("espaceQuai: Voyageur want to jump");
        inTrain:for (Train train : gare.trains){
            if (train.getEtat()==Constantes.SUR_QUAI){
                if (train.getNb_libre()>voyageur.nbTickets){
                    train.nb_libre -= voyageur.nbTickets;
                    voyageur.etat = Constantes.VOYAGEUR_IN_TRAIN;
                    break inTrain;
                }
            }
        }
    }
}
