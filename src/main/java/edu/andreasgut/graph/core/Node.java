package edu.andreasgut.graph.core;

public class Node {

    private String name;


    public Node(String name) {
        this.name = name;
    }


    public String getName() {
        return name;
    }


    @Override
    public String toString(){
        return name;
    }



}
