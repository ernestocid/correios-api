package correiosapi.tests;


import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;
import correiosapi.CorreiosAPI;


public class CorreiosTest {
	
	@Test
	public void shouldGetStatusList() {
		CorreiosAPI api = new CorreiosAPI("RA035994007CN");
		List<HashMap<String, String>> status = api.getStatusList();
		
		List<HashMap<String, String>> expected = new ArrayList<HashMap<String,String>>();

		HashMap<String, String> status2 = new HashMap<String, String>();
		status2.put("date", "26/04/2011 09:35");
		status2.put("location", "CHINA - CHINA");
		status2.put("description", "Em tr‰nsito para UNIDADE DE TRATAMENTO INTERNACIONAL - BRASIL");
		status2.put("situation", "Encaminhado");
		expected.add(status2);
		
		HashMap<String, String> status1 = new HashMap<String, String>();
		status1.put("date", "23/04/2011 11:54");
		status1.put("location", "CHINA - CHINA");
		status1.put("situation", "Postado");
		expected.add(status1);
		
		assertEquals(expected, status);
	}

}
