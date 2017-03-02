package blService.userService;

public interface StrategyGetterService {

	/**
	 * 
	 * @param name
	 *            user name
	 * @return "days", "low_price", "high_price", "low_chg", "high_chg",
	 *         "low_volume", "high_volume", "average", "trend"
	 */
	public double[] getBuyStrategy(String name);

	/**
	 * 
	 * @param name
	 *            user name
	 * @return "days", "low_price", "high_price", "low_chg", "high_chg",
	 *         "low_volume", "high_volume", "average", "trend"
	 */
	public double[] getsellStrategy(String name);
}
