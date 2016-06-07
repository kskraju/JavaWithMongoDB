import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

public class MongoDBExample {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MongoClient mongo = null;
		try {
			// Default MONGODB port is : 27017
			mongo = new MongoClient( "127.0.0.1" , 27017 );
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		DB db = mongo.getDB("test");
		//DBCollection table = db.getCollection("user");
		
		DBCollection collection = db.getCollection("test");
		//DBCollection table = db.getCollection("test");

		DBCursor cursor = collection.find();

		while (cursor.hasNext()) {
			System.out.println(cursor.next());
		}
		
		// Insert data into DB
		insertIntoMongoDB(collection);
		
		System.out.println("Insertion completed .............. ");
		//Select all document from a collection
        selectAllRecordsFromACollection(collection);
        
        System.out.println("Select all records completed .............. ");
        //Select first document in collection
        selectFirstRecordInCollection(collection);
         
        System.out.println("Select First record completed .............. ");
        //Select single document and single field based on record number
        selectSingleRecordAndFieldByRecordNumber(collection);
         
        //Select all documents where record number = n
        selectAllRecordByRecordNumber(collection);
         
        //In example
        in_Example(collection);
         
        //Less than OR greater than example
        lessThan_GreaterThan_Example(collection);
         
        //Select document where record number != n
        negation_Example(collection);
         
        //And logical comparison query example
        andLogicalComparison_Example(collection);
         
        //Select documents based on regex match LIKE example
        regex_Example(collection);
	}
	/**
	 *  { "_id" : { "$oid" : "56df335630a9f73114d0bfb0"} , "id" : "1" , "name" : "sreekanth" , "city" : "Fremont"}
		{ "_id" : { "$oid" : "56df336530a9f73114d0bfb1"} , "id" : "2" , "name" : "prakash" , "city" : "Fremont"}
		{ "_id" : { "$oid" : "56df337930a9f73114d0bfb2"} , "id" : "3" , "name" : "srinivas" , "city" : "Dallas"}
		{ "_id" : { "$oid" : "56df338b30a9f73114d0bfb3"} , "id" : "4" , "name" : "murali" , "city" : "Fremont"}
		{ "_id" : { "$oid" : "56df33a630a9f73114d0bfb4"} , "id" : "5" , "name" : "kiran" , "city" : "DesMoinse"}
		{ "_id" : { "$oid" : "56df33ba30a9f73114d0bfb5"} , "id" : "6" , "name" : "krishna" , "city" : "Sunnyvale"}
		{ "_id" : { "$oid" : "56df33ca30a9f73114d0bfb6"} , "id" : "7" , "name" : "krishnan" , "city" : "Walnut Creek"}
		{ "_id" : { "$oid" : "56df3a6661d6b850231d4528"} , "id" : "8" , "name" : "abcd" , "city" : "SFO"}
		{ "_id" : { "$oid" : "56df3a7b61d6b850231d4529"} , "id" : "9" , "name" : "bcde" , "city" : "San Jose"}
	 * 
	 * 
	 * */
	
	private static void insertIntoMongoDB(DBCollection collection){
		List<DBObject> list = new ArrayList<DBObject>();

		Calendar cal = Calendar.getInstance();

		for (int i = 1; i <= 5; i++) {

			BasicDBObject data = new BasicDBObject();
			data.append("id", i);
			data.append("name", "sreekanth-" + i);
			data.append("city", "city-" + i);
			// data.append("date", cal.getTime());

			// +1 day
			cal.add(Calendar.DATE, 1);

			list.add(data);

		}

		collection.insert(list);
	}
	private static void selectFirstRecordInCollection(DBCollection collection) {
	    DBObject dbObject = collection.findOne();
	    System.out.println(dbObject);
	}
	
	private static void selectAllRecordsFromACollection(DBCollection collection) {
	    DBCursor cursor = collection.find();
	    while(cursor.hasNext()) {
	        System.out.println(cursor.next());
	    }
	}
	
	private static void selectSingleRecordAndFieldByRecordNumber(DBCollection collection) {
	    BasicDBObject whereQuery = new BasicDBObject();
	    whereQuery.put("id", 5);
	    BasicDBObject fields = new BasicDBObject();
	    fields.put("id", 1);
	  
	    DBCursor cursor = collection.find(whereQuery, fields);
	    while (cursor.hasNext()) {
	        System.out.println(cursor.next());
	    }
	}
	
	private static void selectAllRecordByRecordNumber(DBCollection collection) {
	    BasicDBObject whereQuery = new BasicDBObject();
	    whereQuery.put("id", 5);
	    DBCursor cursor = collection.find(whereQuery);
	    while(cursor.hasNext()) {
	        System.out.println(cursor.next());
	    }
	}
	
	private static void in_Example(DBCollection collection) {
	    BasicDBObject inQuery = new BasicDBObject();
	    List<Integer> list = new ArrayList<Integer>();
	    list.add(2);
	    list.add(4);
	    list.add(5);
	    inQuery.put("id", new BasicDBObject("$in", list));
	    DBCursor cursor = collection.find(inQuery);
	    while(cursor.hasNext()) {
	        System.out.println(cursor.next());
	    }
	}

	private static void lessThan_GreaterThan_Example(DBCollection collection) {
	    BasicDBObject getQuery = new BasicDBObject();
	    getQuery.put("id", new BasicDBObject("$gt", 2).append("$lt", 5));
	    DBCursor cursor = collection.find(getQuery);
	    while(cursor.hasNext()) {
	        System.out.println(cursor.next());
	    }
	}
	
	// Not in clause
	private static void negation_Example(DBCollection collection) {
	    BasicDBObject neQuery = new BasicDBObject();
	    neQuery.put("id", new BasicDBObject("$ne", 4));
	    DBCursor cursor = collection.find(neQuery);
	    while(cursor.hasNext()) {
	        System.out.println(cursor.next());
	    }
	}
	
	private static void andLogicalComparison_Example(DBCollection collection) {
	    BasicDBObject andQuery = new BasicDBObject();
	    List<BasicDBObject> obj = new ArrayList<BasicDBObject>();
	    obj.add(new BasicDBObject("id", 2));
	    obj.add(new BasicDBObject("name", "sreekanth"));
	    andQuery.put("$and", obj);
	  
	    System.out.println(andQuery.toString());
	  
	    DBCursor cursor = collection.find(andQuery);
	    while (cursor.hasNext()) {
	        System.out.println(cursor.next());
	    }
	}
	
	private static void regex_Example(DBCollection collection) {
	    BasicDBObject regexQuery = new BasicDBObject();
	    regexQuery.put("name", 
	        new BasicDBObject("$regex", "sree[3]")
	        .append("$options", "i"));
	  
	    System.out.println(regexQuery.toString());
	  
	    DBCursor cursor = collection.find(regexQuery);
	    while (cursor.hasNext()) {
	        System.out.println(cursor.next());
	    }
	}
}
