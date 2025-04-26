package uk.ac.hope.mcse.android.coursework;

import android.net.Uri;

public class Resident {
    private Uri profilePicture;
    private int roomNumber;
    private String name;
    private int age;
    private String bio;

    public Resident(Uri profilePicture, int roomNumber, String name, int age, String bio) {
        this.profilePicture = profilePicture;
        this.roomNumber = roomNumber;
        this.name = name;
        this.age = age;
        this.bio = bio;
    }

    public Uri getProfilePicture() {
        return profilePicture;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getBio() {
        return bio;
    }
}
