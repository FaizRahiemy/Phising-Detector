/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PhisingDetector;

import com.jaunt.Element;
import com.jaunt.JauntException;
import com.jaunt.UserAgent;
import java.util.List;

/**
 *
 * @author faiz
 */
public class Check {
    public static void main(String[] args) {
        String domain = "http://safety-busines-pages.com/";
        Check check = new Check();
        check.checkRequest(domain);
        check.checkAnchor(domain);
    }
    
    public void checkRequest(String domain){
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
            float points = 0;
            if (lis.size() != 0){
                points = (((float)lis.size()-point)/lis.size())*100;
            }
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
    
    public void checkAnchor(String domain){
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
            List<String> lis = div.findAttributeValues("<a href>");
            for (int i = 0; i < lis.size(); i++) {
                System.out.print(lis.get(i));
                if (lis.get(i).contains(chkDomain)){
                    System.out.print(" [x]");
                    point++;
                }
                System.out.println("");
            }
            float points = 0;
            if (lis.size() != 0){
                points = (((float)lis.size()-point)/lis.size())*100;
            }
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
