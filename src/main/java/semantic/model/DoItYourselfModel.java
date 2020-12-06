package semantic.model;

import java.util.List;

import semantic.controler.Controler;

public class DoItYourselfModel implements IModelFunctions
{
	IConvenienceInterface model;
	
	public DoItYourselfModel(IConvenienceInterface m) {
		this.model = m;
	}

	@Override
	public String createPlace(String name) {
		return model.createInstance(name, model.getEntityURI("Place").get(0)); 
	}

	@Override
	public String createInstant(TimestampEntity instant) {
		String instantURI = model.createInstance(instant.getTimeStamp(), model.getEntityURI("Instant").get(0)); 
		String propURI = model.getEntityURI("a pour timestamp").get(0);
		model.addDataPropertyToIndividual(instantURI, propURI, instant.getTimeStamp());
		return instantURI;
	}

	@Override
	public String getInstantURI(TimestampEntity instant) {
		return model.getEntityURI(instant.timestamp).size()> 0 ? model.getEntityURI(instant.timestamp).get(0) : null;
	}

	@Override
	public String getInstantTimestamp(String instantURI)
	{
		List<List<String>> listProp = model.listProperties(instantURI); 
		String propURI = model.getEntityURI("a pour timestamp").get(0);
		for (int i=0; i<listProp.size(); i++) {
			if (listProp.get(i).get(0).equals(propURI)) {
				return listProp.get(i).get(1); 
			}
		}
		return null;  
	}

	@Override
	public String createObs(String value, String paramURI, String instantURI) {
		String obsURI = model.createInstance("obs"+value, model.getEntityURI("Observation").get(0)); 
		String propURI = model.getEntityURI("a pour valeur").get(0); 
		model.addDataPropertyToIndividual(obsURI, propURI, value);
		propURI = model.getEntityURI("a pour date").get(0);
		model.addObjectPropertyToIndividual(obsURI, propURI, instantURI);
		String sensorURI = model.whichSensorDidIt(this.getInstantTimestamp(instantURI), paramURI); 
		model.addObservationToSensor(obsURI, sensorURI);
		return obsURI;
	}
}
