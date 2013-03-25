package org.turing.api.service;

import java.util.UUID;
import org.turing.api.domain.Role;
import org.turing.api.domain.User;
import org.turing.api.domain.Matrix;
import org.turing.api.algorithms.factorization.ProbabilisticMatrixFactorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

/**
 * Service for initializing MongoDB with sample data using {@link MongoTemplate}
 */
public class InitMongoService {
	
	@Autowired
	private MongoTemplate mongoTemplate;

	public void init() {
		// Drop existing collections
		mongoTemplate.dropCollection("role");
		mongoTemplate.dropCollection("user");
		mongoTemplate.dropCollection("matrix");

		// Create new records
		Role adminRole = new Role();
		adminRole.setId(UUID.randomUUID().toString());
		adminRole.setRole(1);
		
		Role userRole = new Role();
		userRole.setId(UUID.randomUUID().toString());
		userRole.setRole(2);
		
		User yomom = new User();
		yomom.setId(UUID.randomUUID().toString());
		yomom.setFirstName("Your");
		yomom.setLastName("Momma");
		yomom.setPassword("21232f297a57a5a743894a0e4a801fc3");
		yomom.setRole(adminRole);
		yomom.setUsername("phat");
		
		User mofucka = new User();
		mofucka.setId(UUID.randomUUID().toString());
		mofucka.setFirstName("Mo");
		mofucka.setLastName("Fucker");
		mofucka.setPassword("ee11cbb19052e40b07aac0ca060c23ee");
		mofucka.setRole(userRole);
		mofucka.setUsername("yoyoma");

		double[][] knowledgeBase = {
	 		{5.0, 3.0, 0.0, 1.0, 5.0, 3.0, 0.0},
	  		{4.0, 0.0, 0.0, 5.0, 3.0, 0.0, 1.0},
	  		{1.0, 5.0, 3.0, 0.0, 1.0, 0.0, 5.0},	
	  		{5.0, 3.0, 0.0, 1.0, 0.0, 0.0, 4.0},
	 		{5.0, 3.0, 0.0, 1.0, 5.0, 3.0, 0.0},	
	  		{5.0, 3.0, 0.0, 1.0, 0.0, 0.0, 4.0}
		};

		ProbabilisticMatrixFactorization pmf = new ProbabilisticMatrixFactorization();
		Matrix neo = new Matrix();
		neo.setId("testMatrix");
		neo.setKnowledgeBase(knowledgeBase);
		neo.setInferred(pmf.matrix_factorization(knowledgeBase));
		neo.setSteps(100000);
		neo.setAlpha(0.0001);
		neo.setBeta(0.001);
		neo.setK(2);
		
		// Insert to db
		mongoTemplate.insert(yomom, "user");
		mongoTemplate.insert(mofucka, "user");
		mongoTemplate.insert(adminRole, "role");
		mongoTemplate.insert(userRole, "role");
		mongoTemplate.insert(neo, "matrix");

	}
}
