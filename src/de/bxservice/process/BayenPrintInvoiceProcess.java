package de.bxservice.process;

import org.compiere.process.SvrProcess;

public class BayenPrintInvoiceProcess extends SvrProcess{

	@Override
	protected void prepare() {
	}

	@Override
	protected String doIt() throws Exception {
		
		String where = "DateOrdered = to_date(?, 'yyyy-mm-dd') ";
		
		StatusPrinter printer = new StatusPrinter();
		printer.setWhereClause(where);
		printer.printOrder(getAD_PInstance_ID(), get_TrxName());
		
		return "";
	}

}
