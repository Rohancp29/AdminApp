package com.technolite.adminapp;

public class Member {
    private String memberId;
    private String member_name;
    private String member_addrs;
    private String member_number;
    private String member_fee;
    private String Duration;
    private String Batch;
    private String join_date;
    private String member_image;
    private String member_remaining_fee;
    private  String reminder_status;

    public String getMember_name() {
        return member_name;
    }

    public void setMember_name(String member_name) {
        this.member_name = member_name;
    }

    public String getMember_addrs() {
        return member_addrs;
    }

    public void setMember_addrs(String member_addrs) {
        this.member_addrs = member_addrs;
    }

    public String getMember_number() {
        return member_number;
    }

    public void setMember_number(String member_number) {
        this.member_number = member_number;
    }

    public String getMember_fee() {
        return member_fee;
    }

    public void setMember_fee(String member_fee) {
        this.member_fee = member_fee;
    }

    public String getDuration() {
        return Duration;
    }

    public void setDuration(String duration) {
        Duration = duration;
    }

    public String getBatch() {
        return Batch;
    }

    public void setBatch(String batch) {
        Batch = batch;
    }

    public String getJoin_date() {
        return join_date;
    }

    public void setJoin_date(String join_date) {
        this.join_date = join_date;
    }

    public String getMember_image() {
        return member_image;
    }

    public void setMember_image(String member_image) {
        this.member_image = member_image;
    }

    public String getMember_remaining_fee() {
        return member_remaining_fee;
    }

    public void setMember_remaining_fee(String member_remaining_fee) {
        this.member_remaining_fee = member_remaining_fee;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getReminder_status() {
        return reminder_status;
    }

    public void setReminder_status(String reminder_status) {
        this.reminder_status = reminder_status;
    }

    public Member( String member_name, String member_addrs, String member_number, String member_fee, String Duration,  String join_date,  String reminder_status) {

        this.member_name = member_name;
        this.member_addrs = member_addrs;
        this.member_number = member_number;
        this.member_fee = member_fee;
        this.Duration = Duration;

        this.join_date = join_date;

        this.reminder_status=reminder_status;
    }


}

