package com.ggl.mongo.dal;

import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.core.query.Update;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;



//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import java.util.stream.Stream;

import com.ggl.bo.GglBo;
import com.ggl.dto.Member;
import com.ggl.email.InvestmentEmail;
import com.ggl.model.UserDetail;
import com.ggl.mongo.model.MiniTree;
import com.ggl.mongo.model.MiniTreeSort;
import com.ggl.mongo.model.OwnTree;
import com.ggl.mongo.model.PrivateTree;
import com.ggl.mongo.model.PublicTreeSort;
import com.ggl.mongo.model.Publictree;
import com.ggl.mongo.model.RandomNumber;
import com.ggl.mongo.model.TempMiniTree;
import com.ggl.mongo.model.TempOwnTree;
import com.ggl.mongo.model.TempPrivateTree;
import com.ggl.mongo.model.TempPublicTree;
import com.ggl.util.Email;
//import com.mongodb.client.model.Sorts;
//import com.mongodb.operation.OrderBy;


@Repository
public class PublicTreeImpl implements PublicTreeDAL {
	
	public static final Logger logger = LoggerFactory.getLogger(PublicTreeImpl.class);


	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	GglBo investmentBo1;

	
	private RandomNumberDAL randamNumberDAL1;
	
	//public String insertUser(Publictree publictree);

	
	
	public PublicTreeImpl(RandomNumberDAL randamNumberDAL1) {
	//	this.randamNumberRepository = randamNumberRepository;
		this.randamNumberDAL1 = randamNumberDAL1;
	}

	
	@Override
	public String insertTempPublicUser(TempPublicTree temppublictree) 
	{
	mongoTemplate.insert(temppublictree);//(query, RandamNumber.class);
	return "";
	}

	@Override
	public String insertTempPrivateTreeUser(TempPrivateTree temprivate)
	{
	mongoTemplate.insert(temprivate);//(query, RandamNumber.class);
	return "";
	}
	
	@Override
	public String insertTempOwnTreeUser(TempOwnTree tempowntree)
	{
	mongoTemplate.insert(tempowntree);//(query, RandamNumber.class);
	return "";
	}

	@Override
	public String insertUser(Publictree publictree) {
		mongoTemplate.insert(publictree);//(query, RandamNumber.class);
		return "";
	}
	
	@Override
	public List<Publictree> getSinglePurchaseUnitByUserId(String primaryKey) {
		List<Publictree> list;
		Query query = new Query();
		query.addCriteria(Criteria.where("userID").is(Integer.valueOf(primaryKey)));
		list = mongoTemplate.find(query, Publictree.class);
		return list;
		//return mongoTemplate.find(query, Publictree.class);
	}
	
	@Override
	public Publictree get8ComeOneOut() {
		logger.info("-------------------Inside get8ComeOneOut--------------------------"); 

		Publictree publictree;
		Query query = new Query();
		query.addCriteria(Criteria.where("userQueueStatus").is("IN"));
		publictree = mongoTemplate.findOne(query, Publictree.class);		
		List<Publictree>  list = mongoTemplate.find(query, Publictree.class);		
		logger.info("-------------------Unsorted--------------------------"); 
		for (int i=0; i<list.size(); i++) {
            logger.info(list.get(i).getInvoiceNumber()); 
		}
        Collections.sort(list, new PublicTreeSort()); 
  
        logger.info("---------------------Sorted by User Id-------------------------"); 
        for (int i=0; i<list.size(); i++) {
            logger.info(list.get(i).getInvoiceNumber()); 
        }
	
		return publictree;
		//return mongoTemplate.find(query, Publictree.class);
	}
	
	
	public void reOrderPrivateTree(PrivateTree privatetree) {
		logger.info("-------------------Inside reOrderPrivateTree reOrder--------------------------"); 
		Update update=null;// = new Update();
		Query query = new Query();
		query.addCriteria(Criteria.where("userQueueStatus").is("IN"));
		query.addCriteria(Criteria.where("referanceGSPNumber").is(privatetree.getReferanceGSPNumber()));
		List<PrivateTree>  list = mongoTemplate.find(query, PrivateTree.class);		
		logger.info("-------------------reOrderPrivateTree Unsorted--------------------------"); 
		for (int i=0; i<list.size(); i++) {
            logger.info(list.get(i).getInvoiceNumber()); 
		}
     //   Collections.sort(list, new PrivateTree());
        logger.info("---------------------Sorted reOrderPrivateTree by User Id-------------------------"); 
       int j=1;
        for (int i=0; i<list.size(); i++) {
        	update = new Update();
            logger.info(list.get(i).getInvoiceNumber());             
            query = new Query();
            query.addCriteria(Criteria.where("invoiceNumber").is(list.get(i).getInvoiceNumber()));
    		//update.set("queueNumber",temp);    
    		update.set("queueNumber",j);    

    		mongoTemplate.updateFirst(query, update, PrivateTree.class);    		
    		j++;
    		logger.info("---------------Successfully updated--------------");
        }
	
	}
	
