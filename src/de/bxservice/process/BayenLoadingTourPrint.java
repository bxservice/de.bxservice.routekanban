package de.bxservice.process;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

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
		String dateParameter = Env.getContext(Env.getCtx(), "#KDB_Params");
		
		int i = dateParameter.indexOf("'");
		if (i != -1) {
			String temp = dateParameter.substring(i+1, dateParameter.length());	// from first '

			int j = temp.indexOf("'");						// next '
			if (j < 0) {									// no second tag
				return "@Error"; //No date
			}

			dateParameter = temp.substring(0, j);
		} else 
			return "@Error"; //No date
		
		Date date=new SimpleDateFormat("yyyy-MM-dd").parse(dateParameter);
		ProcessInfo pi = printer.printLoadingTour(getAD_PInstance_ID(), new Timestamp(date.getTime()), get_TrxName());
		ServerProcessCtl.process(pi, null);
		
		return "";
	}
}
