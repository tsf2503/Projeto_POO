abstract class Event {

    protected Pec pec;
    protected double time;
    protected Individual individual;

    public Event(double time, Individual individual, Pec pec) {
        this.time = time;
        this.individual = individual;
        this.pec = pec;
    }

    public double getTime() {
        return time;
    }
    public Individual getIndividual() {
        return individual;
    }
    public Pec getPec() {
        return pec;
    }

    @Override
    public String toString() {
        return "Event{" +
                "time=" + time +
                ", individual=" + individual +
                '}';
    }

    abstract void execute();
}
