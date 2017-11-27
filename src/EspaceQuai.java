import java.util.ArrayList;

/**
 * Created by 18012666 on 27/11/17.
 */
public class EspaceQuai {
    private int nb_voies = 3 ;

    ArrayList<Train> trainsSurQuai = new ArrayList<>();
    ArrayList<Train> trainWaitting = new ArrayList<>();

    private Gare gare ;

    public EspaceQuai(int nb_voies, Gare gare) {
        this.nb_voies = nb_voies;
        this.gare = gare;
    }

    public synchronized void garerTrain (Train train){

        if (trainsSurQuai.size() < nb_voies ){
            trainWaitting.remove(train);
            trainsSurQuai.add(train);
            train.setEta(Constantes.SUR_QUAI);
        }else {
            train.setEta(Constantes.EN_GARE);
            trainWaitting.add(train);
            try {
                train.wait();
                this.garerTrain(train);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public synchronized void departTrain(Train train){
        if (trainsSurQuai.contains(train)){
            train.setEta(Constantes.SUR_RAILL);
            trainsSurQuai.remove(train) ;
            trainWaitting.notifyAll();
        }else {
            System.out.println("Ce traint n'est pas en Gare ");
        }
    }

    public synchronized void addTrainWait(Train train){
        trainWaitting.add(train);
    }





    public synchronized boolean isOnGare(Train train){
        return  trainsSurQuai.contains(train);
    }


}
