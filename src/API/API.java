/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package API;

import API.Whois.Whois;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 *
 * @author Faiz Rahiemy
 */
public interface API {
    @GET("WhoisService")
    Call<Whois> whois(@Query("apiKey") String apiKey, @Query("domainName") String domainName, @Query("outputFormat") String outputFormat);
}
