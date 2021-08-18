package de.gaalop.codegenGappIntrinsics.sse;

import java.util.ArrayList;
import java.util.List;

public class VectorIntrinsics {
    String name;
    ArrayList<String> elements;

    VectorIntrinsics(String name){
        this.name = name;
        elements = new ArrayList<String>();
    }

    public String getName() {
        return name;
    }

    public ArrayList<String> getElements() {
        return elements;
    }

    public int getSize(){ return elements.size(); }

    public String getElement(int x){
        if (x>=elements.size())
            return "0.0";
        return elements.get(x);
    }
}
