package com.shinobigroup.collections.util;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.Before;
import org.junit.Test;

import com.shinobigroup.collections.util.CollectionSizeThresholdMonitor;

import java.util.ArrayList;
import java.util.Collection;

public class CollectionSizeThresholdMonitorTest {

    private Collection<Integer> collection;
    private CollectionSizeThresholdMonitor<Integer> monitor;
    
    @Before
    public void setup() {
	collection = new ArrayList<Integer>();
	monitor = CollectionSizeThresholdMonitor.monitor(collection);
    }
    
    @Test
    public void monitorShouldNotifyListenerOnlyWhenConditionIsMet() {
	final int changed[] = { 0 };
	monitor.when(size -> size > 3,collection -> changed[0]++);
	monitor.add(1);
	monitor.add(2);
	monitor.add(3);
	monitor.add(3);

	assertThat(collection.size(),is(4));
	assertThat(changed[0],is(1));
    }

    @Test
    public void monitorShouldNotifyListenerOnlyWhenTheirConditionIsMet() {
	final int changed[] = { 0, 0, 0, 0 };
	
	monitor.when(size -> size > 0,collection -> changed[0]++);
	monitor.when(size -> size > 1,collection -> changed[1]++);
	monitor.when(size -> size > 2,collection -> changed[2]++);
	monitor.when(size -> size > 3,collection -> changed[3]++);
	
	monitor.add(1);
	monitor.add(2);
	monitor.add(3);
	monitor.add(4);

	assertThat(changed[0],is(4));
	assertThat(changed[1],is(3));
	assertThat(changed[2],is(2));
	assertThat(changed[3],is(1));
    }

}
