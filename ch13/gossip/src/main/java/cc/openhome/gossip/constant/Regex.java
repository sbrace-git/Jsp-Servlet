package cc.openhome.gossip.constant;

import java.util.regex.Pattern;

public class Regex {
    public static final Pattern passwdRegex = Pattern.compile("^\\w{8,16}$");
}
