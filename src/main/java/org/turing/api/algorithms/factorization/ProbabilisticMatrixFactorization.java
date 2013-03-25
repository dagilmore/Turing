package org.turing.api.algorithms.factorization;

import org.apache.commons.math3.linear.BlockRealMatrix;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.analysis.function.Pow;
import java.util.Random;
import java.lang.Math;

public class ProbabilisticMatrixFactorization {

private double[][] initial;
private double[][] raw;
private double[][] combined;

private int K;
private int STEPS;
private double ALPHA;
private double BETA;

	public ProbabilisticMatrixFactorization()
	{
		K = 2;
		STEPS = 100000;
		ALPHA = 0.0001;
		BETA = 0.001;
	}
	
	public double[][] matrix_factorization(double[][] blockData){
		
		setInitial(blockData);

		int yLength = blockData.length;
		int xLength = blockData[0].length;

		double[][] userLatents = randMatrix(yLength,K);
		double[][] qualityLatents =  randMatrix(xLength,K);

		BlockRealMatrix R = new BlockRealMatrix(blockData);
		BlockRealMatrix P = new BlockRealMatrix(userLatents);
		BlockRealMatrix Q = new BlockRealMatrix(qualityLatents);

		raw = matrix_factorization(R,P,Q,K).getData();

		return combine(blockData,raw);
	}

	private BlockRealMatrix matrix_factorization(BlockRealMatrix r,BlockRealMatrix p,BlockRealMatrix q,int K){	
	
	double newP = 0.0;
	double newQ = 0.0;
	q = q.transpose();
	double eij = 0;
	for(int step = 0; step<STEPS; step++){
		
		for(int i = 0; i<r.getRowDimension()-1; i++){
		for(int j = 0; j<r.getColumnDimension()-1; j++){
			double entry = r.getEntry(i,j);
			if(entry > 0) {
			eij = entry - this.dotProduct(p.getRow(i), q.getColumn(j));
			
			//TEST EIJ
			// System.out.println(eij);

			for(int k = 0; k<K; k++){
			newP = p.getEntry(i,k) + ALPHA * (2 * eij * q.getEntry(k,j) - BETA * p.getEntry(i,k));
			p.setEntry(i,k, newP);
			newQ = q.getEntry(k,j) + ALPHA * (2 * eij * p.getEntry(i,k) - BETA * q.getEntry(k,j));
			q.setEntry(k,j, newQ);
			}

		}}}
		
		BlockRealMatrix eR = p.multiply(q);
		double e = 0;
		
		for(int i = 0; i<r.getRowDimension()-1; i++){
		for(int j = 0; j<r.getColumnDimension()-1; j++){
			double entry = r.getEntry(i,j);
			if(entry > 0) {
			Pow pow = new Pow();
			e = e +	pow.value(entry - dotProduct(p.getRow(i), q.getColumn(j)),2);
			
			//TEST E
			// System.out.println(e);

			for(int k = 0; k<K; k++){
			e = e + (BETA/2) * ( pow.value(p.getEntry(i,k),2) + pow.value(q.getEntry(k,j),2) );
			}}

		if(e < 0.0001) break;

		}}
	}	

	BlockRealMatrix ret = dotProduct(p, q);
	return ret;
	}

	public void printMatrix(double[][] mat)
	{
		for(int i = 0; i<mat.length; i++){
		for(int j = 0; j<mat[0].length; j++){
		System.out.print(round(mat[i][j],2) + " ");
		}
		System.out.print("\n");
		}
		System.out.print("\n");
	}

	public void printMatrix(BlockRealMatrix mat)
	{
		for(int i = 0; i<mat.getRowDimension(); i++){
		for(int j = 0; j<mat.getColumnDimension(); j++){

		
		System.out.print(round(mat.getEntry(i,j),2) + " ");
		}
		System.out.print("\n");}
		System.out.print("\n");
	}	

	public double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    long factor = (long) Math.pow(10, places);
	    value = value * factor;
	    long tmp = Math.round(value);
	    return (double) tmp / factor;
	}
	
	private BlockRealMatrix dotProduct(BlockRealMatrix a, BlockRealMatrix b) {
		return a.multiply(b);
	}
	
	private double dotProduct(double[] a, double[] b) {
		
		if (a.length != b.length) {
			return -1;
		}
		
		double dotProduct = 0.0;
		
		for (int i = 0; i < a.length; i++) {
			dotProduct += a[i] * b[i];
		}
		
		return dotProduct;
	}

	private double[][] randMatrix(int x, int y) 
	{
	Random rand = new Random();
	double[][] temp = new double[x][y];
	for(int i = 0; i < x; i++){
	for(int j = 0; j < y; j++){
		temp[i][j] = rand.nextDouble();}
	}
	return temp;
	}

	private double[][] combine(double[][] init, double[][] inferred){

		//Use a matrix of doubles to avoid filling in
		//known values from factored matrix
		int yLength = init.length;
		int xLength = init[0].length;

		boolean[][] known = new boolean[yLength][xLength];
		for(int i = 0; i < yLength; i++){
		for(int j = 0; j < xLength; j++){
			if(init[i][j]!=0)
				known[i][j] = true;
			else
				known[i][j] = false;
		}
		}

		double[][] ret = init;

		for(int i = 0; i < yLength; i++){
		for(int j = 0; j < xLength; j++){
			if(!known[i][j])
				ret[i][j] = inferred[i][j];
		}
		}
		return ret;

	} 
	
	public void setInitial(double[][] init){
		this.initial = init;
	}
	public double[][] getInitial(){
		return initial;
	}
	public void setSteps(int steps){
		this.STEPS = steps;
	}
	public int getSteps(){
		return this.STEPS;
	}
	public void setAlpha(double alpha){
		this.ALPHA = alpha;
	}
	public double getAlpha(){
		return this.ALPHA;
	}
	public void setBeta(double beta){
		this.BETA = beta;
	}
	public double getBeta(){
		return this.BETA;
	}
	public void setK(int k){
		this.K = k;
	}
	public int getK(){
		return this.K;
	}
	public double[][] getRaw(){
		return this.raw;
	}

}
