package com.shinobigroup.util.pid;

public abstract class AbstractSimplePIDController extends AbstractPIDController {

    protected long error() {
	return setpoint() - signal();
    }
    
    protected long integral() {
	return accumulated_integral((accumulated_integral() + error()) * dt());
    }
    
    protected long derivative() {
	return (error() - previous()) * dt();
    }
    
    abstract protected long accumulated_integral();
    abstract protected long accumulated_integral(long integral);
    
    abstract protected long previous();
    abstract protected long setpoint();
    abstract protected long signal();
    abstract protected long dt();
    
}
