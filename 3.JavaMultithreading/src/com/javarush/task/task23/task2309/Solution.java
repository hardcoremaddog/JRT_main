package com.javarush.task.task23.task2309;

import com.javarush.task.task23.task2309.vo.*;

import java.util.List;

//Требования:

//        6. В методе getUsers должен быть создан объект класса AbstractDbSelectExecutor с параметром типа User.
//        7. В методе getUsers должен быть создан объект класса AbstractDbSelectExecutor с параметром типа Location.
//        8. В методе getUsers должен быть создан объект класса AbstractDbSelectExecutor с параметром типа Server.
//        9. В методе getUsers должен быть создан объект класса AbstractDbSelectExecutor с параметром типа Subject.
//        10. В методе getUsers должен быть создан объект класса AbstractDbSelectExecutor с параметром типа Subscription.
//        11. Метод getUsers должен возвращать корректный список(в соответствии с условием задачи).
//        12. Метод getLocations должен возвращать корректный список(в соответствии с условием задачи).
//        13. Метод getServers должен возвращать корректный список(в соответствии с условием задачи).
//        14. Метод getSubjects должен возвращать корректный список(в соответствии с условием задачи).
//        15. Метод getSubscriptions должен возвращать корректный список(в соответствии с условием задачи).
public class Solution {

    public List<Location> getLocations() {
        return new AbstractDbSelectExecutor<Location>() {
            public String getQuery() {
                return "SELECT * FROM LOCATION";
            }
        }.execute();

    }

    public List<Server> getServers() {
        return new AbstractDbSelectExecutor<Server>() {
            public String getQuery() {
                return "SELECT * FROM SERVER";
            }
        }.execute();
    }

    public List<Subject> getSubjects() {
        return new AbstractDbSelectExecutor<Subject>() {
            public String getQuery() {
                return "SELECT * FROM SUBJECT";
            }
        }.execute();
    }

    public  List<Subscription> getSubscriptions() {
        return new AbstractDbSelectExecutor<Subscription>() {
            public String getQuery() {
                return "SELECT * FROM SUBSCRIPTION";
            }
        }.execute();
    }

    public List<User> getUsers() {
        return new AbstractDbSelectExecutor<User>() {

            public String getQuery() {
                return "SELECT * FROM USER";
            }

        }.execute();

    }




    public static void main(String[] args) {
        Solution solution = new Solution();
        print(solution.getUsers());
        print(solution.getLocations());
        print(solution.getSubscriptions());
    }

    public static void print(List list) {
        String format = "Id=%d, name='%s', description=%s";
        for (Object obj : list) {
            NamedItem item = (NamedItem) obj;
            System.out.println(String.format(format, item.getId(), item.getName(), item.getDescription()));
        }
    }
}
