package parsing;

import entities.*;
import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

public class Parser {
    private ArrayList<League> leaguesArrayList = new ArrayList<>();
    private ArrayList<FootballClub> footballClubsArrayList = new ArrayList<>();
    private ArrayList<ClubStatistics> clubStatisticsArrayList =new ArrayList<>();
    private ArrayList<Player> playerArrayList = new ArrayList<>();
    private  ArrayList<PlayerStatistics> playerStatisticsArrayList= new ArrayList<>();


    private final Map<String,String> dictionaryOfLeagues = new HashMap<String,String>();
    {
        dictionaryOfLeagues.put("Английская Премьер-лига", "https://www.sports.ru/epl/table");
        dictionaryOfLeagues.put("Ла Лига", "https://www.sports.ru/la-liga/table/");
        dictionaryOfLeagues.put("Серия А", "https://www.sports.ru/seria-a/table/");
        dictionaryOfLeagues.put("Бундеслига", "https://www.sports.ru/bundesliga/table/");
        dictionaryOfLeagues.put("Лига 1", "https://www.sports.ru/ligue-1/table/");
    }

    public Parser(String league_name)
    {
        //получение данных для League
        getLeagues(league_name);
    }

    private void getLeagues(String league_name) {
        Document page = null;
        page = getDocument(dictionaryOfLeagues.get(league_name));
        Elements tableOfTeams = page.select("table[class=stat-table table  sortable-table]");
        Elements tableOfLeagueInformation = page.select("table[class=profile-table]");
        String country = tableOfLeagueInformation.select("tr").get(0).select("td").text();
        int indexOfTeams = 0;
        int count_of_teams = 0;
        Elements teamElement = tableOfTeams.select("tr");
        Element lastTeamElement = teamElement.last();
        ArrayList<Integer> countOfMatchesList = new ArrayList<>();
        String currentTeamURL = null;
        while (true) {
            Element currentTeamElement = teamElement.get(indexOfTeams + 1);
            countOfMatchesList.add(Integer.parseInt(currentTeamElement.select("td").get(2).text()));
            currentTeamURL = currentTeamElement.select("td").get(1).select("a[class=name]").attr("href");

            //получение данных для FootballCLub и ClubStatistics
            getClubWithStatistics(currentTeamURL, league_name);

            indexOfTeams++;
            count_of_teams++;

            if (currentTeamElement == lastTeamElement)
                break;
        }
        int count_of_tours_played = Collections.max(countOfMatchesList);
        countOfMatchesList.clear();

        leaguesArrayList.add(new League(league_name, count_of_teams, count_of_tours_played, country));

        //вывод спарсенных данных о лиге
        System.out.println("Наименование лиги: " + league_name);
        System.out.println("Количество команд: " + count_of_teams);
        System.out.println("Количество сыгранных туров: " + count_of_tours_played);
        System.out.println("Место провденеия: " + country);
        System.out.println("\n");
    }

    private void getClubWithStatistics(String clubURL, String leagueName)
    {
        Document clubPage = getDocument(clubURL);
        Elements clubTable = clubPage.select("div[class=c-tag-header tag-main-block]");
        Elements profileClubTable = clubPage.select("table[class=profile-table]");
        Element statOfClubTableElement = clubPage.select("table[class=stat-table]").select("tr").get(1);
        String club_name = clubTable.select("h1[class=titleH1]").text();
        String [] surnameAndNameOfCoach = profileClubTable.select("a").text().split(" ");
        String surname_of_coach = null;
        String name_of_coach = surnameAndNameOfCoach[0];
        if (surnameAndNameOfCoach[0].isEmpty()) {
            surname_of_coach = "Главный";
            name_of_coach = "Тренер";
        }
        else {
            if (surnameAndNameOfCoach.length == 3)
            {
                surname_of_coach = surnameAndNameOfCoach[1] + " " + surnameAndNameOfCoach[2];
            }
            else if (surnameAndNameOfCoach.length == 2) {
                surname_of_coach = surnameAndNameOfCoach[1];
            }
            else
                surname_of_coach = name_of_coach;
        }

        Elements statOfClubInformationElement = statOfClubTableElement.select("td");
        int tournament_position = Integer.parseInt(statOfClubInformationElement.get(2).text());
        int count_of_matches_played = Integer.parseInt(statOfClubInformationElement.get(3).text());
        int wins = Integer.parseInt(statOfClubInformationElement.get(4).text());
        int draws = Integer.parseInt(statOfClubInformationElement.get(5).text());
        int losses = Integer.parseInt(statOfClubInformationElement.get(6).text());
        int goal_for = Integer.parseInt(statOfClubInformationElement.get(7).text());
        int goal_against = Integer.parseInt(statOfClubInformationElement.get(8).text());
        int goal_difference = Integer.parseInt(statOfClubInformationElement.get(9).text());
        int points = Integer.parseInt(statOfClubInformationElement.get(10).text());

        int count_of_players = getSquad(clubURL, club_name);

        footballClubsArrayList.add(new FootballClub(club_name, leagueName, count_of_players, surname_of_coach, name_of_coach));
        clubStatisticsArrayList.add(new ClubStatistics(club_name, tournament_position, count_of_matches_played, wins, draws,
                losses, goal_for, goal_against, goal_difference, points));

        //вывод спарсенных данных о футбольном клубе
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println("Наименование клуба: " + club_name);
        System.out.println("Наименование лиги: " + leagueName);
        System.out.println("Количество игроков: " + count_of_players);
        System.out.println("Фамилия тренера: " + surname_of_coach);
        System.out.println("Имя тренера: " + name_of_coach);
        System.out.println("_________________________________________________________");
        //вывод спарсенных данных о статистике клуба
        System.out.println("Наименование клуба: " + club_name);
        System.out.println("Турнирное положение: " + tournament_position);
        System.out.println("Матчей сыграно: " + count_of_matches_played);
        System.out.println("Победы: " + wins);
        System.out.println("Ничьи: " + draws);
        System.out.println("Поражения: " + losses);
        System.out.println("Забито голов: " + goal_for);
        System.out.println("Пропущено голов: " + goal_against);
        System.out.println("Разница голов: " + goal_difference);
        System.out.println("Количество очков: " + points);
        System.out.println("=========================================================");
    }

