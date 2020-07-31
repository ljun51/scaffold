package io.github.ljun51.spi.service;

public class Cat implements ServiceSpi {

    // 1.当接口提供具体实现后，在META-INF/services目录创建一个以接口全限定名的文件，内容为实现类的全限定名
    // 2.接口实现类所在的jar必须在主程序的class path中
    // 3.主程序通过ServiceLoader扫描META-INF/services动态加载实现模块
    // 4.实现类必须要有无参构造函数
    
    @Override
    public void say() {
        System.out.println("miao miao");
    }
}