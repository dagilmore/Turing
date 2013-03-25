package org.turing.api.repository;

import org.turing.api.domain.Matrix;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MatrixRepository extends MongoRepository<Matrix, String> {
	
	Matrix findById(String id);
}
