package correiosapi;

import java.util.HashMap;
import java.util.List;

public class CorreiosAPI {

	private String packageCode;
	
	public CorreiosAPI(String packageCode) {
		this.packageCode = packageCode;
	}

	public List<HashMap<String, String>> getStatusList() {
		String packageInfoURL = "http://websro.correios.com.br/sro_bin/txect01$.Inexistente?P_LINGUA=001&P_TIPO=002&P_COD_LIS=" + packageCode;
		WebsiteScrapper scrapper = new WebsiteScrapper(packageInfoURL);
		List<HashMap<String, String>> statusList = scrapper.getStatusList();
		
		return statusList;
	}

}
