package if21b151.application.card.repository;

import if21b151.application.card.model.Card;
import if21b151.application.card.model.CardPackage;
import if21b151.application.card.service.CardService;
import if21b151.application.card.service.CardServiceImpl;
import if21b151.application.user.model.User;
import if21b151.application.user.repository.UserRepository;
import if21b151.application.user.repository.UserRepositoryImpl;
import if21b151.database.DataBase;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CardRepositoryImpl implements CardRepository {

    @Override
    public int countPackages() {
        int count = 0;

        String sql = """
                select count (*) from packages
                """;
        try {
            PreparedStatement statement = DataBase.getConnection().prepareStatement(sql);
            ResultSet results = statement.executeQuery();
            while (results.next()) {
                count = results.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return count;
    }

    @Override
    public void addNewPackage(CardPackage cardPackage) {

        int packageID = countPackages() + 1;
        List<String> cardIDs = new ArrayList<>();
        for (Card card : cardPackage.getPackageCards()) {
            //1 add card to cards
            insertCardInCards(card);
            //2 add ID to cardIDs Array
            cardIDs.add(card.getId());
        }
        //3 add IDs to Package Table
        addIDToPackages(packageID, cardIDs);
    }

    @Override
    public int acquirePackage(User user) {
        UserRepository userRepository = new UserRepositoryImpl();

        if (user.getStats().getCoins() >= 5) {
            if (countPackages() > 0) {
                /*get full list incl package id*/
                List<String> cardIDs = getLatestPackageCardIDs();
                /*extract package ID from list*/
                int packageID = Integer.parseInt(cardIDs.get(5));
                /*remove package id from list*/
                cardIDs.remove(5);
                for (String id : getLatestPackageCardIDs()) {
                    //
                    //2 change cards with this IDs in cards table to user owning
                    //
                    setUsernameToCard(user, id);
                }
                //
                //3 delete package with this ID
                //
                deletePackageByID(packageID);
                user.getStats().setCoins(user.getStats().getCoins() - 5);
                userRepository.updateStats(user);
                return 2;
            } else {
                return 1;
                //no packages left
            }
        } else {
            return 0;
            //out of money
        }
    }

    @Override
    public List<Card> getAllCardsByUsername(String username) {
        List<Card> userCards = new ArrayList<>();
        String sql = """
                select * from cards where username=?
                """;
        try {
            PreparedStatement statement = DataBase.getConnection().prepareStatement(sql);
            statement.setString(1, username);
            ResultSet results = statement.executeQuery();
            while (results.next()) {
                Card card = new Card(results.getString(1), results.getString(2), results.getString(3), results.getInt(4));
                userCards.add(card);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return userCards;
    }

    @Override
    public List<Card> getUserDeckCards(String username) {
        List<Card> userCards = new ArrayList<>();
        String sql = """
                select * from cards where username=? AND deck='1'
                """;
        try {
            PreparedStatement statement = DataBase.getConnection().prepareStatement(sql);
            statement.setString(1, username);
            ResultSet results = statement.executeQuery();
            while (results.next()) {
                Card card = new Card(results.getString(1), results.getString(2), results.getString(3), results.getInt(4));
                userCards.add(card);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return userCards;
    }

    @Override
    public int configureDeck(User user, List<String> cardIDs) {
        //1 check if list is 4 long
        if (cardIDs.size() == 4) {
            //2 check for deck cards
            if (!(getUserDeckCards(user.getUsername()).size() > 0)) {
                //3 change selected cards (by ID) to deck = 1:
                for (String id : cardIDs) {
                    String sql = """
                            update cards set deck = ? where username=? AND id=?
                            """;
                    try {
                        PreparedStatement statement = DataBase.getConnection().prepareStatement(sql);
                        statement.setInt(1, 1);
                        statement.setString(2, user.getUsername());
                        statement.setString(3, id);
                        statement.execute();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
                return 0;
            } else {
                //user hat schon karten im deck
                return 1;
            }
        } else {
            //zu wenig karten-ids übergeben
            return 2;
        }
    }

    private void setUsernameToCard(User user, String id) {
        String sql = """
                update cards set username=? where id=?
                """;
        try {
            PreparedStatement statement = DataBase.getConnection().prepareStatement(sql);
            statement.setString(1, user.getUsername());
            statement.setString(2, id);

            statement.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void deletePackageByID(int id) {
        String sql = """
                delete from packages where id=?
                """;
        try {
            PreparedStatement statement = DataBase.getConnection().prepareStatement(sql);
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private List<String> getLatestPackageCardIDs() {
        String sql = """
                select * from packages limit 1
                """;
        List<String> cardIDs = new ArrayList<>();
        try {
            PreparedStatement statement = DataBase.getConnection().prepareStatement(sql);
            ResultSet results = statement.executeQuery();
            while (results.next()) {
                int packageID = results.getInt(1);
                String cardID1 = results.getString(2);
                String cardID2 = results.getString(3);
                String cardID3 = results.getString(4);
                String cardID4 = results.getString(5);
                String cardID5 = results.getString(6);
                cardIDs.add(cardID1);
                cardIDs.add(cardID2);
                cardIDs.add(cardID3);
                cardIDs.add(cardID4);
                cardIDs.add(cardID5);
                cardIDs.add(Integer.toString(packageID));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return cardIDs;
    }

    private void insertCardInCards(Card card) {

        String sql = """
                insert into cards (id,name,username,damage,deck, trade) values(?,?,?,?,?,?)
                """;

        try {
            PreparedStatement statement = DataBase.getConnection().prepareStatement(sql);
            statement.setString(1, card.getId());
            statement.setString(2, card.getName());
            statement.setString(3, "0");
            statement.setDouble(4, card.getDamage());
            statement.setInt(5, 0);
            statement.setInt(6, 0);

            statement.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void addIDToPackages(int id, List<String> IDs) {
        String sql = """
                insert into packages (id,card_id_1,card_id_2,card_id_3,card_id_4,card_id_5) values(?,?,?,?,?,?)
                """;

        try {
            PreparedStatement statement = DataBase.getConnection().prepareStatement(sql);
            statement.setInt(1, id);
            statement.setString(2, IDs.get(0));
            statement.setString(3, IDs.get(1));
            statement.setString(4, IDs.get(2));
            statement.setString(5, IDs.get(3));
            statement.setString(6, IDs.get(4));

            statement.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
