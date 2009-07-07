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
		System.out.println("asdf");
		if (args.length == 0) {
			System.err.println("Must supply filename.");
		}

		try {
			File f = new File(args[0]);
			TransactionStore wstore = new TransactionStore();
			
			wstore.addFilter(new Filters.DateAfter(Util.getDateFromIso8601Day("2009-06-01")));
			wstore.addFilter(new Filters.DateBefore(Util.getDateFromIso8601Day("2009-06-30")));
			wstore.addFilter(new Filters.NotTransfer());
			wstore.addFilter(new Filters.AmountLessThanOrEqual(0));
			
			
			DomParseSupport.loadTransactions(f, wstore);
			System.out.println(wstore);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
