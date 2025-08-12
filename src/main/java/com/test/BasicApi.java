package com.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Random;

@SpringBootApplication
public class BasicApi{
    public static void main(String[] args){
        SpringApplication.run(BasicApi.class, args);
    }

}

class Person {
    private String name;
    private int age;

    public Person() {}

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    // Add these getters
    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }
}

@RestController
class InnerBasicApi {
    @RequestMapping("/{name}")
    public Person hello(@PathVariable("name")String name){
        Random random = new Random();
        int age = random.nextInt(100);
        return new Person(name, age);
    }
}