	public void reOrder() {
		logger.info("-------------------Inside reOrder--------------------------"); 
		Update update=null;// = new Update();
		Query query = new Query();
		query.addCriteria(Criteria.where("userQueueStatus").is("IN"));
		List<Publictree>  list = mongoTemplate.find(query, Publictree.class);		
		logger.info("-------------------Unsorted--------------------------"); 
		for (int i=0; i<list.size(); i++) {
            logger.info(list.get(i).getInvoiceNumber()); 
		}
        Collections.sort(list, new PublicTreeSort());
        logger.info("---------------------Sorted by User Id-------------------------"); 
        int j=1;
        for (int i=0; i<list.size(); i++) {
        	
        	update = new Update();
        	//int temp = list.get(i).getQueueNumber()-1;
        	//logger.info("Update Queue Number-------------->"+temp);
            logger.info(list.get(i).getInvoiceNumber());             
            query = new Query();
            query.addCriteria(Criteria.where("invoiceNumber").is(list.get(i).getInvoiceNumber()));
    		update.set("queueNumber",j);    
    		//mongoTemplate.updateFirst(query, update, Publictree.class);
    		mongoTemplate.updateFirst(query, update, Publictree.class);

    		j++;
    		logger.info("---------------Successfully updated--------------");
        }
	
	}
	
	@Override
	public String updateOutNumber(Publictree publictree,int userID) {
		Update update = new Update();
		Query query = new Query();
		query.addCriteria(Criteria.where("invoiceNumber").is(publictree.getInvoiceNumber()));
		update.set("userQueueStatus", "OUT");
		update.set("queueNumber", 0);	
		update.set("userstatus", "CLOSED");
		//update.set("paymentStatus", 0);
		
		mongoTemplate.updateFirst(query, update, Publictree.class);
		logger.info("-------------------- Re Order start--------------------");
		reOrder();
		logger.info("-------------------- Re Order end--------------------");
		
		   // Save Fresh New Public Tree as Temp table start
		   TempPublicTree temppublictree = new TempPublicTree();
		   temppublictree.setPaymentStatus("NOT PAID");
		   temppublictree.setPayAmount(100);
		   temppublictree.setNumberofUnits(1);
		   temppublictree.setUserID(userID);
		   temppublictree.setCurrency("SGD");
		   
		   RandomNumber randamNumber= randamNumberDAL1.getTempPublicRandomNumber();
		   int curInNumber = randamNumber.getPublicCount();
		   int nextInvoiceNumber = curInNumber+1;	
		   temppublictree.setInvoiceCode(randamNumber.getPublicInvCode() + nextInvoiceNumber);
		   
		   insertTempPublicUser(temppublictree);//.updateRandamNumber(document);	
		   
		   RandomNumber document = new RandomNumber();
		   document.setPublicCount(nextInvoiceNumber);
		   randamNumberDAL1.updateTempPublicRandamNumber(document);
		   
		   // Response 
		   Member member = new Member();
		   member.setInvoiceNumber(temppublictree.getInvoiceCode());
		   member.setTotalAmount(100);
		   UserDetail userdetails = investmentBo1.getMemberEmailID(userID);
  		 
			 if(userdetails.getEmail1().isEmpty()) {
				 logger.info("----------- No Email ID----------------");
			}
			else{
				logger.info("----------- Found Public Closed Email ID----------------"+userdetails.getEmail1());
			    //InvestmentEmail.temp8OutandOnseSavedPublicTree(member,userdetails.getEmail1());
			     
			    //Closed Email Start
				member.setBookingCode(publictree.getInvoiceNumber()); 
				InvestmentEmail.EightComeOneClosedEmail(member,userdetails.getEmail1()); 
				Email.adminunitclosedalertEmail(member);
			}
		   // Save Fresh New Public Tree as Temp table end
		return "";
	}
	
	// Own Tree
	@Override
	public String createOwnTree(OwnTree owntree) {
				mongoTemplate.save(owntree);
				//mongoTemplate.insert(owntree);//(query, RandamNumber.class);
				return "";
			}
			
			@Override
			public List<OwnTree> getSingleOwnPurchaseUnitByUserId(String primaryKey){
				List<OwnTree> list;
				Query query = new Query();
				query.addCriteria(Criteria.where("userID").is(Integer.valueOf(primaryKey)));
				list = mongoTemplate.find(query, OwnTree.class);
				return list;
			}
			public PrivateTree getOwn8ComeOneOut() {
				PrivateTree privateTree=null;
				return privateTree;
			}
			public String updateOwnOutNumber(OwnTree owntree) {
				return "";
			}
	
