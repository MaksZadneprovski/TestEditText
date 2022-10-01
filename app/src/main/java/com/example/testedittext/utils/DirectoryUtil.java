package com.example.testedittext.utils;

import android.content.Context;

import com.example.testedittext.entities.ReportEntity;


import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class DirectoryUtil {

    public static ArrayList<ReportEntity> getReportEntityList(Context context) {
        ArrayList<ReportEntity> reportEntityList = new ArrayList<>();
        String reportPath = context.getExternalFilesDir(null).toString();

        File rootDir = new File(reportPath);
        List<String> result = new ArrayList<>();
        Queue<File> fileTree = new PriorityQueue<>();

        Collections.addAll(fileTree, rootDir.listFiles());

        while (!fileTree.isEmpty())
        {
            File currentFile = fileTree.remove();
            if(currentFile.isDirectory()){
                reportEntityList.add(new ReportEntity(currentFile.getName()));
                Collections.addAll(fileTree, currentFile.listFiles());
            } else {
                System.out.println("File "+ currentFile.getName());
                result.add(currentFile.getAbsolutePath());
            }
        }

        return reportEntityList;
    }

    public static boolean deleteDirectory(File directoryToBeDeleted) {
        File[] allContents = directoryToBeDeleted.listFiles();
        if (allContents != null) {
            for (File file : allContents) {
                deleteDirectory(file);
            }
        }
        return directoryToBeDeleted.delete();
    }
}
