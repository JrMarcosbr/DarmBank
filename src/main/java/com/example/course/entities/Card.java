package com.example.course.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.example.course.entities.enums.CardStatus;


@Entity
@Table(name = "tb_cart")
public class Card implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private int cardType;
	private String cardName;
	private int flag;
	private String cardNumber;
	private String cvc;
//	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-yy", timezone = "GMT")
	private String shelfLife;

	@ManyToOne
	@JoinColumn(name = "client_id")
	private User client;

//	@OneToMany(mappedBy = "id.order")
//	private Set<OrderItem> items = new HashSet<>();
//	
//	@OneToOne(mappedBy = "order", cascade = CascadeType.ALL)
//	private Payment payment;
	
	private Integer cartStatus;
	
	public Card() {
	}

	public Card(Long id, int cardType, String cardName, int flag, String cardNumber, String cvc,
			 String shelfLife, CardStatus cartStatus, User client) {
		this.id = id;
		this.cardType = cardType;
		this.cardName = cardName;
		this.flag = flag;
		this.cardNumber = cardNumber;
		this.cvc = cvc;
		this.shelfLife = shelfLife;
		setCartStatus(cartStatus);
		this.client = client;
	}	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getCardType() {
		return cardType;
	}

	public void setCardType(int cardType) {
		this.cardType = cardType;
	}

	public String getCardName() {
		return cardName;
	}

	public void setCardName(String cardName) {
		this.cardName = cardName;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getCvc() {
		return cvc;
	}

	public void setCvc(String cvc) {
		this.cvc = cvc;
	}

	public String getShelfLife() {
		return shelfLife;
	}

	public void setshelfLife(String shelfLife) {
		this.shelfLife = shelfLife;
	}

	public CardStatus getCartStatus() {
		return CardStatus.valueOf(cartStatus);
	}

	public void setCartStatus(CardStatus cartStatus) {
		if (cartStatus != null) {
			this.cartStatus = cartStatus.getCode();
		}	
	}

	public User getClient() {
		return client;
	}

	public void setClient(User client) {
		this.client = client;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Card other = (Card) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}