package businessLogic.userBL;

import blService.userService.StrategyGetterService;
import dataService.userService.UserStrategyReaderService;
import user.UserStrategyReader;

public class StrategyGetter implements StrategyGetterService {

	/**
	 * 
	 * @param name
	 *            user name
	 * @return "days", "low_price", "high_price", "low_chg", "high_chg",
	 *         "low_volume", "high_volume", "average", "trend"
	 */
	public double[] getBuyStrategy(String name) {
		UserStrategyReaderService reader = new UserStrategyReader();
		return reader.getStrategy(name, "buy_strategy");
	}

	/**
	 * 
	 * @param name
	 *            user name
	 * @return "days", "low_price", "high_price", "low_chg", "high_chg",
	 *         "low_volume", "high_volume", "average", "trend"
	 */
	public double[] getsellStrategy(String name) {
		UserStrategyReaderService reader = new UserStrategyReader();
		return reader.getStrategy(name, "sell_strategy");
	}
}
