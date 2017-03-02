package integrationTest;

import static org.junit.Assert.*;

import org.junit.Test;

import user.UserStrategyWriter;

public class UserStrategyWriterTest {

	
	@Test
	public void test() {
		UserStrategyWriter writer = new UserStrategyWriter();
		double[] arr = { 1, 0, 20, -2, 10, 10, 1000000, 5, 5 };
		writer.saveStrategy("abc", arr, "buy_strategy");
	}

}
