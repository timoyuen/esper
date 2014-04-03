package esperengine.cepConfig;
import com.espertech.esper.client.*;
public class CepConfig
{
	private	Configuration cepConfigure;
	private EPAdministrator cepAdmin;
	private EPRuntime cepRT;
	public CepConfig()
	{
		cepConfigure = new Configuration();
		EPServiceProvider cep = EPServiceProviderManager.getDefaultProvider(cepConfigure);
		cepRT = cep.getEPRuntime();
		cepAdmin = cep.getEPAdministrator();
	}

	public void addEventType(String eventName, Class className)
	{
		cepAdmin.getConfiguration().addEventType(eventName, className);
	}

	public void createEPL(String epl, UpdateListener listener)
	{
		EPStatement cepStatement = cepAdmin.createEPL(epl);
		cepStatement.addListener(listener);
	}
	public EPRuntime getRuntime()
	{
		return cepRT;
	}
	public static void sendEvent(Object crt, Object event)
	{
		EPRuntime cepRT = (EPRuntime)crt;
		cepRT.sendEvent(event);
	}
}