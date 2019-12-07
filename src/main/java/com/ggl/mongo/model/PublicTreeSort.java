package com.ggl.mongo.model;

import java.util.Comparator;

public class PublicTreeSort implements Comparator<Publictree>  {

	 public int compare(Publictree a, Publictree b) 
	    { 
	        return a.getQueueNumber() - b.getQueueNumber(); 
	    } 
	 
}
