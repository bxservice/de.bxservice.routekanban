package de.bxservice.process;

import java.util.List;

import org.compiere.model.MOrder;
import org.compiere.model.Query;
import org.compiere.util.CLogger;
import org.compiere.util.Env;

import de.bayen.freibier.util.OrderDotMatrixFormat;

public class StatusPrinter {
	
	/**	Logger							*/
	protected CLogger log = CLogger.getCLogger (getClass());
	private StringBuilder whereClause = new StringBuilder();
	
	public StatusPrinter(String andClause) {
		
		whereClause.append(" EXISTS (SELECT viewID FROM T_Selection WHERE T_Selection.AD_PInstance_ID=? ");
		whereClause.append(" AND (cast(T_Selection.viewID as int)) = C_Order.BAY_Route_ID) ");
		whereClause.append(" AND IsSoTrx='Y' AND IsPrinted='N' AND DocStatus IN ('IP') AND ");
		whereClause.append(andClause);
	}
	
	public void printOrder(int AD_PInstance_ID, String trxName) {
		
		List<MOrder> statusOrders = new Query(Env.getCtx(), MOrder.Table_Name, whereClause.toString(), trxName)
				.setParameters(AD_PInstance_ID, Env.getContextAsDate(Env.getCtx(), "#Date"))
				.setOnlyActiveRecords(true)
				.list();
		
		for (MOrder order : statusOrders) {
			String fileName = new OrderDotMatrixFormat().print(Env.getCtx(), order.getC_Order_ID(), trxName);
			order.setIsPrinted(true);
			order.saveEx(trxName);
			log.fine("@File@ @Generated@ " + fileName);
		}
	}

}
