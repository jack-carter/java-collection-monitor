package com.shinobigroup.util.pid;

import java.util.function.Supplier;

public class SimplePIDController extends AbstractSimplePIDController {

    public SimplePIDController(final Supplier<Long> signal) {
	this.signal = signal;
    }
    
    @Override
    protected long previous() {
	return previous;
    }

    @Override
    protected long setpoint() {
	return setpoint;
    }

    @Override
    protected long signal() {
	return signal.get();
    }

    @Override
    protected long dt() {
	return dt;
    }

    @Override
    protected int Kp() {
	return Kp;
    }

    @Override
    protected double Ki() {
	return Ki;
    }

    @Override
    protected int Kd() {
	return Kd;
    }

    @Override
    protected void previous(long error) {
	this.previous = error;
    }

    @Override
    protected void integral(final long integral) {
	this.integral = integral;
    }
    
    private Supplier<Long> signal;
    
    private long setpoint;
    private long previous;
    private long integral;
    private long dt;
    
    private int Kp;
    private double Ki;
    private int Kd;

}
