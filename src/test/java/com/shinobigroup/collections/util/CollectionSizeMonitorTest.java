package com.shinobigroup.collections.util;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.junit.*;

import com.shinobigroup.collections.util.CollectionSizeMonitor;

// TODO: repeat these tests will other Collection<T> subclasses
public class CollectionSizeMonitorTest {

    private Collection<Integer> collection;
    private CollectionSizeMonitor<Integer> monitor;
    
    @Before
    public void setup() {
	collection = new ArrayList<Integer>();
	monitor = new CollectionSizeMonitor<Integer>(collection);
    }
    
    @Test
    public void monitorShouldReportTheSameSizeAsTheUnderlyingCollection() {
	assertThat(monitor.size(),is(collection.size()));
    }
    
    @Test
    public void monitorShouldAllowListenerRegistration() {
	monitor.addListener(collection -> { /* do nothing */ });
    }
    
    @Test
    public void monitorShouldNotNotifyListenersAfterRemoval() {
	final boolean changed[] = { false };
	CollectionSizeMonitor.Listener listener = collection -> { changed[0] = true; };

	monitor.addListener(listener);
	monitor.add(1);
	
	assertThat(changed[0],is(true));
	
	changed[0] = false;
	monitor.removeListener(listener);
	monitor.add(2);
	
	assertThat(changed[0],is(false));	
    }
    
    @Test
    public void monitorShouldNotNotifyListenersOnOperationsThatDontAlterSize() {
	final boolean changed[] = { false };
	monitor.addListener(collection -> { changed[0] = true; });
	monitor.size();
	monitor.isEmpty();
	monitor.contains(1);
	monitor.iterator();
	monitor.toArray();
	monitor.toArray(new String[] {});
	monitor.containsAll(Arrays.asList(1));
	
	assertThat(changed[0],is(false));	
    }
    
    @Test
    public void monitorNotifiesListenersUponAdd() {
	final boolean changed[] = { false };
	monitor.addListener(collection -> { changed[0] = true; });
	monitor.add(1);
	
	assertThat(changed[0],is(true));
    }
    
    @Test
    public void monitorNotifiesListenersUponRemove() {
	final boolean changed[] = { false };
	monitor.add(1);
	monitor.addListener(collection -> { changed[0] = true; });
	monitor.remove(1);
	
	assertThat(changed[0],is(true));
    }
    
    @Test
    public void monitorShouldNotNotifyListenersUponRemoveWhenSizeDoesNotChange() {
	final boolean changed[] = { false };
	monitor.addListener(collection -> { changed[0] = true; });
	monitor.remove(1);
	
	assertThat(changed[0],is(false));
    }
    
    @Test
    public void monitorNotifiesListenersUponAddAll() {
	final boolean changed[] = { false };
	monitor.addListener(collection -> { changed[0] = true; });
	monitor.addAll(Arrays.asList(1,2,3));
	
	assertThat(changed[0],is(true));
    }
    
    @Test
    public void monitorShouldNotNotifyListenersUponRemoveAllWhenSizeDoesNotChange() {
	final boolean changed[] = { false };
	monitor.addAll(Arrays.asList(1,2,3));
	monitor.addListener(collection -> { changed[0] = true; });
	monitor.removeAll(Arrays.asList(1,2,3));
	
	assertThat(changed[0],is(true));
    }
    
    @Test
    public void monitorNotifiesListenersUponRemoveAll() {
	final boolean changed[] = { false };
	monitor.addListener(collection -> { changed[0] = true; });
	monitor.removeAll(Arrays.asList(1,2,3));
	
	assertThat(changed[0],is(false));
    }
    
    @Test
    public void monitorNotifiesListenersUponRetainAll() {
	final boolean changed[] = { false };
	monitor.addAll(Arrays.asList(1,2,3));
	monitor.addListener(collection -> { changed[0] = true; });
	monitor.retainAll(Arrays.asList(1));
	
	assertThat(changed[0],is(true));
    }
    
    @Test
    public void monitorShouldNotNotifyListenersUponRetainAllWhenSizeDoesNotChange() {
	final boolean changed[] = { false };
	monitor.addAll(Arrays.asList(1,2,3));
	monitor.addListener(collection -> { changed[0] = true; });
	monitor.addAll(Arrays.asList(1,2,3));
	
	assertThat(changed[0],is(true));
    }
    
    @Test
    public void monitorNotifiesListenersUponClear() {
	final boolean changed[] = { false };
	monitor.add(1);
	monitor.addListener(collection -> { changed[0] = true; });
	monitor.clear();
	
	assertThat(changed[0],is(true));
    }
    
    @Test
    public void monitorShouldNotNotifyListenersUponClearWhenSizeDoesNotChange() {
	final boolean changed[] = { false };
	monitor.addListener(collection -> { changed[0] = true; });
	monitor.clear();
	
	assertThat(changed[0],is(false));
    }
    
}