		// Private Tree
	
		@Override
		public String insertPrivateUser(PrivateTree privatetree) {
		mongoTemplate.save(privatetree);//(query, RandamNumber.class);
		return "";
		}
		
		@Override
		public List<PrivateTree> getSinglePrivatePurchaseUnitByUserId(String primaryKey,String treeName){
			List<PrivateTree> list;
			Query query = new Query();
			query.addCriteria(Criteria.where("userID").is(Integer.valueOf(primaryKey)));
			query.addCriteria(Criteria.where("referanceGSPNumber").is(treeName));
			list = mongoTemplate.find(query, PrivateTree.class);
			return list;
		}
		
		@Override
		public List<OwnTree> loadTreeName(){
			List<OwnTree> list;
			list = mongoTemplate.findAll(OwnTree.class);//.find(query, OwnTree.class);
			return list;
		}
		
		@Override
		public List<TempPublicTree> getTempPublicTree(){
			List<TempPublicTree> list;
			list = mongoTemplate.findAll(TempPublicTree.class);//.find(query, OwnTree.class);
			return list;
		
		}
		
		@Override
		public List<TempPrivateTree> getTempPrivateTree(){
			List<TempPrivateTree> list;
			list = mongoTemplate.findAll(TempPrivateTree.class);//.find(query, OwnTree.class);
			return list;
		
		}
		
		@Override
		public List<TempOwnTree> getTempOwnTree(){
			List<TempOwnTree> list;
			list = mongoTemplate.findAll(TempOwnTree.class);//.find(query, OwnTree.class);
			return list;
		
		}
		
		@Override
		public TempPrivateTree getTempPrivateSingleUnitTree(String invoiceCode){
			TempPrivateTree response=null;
			Query query = new Query();
			query.addCriteria(Criteria.where("invoiceCode").is(invoiceCode));
			response = mongoTemplate.findOne(query, TempPrivateTree.class);
			return response;
		}
		
		@Override
		public TempOwnTree getTempOwnSingleUnitTree(String invoiceCode){
			TempOwnTree response=null;
			Query query = new Query();
			query.addCriteria(Criteria.where("invoiceCode").is(invoiceCode));
			response = mongoTemplate.findOne(query, TempOwnTree.class);
			return response;
		}
		
		
		@Override
		public boolean RemoveTempPrivateSingleUnitTree(TempPrivateTree temptree){
			mongoTemplate.remove(temptree);
			return true;
		}
		
		@Override
		public boolean RemoveTempOwnSingleUnitTree(TempOwnTree temptree){
			mongoTemplate.remove(temptree);
			return true;
		}
		
		
		@Override
		public TempPublicTree getTempPublicSingleUnitTree(String invoiceCode){
			TempPublicTree response=null;
			Query query = new Query();
			query.addCriteria(Criteria.where("invoiceCode").is(invoiceCode));
			response = mongoTemplate.findOne(query, TempPublicTree.class);
			return response;
		}

		// Reject Public Tree
		@Override
		public TempPublicTree rejectPublicUnit(String invoiceCode){
			TempPublicTree response=null;
			Query query = new Query();
			query.addCriteria(Criteria.where("invoiceCode").is(invoiceCode));
			mongoTemplate.remove(query, TempPublicTree.class);
			return response;
		}
		
		// Reject Private
		@Override
		public TempPrivateTree rejectPrivatecUnit(String invoiceCode) {
			TempPrivateTree response=null;
			Query query = new Query();
			query.addCriteria(Criteria.where("invoiceCode").is(invoiceCode));
			mongoTemplate.remove(query, TempPrivateTree.class);
			return response;
		}
		// Reject Own
		@Override
		public TempOwnTree rejectOwnTree(String invoiceCode) {
			TempOwnTree response=null;
			Query query = new Query();
			query.addCriteria(Criteria.where("invoiceCode").is(invoiceCode));
			mongoTemplate.remove(query, TempOwnTree.class);
			return response;
		}

		public TempPublicTree validatePublic(String tempInvoiceNumber) {
			TempPublicTree res=null;
			try {
				Query query = new Query();
				query.addCriteria(Criteria.where("invoiceCode").is(tempInvoiceNumber));
				res = mongoTemplate.findOne(query, TempPublicTree.class);
				if(res != null) {
					System.out.println("-------------Public Match ------");
				}else {
					System.out.println("------ Public Not Match---------------");
				}
				//System.out.println("Output-->"+res.getInvoiceCode());
			}catch(Exception e) {
				e.printStackTrace();
			}
			return res;
		}
		
