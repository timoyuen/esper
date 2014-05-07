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

	private static String getEPLFiltered(String epl, List<String> argExampleList) {
		int anything = -1;
		int argIndex = 0;
		while (true) {
			anything = epl.indexOf("?", anything + 1);
			if (anything == -1) {
				break;
			} else {
				if (epl.substring(anything - 1, anything).equals("=")) {
					epl = epl.replaceFirst("\\?", "#");
					argIndex += 1;
					log.info("1"+epl);
				} else {
					log.info("2"+epl);
					epl = epl.replaceFirst("\\?", argExampleList.get(argIndex));
					argExampleList.remove(argIndex);
				}
			}
		}
		return epl.replace("#", "?");
	}
	public void createEPL(String epl, List<String> args, String eplName, UpdateListener listener)
	{
		/// createEPL(String eplStatement, String statementName)
		log.info("createing epl "+eplName+": "+epl);
		epl = getEPLFiltered(epl, args);
		log.info("filtered EPL"+epl);
		log.info(args);
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

	public void destroyEPL(int subId) {
		log.info("DestroyEPL..."+subId);
		try {
			EPStatement epl = cepAdmin.getStatement(Integer.toString(subId));
			epl.destroy();
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
		List<String> oldArgs = new ArrayList<String>();
		for (String a : args)
			oldArgs.add(a);
		String filteredEPL = getEPLFiltered(epl, args);
		boolean flag = true;
		try {
			EPPreparedStatement pstmt = testCepAdmin.prepareEPL(filteredEPL);
			for (String arg : args) {
				pstmt.setObject(++i, arg);
			}
			testCepAdmin.create(pstmt);
		} catch (Exception e) {
			flag = false;
			System.out.println(e);
		} finally {
			testCepAdmin.destroyAllStatements();
		}
		args.clear();
		for (String a : oldArgs)
			args.add(a);
		return flag;
	}
}