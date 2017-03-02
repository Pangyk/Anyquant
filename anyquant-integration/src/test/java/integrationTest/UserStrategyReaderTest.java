package integrationTest;

import static org.junit.Assert.*;

import org.junit.Test;

import user.UserStrategyReader;

public class UserStrategyReaderTest {

	@Test
	public void test() {
		UserStrategyReader reader = new UserStrategyReader();
		double[] arr = reader.getStrategy("abc", "buy_strategy");
		for (int i = 0; i < arr.length; i++) {
			System.out.println(arr[i]);
		}
	}

}
