TweetsViewer
============

Tweets Viewer on Google Maps with Markers and Heat Maps

Team Name : Tech Infinity

Members : (1) Vishal Vyas      ( vnv2102@columbia.edu )
		  (2) Utkarsha Prakash ( up2127@columbia.edu  )
		  
----------------
Features :
----------------
1). Shows Tweets taken live from tweeter on the maps.
2). Tweets are clustered based on ZOOM Level. More you zoom in, the more detailed markers are shown else on zoom out we sow a clustered marker
3). Tweets can also be filtered and seen on map. A drop down provided for same
4). Tweets also further rendered on Heat Map for easier visualisation
5). Tweets persisted in Mongo DB for better Performance event for Large dataset
6). Ajax + REST API used to avoid page refreshes 

----------------
Technology Stack
----------------
Java : for back-end of Project (persistence and querying of data)
JAX-RS : Support for REST added using JAX RS Java API
Mongo DB : For data persistence
HTML + CSS : Client Pages 
JavaScript : for handling client side Map and Marker rendering 
JQuery  : for sending AJAX request to server


----------------
--- LIVE URL ---- Deployed on EC2---- 
----------------
http://columbiatweets-env.elasticbeanstalk.com/html/tweetMaps.html

----------------
--- Demo Video
----------------
https://www.youtube.com/watch?v=NvUf4JljzdU

----------------
--- Github Code URL
----------------
https://github.com/Vishal-N-Vyas/TweetsViewer
