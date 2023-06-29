package com.example.vuesever.utils;

import cn.hutool.extra.mail.MailAccount;

public class EmailAccountHelper {
    public static MailAccount createMailAccount(){
        MailAccount mailAccount = new MailAccount();
        mailAccount.setHost("smtp.yeah.net");
        mailAccount.setPort(25);
        mailAccount.setFrom("kuzumusic@yeah.net");
        mailAccount.setUser("KuZuMusic");
        mailAccount.setPass("ZPARLMGSKOPNQAGI");
        mailAccount.setAuth(true);
        return mailAccount;
    }
}
