###### Music Albums Manager 

This project is a web app built with Spring Boot. <br>
Main purpose of this project is for me to learn Spring framework and improve java coding skills.
Second goal is to help my father manage his old vinyls and CDs collection, which he already started to catalogue in 
excel sheet (not very effective way to do this so not many were added :-) )
To achieve that goal I created MVC web app with Spring Boot, where user can add his albums, and for my father the most 
important was possibility to import albums from the sheet to app's database.
This functionality was added with the Apache POI interface.<br>
Next thing which helps adding albums to collection is a feature that populates album data with "release id" from Discogs 
service with use of its REST API. Of course when such import doesn't perfectly suit our needs there is always an option 
to update that info with our own input. 