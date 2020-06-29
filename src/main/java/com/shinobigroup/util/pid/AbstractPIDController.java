package com.shinobigroup.util.pid;

public abstract class AbstractPIDController {

    public long output() {	
	return (long)(Kp() * error() + Ki() * integral() + Kd() * derivative());
    }

    abstract protected int Kp();    // Proportional gain
    abstract protected double Ki(); // Integral gain
    abstract protected int Kd();    // Derivative gain
    
    abstract protected long error();
    abstract protected long integral();
    abstract protected long derivative();
    
}
