package crawler.init;

public class CodeTransfer {
	
	
	public String transfer(String rawcode){
		String code="";
		if(rawcode.charAt(0)=='0'){
			code="sz";
			code+=rawcode;
		}else {
			code="sh";
			code+=rawcode;
		}
		return code;
	}
	
	
}
