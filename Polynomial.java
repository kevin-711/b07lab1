import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;

public class Polynomial {
	
	double [] coef;
	int [] exp;
	
	public Polynomial() {
		coef = new double[1];
		exp = new int[1];
	}
	
	public Polynomial(double [] coef, int [] exp) {
		
		int len = coef.length;
		
		this.coef = new double[len];
		
		for(int i = 0; i < len; i++) {
			this.coef[i] = coef[i];
		}
		
		this.exp = new int[len];
		
		for(int i = 0; i < len; i++) {
			this.exp[i] = exp[i];
		}
		
	}
	
	public Polynomial(File file) {
		
		try {
			Scanner input = new Scanner(file);
			String text = input.nextLine();
			text = text.replace("-", "+-");
			
	        String[] split = text.split("[+]");
	        
//	        for (int i = 0; i < split.length; i++) {
//	        	System.out.println(split[i]);
//	        }
	        
	        coef = new double[split.length];
			exp = new int[split.length];
	        
	        for (int i = 0; i < split.length; i++) {
	        	
	        	if (split[i].length() == 1) {
	        		coef[i] = Double.parseDouble(split[i]);
	        		exp[i] = 0;
	        	} else {
	        		String[] split2 = split[i].split("x");
	        		
	        		coef[i] = Double.parseDouble(split2[0]);
	        		exp[i] = Integer.parseInt(split2[1]);
	        	}
	        	
	        }
	        
	        for (int i = 0; i < split.length; i++) {
				System.out.println(coef[i]);
			}
			System.out.println(' ');
			for (int i = 0; i < split.length; i++) {
				System.out.println(exp[i]);
			}	        
	        
		} catch (FileNotFoundException e) {
			System.out.println("Error: " + e);
		}
	}
	
	
//	Helper Function
//	Returns indice of an element in an array
	public int isInArray(int a, int [] arr) {
		for (int i = 0; i < arr.length; i++) {
			if (a == arr[i]) {
				return i;
			}
		}	
		return -1;
	}
	
	public Polynomial add(Polynomial poly) {
		
		int poly1_len = coef.length;
		int poly2_len = poly.coef.length;
		int new_len = poly1_len + poly2_len;
		
		double [] newCoef = new double[new_len];
		int [] newExp = new int[new_len];
		
		int count = 0;
		
		for (int i = 0; i < poly1_len; i++) {
			
			int indice = isInArray(exp[i], newExp);
			
			if (indice != -1 && exp[indice] != 0) {
				newCoef[indice] += coef[i];
			} else {
				newCoef[count] = coef[i];
				newExp[count] = exp[i];
				count++;
			}
		}
		
		for (int i = 0; i < poly2_len; i++) {
			
			int indice = isInArray(poly.exp[i], newExp);
			
			if (indice != -1 && poly.exp[indice] != 0) {
				newCoef[indice] += poly.coef[i];
			} else {
				newCoef[count] = poly.coef[i];
				newExp[count] = poly.exp[i];
				count++;
			}
		}
		
//		Create new arrays, getting rid of redundant (0) exponents
		int lenfinal = 0;
		for (int i = 0; i < new_len; i++) {
			if (newCoef[i] != 0) {
				lenfinal++;
			}
		}
		double [] finalCoef = new double[lenfinal];
		int [] finalExp = new int[lenfinal];
		
		int indice = 0;
		for (int i = 0; i < new_len; i++) {
			if (newCoef[i] != 0) {
				finalCoef[indice] = newCoef[i];
				finalExp[indice] = newExp[i];
				indice++;
			}
		}
		
//		for (int i = 0; i < lenfinal; i++) {
//			System.out.println(finalCoef[i]);
//		}
//		System.out.println(' ');
//		for (int i = 0; i < lenfinal; i++) {
//			System.out.println(finalExp[i]);
//		}
		
		Polynomial res = new Polynomial(finalCoef, finalExp);
		
		return res;
	}
	
	public double evaluate(double x) {
		
		double res = 0;
		
		for (int i = 0; i < coef.length; i++) {
			res += coef[i]*Math.pow(x, exp[i]);
		}
		
		return res;
	}
	
	public boolean hasRoot(double x) {
		
		if (evaluate(x) == 0) {
			return true;
		}
		return false;
		
	}
	
	public Polynomial multiply(Polynomial poly) {
		
		int poly1_len = coef.length;
		int poly2_len = poly.coef.length;
		int new_len = poly1_len * poly2_len;
		
		Polynomial fin = new Polynomial();
		
		
		for (int i = 0; i < poly1_len; i++) {
			
			double [] newCoef = new double[new_len];
			int [] newExp = new int[new_len];
			
			for (int j = 0; j < poly2_len; j++) {
				newCoef[j] = coef[i]*poly.coef[j]; 
				newExp[j] = exp[i] + poly.exp[j];
			}
			
			Polynomial part = new Polynomial(newCoef, newExp);
			
			fin = fin.add(part);
		}

		return fin;
	}
	
	public void saveToFile(String filename) {
		
		String polystring = "";
		
		for (int i = 0; i < coef.length; i++) {
			if (exp[i] == 0) {
				polystring += coef[i] + "+";
			} else {
				polystring += coef[i] + "x" + exp[i] + "+";
			}
		}
		
		polystring = polystring.replace("+-", "-");
		if (polystring.charAt(polystring.length() - 1) == '+') {
			polystring = polystring.substring(0, polystring.length() - 1);
		}
		
		try {
			FileWriter writer;
			writer = new FileWriter(filename);
			writer.write(polystring);
			writer.close();
		} catch (IOException e) {
			System.out.println("Error: " + e);
		}
        
		
        
        
	}
	
}
