package com.javarush.task.task31.task3106;

import java.io.*;
import java.util.*;
import java.util.zip.ZipInputStream;

/*
Разархивируем файл
*/
public class Solution {

    public static void main(String[] args) throws IOException {
        if (args.length < 2) return;

        String resultFileName = args[0];
        int filePartCount = args.length - 1;
        String[] fileNamePart = new String[filePartCount];
        for (int i = 0; i < filePartCount; i++) {
            fileNamePart[i] = args[i + 1];
        }
        Arrays.sort(fileNamePart);

        List<FileInputStream> fisList = new ArrayList<>();
        for (int i = 0; i < filePartCount; i++) {
            fisList.add(new FileInputStream(fileNamePart[i]));
        }
        SequenceInputStream seqInStream = new SequenceInputStream(Collections.enumeration(fisList));
        ZipInputStream zipInStream = new ZipInputStream(seqInStream);
        FileOutputStream fileOutStream = new FileOutputStream(resultFileName);
        byte[] buf = new byte[1024 * 1024];
        while (zipInStream.getNextEntry() != null) {
            int count;
            while ((count = zipInStream.read(buf)) != -1) {
                fileOutStream.write(buf, 0, count);
            }
        }
        seqInStream.close();
        zipInStream.close();
        fileOutStream.close();
    }
}


    //hardcoremaddog main
//    public static void main(String[] args) throws IOException {
//
//        String resultFileName = args[0];
//        String fullArcName = args[1].substring(0, args[1].lastIndexOf("."));
//        final byte[] BUFFER = new byte[1024 * 1024];
//
//        Set<String> sortedArgs = new TreeSet<>(Arrays.asList(args).subList(1, args.length));
//
//        ByteArrayOutputStream fullArcByteArrayStream = new ByteArrayOutputStream();
//
//        for (String arg : sortedArgs) {
//            try (FileInputStream fileInputStream = new FileInputStream(arg)) {
//                int count;
//                while ((count = fileInputStream.read(BUFFER)) != -1) {
//                    fullArcByteArrayStream.write(BUFFER, 0, count); //сборка частей архива
//                }
//            }
//        }
//
//        try (FileOutputStream fileOutputStream = new FileOutputStream(fullArcName)) {
//            fileOutputStream.write(fullArcByteArrayStream.toByteArray()); //запись всех частей архива в один архив
//        }
//
//        try (ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(fullArcName))) {
//            while (zipInputStream.getNextEntry() != null) {
//                File newFile = new File(resultFileName);
//                try (FileOutputStream fileOutputStream = new FileOutputStream(newFile)) {
//                    int count;
//                    while ((count = zipInputStream.read(BUFFER)) != -1) {
//                        fileOutputStream.write(BUFFER, 0, count); //распаковка архива в файл
//                    }
//                }
//            }
//        }
//    }
//}