package org.sbm.demo.data;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import lombok.EqualsAndHashCode;

@ToString @EqualsAndHashCode
public class Company {
    @Getter @Setter @NonNull private String name;
    @Getter @Setter @NonNull private String addr;
}
