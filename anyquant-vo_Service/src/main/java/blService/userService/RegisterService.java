package blService.userService;

import blService.userExceptionBL.RegisterException;

public interface RegisterService {

	public boolean register(String name, String password) throws RegisterException;
}
