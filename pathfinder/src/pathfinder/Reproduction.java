package pathfinder;
//TODO: Implement the Reproduction event logic

public class Reproduction extends Event{

    Reproduction(double time, Individual individual, Pec pec) {
        super(time, individual, pec);
    }

    @Override
    public void execute() {
        // Implementation of the reprodruction event logic
        if (individual.isAlive()) {
            Individual child = individual.reproduce();

            pec.addEvent(new Reproduction(individual.getMoveTime() + time, individual, pec));

            pec.addEvent(new Move(child.getMoveTime() + time, child, pec));
            pec.addEvent(new Death(child.getDeathTime() + time, child, pec));
            pec.addEvent(new Reproduction(child.getReproductionTime() + time, child, pec));
        }
    }

}
