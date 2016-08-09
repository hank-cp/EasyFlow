package au.com.ds.ef;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Executor;

public class FlowBuilder<C extends StatefulContext> {

    private static ThreadLocal<List<Transition>> transitions = new ThreadLocal<>();

    private StateEnum startState;
    private boolean skipValidation;
    private Executor executor;
    private List<Transition> sealedTransitions;

    public static class ToHolder {
        private EventEnum event;

        private ToHolder(EventEnum event) {
            this.event = event;
        }

        public Transition to(StateEnum state) {
            Transition transition = new Transition(event, state, false);
            register(transition);
            return transition;
        }

        public Transition finish(StateEnum state) {
            Transition transition = new Transition(event, state, true);
            register(transition);
            return transition;
        }
    }

	private FlowBuilder(StateEnum startState) {
        this.startState = startState;
	}

	public static <C extends StatefulContext> FlowBuilder<C> from(StateEnum startState) {
		return new FlowBuilder<>(startState);
	}

	public static <C extends StatefulContext> EasyFlow<C> fromTransitions(StateEnum startState,
                                                                          Collection<Transition> transitions, boolean skipValidation) {
        EasyFlow<C> flow = new EasyFlow<C>(startState);
        flow.setTransitions(transitions, skipValidation);
        return flow;
    }

    public static ToHolder on(EventEnum event) {
        return new ToHolder(event);
    }

    public FlowBuilder<C> transit(Transition... transitions) {
        return transit(false, transitions);
    }

	public FlowBuilder<C> transit(boolean skipValidation, Transition... transitions) {
        for (Transition transition : transitions) {
            transition.setStateFrom(startState);
        }
        this.skipValidation = skipValidation;
        return this;
	}

    public FlowBuilder<C> setExecutor(Executor executor) {
        this.executor = executor;
        return this;
    }

    private static void register(Transition transition) {
        List<Transition> list = transitions.get();
        if (list == null) {
            list = new ArrayList<>();
            transitions.set(list);
        }
        list.add(transition);
    }

    private List<Transition> consumeTransitions() {
        if (sealedTransitions == null) {
            sealedTransitions = transitions.get();
            transitions.remove();
        }
        return sealedTransitions;
    }

    public <C1 extends StatefulContext> EasyFlow<C1> build() {
        EasyFlow<C1> flow = new EasyFlow<>(startState);
        flow.setTransitions(consumeTransitions(), skipValidation);
        if (executor != null) flow.executor(executor);
        return flow;
    }
}
