package de.bxservice.process;

import org.adempiere.base.IProcessFactory;
import org.compiere.process.ProcessCall;

public class BayenProcessFactory implements IProcessFactory {

	@Override
	public ProcessCall newProcessInstance(String className) {
		if (BayenPrintInvoiceProcess.class.getName().equals(className))
			return new BayenPrintInvoiceProcess();
		if (BayenPrintInvoiceTomorrow.class.getName().equals(className))
			return new BayenPrintInvoiceTomorrow();
		if (BayenLoadingTourPrint.class.getName().equals(className))
			return new BayenLoadingTourPrint();
		if (BayenLoadingTourPrintTomorrow.class.getName().equals(className))
			return new BayenLoadingTourPrintTomorrow();
		
		return null;
	}

}
