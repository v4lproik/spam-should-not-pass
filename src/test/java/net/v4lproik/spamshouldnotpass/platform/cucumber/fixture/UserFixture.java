package net.v4lproik.spamshouldnotpass.platform.cucumber.fixture;

import net.v4lproik.spamshouldnotpass.platform.models.MemberPermission;
import net.v4lproik.spamshouldnotpass.platform.models.MemberStatus;

import java.util.UUID;

public class UserFixture {
    public UUID userId;
    public String email;
    public MemberPermission permission;
    public MemberStatus status;
    public String password;
    public String firstname;
    public String lastname;
    public String nickname;
    public String corporation;

    public String authToken;
}
