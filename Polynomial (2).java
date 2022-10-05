package lab1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Polynomial{
	public static void main(String args[]) {
		System.out.println("yes");
	}
	List<Double> coef;
	List<Integer> exp;

	public Polynomial(){
		coef = new ArrayList<Double>();
		coef.add((double) 0);

		exp = new ArrayList<Integer>();
		exp.add(0);
	}
	
	public Polynomial(List<Double> coefficients, List<Integer> exponents){
		coef = new ArrayList<>(coefficients);
		exp = new ArrayList<>(exponents);
	}

	public Polynomial(File f) throws FileNotFoundException {
//		Scanner reader = new Scanner(f);
//		String data = reader.nextLine();
//		reader.close();
//		System.out.println(data);
//
//
//		// we want to add + in front of -
//		String modifiedData = "";
//		String[] s = data.split("-");
//		for (String ss : s) {
//			System.out.println(ss);
//		}
//
//
//		for (int i = 0; i < s.length; i++) {
//			modifiedData = modifiedData.concat("+-" + s[i]);
//		}
//		System.out.println(modifiedData);
//
//		// then split string into elements
//		// each element has 3 parts: coef, x, exp (eg. -3x5)
//		String[] parsedStrings = modifiedData.split("[+]");
//		List<String> newParsedString = new ArrayList<>(List.of(parsedStrings));
//
//		System.out.println(newParsedString);





	}

	
	public Polynomial add(Polynomial p1){

		List<Double> coefficients = new ArrayList<Double>(this.coef);
		List<Integer> exponents = new ArrayList<Integer>(this.exp);

		// add p1 into the new lists
		for (int i = 0; i < p1.coef.size(); i++) {
			// if we have same exponent in p1, just add coefficient
			Integer currentExp = p1.exp.get(i);
			Double currentCoef = p1.coef.get(i);

			if (exponents.contains(currentExp)) {
				int index = exponents.indexOf(currentExp);
				coefficients.set(index, coefficients.get(index) + currentCoef);
			} else {
				// insert currentExp and currentCoef
				// 3 cases: at first, at last, in between
				// there might be a better way but this works
				if (currentExp > exponents.get(0)) {
					exponents.add(0, currentExp);
					coefficients.add(0, currentCoef);
				} else if (exponents.get(exponents.size() - 1) > currentExp) {
					int lastIndex = exponents.size() - 1;
					exponents.add(lastIndex, currentExp);
					coefficients.add(lastIndex, currentCoef);
				} else {
					// search for the right place to insert the current exp and coef
					for (int j = 0; j < exponents.size() - 1; j++) {
						if (exponents.get(j) > currentExp && currentExp > exponents.get(j + 1)) {
							exponents.add(j + 1, currentExp);
							coefficients.add(j + 1, currentCoef);
						}
					}
				}
			}
		}

		return new Polynomial(coefficients, exponents);
	}

	public Polynomial multiply(Polynomial p) {
		// notice (a1 + .. + an)(b1 + .. + bn) = a1(b1 + .. + bn) + .. + an(b1 + .. + bn)
		// break Polynomial a and b into small polynomials
		List<Polynomial> ps1 = new ArrayList<>();
		for (int i = 0; i < this.coef.size(); i++) {
			Polynomial temp = new Polynomial(
				new ArrayList<>(Arrays.asList(this.coef.get(i))),
				new ArrayList<>(Arrays.asList(this.exp.get(i))));
			ps1.add(temp);
		}

		List<Polynomial> ps2 = new ArrayList<>();
		for (int i = 0; i < p.coef.size(); i++) {
			Polynomial temp = new Polynomial(
				new ArrayList<>(Arrays.asList(p.coef.get(i))),
				new ArrayList<>(Arrays.asList(p.exp.get(i))));
			ps2.add(temp);
		}

		// for each two elements, multiply and add them to accumulator
		Polynomial accumulator = new Polynomial();
		for (int i = 0; i < ps1.size(); i++) {
			for (int j = 0; j < ps2.size(); j++) {
				Double tempCoef = ps1.get(i).coef.get(0) * ps2.get(j).coef.get(0);
				Integer tempExp = ps1.get(i).exp.get(0) + ps2.get(j).exp.get(0);

				if (tempCoef == 0) {
					continue;
				}

				Polynomial temp = new Polynomial(Arrays.asList(tempCoef), Arrays.asList(tempExp));
				accumulator = accumulator.add(temp);
//				System.out.println("adding " + tempCoef + "x^" + tempExp + ":\n" + accumulator.exp + " " + accumulator.coef);


			}
		}
		return accumulator;
	}

	public List<Double> getCoef() {
		return coef;
	}

	public List<Integer> getExp() {
		return exp;
	}

	public double evaluate(double x) {
		double Result = 0; 
		for(int i = 0; i < this.coef.size(); i++) {
			Result += this.coef.get(i) * Math.pow(x, this.exp.get(i));
		}
		return Result; 
	}
	public boolean hasRoot(double x) {
		return this.evaluate(x)== 0;
	}
	
}