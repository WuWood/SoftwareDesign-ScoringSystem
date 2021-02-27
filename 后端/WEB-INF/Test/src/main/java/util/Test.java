package util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Test {
    public static void main(String[] args) {
//        String birthday = "2001-05-24";
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        try {
//            System.out.print(simpleDateFormat.parse(birthday));
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//
//        java.util.Date utilDate = new Date();                                    //获取java.util.Date对象---也即当前时间
//        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());    //将java.util.Date类型转换成java.sal.Date类型
//        System.out.print(sqlDate);

        //24小时制
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//12小时制
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String date1 = "2017-04-23 16:45:12";
        try {
            System.out.println(sdf.parse(date1));
            System.out.println(sdf.format(sdf.parse(date1))); //2017-04-23 16:45:12
            System.out.println(sdf1.format(sdf.parse(date1))); //2017-04-23 04:45:12
            System.out.println(sdf.format(new Date()));
            System.out.println(new Date());
        } catch (ParseException e) {
// TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
