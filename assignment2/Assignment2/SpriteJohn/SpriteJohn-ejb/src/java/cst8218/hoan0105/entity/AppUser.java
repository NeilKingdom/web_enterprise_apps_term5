/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cst8218.hoan0105.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import javax.enterprise.inject.Instance;
import javax.enterprise.inject.spi.CDI;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.security.enterprise.identitystore.PasswordHash;
import javax.security.enterprise.identitystore.Pbkdf2PasswordHash;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author J-PC
 */
@Entity
@Table(name = "APPUSER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "APPUSER.findAll", query = "SELECT a FROM AppUser a"),
    @NamedQuery(name = "APPUSER.findById", query = "SELECT a FROM AppUser a WHERE a.id = :id"),
    @NamedQuery(name = "APPUSER.findByGroupname", query = "SELECT a FROM AppUser a WHERE a.groupname = :groupname"),
    @NamedQuery(name = "APPUSER.findByPassword", query = "SELECT a FROM AppUser a WHERE a.password = :password"),
    @NamedQuery(name = "APPUSER.findByRegistrationdate", query = "SELECT a FROM AppUser a WHERE a.registrationdate = :registrationdate"),
    @NamedQuery(name = "APPUSER.findByUserid", query = "SELECT a FROM AppUser a WHERE a.userid = :userid")})
public class AppUser implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Long id;
    @NotNull
    @Size(max = 255)
    @Column(name = "GROUPNAME")
    private String groupname;
    @NotNull
    @Size(max = 255)
    @Column(name = "PASSWORD")
    private String password;
    @NotNull
    @Column(name = "REGISTRATIONDATE")
    @Temporal(TemporalType.DATE)
    private Date registrationdate;
    @NotNull
    @Size(max = 255)
    @Column(name = "USERID")
    private String userid;

    public AppUser() {
        registrationdate = new Date();
    }

    public AppUser(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    public String getPassword() {
        return "";
    }

    public void setPassword(String pass) {
        
        // initialize a PasswordHash object which will generate password hashes
        Instance<? extends PasswordHash> instance = CDI.current().select(Pbkdf2PasswordHash.class);
        PasswordHash passwordHash = instance.get();
        passwordHash.initialize(new HashMap<String,String>()); // todo: are the defaults good enough?
        // now we can generate a password entry for a given password
        this.password = pass; //pretend the user has chosen a password mySecretPassword
        this.password = passwordHash.generate(this.password.toCharArray());
        //at this point, passwordEntry refers to a salted/hashed password entry corresponding to mySecretPasswor
    }

    public Date getRegistrationdate() {
        return registrationdate;
    }

    public void setRegistrationdate(Date registrationdate) {
        this.registrationdate = registrationdate;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AppUser)) {
            return false;
        }
        AppUser other = (AppUser) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cst8218.hoan0105.entity.Appuser[ id=" + id + " ]";
    }
    
}
