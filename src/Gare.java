import java.util.ArrayList;

/**
 * Created by 18012666 on 27/11/17.
 */
public class Gare extends Thread{

    public static Gare instance = null;
    ArrayList<Train> trains = new ArrayList<>();

    EspaceVente espaceVente = EspaceVente.getInstance();

    public static EspaceQuai  espaceQuai = EspaceQuai.getInstance() ;


    private Gare(){

    }

    public static Gare getInstance(){
        if (instance==null){
            instance = new Gare();
        }
        return instance;
    }


    public void addTrain(Train train){
        trains.add(train);
        System.out.println("Gare: new train "+train);
    }

    public void addTrain(int capacity){
        Train train = new Train(capacity);
        trains.add(train);
        train.start();
        System.out.println("Gare: new train "+train);
    }

    public void addTrain(int capacity, String flag){
        Train train = new Train(capacity, flag);
        trains.add(train);
        train.start();
        System.out.println("Gare: new train "+train);
    }



    @Override
    public void run() {

        this.addTrain(10, "T850");
        this.addTrain(15,"T90");
        this.addTrain(15,"T50");
        this.addTrain(15,"T40");
        this.addTrain(15,"T30");
        this.addTrain(15,"T20");
        this.addTrain(15,"T10");

        for (int i = 0 ; i< 110; i++){

            Voyageur v1 = new Voyageur("v"+i);
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
