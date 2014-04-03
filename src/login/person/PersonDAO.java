package login.person;
public interface PersonDAO
{
	public boolean isLogin(PersonVo pv);
	public int  newUser(PersonVo pv);
	public boolean isUserExist(PersonVo pv);
}