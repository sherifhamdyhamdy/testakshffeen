package com.sget.akshef.utils;



import java.util.Comparator;

import com.sget.akshef.hibernate.beans.BranchBean;



public class RatingComparator implements Comparator<BranchBean> {

	
	@Override
    public int compare(BranchBean a, BranchBean b) {
        return a.getRating() > b.getRating() ? -1 : a.getRating() == b.getRating() ? 0 : 1;
    }
}
