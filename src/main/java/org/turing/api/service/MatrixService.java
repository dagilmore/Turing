package org.turing.api.service;

import java.util.List;
import java.util.UUID;

import org.turing.api.domain.Matrix;
import org.turing.api.repository.MatrixRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MatrixService {

	@Autowired
	private MatrixRepository matrixRepository;
		

	public Matrix create(Matrix matrix) {
		// matrix.setId(UUID.randomUUID().toString());

		return matrixRepository.save(matrix);
	}
	
	public Matrix read(Matrix matrix) {
		return matrix;
	}
	
	public List<Matrix> readAll() {
		return matrixRepository.findAll();
	}
	
	public Matrix update(Matrix matrix) {
		Matrix existingMatrix = matrixRepository.findById(matrix.getId());
		
		return matrixRepository.save(existingMatrix);
	}
	
	public Boolean delete(Matrix matrix) {
		Matrix existingMatrix = matrixRepository.findById(matrix.getId());
		
		if (existingMatrix == null) {
			return false;
		}

		matrixRepository.delete(existingMatrix);
		return true;
	}
}
