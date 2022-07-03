package org.example.rest;

import org.example.rest.configuration.MyConfig;
import org.example.rest.entity.User;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MyConfig.class);

        Communication communication = context.getBean("communication", Communication.class);

        List<User> allUsers = communication.getAllUsers();
        System.out.println(allUsers);

        User user = new User(3l, "James", "Brown", (byte) 34);
        communication.saveUser(user);
        User user1 = new User(3l,"Thomas", "Shelby", (byte)34);
        communication.updateUser(user1);
//        User userReceivedByID = communication.getOneUser(1L);
//        System.out.println(userReceivedByID);

//        User user = new User(5L, "Sveta", "Sokolova", (byte)23);
//        communication.saveUser(user);

    }
}
