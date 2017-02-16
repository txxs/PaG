package com.lemain.domain;

/**
 * Created by jianghuimin on 2017/1/18.
 */

/**
 * 初步只关注用户最少信息
 */
public class User {

    private Integer id;
    private String phoneNum;//注册的电话号码
    private String passWord;//注册密码
    private String headPic;//头像的路径
    private String sex;//性别
    private Integer age;//年龄

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getHeadPic() {
        return headPic;
    }

    public void setHeadPic(String headPic) {
        this.headPic = headPic;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", phoneNum='" + phoneNum + '\'' +
                ", passWord='" + passWord + '\'' +
                ", headPic='" + headPic + '\'' +
                ", sex='" + sex + '\'' +
                ", age=" + age +
                '}';
    }
}
