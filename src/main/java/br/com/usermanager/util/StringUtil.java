package br.com.usermanager.util;

import java.text.Normalizer;

public class StringUtil {
	
	public static String normalizeAccents(String str)
	{
		return Normalizer.normalize(str, Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
	}

}
