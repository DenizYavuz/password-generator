import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class CryptoUtils {

	public byte[] sha512(String input) {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA-512");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		byte[] inputBytes = input.getBytes();
		byte[] hashBytes = md.digest(inputBytes);

		return hashBytes;
	}

	// A-Z, a-z, 0-9
	public String encodeBase64(byte[] data) {
		char[] tbl = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S',
				'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
				'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8',
				'9', '0', '0' };

		StringBuilder buffer = new StringBuilder();
		int pad = 0;
		for (int i = 0; i < data.length; i += 3) {

			int b = ((data[i] & 0xFF) << 16) & 0xFFFFFF;
			if (i + 1 < data.length) {
				b |= (data[i + 1] & 0xFF) << 8;
			} else {
				pad++;
			}
			if (i + 2 < data.length) {
				b |= (data[i + 2] & 0xFF);
			} else {
				pad++;
			}

			for (int j = 0; j < 4 - pad; j++) {
				int c = (b & 0xFC0000) >> 18;
				buffer.append(tbl[c]);
				b <<= 6;
			}
		}

		return buffer.toString();
	}

	// A-Z, a-z, 0-9, ., -, :, +, =, ^, !, /, *, ?, &, <, >, (, ), [, ], {, },
	// @, %, $, #
	public String encodeASCII85(byte[] b) {
		int off = 0;
		int len = b.length;

		long i = 0;
		StringBuffer s = new StringBuffer();
		// s.append("<~");
		while (len >= 4) {
			i = ((b[off] & 0xFFL) << 24L) | ((b[off + 1] & 0xFFL) << 16L) | ((b[off + 2] & 0xFFL) << 8L)
					| (b[off + 3] & 0xFFL);
			if (i == 0) {
				s.append('z');
			} else {
				s.append((char) ('!' + ((i / 52200625) % 85)));
				s.append((char) ('!' + ((i / 614125) % 85)));
				s.append((char) ('!' + ((i / 7225) % 85)));
				s.append((char) ('!' + ((i / 85) % 85)));
				s.append((char) ('!' + (i % 85)));
			}
			off += 4;
			len -= 4;
		}
		switch (len) {
		case 3:
			i = ((b[off] & 0xFFL) << 24L) | ((b[off + 1] & 0xFFL) << 16L) | ((b[off + 2] & 0xFFL) << 8L);
			s.append((char) ('!' + ((i / 52200625) % 85)));
			s.append((char) ('!' + ((i / 614125) % 85)));
			s.append((char) ('!' + ((i / 7225) % 85)));
			s.append((char) ('!' + ((i / 85) % 85)));
			break;
		case 2:
			i = ((b[off] & 0xFFL) << 24L) | ((b[off + 1] & 0xFFL) << 16L);
			s.append((char) ('!' + ((i / 52200625) % 85)));
			s.append((char) ('!' + ((i / 614125) % 85)));
			s.append((char) ('!' + ((i / 7225) % 85)));
			break;
		case 1:
			i = ((b[off] & 0xFFL) << 24L);
			s.append((char) ('!' + ((i / 52200625) % 85)));
			s.append((char) ('!' + ((i / 614125) % 85)));
			break;
		}
		// s.append("~>");
		return s.toString();
	}

	// 0-9
	public String toNumbers(byte[] inputBytes) {
		StringBuffer hashString = new StringBuffer();
		for (int i = 0; i < inputBytes.length; i++) {
			int number = (inputBytes[i] + 128) % 10;
			hashString.append(number);
		}
		return hashString.toString();
	}
}