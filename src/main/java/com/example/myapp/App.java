package com.example.myapp;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.regex.Pattern;

import com.example.myapp.folders.ChapterDir;
import com.example.myapp.folders.Image;
import com.example.myapp.folders.SeriesDir;
import com.example.myapp.folders.VolumeDir;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.model.*;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.paginators.ListObjectsV2Iterable;


public class App {

    public static void main(String[] args) throws IOException {

        Scanner scan = new Scanner(System.in);
        Region region = Region.US_WEST_1;
        S3Client s3 = S3Client.builder().region(region).build();

        String baseUrl = "https://cubaricoloredmanga.s3-us-west-1.amazonaws.com";
        String bucketName = "cubaricoloredmanga";
        String prefixName = "Dragon Ball Super";
        String key = "key";
        String chapterScheme;
                //Chapter \w\d\s-
        int sortingAlgorithm;
        //
        System.out.println("What is the name of the prefix (ex. Bleach/, Naruto/)");
        prefixName = scan.nextLine();
        System.out.println("What is the name of the Bucket");
        bucketName = scan.nextLine();
        System.out.println("Give the base url of your S3 storage (ex. https://bucketname.region.amazonaws.com)");
        baseUrl = scan.nextLine();
        System.out.println("Choose which sorting algorithm to use [1 for default, 2 for alternative](if you crash using the default try the alternative");
        sortingAlgorithm = scan.nextInt();
        scan.nextLine();
        //The meat of the program
        List<S3Object> BucketObjectList = listAllBucketObjects(s3, bucketName, prefixName);
        if (sortingAlgorithm == 1){
            Collections.sort(BucketObjectList,new AlphaNumericComparator());
        } else if (sortingAlgorithm == 2){
            Collections.sort(BucketObjectList,new NaturalOrderComparator());
        } else {
            Collections.sort(BucketObjectList,new AlphaNumericComparator());
        }
        System.out.println("Closing the connection to Amazon S3");
        s3.close();
        System.out.println("Connection closed");
        System.out.println("Exiting...");
        ArrayList<Image> imageLibrary = new ArrayList<Image>();
        ArrayList<SeriesDir> seriesLibrary = new ArrayList<SeriesDir>();
        ArrayList<VolumeDir> volumeLibrary = new ArrayList<VolumeDir>();
        ArrayList<ChapterDir> chapterLibrary = new ArrayList<ChapterDir>();

        String groupName = "";
        String mangaThumbnail = "";
        String mangaName, mangaAuthor, mangaArtist, mangaDescription;
        mangaName = mangaAuthor = mangaArtist = mangaDescription = "";
        java.io.File json = new java.io.File("CubariS3JsonOutput.json");
        if (!json.exists()) {
            json.createNewFile();
        }
        long updatedTime = System.currentTimeMillis() / 1000L;
        PrintWriter pw = new PrintWriter(json);

        System.out.println("What is your group name");
        groupName = scan.nextLine();
        System.out.println("What is the full name of the manga");
        mangaName = scan.nextLine();
        System.out.println("Who is the author of the manga");
        mangaAuthor = scan.nextLine();
        System.out.println("Who is the artist of the manga");
        mangaArtist = scan.nextLine();
        System.out.println("Give a description of the manga");
        mangaDescription = scan.nextLine();
        System.out.println("Paste a link to the thumbnail you want to show on the main page");
        mangaThumbnail = scan.nextLine();

        int globalSeriesIndex = 0;
        int globalVolumeIndex = 0;
        int globalChapterIndex = 0;
        //Splits Name data to only retrieve images (which all have four parameters) and add them to an imageLibrary
        for (int i = 0; i < BucketObjectList.size(); i++) {
            /*
            String[] stringArray = BucketObjectList.get(i).key().split("[/.]", 0);
            if (stringArray.length == 5) {

             */
            String[] stringArray = BucketObjectList.get(i).key().split("/", 0);
            if (stringArray.length == 4)
                imageLibrary.add(new Image(stringArray[0], stringArray[1], stringArray[2], stringArray[3]));
        }
        //Generates Series Directory
        for (int i = 0; i < imageLibrary.size(); i++){
            String imageSeriesName = imageLibrary.get(i).getSeriesName();
            int imageSeriesMatch = 0;
            if (seriesLibrary.size() > 0){
                for (int ii = 0; ii < seriesLibrary.size(); ii++){
                    if (seriesLibrary.get(ii).getSeriesName().equals(imageSeriesName)){
                        imageSeriesMatch++;
                    }
                }
                if (imageSeriesMatch == 0){
                    seriesLibrary.add(new SeriesDir(imageSeriesName, globalSeriesIndex));
                    globalSeriesIndex++;
                }
            } else {
                seriesLibrary.add(new SeriesDir(imageSeriesName, globalSeriesIndex));
                globalSeriesIndex++;
            }
        }
        for (int i = 0; i < imageLibrary.size(); i++){
            String imageSeriesName = imageLibrary.get(i).getSeriesName();
            String imageVolumeName = imageLibrary.get(i).getVolumeName();
            int imageVolumeMatch = 0;
            //System.out.println(imageLibrary.get(990).getVolumeName());
            if (volumeLibrary.size() > 0){
                for (int ii = 0; ii < volumeLibrary.size(); ii++){
                    if (volumeLibrary.get(ii).getVolumeName().equals(imageVolumeName)){
                        imageVolumeMatch++;
                    }
                }
                if (imageVolumeMatch == 0){
                    volumeLibrary.add(new VolumeDir(imageVolumeName, imageSeriesName, globalVolumeIndex));
                    globalVolumeIndex++;
                }
            } else {
                volumeLibrary.add(new VolumeDir(imageVolumeName, imageSeriesName, globalVolumeIndex));
                globalVolumeIndex++;
            }
        }
        for (int i = 0; i < imageLibrary.size(); i++){
            String imageSeriesName = imageLibrary.get(i).getSeriesName();
            String imageVolumeName = imageLibrary.get(i).getVolumeName();
            String imageChapterName = imageLibrary.get(i).getChapterName();
            int imageChapterMatch = 0;
            //System.out.println(imageLibrary.get(990).getVolumeName());
            if (chapterLibrary.size() > 0){
                for (int ii = 0; ii < chapterLibrary.size(); ii++){
                    if (chapterLibrary.get(ii).getChapterName().equals(imageChapterName)){
                        imageChapterMatch++;
                    }
                }
                if (imageChapterMatch == 0){
                    chapterLibrary.add(new ChapterDir(imageChapterName, imageSeriesName, imageVolumeName, globalChapterIndex));
                    globalChapterIndex++;
                }
            } else {
                chapterLibrary.add(new ChapterDir(imageChapterName, imageSeriesName, imageVolumeName, globalVolumeIndex));
                globalChapterIndex++;
            }
        }

        for (int i = 0; i < volumeLibrary.size(); i++){
            for (int ii = 0; ii < seriesLibrary.size(); ii++){
              if (volumeLibrary.get(i).getVolumeSeries().equals(seriesLibrary.get(ii).getSeriesName())){
                  seriesLibrary.get(ii).addToVolumeDirs(volumeLibrary.get(i));
              }
            }
        }
        for (int i = 0; i < chapterLibrary.size(); i++){
            for (int ii = 0; ii < seriesLibrary.size(); ii++){
                for (int iii = 0; iii < seriesLibrary.get(ii).getVolumeDirs().size(); iii++){
                    if (chapterLibrary.get(i).getChapterVolume().equals(seriesLibrary.get(ii).getVolumeDirs().get(iii).getVolumeName())){
                        seriesLibrary.get(ii).getVolumeDirs().get(iii).addToChapterDirs(chapterLibrary.get(i));
                    }
                }
            }
        }
        for (int i = 0; i < imageLibrary.size(); i++){
            for (int ii = 0; ii < seriesLibrary.size(); ii++){
                for (int iii = 0; iii < seriesLibrary.get(ii).getVolumeDirs().size(); iii++){
                    for (int iiii = 0; iiii < seriesLibrary.get(ii).getVolumeDirs().get(iii).getChapterDirs().size(); iiii++){
                        if (imageLibrary.get(i).getChapterName().equals(seriesLibrary.get(ii).getVolumeDirs().get(iii).getChapterDirs().get(iiii).getChapterName())){
                            seriesLibrary.get(ii).getVolumeDirs().get(iii).getChapterDirs().get(iiii).addToImageDirs(imageLibrary.get(i));
                        }
                    }
                }
            }
        }
        //System.out.println(baseUrl+"/"+imageLibrary.get(90).getSeriesName()+"/"+imageLibrary.get(90).getVolumeName()+"/"+imageLibrary.get(90).getChapterName()+"/"+imageLibrary.get(90).getImageName());
        System.out.println("Finished Loading Image Library");


        pw.println("{\n\t\"title\": \"" + mangaName + "\",\n" +
                "\t\"description\": \"" + mangaDescription + "\",\n" +
                "\t\"artist\": \"" + mangaArtist + "\",\n" +
                "\t\"author\": \"" + mangaAuthor + "\",\n" +
                "\t\"cover\": \"" + mangaThumbnail + "\",\n" +
                "\t\"chapters\": {");
        int globalChapterNumber = 1;
        for (int i = 0; i < seriesLibrary.get(0).getVolumeDirs().size(); i++) {
            for (int ii = 0; ii < seriesLibrary.get(0).getVolumeDirs().get(i).getChapterDirs().size(); ii++) {
                pw.println("\t\t\"" + (globalChapterNumber) + "\": {");
                String tempChapterName = seriesLibrary.get(0).getVolumeDirs().get(i).getChapterDirs().get(ii).getChapterName();
                String pattern = "(Chapter.*- )";
                tempChapterName = tempChapterName.replaceAll(pattern, "");
                pw.println("\t\t\t\"title\": \"" + tempChapterName + "\",\n" +
                        "\t\t\t\"volume\": \"" + (seriesLibrary.get(0).getVolumeDirs().get(i).getVolumeIndex()+1) + "\",\n" +
                        "\t\t\t\"groups\": {\n" +
                        "\t\t\t\t\"" + groupName + "\": [");
                for (int iii = 0; iii < (seriesLibrary.get(0).getVolumeDirs().get(i).getChapterDirs().get(ii).getImageDirs().size() - 1); iii++) {
                    String imgUrl = "\t\t\t\t\t\""+ baseUrl + "/" + prefixName + seriesLibrary.get(0).getVolumeDirs().get(i).getVolumeName() + "/" + seriesLibrary.get(0).getVolumeDirs().get(i).getChapterDirs().get(ii).getChapterName() + "/" + seriesLibrary.get(0).getVolumeDirs().get(i).getChapterDirs().get(ii).getImageDirs().get(iii).getImageName() + "\",";
                    imgUrl = imgUrl.replace("#","%23");
                    imgUrl = imgUrl.replace("%", "%25");
                    imgUrl = imgUrl.replace(" ","+");
                    pw.println(imgUrl);
                }
                String imgUrl = "\t\t\t\t\t\"" + baseUrl + "/" + prefixName  + seriesLibrary.get(0).getVolumeDirs().get(i).getVolumeName() + "/" + seriesLibrary.get(0).getVolumeDirs().get(i).getChapterDirs().get(ii).getChapterName() + "/" + seriesLibrary.get(0).getVolumeDirs().get(i).getChapterDirs().get(ii).getImageDirs().get(seriesLibrary.get(0).getVolumeDirs().get(i).getChapterDirs().get(ii).getImageDirs().size() - 1).getImageName() + "\"";
                imgUrl = imgUrl.replace("#","%23");
                imgUrl = imgUrl.replace("%", "%25");
                imgUrl = imgUrl.replace(" ","+");
                pw.println(imgUrl);
                pw.println("\t\t\t\t]");
                pw.println("\t\t\t},");
                pw.println("\t\t\t\"last_updated\": \"" + updatedTime + "\"");
                pw.println("\t\t},");
                globalChapterNumber++;
            }
        }
        pw.println("\t}\n" +
                "}");
        pw.close();
        //String newJson = Files.readString(Path.of("CubariS3JsonOutput.json"));
        //newJson = newJson.replace("},\\n\\t}", "}\\n\\t}");
        //PrintWriter dw = new PrintWriter(json);
        //dw.print(newJson);
        //dw.close();
        System.out.println("\n\nJson File Successfully Created (Check JavaDriveOutput.json)");
        System.out.println("MAKE SURE TO REMOVE THE LAST COMMA");

    }
    public static List<S3Object> listAllBucketObjects(S3Client s3, String bucketName, String prefixName){
        ListObjectsV2Request request = ListObjectsV2Request.builder().bucket(bucketName).prefix(prefixName).build();
        ListObjectsV2Iterable response = s3.listObjectsV2Paginator(request);
        List<S3Object> objectsArrayList = new ArrayList<S3Object>();

        for (ListObjectsV2Response page : response) {
            page.contents().forEach((S3Object object) -> {
                objectsArrayList.add(object);
                // TODO: Consume `object` the way you need
            });
        }
        return objectsArrayList;
    }
    public static List<S3Object> listBucketObjects(S3Client s3, String bucketName ) {
        List<S3Object> objects = null;
        try {
            ListObjectsRequest listObjects = ListObjectsRequest
                    .builder()
                    .bucket(bucketName)
                    .build();

            ListObjectsResponse res = s3.listObjects(listObjects);
            objects = res.contents();
            return (objects);
        } catch (S3Exception e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            System.exit(1);
        }

        return objects;
    }
}

//Do something along these lines
            /*
            int paranthesisMockup = 0;
            for (int i = 0; i < objects.size(); i++){
                if (paranthesisMockup = 1){
                    if (paranthesisMockup = 2) {
                        if (paranthesisMockup = 3) {
                                //You are an image. You get added to the image database
                                //You have your chapterName
                                //All the images with the same chapterName get added to a single chapter
                                //Volume name = the vol. Name of a single image (they all belong to the same anyways
                                //Take the Series name of a single image from a single chapter, from a single vol. to assign
                                //Make sure everything is sorted out.
                        } else {
                            //You are a chapter
                        }
                    } else {
                        //You are a volume
                    }
                } else {
                    //You are a series
                }

            }

             */

//Required Regex
//find "\/"