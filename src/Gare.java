import java.util.ArrayList;

/**
 * Created by 18012666 on 27/11/17.
 */
public class Gare {

    ArrayList<Train> trains = new ArrayList<>();

    private EspaceVente espaceVente = new EspaceVente();

    private EspaceQuai  espaceQuai = new EspaceQuai(3,this) ;

    public void addTrain(Train train){
        trains.add(train);
    }






}
