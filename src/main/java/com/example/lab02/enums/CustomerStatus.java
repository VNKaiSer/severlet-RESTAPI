package com.example.lab02.enums;

public enum CustomerStatus {

    ACTIVE(0),
    IN_ACTIVE(1);
     private int value;
     CustomerStatus(int value){
         this.value = value;
     }

     public int getValue(){
         return value;
     }
}
