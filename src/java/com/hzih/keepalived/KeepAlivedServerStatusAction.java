package com.hzih.keepalived;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 13-9-6
 * Time: 上午9:59
 * To change this template use File | Settings | File Templates.
 */
public class KeepAlivedServerStatusAction extends ActionSupport {
    private static final Logger logger = Logger.getLogger(KeepAlivedServerStatusAction.class);



    public String getLinuxCommandResultLine(String[] command ) {
        try {
            Process process = Runtime.getRuntime().exec(command);
            BufferedReader brs = new BufferedReader(new InputStreamReader(process.getInputStream()));
            while(true){
                String line = brs.readLine();
                if(line==null){
                    break;
                }else {
                    return line;
                }
            }
        } catch (IOException e) {
            logger.error("getLinuxCommandResultLine exec error",e);
        }
        return null;
    }

    public String openServer() throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setCharacterEncoding("utf-8");
        String msg = "0";
        try {
            String[] cmd_ = {
                    "/bin/sh",
                    "-c",
                    "service keepalived start"
            };
            Runtime.getRuntime().exec(cmd_);
            Thread.sleep(1000 * 2);
            //proc.exec("\"ps -fe|grep keepalived |grep -v grep|wc -l\"");
            String[] cmd = {
                    "/bin/sh",
                    "-c",
                    "ps -fe|grep keepalived |grep -v grep|wc -l"
            };
            String msg_on = getLinuxCommandResultLine(cmd);
           /* logger.info("result_code:"+proc.getResultCode());
            String msg_on = proc.getOutput();*/
            logger.info("msg:"+msg_on);
            //String error_msg = proc.getErrorOutput();
            //logger.info("error_msg:"+error_msg);
//            if (msg_on.contains("is running")) {
            //if (msg_on.contains("running")) {
            if (Integer.parseInt(msg_on)>0) {
//            if (msg_on.contains("active")) {
                msg = "1";
            } else {
                msg = "0";
            }
        } catch (Exception e) {
            logger.info(e.getMessage(),e);
            msg = "0";
        }
        String json = "{success:true,msg:'" + msg + "'}";


        return null;
    }

    public String closeServer() throws IOException {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setCharacterEncoding("utf-8");
        String msg = "0";
        try {
            String[] cmd_ = {
                    "/bin/sh",
                    "-c",
                    "service keepalived stop"
            };
            Runtime.getRuntime().exec(cmd_);
            Thread.sleep(1000 * 2);
            //proc.exec("service keepalived status");
            //proc.exec("\"ps -fe|grep keepalived |grep -v grep|wc -l\"");
            String[] cmd = {
                    "/bin/sh",
                    "-c",
                    "ps -fe|grep keepalived |grep -v grep|wc -l"
            };
            String msg_on = getLinuxCommandResultLine(cmd);
           /* logger.info("result_code:"+proc.getResultCode());
            String msg_on = proc.getOutput();*/
            logger.info("msg:"+msg_on);
            //String error_msg = proc.getErrorOutput();
            //logger.info("error_msg:"+error_msg);
//            if (msg_on.contains("is running")) {
                //if (msg_on.contains("running")) {
                if (Integer.parseInt(msg_on)>0) {
//            if (msg_on.contains("active")) {
                msg = "1";
            } else {
                msg = "0";
            }
        } catch (Exception e) {
            logger.info(e.getMessage(),e);
            msg = "0";
        }
        String json = "{success:true,msg:'" + msg + "'}";
        PrintWriter writer = response.getWriter();
        writer.write(json);
        writer.flush();
        writer.close();
        return null;
    }

    public String checkServerStatus() throws IOException {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setCharacterEncoding("utf-8");
        String msg = "0";
        try {
            //Proc proc = new Proc();
            //proc.exec("\"ps -fe|grep keepalived |grep -v grep|wc -l\"");
            String[] cmd = {
                    "/bin/sh",
                    "-c",
                    "ps -fe|grep keepalived |grep -v grep|wc -l"
            };
            String msg_on = getLinuxCommandResultLine(cmd);
           /* logger.info("result_code:"+proc.getResultCode());
            String msg_on = proc.getOutput();*/
            logger.info("msg:"+msg_on);
            //String error_msg = proc.getErrorOutput();
            //logger.info("error_msg:"+error_msg);
//            if (msg_on.contains("is running")) {
            //if (msg_on.contains("running")) {
            if (Integer.parseInt(msg_on)>0) {
//            if (msg_on.contains("active")) {
                msg = "1";
            } else {
                msg = "0";
            }
        } catch (Exception e) {
            logger.info(e.getMessage(),e);
            msg = "0";
        }
        String json = "{success:true,msg:'" + msg + "'}";
        PrintWriter writer = response.getWriter();
        writer.write(json);
        writer.flush();
        writer.close();
        return null;
    }

}
