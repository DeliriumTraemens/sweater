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
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private User author;
	
	private String filename;
	
	//----Constructors---//
	
	public Message() {
	}
	
	public Message(String text, String tag) {
		this.text = text;
		this.tag = tag;
	}
	
	public Message(String text, String tag, User author) {
		this.text = text;
		this.tag = tag;
		this.author = author;
	}
	
	public Message(String text, String tag, User author, String filename) {
		this.text = text;
		this.tag = tag;
		this.author = author;
		this.filename = filename;
	}
	
	//----Methods---//
	public String getAuthorName(){
		return author !=null ? author.getUsername() : "<none>";
		
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
	
	public User getAuthor() {
		return author;
	}
	
	public void setAuthor(User author) {
		this.author = author;
	}
	
	public String getFilename() {
		return filename;
	}
	
	public void setFilename(String filename) {
		this.filename = filename;
	}
	//----To String---//
	
	@Override
	public String toString() {
		return "\nMessage{" +
				       "id=" + id +
				       ", text='" + text + '\'' +
				       ", tag='" + tag + '\'' +
				       ", author=" + author +
				       ", filename='" + filename + '\'' +
				       '}';
	}
}
