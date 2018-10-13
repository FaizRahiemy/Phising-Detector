/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PhisingDetector;

import Local.URL;
import com.jaunt.Element;
import com.jaunt.Elements;
import com.jaunt.JauntException;
import com.jaunt.UserAgent;
import java.util.List;

/**
 *
 * @author faiz
 */
public class Test {
    public static void main(String[] args) {
        String domain = "http://facebook.com";
        String chkDomain = "";
        if (domain.contains("https")){
            chkDomain = domain.replace("https://", "");
        }else if (domain.contains("http")){
            chkDomain = domain.replace("http://", "");
        }
        chkDomain = chkDomain.split("/")[0];
        int point = 0;
        try{
            UserAgent userAgent = new UserAgent(); 
            userAgent.visit(domain);

            Element div = userAgent.doc;
            List<String> lis = div.findAttributeValues("<img src='.*'>");
            for (int i = 0; i < lis.size(); i++) {
                System.out.print(lis.get(i));
                if (lis.get(i).contains(chkDomain)){
                    System.out.print(" [x]");
                    point++;
                }
                System.out.println("");
            }
            float points = (((float)lis.size()-point)/lis.size())*100;
            System.out.println("((" + lis.size() + "-" + point + ")/" + lis.size() + ")*100");
            System.out.println("size: " + lis.size());
            System.out.println("chkdomain: " + chkDomain);
            System.out.println("asli: " + point);
            System.out.println("luar: " + (lis.size()-point));
            System.out.println("poin: " + points + "%");
        }catch(JauntException e){                   
            System.err.println(e);
        }
    }
}
