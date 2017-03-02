package integrationTest;

import static org.junit.Assert.*;

import org.junit.Test;

import user.SignInCheck;

public class UserTest {

	@Test
	public void test() {
		SignInCheck signIn = new SignInCheck();
		System.out.println(signIn.check("abc", "123456"));
	}

}
