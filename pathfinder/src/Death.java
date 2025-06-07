//TODO: Implement the Death event logic
public class Death implements Event {
    public Individual individual;

    Death(Individual individual){
        this.individual = individual;
        
        // Constructor for Death event
        System.out.println("Death event created.");
    }

    @Override
    public void execute() {
        // Implementation of the death event logic
        System.out.println("Executing death event.");
    }
}
