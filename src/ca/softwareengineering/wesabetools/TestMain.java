package ca.softwareengineering.wesabetools;

import java.io.File;
import ca.softwareengineering.wesabetools.model.Filters;
import ca.softwareengineering.wesabetools.model.TransactionStore;
import ca.softwareengineering.wesabetools.model.Util;
import ca.softwareengineering.wesabetools.report.SpendingReport;
import ca.softwareengineering.wesabetools.report.TransactionReport;
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
			TransactionStore filteredStore = new TransactionStore();

			String startDate = args[1], endDate = args[2];
			filteredStore.addFilter(new Filters.DateAfter(Util.getDateFromIso8601Day(startDate)));
			filteredStore.addFilter(new Filters.DateBefore(Util.getDateFromIso8601Day(endDate)));
			filteredStore.addFilter(new Filters.NotTransfer());
			filteredStore.addFilter(new Filters.AmountLessThanOrEqual(0));

			DomParseSupport.loadTransactions(f, filteredStore);
			System.out.printf("Date Range: %s - %s\n", startDate, endDate);
			System.out.println(filteredStore.getStats());

			TransactionReport spendingRep = new SpendingReport();
			writeReportHeader(spendingRep);
			spendingRep.runReport(System.out, filteredStore);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public static void writeReportHeader(TransactionReport rep) {
		// Write header
		System.out.println("====================");
		System.out.println("Class: " + rep.getClass().getSimpleName());
		System.out.println("====================");
	}

}
