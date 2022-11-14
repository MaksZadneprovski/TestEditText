package com.example.testedittext.utils;


import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Queue;

public class DirectoryUtil {
//    public static String currentDirectory;
//
//    public static ArrayList<File> getReportList(String path) {
//        // path = context.getExternalFilesDir(null).toString();
//        File rootDir = new File(path);
//        ArrayList<File> result = new ArrayList<>();
//        Queue<File> fileTree = new PriorityQueue<>();
//
//        Collections.addAll(fileTree, Objects.requireNonNull(rootDir.listFiles()));
//
//        while (!fileTree.isEmpty()) {
//            File currentFile = fileTree.remove();
//            if(currentFile.isDirectory()){
//                result.add(currentFile);
//                //Collections.addAll(fileTree, currentFile.listFiles());
//            } else {
//                //System.out.println(currentFile.getAbsolutePath());
//            }
//        }
//        return result;
//    }
//
//    public static boolean deleteDirectory(File directoryToBeDeleted) {
//        File[] allContents = directoryToBeDeleted.listFiles();
//        if (allContents != null) {
//            for (File file : allContents) {
//                deleteDirectory(file);
//            }
//        }
//        return directoryToBeDeleted.delete();
//    }
//
//    public static String getCurrentFolder(){
//        String title = currentDirectory.substring(currentDirectory.lastIndexOf("/") + 1);
//        return title;
//    }
}
