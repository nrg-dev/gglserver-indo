package com.ggl.mongo.dal;


//import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

//import com.ggl.model.RandamNumber;

//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;

import com.ggl.mongo.model.MemberDetails;
import com.ggl.mongo.model.MemberRanomNumber;
//import com.mongodb.client.model.Sorts;
//import com.mongodb.operation.OrderBy;
//import com.ggl.mongo.model.PrivateTree;


@Repository
public class MemberImpl implements MemberDAL {
	
	public static final Logger logger = LoggerFactory.getLogger(MemberImpl.class);


	@Autowired
	private MongoTemplate mongoOperations;

	
	//public String insertUser(Publictree publictree);

	// ---------------------------- get Random member Code --------------
		@Transactional(value="transactionManager")
	//	@SuppressWarnings (value="unchecked")
		public int getRandomNumber(int newCode,String requestType) {
			Update update=null;
			try {
				logger.info("-------------- getRandamCode inside try ---------->");
			
				Query query = new Query();
				query.addCriteria(Criteria.where("consnumber").is("GGLRandom"));
				MemberRanomNumber mr = mongoOperations.findOne(query, MemberRanomNumber.class);
				logger.info("-------------- getRandamCode  after select query ---------->");

				if(requestType.equalsIgnoreCase("CurrentGLGCode"))
				{
					logger.info("-------------- getRandamCode  inside CurrentGGLCode If ---------->");
					int temp = mr.getCurrent_member_number();//getCurrent_Member_Number();
					logger.info("Existing code ---------->"+temp);
					newCode = mr.getCurrent_member_number() + 1;
					logger.info("Generated New code ------------>"+newCode);			
		        	update = new Update();
		            //query = new Query();
		            //query.addCriteria(Criteria.where("consnumber").is("GGLRandom"));
		    		update.set("current_member_number",newCode);    
		    		mongoOperations.updateFirst(query, update, MemberRanomNumber.class);    		

		    		
					logger.info("New Member Code Updated Successfully");
				}
				
				if(requestType.equalsIgnoreCase("CurrentGroupCode")){
					logger.info("-------------- getRandamCode  inside If CurrentGroupCode ---------->");

					int currentGroupCode = mr.getCurrent_group_number();//.getCurrent_Group_Number();
					newCode = currentGroupCode + 1;
					logger.info("Generated New Group Code  ------------>"+newCode);		
					
					update = new Update();
		           // query = new Query();
		            //query.addCriteria(Criteria.where("consnumber").is("GGLRandom"));
		    		update.set("current_group_number",newCode);    
		    		mongoOperations.updateFirst(query, update, MemberRanomNumber.class);    
		    		
					//query=entityManager.createQuery("update RandamNumber set Current_Group_Number="+newCode);
				    //query.executeUpdate();
					logger.info("New Group Code Updated Successfully for CurrentGroupCode If");
				}
				
				if(requestType.equalsIgnoreCase("treeNumber")){
					logger.info("-------------- getRandamCode  inside If treeNumber ---------->");
					int currentTreeNumber = mr.getTreerandamnumber();//.getTreeRandamNumber();
					newCode = currentTreeNumber + 1;
					logger.info("Generated New Group Code  ------------>"+newCode);		
					update = new Update();
					update.set("treerandamnumber",newCode);   
					mongoOperations.updateFirst(query, update, MemberRanomNumber.class);   
				//	query=entityManager.createQuery("update RandamNumber set treeRandamNumber="+newCode);
				  //  query.executeUpdate();
					logger.info("New Group Code Updated Successfully for TreeNumber if");
				}
			
				
				return newCode;
			}catch(Exception e) {
				logger.info("Exception ------------>"+e.getMessage());
			}finally
			{
				
			}
			return newCode;	
		}
			
		
	
	public boolean validateMember(String memberID) {
		logger.info("Ref Member ID -->"+memberID);
		Query query = new Query();
		query.addCriteria(Criteria.where("member_Number").is(memberID));
		MemberDetails md = mongoOperations.findOne(query, MemberDetails.class);
		if(md !=null) {
			logger.info("Value found...");
			return true;
		}
		else {
			logger.info("No Value found...");
			return false;
		}
		/*if(md.getMember_Number()!=null) {


		}
		else 
		{
			

		}*/
	}

	@Override
	public String saveMember(MemberDetails memberDetails) 
	{
		mongoOperations.insert(memberDetails);//(query, RandamNumber.class);
	return "";
	}

		
}
