package ca.softwareengineering.wesabetools;

import java.io.File;

import ca.softwareengineering.wesabetools.model.Filters;
import ca.softwareengineering.wesabetools.model.TransactionStore;
import ca.softwareengineering.wesabetools.model.Util;
import ca.softwareengineering.wesabetools.xml.DomParseSupport;

public class TestMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if (args.length < 3) {
			System.err.println("Must supply arguments: filename startdate(yyyy-MM-dd) enddate(yyyy-MM-dd).");
			return;
		}

		try {
			File f = new File(args[0]);
			TransactionStore wstore = new TransactionStore();

			String startDate = args[1], endDate = args[2];
			wstore.addFilter(new Filters.DateAfter(Util.getDateFromIso8601Day(startDate)));
			wstore.addFilter(new Filters.DateBefore(Util.getDateFromIso8601Day(endDate)));
			wstore.addFilter(new Filters.NotTransfer());
			wstore.addFilter(new Filters.AmountLessThanOrEqual(0));

			DomParseSupport.loadTransactions(f, wstore);
			System.out.printf("Date Range: %s - %s\n", startDate, endDate);
			System.out.println(wstore.getStats());
			SpendingReport.getSpendingReport(wstore);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
