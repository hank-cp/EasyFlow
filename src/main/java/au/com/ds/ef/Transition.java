package au.com.ds.ef;

import au.com.ds.ef.call.ConditionChecker;

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
    private ConditionChecker conditionChecker;

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

    public <C extends StatefulContext> Transition onlyIf(ConditionChecker<C> checker) {
        conditionChecker = checker;
        return this;
    }

    protected boolean satisfyCondition(StatefulContext stateCtx) {
        return conditionChecker == null || conditionChecker.check(stateCtx);
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