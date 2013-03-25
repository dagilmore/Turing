package org.turing.api.controller;

import org.turing.api.domain.Matrix;
import org.turing.api.dto.MatrixListDto;
import org.turing.api.service.MatrixService;
import org.turing.api.algorithms.factorization.ProbabilisticMatrixFactorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.turing.api.repository.MatrixRepository;

@Controller
@RequestMapping("/matrix")
public class MatrixController {

	@Autowired
	private MatrixService service;

	@RequestMapping
	public String getMatrixPage() {

		MatrixListDto matrixListDto = new MatrixListDto();
		matrixListDto.setMatrices(service.readAll());

		return "matrix";
	}
	
	@RequestMapping(value="/records")
	public @ResponseBody MatrixListDto getMatrixs() {
		
		MatrixListDto matrixListDto = new MatrixListDto();
		matrixListDto.setMatrices(service.readAll());
		return matrixListDto;
	}
	
	@RequestMapping(value="/get")
	public @ResponseBody Matrix get(@RequestBody Matrix matrix) {
		return service.read(matrix);
	}
			//@RequestParam double[][] knowledgeBase,
	@RequestMapping(value="/create", method=RequestMethod.POST)
	public @ResponseBody Matrix create(
			@RequestParam String name,
			@RequestParam int steps,
			@RequestParam double alpha,
			@RequestParam double beta,
			@RequestParam int k){

		ProbabilisticMatrixFactorization pmf = new ProbabilisticMatrixFactorization();
		pmf.setSteps(steps);
		pmf.setAlpha(alpha);
		pmf.setBeta(beta);
		pmf.setK(k);

		double[][] knowledgeBase = {
	 		{5.0, 3.0, 0.0, 1.0, 5.0, 3.0, 0.0},
	  		{4.0, 0.0, 0.0, 5.0, 3.0, 0.0, 1.0},
	  		{1.0, 5.0, 3.0, 0.0, 1.0, 0.0, 5.0},	
	  		{5.0, 3.0, 0.0, 1.0, 0.0, 0.0, 4.0},
	 		{5.0, 3.0, 0.0, 1.0, 5.0, 3.0, 0.0},	
	  		{5.0, 3.0, 0.0, 1.0, 0.0, 0.0, 4.0}
		};

		Matrix newMatrix = new Matrix();
		newMatrix.setId(name);
		newMatrix.setKnowledgeBase(knowledgeBase);
		newMatrix.setInferred(pmf.matrix_factorization(knowledgeBase));
		newMatrix.setSteps(steps);
		newMatrix.setAlpha(alpha);
		newMatrix.setBeta(beta);
		newMatrix.setK(k);
		return service.create(newMatrix);
	}
	
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public @ResponseBody Matrix update(
			@RequestParam String id,
			@RequestParam int steps,
			@RequestParam double alpha,
			@RequestParam double beta,
			@RequestParam int k) {

		// MatrixRepository matrixRepository = new MatrixRepository();

		Matrix existingMatrix = new Matrix();  //matrixRepository.findById(name);
		existingMatrix.setSteps(steps);
		existingMatrix.setAlpha(alpha);
		existingMatrix.setBeta(beta);
		existingMatrix.setK(k);
		
		return service.update(existingMatrix);
	}
	
	@RequestMapping(value="/delete", method=RequestMethod.POST)
	public @ResponseBody Boolean delete(
			@RequestParam String name) {

		Matrix existingMatrix = new Matrix();
		existingMatrix.setId(name);
		
		return service.delete(existingMatrix);
	}
}
