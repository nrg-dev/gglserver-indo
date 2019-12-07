package com.ggl.mongo.dal;

//import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.core.query.Update;

import com.ggl.mongo.model.MiniTree;
import com.ggl.mongo.model.RandomNumber;


@Repository
public class RandomNumberImpl implements RandomNumberDAL {

	public static final Logger logger = LoggerFactory.getLogger(RandomNumberImpl.class);

	@Autowired
	private MongoTemplate mongoTemplate;

	

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

	@Override
	public RandomNumber getAllRandamNumber() {
		 RandomNumber radamNumber=null;
		try {
			logger.info("----------- Inside getAllRandamNumber-----------");
			Query query = new Query();
			logger.info("-----------  Before addCriteria-----------");
		    query.addCriteria(Criteria.where("randomID").is(1));
			logger.info("-----------  After addCriteria-----------");
			radamNumber = mongoTemplate.findOne(query, RandomNumber.class);
			logger.info("-----------  Successfully findOne-----------"+radamNumber.getInvocieNumber());
			return radamNumber;//mongoTemplate.find(query, RandamNumber.class);//(RandamNumber.class);
		}catch(Exception e) {
			e.printStackTrace();
			return radamNumber;


		}finally {
			
		}
		
	}

	
	// Temp Public Random Number
	@Override
	public RandomNumber getTempPublicRandomNumber() {
		 RandomNumber radamNumber=null;
		try {
			logger.info("----------- Inside getAllRandamNumber-----------");
			Query query = new Query();
			logger.info("-----------  Before addCriteria-----------");
		    query.addCriteria(Criteria.where("randomID").is(3));
			logger.info("-----------  After addCriteria-----------");
			radamNumber = mongoTemplate.findOne(query, RandomNumber.class);
			logger.info("-----------  Public Tree Invoice Number -----------"+radamNumber.getPublicCount());
			logger.info("-----------  Public Tree Invoice Code-----------"+radamNumber.getPublicInvCode());


			return radamNumber;//mongoTemplate.find(query, RandamNumber.class);//(RandamNumber.class);
		}catch(Exception e) {
			e.printStackTrace();
			return radamNumber;


		}finally {
			
		}
		
	}
	
	
	
	
	@Override
	public String updateRandamNumber(RandomNumber rn) {
		logger.info("currentqueueNumber"+rn.getCurrentqueueNumber());
		logger.info("nextOutqueueNumber"+rn.getNextOutqueueNumber());
		logger.info("invocieNumber"+rn.getInvocieNumber());
		
		Query query = new Query();
	    query.addCriteria(Criteria.where("randomID").is(1));
			Update update = new Update();
			update.set("currentqueueNumber", rn.getCurrentqueueNumber());
			if(rn.getNextOutqueueNumber()!=0) {
				update.set("nextOutqueueNumber", rn.getNextOutqueueNumber());

			}
			update.set("invocieNumber", rn.getInvocieNumber());

		mongoTemplate.updateFirst(query, update, RandomNumber.class);//(query, RandamNumber.class);
		return "";//mongoTemplate.find(query, RandamNumber.class);//(RandamNumber.class);
	}
	
	
	// Update Temp Public Tree
	@Override
	public String updateTempPublicRandamNumber(RandomNumber rn) {
		logger.info("randomID"+rn.getRandomID());
		logger.info("Public Count"+rn.getPublicCount());
		logger.info("Public Code"+rn.getPublicInvCode());
		
		Query query = new Query();
	    query.addCriteria(Criteria.where("randomID").is(3));
			Update update = new Update();
			update.set("publicCount", rn.getPublicCount());
		mongoTemplate.updateFirst(query, update, RandomNumber.class);//(query, RandamNumber.class);
		return "";//mongoTemplate.find(query, RandamNumber.class);//(RandamNumber.class);
	}
	
	
	// Update Temp Public Tree
	@Override
	public String updateTempPrivateRandamNumber(RandomNumber rn) {
		logger.info("randomID"+rn.getRandomID());
		logger.info("Public Count"+rn.getPublicCount());
		logger.info("Public Code"+rn.getPublicInvCode());
		
		Query query = new Query();
	    query.addCriteria(Criteria.where("randomID").is(3));
			Update update = new Update();
			update.set("privateCount", rn.getPrivateCount());
		mongoTemplate.updateFirst(query, update, RandomNumber.class);//(query, RandamNumber.class);
		return "";//mongoTemplate.find(query, RandamNumber.class);//(RandamNumber.class);
	}
	
	
	// Update Temp Public Tree
	@Override
	public String updateTempOwnTreeandamNumber(RandomNumber rn) {
		logger.info("randomID"+rn.getRandomID());
		logger.info("Own Code"+rn.getOwnInvCode());
		logger.info("Own Count"+rn.getOwnCount());
		
		Query query = new Query();
	    query.addCriteria(Criteria.where("randomID").is(3));
			Update update = new Update();
			update.set("ownCount", rn.getOwnCount());
		mongoTemplate.updateFirst(query, update, RandomNumber.class);//(query, RandamNumber.class);
		return "";//mongoTemplate.find(query, RandamNumber.class);//(RandamNumber.class);
	}
	
	
	
