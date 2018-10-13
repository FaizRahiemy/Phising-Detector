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
public class Whois
{
    private WhoisRecord WhoisRecord;

    public WhoisRecord getWhoisRecord ()
    {
        return WhoisRecord;
    }

    public void setWhoisRecord (WhoisRecord WhoisRecord)
    {
        this.WhoisRecord = WhoisRecord;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [WhoisRecord = "+WhoisRecord+"]";
    }
}
