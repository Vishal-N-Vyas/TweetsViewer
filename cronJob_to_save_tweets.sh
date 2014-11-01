#!/bin/bash
# gets the Tweets from Twitter and saves to database.
# for demo purpose we are getting only tweets for 5 mins every hours
# however below timing can be configured as well.

java -cp  /home/ec2-user/vnv2102/TweetSaveAndPersist.jar com.example.helloworld.core.TweetGetAndPersist | tee /var/www/TweetSaveAndPersist.hourly.log &
sleep 4
echo '-------------------------------'
echo 'closing Tweet Persister'
echo '-------------------------------'
ps aux | grep TweetSaveAndPersist.jar

pkill -f 'TweetSaveAndPersist'
sleep 2

echo ''
echo '-------------------------------'
echo 'closed Tweet Saving Job'
echo '-------------------------------'
ps aux | grep TweetSaveAndPersist.jar

echo '-------------------------------'
echo 'Exiting'
echo '-------------------------------'