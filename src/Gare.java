import java.util.ArrayList;

/**
 * Created by 18012666 on 27/11/17.
 */
public class Gare extends Thread{

    ArrayList<Train> trains = new ArrayList<>();

    EspaceVente espaceVente = new EspaceVente();

    EspaceQuai  espaceQuai = new EspaceQuai(3,this) ;

    public void addTrain(Train train){
        trains.add(train);
        System.out.println("gare: added new train");
    }

    public void addTrain(int capacity){
        Train train = new Train(capacity,espaceQuai);
        trains.add(train);
        train.start();

        System.out.println("gare: added new train");
    }



    @Override
    public void run() {

        this.addTrain(10);
        this.addTrain(15);

        for (int i = 0 ; i< 110; i++){

            Voyageur v1 = new Voyageur(this);
            v1.start();
        }

    }

    public static void main(String[] args){
        System.out.println("Start");

        Gare gare = new Gare();
        gare.start();

        System.out.println("End");
    }


}
