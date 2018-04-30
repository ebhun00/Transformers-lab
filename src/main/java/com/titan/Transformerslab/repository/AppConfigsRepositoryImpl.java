package com.titan.Transformerslab.repository;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.titan.Transformerslab.domain.AppConfigs;

@Component
public class AppConfigsRepositoryImpl implements AppConfigsRepository {


	@Autowired
    MongoTemplate mongoTemplate;
	
	@Override
	public AppConfigs findByKey(String key) {
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(key));
		return (AppConfigs) mongoTemplate.findOne(query, AppConfigs.class);
	}
	
	public void saveAppConfig(AppConfigs appConfig) {
		mongoTemplate.save(appConfig);
		System.out.println("Saved");
	}

	public List<AppConfigs> find() {
		return  (List<AppConfigs>) mongoTemplate.findAll(AppConfigs.class);
	}

}
