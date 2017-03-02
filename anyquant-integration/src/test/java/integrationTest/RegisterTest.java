package integrationTest;

import static org.junit.Assert.*;

import org.junit.Test;

import user.RegisterCheck;

public class RegisterTest {

	@Test
	public void test() {
		RegisterCheck register = new RegisterCheck();
		System.out.println(register.check("abc", "123456"));
	}

}
