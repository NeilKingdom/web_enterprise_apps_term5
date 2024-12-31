package cst8218.hoan0105.entity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-12-02T20:23:24")
@StaticMetamodel(AppUser.class)
public class AppUser_ { 

    public static volatile SingularAttribute<AppUser, String> password;
    public static volatile SingularAttribute<AppUser, Date> registrationdate;
    public static volatile SingularAttribute<AppUser, Long> id;
    public static volatile SingularAttribute<AppUser, String> groupname;
    public static volatile SingularAttribute<AppUser, String> userid;

}