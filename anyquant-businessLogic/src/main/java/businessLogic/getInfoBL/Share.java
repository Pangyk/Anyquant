package businessLogic.getInfoBL;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Map.Entry;

import blService.getInfoBLService.GetShareBLService;
import businessLogic.statisticsBL.ATRMark;
import businessLogic.statisticsBL.AverageLine;
import businessLogic.statisticsBL.PBratio;
import businessLogic.statisticsBL.PEratio;
import data.getShare.ShareGetter;
import dataService.getShareDataService.GetShareService;
import dataService.sqlService.SQLUtilityService;
import dto.ShareDTO;
import inteService.apiInteService.ApiInteShareService;
import integration.apiInte.ShareInte;
import sql.AdvancedUtil;
import vo.ATRVO;
import vo.AverageVO;
import vo.FullShareVO;
import vo.ShareVO;
import vo.SimpilifiedShareVO;

public class Share implements GetShareBLService {

	/**
	 * 
	 * @param strategy
	 *            搴斿鍙樻洿
	 */
	public Share(String strategy) {

	}

	/**
	 * default strategy:open+close
	 */
	public Share() {

	}

	/**
	 * 鍒濆鍖栬偂绁ㄤ俊鎭�
	 * 
	 * @param strategy
	 * @param exchange
	 * @return 鎵�鏈夎偂绁ㄦ渶杩戜竴澶╃殑淇℃伅鍒楄〃
	 */
	private ArrayList<ShareVO> getOverViewList(String strategy, String exchange) {
		GetShareService service1 = new ShareGetter("sh");
		GetShareService service2 = new ShareGetter("sz");

		ShareInte shareInte = new ShareInte();

		// 浣跨敤榛樿鏃跺尯鍜岃瑷�鐜鑾峰緱涓�涓棩鍘�
		Calendar cal = Calendar.getInstance();
		// 瀹氫箟鏍煎紡
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		// 鍙栧綋鍓嶆棩鏈熺殑鍓�5澶�
		cal.add(Calendar.DAY_OF_MONTH, -3);
		String startDate = format.format(cal.getTime());
		// 鍙栧綋鍓嶆棩鏈熺殑鍚庝竴澶�
		cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, +1);
		String endDate = format.format(cal.getTime());

		ArrayList<ShareVO> list = new ArrayList<ShareVO>();
		String[] idList = { "sz002024", "sh601398", "sh601166", "sh601857", "sz002458", "sz002256", "sz002192",
				"sz300319", "sz002218", "sh600005" };

