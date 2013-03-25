package org.turing.api.dto;

import java.util.List;

import org.turing.api.domain.Matrix;

public class MatrixListDto {

	private List<Matrix> matrices;

	public List<Matrix> getMatrices() {
		return matrices;
	}

	public void setMatrices(List<Matrix> matrices) {
		this.matrices = matrices;
	}
}
