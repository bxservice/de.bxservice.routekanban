package de.bxservice.process;

import org.compiere.process.SvrProcess;

public class BayenPrintInvoiceTomorrow extends SvrProcess {

	@Override
	protected void prepare() {
	}

	@Override
	protected String doIt() throws Exception {
		
		String where = " DateOrdered = next_working_day(?) ";
		
		StatusPrinter printer = new StatusPrinter(where);
		printer.printOrder(getAD_PInstance_ID(), get_TrxName());
		
		return "";
	}

}
