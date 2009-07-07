package ca.softwareengineering.wesabetools.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Container for WesabeTransaction objects that applies filtering at add time.
 */
public class TransactionStore {
	private final List<WesabeTransaction> _txList = new ArrayList<WesabeTransaction>();
	private final Set<TransactionFilter> _filters = new HashSet<TransactionFilter>();
	private int cnt_reject = 0;

	public TransactionStore() {
	}

	public void addFilter(TransactionFilter filter) {
		_filters.add(filter);
	}

	public boolean addTransaction(WesabeTransaction tx) {
		boolean add = true;
		for (TransactionFilter f : _filters) {
			add = add && f.accept(tx);
			if (!add)
				break;
		}
		if (add) {
			_txList.add(tx);
		} else {
			cnt_reject++;
		}
		return add;
	}

	public List<WesabeTransaction> transactions() {
		return Collections.unmodifiableList(_txList);
	}

	public String getStats() {
		return String.format("%s transactions, %s rejected by filters.", _txList.size(), cnt_reject);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (WesabeTransaction t : _txList) {
			sb.append(t.toString()).append("\n");
		}
		sb.append(getStats());
		return sb.toString();
	}
}
