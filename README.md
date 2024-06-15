# Run it locally
To run this backend locally, clone it to your pc and open it with Intellij or any other IDE.

The fastest way would be to import these environment variables to your Intellij run configuration and change the database credentials if needed and create the `rss_pos` db.

```env
API_COMMENT_URL=http://localhost:8080;
API_UPDATE_FREQUENCY=30;
DATABASE_DDL_AUTO=none;
DATABASE_PASSWORD=postgres;
DATABASE_URL=jdbc:postgresql://localhost:5432/rss_pos;
DATABASE_USERNAME=postgres
```

Alternatively, you can configure all of this yourself:
Navigate to the [application.properties](src/main/resources/application.properties) file and either replace all the environment variables with hard coded value or set these environment variables yourself.
You will also have to set the API_COMMENT_URL defined in the [UserCommentInitializer.java](src/main/java/at/kaindorf/rssbackend/db/UserCommentInitializer.java) to any value.
The last value API_UPDATE_FREQUENCY used in [RssUpdater.java](src/main/java/at/kaindorf/rssbackend/db/RssUpdater.java) will have to be set to any number in seconds (preferrably 30 seconds)

# RSS Backend
--------

Hello People! 
This is the Backend repo for "RSS".
It's really sophisticated!
-------

## Really Sophisticated Story Feed

DB Name: 
