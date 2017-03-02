package integration.apiInte;

import java.util.ArrayList;

import dto.ShareDTO;
import inteService.apiInteService.ApiInteBenchmarkService;
import sql.DBUtility;

public class BenchmarkInte implements ApiInteBenchmarkService{
	
	public ArrayList<ShareDTO> getBenchmark(String start, String end) {
		String url = "http://114.55.37.133:8010/api/benchmark/hs300/";
		Common common = new Common();
		return common.getList("sh000300", start, end, url);
	}

	@Override
	public ArrayList<ShareDTO> getBenchmark(String strategy,String start, String end) {
		DBUtility dbUtility = new DBUtility();
		return dbUtility.getData("sh000300", start, end);
	}
}
