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
	private int tipo_cartao;
	private String nome_cartao;
	private int bandeira;
	private String num_cartao;
	private String cvc;
//	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-yy", timezone = "GMT")
	private String validade;

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

	public Card(Long id, int tipo_cartao, String nome_cartao, int bandeira, String num_cartao, String cvc,
			 String validade, CardStatus cartStatus, User client) {
		this.id = id;
		this.tipo_cartao = tipo_cartao;
		this.nome_cartao = nome_cartao;
		this.bandeira = bandeira;
		this.num_cartao = num_cartao;
		this.cvc = cvc;
		this.validade = validade;
		setCartStatus(cartStatus);
		this.client = client;
	}	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getTipo_cartao() {
		return tipo_cartao;
	}

	public void setTipo_cartao(int tipo_cartao) {
		this.tipo_cartao = tipo_cartao;
	}

	public String getNome_cartao() {
		return nome_cartao;
	}

	public void setNome_cartao(String nome_cartao) {
		this.nome_cartao = nome_cartao;
	}

	public int getBandeira() {
		return bandeira;
	}

	public void setBandeira(int bandeira) {
		this.bandeira = bandeira;
	}

	public String getNum_cartao() {
		return num_cartao;
	}

	public void setNum_cartao(String num_cartao) {
		this.num_cartao = num_cartao;
	}

	public String getCvc() {
		return cvc;
	}

	public void setCvc(String cvc) {
		this.cvc = cvc;
	}

	public String getValidade() {
		return validade;
	}

	public void setValidade(String validade) {
		this.validade = validade;
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