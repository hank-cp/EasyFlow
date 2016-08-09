package au.com.ds.ef;

/**
 * User: andrey
 * Date: 6/12/2013
 * Time: 2:21 PM
 */
public final class Transition {
    private EventEnum event;
    private StateEnum stateFrom;
    private StateEnum stateTo;
    private boolean isFinal;

    public Transition(EventEnum event, StateEnum stateFrom, StateEnum stateTo) {
        this.event = event;
        this.stateFrom = stateFrom;
        this.stateTo = stateTo;
        this.isFinal = false;
    }

    public Transition(EventEnum event, StateEnum stateFrom, StateEnum stateTo, boolean isFinal) {
        this.event = event;
        this.stateFrom = stateFrom;
        this.stateTo = stateTo;
        this.isFinal = isFinal;
    }

    protected Transition(EventEnum event, StateEnum stateTo, boolean isFinal) {
        this.event = event;
        this.stateTo = stateTo;
        this.isFinal = isFinal;
    }

    public EventEnum getEvent() {
        return event;
    }

    protected void setStateFrom(StateEnum stateFrom) {
        this.stateFrom = stateFrom;
    }

    public StateEnum getStateFrom() {
        return stateFrom;
    }

    public StateEnum getStateTo() {
        return stateTo;
    }

    public boolean isFinal() {
        return isFinal;
    }

    public Transition transit(Transition... transitions) {
        for (Transition transition : transitions) {
            transition.setStateFrom(stateTo);
        }

        return this;
    }

    @Override
    public String toString() {
        return "Transition{" +
            "event=" + event +
            ", stateFrom=" + stateFrom +
            ", stateTo=" + stateTo +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Transition that = (Transition) o;

        if (!event.equals(that.event)) return false;
        if (!stateFrom.equals(that.stateFrom)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = event.hashCode();
        result = 31 * result + stateFrom.hashCode();
        return result;
    }
}