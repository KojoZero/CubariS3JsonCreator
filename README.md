## Before you start anything you need to setup Amazon S3 and get your account access key
To do so follow step 1 (only step 1) of this guide (skip the Apache Maven part): https://docs.aws.amazon.com/sdk-for-java/latest/developer-guide/get-started.html#get-started-setup


If you're not experienced with how to use Cubari Gists, I recommend you read this reddit post to get an idea of how it works: https://www.reddit.com/r/manga/comments/mcicbp/sl_how_to_host_a_series_on_imgur_with_guyamoe/

The main info to get from that post is how to use github for Cubari and how to add more chapters to your github file. 

## Before running this program, make sure to install at least Java 11: https://www.oracle.com/java/technologies/javase-jdk11-downloads.html
Here's how your S3 Bucket and manga series need to be setup
You need to create an S3 Bucket (MAKE SURE THAT All THE FOLDERS ARE PUBLIC ACCESS)

Take your manga files on your computer and make sure that they are in this format:
```
--<Manga Series Name (this is the prefix)e>
	--<Vol.X>
			--<Chapter X - Name of the Chapter>
				--<Image X.jpg>
				--<image X.png>
				--<Image X.jpg>
```


The manga series folder should have its volume folder, and each volume should contain its chapters, and each chapter should contain all the images of that chapter.

If you use the format: Chapter 53 - The Next Namekian, this code will rename it as: The Next Namekian (which is chapter 53)
If you don't specifically use Chapter and use something else (like Chapitre), it won't automatically rename for you.

If you don't want to do it by chapter but rather by volume, just put all the images in a single chapter like so:
```
--<Manga Series Name>
		--<Vol.X>
				--<Vol X>
					--<Image X>
					--<image X>
					--<Image X>
```

## Upload this manga to your bucket.

To run this Cubari S3 Json creator open Command Prompt (if you're on Windows) or Terminal (If you're on Mac or Linux)
Type "cd " (without quotations, with the space) and  drag the CubariS3JsonCreator folder to the CommandPrompt/Terminal Window. Press enter/return
Then type "java -jar " (without quotations, with the space), and drag the CubariS3JsonCreator.jar file to the CommandPrompt/Terminal Window. Press enter/return.

There, it will ask you the name of the prefix. This is the <Manga Series Name> with "/" added to it.
Then enter your bucket name.
Next It'll ask you for your base url. You can find this by clicking on a random jpg and looking at the "Object URL". Copy it from "https://" to ".com". (DO NOT COPY ANYTHING AFTER ".com" OR THIS PROGRAM WILL FAIL)
Then follow the instructions in the terminal window. 
(Once you reach the paste a link to the thumbnail part, if it might looks like the program froze, don't worry. In actuality, it's getting all the potentially thousands of image links that you have within each chapter in each volume)

Once it says that the Json file was successfully created, open the CubariS3JsonOutput.json file.
Go to the bottom and remove the last comma.
Now you can copy (Ctrl+A and Ctrl+C) the json contents and paste it to your github file.
