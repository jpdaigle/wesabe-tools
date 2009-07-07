package ca.softwareengineering.wesabetools.model;

public interface TransactionFilter {
	public boolean accept(WesabeTransaction w);
}