    private int getSquad(String clubURL, String clubName){
        Document squadPage = getDocument(clubURL + "team/");
        Document playersStatisticsPage = getDocument(clubURL + "stat/");
        Elements fieldPlayersStatisticsTable = playersStatisticsPage.select("table[class=stat-table sortable-table js-active]");
        Elements goalkeepersStatisticsTable = playersStatisticsPage.select("table[class=stat-table js-active]");
        Elements playersTable = squadPage.select("table[class=stat-table sortable-table]");
        Element lastPlayerElement = playersTable.select("tr").last();
        int indexOfPlayers = 0;
        String playerURL = null;
        int count_of_players = 0;

        boolean isFieldPlayerHasNotStat;
        boolean isGoalkeeperHasNotStat;

        while (true)
        {
            Element currentPlayerElement = playersTable.select("tr").get(indexOfPlayers + 1);
            playerURL = currentPlayerElement.select("a").attr("href");
            String jerseyNumber = currentPlayerElement.select("td").get(0).text();
            int jersey_number = 0;
            if (!jerseyNumber.isEmpty())
                jersey_number = Integer.parseInt(jerseyNumber);
            String playerPosition = currentPlayerElement.select("td").last().text();

            isFieldPlayerHasNotStat = fieldPlayersStatisticsTable.select("a[href=" + playerURL + "]").isEmpty();
            isGoalkeeperHasNotStat = goalkeepersStatisticsTable.select("a[href=" + playerURL + "]").isEmpty();

            //получение данных для Player
            getPlayer(playerURL, clubName, jersey_number, playerPosition, isFieldPlayerHasNotStat, isGoalkeeperHasNotStat);

            indexOfPlayers++;
            count_of_players++;
            if (currentPlayerElement == lastPlayerElement)
            {
                break;
            }
        }
        return count_of_players;
    }

    private void getPlayer(String playerURL, String clubName, int jersey_number, String position, boolean isFieldPlayerHasNotStat, boolean isGoalkeeperHasNotStat){
        Document playerPage = getDocument(playerURL);
        Elements profilePlayerTable = playerPage.select("table[class=profile-table]");
        String [] surnameAndNameOfPlayer = playerPage.select("h1[class=titleH1]").text().split(" ");
        String surname_of_player = null;
        String name_of_player = surnameAndNameOfPlayer[0];
        if (surnameAndNameOfPlayer.length == 3)
        {
            surname_of_player = surnameAndNameOfPlayer[1] + " " + surnameAndNameOfPlayer[2];
        }
        else if (surnameAndNameOfPlayer.length == 2) {
            surname_of_player = surnameAndNameOfPlayer[1];
            if(surname_of_player.equals("Д′Амброзио"))
            {
                surname_of_player = "Амброзио";
            }
        }
        else
            surname_of_player = name_of_player;
        LocalDate date_of_birth = null;
        try {
            String dateOfBirthAndAge = profilePlayerTable.select("tr").get(0).select("td").get(0).text();
            String dateOfBirth = dateOfBirthAndAge.substring(0, dateOfBirthAndAge.indexOf(" |"));
            date_of_birth = getDataOfBirth(dateOfBirth);
        }
        catch (Exception e)
        {
            date_of_birth = LocalDate.of(1999, 12,31);
        }
        String nationality = profilePlayerTable.select("td").get(1).select("i").first().attr("title");

        playerArrayList.add(new Player(surname_of_player, name_of_player, date_of_birth, nationality, clubName, jersey_number, position));

        //получение данных для PlayerStatistics
        getPlayerStatistics(playerPage.select("table[class=\"stat-table career\"]"), position, isFieldPlayerHasNotStat, isGoalkeeperHasNotStat);

        //вывод спарсенных данных о игроке
        System.out.println("#########################################################");
        System.out.println("Фамилия игрока: " + surname_of_player);
        System.out.println("Имя игрока: " + name_of_player);
        System.out.println("Дата рождения: " + date_of_birth);
        System.out.println("Национальность: " + nationality);
        System.out.println("Наименование клуба: " + clubName);
        System.out.println("Игровой номер: " + jersey_number);
        System.out.println("Позиция: " + position);
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
    }

