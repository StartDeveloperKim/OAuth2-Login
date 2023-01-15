package oauth.login.api.entity.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class UserRefreshToken {

    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long refreshTokenSeq;

    @NotNull
    @Size(max = 64)
    @Column(length = 64, unique = true)
    private String userId;

    @NotNull
    @Size(max = 256)
    @Column(length = 256)
    private String refreshToken;

    public UserRefreshToken(
            @NotNull @Size(max = 64) String userId,
            @NotNull @Size(max = 256) String refreshToken
    ) {
        this.userId = userId;
        this.refreshToken = refreshToken;
    }
}
