package com.tianee.oa.core.workFlowFrame;

import com.tianee.oa.core.workFlowFrame.dataloader.TeeWorkFlowDataFactoryInterface;
import com.tianee.oa.core.workFlowFrame.insertor.TeeWorkFlowStartInsertorInterface;
import com.tianee.oa.core.workFlowFrame.validator.TeeWorkFlowValidatorFactoryInterface;

public interface TeeWorkFlowFactoryContextInterface {

	public TeeWorkFlowDataFactoryInterface getDataFactory();

	public void setDataFactory(TeeWorkFlowDataFactoryInterface dataFactory);

	public TeeWorkFlowValidatorFactoryInterface getValidatorFactory();

	public void setValidatorFactory(
			TeeWorkFlowValidatorFactoryInterface validatorFactory);

	public TeeWorkFlowStartInsertorInterface getWorkFlowStartInsertor();

	public void setWorkFlowStartInsertor(
			TeeWorkFlowStartInsertorInterface workFlowStartInsertor);

}