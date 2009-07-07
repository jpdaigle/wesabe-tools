package ca.softwareengineering.wesabetools.report;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import ca.softwareengineering.wesabetools.model.MutableDouble;
import ca.softwareengineering.wesabetools.model.TransactionStore;
import ca.softwareengineering.wesabetools.model.Util;
import ca.softwareengineering.wesabetools.model.WesabeTransaction;

public class SpendingReport implements TransactionReport {

	public void runReport(OutputStream os, TransactionStore store) throws IOException {
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));

		List<WesabeTransaction> transactions = store.transactions();
		Map<String, MutableDouble> sums = new HashMap<String, MutableDouble>();

		for (WesabeTransaction t : transactions) {
			t.customData = Boolean.FALSE; // whether counted
		}

		boolean done = false;
		while (!done) {
			sums.clear();
			done = true;
			for (WesabeTransaction t : transactions) {
				if ((Boolean) t.customData) {
					continue; // already processed
				} else {
					done = false;
				}
				for (String tag : t.getTags()) {
					sumUp(sums, tag, Math.abs(t.getAmount()));
				}
			}
			String toptag = findTopTag(sums);
			double tag_amt = 0;
			for (WesabeTransaction t : transactions) {
				if ((Boolean) t.customData)
					continue; // already processed transaction

				if (t.getTags().contains(toptag)) {
					t.customData = Boolean.TRUE;
					tag_amt += Math.abs(t.getAmount());
				}
			}
			if (tag_amt > 0) {
				String strout = String.format(" %s %2.2f\n", Util.padRight(toptag, 20), tag_amt);
				bw.write(strout);
			}
		}
		bw.flush();
	}

	public String getSpendingReport(TransactionStore store) {
		return "";
	}

	private static void sumUp(Map<String, MutableDouble> map, String t, double v) {
		MutableDouble md = map.get(t);
		md = (md != null) ? md : new MutableDouble(0);
		md.increment(v);
		map.put(t, md);
	}

	public static String findTopTag(Map<String, MutableDouble> map) {
		double max = 0;
		String strmax = "";
		for (Entry<String, MutableDouble> e : map.entrySet()) {
			if (e.getValue().get() > max) {
				max = e.getValue().get();
				strmax = e.getKey();
			}
		}
		return strmax;
	}

}
