package businessLogic.userBL;

import blService.userExceptionBL.EmptyNameException;
import blService.userExceptionBL.EmptyPasswordException;
import blService.userExceptionBL.RegisterException;
import blService.userExceptionBL.UserDuplException;
import blService.userService.RegisterService;
import dataService.userService.RegisterCheckService;
import user.RegisterCheck;

public class Register implements RegisterService {

	public boolean register(String name, String password) throws RegisterException {
		if (name == null || name.equals(""))
			// name not set
			throw new EmptyNameException();
		else if (password == null || password.equals(""))
			// password not set
			throw new EmptyPasswordException();
		else {
			RegisterCheckService register = new RegisterCheck();
			int check = register.check(name, password);
			switch (check) {
			case 0:
				return true;
			case 1:
				// duplication
				throw new UserDuplException();
			default:
				throw new RegisterException();
			}
		}
	}
}
