package models;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Card implements Serializable
{
	private int id;
	private Customer customer;
	private String cardNumber;
	private float discountPercent;
	private String partOwner;
	private String type;
	
	public int getId()
	{
		return id;
	}
	public void setId(int id)
	{
		this.id = id;
	}
	
	public Customer getCustomer()
	{
		return customer;
	}
	public void setCustomer(Customer customer)
	{
		this.customer = customer;
	}
	
	public String getCardNumber()
	{
		return cardNumber;
	}
	public void setCardNumber(String cardNumber)
	{
		this.cardNumber = cardNumber;
	}
	
	public float getDiscountPercent()
	{
		return discountPercent;
	}
	public void setDiscountPercent(float discountPercent)
	{
		this.discountPercent = discountPercent;
	}
	
	public String getPartOwner()
	{
		return partOwner;
	}
	public void setPartOwner(String partOwner)
	{
		this.partOwner = partOwner;
	}
	
	public String getType()
	{
		return type;
	}
	public void setType(String type)
	{
		this.type = type;
	}
}