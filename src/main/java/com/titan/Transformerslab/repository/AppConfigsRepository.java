package com.titan.Transformerslab.repository;


import com.titan.Transformerslab.domain.AppConfigs;

public interface AppConfigsRepository  {
	AppConfigs findByKey(String key);
}
