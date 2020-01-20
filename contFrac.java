import java.util.*;

class contFrac {
	
	public static void contToClosed(int len, int[] contFracArray, int[] newArray, int[] pVals, int[] qVals, int index) {
		if (index == len) {
			solveQuad(pVals[index+1], qVals[index+1], pVals[index], qVals[index], contFracArray);
			
		}else {
		
			pVals[index+2] = newArray[index] * pVals[index+1] + pVals[index];
			qVals[index+2] = newArray[index] * qVals[index+1] + qVals[index];
			
			contToClosed(len, contFracArray, newArray, pVals, qVals, ++index);
		
		
		}
		
	}
	
	public static void solveQuad(int pVal, int qVal, int pValLow, int qValLow, int[] contFracArray) {
		int a = qValLow;
		int b = -1  * qValLow * contFracArray[0] + qVal  - pValLow;
		int c = -1 * qVal * contFracArray[0] - pVal;
		
		int discr = (b*b + -1* 4*a*c);
		int coeff = simplifyPerfSquare(discr, 1, 2);
		
		discr = discr / (coeff*coeff);
		int negB = -b;
		int denom = 2*a;
		
		//just need to simplify the coefficients 
		
		int commonFac = comFac(comFac(coeff, negB), denom);
		coeff /= commonFac;
		negB /= commonFac;
		denom /= commonFac;
		
		System.out.println("(" + negB + " + " + coeff + "sqrt(" + discr + ")) / " + denom);
	}
	
	public static int comFac(int a, int b) {
		if (a==0 ) {
			return b;
		}else if (b == 0) {
			return a;
		}
		
		if (a>b) {
			return euclidAlgorithm(a, b);
		}else if (b>a) {
			return euclidAlgorithm(b, a);
		}else {
			return a;
		}
		
	}
	public static int euclidAlgorithm(int a, int b) {
		if (a%b == 0) {
			return b;
		}else {
			return euclidAlgorithm (b, a%b);
		}
	}
	
	public static int simplifyPerfSquare(int n, int coeff, int curr) {		
		while (curr*curr <= n ) {
			if (n%curr == 0 && (n/curr)%curr == 0) {
				n = n/(curr*curr);
				coeff *= curr;
				curr--;				
			}
			curr++;
		}
		return coeff;
	}
	
	public static void closedToCont(int A, int B, int a, int val, int[][] memory) {
		
		int[] curr = new int[]{A, B, a};
		
		if (!compareToMemory(curr, memory)) {
			memory = updateMemory(curr, memory);
			
			System.out.print(a + " ");
			
			A = a*B - A;
			B = (val - A*A) / B;
			a = (int)Math.floor((Math.floor(Math.sqrt(val)) + A) / B);
			
			closedToCont(A, B, a, val, memory);	
		}
		
		
	}
	
	public static int[][] updateMemory(int[] newItem, int[][] memory) {
		int len = memory.length;
		
		int[][] newMemory = new int[len+1][3];
		
		for (int i = 0; i<len; i++) {
			for (int j = 0; j<3; j++) {
				newMemory[i][j] = memory[i][j];
				
			}
		}
		
		newMemory[len][0] = newItem[0];
		newMemory[len][1] = newItem[1];
		newMemory[len][2] = newItem[2];
		
		return newMemory;
	}
	
	static boolean arrayCompare(int[] arrayOne, int[] arrayTwo) {
		if (arrayOne.length != arrayTwo.length) {
			return false;
		}
		for (int i = 0; i< arrayTwo.length; i++) {
			if (arrayOne[i] != arrayTwo[i]) {
				return false;
			}
		}
		return true;
	}
	
	//for testing purposes
	static void printArray(int[] array) {
		for (int i = 0; i< array.length; i++) {
			System.out.print(array[i] + " ");
		}
		System.out.println("");
	}
	
	static boolean compareToMemory(int[] input, int[][] memory) {
		int size = memory.length;
		
		for (int i = 0; i<size; i++) {
			if (arrayCompare(input, memory[i])) {
				return true;
			}
		}
		return false;
		
	}
	
	// want it to be of form memory[the input number] [number 1-3]
	
	public static void main (String[]args) {
		Scanner scan = new Scanner(System.in);
		int input = 0;
		
		///*
		while (input != 1 && input != 2) {
			System.out.println("Type 1 for cont frac -> closed, type 2 for closed -> cont frac");
			String tempInp = scan.next();	
			try {
				input = Integer.parseInt(tempInp);
			} catch (Exception e) {
				System.out.println("Error: Enter an integer");
				input = 4;
			}
		}
		
		if (input == 1) {
			System.out.println("What is the length of the continued fraction (including the non-recursive part)");
			int len = scan.nextInt();
			int[] contFracArray = new int[len];
			System.out.println("Enter a continued fraction with the numbers separated by spaces, and I will express it in closed form.");
			
			for (int i = 0; i<len; i++) {
				contFracArray[i] = scan.nextInt();
			}
			
			
			int[] newArray = Arrays.copyOf(contFracArray, len);
			newArray[len-1] = contFracArray[len-1] - newArray[0];
			newArray[0] = 0;
			int[] pVals = new int[len+2];
			int[] qVals = new int[len+2]; // note that everything indexed here is up two spots.
			
			pVals[0] = 0;
			pVals[1] = 1;
			qVals[0] = 1;
			qVals[1] = 0;
			
			contToClosed(len, contFracArray, newArray, pVals, qVals, 0);
		}else if (input == 2) {
			System.out.println("Enter a value, and I will output the continued fraction of its square root.");
			int val = scan.nextInt();
			if (Math.sqrt(val) - Math.round(Math.sqrt(val)) == 0.0) {
				System.out.println((int)Math.sqrt(val));
			}else {
				closedToCont(0, 1, (int)Math.floor(Math.sqrt(val)), val, new int[0][3]);
			}
			
		}
	
	//*/
		
		
	}
	
}