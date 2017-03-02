package blService.userService;

public interface StrategyStoreService {

	/**
	 * 
	 * @param buyArr
	 *            "days", "low_price", "high_price", "low_chg", "high_chg",
	 *            "low_volume", "high_volume", "average", "trend"
	 */
	public boolean saveBuyStrategy(String name, double[] buyArr);

	/**
	 * 
	 * @param sellArr
	 *            "days", "low_price", "high_price", "low_chg", "high_chg",
	 *            "low_volume", "high_volume", "average", "trend"
	 */
	public boolean saveSellStrategy(String name, double[] sellArr);
}
