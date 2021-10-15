package ittep.restapidemo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //логин или почта
    @Column(unique = true)
    private String login;

    //github или google
    private String provider;

    private String role;

    private String password;

    private boolean enabled;
}
