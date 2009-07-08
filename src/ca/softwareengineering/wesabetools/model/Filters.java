package ca.softwareengineering.wesabetools.model;

/**
 * A few {@link TransactionFilter} implementations.
 */
public class Filters {
	public static class DateBeforeOrEqual implements TransactionFilter {
		long _date;

		public DateBeforeOrEqual(long date) {
			_date = date;
		}

		public boolean accept(WesabeTransaction w) {
			return w.getDate() <= _date;
		}
	}

	public static class DateAfterOrEqual implements TransactionFilter {
		long _date;

		public DateAfterOrEqual(long date) {
			_date = date;
		}

		public boolean accept(WesabeTransaction w) {
			return w.getDate() >= _date;
		}
	}

	public static class NotTransfer implements TransactionFilter {
		public boolean accept(WesabeTransaction w) {
			return !w.isTransfer();
		}
	}

	public static class AmountLessThanOrEqual implements TransactionFilter {
		final double cutoff;

		public AmountLessThanOrEqual(double cutoff) {
			this.cutoff = cutoff;
		}

		public boolean accept(WesabeTransaction w) {
			return w.getAmount() <= this.cutoff;
		}
	}
}
