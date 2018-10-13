/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PhisingDetector;

import API.API;
import API.Config;
import API.RetrofitHelper;
import API.Whois.Whois;
import Controller.ControllerMenu;
import Local.URL;
import java.util.Scanner;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 *
 * @author rajar
 */
public class PhisingDetector {
    
    public static void main(String[] args) {
        ControllerMenu menu = new ControllerMenu();
        API api;
        api = RetrofitHelper.getClient().create(API.class);

        Config config = new Config();
        
        Scanner input = new Scanner(System.in);
        
        String domain = input.nextLine();
        URL url = new URL(domain);
        
        Call<Whois> callEvent = api.whois(config.getApiKey(), domain, config.getOutputFormat());
        callEvent.enqueue(new Callback<Whois>() {
            @Override
            public void onResponse(Call<Whois> callEvent, Response<Whois> response) {
                if (response.body().getWhoisRecord().getRegistryData().getRawText().charAt(1) == ' '){
                    System.out.println("Domain not found in the whois data");
                }else{
                    System.out.println("Estimated domain age : " + response.body().getWhoisRecord().getEstimatedDomainAge());
                }
                System.out.println("URL Length : " + url.getUrl().length());
                System.out.println("Dot Count : " + url.countDot());
                System.out.println("Point Long URL : " + url.getPointUrlLength());
                System.out.println("Point Suffix : " + url.getPointSuffix());
                System.out.println("Point Dot Count : " + url.getPointDot());
                if (response.body().getWhoisRecord().getRegistryData().getRawText().charAt(1) != ' '){
                    System.out.println("Point Domain Age : " + response.body().getWhoisRecord().getPoint());
                }
            }

            @Override
            public void onFailure(Call<Whois> callEvent, Throwable t) {
            }
        });
    }
}
