import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Lfsr {
	private String seed;
	private String polynomial;
	private int[] seedArray;
	private int[] polynomialArray;
	private int period;
	private byte sum;
	private byte pos;
	private List<Byte> posList;
	private StringBuilder leftSide;
	private StringBuilder rightSide;

	public Lfsr(String polynomial, String seed) throws java.lang.Exception {
		if (seed.length() < polynomial.length())
			throw new Exception();
		leftSide = new StringBuilder();
		rightSide = new StringBuilder();
		setSeed(seed);
		setPolynomial(polynomial);
		seedArray = seed.chars().map(c -> c -= '0').toArray();
		polynomialArray = polynomial.chars().map(c -> c -= '0').toArray();
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
		int counter = 0;
		int lastOnePos = 0;
		for (int z = 0; z < polynomialArray.length; z++)
			if (polynomialArray[z] == 1)
				lastOnePos = z;
		int counter2 = 1;
		System.out.print("Starting polynomial: " + polynomial + ", f(x) = 1 + ");
		leftSide.append("Wielomian: " + polynomial + ", f(x) = 1 + ");
		for (int a = 0; a < polynomialArray.length; a++) {
			if (lastOnePos == a) {
				System.out.print("x^" + counter2);
				leftSide.append("x^" + counter2);
			} else if (polynomialArray[a] != 0) {
				System.out.print("x^" + counter2 + " + ");
				leftSide.append("x^" + counter2 + " + ");
			}
			++counter2;
		}
		leftSide.append("\n");
		System.out.print("\n");
		System.out.println("Starting seed: " + seed + "\n");
		leftSide.append("Ziarno: " + seed + "\n");
		rightSide.append("Generating ...\n");
		for (int i = 0; i < period; i++) {
			int[] tempArray = seedArray.clone();
			for (int j = 0; j < seedArray.length; j++)
				if (j == 0)
					seedArray[j] = calculateXor();
				else
					seedArray[j] = tempArray[j - 1];
			System.out.print(++counter + ": ");
			rightSide.append(counter + ": ");
			Arrays.stream(seedArray).forEach(e -> rightSide.append(e));
			Arrays.stream(seedArray).forEach(System.out::print);
			System.out.println();
			rightSide.append("\n");
		}
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
