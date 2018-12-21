package cn.service.modules;

import cn.service.common.util.Global;

/**
 * Created by zw on 18/6/9.
 */
public class TestJstack {

    public static  void main(String[] args){
//        int i=1;
//        while (i<9999999) {
//            System.out.print(i);
//            i++;
//
//                Thread.sleep(3000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
        try {
        String dd = Global.getDefaultCategory1();
        byte[] tempByte = dd.getBytes("ISO-8859-1");
        String uniStr = new String(tempByte,"utf-8");
        System.out.println(uniStr);
        } catch (Exception e) {
            e.printStackTrace();
       }
    }
}