package de.bxservice.process;

import java.util.List;

import org.compiere.model.MOrder;
import org.compiere.model.Query;
import org.compiere.process.SvrProcess;

import de.bayen.freibier.util.OrderDotMatrixFormat;

public class BayenPrintInvoiceProcess extends SvrProcess{

	@Override
	protected void prepare() {
	}

	@Override
	protected String doIt() throws Exception {
		
		String where = " EXISTS (SELECT viewID FROM T_Selection WHERE T_Selection.AD_PInstance_ID=? "
				+ " AND (cast(T_Selection.viewID as int)) = C_Order.BAY_Route_ID) ";
		
		List<MOrder> statusOrders = new Query(getCtx(), MOrder.Table_Name, where, get_TrxName())
				.setParameters(getAD_PInstance_ID())
				.setOnlyActiveRecords(true)
				.list();
		
		for (MOrder order : statusOrders) {
			String fileName = new OrderDotMatrixFormat().print(getCtx(), order.getC_Order_ID(), get_TrxName());
			order.setIsPrinted(true);
			order.saveEx();
			log.fine("@File@ @Generated@ " + fileName);
		}
		
		return "";
	}

}
