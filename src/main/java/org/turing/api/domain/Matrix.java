package org.turing.api.domain;

import com.google.gson.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Matrix {
	
	@Id
	private String id;

	private String knowledgeBase;
	private String inferred;
	private int steps;
	private double alpha;
	private double beta;
	private int k;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public double[][] getKnowledgeBase() {
		Gson gson = new Gson();
		return gson.fromJson(knowledgeBase,double[][].class);
	}

	public void setKnowledgeBase(double[][] knowledgeBase) {
		Gson gson = new Gson();
		this.knowledgeBase = gson.toJson(knowledgeBase);
	}
	public double[][] getInferred() {
		Gson gson = new Gson();
		return gson.fromJson(inferred,double[][].class);
	}

	public void setInferred(double[][] inferred) {
		Gson gson = new Gson();
		this.inferred = gson.toJson(inferred);
	}
	public int getSteps() {
		return steps;
	}

	public void setSteps(int steps) {
		this.steps = steps;
	}
	public double getAlpha() {
		return alpha;
	}

	public void setAlpha(double alpha) {
		this.alpha = alpha;
	}
	public double getBeta() {
		return beta;
	}

	public void setBeta(double beta) {
		this.beta = beta;
	}
	public int getK() {
		return k;
	}

	public void setK(int k) {
		this.k = k;
	}

}
