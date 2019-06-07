package Requests;

public class AccountCheckRequest {

	public String username;
    public String password;

    public AccountCheckRequest(String _username,String _password){
    	this.username = _username;
    	this.password = _password;
    }

}
