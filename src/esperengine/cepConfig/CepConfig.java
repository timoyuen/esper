package esperengine.cepConfig;
import java.util.*;
import com.espertech.esper.client.*;
import com.espertech.esper.collection.*;
import esperengine.stock.*;
public class CepConfig
{
	private	Configuration cepConfigure;
	private EPAdministrator cepAdmin;
	private EPRuntime cepRT;
	static Configuration testCepConfigure = new Configuration();
	static EPAdministrator testCepAdmin;
	static {
		EPServiceProvider testCep = EPServiceProviderManager.getDefaultProvider(testCepConfigure);
		testCepAdmin = testCep.getEPAdministrator();
		testCepAdmin.getConfiguration().addEventType("Stock", StockInfo.class);
	}
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

	public void createEPL(String epl, String eplName, UpdateListener listener)
	{
		/// createEPL(String eplStatement, String statementName)
		EPStatement cepStatement = cepAdmin.createEPL(epl, eplName);
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
	public static boolean isEPLValid(String epl, String []args) {
		int i = 0;
		boolean flag = true;
		try {
			EPPreparedStatement pstmt = testCepAdmin.prepareEPL(epl);
			for (String arg : args) {
				pstmt.setObject(++i, arg);
			}
			testCepAdmin.create(pstmt);
		} catch (Exception e) {
			flag = false;
		} finally {
			testCepAdmin.destroyAllStatements();
		}
		return flag;
	}
}