	@Override
	public String updateRandamNumberForOwnTree(RandomNumber rn) {
		logger.info("Own Tree currentqueueNumber"+rn.getCurrentqueueNumber());
		logger.info("Own Tree nextOutqueueNumber"+rn.getNextOutqueueNumber());
		logger.info("Own Tree invocieNumber"+rn.getInvocieNumber());
		
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
	
	//----------- TempMiniTree Random Number ----------
	@Override
	public RandomNumber getTempMiniRandomNumber() {
		 RandomNumber radamNumber=null;
		try {
			logger.info("--------- Inside getTempMiniRandomNumber ---------");
			Query query = new Query();
			logger.info("----------- Before addCriteria -----------");
		    query.addCriteria(Criteria.where("randomID").is(3));
			logger.info("----------- After addCriteria -----------");
			radamNumber = mongoTemplate.findOne(query, RandomNumber.class);
			logger.info("----------- Mini Tree Invoice Number -----------"+radamNumber.getMiniCount());
			logger.info("----------- Mini Tree Invoice Code-----------"+radamNumber.getMiniInvCode());

			return radamNumber;
		}catch(Exception e) {
			e.printStackTrace();
			return radamNumber;
		}finally {
			
		}
	}
	
	//----------- Update TempMiniTree ---------
	@Override
	public String updateTempMiniRandamNumber(RandomNumber rn) {
		logger.info("Mini RandomID -->"+rn.getRandomID());
		logger.info("Mini Count -->"+rn.getMiniCount());
		logger.info("Mini Code -->"+rn.getMiniInvCode());
		
		Query query = new Query();
	    query.addCriteria(Criteria.where("randomID").is(3));
		Update update = new Update();
		update.set("miniCount", rn.getMiniCount());
		mongoTemplate.updateFirst(query, update, RandomNumber.class);
		return "";
	}
	
	@Override
	public String updateMiniRandamNumber(RandomNumber rn) {
		logger.info("Mini CurrentqueueNumber -------->"+rn.getCurrentqueueNumber());
		logger.info("Mini NextOutqueueNumber ------>"+rn.getMininextOutqueueNumber());
		logger.info("Mini InvocieNumber -------->"+rn.getInvocieNumber());
		
		Query query = new Query();
	    query.addCriteria(Criteria.where("randomID").is(4));
		Update update = new Update();
		update.set("currentqueueNumber", rn.getCurrentqueueNumber());
		if(rn.getMininextOutqueueNumber()!=0) {
			update.set("mininextOutqueueNumber", rn.getMininextOutqueueNumber());
		}
		update.set("invocieNumber", rn.getInvocieNumber());

		mongoTemplate.updateFirst(query, update, RandomNumber.class);
		return "";
	}
	
	
	@Override
	public RandomNumber getAllMiniRandamNumber() {
		 RandomNumber radamNumber=null;
		try {
			logger.info("----------- Inside getAllMiniRandamNumber-----------");
			Query query = new Query();
			logger.info("-----------  Before addCriteria-----------");
		    query.addCriteria(Criteria.where("randomID").is(4));
			logger.info("-----------  After addCriteria-----------");
			radamNumber = mongoTemplate.findOne(query, RandomNumber.class);
			logger.info("-----------  Successfully findOne-----------"+radamNumber.getInvocieNumber());
			return radamNumber;
		}catch(Exception e) {
			e.printStackTrace();
			return radamNumber;


		}finally {
			
		}	
	}
	
	@Override
	public MiniTree getLastMiniQueueNumber() {
		MiniTree miniNumber=null;
		try {
			logger.info("----------- Inside getLastMiniQueueNumber-----------");
			Query query = new Query();
			logger.info("-----------  Before Mini LastQueueNumber addCriteria-----------");
			query.limit(1);
			query.with(new Sort(Sort.Direction.DESC, "queueNumber"));
			logger.info("-----------  After addCriteria-----------");
			miniNumber = mongoTemplate.findOne(query, MiniTree.class);
			logger.info("-----------  Successfully find LastOne -----------"+miniNumber.getQueueNumber());
			return miniNumber;
		}catch(Exception e) {
			e.printStackTrace();
			return miniNumber;
		}finally {
			
		}
	}
	
	
	
}
