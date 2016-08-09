package au.com.ds.ef.call;

import au.com.ds.ef.StatefulContext;

public interface ConditionChecker<C extends StatefulContext> extends Handler {
	boolean check(C context);
}
