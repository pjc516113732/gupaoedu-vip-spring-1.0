package com.gupaoedu.mvcframework.servlet.v1;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

/**
 * @Classname JCDispatcherServlet
 * @Description TODO
 * @Date 2019/4/16
 * @Author by JunChang
 */
public class JCDispatcherServlet extends HttpServlet {

    private Properties properties = new Properties();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        //1.加载配置文件
        doLoadConfig(config.getInitParameter("contextConfigLocation"));
        //2.扫描相关的类
        doScanner(properties.getProperty("scanPackage"));
        //3.扫描的类放入IOC容器中
        doInstance();
        //4.完成依赖注入
        doAutowired();
        //5.初始化handlerMapping
        initHandlerMapping();

    }

    private void initHandlerMapping() {

    }

    private void doAutowired() {

    }

    private void doInstance() {

    }

    private void doScanner(String scanPackage) {

        URL url = this.getClass().getClassLoader().getResource("/"+scanPackage.replace("\\.","/"));
        File classPath = new File(url.getFile());
        File[] files = classPath.listFiles();
        for (File file : files) {
            if (file.isDirectory()){
                doScanner(scanPackage+"."+file.getName());
            }else {

            }
        }

    }

    private void doLoadConfig(String contextConfigLocation) {
        InputStream fis = this.getClass().getClassLoader().getResourceAsStream(contextConfigLocation);
        try {
            properties.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (null != fis){
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
