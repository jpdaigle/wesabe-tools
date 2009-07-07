package ca.softwareengineering.wesabetools.model;

import java.text.ParseException;
import java.util.List;

/*
 * Partial representation of a transaction (we don't grab all of the fields, just the few we're interested in).
 */
public class WesabeTransaction {
	private final long date;
	private final double amount;
	private final List<String> tags;
	private final boolean isTransfer;

	// This field is mutable and public, used by reports to store their state
	public Object customData;

	public WesabeTransaction(long date, double amount, List<String> tags, boolean isTransfer) {
		this.date = date;
		this.amount = amount;
		this.tags = tags;
		this.isTransfer = isTransfer;
	}

	public static WesabeTransaction build(String date, String amount, List<String> tags, boolean isTransfer)
			throws ParseException {
		long time = Util.getDateFromIso8601Day(date);
		return new WesabeTransaction(time, Double.parseDouble(amount), tags, isTransfer);
	}

	public long getDate() {
		return date;
	}

	public double getAmount() {
		return amount;
	}

	public List<String> getTags() {
		return tags;
	}

	public boolean isTransfer() {
		return isTransfer;
	}

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append(String.format("Date:%s Amt:%s Transfer:%s [", Util.getDateString(getDate()), getAmount(),
				isTransfer()));
		for (String t : getTags()) {
			str.append(t).append(" ");
		}
		str.append("]");
		return str.toString();
	}
}
