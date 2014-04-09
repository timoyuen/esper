package login.person;
public interface PersonDAO
{
	public boolean isLogin(PersonVo pv);
	public boolean newUser(PersonVo pv);
	public boolean isUserExist(PersonVo pv);
}