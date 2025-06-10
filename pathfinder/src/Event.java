abstract class Event {

    protected Pec pec;
    protected int time;
    protected Individual individual;

    public Event(int time, Individual individual, Pec pec) {
        this.time = time;
        this.individual = individual;
        this.pec = pec;
    }

    public int getTime() {
        return time;
    }
    public Individual getIndividual() {
        return individual;
    }
    public Pec getPec() {
        return pec;
    }

    abstract void execute();
}
