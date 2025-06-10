//TODO: Implement the Death event logic
public class Death extends Event {

    Death(int time, Individual individual, Pec pec) {
        super(time, individual, pec);
    }

    @Override
    public void execute() {
        // Implementation of the death event logic
        individual.death();
    }
}
