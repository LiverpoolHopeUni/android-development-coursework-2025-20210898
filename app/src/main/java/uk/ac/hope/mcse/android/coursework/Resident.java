package uk.ac.hope.mcse.android.coursework;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

public class Resident implements Parcelable {
    private final Uri profilePicture;
    private final int roomNumber;
    private final String name;
    private final int age;
    private final String bio;

    public Resident(Uri profilePicture, int roomNumber, String name, int age, String bio) {
        this.profilePicture = profilePicture;
        this.roomNumber = roomNumber;
        this.name = name;
        this.age = age;
        this.bio = bio;
    }

    protected Resident(Parcel in) {
        profilePicture = in.readParcelable(Uri.class.getClassLoader());
        roomNumber = in.readInt();
        name = in.readString();
        age = in.readInt();
        bio = in.readString();
    }

    public static final Creator<Resident> CREATOR = new Creator<Resident>() {
        @Override
        public Resident createFromParcel(Parcel in) {
            return new Resident(in);
        }

        @Override
        public Resident[] newArray(int size) {
            return new Resident[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(profilePicture, flags);
        dest.writeInt(roomNumber);
        dest.writeString(name);
        dest.writeInt(age);
        dest.writeString(bio);
    }
}
