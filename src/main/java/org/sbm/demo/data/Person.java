package org.sbm.demo.data;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import lombok.EqualsAndHashCode;

@ToString @EqualsAndHashCode
public class Person {
    @Getter @Setter @NonNull private String fname;
    @Getter @Setter @NonNull private String lname;
    @Getter @Setter @NonNull private Company company;
}
