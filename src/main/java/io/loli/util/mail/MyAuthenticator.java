package io.loli.util.mail;

import javax.mail.*;

/**
 * 发送邮件时的验证 <br/>
 * User: choco(loli@linux.com) <br/>
 * Date: 2014年4月26日 <br/>
 * Time: 下午3:46:11 <br/>
 * 
 * @author choco
 */
public class MyAuthenticator extends Authenticator {
    String userName = null;
    String password = null;

    public MyAuthenticator() {
    }

    public MyAuthenticator(String username, String password) {
        this.userName = username;
        this.password = password;
    }

    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(userName, password);
    }
}