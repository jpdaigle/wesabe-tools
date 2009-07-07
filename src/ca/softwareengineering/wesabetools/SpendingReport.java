package ca.softwareengineering.wesabetools;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ca.softwareengineering.wesabetools.model.TransactionStore;
import ca.softwareengineering.wesabetools.model.WesabeTransaction;

public class SpendingReport {

	public static class MutableDouble {
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

	}

	public static String getSpendingReport(TransactionStore store) {
		List<WesabeTransaction> transactions = store.transactions();
		Map<String, MutableDouble> sums = new HashMap<String, MutableDouble>();

		for (WesabeTransaction t : transactions) {
			t.customData = Boolean.FALSE; // whether counted
		}
		

		return "";
	}

	public static String findTopTag(Map<String, MutableDouble> map) {
		return "";
	}

}
