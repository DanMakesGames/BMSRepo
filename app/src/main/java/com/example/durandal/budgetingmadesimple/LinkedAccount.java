package com.example.durandal.budgetingmadesimple;

public class LinkedAccount extends Account {
    public static final int REQUEST_SENT = 0;    //supervisor to supervisee
    public static final int ACCEPTED = 1;   //supervisor acctpted the request
    public static final int DECLINED = 2;   //supervisee declined the request
    public static final int UNLINK_SENT = 3;   //supervisee send the request to unlink
    public static final int UNLINK_GRANTED = 4;   //supervisee granted the request to unlink
    public static final int UNLINK_DENIED = 5;   //supervisor denied the request to unlink

    private int status = 0;
    private int relationId = 0;

    public LinkedAccount(int userID, String userName, String userEmail, int status, int relationId) {
        super(userID, userName, userEmail);
        this.status = status;
        this.status = relationId;
    }

    public boolean isLinked() {
        return (status == 1 || status == 3 || status == 5);
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getRelationId(){
        return relationId;
    }

}
