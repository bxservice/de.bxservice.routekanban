package de.bxservice.process;

import java.util.ArrayList;

import org.compiere.model.MProcess;
import org.compiere.process.ProcessInfo;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.ServerProcessCtl;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;

public class BayenLoadingTourPrint extends SvrProcess {

	private static final String PROCESS_VALUE = "BAYLoadingTour"; 
	
	@Override
	protected void prepare() {
	}

	@Override
	protected String doIt() throws Exception {
		
		int BAY_Route_ID = DB.getSQLValue(get_TrxName(), "SELECT viewID FROM T_Selection WHERE T_Selection.AD_PInstance_ID = " + getAD_PInstance_ID());

		ProcessInfo pi = new ProcessInfo("", MProcess.getProcess_ID(PROCESS_VALUE, get_TrxName()));
		
		ArrayList<ProcessInfoParameter> jasperPrintParams = new ArrayList<ProcessInfoParameter>();
		ProcessInfoParameter pip;
		pip = new ProcessInfoParameter("BAY_Route_ID", BAY_Route_ID, null, null, null);
		jasperPrintParams.add(pip);
		pip = new ProcessInfoParameter("DateOrdered", Env.getContextAsDate(getCtx(), "#Date"), null, null, null);
		jasperPrintParams.add(pip);
		pi.setParameter(jasperPrintParams.toArray(new ProcessInfoParameter[]{}));

		ServerProcessCtl.process(pi, null);
		
		return "";
	}
}
