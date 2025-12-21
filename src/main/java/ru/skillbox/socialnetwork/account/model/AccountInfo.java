package ru.skillbox.socialnetwork.account.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "account_info")
public class AccountInfo {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "phone", unique = true)
    private String phone;

    @Column(name = "photo")
    private String photo;

    @Column(name = "about")
    private String about;

    @Column(name = "city")
    private String city;

    @Column(name = "country")
    private String country;

    @Column(name = "bd")
    private LocalDate birthDate;

    @Column(name = "photo_name")
    private String photoName;

    @Column(name = "emoji_status")
    private String emojiStatus;

    @OneToOne
    @JoinColumn(name = "acc_id")
    private Account account;

}
