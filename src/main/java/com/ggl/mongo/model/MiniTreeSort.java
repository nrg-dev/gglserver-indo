package com.ggl.mongo.model;

import java.util.Comparator;

public class MiniTreeSort implements Comparator<MiniTree>  {

	 public int compare(MiniTree a, MiniTree b) 
	    { 
	        return a.getQueueNumber() - b.getQueueNumber(); 
	    } 
	 
}
