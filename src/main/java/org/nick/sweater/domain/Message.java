package org.nick.sweater.domain;

import javax.persistence.*;

@Entity
@Table
public class Message {
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO )
	private Integer id;
	
	private String text;
	private String tag;
	
	//----Constructors---//
	
	public Message() {
	}
	
	public Message(String text, String tag) {
		this.text = text;
		this.tag = tag;
	}
	//----Getters and Setterrs---//
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public String getTag() {
		return tag;
	}
	
	public void setTag(String tag) {
		this.tag = tag;
	}
	
	//----To String---//
	
	@Override
	public String toString() {
		return "\nMessage{" +
				       "id=" + id +
				       ", text='" + text + '\'' +
				       ", tag='" + tag + '\'' +
				       '}';
	}
}
