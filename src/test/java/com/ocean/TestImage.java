package com.ocean;

import org.junit.Test;

/**
 * Created by 99512 on 2019/1/9 9:44.
 */
public class TestImage {

    @Test
    public void test01(){
        try {
            ResizeImage.transAllPhoto("D:\\新建文件夹");
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}
