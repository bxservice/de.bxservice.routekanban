package de.bxservice.process;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.compiere.model.MOrder;
import org.compiere.model.MProcess;
import org.compiere.model.Query;
import org.compiere.process.ProcessInfo;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.Env;

import de.bayen.freibier.util.OrderDotMatrixFormat;

public class StatusPrinter {
	
	private static final String PROCESS_VALUE = "BAYLoadingTour"; 
	
	/**	Logger							*/
	protected CLogger log = CLogger.getCLogger (getClass());
	private StringBuilder whereClause = new StringBuilder();
	
	public void setWhereClause(String andClause) {
		
		whereClause.append(" EXISTS (SELECT viewID FROM T_Selection WHERE T_Selection.AD_PInstance_ID=? ");
		whereClause.append(" AND (cast(T_Selection.viewID as int)) = C_Order.BAY_Route_ID) AND ");
		whereClause.append(andClause);
	}
	
	public void printOrder(int AD_PInstance_ID, String trxName) {
		
		List<MOrder> statusOrders = new Query(Env.getCtx(), MOrder.Table_Name, whereClause.toString(), trxName)
				.setParameters(AD_PInstance_ID)
				.setOnlyActiveRecords(true)
				.list();
		
		for (MOrder order : statusOrders) {
			String fileName = new OrderDotMatrixFormat().print(Env.getCtx(), order.getC_Order_ID(), trxName);
			order.setIsPrinted(true);
			order.saveEx(trxName);
			log.fine("@File@ @Generated@ " + fileName);
		}
	}
	
	public ProcessInfo printLoadingTour(int AD_PInstance_ID, Timestamp date, String trxName) {
		int BAY_Route_ID = DB.getSQLValue(trxName, "SELECT viewID FROM T_Selection WHERE T_Selection.AD_PInstance_ID = " + AD_PInstance_ID);

		ProcessInfo pi = new ProcessInfo("", MProcess.getProcess_ID(PROCESS_VALUE, trxName));
		
		ArrayList<ProcessInfoParameter> jasperPrintParams = new ArrayList<ProcessInfoParameter>();
		ProcessInfoParameter pip;
		pip = new ProcessInfoParameter("BAY_Route_ID", BAY_Route_ID, null, null, null);
		jasperPrintParams.add(pip);
		pip = new ProcessInfoParameter("DateOrdered", date, null, null, null);
		jasperPrintParams.add(pip);
		pi.setParameter(jasperPrintParams.toArray(new ProcessInfoParameter[]{}));
		
		return pi;
	}

}
