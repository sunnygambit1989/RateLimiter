# RateLimiter

RATE LIMITER APP
SUNDAY, JUNE 21, 2021
VERSION 0.1
























Table of Contents
1.1.	Approach	3
1.2.	Data Storage	3
1.3.	Assumptions:	3
1.4.	Additional Improvements:	3
1.5.	How To run Rate-limiter Application:	4
2.1	Request for ‘Rate-Limiter Developers API can be sent as follows:	4
2.1	Developers API ‘Success call’	5
2.2	Developer API ‘Rate-Limt Capacity Exhausted’	5



















1.1.	Approach

To implement the rate limiter. I have chosen Bucket4j library. I could also choose Resilience4j but it best suited for circuit breaker (source: https://reflectoring.io/).
Reason to choose Bucket4j:
1)	The documentation of Bucket4j is well explained.
2)	Support community is very large for Bucket4j.
3)	Easy to implement and extendable too.

1.2.	Data Storage

In current implementation, we have used the in-memory storage. It is not suitable for distributed system rather Redis or any permanent storage must be preferred.
1.3.	Assumptions:

1)	The user is properly authenticated.
2)	We do not have any context path for our application.

1.4.	Additional Improvements:
We are using userid+url combination to throttle the request but this approach has its own limitation.
Right Approach to implement the rate limiting is combination of IP-Address+user-id+url.
Improvements can be done:
1)	We can create different implementation of rate limiting algorithms like: Fixed Window Algorithm.
2)	We can use permanent storage and cache some active user with LRU cache strategy.
3)	We can also implement database sharding based on user-id.
4)	There are so many things we can improve(We can discuss while going through the code)


1.5.	  How To run Rate-limiter Application:
Before running the Application Some important Points:
There are two users registered with us:
User-id	Rate Limit capacity	End Point	Refill Rate
1	2	/api/v1/developers	2 minutes
1	4	/api/v1/organizations	1 second
2	3	/api/v1/developers	1 second
2	2	/api/v1/organizations
	1 minute

Below are the steps to run RateLimiter:
Step 1: Import the project in Intellij/Eclipse.
Step 2: Start RateLimiterApplication class

Step: 3:  There are two rest end points
1)	http://localhost:8080/api/v1/developers          ( Get Request)
2)	http://localhost:8080/ /api/v1/organizations     ( Get Request)

Hit any rest Rest end point from Postman and set header value as below:
 

Step 4: end

2.1	Request for ‘Rate-Limiter Developers API can be sent as follows:

Request Type	get
Request Header	user-id
Request URL	http://localhost:8080/api/v1/developers

Request Body	

2.1	  Developers API ‘Success call’
                
HTTP Response Code	200
Response Body	[
    "sunny",
    "Amit"
]


2.2	Developer API ‘Rate-Limt Capacity Exhausted’
                
       
HTTP Response Code	429
Response Body	{
    "timestamp": "2021-06-20T19:03:13.518+00:00",
    "status": 429,
    "error": "Too Many Requests",
    "path": "/api/v1/developers"
}
	




