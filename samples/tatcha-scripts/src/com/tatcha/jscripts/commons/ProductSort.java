package com.tatcha.jscripts.commons;

import java.util.Comparator;

import com.tatcha.jscripts.dao.Product;

public class ProductSort implements Comparator<Product>{

	@Override
	public int compare(Product o1, Product o2) {
//			int id1 = Integer.parseInt(o1.getPid());
//			int id2 = Integer.parseInt(o2.getPid());
//			System.out.println("id1 "+id1+" id2 "+id2);
		
//		return id1 - id2;	
//		return id1 > id2 ? -1 : id1 == id2 ? 0 : 1;
		int val = o1.getPid().compareTo(o2.getPid());
		if(val==0){
			int val2 = o1.getName().compareTo(o2.getName());
			if(val2==0){
				int val3 = o1.getPrice().compareTo(o2.getPrice());
				if(val3 == 0){}
				else return val3;
			}else
				return val2;
		}
		return val;
	}
	
	/*public int compare(Product o1, Product o2) {
		int val = o1.getPid().compareTo(o2.getPid());
		return val;
	}*/
}