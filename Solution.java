package canessa.seach.damaged;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import java.util.Random;

/*
 * 
 */
public class Solution {

	/*
	 * Build multimap from long string
	 */
	static Multimap<String, Long> buildMultiMap(String longStr, int patternLen) {
		
		// **** ****
		Multimap<String, Long> multiMap = ArrayListMultimap.create();
		
		// **** get the length of the long string ****
		int len = longStr.length();
		
		// ???? ????
		System.out.println("buildMultiMap <<< len: " + len);
		
		// **** loop once per pattern position ****
		for (long index = 0; index < len; index += patternLen) {

			// **** get the next pattern from the long string ****
			String pattern = longStr.substring((int)index, (int)(index + patternLen));

			// ???? ????
			System.out.println("buildMultiMap <<< pattern ==>" + pattern + "<== index: " + index);
			
			// **** insert in multimap (if needed) ****
			multiMap.put(pattern, index);
		}

		// **** ****
		return multiMap;
	}
	
	/*
	 * Build a pattern string with a specified pattern.
	 */
	static String buildPattern(int len, char c) {
		
		StringBuilder pattern = new StringBuilder();
		
		// **** ****
		while (len-- > 0) {
			pattern.append(c);
		}
		
		// **** ****
		return pattern.toString();
	}
	
	/*
	 * Damage the specified string the specified number of times 
	 * by replacing (not inserting or deleting) characters with
	 * decimal digits.
	 */
	static String damageString(String longStr, int errorCount) throws InterruptedException {
	
		Random rand = new Random(System.currentTimeMillis());
		
		// **** instantiate a string builder (for performance reasons) ****
		StringBuilder sb = new StringBuilder(longStr);
		
		// **** ****
		int strLen = longStr.length();
		
		// ???? ????
		System.out.println("damageString <<< strLen: " + strLen);
		
		// **** loop damaging the long string ****
		for (int i = 0; i < errorCount; i++) {
			
			// **** determine the position to damage in the long string ****
			int pos = rand.nextInt(strLen);
			
			// ???? ????
			System.out.println("damageString <<<    pos: " + pos);
			
			// **** generate a digit ****
			int digit = rand.nextInt(9);
			
			// ???? ????
			System.out.println("damageString <<<  digit: " + digit);
						
			// **** replace the selected character with the generated digit ****
			sb.replace(pos, pos + 1, Integer.toString(digit));
			
			// **** reduce number of duplicate random numbers ****
			Thread.sleep(0);
		}

		// **** ****
		return sb.toString();
	}
	
	/*
	 * Build a long string with a set of patterns of the specified length.
	 */
	static String buildLongString(long len, int patternLen) {
		
		StringBuilder longStr = new StringBuilder();
		
		// ???? ????
		System.out.println("buildLongString <<< len: " + len + " patternLen: " + patternLen);
		
		// **** loop generating the patterns and appending them to the string ****
		for (int i = 0; i < len / patternLen; i++) {
			
			// **** build pattern ****
			char c = (char) ((i % 26) + 'A');
			String pattern = buildPattern(patternLen, c);
			
			// ???? ????
			System.out.println("buildLongString <<< pattern ==>" + pattern + "<==");
			
			// **** append pattern to long string ****
			longStr.append(pattern);
			
			// ???? ????
			System.out.println("buildLongString <<< longStr ==>" + longStr + "<==");
		}

		// **** ****
		return longStr.toString();
	}
	
	/*
	 * 
	 */
	static Multimap<String, Long> damagedPatterns(Multimap<String, Long>multiMap, long strLen, int patternLen) {
		
		// **** remove non-damaged patterns ****
		for (int i = 0; i < 26; i++) {
			
			// **** build pattern ****
			char c = (char) ((i % 26) + 'A');
			String pattern = buildPattern(patternLen, c);
			
			// ???? ????
			System.out.println("buildMultiMap <<< i: " + i + " pattern ==>" + pattern + "<==");

			// **** remove specified pattern ****
			multiMap.removeAll(pattern);
		}
		
		// **** ****
		return multiMap;
	}

	
	/*
	 * Test scaffolding.
	 */
	public static void main(String[] args) throws InterruptedException {
		
		final int 	PATTERN_LEN 	= 8;
		final long 	LONG_STR_LEN	= 2048;
		final int	ERROR_COUNT		= 5;
		
		// **** build long string ****
		String longStr = buildLongString(LONG_STR_LEN, PATTERN_LEN);

		// **** damage the long string ****
		longStr = damageString(longStr, ERROR_COUNT);

		// ???? ????
		int len = longStr.length();
		System.out.println("main <<< len: " + len + " longStr ==>" + longStr + "<==");
		
		// **** build multimap using the long string (includes damaged and non-damaged patterns) ****
		Multimap<String, Long> multiMap = buildMultiMap(longStr, PATTERN_LEN);
		
		// ???? ????
		System.out.println("main <<< multiMap: " + multiMap.toString());
		
		// **** return list of damaged sequences ****
		Multimap<String, Long> badPatterns = damagedPatterns(multiMap, LONG_STR_LEN, PATTERN_LEN);
		
		// **** display the damaged sequences ****
		int size = badPatterns.size();
		System.out.println("main <<<     size: " + size);
		System.out.println("main <<< multiMap: " + badPatterns.toString());
	}
	
}
