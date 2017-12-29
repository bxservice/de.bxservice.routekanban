package de.bxservice.process;

import org.adempiere.base.IProcessFactory;
import org.compiere.process.ProcessCall;

public class BayenProcessFactory implements IProcessFactory {

	@Override
	public ProcessCall newProcessInstance(String className) {
		if (BayenPrintInvoiceProcess.class.getName().equals(className))
			return new BayenPrintInvoiceProcess();
		
		return null;
	}

}
