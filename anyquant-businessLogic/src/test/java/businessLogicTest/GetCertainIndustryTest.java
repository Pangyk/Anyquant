package businessLogicTest;

import static org.junit.Assert.*;

import java.util.ArrayList;
import org.junit.Test;

import blService.IndustriesBLService.GetCertainIndustryInfoBLService;
import businessLogic.industriesBL.GetCertainIndustryInfo;
import vo.ShareVO;
import vo.SpecificIndustryVO;

public class GetCertainIndustryTest {
	@Test
	public void test() {
		GetCertainIndustryInfoBLService service =new GetCertainIndustryInfo();
		SpecificIndustryVO vo=service.getCertainIndustryInfo("酒店及餐饮");
		ArrayList<ShareVO> voList = vo.getInfoList();
		for (ShareVO shareVO : voList) {
			System.out.println(shareVO.getName()+" "+shareVO.getID()+" open:"+shareVO.getOpen()+"close:"+shareVO.getClose()
			+"high:"+shareVO.getHigh()+"low:"+shareVO.getLow()+"volume:"+shareVO.getVolume());
		}
	}

}
