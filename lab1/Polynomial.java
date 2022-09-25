package lab1;	

public class Polynomial{
	public static void main(String args[]) {
		System.out.println("yes");
	}
	double[] Coef;
	public Polynomial(){
		Coef = new double[1];
		Coef[0] = 0; 
	}
	
	public Polynomial(double[] coefficients){
		Coef = coefficients; 
		
	}
	
	
	public Polynomial add(Polynomial polynomial1){
		double Results[] = new double[Math.max(this.Coef.length, polynomial1.Coef.length)];
		for(int i = 0; i < Results.length; i++ ) {
			if(i < Coef.length) {
				Results[i] += Coef[i];
			}
			
			if(i < polynomial1.Coef.length) {
				Results[i] += polynomial1.Coef[i]; 
			}
			
		}
		return new Polynomial(Results); 
	}
	
	public double evaluate(double x) {
		double Result = 0; 
		for(int i = 0; i < this.Coef.length; i++) {
			Result += this.Coef[i] * Math.pow(x,i); 
		}
		return Result; 
	}
	public boolean hasRoot(double x) {
		return this.evaluate(x)== 0;
	}
	
}