		public TempPrivateTree validatePrivate(String tempInvoiceNumber) {

			TempPrivateTree res=null;
			try {
				Query query = new Query();
				query.addCriteria(Criteria.where("invoiceCode").is(tempInvoiceNumber));
				res = mongoTemplate.findOne(query, TempPrivateTree.class);
				if(res != null) {
					System.out.println("-------------Private Match ------");
				}else {
					System.out.println("------ Private Not Match---------------");
				}
				//System.out.println("Output-->"+res.getInvoiceCode());
			}catch(Exception e) {
				e.printStackTrace();
			}
			
			
			return res;
		
		}
		public TempOwnTree validateOwn(String tempInvoiceNumber) {


			TempOwnTree res=null;
			try {
				Query query = new Query();
				query.addCriteria(Criteria.where("invoiceCode").is(tempInvoiceNumber));
				res = mongoTemplate.findOne(query, TempOwnTree.class);
				if(res != null) {
					System.out.println("-------------Own Match ------");
				}else {
					System.out.println("------ Own Not Match---------------");
				}
				//System.out.println("Output-->"+res.getInvoiceCode());
			}catch(Exception e) {
				e.printStackTrace();
			}
			
			
			return res;
		
		
		}

		
		
		@Override
		public boolean RemoveTempPublicSingleUnitTree(TempPublicTree temptree){
			mongoTemplate.remove(temptree);
			return true;
		}
		
		@Override
		public String validateOwnTreeName(String primaryKey){
			String uiresponse;
			OwnTree response=null;
			Query query = new Query();
			query.addCriteria(Criteria.where("invoiceCode").is(primaryKey));
			response = mongoTemplate.findOne(query, OwnTree.class);
			if(response!=null) {
				logger.info("Good...");
				uiresponse="Valid";
			}
			else {
				logger.info("Bad...");
				uiresponse="InValid";

			}
			return uiresponse;
		}
		
		
		@Override
		public PrivateTree getPrivate8ComeOneOut() {
			PrivateTree privatetree=null;
			logger.info("-------------------Inside getPrivate8ComeOneOut--------------------------"); 

			//Publictree publictree;
			Query query = new Query();
			query.addCriteria(Criteria.where("userQueueStatus").is("IN"));
			privatetree = mongoTemplate.findOne(query, PrivateTree.class);		
			List<PrivateTree>  list = mongoTemplate.find(query, PrivateTree.class);		
			logger.info("-------------------Unsorted--------------------------"); 
			for (int i=0; i<list.size(); i++) {
	            logger.info(list.get(i).getInvoiceNumber()); 
			}
	      //  Collections.sort(list, new PublicTreeSort()); 
	  
	        logger.info("---------------------Sorted by User Id-------------------------"); 
	        for (int i=0; i<list.size(); i++) {
	            logger.info(list.get(i).getInvoiceNumber()); 
	        }
		
			//return mongoTemplate.find(query, Publictree.class);
		
			return privatetree;
		}
		@Override
		public String updatePrivateOutNumber(PrivateTree privatetree,Member member) {
			Update update = new Update();
			Query query = new Query();
			query.addCriteria(Criteria.where("invoiceNumber").is(privatetree.getInvoiceNumber()));
			update.set("userQueueStatus", "OUT");
			update.set("queueNumber", 0);	
			update.set("userstatus", "CLOSED");
			mongoTemplate.updateFirst(query, update, PrivateTree.class);
			logger.info("-------------------- Re Order start -------------------");
			reOrderPrivateTree(privatetree);
			// Send Email for Closed 
			member.setInvoiceNumber(privatetree.getInvoiceNumber()); 
			Email.EightComeOneClosedEmail(member);
			Email.adminunitclosedalertEmail(member);
			logger.info("---------------- Re Order end ----------------");
			return "";
		
		}

		// Random Number Generate and Update
		
		@Override
		public RandomNumber getAllOwnTreeRandomNumber() {
			 RandomNumber radomNumber=null;
			try {
				logger.info("----------- Inside Own and Private Tree -----------");
				Query query = new Query();
				logger.info("-----------  Before Inside Own and Private Tree addCriteria-----------");
			    query.addCriteria(Criteria.where("randomID").is(2));
				logger.info("-----------  After Inside Own and Private Tree addCriteria-----------");
				radomNumber = mongoTemplate.findOne(query, RandomNumber.class);
				logger.info("-----------  Successfully Inside Own and Private Tree findOne-----------");
				return radomNumber;//mongoTemplate.find(query, RandamNumber.class);//(RandamNumber.class);
			}catch(Exception e) {
				e.printStackTrace();
				return radomNumber;


			}finally {
				
			}
			
		}
		
		
		// Random Number Generate and Update

