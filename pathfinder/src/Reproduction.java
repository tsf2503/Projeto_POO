//TODO: Implement the Reproduction event logic
public class Reproduction implements Event{

    Reproduction() {
        // Constructor for Reproduction event
        System.out.println("Reproduction event created.");
    }

    @Override
    public void execute() {
        // Implementation of the reproduction event logic
        System.out.println("Executing reproduction event.");
    }
}
