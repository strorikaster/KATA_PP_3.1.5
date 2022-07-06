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



        User user = new User(3L,"James", "Brown", (byte) 34);

        communication.saveUser(user);

        user.setId(3L);
        user.setName("Thomas");
        user.setLastName("Shelby");
        user.setAge((byte)34);
        communication.updateUser(user);

        communication.deleteUser(3L);

    }
}
