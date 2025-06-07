//TODO: Implement the Move event logic
public class Move implements Event {
    Move() {
        // Constructor for Move event
        System.out.println("Move event created.");
    }

    @Override
    public void execute() {
        // Implementation of the move event logic
        System.out.println("Executing move event.");
    }

}
