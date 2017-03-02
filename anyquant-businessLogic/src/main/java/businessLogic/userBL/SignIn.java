package businessLogic.userBL;

import blService.userExceptionBL.PasswordException;
import blService.userExceptionBL.SignInException;
import blService.userExceptionBL.UserNotExistException;
import blService.userService.SignInService;
import dataService.userService.SignInCheckService;
import user.SignInCheck;
import vo.UserInfoVO;

public class SignIn implements SignInService {

	public UserInfoVO signIn(String name, String password) throws SignInException {
		SignInCheckService signIn = new SignInCheck();
		int check = signIn.check(name, password);
		switch (check) {
		case 0:
			return new UserInfoVO(name, password);
		case 1:
			// no username found
			throw new UserNotExistException();
		case 2:
			// incorrect password
			throw new PasswordException();
		default:
			throw new SignInException();
		}
	}
}