		@Override
		public OwnTree getAllPrivateTreeRandomNumber(String refID) {
			OwnTree owntree=null;
			logger.info("Referance Number------------>"+refID);
			try {
				logger.info("----------- Inside Only Private Tree -----------");
				Query query = new Query();
				logger.info("-----------  Before Inside Only Private Tree addCriteria-----------");
			   // query.addCriteria(Criteria.where("invocieNumber").is(refID));
			    query.addCriteria(Criteria.where("invoiceCode").is(refID));
			    
				logger.info("-----------  After Inside Only Private Tree addCriteria-----------");
				owntree = mongoTemplate.findOne(query, OwnTree.class);
				logger.info("-----------  Invoice Number -----------"+owntree.getInvocieNumber());
				logger.info("-----------  Successfully Inside Own and Private Tree findOne-----------");
				return owntree;//mongoTemplate.find(query, RandamNumber.class);//(RandamNumber.class);
			}catch(Exception e) {
				e.printStackTrace();
				return owntree;


			}finally {
				
			}
			
		}
		
		
		

		@Override
		public String updateOwnTreeRandomNumber(RandomNumber rn) {
			logger.info("Inside Own Tree currentqueueNumber"+rn.getCurrentqueueNumber());
			logger.info("Inside Own  Tree nextOutqueueNumber"+rn.getNextOutqueueNumber());
			logger.info("Inside Own Tree invocieNumber"+rn.getInvocieNumber());
			
			Query query = new Query();
		    query.addCriteria(Criteria.where("randomID").is(2));
				Update update = new Update();
				update.set("currentqueueNumber", rn.getCurrentqueueNumber());
				if(rn.getNextOutqueueNumber()!=0) {
					update.set("nextOutqueueNumber", rn.getNextOutqueueNumber());

				}
				update.set("invocieNumber", rn.getInvocieNumber());

			mongoTemplate.updateFirst(query, update, RandomNumber.class);//(query, RandamNumber.class);
			return "";//mongoTemplate.find(query, RandamNumber.class);//(RandamNumber.class);
		}
		
		public String updatePrivateTreeRandomNumber(OwnTree rn) {

			logger.info("Invoice Refer Number or Tree Name ---->"+rn.getInvoiceCode());
			logger.info("updatePrivateTreeRandomNumber  currentqueueNumber"+rn.getCurrentqueueNumber());
			logger.info("updatePrivateTreeRandomNumber  nextOutqueueNumber"+rn.getNextOutqueueNumber());
			logger.info("updatePrivateTreeRandomNumber invocieNumber"+rn.getInvocieNumber());
			logger.info("updatePrivateTreeRandomNumber invoiceCOde"+rn.getInvoiceCode());

			
			Query query = new Query();
		    query.addCriteria(Criteria.where("invoiceCode").is(rn.getInvoiceCode()));
				Update update = new Update();
				update.set("currentqueueNumber", rn.getCurrentqueueNumber());
				if(rn.getNextOutqueueNumber()!=0) {
					update.set("nextOutqueueNumber", rn.getNextOutqueueNumber());

				}
				update.set("invocieNumber", rn.getInvocieNumber());

			mongoTemplate.updateFirst(query, update, OwnTree.class);//(query, RandamNumber.class);
			return "";//mongoTemplate.find(query, RandamNumber.class);//(RandamNumber.class);
		
		}
		
		// Server
		private String privatefiles="/home/ec2-user/GGL/PrivatePayment/";
		private String publicfiles="/home/ec2-user/GGL/PublicPayment/";
		private String ownfiles="/home/ec2-user/GGL/OwnPayment/";
		private String minifiles="/home/ec2-user/GGL/MiniPayment/";

		// Local
		/*private String privatefiles="E:\\temp\\PrivatePayment\\";
		private String publicfiles="E:\\temp\\PublicPayment\\";
		private String ownfiles="E:\\temp\\OwnPayment\\";*/

		
		private final Path publicrootLocation = Paths.get(publicfiles);
		private final Path privateRootLocation = Paths.get(privatefiles);
		private final Path ownRootLocation = Paths.get(ownfiles);
		private final Path minirootLocation = Paths.get(minifiles);

		@Override
		public String storeImage(MultipartFile file ,String invoiceNumber,String treeName) {
			System.out.println("Tree Name ---------->"+treeName);
			String fileName=null;
			String status = "Fail";
			try {
				if(invoiceNumber != null) {
					fileName = invoiceNumber + ".jpg";
				}
				else {
					fileName = file.getOriginalFilename();
				}
				
				if(treeName.equalsIgnoreCase("publicTree")){ 
					System.out.println("---- Inside Public Tree Path ---->");
					Files.deleteIfExists(this.publicrootLocation.resolve(fileName));
					Files.copy(file.getInputStream(), this.publicrootLocation.resolve(fileName));
				}else if(treeName.equalsIgnoreCase("privateTree")){
					System.out.println("---- Inside Private Tree Path ---->");
					 Files.deleteIfExists(this.privateRootLocation.resolve(fileName));
					Files.copy(file.getInputStream(), this.privateRootLocation.resolve(fileName));
				}else if(treeName.equalsIgnoreCase("ownTree")){
					System.out.println("---- Inside Own Tree Path ---->");
					 Files.deleteIfExists(this.ownRootLocation.resolve(fileName));
					Files.copy(file.getInputStream(), this.ownRootLocation.resolve(fileName));
				}else if(treeName.equalsIgnoreCase("miniTree")){ 
					System.out.println("---- Inside Mini Tree Path ---->");
					Files.deleteIfExists(this.minirootLocation.resolve(fileName));
					Files.copy(file.getInputStream(), this.minirootLocation.resolve(fileName));
				}
				
				status="Success";
			} catch (Exception e) {
				status="failure";
				System.out.println("Exception -->"+e.getMessage());
				throw new RuntimeException("FAIL!");
			}
			return status;
			
		}
		
