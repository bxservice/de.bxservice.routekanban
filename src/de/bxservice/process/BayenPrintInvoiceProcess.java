package de.bxservice.process;

import org.compiere.process.SvrProcess;
import org.compiere.util.Env;
import org.kanbanboard.model.MKanbanBoard;

public class BayenPrintInvoiceProcess extends SvrProcess{

	@Override
	protected void prepare() {
	}

	@Override
	protected String doIt() throws Exception {
		
		int KDB_KanbanBoard_ID = Integer.parseInt(Env.getContext(getCtx(), "#KDB_KanbanBoard_ID"));
		MKanbanBoard kanbanBoard = new MKanbanBoard(getCtx(), KDB_KanbanBoard_ID, get_TrxName());
		
		String where = kanbanBoard.getWhereClause()
				+ " AND " + Env.getContext(Env.getCtx(), "#KDB_Params");

		StatusPrinter printer = new StatusPrinter();
		printer.setWhereClause(where);
		printer.printOrder(getAD_PInstance_ID(), get_TrxName());
		
		return "";
	}

}
