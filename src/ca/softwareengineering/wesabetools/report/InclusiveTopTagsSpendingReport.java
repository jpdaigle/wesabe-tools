package ca.softwareengineering.wesabetools.report;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import ca.softwareengineering.wesabetools.model.MutableDouble;
import ca.softwareengineering.wesabetools.model.TransactionStore;
import ca.softwareengineering.wesabetools.model.Util;
import ca.softwareengineering.wesabetools.model.WesabeTransaction;

public class InclusiveTopTagsSpendingReport implements TransactionReport {

	private static class TagMapEntry implements Comparable<TagMapEntry> {
		final Entry<String, MutableDouble> _e;

		public TagMapEntry(Entry<String, MutableDouble> e) {
			_e = e;
		}

		public int compareTo(TagMapEntry o) {
			if (o._e.getValue().get() > _e.getValue().get())
				return -1;
			else if (o._e.getValue().get() < _e.getValue().get())
				return 1;
			else
				return 0;
		}
	}

	public void runReport(OutputStream os, TransactionStore store) throws IOException {
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));

		List<WesabeTransaction> transactions = store.transactions();
		Map<String, MutableDouble> sums = new HashMap<String, MutableDouble>();

		for (WesabeTransaction t : transactions) {
			for (String tag : t.getTags()) {
				sumUp(sums, tag, Math.abs(t.getAmount()));
			}
		}

		List<TagMapEntry> sortedTags = new ArrayList<TagMapEntry>();
		for (Entry<String, MutableDouble> tag : sums.entrySet()) {
			sortedTags.add(new TagMapEntry(tag));
		}
		Collections.sort(sortedTags);
		for (int i = sortedTags.size() - 1; i >= 0; i--) {
			TagMapEntry tag = sortedTags.get(i);
			String toptag = tag._e.getKey();
			double tag_amt = tag._e.getValue().get();
			bw.write(String.format(" %s %2.2f\n", Util.padRight(toptag, 20), tag_amt));
		}

		bw.flush();
	}

	private static void sumUp(Map<String, MutableDouble> map, String t, double v) {
		MutableDouble md = map.get(t);
		md = (md != null) ? md : new MutableDouble(0);
		md.increment(v);
		map.put(t, md);
	}

}