		public Stream<Path> loadPublicImage() {
			System.out.println("------ Inside loadPublicImage Method --------");
	        try {
	            return Files.walk(this.publicrootLocation, 1)
	                    .filter(path -> path.equals(this.publicrootLocation))
	                    .map(path -> this.publicrootLocation.relativize(path));
	        } catch (IOException e) {
	        	System.out.println("Exception -->"+e.getMessage());
	            throw new RuntimeException("Failed to read stored files", e);
	        }
	    }
		
		public Stream<Path> loadPrivateImage() {
			System.out.println("------ Inside loadPrivateImage Method --------");
	        try {
	            return Files.walk(this.privateRootLocation, 1)
	                    .filter(path -> path.equals(this.privateRootLocation))
	                    .map(path -> this.privateRootLocation.relativize(path));
	        } catch (IOException e) {
	        	System.out.println("Exception -->"+e.getMessage());
	            throw new RuntimeException("Failed to read stored files", e);
	        }
	    }
		
		public Stream<Path> loadOwnImage() {
			System.out.println("------ Inside loadOwnImage Method --------");
	        try {
	            return Files.walk(this.ownRootLocation, 1)
	                    .filter(path -> path.equals(this.ownRootLocation))
	                    .map(path -> this.ownRootLocation.relativize(path));
	        } catch (IOException e) {
	        	System.out.println("Exception -->"+e.getMessage());
	            throw new RuntimeException("Failed to read stored files", e);
	        }
	    }

		public Resource loadPublicFile(String filename) {
			System.out.println("------- Inside loadfile Method ---------");
			try {
				System.out.println("------- Public Tree root Path ---------"+publicrootLocation);
				Path file = publicrootLocation.resolve(filename);
				System.out.println("------- After Path Called ---------"+file);
				Resource resource = new UrlResource(file.toUri());
				System.out.println("------- After Resources ---------"+resource);
				if (resource.exists() || resource.isReadable()) {
					return resource;
				} else {
					Path file1 = publicrootLocation.resolve("NoImage.jpg"); 
					Resource status = new UrlResource(file1.toUri());
					return status;
					//throw new RuntimeException("FAIL!"); 
				}
			} catch (MalformedURLException e) {
				System.out.println("Exception -->"+e.getMessage());
				throw new RuntimeException("FAIL!");
			}
		}
		
		public Resource loadPrivateFile(String filename) {
			System.out.println("------- Inside loadfile Method ---------");
			try {
				System.out.println("------- Private Tree root Path ---------"+privateRootLocation);
				Path file = privateRootLocation.resolve(filename);
				System.out.println("------- After Path Called ---------"+file);
				Resource resource = new UrlResource(file.toUri());
				System.out.println("------- After Resources ---------"+resource);
				if (resource.exists() || resource.isReadable()) {
					return resource;
				} else {
					Path file1 = privateRootLocation.resolve("NoImage.jpg"); 
					Resource status = new UrlResource(file1.toUri());
					return status;
					//throw new RuntimeException("FAIL!"); 
				}
			} catch (MalformedURLException e) {
				System.out.println("Exception -->"+e.getMessage());
				throw new RuntimeException("FAIL!");
			}
		}
		
		
		public Resource loadOwnFile(String filename) {
			System.out.println("------- Inside loadfile Method ---------");
			try {
				System.out.println("------- Own Tree root Path ---------"+ownRootLocation);
				Path file = ownRootLocation.resolve(filename);
				System.out.println("------- After Path Called ---------"+file);
				Resource resource = new UrlResource(file.toUri());
				System.out.println("------- After Resources ---------"+resource);
				if (resource.exists() || resource.isReadable()) {
					return resource;
				} else {
					Path file1 = ownRootLocation.resolve("NoImage.jpg"); 
					Resource status = new UrlResource(file1.toUri());
					return status;
					//throw new RuntimeException("FAIL!"); 
				}
			} catch (MalformedURLException e) {
				System.out.println("Exception -->"+e.getMessage());
				throw new RuntimeException("FAIL!");
			}
		}
		
