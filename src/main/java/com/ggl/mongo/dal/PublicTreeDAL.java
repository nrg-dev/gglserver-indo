package com.ggl.mongo.dal;

import java.util.List;

import com.ggl.dto.Member;
import com.ggl.mongo.model.MiniTree;
import com.ggl.mongo.model.OwnTree;
import com.ggl.mongo.model.PrivateTree;
import com.ggl.mongo.model.Publictree;
import com.ggl.mongo.model.RandomNumber;
import com.ggl.mongo.model.TempMiniTree;
import com.ggl.mongo.model.TempOwnTree;
import com.ggl.mongo.model.TempPrivateTree;
import com.ggl.mongo.model.TempPublicTree;

import java.nio.file.Path;
//import java.util.List;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;
public interface PublicTreeDAL {

	// Member Purchased Public Tree & Saved Into Temp Table
	public String insertTempPublicUser(TempPublicTree temppublictree);
	// Member Purchased Private Tree Based on Own Tree number & Saved Into Temp Table
	public String insertTempPrivateTreeUser(TempPrivateTree temprivate);
	// Member Purchased Own Tree & Saved Into Temp Table
	public String insertTempOwnTreeUser(TempOwnTree tempowntree);	
	// Load the Purchased Public Tree from the Temp Table
	public List<TempPublicTree> getTempPublicTree();
	// Load the Purchased Private Tree from the Temp Table
	public List<TempPrivateTree> getTempPrivateTree();
	// Load the Purchased Own Tree from the Temp Table
	public List<TempOwnTree> getTempOwnTree();
	// Get the Single Private Tree Info Based on Temp Invoice Number
	public TempPrivateTree getTempPrivateSingleUnitTree(String invoiceCode);
	// Get the Single Own Tree Info Based on Temp Invoice Number
	public TempOwnTree getTempOwnSingleUnitTree(String invoiceCode);

	public TempPublicTree validatePublic(String tempInvoiceNumber);
	public TempPrivateTree validatePrivate(String tempInvoiceNumber);
	public TempOwnTree validateOwn(String tempInvoiceNumber);

	// Reject Temp Public Units
	public boolean RemoveTempPublicSingleUnitTree(TempPublicTree temptree);
	// Reject Temp Private Units
	public boolean RemoveTempPrivateSingleUnitTree(TempPrivateTree temptree);
	// Reject Temp Own Units
	public boolean RemoveTempOwnSingleUnitTree(TempOwnTree temptree);
	
	
	public TempPublicTree getTempPublicSingleUnitTree(String invoiceCode);

	public String insertUser(Publictree publictree);
	public List<Publictree> getSinglePurchaseUnitByUserId(String primaryKey);
	public Publictree get8ComeOneOut();
	public String updateOutNumber(Publictree publictree, int userID);

	// Private Tree
	
	public String insertPrivateUser(PrivateTree privatetree);
	public List<PrivateTree> getSinglePrivatePurchaseUnitByUserId(String primaryKey,String treeName);	
	public String validateOwnTreeName(String primaryKey);
	public PrivateTree getPrivate8ComeOneOut();
	public String updatePrivateOutNumber(PrivateTree privatetree,Member member);

	// Own Tree

	public String createOwnTree(OwnTree owntree);
	public List<OwnTree> getSingleOwnPurchaseUnitByUserId(String primaryKey);
	public PrivateTree getOwn8ComeOneOut();
	public String updateOwnOutNumber(OwnTree owntree);
	public List<OwnTree> loadTreeName();

	
	// For Own Tree Random Number 
	public  RandomNumber getAllOwnTreeRandomNumber();
	// For update Own Tree Random Number
	public String updateOwnTreeRandomNumber(RandomNumber rn);
	
	// For Private member Random 
	public OwnTree getAllPrivateTreeRandomNumber(String refID);
	// For update Own Tree Random Number
	public String updatePrivateTreeRandomNumber(OwnTree rn);
	
	public String storeImage(MultipartFile file ,String invoiceNumber,String treeName);
	public Stream<Path> loadPublicImage();
	public Stream<Path> loadPrivateImage();
	public Stream<Path> loadOwnImage();
	public Resource loadPublicFile(String filename);
	public Resource loadPrivateFile(String filename);
	public Resource loadOwnFile(String filename);
	
	// Reject Public Tree
	public TempPublicTree rejectPublicUnit(String invoiceCode);
	public TempPrivateTree rejectPrivatecUnit(String invoiceCode);
	public TempOwnTree rejectOwnTree(String invoiceCode);
	
	//-------- Member Purchased MiniTree & Saved Into TempMiniTable -------
	public String insertTempMiniUser(TempMiniTree tempminitree);
	public List<TempMiniTree> getTempMiniTree();
	public TempMiniTree getTempMiniSingleUnitTree(String invoiceCode);
	public String insertMiniUser(MiniTree minitree);
	public MiniTree get3ComeOneOut();
	public String updateMiniOutNumber(MiniTree miniTree, int userID);
	public boolean RemoveTempMiniSingleUnitTree(TempMiniTree temptree);
	public TempMiniTree rejectMiniUnit(String invoiceCode);
	//------- Store Image on Path -----
	public TempMiniTree validateMini(String tempInvoiceNumber);
	//-------- Get Image on MiniTree ------
	public Stream<Path> loadMiniImage();
	public Resource loadMiniFile(String filename);
	//------- Get MiniUnit Status Details ------
	public List<MiniTree> getSinglePurchaseMiniUnitByUserId(String primaryKey);
	//public List<TempPublicTree> getPublicInvoiceNumber();
	public MiniTree addNewMiniSingleUnitTree(String invoiceNumber);  

}