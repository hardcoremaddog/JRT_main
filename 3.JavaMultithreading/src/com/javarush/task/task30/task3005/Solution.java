package com.javarush.task.task30.task3005;

import java.util.ArrayList;
import java.util.List;

/* 
Такие хитрые исключения!
*/
public class Solution {
    public static void main(String[] args) {
        checkAFlag(new D());
    }

    public static void checkAFlag(D d) {
        if (d != null && //1. проверка, что d, пришедший в качестве параметра не null.
                d.cs != null && //2. проверка, что d.cs не null.
                !d.cs.isEmpty() &&  //3. проверка, что d.cs содержит хотя бы один элемент.
                d.cs.get(0).bs != null && //4. проверка, что d.cs.get(0).bs не null.
                !d.cs.get(0).bs.isEmpty() && //5 проверка, что d.cs.get(0).bs содержит хотя бы один элемент.
                d.cs.get(0).bs.get(0).as != null && //6. проверка, что d.cs.get(0).bs.get(0).as не null.
                !d.cs.get(0).bs.get(0).as.isEmpty() && //7. проверка, что d.cs.get(0).bs.get(0).as.
                d.cs.get(0).bs.get(0).as.get(0).flag) { { //8. проверка, что d.cs.get(0).bs.get(0).as.get(0).flag = true.
                System.out.println("A's flag is true");
            }
        } else { //all other cases
            System.out.println("Oops!");
        }
    }

    static class A {
        boolean flag = true;
    }

    static class B {
        List<A> as = new ArrayList<>();

        {
            as.add(new A());
        }
    }

    static class C {
        List<B> bs = new ArrayList<>();

        {
            bs.add(new B());
        }
    }

    static class D {
        List<C> cs = new ArrayList<>();

        {
            cs.add(new C());
        }
    }
}
