//TODO: Implement the Move event logic
public class Move extends Event {

    Move(int time, Individual individual, Pec pec) {
        super(time, individual, pec);
    }

    @Override
    public void execute() {
        // Implementation of the move event logic
        individual.move();
        pec.addEvent(new Move(individual.getMoveIndex() + time, individual, pec));
    }

}
