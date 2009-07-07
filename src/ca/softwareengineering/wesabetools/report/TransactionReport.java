package ca.softwareengineering.wesabetools.report;

import java.io.IOException;
import java.io.OutputStream;
import ca.softwareengineering.wesabetools.model.TransactionStore;

public interface TransactionReport {
	public void runReport(OutputStream os, TransactionStore store) throws IOException;
}
