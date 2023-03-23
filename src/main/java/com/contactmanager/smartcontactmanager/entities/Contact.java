package com.contactmanager.smartcontactmanager.entities;
import javax.persistence.*;

@Entity
@Table(name="contact_master")
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int cid;
    private String name;
    private String nickname;
    private String email;
    private String work;
    private String number;
    private String uimage;
    @Column(length = 500)
    private String description;
    @ManyToOne()
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Contact(int cid, String name, String nickname, String email, String work, String number, String uimage, String description) {
        this.cid = cid;
        this.name = name;
        this.nickname = nickname;
        this.email = email;
        this.work = work;
        this.number = number;
        this.uimage = uimage;
        this.description = description;
    }

    public Contact() {
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getUimage() {
        return uimage;
    }

    public void setUimage(String uimage) {
        this.uimage = uimage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "cid=" + cid +
                ", name='" + name + '\'' +
                ", nickname='" + nickname + '\'' +
                ", email='" + email + '\'' +
                ", work='" + work + '\'' +
                ", number='" + number + '\'' +
                ", image='" + uimage + '\'' +
                ", description='" + description + '\'' +
                ", user=" + user +
                '}';
}
}