		//-------- Member Purchased MiniTree & Saved Into TempMiniTable -------
		@Override
		public String insertTempMiniUser(TempMiniTree tempminitree) 
		{
			mongoTemplate.insert(tempminitree);
			return "";
		}
		
		//-------- Load MiniTree Values -------
		@Override
		public List<TempMiniTree> getTempMiniTree(){
			List<TempMiniTree> list;
			list = mongoTemplate.findAll(TempMiniTree.class);
			return list;
		
		}
		
		//-------- Approve MiniTree Data ---------
		@Override
		public TempMiniTree getTempMiniSingleUnitTree(String invoiceCode){
			TempMiniTree response=null;
			Query query = new Query();
			query.addCriteria(Criteria.where("invoiceCode").is(invoiceCode));
			response = mongoTemplate.findOne(query, TempMiniTree.class);
			return response;
		}
		//---------- Save Data into MiniTree -----
		@Override
		public String insertMiniUser(MiniTree minitree) {
			mongoTemplate.insert(minitree);
			return "";
		}
		
		//---- Get 3 Unit Then 1 out ----
		@Override
		public MiniTree get3ComeOneOut() {
			logger.info("-------------------Inside get3ComeOneOut Calling --------------------------"); 
			MiniTree minitree;
			Query query = new Query();
			query.addCriteria(Criteria.where("userQueueStatus").is("IN"));
			minitree = mongoTemplate.findOne(query, MiniTree.class);		
			List<MiniTree> list = mongoTemplate.find(query, MiniTree.class);		
			logger.info("------------- Unsorted ----------------"); 
			for (int i=0; i<list.size(); i++) {
	            logger.info(list.get(i).getInvoiceNumber()); 
			}
	        Collections.sort(list, new MiniTreeSort()); 
	  
	        logger.info("----------------- Sorted by User Id ----------------------"); 
	        for (int i=0; i<list.size(); i++) {
	            logger.info(list.get(i).getInvoiceNumber()); 
	        }
			return minitree;
		}
		
		//--------- Update 1Unit in TempMiniTree --------
		@Override
		public String updateMiniOutNumber(MiniTree minitree,int userID) {
			Update update = new Update();
			Query query = new Query();
			query.addCriteria(Criteria.where("invoiceNumber").is(minitree.getInvoiceNumber()));
			update.set("userQueueStatus", "OUT");
			update.set("queueNumber", 0);	
			update.set("userstatus", "CLOSED");
			mongoTemplate.updateFirst(query, update, MiniTree.class);
			logger.info("------------- Re Order start --------------");
			reOrderMiniTree();
			logger.info("Closed Status InvoiceNumber --->"+minitree.getInvoiceNumber()); 
			MiniTree minitreeUserID = new MiniTree();
			minitreeUserID = addNewMiniSingleUnitTree(minitree.getInvoiceNumber());
			logger.info("After add New MiniTree in Temp --->"+minitreeUserID.getUserID()); 
			logger.info("---------------- Re Order end -----------------");
			   // Save Fresh New Public Tree as Temp table start
			   TempMiniTree tempminitree = new TempMiniTree();
			   tempminitree.setPaymentStatus("NOT PAID");
			   tempminitree.setPayAmount(100);
			   tempminitree.setNumberofUnits(1);
			   tempminitree.setUserID(minitreeUserID.getUserID());
			   tempminitree.setCurrency("SGD");
			   
			   RandomNumber randamNumber= randamNumberDAL1.getTempMiniRandomNumber();
			   int curInNumber = randamNumber.getMiniCount();
			   int nextInvoiceNumber = curInNumber+1;	
			   tempminitree.setInvoiceCode(randamNumber.getMiniInvCode() + nextInvoiceNumber);
			   
			   insertTempMiniUser(tempminitree);
			   String newInvoiceCode = randamNumber.getMiniInvCode() + nextInvoiceNumber;
			   logger.info("New Invoice -->"+newInvoiceCode); 
			   
			   RandomNumber document = new RandomNumber();
			   document.setMiniCount(nextInvoiceNumber);
			   randamNumberDAL1.updateTempMiniRandamNumber(document);
			   
			   // Response 
			   Member member = new Member();
			   member.setInvoiceNumber(tempminitree.getInvoiceCode());
			   member.setTotalAmount(100);
			   
			   UserDetail userdetails = investmentBo1.getMemberEmailID(userID);
				if(userdetails.getEmail1().isEmpty()) {
					 logger.info("----------- No Email ID----------------");
				}
				else{
					 logger.info("----------- Found Closed Mini Email ID----------------"+userdetails.getEmail1());
					 // Push Email start 
					member.setBookingCode(minitree.getInvoiceNumber()); 
					InvestmentEmail.ThreeComeOneClosedEmail(member,userdetails.getEmail1()); 
					InvestmentEmail.adminminiunitclosedalertEmail(member);
				}
			return newInvoiceCode;
		}
		
