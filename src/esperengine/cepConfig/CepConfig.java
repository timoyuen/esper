package esperengine.cepConfig;
import java.util.*;
import com.espertech.esper.client.*;
import com.espertech.esper.collection.*;
import esperengine.stock.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

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
    static Log log = LogFactory.getLog(CepConfig.class);
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

	public void createEPL(String epl, List<String> args, String eplName, UpdateListener listener)
	{
		/// createEPL(String eplStatement, String statementName)
		log.info("createing epl "+eplName+": "+epl);
		try {
			int i = 0;
			EPPreparedStatement pstmt = cepAdmin.prepareEPL(epl);
			for (String arg : args) {
				pstmt.setObject(++i, arg);
			}
			EPStatement stmt = cepAdmin.create(pstmt, eplName);
			stmt.addListener(listener);
		} catch (Exception e) {
			System.out.println(e);
		}
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
	public static boolean isEPLValid(String epl, List<String> args) {
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