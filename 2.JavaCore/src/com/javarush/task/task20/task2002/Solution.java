package com.javarush.task.task20.task2002;

import com.sun.org.apache.xpath.internal.SourceTree;

import java.io.*;
import java.util.*;

/* 
Читаем и пишем в файл: JavaRush
*/
public class Solution {
    public static void main(String[] args) {

        try {
            File your_file_name = new File("D:\\1.txt");
            OutputStream outputStream = new FileOutputStream(your_file_name);
            InputStream inputStream = new FileInputStream(your_file_name);

            JavaRush javaRush = new JavaRush();
            User user = new User();
            user.setFirstName("Alexey");
            user.setLastName("Zheludov");
            user.setBirthDate(new GregorianCalendar(1990, 12, 25).getTime());
            user.setCountry(User.Country.RUSSIA);
            user.setMale(true);

            javaRush.users.add(user);

            javaRush.save(outputStream);
            outputStream.flush();

            JavaRush loadedObject = new JavaRush();
            loadedObject.load(inputStream);
            System.out.println("javaRush equals loadedObject: " + javaRush.equals(loadedObject));

            outputStream.close();
            inputStream.close();

        } catch (IOException e) {
            System.out.println("Oops, something wrong with my file");
        } catch (Exception e) {
            System.out.println("Oops, something wrong with save/load method");
        }
    }

    public static class JavaRush {
        public List<User> users = new ArrayList<>();

        public void save(OutputStream outputStream) throws Exception {
            DataOutputStream toFile = new DataOutputStream(outputStream);

            toFile.writeInt(users.size());
            for (User user : users) {
                String firstName = (user.getFirstName() == null) ? "null" : user.getFirstName();
                toFile.writeUTF(firstName);
                String lastName = (user.getLastName() == null) ? "null" : user.getLastName();
                toFile.writeUTF(lastName);
                toFile.writeLong(user.getBirthDate().getTime());
                toFile.writeUTF(user.getCountry().name());
                toFile.writeBoolean(user.isMale());
            }
            toFile.flush();
        }

        public void load(InputStream inputStream) throws Exception {
            DataInputStream fromFile = new DataInputStream(inputStream);

            int usersCount = fromFile.readInt();
            for (int i = 0; i < usersCount; i++) {
                User user = new User();
                String firstName = fromFile.readUTF();
                if (firstName.equals("null")) firstName = null;
                user.setFirstName(firstName);
                String lastName = fromFile.readUTF();
                if (lastName.equals("null")) lastName = null;
                user.setLastName(lastName);
                user.setBirthDate(new Date(fromFile.readLong()));
                user.setCountry(User.Country.valueOf(fromFile.readUTF()));
                user.setMale(fromFile.readBoolean());
                
                users.add(user);
            }
            fromFile.close();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            JavaRush javaRush = (JavaRush) o;

            return users != null ? users.equals(javaRush.users) : javaRush.users == null;

        }

        @Override
        public int hashCode() {
            return users != null ? users.hashCode() : 0;
        }
    }
}
