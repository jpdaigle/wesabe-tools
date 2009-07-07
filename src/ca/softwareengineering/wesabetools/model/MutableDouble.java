/**
 * 
 */
package ca.softwareengineering.wesabetools.model;

public class MutableDouble {
	private double val;

	public MutableDouble(double v) {
		val = v;
	}

	public double get() {
		return val;
	}

	public void set(double val) {
		this.val = val;
	}

	public void increment(double val) {
		this.val += val;
	}

	@Override
	public String toString() {
		return String.valueOf(this.val);
	}
}