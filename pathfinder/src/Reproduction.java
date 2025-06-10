//TODO: Implement the Reproduction event logic
public class Reproduction extends Event{

    Reproduction(int time, Individual individual, Pec pec) {
        super(time, individual, pec);
    }

    @Override
    public void execute() {
        // Implementation of the reprodruction event logic
        individual.reproduce();
        pec.addEvent(new Reproduction(individual.getMoveIndex() + time, individual, pec));
    }

}
