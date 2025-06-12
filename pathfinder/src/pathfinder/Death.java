package pathfinder;
//TODO: Implement the Death event logic

public class Death extends Event {

    Death(double time, Individual individual, Pec pec) {
        super(time, individual, pec);
    }

    @Override
    public void execute() {
        // Implementation of the death event logic
        if (individual.isAlive())
            individual.death();
    }
}
