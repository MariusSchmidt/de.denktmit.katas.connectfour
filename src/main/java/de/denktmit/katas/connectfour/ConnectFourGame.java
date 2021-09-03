package de.denktmit.katas.connectfour;

public class ConnectFourGame {

    private boolean hasToken = false;

    public String printPlayfield() {
        String playfield = """
                +-+-+-+-+-+-+-+
                | | | | | | | |
                +-+-+-+-+-+-+-+
                | | | | | | | |
                +-+-+-+-+-+-+-+
                | | | | | | | |
                +-+-+-+-+-+-+-+
                | | | | | | | |
                +-+-+-+-+-+-+-+
                | | | | | | | |
                +-+-+-+-+-+-+-+
                | | | | |%s| | |
                +-+-+-+-+-+-+-+""";
        String token = hasToken? "1" : " ";
        return String.format(playfield, token);
    }

    public void dropToken(int i) {
        hasToken = true;
    }
}
