package com.ggl.mongo.dal;


import com.ggl.mongo.model.MiniTree;

//import java.util.List;

//import com.ggl.mongo.model.Publictree;
import com.ggl.mongo.model.RandomNumber;



public interface RandomNumberDAL {

	public RandomNumber getAllRandamNumber();
	public RandomNumber getTempPublicRandomNumber();

	
	
	// Fist row update - 1
	public String updateRandamNumber(RandomNumber rn);
	public String updateTempPublicRandamNumber(RandomNumber rn);
	public String updateTempPrivateRandamNumber(RandomNumber rn);
	public String updateTempOwnTreeandamNumber(RandomNumber rn);

	
	
	
	
	// Fist row update - 2
	public String updateRandamNumberForOwnTree(RandomNumber rn);
	
	// For Own Tree Random Number 
	public  RandomNumber getAllOwnTreeRandomNumber();
	
	//---------- Mini Tree RandomNumber --------
	public RandomNumber getTempMiniRandomNumber();
	public RandomNumber getAllMiniRandamNumber();
	public String updateTempMiniRandamNumber(RandomNumber rn);
	public String updateMiniRandamNumber(RandomNumber document);
	public MiniTree getLastMiniQueueNumber();  
}