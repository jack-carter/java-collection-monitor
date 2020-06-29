package com.shinobigroup.collections.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.function.Predicate;

/**
 * This class acts as a <a href="https://en.wikipedia.org/wiki/Decorator_pattern">Decorator</a> for instances of {@code java.util.Collection}.
 * It monitors the {@code collection.size()} of an underlying collection -- using an internal listener on the collection -- and when it detects
 * a change in the size of that collection, it scans the conditions provided by external callers, and for those whose conditions are met, notifies
 * the corresponding listener.
 * 
 * Note that instances of this class also act in the same fashion as a {@link CollectionSizeMonitor}, so that callers may still register general
 * listeners, as well as those requiring specific conditions.
 * 
 * Instances of this class should not be considered thread-safe.
 *
 * @author Jack Carter
 * @email  jack.carter@shinobigroup.com
 * @since  Apr 30, 2018
 *
 * @param <T> the type of elements contained in the underlying collection
 * 
 * NOTES:
 *
 * 1) Should consider changing this a multi-map implementation, to allow multiple actions on the same condition.
 */
public class CollectionSizeThresholdMonitor<T> extends CollectionSizeMonitor<T> {

    //
    // Instantiation
    //
    
    static public <T> CollectionSizeThresholdMonitor<T> monitor(final Collection<T> collection) {
	return new CollectionSizeThresholdMonitor<T>(collection);
    }
    
    public CollectionSizeThresholdMonitor(final Collection<T> collection) {
	super(collection);
	addListener(col -> conditions.entrySet().stream()
		.filter(entry -> entry.getKey().test(col.size()))
		.forEach(entry -> entry.getValue().sizeChanged(col)));
    }
    
    //
    // Listener registration
    //
    
    public CollectionSizeThresholdMonitor<T> when(final Predicate<Integer> condition, final CollectionSizeMonitor.Listener listener) {
	conditions.put(condition, listener);
	return this;
    }

    //
    // Instance variables
    //
    
    private HashMap<Predicate<Integer>,CollectionSizeMonitor.Listener> conditions = new HashMap<Predicate<Integer>,CollectionSizeMonitor.Listener>();

}
