package com.mromer.windfinder.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class Author implements Parcelable {

	private String name;
	private String charge;
	private String healthCenter;
	private String imageURL;

	public static final Parcelable.Creator<Author> CREATOR = new Parcelable.Creator<Author>() {
		public Author createFromParcel(Parcel in) {
			return new Author(in);
		}

		public Author[] newArray(int size) {
			return new Author[size];
		}
	};

	private Author(Parcel in) {
		name = in.readString();
		charge = in.readString();
		healthCenter = in.readString();
		imageURL = in.readString();
	}

	public Author() {
		// TODO Auto-generated constructor stub
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCharge() {
		return charge;
	}

	public void setCharge(String charge) {
		this.charge = charge;
	}

	public String getHealthCenter() {
		return healthCenter;
	}

	public void setHealthCenter(String healthCenter) {
		this.healthCenter = healthCenter;
	}

	public String getImageURL() {
		return imageURL;
	}

	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(name);
		dest.writeString(charge);
		dest.writeString(healthCenter);
		dest.writeString(imageURL);
	}

}
