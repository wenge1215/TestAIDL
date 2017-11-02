package wenge.com.testaidl;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by WENGE on 2017/11/2.
 * 备注：
 */


public class PersonBean implements Parcelable {
    String name;
    int age;

    protected PersonBean(Parcel in) {
        name = in.readString();
        age = in.readInt();
    }

    public PersonBean() {

    }

    public static final Creator<PersonBean> CREATOR = new Creator<PersonBean>() {
        @Override
        public PersonBean createFromParcel(Parcel in) {
            return new PersonBean(in);
        }

        @Override
        public PersonBean[] newArray(int size) {
            return new PersonBean[size];
        }
    };

    //如果需要支持定向tag为out,inout，就要重写该方法
    public void readFromParcel(Parcel dest) {
        //注意，此处的读值顺序应当是和writeToParcel()方法中一致的
        this.name = dest.readString();
        this.age = dest.readInt();
    }
    public PersonBean(String s, int i) {
        name = s;
        age = i;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(age);
    }
}
