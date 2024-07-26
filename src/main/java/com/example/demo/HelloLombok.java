package com.example.demo;

import lombok.Getter;
import lombok.Setter;

// @RequiredArgsConstructor
@Getter
@Setter
public class HelloLombok {
    final private String hello;
    final private int lombok;

    public HelloLombok(String hello, int lombok) {
        this.hello = hello;
        this.lombok = lombok;
    }


    public static void main(String[] args) {
        // HelloLombok helloLombok = new HelloLombok();
        // helloLombok.setHello("헬로");
        // helloLombok.setLombok(5);

        HelloLombok helloLombok = new HelloLombok("헬로", 5);

        System.out.println(helloLombok.getHello());
        System.out.println(helloLombok.getLombok());
    }
}
