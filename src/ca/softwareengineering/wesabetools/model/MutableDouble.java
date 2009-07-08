package ca.softwareengineering.wesabetools.model;

public class MutableDouble implements Comparable<MutableDouble> {
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

	public int compareTo(MutableDouble o) {
		if (o.val > this.val)
			return -1;
		else if (o.val < this.val)
			return 1;
		else
			return 0;
	}

	@Override
	public String toString() {
		return String.valueOf(this.val);
	}
}