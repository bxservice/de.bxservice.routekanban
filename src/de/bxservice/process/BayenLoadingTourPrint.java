package de.bxservice.process;

import org.compiere.process.ProcessInfo;
import org.compiere.process.ServerProcessCtl;
import org.compiere.process.SvrProcess;
import org.compiere.util.Env;

public class BayenLoadingTourPrint extends SvrProcess {

	
	@Override
	protected void prepare() {
	}

	@Override
	protected String doIt() throws Exception {
		
		StatusPrinter printer = new StatusPrinter();
		ProcessInfo pi = printer.printLoadingTour(getAD_PInstance_ID(), Env.getContextAsDate(Env.getCtx(), "#Date"), get_TrxName());
		
		ServerProcessCtl.process(pi, null);
		
		return "";
	}
}
