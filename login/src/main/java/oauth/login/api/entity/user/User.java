package oauth.login.api.entity.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import oauth.login.oauth.entity.ProviderType;
import oauth.login.oauth.entity.RoleType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class User {

    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "USER_SEQ")
    private Long userSeq;

    @NotNull
    @Size(max = 64)
    @Column(name = "USER_ID", length = 64, unique = true)
    private String userId;

    @NotNull
    @Size(max = 100)
    @Column(length = 100)
    private String username;

    @JsonIgnore
    @NotNull
    @Size(max = 128)
    @Column(length = 128)
    private String password;

    @NotNull
    @Size(max = 512)
    @Column(length = 512, unique = true)
    private String email;

    @NotNull
    @Size(min = 1, max = 1)
    @Column(length = 1)
    private String emailVerifiedYn;

    @NotNull
    @Size(max = 512)
    @Column(length = 512)
    private String profileImageUrl;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ProviderType proviederType;

    @NotNull
    @Column(length = 20)
    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    @NotNull
    private LocalDateTime createdAt;

    @NotNull
    private LocalDateTime modifiedAt;
    
    @Builder
    public User(
            @NotNull @Size(max = 64) String userId,
            @NotNull @Size(max = 100) String username,
            @NotNull @Size(max = 512) String email,
            @NotNull @Size(max = 1) String emailVerifiedYn,
            @NotNull @Size(max = 512) String profileImageUrl,
            @NotNull ProviderType providerType,
            @NotNull RoleType roleType,
            @NotNull LocalDateTime createdAt,
            @NotNull LocalDateTime modifiedAt
    ) {
        this.userId = userId;
        this.username = username;
        this.password = "NO_PASS";
        this.email = email != null ? email : "NO_EMAIL";
        this.emailVerifiedYn = emailVerifiedYn;
        this.profileImageUrl = profileImageUrl != null ? profileImageUrl : "";
        this.proviederType = providerType;
        this.roleType = roleType;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }
}
