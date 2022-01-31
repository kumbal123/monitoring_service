# Endpoints monitoring service
This monitoring service does not meet all of the conditions stated in the given
Java/Kotlin task. The service does not include or work with:
- Users - therefor no authentication/authorization
- Model validations
- Microservices architecture style
- Different monitoring intervals

Also there are some issues after editing (PUT) or deleting (DELETE) a 
MonitoredEndpoint because there are two processes (monitoring urls and responding to http calls) that are working with the same
collection of data at the same time. Unfortunately I was not able the come up with a good solution. To recreate 
the issue create few MonitoredEndpoints and delete them all or 
edit them all.

## How to start the service
Navigate to the projects folder and start the service with the following command:
```
docker-compose up
```

This will set up and start MySql db and monitoring service. In case of restarting use the command:
```
docker-compose down
docker-compose up
``` 

If the service will not start for some reason using docker
you can run
```
mvn clean sintall
mvn spring-boot:run 
``` 
However this requires a local running MySql db 

## How to use the service
To use the service you can use an API platform such as Postman or client URL. 
As an example with curl you can:

### Manage MonitoredEndpoints

#### 1. Create new MonitoredEndpoint
```
curl -X 'POST' \
  'http://localhost:6868/endpoints/' \
  -H 'accept: application/json' \
  -H 'Content-Type: application/json' \
  -d '{
      "name": "endpoint1",
      "url": "https://petstore.swagger.io/v2/pet/1"
}'
```

#### 2. Read existing MonitoredEndpoints
```
curl -X 'GET' \
  'http://localhost:6868/endpoints/1' \
  -H 'accept: application/json'
```

#### 3. Edit existing MonitoredEndpoints
```
curl -X 'PUT' \
  'http://localhost:6868/endpoints/1' \
  -H 'accept: application/json' \
  -H 'Content-Type: application/json' \
  -d '{
      "id": 1,
      "name": "endpoint11",
      "url": "https://petstore.swagger.io/v2/pet/11"
}'
```

#### 4. Delete existing MonitoredEndpoints
```
curl -X 'DELETE' \
  'http://localhost:6868/endpoints/1' \
  -H 'accept: application/json'
```

#### 5. View last ten monitored results for particular monitored url
```
curl -X 'GET' \
  'http://localhost:6868/endpoints/1/results' \
  -H 'accept: application/json'
```

### View MonitoringResults

#### 1. Read existing MonitoringResult
```
curl -X 'GET' \
  'http://localhost:6868/results/1' \
  -H 'accept: application/json'
```

#### 2. Read all existing MonitoringResults
```
curl -X 'GET' \
  'http://localhost:6868/results/' \
  -H 'accept: application/json'
```



