package businessLogic.userBL;

import blService.userService.StrategyStoreService;
import dataService.userService.UserStrategyWriterService;
import user.UserStrategyWriter;

public class StrategyStore implements StrategyStoreService {

	/**
	 * 
	 * @param buyArr
	 *            "days", "low_price", "high_price", "low_chg", "high_chg",
	 *            "low_volume", "high_volume", "average", "trend"
	 */
	public boolean saveBuyStrategy(String name, double[] buyArr) {
		UserStrategyWriterService writer = new UserStrategyWriter();
		return writer.saveStrategy(name, buyArr, "buy_strategy");
	}

	/**
	 * 
	 * @param sellArr
	 *            "days", "low_price", "high_price", "low_chg", "high_chg",
	 *            "low_volume", "high_volume", "average", "trend"
	 */
	public boolean saveSellStrategy(String name, double[] sellArr) {
		UserStrategyWriterService writer = new UserStrategyWriter();
		return writer.saveStrategy(name, sellArr, "sell_strategy");
	}
}
