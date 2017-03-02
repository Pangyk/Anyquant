package blService.userService;

import blService.userExceptionBL.SignInException;
import vo.UserInfoVO;

public interface SignInService {

	public UserInfoVO signIn(String name, String password) throws SignInException;
}
