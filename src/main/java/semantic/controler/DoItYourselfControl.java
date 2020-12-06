package semantic.controler;

import java.util.List;

import semantic.model.IConvenienceInterface;
import semantic.model.IModelFunctions;
import semantic.model.ObservationEntity;

public class DoItYourselfControl implements IControlFunctions
{
	private IConvenienceInterface model;
	private IModelFunctions customModel;
	
	public DoItYourselfControl(IConvenienceInterface model, IModelFunctions customModel)
	{
		this.model = model;
		this.customModel = customModel;
	}
	
	@Override
	public void instantiateObservations(List<ObservationEntity> obsList,
			String paramURI) {
		for (ObservationEntity obs: obsList) {
			String instantURi = customModel.getInstantURI(obs.getTimestamp()) ; 
			if (instantURi == null) {
				instantURi = customModel.createInstant(obs.getTimestamp()); 
			}
			customModel.createObs(obs.getValue().toString(), paramURI, instantURi); 
		}
	}
}
