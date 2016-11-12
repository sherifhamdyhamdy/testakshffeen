package com.sget.akshef.hibernate.test;

import com.sget.akshef.hibernate.service.SubcategoryHasSectionsHasBranchService;

/**
 * @author JDeeb
 *
 */
public class TestMain {

	/**
	 * @param args
	 */
	
	public static void main(String[] args) {

		SubcategoryHasSectionsHasBranchService service = new SubcategoryHasSectionsHasBranchService();
		System.out.println(service.getById(1));
	
	}

}
