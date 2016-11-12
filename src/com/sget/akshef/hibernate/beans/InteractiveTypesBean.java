package com.sget.akshef.hibernate.beans;

import java.io.Serializable;

/**
 *
 * @author JDeeb
 */

public class InteractiveTypesBean implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int id;
    
    private String name;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
    
    
    

}
