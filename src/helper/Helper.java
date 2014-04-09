package helper;
import java.util.*;
public class Helper {
	public static List<String> getList(String [] args) {
		List<String> argsList = new ArrayList<String>();
		for (String arg : args) {
			argsList.add(arg);
		}
		return argsList;
	}
}