    private LocalDate getDataOfBirth(String dateOfBirth) {
        String[] dateOfBirthElements = dateOfBirth.split(" ");
        int month = switch (dateOfBirthElements[1]) {
            case "января" -> 1;
            case "февраля" -> 2;
            case "марта" -> 3;
            case "апреля" -> 4;
            case "мая" -> 5;
            case "июня" -> 6;
            case "июля" -> 7;
            case "августа" -> 8;
            case "сентября" -> 9;
            case "октября" -> 10;
            case "ноября" -> 11;
            case "декабря" -> 12;
            default -> 0;
        };
        return LocalDate.of(Integer.parseInt(dateOfBirthElements[2]), month, Integer.parseInt(dateOfBirthElements[0]));
    }

    private void getPlayerStatistics(Elements playerStatisticsTable, String playerPosition, boolean isFieldPlayerHasNotStat, boolean isGoalkeeperHasNotStat)
    {
        int count_of_matches = 0;
        int count_of_min_per_match = 0;
        int goals = 0;
        int assists = 0;
        Integer missed_goals = null;
        Integer clean_sheets = null;
        int yellow_cards = 0;
        int red_cards = 0;
        if (playerPosition.equals("вратарь"))
        {
            if(!isGoalkeeperHasNotStat)
            {
                Elements currentGoalkeeperStatistics = playerStatisticsTable.select("tr").get(1).select("td");
                count_of_matches = Integer.parseInt(currentGoalkeeperStatistics.get(3).text());
                count_of_min_per_match = Integer.parseInt(currentGoalkeeperStatistics.get(15).text());
                goals = Integer.parseInt(currentGoalkeeperStatistics.get(4).text());
                assists = Integer.parseInt(currentGoalkeeperStatistics.get(6).text());
                missed_goals = Integer.parseInt(currentGoalkeeperStatistics.get(9).text());
                clean_sheets = Integer.parseInt(currentGoalkeeperStatistics.get(11).text());
                yellow_cards = Integer.parseInt(currentGoalkeeperStatistics.get(7).text());
                red_cards = Integer.parseInt(currentGoalkeeperStatistics.get(8).text());
            }
            else
            {
                missed_goals = 0;
                clean_sheets = 0;
            }
        }
        else {
            if (!isFieldPlayerHasNotStat) {
                Elements currentFieldPlayerStatistics = playerStatisticsTable.select("tr").get(1).select("td");
                count_of_matches = Integer.parseInt(currentFieldPlayerStatistics.get(3).text());
                count_of_min_per_match = Integer.parseInt(currentFieldPlayerStatistics.get(10).text());
                goals = Integer.parseInt(currentFieldPlayerStatistics.get(4).text());
                assists = Integer.parseInt(currentFieldPlayerStatistics.get(6).text());
                yellow_cards = Integer.parseInt(currentFieldPlayerStatistics.get(8).text());
                red_cards = Integer.parseInt(currentFieldPlayerStatistics.get(9).text());
            }
        }

        playerStatisticsArrayList.add(new PlayerStatistics(count_of_matches, count_of_min_per_match, goals, assists, missed_goals,
                clean_sheets, yellow_cards, red_cards));

        //Вывод спарсенных данных о статистике игрока
        System.out.println("Сыгранных матчей: " + count_of_matches);
        System.out.println("Сыгранных минут в среднем за матч: " + count_of_min_per_match);
        System.out.println("Голы: " + goals);
        System.out.println("Ассисты: " + assists);
        System.out.println("Пропущенные голы: " + missed_goals);
        System.out.println("Сухие матчи: " + clean_sheets);
        System.out.println("Желтые карточки: " + yellow_cards);
        System.out.println("Красные карточки: " + red_cards);
    }

    private Document getDocument(String url)
    {
        Document page = null;
        try {
            page = Jsoup.connect(url)
                    .userAgent("Mozilla / 5.0 (Windows NT 10.0; Win64; x64) AppleWebKit / 537.36 (KHTML, как Gecko) Chrome / 96.0.4664.45 Safari / 537.36")
                    .get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return page;
    }

    public ArrayList<League> getLeaguesArrayList() {
        return leaguesArrayList;
    }

    public ArrayList<FootballClub> getFootballClubsArrayList() {
        return footballClubsArrayList;
    }

    public ArrayList<ClubStatistics> getClubStatisticsArrayList() {
        return clubStatisticsArrayList;
    }

    public ArrayList<Player> getPlayerArrayList() {
        return playerArrayList;
    }

    public ArrayList<PlayerStatistics> getPlayerStatisticsArrayList() {
        return playerStatisticsArrayList;
    }
}
