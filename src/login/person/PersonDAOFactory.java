package login.person;

public class PersonDAOFactory
{
	public static PersonDAO getPersonDAOInstance()
	{
		return new PersonDAOImpl();
	}
}