		//----- Re-Order MiniTree -----
		public void reOrderMiniTree() {
			logger.info("------------------- Inside reOrderMiniTree --------------------------"); 
			Update update=null;// = new Update();
			Query query = new Query();
			query.addCriteria(Criteria.where("userQueueStatus").is("IN"));
			List<MiniTree>  list = mongoTemplate.find(query, MiniTree.class);		
			logger.info("------------------- Unsorted -----------------------"); 
			for (int i=0; i<list.size(); i++) {
	            logger.info(list.get(i).getInvoiceNumber()); 
			}
	        Collections.sort(list, new MiniTreeSort());
	        logger.info("------------------- Sorted by User Id -------------------------"); 
	        int j=1;
	        for (int i=0; i<list.size(); i++) {
	        	update = new Update();
	            logger.info(list.get(i).getInvoiceNumber());             
	            query = new Query();
	            query.addCriteria(Criteria.where("invoiceNumber").is(list.get(i).getInvoiceNumber()));
	    		update.set("queueNumber",j);    
	    		mongoTemplate.updateFirst(query, update, MiniTree.class);
	    		j++;
	    		logger.info("--------------- Successfully updated --------------");
	        }
		}
		
		@Override
		public MiniTree addNewMiniSingleUnitTree(String invoiceNumber){
			MiniTree response=null;
			Query query = new Query();
			query.addCriteria(Criteria.where("invoiceNumber").is(invoiceNumber));
			response = mongoTemplate.findOne(query, MiniTree.class);
			return response;
		}
		//----- Remove Temp Mini Tree ---------
		@Override
		public boolean RemoveTempMiniSingleUnitTree(TempMiniTree temptree){
			mongoTemplate.remove(temptree);
			return true;
		}
		
		//------ Reject MiniTree -------
		@Override
		public TempMiniTree rejectMiniUnit(String invoiceCode){
			TempMiniTree response=null;
			Query query = new Query();
			query.addCriteria(Criteria.where("invoiceCode").is(invoiceCode));
			mongoTemplate.remove(query, TempMiniTree.class);
			return response;
		}
		
		//----- upload image on MiniTree Path ----------
		public TempMiniTree validateMini(String tempInvoiceNumber) {
			TempMiniTree res=null;
			try {
				Query query = new Query();
				query.addCriteria(Criteria.where("invoiceCode").is(tempInvoiceNumber));
				res = mongoTemplate.findOne(query, TempMiniTree.class);
				if(res != null) {
					System.out.println("--------- Mini Match ------");
				}else {
					System.out.println("------ Mini Not Match -----------");
				}
			}catch(Exception e) {
				e.printStackTrace();
			}	
			return res;
		}
		
		//------ Get Image on MiniTree --------
		public Stream<Path> loadMiniImage() {
			System.out.println("------ Inside loadMiniImage Method --------");
	        try {
	            return Files.walk(this.minirootLocation, 1)
	                    .filter(path -> path.equals(this.minirootLocation))
	                    .map(path -> this.minirootLocation.relativize(path));
	        } catch (IOException e) {
	        	System.out.println("MiniTree Exception -->"+e.getMessage());
	            throw new RuntimeException("Failed to read stored files", e);
	        }
	    }
		
		//------- Get File Name on MiniTree -----
		public Resource loadMiniFile(String filename) {
			System.out.println("------- Inside loadMiniFile Method ---------");
			try {
				System.out.println("------- Mini Tree root Path ---------"+minirootLocation);
				Path file = minirootLocation.resolve(filename);
				System.out.println("------- After Path Called ---------"+file);
				Resource resource = new UrlResource(file.toUri());
				System.out.println("------- After Resources ---------"+resource);
				if (resource.exists() || resource.isReadable()) {
					return resource;
				} else {
					Path file1 = minirootLocation.resolve("NoImage.jpg"); 
					Resource status = new UrlResource(file1.toUri());
					return status;
				}
			} catch (MalformedURLException e) {
				System.out.println("Exception -->"+e.getMessage());
				throw new RuntimeException("FAIL!");
			}
		}
		
		//--------- Get MiniTree UnitStatus Details ------
		@Override
		public List<MiniTree> getSinglePurchaseMiniUnitByUserId(String primaryKey) {
			List<MiniTree> list;
			Query query = new Query();
			query.addCriteria(Criteria.where("userID").is(Integer.valueOf(primaryKey)));
			list = mongoTemplate.find(query, MiniTree.class);
			return list;
		}
		
		/*@Override
		public List<TempPublicTree> getPublicInvoiceNumber() {
			List<TempPublicTree> list;
			list = mongoTemplate.findAll(TempPublicTree.class);
			return list;
		}*/
}
