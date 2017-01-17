package me.jp78.spambot.Base.Alts.Data;

import lombok.Builder;
import lombok.Data;

/**
 * Created by thejp on 11/12/2016.
 */
@Data
@Builder
public class AltDetails
{
    private String username;
    private String client_token;
    private String access_token;
    private String uuid;

}
