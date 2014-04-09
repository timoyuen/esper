// 只包含setter和getter方法的类

package login.person;
import java.util.*;
public class PersonVo
{
	// 表中所有字段
	private String userName;
	private String password;
	private String passwordAgain;
	private List errors;
	private String email, telephone;
	private Map<Integer, String> errorMsg;
	private enum ErrorId {
		USERNAME_EMPTY, USERNAME_LEN_ERROR, PASSWORD_EMPTY, PASSWORD_AGAIN_EMPTY,
		TELEPHONE_LENGTH_ERROR, PASSWORD_LENGTH_ERROR,
		PASSWORD_NOT_SAME, EMAIL_EMPTY, TELEPHONE_EMPTY, EMAIL_TYPE_ERROR,
	}
	public PersonVo() {
		errorMsg = new HashMap<Integer, String>();
		errorMsg.put(ErrorId.USERNAME_EMPTY.ordinal(), "ID不能为空！");
		errorMsg.put(ErrorId.USERNAME_LEN_ERROR.ordinal(), "ID的长度应为3~10位！");
		errorMsg.put(ErrorId.PASSWORD_EMPTY.ordinal(), "密码不能为空！");
		errorMsg.put(ErrorId.PASSWORD_AGAIN_EMPTY.ordinal(), "再次输入密码");
		errorMsg.put(ErrorId.PASSWORD_NOT_SAME.ordinal(), "两次密码不一致！");
		errorMsg.put(ErrorId.PASSWORD_LENGTH_ERROR.ordinal(), "密码的长度大于6位！");
		errorMsg.put(ErrorId.EMAIL_EMPTY.ordinal(), "email不能为空!");
		errorMsg.put(ErrorId.TELEPHONE_EMPTY.ordinal(), "联系电话不能为空");
		errorMsg.put(ErrorId.EMAIL_TYPE_ERROR.ordinal(), "email格式错误！");
		errorMsg.put(ErrorId.TELEPHONE_LENGTH_ERROR.ordinal(), "联系电话长度错误");
	}
	private boolean isEmpty(String str, ErrorId errorId) {
		if (str == null || "".equals(str)) {
			errors.add(errorMsg.get(errorId.ordinal()));
			return true;
		}
		return false;
	}
	public boolean regValidate()
	{
		boolean flag = true;
		if (isEmpty(this.userName, ErrorId.USERNAME_EMPTY)) {
			flag = false;
		} else {
			if(this.userName.length() < 3 || this.userName.length() > 10) {
				flag = false;
				errors.add(errorMsg.get(ErrorId.USERNAME_LEN_ERROR));
			}
		}

		if (isEmpty(this.password, ErrorId.PASSWORD_EMPTY)) {
			flag = false;
		} else {
			if(this.password.length() < 6) {
				flag = false;
				errors.add(errorMsg.get(ErrorId.PASSWORD_LENGTH_ERROR.ordinal()));
			}
		}
		if (isEmpty(this.passwordAgain, ErrorId.PASSWORD_AGAIN_EMPTY)) {
			flag = false;
		} else {
			if (!this.passwordAgain.equals(this.password)) {
				flag = false;
				errors.add(errorMsg.get(ErrorId.PASSWORD_NOT_SAME.ordinal()));
			}
		}

		if (isEmpty(this.email, ErrorId.EMAIL_EMPTY)) {
			flag = false;
		} else {
			if (this.email.indexOf('@') == -1) {
				flag = false;
				errors.add(errorMsg.get(ErrorId.EMAIL_TYPE_ERROR.ordinal()));
			}
		}
		if (isEmpty(this.telephone, ErrorId.TELEPHONE_EMPTY)) {
			flag = false;
		} else {
			if (this.telephone.length() != 11) {
				flag = false;
				errors.add(errorMsg.get(ErrorId.TELEPHONE_LENGTH_ERROR.ordinal()));
			}
		}
		System.out.println(errors);
		return flag;
	}

	public boolean loginValidate()
	{
		boolean flag = true;
		if (isEmpty(userName, ErrorId.USERNAME_EMPTY)) {
			flag = false;
		} else {
			if(this.userName.length() < 3 || this.userName.length() > 10) {
				flag = false;
				errors.add(errorMsg.get(ErrorId.USERNAME_LEN_ERROR.ordinal()));
			}
		}
		// 验证密码
		if(isEmpty(password, ErrorId.PASSWORD_EMPTY)) {
			flag = false;
		} else {
			if(this.password.length() < 6) {
				flag = false;
				errors.add(errorMsg.get(ErrorId.PASSWORD_LENGTH_ERROR.ordinal()));
			}
		}
		return flag;
	}

	public void setErrors(List errors) { this.errors = errors; }
	public void setUserName(String userName) { this.userName = userName; }
	public void setPassword(String password) { this.password = password; }
	public void setEmail(String email) { this.email = email; }
	public void setTelephone(String telephone) { this.telephone = telephone; }
	public void setPasswordAgain(String passwordAgain) { this.passwordAgain = passwordAgain; }
	public List getErrors() { return this.errors; }
	public String getUserName() { return this.userName; }
	public String getPassword() { return this.password; }
	public String getEmail() { return this.email; }
	public String getTelephone() { return this.telephone; }
	public String getPasswordAgain() { return this.passwordAgain; }
}
