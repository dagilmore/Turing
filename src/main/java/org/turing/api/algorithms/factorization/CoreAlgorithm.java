/*
 * Single Threaded Matrix Factorization core algorithm
 */

  //STEPS governs the number of times to manipulate the values of the sub matrices
	for(int step = 0; step<STEPS; step++){

    //The next two loops iterate through the x and y 
    //dimensions - respectively - of P and Q. 
		for(int i = 0; i<r.getRowDimension()-1; i++){
		for(int j = 0; j<r.getColumnDimension()-1; j++){
			
      //The following lines are matrix operations that stochastically
      //tweak the values of P and Q
          double entry = r.getEntry(i,j);
    			if(entry > 0) {
    			eij = entry - this.dotProduct(p.getRow(i), q.getColumn(j));
    			for(int k = 0; k<K; k++){
    			newP = p.getEntry(i,k) + ALPHA * (2 * eij * q.getEntry(k,j) - BETA * p.getEntry(i,k));
    			p.setEntry(i,k, newP);
    			newQ = q.getEntry(k,j) + ALPHA * (2 * eij * p.getEntry(i,k) - BETA * q.getEntry(k,j));
    			q.setEntry(k,j, newQ);
			}

		}}}


