import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Cac {
	private String seed;
	private String polynomial;
	private String plainText;
	private int[] seedArray;
	private int[] polynomialArray;
	private int[] XORresult;
	private int period;
	private byte sum;
	private byte pos;
	private short counter;
	private int[] StreamCipherResult;
	private int[] StreamDeipherResult;
	private int[] plainTextInt;
	private List<Byte> posList;
	private StringBuilder leftSide;
	private StringBuilder rightSide;

	public Cac(String polynomial, String seed, String plainText) throws java.lang.Exception {
		if (seed.length() < polynomial.length())
			throw new Exception();
		leftSide = new StringBuilder();
		rightSide = new StringBuilder();
		setSeed(seed);
		setPolynomial(polynomial);
		setPlainText(plainText);
		seedArray = seed.chars().map(c -> c -= '0').toArray();
		polynomialArray = polynomial.chars().map(c -> c -= '0').toArray();
		plainTextInt = plainText.chars().map(c -> c -= '0').toArray();
		pos = 0;
		posList = new ArrayList<>();
		Arrays.stream(polynomialArray).forEach(e -> {
			if (e == 1)
				posList.add(pos++);
			else
				pos++;
		});

		setPeriod(polynomialArray.length);
		shiftPos();
		cipher();
		decipher();
	}

	public int calculateXor() {
		sum = 0;
		posList.forEach(e -> sum += (byte) seedArray[e]);
		if (sum % 2 == 1)
			return 1;
		else
			return 0;
	}

	public void shiftPos() {
		XORresult = new int[plainText.length()];
		int lastOnePos = 0;
		for (int z = 0; z < polynomialArray.length; z++)
			if (polynomialArray[z] == 1)
				lastOnePos = z;
		counter = 1;
		System.out.print("Starting polynomial: " + polynomial + ", f(x) = 1 + ");
		leftSide.append("Wielomian: " + polynomial + ", f(x) = 1 + ");
		for (int a = 0; a < polynomialArray.length; a++) {
			if (lastOnePos == a) {
				System.out.print("x^" + counter);
				leftSide.append("x^" + counter);
			} else if (polynomialArray[a] != 0) {
				System.out.print("x^" + counter + " + ");
				leftSide.append("x^" + counter + " + ");
			}
			++counter;
		}
		leftSide.append("\n");
		System.out.print("\n");
		System.out.println("Starting seed: " + seed + "\n");
		leftSide.append("Ziarno: " + seed + "\nSlowo: " + plainText + "\n");
		rightSide.append("Generating ...\n");

		byte extra = 0;
		counter = 0;

		for (int i = 0; i < plainText.length(); i++) {
			int[] tempArray = seedArray.clone();
			XORresult[i] = calculateXor();
			extra = (byte) (plainTextInt[i] ^ XORresult[i]);

			for (int j = 0; j < seedArray.length; j++)
				if (j == 0) {
					seedArray[j] = extra;
				} else
					seedArray[j] = tempArray[j - 1];

			System.out.print(++counter + ": ");
			rightSide.append(counter + ": ");
			Arrays.stream(seedArray).forEach(e -> rightSide.append(e));
			Arrays.stream(seedArray).forEach(System.out::print);
			System.out.println();
			rightSide.append("\n");
		}

		System.out.print("XOR Result from LFSR: ");
		Arrays.stream(XORresult).forEach(System.out::print);
		rightSide.append("Wynik xora z LFSR:  ");
		Arrays.stream(XORresult).forEach(e -> rightSide.append(e));

	}

	public void cipher() {
		counter = 0;
		StreamCipherResult = new int[plainText.length()];
		Arrays.stream(StreamCipherResult)
				.forEach(e -> StreamCipherResult[counter] = plainTextInt[counter] ^ XORresult[counter++]);
		System.out.print("\nSynchronous Cipher:   ");
		Arrays.stream(StreamCipherResult).forEach(System.out::print);
		rightSide.append("\nSzyfrowanie:            ");
		Arrays.stream(StreamCipherResult).forEach(e -> rightSide.append(e));

	}

	public void decipher() {
		counter = 0;
		StreamDeipherResult = new int[plainText.length()];
		Arrays.stream(StreamCipherResult)
				.forEach(e -> StreamDeipherResult[counter] = StreamCipherResult[counter] ^ XORresult[counter++]);
		System.out.print("\nSynchronous Decipher: ");
		Arrays.stream(StreamDeipherResult).forEach(System.out::print);
		rightSide.append("\nDeszyfrowanie:        ");
		Arrays.stream(StreamDeipherResult).forEach(e -> rightSide.append(e));
	}

	public int getPeriod() {
		return period;
	}

	public void setPeriod(int polynomialLength) {
		this.period = (int) (Math.pow(2, polynomialLength) - 1);
	}

	public String getSeed() {
		return seed;
	}

	public void setSeed(String seed) {
		this.seed = seed;
	}

	public void setPlainText(String plaintext) {
		this.plainText = plaintext;
	}

	public String getPolynomial() {
		return polynomial;
	}

	public void setPolynomial(String polynomial) {
		this.polynomial = polynomial;
	}

	public StringBuilder getLeftSide() {
		return leftSide;
	}

	public void setLeftSide(StringBuilder leftSide) {
		this.leftSide = leftSide;
	}

	public StringBuilder getRightSide() {
		return rightSide;
	}

	public void setRightSide(StringBuilder rightSide) {
		this.rightSide = rightSide;
	}

}