		for (int i = 0; i < idList.length; i++) {
			ArrayList<ShareDTO> shareInfo = shareInte.getShareDetail(idList[i], startDate, endDate, strategy);

			if (shareInfo != null && !shareInfo.isEmpty()) {
				ShareDTO temp = shareInfo.get(shareInfo.size() - 1);
				ShareVO share = transDTOToVO(temp);
				String name = service1.getShareName(idList[i]);
				if (name == null) {
					name = service2.getShareName(idList[i]);
				}
				share.setName(name);
				list.add(share);
			}
		}
		if (list.isEmpty())
			return null;
		else
			return list;
	}

	@Override
	public String getShareName(String id) {
		if (id == null || id.length() < 2)
			return "";
		String exchange = id.substring(0, 2);
		if (exchange.equals("sh") || exchange.equals("sz")) {
			GetShareService share = new ShareGetter(exchange);
			String name = share.getShareName(id);
			if (name == null)
				return "";
			else
				return name;
		} else {
			return "";
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public ListIterator<String> getNameList(String input) {
		String exchange = "";

		// 纭浜ゆ槗鎵�
		if (input.startsWith("sh"))
			exchange = "sh";
		else if (input.startsWith("sz"))
			exchange = "sz";
		else if (input.startsWith("s"))
			exchange = "sh";

		// 鑾峰緱鑲＄エ鍒楄〃
		Iterator map;
		GetShareService service = new ShareGetter(exchange);
		if (exchange.equals("sz")) {
			map = service.getSZShare();
		} else {
			map = service.getSHShare();
		}

		ArrayList<String> list = new ArrayList<String>();
		ListIterator<String> nameList = list.listIterator();

		if (map == null)
			return nameList;
		while (map.hasNext()) {
			Entry entry = (Entry) map.next();
			String id = (String) entry.getKey();
			id = exchange + id;
			if (id.startsWith(input))
				nameList.add(id);
		}
		while (nameList.hasPrevious()) {
			nameList.previous();
		}
		return nameList;
	}

	@Override
	public ArrayList<ShareVO> getInfoList(String exchange) {
		return getOverViewList(null, null);
	}

	@Override
	public ArrayList<ShareVO> getShareDetail(String id, String start, String end, String strategy) {
		// 缁撴潫鏃ユ湡鎺ㄥ悗涓�澶�
		Calendar c = Calendar.getInstance();
		String[] sp = end.split("-");
		c.set(Integer.parseInt(sp[0]), Integer.parseInt(sp[1]) - 1, Integer.parseInt(sp[2]));
		c.add(Calendar.DAY_OF_MONTH, +1);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		end = format.format(c.getTime());

		ApiInteShareService shareInte = new ShareInte();
		ArrayList<ShareDTO> dtoList = shareInte.getShareDetail(id, start, end, strategy);
		return common(dtoList);
	}

	@Override
	public ArrayList<ShareVO> getSpecifiedInfo(String id, String strategy) {
		ApiInteShareService shareInte = new ShareInte();
		ArrayList<ShareDTO> dtoList = shareInte.getShareDetail(id, strategy);
		return common(dtoList);
	}

	public ArrayList<ShareVO> common(ArrayList<ShareDTO> dtoList) {
		if (dtoList == null)
			return null;

		ArrayList<ShareVO> list = new ArrayList<ShareVO>();
		for (ShareDTO dto : dtoList) {
			ShareVO share = transDTOToVO(dto);
			list.add(share);
		}

		// 璁板綍鍒板巻鍙茬邯褰�
		// recordHistory(id);
		if (list.isEmpty())
			return null;
		else
			return list;
	}

	@Override
	public void recordHistory(String name) {

	}

	private ShareVO transDTOToVO(ShareDTO dto) {
		if (dto == null)
			return null;
		ShareVO share = new ShareVO(dto.getID(), dto.getOpen(), dto.getClose(), dto.getDate(), dto.getHigh(),
				dto.getLow(), dto.getVolume(), dto.getTurnover(), dto.getAdj_price(), dto.getPb(), dto.getPe());
		share.setName(dto.getName());
		return share;
	}

	@Override
	public ArrayList<SimpilifiedShareVO> getAllShareToday() {
		SQLUtilityService shareInte = new AdvancedUtil();
		ArrayList<ShareDTO> dtoList = shareInte.getAllShareInOneDay();
		ArrayList<SimpilifiedShareVO> list = new ArrayList<SimpilifiedShareVO>();
		double rate = 0;
		for (ShareDTO dto : dtoList) {
			rate = shareInte.getRate(dto.getID());
			SimpilifiedShareVO share = new SimpilifiedShareVO(dto.getID(), dto.getName(), dto.getClose(), rate);
			share.setName(dto.getName());
			list.add(share);
		}

		return list;
	}

	public FullShareVO getOneShareToday(String id) {
		SQLUtilityService shareInte = new AdvancedUtil();
		ShareDTO dto = shareInte.getLastDay(id);

		FullShareVO share = new FullShareVO(dto.getID(), dto.getOpen(), dto.getClose(), dto.getDate(), dto.getHigh(),
				dto.getLow(), dto.getVolume(), dto.getTurnover(), dto.getAdj_price(), dto.getPb(), dto.getPe());

		share.setName(dto.getName());

		PBratio pb = new PBratio();
		share.setPb(pb.getPB(id));

		PEratio pe = new PEratio();
		share.setPe(pe.getPE(id));

		share.setRate(shareInte.getRate(dto.getID()));
		share.setSum(dto.getClose() * dto.getVolume());

		ATRMark atr = new ATRMark();
		ArrayList<ATRVO> list = atr.getATRMark(7, dto.getDate());
		if (list != null && !list.isEmpty())
			share.setBias(list.get(0).getValue());

		return share;
	}

	@Override
	public ArrayList<SimpilifiedShareVO> find(String text) {
		String benchmark = "sh000300";
		SQLUtilityService shareInte = new AdvancedUtil();
		ArrayList<SimpilifiedShareVO> list = new ArrayList<SimpilifiedShareVO>();

		if (benchmark.contains(text)) {
			ShareDTO dto = shareInte.getLastDay(benchmark);
			double rate = shareInte.getRate(benchmark);
			SimpilifiedShareVO share = new SimpilifiedShareVO(dto.getID(), dto.getName(), dto.getClose(), rate);
			list.add(share);
		}

		ArrayList<ShareDTO> dtoList = shareInte.find(text);

		if (dtoList != null && !dtoList.isEmpty()) {
			double rate = 0;
			for (ShareDTO dto : dtoList) {
				rate = shareInte.getRate(dto.getID());
				SimpilifiedShareVO share = new SimpilifiedShareVO(dto.getID(), dto.getName(), dto.getClose(), rate);
				share.setName(dto.getName());
				list.add(share);
			}
		}

		if (!list.isEmpty())
			return list;
		return null;
	}

	public String analysis(String id) {
		String conclusion = "浠庝粖鏃ユ寚鏍囩湅锛�";
		FullShareVO shareinfo = getOneShareToday(id);
		double pe = shareinfo.getPe();
		if (pe < 0)
			conclusion += "璇ュ叕鍙镐簭鎹�;";
		else if (pe >= 0 && pe < 14)
			conclusion += "鑲＄エ浠峰�艰浣庝及;";
		else if (pe >= 14 && pe < 21)
			conclusion += "鑲＄エ浠峰�兼甯�;";
		else if (pe >= 21 && pe < 28)
			conclusion += "鑲＄エ浠峰�艰楂樹及;";
		else if (pe >= 28)
			conclusion += "鑲″競鍑虹幇鎶曟満鎬ф场娌�;";

		double pb = shareinfo.getPb();
		if (pb <= 6) {
			conclusion += "甯傚噣鐜囪緝浣庯紝鑲＄エ鏈夋綔鍔�;";
		} else if (pb > 6 && pb < 12) {
			conclusion += "甯傚噣鐜囨甯革紝寤鸿灏戦噺涔板叆鎴栬鏈�;";
		} else if (pb > 12) {
			conclusion += "甯傚噣鐜囪緝楂橈紝寤鸿绂诲満;";
		}

		double high = shareinfo.getHigh();
		double low = shareinfo.getLow();
		double close = shareinfo.getClose();
		double open = shareinfo.getOpen();

		double atr = shareinfo.getBias();
		if (atr > close) {
			conclusion += "鍧囩嚎浣嶄簬浠锋牸涔嬩笂锛岀湅璺岋紱";
		} else {
			conclusion += "鍧囩嚎浣嶄簬浠锋牸涔嬩笅锛岀湅娑紱";
		}

		if (high == close && low == open) {// 鍏夊ご鍏夎剼闃崇嚎
			conclusion += "鏋佺寮哄娍涓婃定锛屽悗甯傜湅澶氾紱";
		} else if (high == open && low == close) {// 鍏夊ご鍏夎剼闃寸嚎
			conclusion += "鏋佺寮哄娍涓嬭穼锛屽悗甯傜湅绌猴紱";
		} else if (high > close && low < open && close > open) {// 澶ч槼绾�
			conclusion += "寮哄娍涓婃定锛屽悗甯傜湅澶氾紱";
		} else if (high > open && low < close && close < open) {// 澶ч槾绾�
			conclusion += "寮哄娍涓嬭穼锛屽悗甯傜湅绌猴紱";
		}

		else if (high == close && low < open && close > open) {// 鍏夊ご闃崇嚎
			// if((open-low)<(close-open))
			conclusion += "杈冨己鍔夸笂娑紝绌烘柟寮�濮嬪弽鍑讳簡锛岄渶瑕佹敞鎰忥紱";
			// else
			// conclusion+="鏇鹃亣鍒拌繃鍓х儓鍙嶅嚮锛屽悗甯傛湁鍙橈紱";
		} else if (high == open && low < close && close < open) {// 鍏夊ご闃寸嚎
			// if((close-low)<(open-close))
			conclusion += "杈冨己鍔夸笅璺岋紝澶氭柟寮�濮嬪弽鍑讳簡锛岄渶瑕佹敞鎰忥紱";
			// else
			// conclusion+="鏇鹃亣鍒拌繃鍓х儓鍙嶅嚮锛屽悗甯傛湁鍙橈紱";
		} else if (high > close && low == open && close > open) {// 鍏夎剼闃崇嚎
			// if(high-close<close-open)
			conclusion += "杈冨己鍔夸笂娑紝閬囧埌绌烘柟鍙嶅嚮浜嗭紝闇�瑕佹敞鎰忥紱";
			// else
			// conclusion+="鐩告瘮杩囧幓锛屾懜楂樺彈闃伙紝鍚庡競鏈夊彉锛�";
		} else if (high > open && low == close && close < open) {// 鍏夎剼闃寸嚎
			// if(high-open<open-close)
			conclusion += "杈冨己鍔夸笅璺岋紝閬囧埌澶氭柟鍙嶅嚮浜嗭紝闇�瑕佹敞鎰忥紱";
			// else
			// conclusion+="鐩告瘮杩囧幓锛屾浘缁忓ぇ娑紝鍚庡競鏈夊彉锛�";
		}
		if (low == close) {
			conclusion += "璇ヨ偂鍦ㄥ熬甯傚彈鍒扮殑鎵撳帇寰堝ぇ锛屼笅涓�浜ゆ槗鏃ュ彲鑳戒細杩涗竴姝ヤ笅鎺紝" + "瀵逛簬宸茬粡鏈変竴瀹氳幏鍒╃Н绱殑鐭嚎鎿嶇洏鑰呭簲璇ュ敖蹇嚭灞�锛�";
		} else if (high == close) {
			conclusion += "褰撳ぉ鑲＄エ涓�璺笂娑紝鏈�缁堟敹浜庢渶楂樹环锛岃〃鏄庤鑲″浜庣洏涓帹鍔ㄤ笂娑ㄧ殑褰㈠娍锛�" + "涓嬩竴浜ゆ槗鏃ュ緢鍙兘缁х画鍚戜笂鏀�鐖紝杩涜�屽嚭鐜版洿楂樼殑浠锋牸锛�"
					+ "瀵逛簬鐭嚎鎿嶇洏鑰呮潵璇达紝鍦ㄧ浜屽ぉ鏃╃洏鍑虹幇楂樹环鏃讹紝鍙互鑾峰埄鍑哄眬锛�";
		}
		if (conclusion.charAt(conclusion.length() - 1) == 'a') {
			conclusion = conclusion.substring(0, conclusion.length() - 1);
			conclusion += "銆�";
		}
		// conclusion += lineanalyse(id);
		return conclusion;
	}

	public String lineanalyse(String id) {
		String result = "";
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String enddate = format.format(cal.getTime());
		ArrayList<ShareVO> list = getShareDetail(id, "2015-01-01", enddate, "open+close+high+low+volume");
		ShareVO today = list.get(list.size() - 1);
		ShareVO yesterday = list.get(list.size() - 2);
		// System.out.println(yesterday.getOpen());
		// System.out.println(yesterday.getClose());
		// System.out.println(yesterday.getHigh());
		// System.out.println(yesterday.getLow());
		// System.out.println(today.getOpen());
		// System.out.println(today.getClose());
		// System.out.println(today.getHigh());
		// System.out.println(today.getLow());
		if (yesterday.getOpen() < yesterday.getClose()) {
			if (yesterday.getHigh() < today.getOpen()) {
				result += "寮�鐩樹环鏍奸珮浜庢槰鏃ユ渶楂樹环锛岃〃鏄庝拱鏂圭殑瀹炲姏寰堝己锛岃嫢姝ゆ椂鑲′环宸茬粡缁忓巻浜嗕竴涓紦鎱㈢殑涓婂崌杩囩▼锛屽彲浠ュぇ鑳嗕粙鍏ワ紱";
			}
			if (yesterday.getHigh() >= today.getOpen() && yesterday.getClose() <= today.getOpen()) {
				result += "寮�鐩樹环鍦ㄦ槰鏃ユ敹鐩樹环鍜屾渶楂樹环涔嬮棿锛屼拱瀹剁殑瀹炲姏鏈夋墍鍑忓急锛�" + "搴旇瀵熷崠瀹剁殑瀹炲姏鍐嶅仛涓嬩竴姝ョ殑绛栫暐锛�";
			}
			if (yesterday.getClose() >= today.getOpen() && yesterday.getOpen() <= today.getOpen()) {
				result += "寮�鐩樹环浣庡紑锛屼絾浣嶄簬鍓嶄竴浜ゆ槗鏃ョ殑瀹炰綋K绾夸箣涓紝琛ㄧず涔板鐨勫疄鍔涘凡缁忔瘮杈冨急浜嗭紝" + "鍗栨柟闅忔椂鍙兘鍙嶅嚮锛屾鏃惰瀵嗗垏鍏虫敞鑲″競鐨勫姩鍚戯紝涓�鏃︽湁浠讳綍椋庡惞鑽夊姩灏卞彲鑳藉嚭鐜板彉鐩橈紱";
			}
			if (yesterday.getLow() > today.getOpen()) {
				result += "寮�鐩樹环浣嶄簬鏄ㄦ棩鏈�浣庝环浠ヤ笅锛岃〃鏄庡崠瀹剁殑瀹炲姏閫愭笎鍔犲己锛屽苟寮�濮嬪弽鏀伙紝" + "鍚屾椂涔板鐨勫疄鍔涜〃鐜板緱寰堝急灏忥紝姝ゆ椂搴旇浆鍚戠湅绌猴紝骞剁户缁鏈涳紱";
			}
		}

		if (yesterday.getOpen() > yesterday.getClose()) {
			if (yesterday.getHigh() < today.getOpen()) {
				result += "寮�鐩樹环楂樹簬鏄ㄦ棩鐨勬渶楂樹环锛岃鏄庤偂甯傛槸鍦ㄧ壒鍒潯浠朵笅鍙戠敓浜嗛噸澶х殑鍒╁ソ锛�" + "鑰屼笖鍦ㄦ鍒╁ソ鐨勫埡婵�涓嬶紝鍚庡娍澧為暱杩樻湁寰堝ぇ鐨勭┖闂达紱";
			}
			if (yesterday.getHigh() >= today.getOpen() && yesterday.getClose() <= today.getOpen()) {
				result += "寮�鐩樹环鍦ㄦ槰鏃ユ敹鐩樹环鍜屾渶楂樹环涔嬮棿锛�" + "杩欐椂鍗栨柟鎶涘帇鐨勫ぇ灏忓喅瀹氱潃鑲′环涓嬭皟杩囩▼涓殑鍚庡競鎿嶄綔杩囩▼锛�" + "濡傛灉鎶涘帇澶噸锛屽垯璋冩暣鏈熷皢浼氭瘮杈冩极闀匡紝涔嬪悗璧板娍杩樻湁寰呰瀵燂紱";
			}
			if (yesterday.getClose() <= today.getOpen() && yesterday.getOpen() >= today.getOpen()) {
				result += "寮�鐩樹环浣嶄簬鏄ㄦ棩K绾垮疄浣撲腑锛岃鏄庝拱鍗栧弻鏂圭殑瀹炲姏渚濈劧鏈彉锛�";
			}
			if (yesterday.getClose() >= today.getClose() && yesterday.getLow() <= today.getClose()) {
				result += "鏀剁洏浠峰湪鏄ㄦ棩鏀剁洏涓庢渶浣庝环涔嬮棿锛岃〃鏄庡崠鏂瑰疄鍔涘己鍔诧紝鑰屼拱鏂瑰疄鍔涘緢寮憋紝" + "甯傚満缁х画璧颁綆鐨勫彲鑳芥�ч潪甯稿ぇ锛�";
			}
			if (yesterday.getLow() > today.getOpen()) {
				result += "寮�鐩樹环浣庝簬鏄ㄦ棩鐨勬渶浣庝环锛岃〃鏄庤繖涓競鍦虹┖鏂瑰凡缁忎富瀹颁簡甯傚満鐨勮鎯咃紝鑲″競灏嗗姞閫熶笅璺岋紱";
			}
		}

		String s = "";

		boolean above = true;
		boolean state2 = true;
		boolean state3 = true;
		boolean increase = true;
		boolean rise = true;

		AverageLine ave = new AverageLine();
		ArrayList<AverageVO> average = ave.getAverageLine(id, 7, enddate, 5);
		ArrayList<AverageVO> average10 = ave.getAverageLine(id, 7, enddate, 10);
		for (int i = average.size() - 1; i >= 0; i--) {
			// System.out.println(average.get(i).getDate());
			// System.out.println(list.get(list.size()-i-1).getDate());
			// if (average.get(i).getDate().equals(list.get(i).getDate())) {
			if (list.get(list.size() - i - 1).getClose() < average.get(i).getValue()) {
				state2 = false;
				if (above) {
					above = false;
					s += list.get(list.size() - i - 1).getDate() + "鑲′环璺岀牬绉诲姩骞冲潎绾匡紝鏄崠鍑轰俊鍙凤紱";
				}

				if (state3) {
					if (i > 0)
						if (list.get(list.size() - i - 1).getClose() > list.get(list.size() - i).getClose()) {
							state3 = false;
						}
				}

			} else {
				state3 = false;
				if (state2) {
					if (!increase) {
						if (i > 0)
							if (list.get(list.size() - i - 1).getClose() < list.get(list.size() - i).getClose()) {
								s += list.get(list.size() - i - 1).getDate() + "鑲′环浣嶄簬绉诲姩骞冲潎绾夸箣涓婅繍琛岋紝鍥炴。鏃舵湭璺岀牬绉诲姩骞冲潎绾垮悗鍙堝啀搴︿笂鍗囨椂涓轰拱杩涙椂鏈猴紱";
								increase = true;
							}
					}
					if (i > 0)
						if (list.get(list.size() - i - 1).getClose() > list.get(list.size() - i).getClose()) {
							increase = false;
						}
				}
				if (i > 0)
					if (list.get(list.size() - i - 1).getClose() > list.get(list.size() - i).getClose()) {
						rise = false;
					}
				if (!above) {
					above = true;
					s += list.get(list.size() - i - 1).getDate() + "鑲′环绐佺牬绉诲姩骞冲潎绾匡紝鏄拱鍏ヤ俊鍙凤紱";
				}

			}

			// }

		}
		if (state3) {
			s += "鑲′环浣嶄簬绉诲姩骞冲潎绾夸互涓嬭繍琛岋紝绐佺劧鏆磋穼锛岃窛绂荤Щ鍔ㄥ钩鍧囩嚎澶繙锛�" + "鏋佹湁鍙兘鍚戠Щ鍔ㄥ钩鍧囩嚎闈犺繎锛堢墿鏋佸繀鍙嶏紝涓嬭穼鍙嶅脊锛夛紝姝ゆ椂涓轰拱杩涙椂鏈猴紱";
		}
		if (state2 && rise) {
			s += "鑲′环浣嶄簬绉诲姩骞冲潎绾夸箣涓婅繍琛岋紝杩炵画鏁版棩澶ф定锛岀绉诲姩骞冲潎绾挎剤鏉ユ剤杩滐紝" + "璇存槑杩戞湡鍐呰喘涔拌偂绁ㄨ�呰幏鍒╀赴鍘氾紝闅忔椂閮戒細浜х敓鑾峰埄鍥炲悙鐨勫崠鍘嬶紝搴旀殏鏃跺崠鍑烘寔鑲★紱";
		}

		for (int i = average.size() - 1; i >= 0; i--) {
			if (average.get(i).getValue() == average10.get(i).getValue()) {
				if (i > 0 && i < average.size() - 1) {
					if ((average.get(i + 1).getValue() < average10.get(i + 1).getValue())
							&& (average.get(i - 1).getValue() > average10.get(i - 1).getValue())) {
						s += average.get(i).getDate() + "榛勯噾浜ゅ弶锛屽悗甯備笂娑ㄧ殑鍙兘鎬у緢澶э紝鏄拱鍏ユ椂鏈猴紱";
					}
					if ((average.get(i + 1).getValue() > average10.get(i + 1).getValue())
							&& (average.get(i - 1).getValue() < average10.get(i - 1).getValue())) {
						s += average.get(i).getDate() + "姝讳骸浜ゅ弶锛岄绀鸿偂浠峰皢涓嬭穼锛屾鏃跺簲鍗栧嚭鎸佹湁鐨勮偂绁紝绂诲満瑙傛湜锛�";
					}
				}
			}
		}
		result += s;
		if (result.charAt(result.length() - 1) == 'a') {
			result = result.substring(0, result.length() - 1);
			result += "銆�";
		}
		return result;
	}
}
