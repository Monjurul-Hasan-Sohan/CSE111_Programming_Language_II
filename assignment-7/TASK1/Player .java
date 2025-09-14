class Player{
    static int total = 0;
    static String arrname[] = new String [11];

    public String name;
    public String country;
    public int rating;

    Player(String name, String country, int rating){
        this.name = name;
        this.country = country;
        this.rating = rating;
        total++;
        arrname[total] = name;
    }

    public String player_detail(){
        return "Player Name: " + name + "\nCountry: " + country + "\nJersey Number: " + rating;
    }
    public static void info(){
        System.out.println("Total number of players: " + total);
        System.out.println("Players enlisted so far:");
        for(int i = 0; i < total; i++){
            if(arrname[i] != null){
                System.out.print(arrname[i] + " , ");
            }
        }
        System.out.println();
    }

}