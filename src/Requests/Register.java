package Requests;

public class Register {

	public String name;
	public String password;
	public String email;
	public String creditCard;

	public Register(String name, String password,String email,String creditCard) {
		this.name = name;
		this.password = password;
		this.email = email;
		this.creditCard = creditCard;
	}

}
