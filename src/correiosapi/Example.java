package correiosapi;

import java.util.HashMap;
import java.util.List;

public class Example {

	public static void main(String[] args) {
		CorreiosAPI correios = new CorreiosAPI("RA035994007CN");
		List<HashMap<String, String>> statusList = correios.getStatusList();
		
		for (HashMap<String, String> status : statusList) {
			System.out.print("Data: " + status.get("date") + "\t");
			System.out.print("Local: " + status.get("location") + "\t");
			System.out.print("Situação: " + status.get("situation") + "\t");
			if(status.get("description") != null)
				System.out.print("Descrição: " + status.get("description"));
			System.out.println();
		}
		
	}
	
}
