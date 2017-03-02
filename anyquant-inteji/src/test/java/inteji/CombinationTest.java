package inteji;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import blSerivce.predictSerivce.CombinationService;
import combination.Combination;
import vo.SimpilifiedShareVO;

public class CombinationTest {

	@Test
	public void test() {
		double[] s = { 0, 0, 1000000, 200000000, -5, -10, -10, 1 };
		CombinationService service = new Combination();
		ArrayList<SimpilifiedShareVO> list = service.selectShare(s);
		
		for(SimpilifiedShareVO share:list){
			System.out.println(list.size());
		}
	}

}
