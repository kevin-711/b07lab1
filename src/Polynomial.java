
public class Polynomial {
	
	double [] coef;
	
	public Polynomial() {
		coef = new double[1];
	}
	
	public Polynomial(double [] input) {
		
		coef = new double[input.length];
		
		for(int i = 0; i < input.length; i++) {
			coef[i] = input[i];
		}
		
	}
	
	public Polynomial add(Polynomial poly) {
		
		double [] newCoef = new double[Math.max(poly.coef.length, coef.length)];
		
		if(poly.coef.length <= coef.length) {
			for(int i = 0; i < poly.coef.length; i++) {
				newCoef[i] = poly.coef[i] + coef[i];
			}
			for(int i = poly.coef.length; i < coef.length; i++) {
				newCoef[i] = coef[i];
			}
		}
		if(poly.coef.length > coef.length) {
			for(int i = 0; i < coef.length; i++) {
				newCoef[i] = poly.coef[i] + coef[i];
			}
			for(int i = coef.length; i < poly.coef.length; i++) {
				newCoef[i] = poly.coef[i];
			}
		}
		
		Polynomial res = new Polynomial(newCoef);
		
		return res;
	}
	
	public double evaluate(double x) {
		
		double res = 0;
		
		for(int i = 0; i < coef.length; i++) {
			res += coef[i]*Math.pow(x, i);
		}
		
		return res;
	}
	
	public boolean hasRoot(double x) {
		
		if (evaluate(x) == 0) {
			return true;
		}
		return false;
		
	}
}
