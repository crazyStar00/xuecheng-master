package com.xuecheng.test.freemarker.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Student {
    /**
     * 姓名
     */
    String name;
    /**
     * 年龄
     */
    int age;
    /**
     * 生日
     */
    Date birthday;
    /**
     * 钱包
     */
    Float money;
    /**
     * 朋友列表
     */
    List<Student> friends;
    /**
     * 最好的朋友
     */
    Student bestFriend;
}
