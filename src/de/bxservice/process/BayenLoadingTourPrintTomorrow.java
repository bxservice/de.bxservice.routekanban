package de.bxservice.process;

import java.sql.Timestamp;

import org.compiere.process.ProcessInfo;
import org.compiere.process.ServerProcessCtl;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;

public class BayenLoadingTourPrintTomorrow extends SvrProcess {

	
	@Override
	protected void prepare() {
	}

	@Override
	protected String doIt() throws Exception {
		
		Timestamp date = DB.getSQLValueTSEx(get_TrxName(), "SELECT next_working_day(?) FROM DUAL", Env.getContextAsDate(Env.getCtx(), "#Date"));
		
		StatusPrinter printer = new StatusPrinter();
		ProcessInfo pi = printer.printLoadingTour(getAD_PInstance_ID(), date, get_TrxName());
		
		ServerProcessCtl.process(pi, null);
		
		return "";
	}

}
