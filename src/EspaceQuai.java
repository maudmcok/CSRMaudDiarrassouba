/**
 * Created by 18012666 on 27/11/17.
 */
public class EspaceQuai {
    private int nb_voies = 3 ;
    private int nbVoiesDispo = nb_voies ;


    public  static EspaceQuai instance = null;

    private Gare gare ;

    public static EspaceQuai getInstance(){
        if (instance==null){
            instance = new EspaceQuai();
        }
        return instance;
    }

    private EspaceQuai() {
        this.gare = Gare.getInstance();
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

    public  void monterTrain(Voyageur voyageur) {
        System.out.println("espaceQuai: Voyageur want to jump");
        inTrain:for (Train train : gare.trains){
            if (train.getEtat()==Constantes.SUR_QUAI){
                if (train.getFree_seats()>voyageur.nbTickets){
                    train.free_seats -= voyageur.nbTickets;
                    voyageur.etat = Constantes.VOYAGEUR_IN_TRAIN;
                    break inTrain;
                }
            }
        }
    }
}
