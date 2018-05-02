# Transformers-lab

# project checkout:
```
git clone https://github.com/ebhun00/Transformers-lab.git
```

# project build
```gradle
gradlew.bat clean eclipse  -- download dependencies and setup workspace
gradlew.bat build -- to generate application jar in build folder
gradlew.bat bootRun  -- to run application in local
```

# project details
```link
checkout "documentation" folder
https://github.com/ebhun00/Transformers-lab/blob/master/documentation/ETL-Replacement/ETL_replacement.pptx
```


# Mongo DB installation and running mongo DB

DB software copy is available in Dev host: dg01064d:/apps/software/
Using below command  you can copy to desired host: Below example to copy from **Dev** to **QA**

``` 
ssh user@dg01064d
cd /apps/software
scp -r mongodb-linux-x86_64-rhel70-3.6.0-rc0 ii00wl@qg01064e:/apps/software/
```
# Make Mongo available as global , edit ~./bashrc and add below export command
```
export PATH=$PATH:/apps/software/mongodb-linux-x86_64-rhel70-3.6.0-rc0/bin
```

#To start DB process in background :

```
nohup mongod -port  27017 -dbpath=data/db &
```

# deploy application jar from local to remote server:

	Move to local folder where app jar is available , example : cd /drives/c/customInfo/development/exp/Transformers-lab/build/libs
	scp ETL-transformer-0.0.1.jar ii00wl@qg01064e:/apps/scope/custom/
	nohup java -jar ETL-transformer-0.0.1.jar &
	
	
	

# Swagger Url-QA:	
```
http://qg01064e.safeway.com:8081/swagger-ui.html#
```

# Currently Application has 2 dormants
1.	XmlApproach / DBUpdate approach, currently XmlApproach is disabled, DBUpdate is active , and can be switched with out app downtime.
2.	Flag to process for specific stores, we can add more store in runtime by this endpoints : 
```
http://qg01064e.safeway.com:8081/app-config/key/stores/value/1713,1502
```
	
# Files has to drop in below folder:

```
host: qg01064e.safeway.com
path: /apps/scope/custom/inbound
Folders
read : to receive input files
processed : successfully processed files moved to this folder
failed: Failed processed files moved to this folder
```

#Log level changes


**Log level to info:**

```
  http://localhost:8081/loglevel/info?package=com.titan
  ```
**Log level to debug:** 

```
http://localhost:8081/loglevel/debug?package=com.titan
```

# Order sequence logic:
![alt text](https://github.com/ebhun00/Transformers-lab/blob/master/documentation/ETL-Replacement/order_sequence_design.jpg)
