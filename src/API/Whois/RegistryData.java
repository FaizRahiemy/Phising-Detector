/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package API.Whois;

/**
 *
 * @author rajar
 */
public class RegistryData
{
    private String footer;

    private String parseCode;

    private String rawText;

    private String whoisServer;
    
    private String strippedText;

    private String domainName;

    private String dataError;

    private String header;

    public String getFooter ()
    {
        return footer;
    }

    public void setFooter (String footer)
    {
        this.footer = footer;
    }

    public String getParseCode ()
    {
        return parseCode;
    }

    public void setParseCode (String parseCode)
    {
        this.parseCode = parseCode;
    }

    public String getRawText ()
    {
        return rawText;
    }

    public void setRawText (String rawText)
    {
        this.rawText = rawText;
    }

    public String getWhoisServer ()
    {
        return whoisServer;
    }

    public void setWhoisServer (String whoisServer)
    {
        this.whoisServer = whoisServer;
    }

    public String getStrippedText ()
    {
        return strippedText;
    }

    public void setStrippedText (String strippedText)
    {
        this.strippedText = strippedText;
    }

    public String getDomainName ()
    {
        return domainName;
    }

    public void setDomainName (String domainName)
    {
        this.domainName = domainName;
    }

    public String getDataError ()
    {
        return dataError;
    }

    public void setDataError (String dataError)
    {
        this.dataError = dataError;
    }

    public String getHeader ()
    {
        return header;
    }

    public void setHeader (String header)
    {
        this.header = header;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [footer = "+footer+", parseCode = "+parseCode+", rawText = "+rawText+", whoisServer = "+whoisServer+", strippedText = "+strippedText+", domainName = "+domainName+", dataError = "+dataError+", header = "+header+"]";
    }
}